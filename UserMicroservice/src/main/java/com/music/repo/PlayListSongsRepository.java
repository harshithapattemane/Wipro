package com.music.repo;

import com.music.entity.PlayListSongs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayListSongsRepository extends JpaRepository<PlayListSongs, Long> {
    List<PlayListSongs> findByPlayList_Id(Long playListId);
    List<PlayListSongs> findByPlayList_IdAndNameContainingIgnoreCase(Long playListId, String name);
}
