package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * @author amuthansakthivel
 * @version 1.0
 */
@Jacksonized
@ToString
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
public class Result {

  @JsonProperty("case_id")
  private int caseID;

  @JsonProperty("status_id")
  private int statusId;

  @JsonProperty("comment")
  private String comment;

  @JsonProperty("assignedto_id")
  private int assignedToID;

  @JsonIgnore
  private boolean isTestPassed;

  Result(int caseID, int statusId, String comment, int assignedToID, boolean isTestPassed) {
    this.caseID = caseID;
    this.statusId = statusId;
    this.comment = comment;
    this.assignedToID = assignedToID;
    this.isTestPassed = isTestPassed;
  }

  public static ResultBuilder builder() {
    return new ResultBuilder();
  }

  public static class ResultBuilder {
    private int caseID;
    private int statusId;
    private String comment;
    private int assignedToID;
    private boolean isTestPassed;

    ResultBuilder() {
    }

    public ResultBuilder setCaseID(int caseID) {
      this.caseID = caseID;
      return this;
    }

    public ResultBuilder setStatusId(int statusId) {
      this.statusId = statusId;
      return this;
    }

    public ResultBuilder setComment(String comment) {
      this.comment = comment;
      return this;
    }

    public ResultBuilder setAssignedToID(int assignedToID) {
      this.assignedToID = assignedToID;
      return this;
    }

    public ResultBuilder setIsTestPassed(boolean isTestPassed) {
      this.isTestPassed = isTestPassed;
      if(isTestPassed) this.setStatusId(1);
      else this.setStatusId(5);
      return this;
    }

    public Result build() {
      return new Result(caseID, statusId, comment, assignedToID, isTestPassed);
    }

    public String toString() {
      return "Result.ResultBuilder(caseID=" + this.caseID + ", statusId=" + this.statusId + ", comment=" + this.comment + ", assignedToID=" + this.assignedToID + ", isTestPassed=" + this.isTestPassed + ")";
    }
  }
}
