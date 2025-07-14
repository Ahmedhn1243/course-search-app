package com.ahmedhussain.demo.DTO;

import com.ahmedhussain.demo.documents.CourseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private long total;
    private List<CourseDocument> courses;
}