package com.music.controller;

import com.music.entity.Song;
import com.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/songs/ui")
public class SongsUIController {

    @Autowired
    private SongService songService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "q", required = false) String q, Model model) {
        String query = (q == null) ? "" : q.trim();
        try {
            if (!query.isBlank()) {
                model.addAttribute("songs", songService.searchByName(query));
            } else {
                model.addAttribute("songs", songService.getAllSongs());
            }
        } catch (Exception ex) {
            
            model.addAttribute("songs", java.util.Collections.emptyList());
            model.addAttribute("error", "Could not load songs: " + ex.getMessage());
        }
        model.addAttribute("q", query);
        return "song-list";
    }

    @GetMapping("/add")
    public String addForm() {
        return "song-add";
    }

    @PostMapping("/add")
    public String addSubmit(
            @RequestParam String name,
            @RequestParam(required = false) String singer,
            @RequestParam(required = false) String musicDirector,
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String releaseDate,
            @RequestParam(required = false) Boolean visible,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirect
    ) {
        Song s = new Song();
        s.setName(name);
        s.setSinger(singer);
        s.setMusicDirector(musicDirector);
        s.setAlbum(album);

        if (releaseDate != null && !releaseDate.isBlank()) {
            try { s.setReleaseDate(LocalDate.parse(releaseDate)); } catch (Exception ignored) {}
        }

        s.setVisible(visible != null && visible);

        Song saved = songService.addSong(s);
        redirect.addFlashAttribute("success", "Song created successfully");
        return "redirect:/songs/ui/view/" + saved.getId();
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("song", songService.getSong(id));
            return "song-view";
        } catch (Exception ex) {
            // If not found or any error, go back to list with an error message
            return "redirect:/songs/ui/list?error=" + java.net.URLEncoder.encode("Song not found", java.nio.charset.StandardCharsets.UTF_8);
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("song", songService.getSong(id));
            return "song-edit";
        } catch (Exception ex) {
            return "redirect:/songs/ui/list?error=" + java.net.URLEncoder.encode("Song not found", java.nio.charset.StandardCharsets.UTF_8);
        }
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String singer,
            @RequestParam(required = false) String musicDirector,
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String releaseDate,
            @RequestParam(required = false) Boolean visible,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirect
    ) {
        try {
            Song s = songService.getSong(id);
            s.setName(name);
            s.setSinger(singer);
            s.setMusicDirector(musicDirector);
            s.setAlbum(album);
            if (releaseDate != null && !releaseDate.isBlank()) {
                try { s.setReleaseDate(LocalDate.parse(releaseDate)); } catch (Exception e) { s.setReleaseDate(null); }
            } else {
                s.setReleaseDate(null);
            }
            s.setVisible(visible != null && visible);
            songService.updateSong(s);
            redirect.addFlashAttribute("success", "Song updated successfully");
        } catch (Exception ex) {
            redirect.addFlashAttribute("error", "Could not update song: " + ex.getMessage());
        }
        
        return "redirect:/songs/ui/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirect) {
        try {
            songService.deleteSong(id);
            redirect.addFlashAttribute("success", "Song deleted successfully");
        } catch (Exception ex) {
            redirect.addFlashAttribute("error", "Could not delete song: " + ex.getMessage());
        }
        return "redirect:/songs/ui/list";
    }
}


