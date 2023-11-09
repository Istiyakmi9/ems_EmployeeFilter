package com.bot.employeeFilter.model;

import lombok.Data;

@Data
public class FilteredEmployee {
    int designationId;
    String email;
    boolean selected;
    String text;
    int value;
}
