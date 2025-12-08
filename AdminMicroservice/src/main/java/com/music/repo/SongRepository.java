package com.music.repo;

import com.music.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByNameContainingIgnoreCase(String name);
    List<Song> findBySingerContainingIgnoreCase(String singer);
    List<Song> findByMusicDirectorContainingIgnoreCase(String musicDirector);
    List<Song> findByAlbumContainingIgnoreCase(String album);
    List<Song> findByVisibleTrue();

}
