package com.bais.dais.baisclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private int id;
    private int user;
    private String sender;
    private String subject;
    private String body;
    private boolean isRead;
    private String created_at;
    private String updated_at;
}