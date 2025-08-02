package com.railse.hiring.workforcemngmt.service;

import com.railse.hiring.workforcemngmt.dto.CommentDto;
import com.railse.hiring.workforcemngmt.model.TaskActivity;
import com.railse.hiring.workforcemngmt.model.TaskComment;
import com.railse.hiring.workforcemngmt.repository.InMemoryTaskActivityRepository;
import com.railse.hiring.workforcemngmt.repository.InMemoryTaskCommentsRepository;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;

@Service
public class TaskActivityService {
    private final InMemoryTaskActivityRepository repo;
    public TaskActivityService(InMemoryTaskActivityRepository repo) {
        this.repo = repo;
    }
    public void log(Long taskId, Long userId, String description) {
        TaskActivity activity = new TaskActivity();
        activity.setTaskId(taskId);
        activity.setUserId(userId);
        activity.setDescription(description);
        repo.save(activity);
    }
    public List<TaskActivity> getActivities(Long taskId) {
        return repo.findByTaskId(taskId);
    }



}
