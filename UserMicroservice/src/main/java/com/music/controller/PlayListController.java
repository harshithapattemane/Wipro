package com.music.controller;

import com.music.entity.PlayList;
import com.music.entity.PlayListSongs;
import com.music.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlayListController {

    @Autowired
    private PlayListService playListService;

    @PostMapping
    public ResponseEntity<PlayList> addPlaylist(@RequestBody PlayList p) {
        return new ResponseEntity<>(playListService.addPlayList(p), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PlayList> updatePlaylist(@RequestBody PlayList p) {
        return new ResponseEntity<>(playListService.updatePlayList(p), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playListService.deletePlayList(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PlayList>> getAllPlaylists() {
        return new ResponseEntity<>(playListService.getAllPlayLists(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayList> getOnePlaylist(@PathVariable Long id) {
        return new ResponseEntity<>(playListService.getPlayList(id), HttpStatus.OK);
    }

    @PostMapping("/{playlistId}/songs")
    public ResponseEntity<PlayListSongs> addSongJson(
            @PathVariable Long playlistId,
            @RequestBody PlayListSongs s) {
        return new ResponseEntity<>(playListService.addSong(playlistId, s.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{playlistId}/songs")
    public ResponseEntity<List<PlayListSongs>> getSongs(@PathVariable Long playlistId) {
        return new ResponseEntity<>(playListService.listSongs(playlistId), HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<PlayList>> searchPlaylists(@RequestParam String q) {
        return new ResponseEntity<>(playListService.searchPlaylists(q), HttpStatus.OK);
    }


    @DeleteMapping("/songs/{songId}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long songId) {
        playListService.deleteSong(songId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add-form", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<PlayList> addPlaylistForm(@RequestParam String name) {
        PlayList p = new PlayList();
        p.setName(name);
        return new ResponseEntity<>(playListService.addPlayList(p), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/songs-form", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<PlayListSongs> addSongForm(
            @RequestParam Long playlistId,
            @RequestParam String name) {
        PlayListSongs saved = playListService.addSong(playlistId, name);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PutMapping("/songs/{songId}")
    public ResponseEntity<PlayListSongs> updatePlaylistSong(
            @PathVariable Long songId,
            @RequestParam String name
    ) {
        PlayListSongs updated = playListService.updateSongInPlaylist(songId, name);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    
    @GetMapping("/{playlistId}/songs/search")
    public ResponseEntity<List<PlayListSongs>> searchSongs(
            @PathVariable Long playlistId,
            @RequestParam String q
    ) {
        return new ResponseEntity<>(playListService.searchSongsInPlaylist(playlistId, q), HttpStatus.OK);
    }

}
