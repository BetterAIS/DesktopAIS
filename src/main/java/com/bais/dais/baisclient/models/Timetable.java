package com.bais.dais.baisclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timetable {
    private int user;
    private int day;
    private String lesson;
    private int time;
    private String teacher;
    private String room;
}