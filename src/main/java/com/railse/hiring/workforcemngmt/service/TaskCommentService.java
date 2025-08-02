package com.railse.hiring.workforcemngmt.service;

import com.railse.hiring.workforcemngmt.dto.CommentDto;
import com.railse.hiring.workforcemngmt.model.TaskComment;
import com.railse.hiring.workforcemngmt.repository.InMemoryTaskCommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskCommentService implements ICommentService{
        private final InMemoryTaskCommentsRepository repository;
        private final TaskActivityService taskActivityService;


        public TaskComment addComment(CommentDto commentDto) {
            TaskComment comment = new TaskComment();
            comment.setComment(commentDto.getComment());
            comment.setTaskId(commentDto.getTaskId());
            comment.setUserId(commentDto.getUserId());
            comment.setTimestamp(System.currentTimeMillis());
            taskActivityService.log(comment.getTaskId(), comment.getUserId(), "Comment posted by user with User ID:" + comment.getUserId());
            return repository.save(comment);
        }


        public List<TaskComment> getComments(Long taskId) {
            return repository.findByTaskId(taskId);
        }
}
