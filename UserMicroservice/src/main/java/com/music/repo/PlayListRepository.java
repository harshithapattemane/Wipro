package com.music.repo;

import com.music.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {
    List<PlayList> findByNameContainingIgnoreCase(String name);
    List<PlayList> findByOwner_Username(String username);
    List<PlayList> findByOwner_UsernameAndNameContainingIgnoreCase(String username, String name);
}
