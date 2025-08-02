package com.railse.hiring.workforcemngmt.service;



import com.railse.hiring.workforcemngmt.dto.*;
import com.railse.hiring.workforcemngmt.model.enums.Priority;


import java.util.List;

public interface TaskManagementService {
    List<TaskManagementDto> createTasks(TaskCreateRequest request);
    List<TaskManagementDto> updateTasks(UpdateTaskRequest request);
    String assignByReference(AssignByReferenceRequest request);
    List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request);
    TaskManagementDto findTaskById(Long id);

    TaskManagementDto updateTaskPriority(Long id, Priority request);

    List<TaskManagementDto> getTasksByPriority(Priority priority);

    TaskDetailsDto getTaskDetails(Long id);
}
