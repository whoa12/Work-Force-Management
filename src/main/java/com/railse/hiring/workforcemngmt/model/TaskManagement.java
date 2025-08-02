package com.railse.hiring.workforcemngmt.model;

import com.railse.hiring.workforcemngmt.model.enums.Priority;
import com.railse.hiring.workforcemngmt.model.enums.ReferenceType;
import com.railse.hiring.workforcemngmt.model.enums.Task;
import com.railse.hiring.workforcemngmt.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskManagement {
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private Task task;
    private String description;
    private TaskStatus status;
    private Long assigneeId; // Simplified from Entity for this assignment
    private Long taskDeadlineTime;
    private Priority priority;


}

