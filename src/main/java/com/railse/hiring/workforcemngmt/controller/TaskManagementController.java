package com.railse.hiring.workforcemngmt.controller;

import com.railse.hiring.workforcemngmt.dto.*;
import com.railse.hiring.workforcemngmt.model.TaskActivity;
import com.railse.hiring.workforcemngmt.model.TaskComment;
import com.railse.hiring.workforcemngmt.model.enums.Priority;
import com.railse.hiring.workforcemngmt.model.enums.TaskStatus;
import com.railse.hiring.workforcemngmt.model.response.Response;
import com.railse.hiring.workforcemngmt.service.TaskManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-mgmt")
public class TaskManagementController {


    private final TaskManagementService taskManagementService;


    public TaskManagementController(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
    }


    @GetMapping("/{id}")
    public Response<TaskManagementDto> getTaskById(@PathVariable Long id) {
        return new Response<>(taskManagementService.findTaskById(id));
    }


    @PostMapping("/create")
    public Response<List<TaskManagementDto>> createTasks(@RequestBody TaskCreateRequest request) {
        return new Response<>(taskManagementService.createTasks(request));
    }


    @PostMapping("/update")
    public Response<List<TaskManagementDto>> updateTasks(@RequestBody UpdateTaskRequest request) {
        return new Response<>(taskManagementService.updateTasks(request));
    }


    @PostMapping("/assign-by-ref")
    public Response<String> assignByReference(@RequestBody AssignByReferenceRequest request) {
        return new Response<>(taskManagementService.assignByReference(request));
    }


    @PostMapping("/fetch-by-date/v2")
    public Response<List<TaskManagementDto>> fetchByDate(@RequestBody TaskFetchByDateRequest request) {
        return new Response<>(taskManagementService.fetchTasksByDate(request));

    }

    @PutMapping("/update-priority/{taskId}")
    public Response<TaskManagementDto> updateTaskPriority(@PathVariable Long taskId, @RequestBody PriorityRequest req) {
        return new Response<>(taskManagementService.updateTaskPriority(taskId, req.getNewPriority()));

    }
    @GetMapping("/tasks/priority/{priority}")
    public Response<List<TaskManagementDto>> getTasksByPriority(@PathVariable Priority priority) {
        return new Response<>(taskManagementService.getTasksByPriority(priority));

    }

    @GetMapping("/{id}/get-details")
    public Response<TaskDetailsDto> getTaskWithDetails(@PathVariable Long id) {
        return new Response<>(taskManagementService.getTaskDetails(id));
    }





}
