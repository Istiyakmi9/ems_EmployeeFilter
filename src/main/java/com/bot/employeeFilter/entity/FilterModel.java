package com.bot.employeeFilter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
public class FilterModel {
    public String serachString;
    public int pageIndex;
    public String sortBy;
    public int pageSize;
}
