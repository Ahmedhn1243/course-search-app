package com.ahmedhussain.demo.service;

import com.ahmedhussain.demo.documents.CourseDocument;
import com.ahmedhussain.demo.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;

@Service
public class DataLoaderService {

    private final CourseRepository repository;

    public DataLoaderService(CourseRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadCourses() {
        try {
            InputStream is = new ClassPathResource("sample-courses.json").getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            List<CourseDocument> courses = mapper.readValue(is, new TypeReference<>() {});
            repository.saveAll(courses);
            System.out.println("Courses loaded into Elasticsearch");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

