package com.railse.hiring.workforcemngmt.service;

import com.railse.hiring.workforcemngmt.dto.CommentDto;
import com.railse.hiring.workforcemngmt.model.TaskComment;

import java.util.List;

public interface ICommentService {
    TaskComment addComment(CommentDto commentDto);
    List<TaskComment> getComments(Long taskId);
}
