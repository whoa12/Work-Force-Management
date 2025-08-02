package com.railse.hiring.workforcemngmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.railse.hiring.workforcemngmt.model.enums.Priority;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class PriorityRequest {
    private Priority newPriority;
}
