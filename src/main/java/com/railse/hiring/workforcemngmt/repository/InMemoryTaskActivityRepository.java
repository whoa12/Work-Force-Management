package com.railse.hiring.workforcemngmt.repository;

import com.railse.hiring.workforcemngmt.model.TaskActivity;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaskActivityRepository implements TaskActivityRepository{

    private final Map<Long, TaskActivity> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public TaskActivity save(TaskActivity activity) {
        activity.setId(idGen.getAndIncrement());
        activity.setTimestamp(System.currentTimeMillis());
        store.put(activity.getId(), activity);
        return activity;
    }

    public List<TaskActivity> findByTaskId(Long taskId) {
        return store.values().stream()
                .filter(a -> a.getTaskId().equals(taskId))
                .sorted(Comparator.comparingLong(TaskActivity :: getTimestamp))
                .collect(Collectors.toList());
    }
}

