package com.railse.hiring.workforcemngmt.model;

import lombok.Data;

@Data
public class TaskActivity {
    private Long id;
    private Long taskId;
    private String description;
    private Long userId;
    private Long timestamp;
}
