package com.music.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AdminSong {
    private Long id;
    private String name;
    private String singer;
    private String musicDirector;
    private String album;
    private LocalDate releaseDate;
    private boolean visible;
}
