package com.railse.hiring.workforcemngmt.controller;

import com.railse.hiring.workforcemngmt.dto.CommentDto;
import com.railse.hiring.workforcemngmt.model.Comments;
import com.railse.hiring.workforcemngmt.model.TaskActivity;
import com.railse.hiring.workforcemngmt.model.response.Response;
import com.railse.hiring.workforcemngmt.service.TaskActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-activities")
public class ActivityController {
    private final TaskActivityService activityService;

    public ActivityController(TaskActivityService activityService) {
        this.activityService = activityService;
    }


    @GetMapping("/{taskId}")
    public List<TaskActivity> getActivitiesByTaskId(@PathVariable Long taskId) {
        return activityService.getActivities(taskId);
    }
}
