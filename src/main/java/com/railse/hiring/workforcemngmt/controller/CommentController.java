package com.railse.hiring.workforcemngmt.controller;

import com.railse.hiring.workforcemngmt.dto.CommentDto;
import com.railse.hiring.workforcemngmt.dto.TaskManagementDto;
import com.railse.hiring.workforcemngmt.model.TaskComment;
import com.railse.hiring.workforcemngmt.model.response.Response;
import com.railse.hiring.workforcemngmt.service.TaskCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final TaskCommentService commentService;

    @PostMapping("/add")
     public Response<TaskComment> addComment(@RequestBody CommentDto commentDto){
        return new Response<>(commentService.addComment(commentDto));
    }

    @GetMapping("/{taskId}")
    public Response<List<TaskComment>> getComment(@PathVariable("taskId") Long taskId){
        return new Response<>(commentService.getComments(taskId));
    }
}
