package com.railse.hiring.workforcemngmt.service;

import com.railse.hiring.workforcemngmt.dto.*;
import com.railse.hiring.workforcemngmt.exception.ResourceNotFoundException;
import com.railse.hiring.workforcemngmt.mapper.ITaskManagementMapper;
import com.railse.hiring.workforcemngmt.model.TaskActivity;
import com.railse.hiring.workforcemngmt.model.TaskComment;
import com.railse.hiring.workforcemngmt.model.TaskManagement;
import com.railse.hiring.workforcemngmt.model.enums.Priority;
import com.railse.hiring.workforcemngmt.model.enums.Task;
import com.railse.hiring.workforcemngmt.model.enums.TaskStatus;
import com.railse.hiring.workforcemngmt.repository.TaskActivityRepository;
import com.railse.hiring.workforcemngmt.repository.TaskCommentRepository;
import com.railse.hiring.workforcemngmt.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskManagementServiceImpl implements TaskManagementService {


    private final TaskRepository taskRepository;
    private final ITaskManagementMapper taskMapper;
    private final TaskActivityService taskActivityService;
    private final TaskCommentRepository commentRepository;
    private final TaskActivityRepository activityRepository;





    @Override
    public TaskManagementDto findTaskById(Long id) {
        TaskManagement task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return taskMapper.modelToDto(task);
    }

    @Override
    public TaskManagementDto updateTaskPriority(Long id, Priority newPriority) {
        TaskManagement task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setPriority(newPriority);
        TaskManagement newTask = taskRepository.save(task);

        return taskMapper.modelToDto(newTask);
    }

    @Override
    public List<TaskManagementDto> getTasksByPriority(Priority priority) {
        List<TaskManagement> tasks = taskRepository.findAll().stream().filter(
                task -> task.getPriority().equals(priority)
        ).collect(Collectors.toList());
        return taskMapper.modelListToDtoList(tasks);
    }

    @Override
    public TaskDetailsDto getTaskDetails(Long id) {
        TaskManagement task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        List<TaskComment> comments = commentRepository.findByTaskId(id);
        List<TaskActivity> activities = activityRepository.findByTaskId(id);

        comments.sort(Comparator.comparingLong(TaskComment::getTimestamp));

        activities.sort(Comparator.comparingLong(TaskActivity::getTimestamp));

        TaskDetailsDto dto = new TaskDetailsDto();
        dto.setId(task.getId());
        dto.setReferenceId(task.getReferenceId());
        dto.setReferenceType(task.getReferenceType());
        dto.setTask(task.getTask());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setAssigneeId(task.getAssigneeId());
        dto.setTaskDeadlineTime(task.getTaskDeadlineTime());
        dto.setPriority(task.getPriority());
        dto.setComments(comments);
        dto.setActivityHistory(activities);

        return dto;    }


    @Override
    public List<TaskManagementDto> createTasks(TaskCreateRequest createRequest) {
        List<TaskManagement> createdTasks = new ArrayList<>();
        for (TaskCreateRequest.RequestItem item : createRequest.getRequests()) {
            TaskManagement newTask = new TaskManagement();
            newTask.setReferenceId(item.getReferenceId());
            newTask.setReferenceType(item.getReferenceType());
            newTask.setTask(item.getTask());
            newTask.setAssigneeId(item.getAssigneeId());
            newTask.setPriority(item.getPriority());
            newTask.setTaskDeadlineTime(item.getTaskDeadlineTime());
            newTask.setStatus(TaskStatus.ASSIGNED);
            newTask.setDescription("New task created.");
            TaskManagement savedTask = taskRepository.save(newTask);
            createdTasks.add(taskRepository.save(newTask));
            taskActivityService.log(savedTask.getId(), 1L, "User 1 created the task.");
        }

        return taskMapper.modelListToDtoList(createdTasks);
    }


    @Override
    public List<TaskManagementDto> updateTasks(UpdateTaskRequest updateRequest) {
        List<TaskManagement> updatedTasks = new ArrayList<>();
        for (UpdateTaskRequest.RequestItem item : updateRequest.getRequests()) {
            TaskManagement task = taskRepository.findById(item.getTaskId())
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + item.getTaskId()));


            if (item.getTaskStatus() != null) {
                task.setStatus(item.getTaskStatus());
            }
            if (item.getDescription() != null) {
                task.setDescription(item.getDescription());
            }
            taskActivityService.log(item.getTaskId(), 2L, "User 2 updated the task");
            updatedTasks.add(taskRepository.save(task));
        }
        return taskMapper.modelListToDtoList(updatedTasks);
    }


    @Override
    public String assignByReference(AssignByReferenceRequest request) {
        List<Task> applicableTasks = Task.getTasksByReferenceType(request.getReferenceType());
        List<TaskManagement> existingTasks = taskRepository.findByReferenceIdAndReferenceType(request.getReferenceId(), request.getReferenceType());


        for (Task taskType : applicableTasks) {
            List<TaskManagement> tasksOfType = existingTasks.stream()
                    .filter(t -> t.getTask() == taskType && t.getStatus() != TaskStatus.COMPLETED)
                    .collect(Collectors.toList());


            // BUG #1 is here. It should assign one and cancel the rest.
            // Instead, it reassigns ALL of them.
            if (!tasksOfType.isEmpty()) {
                for (TaskManagement taskToUpdate : tasksOfType) {
                    taskToUpdate.setAssigneeId(taskToUpdate.getAssigneeId());
                    //taskToUpdate.setStatus(TaskStatus.CANCELLED);
                    taskRepository.save(taskToUpdate);
                }
            } else {
                // Create a new task if none exist
                TaskManagement newTask = new TaskManagement();
                newTask.setReferenceId(request.getReferenceId());
                newTask.setReferenceType(request.getReferenceType());
                newTask.setTask(taskType);
                newTask.setAssigneeId(request.getAssigneeId());
                newTask.setStatus(TaskStatus.ASSIGNED);

                TaskManagement assignedTask = taskRepository.save(newTask);
                taskActivityService.log(assignedTask.getId(), 3L, "User 3 assigned the task");
            }
        }
        return "Tasks assigned successfully for reference " + request.getReferenceId();
    }


    @Override
    public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request) {
        List<TaskManagement> tasks = taskRepository.findByAssigneeIdIn(request.getAssigneeIds());


        // BUG #2 is here. It should filter out CANCELLED tasks but doesn't.
        List<TaskManagement> filteredTasks = tasks.stream()
             //   .filter(task -> task.getStatus()!=TaskStatus.CANCELLED)
               //  .filter(task -> (request.getStartDate()<=task.getTaskDeadlineTime() && request.getEndDate()>=task.getTaskDeadlineTime())
                //|| task.getTaskDeadlineTime()> request.getStartDate() && task.getStatus()!=TaskStatus.CANCELLED)



                .collect(Collectors.toList());


        return taskMapper.modelListToDtoList(filteredTasks);
    }
}

