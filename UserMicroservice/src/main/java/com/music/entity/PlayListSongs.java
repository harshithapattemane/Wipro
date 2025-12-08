package com.music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayListSongs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    
    private Long adminSongId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "play_list_id", nullable = false)
    private PlayList playList;
}
