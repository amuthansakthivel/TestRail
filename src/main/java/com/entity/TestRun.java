package com.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder(setterPrefix = "set")
@ToString
public class TestRun {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("suite_id")
  private int suiteID;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("name")
  private String name;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("description")
  private String description;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("milestone_id")
  private int milestoneId;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("assignedto_id")
  private int assignedToID;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("include_all")
  private boolean includeAll;

  @Singular
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("case_ids")
  private List<Integer> caseIDs;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("refs")
  private String references;
}
