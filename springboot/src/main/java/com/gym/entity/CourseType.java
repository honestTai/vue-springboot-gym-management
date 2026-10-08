package com.gym.entity;

import lombok.Data;

@Data
public class CourseType {

    private String label;
    private String value;

    public CourseType(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
