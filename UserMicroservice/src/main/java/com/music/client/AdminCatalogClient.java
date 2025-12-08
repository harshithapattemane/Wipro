package com.music.client;

import com.music.dto.AdminSong;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
@ConditionalOnProperty(name = "admin.service.base-url")
public class AdminCatalogClient {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public AdminCatalogClient(@Value("${admin.service.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<AdminSong> listVisible() {
        String url = baseUrl + "/songs/visible";
        try {
            ResponseEntity<AdminSong[]> resp = restTemplate.getForEntity(url, AdminSong[].class);
            return Arrays.asList(resp.getBody() != null ? resp.getBody() : new AdminSong[0]);
        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }

    public AdminSong getOne(Long id) {
        String url = baseUrl + "/songs/" + id;
        try {
            return restTemplate.getForObject(url, AdminSong.class);
        } catch (RestClientException ex) {
            return null;
        }
    }

    public List<AdminSong> searchByName(String q) {
        String url = baseUrl + "/songs/search/name?q=" + encode(q);
        try {
            ResponseEntity<AdminSong[]> resp = restTemplate.getForEntity(url, AdminSong[].class);
            return Arrays.asList(resp.getBody() != null ? resp.getBody() : new AdminSong[0]);
        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }

    public List<AdminSong> searchBySinger(String q) {
        String url = baseUrl + "/songs/search/singer?q=" + encode(q);
        try {
            ResponseEntity<AdminSong[]> resp = restTemplate.getForEntity(url, AdminSong[].class);
            return Arrays.asList(resp.getBody() != null ? resp.getBody() : new AdminSong[0]);
        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }

    public List<AdminSong> searchByAlbum(String q) {
        String url = baseUrl + "/songs/search/album?q=" + encode(q);
        try {
            ResponseEntity<AdminSong[]> resp = restTemplate.getForEntity(url, AdminSong[].class);
            return Arrays.asList(resp.getBody() != null ? resp.getBody() : new AdminSong[0]);
        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }

    public List<AdminSong> searchByDirector(String q) {
        String url = baseUrl + "/songs/search/director?q=" + encode(q);
        try {
            ResponseEntity<AdminSong[]> resp = restTemplate.getForEntity(url, AdminSong[].class);
            return Arrays.asList(resp.getBody() != null ? resp.getBody() : new AdminSong[0]);
        } catch (RestClientException ex) {
            return Collections.emptyList();
        }
    }

    
    public List<AdminSong> searchByReleaseDate(String q) {
        if (q == null || q.isBlank()) return listVisible();
        String needle = q.trim().toLowerCase();
        try {
            List<AdminSong> all = listVisible();
            return all.stream()
                    .filter(s -> s.getReleaseDate() != null && s.getReleaseDate().toString().toLowerCase().contains(needle))
                    .toList();
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    private String encode(String s) {
        return s == null ? "" : URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
