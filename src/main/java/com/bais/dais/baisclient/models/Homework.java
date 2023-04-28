package com.bais.dais.baisclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Homework {
    private int id;
    private int user;
    private String title;
    private String description;
    private String link;
    private String created_at;
    private String updated_at;
}