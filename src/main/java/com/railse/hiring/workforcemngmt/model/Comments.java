package com.railse.hiring.workforcemngmt.model;

import lombok.Data;

@Data
public class Comments {
    private Long id;
    private Long taskId;
    private Long userId;
    private String comment;
    private Long timestamp;
}
