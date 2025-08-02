package com.railse.hiring.workforcemngmt.repository;

import com.railse.hiring.workforcemngmt.model.TaskActivity;

import java.util.List;

public interface TaskActivityRepository {
    TaskActivity save(TaskActivity activity);
    public List<TaskActivity> findByTaskId(Long taskId);
}
