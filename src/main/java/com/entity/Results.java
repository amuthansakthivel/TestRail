package com.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@ToString
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class Results {

  @JsonProperty("results")
  private List<Result> result;
}
