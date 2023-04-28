package com.bais.dais.baisclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private int id;
    private int user;
    private String author;
    private String subject;
    private String title;
    private String description;
    private String file_path;
    private String link;
    private String created_at;
    private String updated_at;
}