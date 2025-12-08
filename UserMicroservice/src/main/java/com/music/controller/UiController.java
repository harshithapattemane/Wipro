package com.music.controller;

import com.music.client.AdminCatalogFeign;
import com.music.dto.AdminSong;
import com.music.entity.PlayList;
import com.music.entity.PlayListSongs;
import com.music.entity.User;
import com.music.service.PlayListService;
import com.music.service.UserService;
import com.music.security.JwtUtil;
import com.music.repo.UserRepository;
import com.music.repo.PlayListSongsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@Controller
@RequestMapping("/ui")
public class UiController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlayListService playListService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayListSongsRepository playListSongsRepository;

    @Autowired
    private AdminCatalogFeign adminCatalogClient;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String emailId,
            @RequestParam String phoneNumber,
            @RequestParam String role,
            Model model
    ) {
        try {
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setEmailId(emailId);
            u.setPhoneNumber(phoneNumber);
            u.setRole(role);

            userService.addUser(u);
            model.addAttribute("success", "User registered successfully!");
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpServletResponse response
    ) {
        User probe = new User();
        probe.setUsername(username);
        probe.setPassword(password);

        boolean ok = userService.findUser(probe);
        if (!ok) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        // Load full user to build JWT
        User db = userRepository.findByUsername(username).orElse(null);
        if (db == null) {
            model.addAttribute("error", "User not found");
            return "login";
        }

        String token = jwtUtil.generateToken(db.getUsername(), db.getId(), db.getRole());
        Cookie cookie = new Cookie("AUTH_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600); // 1 hour
        response.addCookie(cookie);

        return "redirect:/ui/playlists";
    }

    @PostMapping("/logout")
    public String handleLogout(HttpServletResponse response) {
        Cookie cookie = new Cookie("AUTH_TOKEN", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete
        response.addCookie(cookie);
        return "redirect:/ui/home";
    }

    @GetMapping("/playlists")
    public String listPlaylists(
            @RequestParam(name = "q", required = false) String q,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;

        List<PlayList> playlists;
        if (q != null && !q.isBlank()) {
            playlists = playListService.searchPlaylistsForOwner(username, q);
            model.addAttribute("searchTerm", q);
        } else {
            playlists = playListService.getAllPlayListsByOwner(username);
        }

        model.addAttribute("playlists", playlists);
        return "playlists";
    }

    @GetMapping("/playlists/new")
    public String showCreatePlaylistForm() {
        return "playlist-add";
    }

    @PostMapping("/playlists/new")
    public String handleCreatePlaylist(
            @RequestParam String name
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth != null ? auth.getName() : null;
        User owner = userRepository.findByUsername(username).orElse(null);

        PlayList p = new PlayList();
        p.setName(name);
        p.setOwner(owner);
        playListService.addPlayList(p);

        return "redirect:/ui/playlists";
    }

    @GetMapping("/playlists/{id}")
    public String playlistDetail(@PathVariable Long id, Model model) {
        PlayList playlist = playListService.getPlayList(id);
        if (!isOwner(playlist)) {
            return "redirect:/ui/playlists";
        }
        List<PlayListSongs> songs = playListService.listSongs(id);

        model.addAttribute("playlist", playlist);
        model.addAttribute("songs", songs);

        return "playlist-detail";
    }

    // Note: Free-text song creation from user UI has been removed.
    // Users should add songs only from Admin catalog.

   

    @GetMapping("/playlists/{id}/songs/catalog")
    public String browseAdminCatalog(
            @PathVariable Long id,
            // New style: distinct search bars
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "singer", required = false) String singer,
            @RequestParam(name = "album", required = false) String album,
            @RequestParam(name = "director", required = false) String director,
            // Backward compatibility with old UI
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "q", required = false) String q,
            Model model
    ) {
        PlayList playlist = playListService.getPlayList(id);
        if (!isOwner(playlist)) {
            return "redirect:/ui/playlists";
        }

        // Decide which search to run: prefer new dedicated params; if none, fall back to old type/q; else list visible
        java.util.List<AdminSong> songs;
        if (name != null && !name.isBlank()) {
            songs = adminCatalogClient.searchByName(name);
        } else if (singer != null && !singer.isBlank()) {
            songs = adminCatalogClient.searchBySinger(singer);
        } else if (album != null && !album.isBlank()) {
            songs = adminCatalogClient.searchByAlbum(album);
        } else if (director != null && !director.isBlank()) {
            songs = adminCatalogClient.searchByDirector(director);
        } else if (q != null && !q.isBlank()) {
            String t = (type == null || type.isBlank()) ? "name" : type;
            switch (t) {
                case "singer" -> songs = adminCatalogClient.searchBySinger(q);
                case "album" -> songs = adminCatalogClient.searchByAlbum(q);
                case "director" -> songs = adminCatalogClient.searchByDirector(q);
                case "releaseDate", "date" -> {
                    String needle = q == null ? "" : q.trim();
                    songs = adminCatalogClient.listVisible().stream()
                            .filter(s -> s.getReleaseDate() != null && s.getReleaseDate().toString().contains(needle))
                            .toList();
                }
                default -> songs = adminCatalogClient.searchByName(q);
            }
        } else {
            songs = adminCatalogClient.listVisible();
        }

        boolean hasFilters =
                (name != null && !name.isBlank()) ||
                (singer != null && !singer.isBlank()) ||
                (album != null && !album.isBlank()) ||
                (director != null && !director.isBlank()) ||
                (q != null && !q.isBlank());

        model.addAttribute("playlist", playlist);
        model.addAttribute("songs", songs);
        // Echo back the last used inputs for UX (optional)
        model.addAttribute("name", name);
        model.addAttribute("singer", singer);
        model.addAttribute("album", album);
        model.addAttribute("director", director);
        model.addAttribute("hasFilters", hasFilters);
        return "playlist-catalog";
    }

    @PostMapping("/playlists/{id}/songs/add-from-admin")
    public String addFromAdmin(
            @PathVariable Long id,
            @RequestParam("songId") Long songId
    ) {
        PlayList playlist = playListService.getPlayList(id);
        if (!isOwner(playlist)) {
            return "redirect:/ui/playlists";
        }
        playListService.addSongFromAdmin(id, songId);
        return "redirect:/ui/playlists/" + id;
    }

    @GetMapping("/playlists/{id}/songs/search")
    public String searchSongsInPlaylist(
            @PathVariable Long id,
            @RequestParam String q,
            Model model
    ) {
        PlayList playlist = playListService.getPlayList(id);
        if (!isOwner(playlist)) {
            return "redirect:/ui/playlists";
        }
        List<PlayListSongs> songs = playListService.searchSongsInPlaylist(id, q);

        model.addAttribute("playlist", playlist);
        model.addAttribute("songs", songs);
        model.addAttribute("searchTerm", q);

        return "playlist-detail";
    }

    

    @GetMapping("/playlists/{id}/rename")
    public String showRenamePlaylist(@PathVariable Long id, Model model) {
        PlayList p = playListService.getPlayList(id);
        if (!isOwner(p)) return "redirect:/ui/playlists";
        model.addAttribute("playlist", p);
        return "playlist-rename";
    }

    @PostMapping("/playlists/{id}/rename")
    public String renamePlaylist(@PathVariable Long id, @RequestParam String name) {
        PlayList p = playListService.getPlayList(id);
        if (!isOwner(p)) return "redirect:/ui/playlists";
        p.setName(name);
        playListService.updatePlayList(p);
        return "redirect:/ui/playlists/" + id;
    }

    @PostMapping("/playlists/{id}/delete")
    public String deletePlaylist(@PathVariable Long id) {
        PlayList p = playListService.getPlayList(id);
        if (!isOwner(p)) return "redirect:/ui/playlists";
        playListService.deletePlayList(id);
        return "redirect:/ui/playlists";
    }

    

    @PostMapping("/playlists/songs/{songId}/edit")
    public String editSong(@PathVariable Long songId, @RequestParam String name) {
        PlayListSongs s = playListSongsRepository.findById(songId)
                .orElse(null);
        if (s == null || !isOwner(s.getPlayList())) return "redirect:/ui/playlists";
        playListService.updateSongInPlaylist(songId, name);
        return "redirect:/ui/playlists/" + s.getPlayList().getId();
    }

    @PostMapping("/playlists/songs/{songId}/delete")
    public String deleteSong(@PathVariable Long songId) {
        PlayListSongs s = playListSongsRepository.findById(songId)
                .orElse(null);
        if (s == null || !isOwner(s.getPlayList())) return "redirect:/ui/playlists";
        Long playlistId = s.getPlayList().getId();
        playListService.deleteSong(songId);
        return "redirect:/ui/playlists/" + playlistId;
    }

    private boolean isOwner(PlayList playlist) {
        if (playlist == null || playlist.getOwner() == null) return false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && playlist.getOwner().getUsername().equals(auth.getName());
    }
}
