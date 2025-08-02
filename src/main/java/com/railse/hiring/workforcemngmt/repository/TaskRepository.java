package com.railse.hiring.workforcemngmt.repository;




import com.railse.hiring.workforcemngmt.model.TaskManagement;
import com.railse.hiring.workforcemngmt.model.enums.ReferenceType;

import java.lang.ref.Reference;
import java.util.List;
import java.util.Optional;


public interface TaskRepository {
    Optional<TaskManagement> findById(Long id);
    TaskManagement save(TaskManagement task);
    List<TaskManagement> findAll();
    List<TaskManagement> findByReferenceIdAndReferenceType(Long referenceId, ReferenceType referenceType);
    List<TaskManagement> findByAssigneeIdIn(List<Long> assigneeIds);
}

