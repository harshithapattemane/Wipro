package com.music.client;

import com.music.dto.AdminSong;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "admin-service")
public interface AdminCatalogFeign {

    @GetMapping("/songs/visible")
    List<AdminSong> listVisible();

    @GetMapping("/songs/{id}")
    AdminSong getOne(@PathVariable("id") Long id);

    @GetMapping("/songs/search/name")
    List<AdminSong> searchByName(@RequestParam("q") String q);

    @GetMapping("/songs/search/singer")
    List<AdminSong> searchBySinger(@RequestParam("q") String q);

    @GetMapping("/songs/search/album")
    List<AdminSong> searchByAlbum(@RequestParam("q") String q);

    @GetMapping("/songs/search/director")
    List<AdminSong> searchByDirector(@RequestParam("q") String q);
}
