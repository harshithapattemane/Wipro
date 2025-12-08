package com.music.service;

import com.music.client.AdminCatalogFeign;
import com.music.dto.AdminSong;
import com.music.entity.PlayList;
import com.music.entity.PlayListSongs;
import com.music.repo.PlayListRepository;
import com.music.repo.PlayListSongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private PlayListSongsRepository playListSongsRepository;

    @Autowired
    private AdminCatalogFeign adminCatalogClient;

    public PlayList addPlayList(PlayList p) { return playListRepository.save(p); }

    public PlayList updatePlayList(PlayList p) {
        if (p.getId() == null || !playListRepository.existsById(p.getId())) {
            throw new RuntimeException("Playlist with ID " + p.getId() + " does not exist.");
        }
        return playListRepository.save(p);
    }

    public void deletePlayList(Long id) {
        if (!playListRepository.existsById(id)) {
            throw new RuntimeException("Playlist with ID " + id + " does not exist.");
        }
        playListRepository.deleteById(id);
    }

    public List<PlayList> getAllPlayLists() { return playListRepository.findAll(); }

    public List<PlayList> getAllPlayListsByOwner(String username) {
        return playListRepository.findByOwner_Username(username);
    }

    public PlayListSongs addSong(Long playlistId, String name) {
        PlayList playlist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        PlayListSongs s = new PlayListSongs();
        s.setName(name);
        s.setPlayList(playlist);

        return playListSongsRepository.save(s);
    }

    public PlayListSongs addSongFromAdmin(Long playlistId, Long adminSongId) {
        PlayList playlist = playListRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        AdminSong adminSong = adminCatalogClient.getOne(adminSongId);
        if (adminSong == null || !adminSong.isVisible()) {
            throw new RuntimeException("Selected song is not available");
        }

        PlayListSongs s = new PlayListSongs();
        s.setPlayList(playlist);
        s.setAdminSongId(adminSong.getId());
        
        s.setName(adminSong.getName());
        return playListSongsRepository.save(s);
    }

    public List<PlayListSongs> listSongs(Long playListId) { return playListSongsRepository.findByPlayList_Id(playListId); }

    public List<PlayListSongs> searchSongs(Long playListId, String q) {
        return playListSongsRepository.findByPlayList_IdAndNameContainingIgnoreCase(playListId, q);
    }

    public void deleteSong(Long songId) {
        if (!playListSongsRepository.existsById(songId)) {
            throw new RuntimeException("Song with ID " + songId + " does not exist.");
        }
        playListSongsRepository.deleteById(songId);
    }
    
    public PlayList getPlayList(Long id) {
        return playListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist with ID " + id + " not found"));
    }
    
    public List<PlayList> searchPlaylists(String q) {
        return playListRepository.findByNameContainingIgnoreCase(q);
    }

    public List<PlayList> searchPlaylistsForOwner(String username, String q) {
        return playListRepository.findByOwner_UsernameAndNameContainingIgnoreCase(username, q);
    }

    public PlayListSongs updateSongInPlaylist(Long songId, String newName) {
        PlayListSongs s = playListSongsRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Playlist song with ID " + songId + " not found"));

        s.setName(newName);
        return playListSongsRepository.save(s);
    }

    public List<PlayListSongs> searchSongsInPlaylist(Long playlistId, String q) {
        return playListSongsRepository.findByPlayList_IdAndNameContainingIgnoreCase(playlistId, q);
    }
}
