package com.music.service;


import com.music.entity.Song;
import com.music.repo.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public Song addSong(Song song) { return songRepository.save(song); }

    public Song updateSong(Song song) {
        if (song.getId() == null || !songRepository.existsById(song.getId())) {
            throw new RuntimeException("Song with ID " + song.getId() + " does not exist.");
        }
        return songRepository.save(song);
    }

    public void deleteSong(Long id) {
        if (!songRepository.existsById(id)) {
            throw new RuntimeException("Song with ID " + id + " does not exist.");
        }
        songRepository.deleteById(id);
    }

    public List<Song> getAllSongs() { return songRepository.findAll(); }

    public Song getSong(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song with ID " + id + " does not exist."));
    }

    public List<Song> searchByName(String q)
    { 
    	return songRepository.findByNameContainingIgnoreCase(q);
    }
    
    public List<Song> searchBySinger(String q) 
    { 
    	return songRepository.findBySingerContainingIgnoreCase(q); 
    }
    
    public List<Song> searchByMusicDirector(String q) 
    { 
    	return songRepository.findByMusicDirectorContainingIgnoreCase(q); 
    }
    
    public List<Song> searchByAlbum(String q) 
    { 
    	return songRepository.findByAlbumContainingIgnoreCase(q); 
    }
    
    public List<Song> getVisibleSongs() {
        return songRepository.findByVisibleTrue();
    }

}
