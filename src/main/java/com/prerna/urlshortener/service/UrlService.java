package com.prerna.urlshortener.service;

import com.prerna.urlshortener.util.CodeGenerator;

import java.net.URL;
import java.util.*;
//Service class
public class UrlService {

    // storing shortCode -> original url
    // using map because we are not using DB (as per assignment)
    private Map<String, String> shortToOriginal = new HashMap<>();

    // storing original -> short so that same url should return same code
    private Map<String, String> originalToShort = new HashMap<>();

    // to keep track of domain usage for stats api
    private Map<String, Integer> domainCount = new HashMap<>();


    // this method will create short url
    public String createShortUrl(String originalUrl) {

        // check if already present
        // assignment says same url should give same short url
        if (originalToShort.containsKey(originalUrl)) {
            return originalToShort.get(originalUrl);
        }

        // generate random code of length 6
        String code = CodeGenerator.generateCode(6);

        // save in both maps
        shortToOriginal.put(code, originalUrl);
        originalToShort.put(originalUrl, code);

        // update domain count for metrics api
        addDomainCount(originalUrl);

        return code;
    }


    // used while redirect
    // get original url from short code
    public String getOriginalUrl(String code) {
        return shortToOriginal.get(code);
    }


    // counting domain name like youtube.com google.com etc
    // needed for stats api (top 3 domains)
    private void addDomainCount(String url) {
        try {
            URL u = new URL(url);
            String domain = u.getHost();

            // increase count if exists else put 1
            domainCount.put(domain,
                    domainCount.getOrDefault(domain, 0) + 1);

        } catch (Exception e) {
            // if url invalid just ignore
        }
    }


    // return top 3 domains based on count
    public Map<String, Integer> getStats() {

        // convert to list to sort
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(domainCount.entrySet());

        // sorting in descending order
        list.sort((a, b) -> b.getValue() - a.getValue());

        Map<String, Integer> result = new LinkedHashMap<>();

        int count = 0;

        // only top 3 needed
        for (Map.Entry<String, Integer> e : list) {

            result.put(e.getKey(), e.getValue());
            count++;

            if (count == 3) break;
        }

        return result;
    }
}