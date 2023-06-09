package com.bot.employeeFilter.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
public class FilterModel {
    @JsonProperty("SerachString")
    public String serachString;
    @JsonProperty("PageIndex")
    public int pageIndex;
    @JsonProperty("SortBy")
    public String sortBy;
    @JsonProperty("PageSize")
    public int pageSize;
}
