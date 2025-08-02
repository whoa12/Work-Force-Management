package com.railse.hiring.workforcemngmt.dto;

import com.railse.hiring.workforcemngmt.model.TaskActivity;
import com.railse.hiring.workforcemngmt.model.TaskComment;
import com.railse.hiring.workforcemngmt.model.enums.Priority;
import com.railse.hiring.workforcemngmt.model.enums.ReferenceType;
import com.railse.hiring.workforcemngmt.model.enums.Task;
import com.railse.hiring.workforcemngmt.model.enums.TaskStatus;
import lombok.Data;

import java.util.List;

@Data
public class TaskDetailsDto {
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private Task task;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
    private Long taskDeadlineTime;
    private Priority priority;

    private List<TaskComment> comments;
    private List<TaskActivity> activityHistory;
}
