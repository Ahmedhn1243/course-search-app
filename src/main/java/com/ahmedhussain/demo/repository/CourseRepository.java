package com.ahmedhussain.demo.repository;

import com.ahmedhussain.demo.documents.CourseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String> {
}

