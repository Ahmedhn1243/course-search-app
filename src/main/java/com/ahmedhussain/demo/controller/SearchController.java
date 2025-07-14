// src/main/java/com/example/coursesearch/controller/SearchController.java
package com.ahmedhussain.demo.controller;

import com.ahmedhussain.demo.DTO.SearchRequest;
import com.ahmedhussain.demo.DTO.SearchResponse;
import com.ahmedhussain.demo.service.SearchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public SearchResponse searchCourses(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false, defaultValue = "upcoming") String sort,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        SearchRequest request = new SearchRequest();
        request.setQ(q);
        request.setCategory(category);
        request.setType(type);
        request.setMinAge(minAge);
        request.setMaxAge(maxAge);
        request.setMinPrice(minPrice);
        request.setMaxPrice(maxPrice);
        request.setStartDate(startDate);
        request.setSort(sort);
        request.setPage(page);
        request.setSize(size);

        return searchService.searchCourses(request);
    }
}
