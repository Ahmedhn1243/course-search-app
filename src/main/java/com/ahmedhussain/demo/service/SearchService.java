package com.ahmedhussain.demo.service;

import com.ahmedhussain.demo.DTO.SearchRequest;
import com.ahmedhussain.demo.DTO.SearchResponse;
import com.ahmedhussain.demo.documents.CourseDocument;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.data.elasticsearch.core.SearchHit;

@Service
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public SearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public SearchResponse searchCourses(SearchRequest request) {
        // 1. Build the base query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 2. Add full-text search
        if (request.getQ() != null && !request.getQ().isEmpty()) {
                boolQuery.must(QueryBuilders.multiMatchQuery(request.getQ(), "title", "description")
                    .fuzziness(org.elasticsearch.common.unit.Fuzziness.AUTO));
            }
    
            // 3. Add filters
            addTermFilter(boolQuery, "category", request.getCategory());
            addTermFilter(boolQuery, "type", request.getType());
            addRangeFilter(boolQuery, "minAge", request.getMinAge() != null ? request.getMinAge().doubleValue() : null, null);
            addRangeFilter(boolQuery, "maxAge", null, request.getMaxAge() != null ? request.getMaxAge().doubleValue() : null);
            addRangeFilter(boolQuery, "price", request.getMinPrice(), request.getMaxPrice());
            
            if (request.getStartDate() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("nextSessionDate")
                    .gte(request.getStartDate()));
            }
    
            // 4. Build the complete query
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withSorts(buildSorts(request.getSort()))
                .withPageable(PageRequest.of(request.getPage(), request.getSize()))
                .build();
    
            // 5. Execute search
            SearchHits<CourseDocument> hits = elasticsearchOperations.search(searchQuery, CourseDocument.class);
    
            // 6. Prepare response
            List<CourseDocument> courses = hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
            SearchResponse response = new SearchResponse();
            response.setCourses(courses);
            return response;
        }

    private void addTermFilter(BoolQueryBuilder boolQuery, String field, String value) {
        if (value != null && !value.isEmpty()) {
            boolQuery.filter(QueryBuilders.termQuery(field, value));
        }
    }

    private void addRangeFilter(BoolQueryBuilder boolQuery, String field, Double min, Double max) {
        if (min != null || max != null) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(field);
            if (min != null) rangeQuery.gte(min);
            if (max != null) rangeQuery.lte(max);
            boolQuery.filter(rangeQuery);
        }
    }

    private List<SortBuilder<?>> buildSorts(String sortOption) {
        List<SortBuilder<?>> sorts = new ArrayList<>();
        
        // Use modern switch expression for Java 17
        String option = sortOption != null ? sortOption : "upcoming";
        
        switch (option) {
            case "priceAsc" -> sorts.add(SortBuilders.fieldSort("price").order(SortOrder.ASC));
            case "priceDesc" -> sorts.add(SortBuilders.fieldSort("price").order(SortOrder.DESC));
            default -> sorts.add(SortBuilders.fieldSort("nextSessionDate").order(SortOrder.ASC));
        }
        
        return sorts;
    }
}