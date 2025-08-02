package com.railse.hiring.workforcemngmt.repository;

import com.railse.hiring.workforcemngmt.model.TaskComment;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaskCommentsRepository implements TaskCommentRepository{
    private final Map<Long, TaskComment> commentStore = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public TaskComment save(TaskComment comment) {
        comment.setId(idGen.getAndIncrement());
        comment.setTimestamp(System.currentTimeMillis());
        commentStore.put(comment.getId(), comment);
        return comment;
    }
    public List<TaskComment> findByTaskId(Long taskId) {
        return commentStore.values().stream()
                .filter(c -> c.getTaskId().equals(taskId))
                .sorted(Comparator.comparingLong(TaskComment::getTimestamp))
                .collect(Collectors.toList());
    }
}
