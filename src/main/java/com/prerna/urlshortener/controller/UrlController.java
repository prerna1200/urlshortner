package com.prerna.urlshortener.controller;

import com.prerna.urlshortener.service.UrlService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class UrlController {

    // creating service object manually
    // since we are not using DB or complex config
    private UrlService urlService = new UrlService();


    // API to create short url
    // input: original url
    // output: short code
    @PostMapping("/shorten")
    public String createShortUrl(@RequestBody Map<String, String> body) {

        String url = body.get("url");

        String code = urlService.createShortUrl(url);

        return code;
    }

    // redirect api
    // when user opens short code
    @GetMapping("/{code}")
    public void redirect(@PathVariable String code,
                         HttpServletResponse response) throws IOException {

        String original = urlService.getOriginalUrl(code);

        if (original != null) {
            response.sendRedirect(original);
        } else {
            response.sendError(404, "Not found");
        }
    }


    // stats api
    // return top 3 domains
    @GetMapping("/stats")
    public Map<String, Integer> getStats() {
        return urlService.getStats();
    }
}