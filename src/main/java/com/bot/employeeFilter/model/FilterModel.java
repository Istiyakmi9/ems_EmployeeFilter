package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
public class FilterModel {
    @JsonProperty("SearchString")
    public String searchString;
    @JsonProperty("PageIndex")
    public int pageIndex;
    @JsonProperty("SortBy")
    public String sortBy;
    @JsonProperty("PageSize")
    public int pageSize;
    @JsonProperty("CompanyId")
    public int companyId;
}
