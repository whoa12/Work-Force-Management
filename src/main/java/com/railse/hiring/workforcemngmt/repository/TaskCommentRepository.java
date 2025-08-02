package com.railse.hiring.workforcemngmt.repository;

import com.railse.hiring.workforcemngmt.model.TaskComment;

import java.util.List;

public interface TaskCommentRepository {
     TaskComment save(TaskComment comment);
    public List<TaskComment> findByTaskId(Long taskId);
}
