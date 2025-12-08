package com.music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    // Ensure songs are removed when a playlist is deleted to avoid FK errors
    @OneToMany(mappedBy = "playList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayListSongs> songs = new ArrayList<>();
}
