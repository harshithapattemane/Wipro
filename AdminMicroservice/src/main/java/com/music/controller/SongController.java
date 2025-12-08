package com.music.controller;

import com.music.entity.Song;
import com.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        return new ResponseEntity<>(songService.addSong(song), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Song> updateSong(@RequestBody Song song) {
        return new ResponseEntity<>(songService.updateSong(song), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAll() {
        return new ResponseEntity<>(songService.getAllSongs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(songService.getSong(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/form", method = { RequestMethod.POST, RequestMethod.GET })
    public ResponseEntity<Song> addSongForm(
            @RequestParam String name,
            @RequestParam(required = false) String singer,
            @RequestParam(required = false) String musicDirector,
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String releaseDate,
            @RequestParam(required = false) Boolean visible
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
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Song>> searchByName(@RequestParam String q) {
        return new ResponseEntity<>(songService.searchByName(q), HttpStatus.OK);
    }

    @GetMapping("/search/singer")
    public ResponseEntity<List<Song>> searchBySinger(@RequestParam String q) {
        return new ResponseEntity<>(songService.searchBySinger(q), HttpStatus.OK);
    }

    @GetMapping("/search/director")
    public ResponseEntity<List<Song>> searchByDirector(@RequestParam String q) {
        return new ResponseEntity<>(songService.searchByMusicDirector(q), HttpStatus.OK);
    }

    @GetMapping("/search/album")
    public ResponseEntity<List<Song>> searchByAlbum(@RequestParam String q) {
        return new ResponseEntity<>(songService.searchByAlbum(q), HttpStatus.OK);
    }

    @GetMapping("/visible")
    public ResponseEntity<List<Song>> getVisibleSongs() {
        return new ResponseEntity<>(songService.getVisibleSongs(), HttpStatus.OK);
    }
}
