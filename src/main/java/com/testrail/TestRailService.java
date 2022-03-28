package com.testrail;

import com.config.factory.TestRailConfigFactory;
import com.entity.Result;
import com.entity.Results;
import com.entity.TestRun;
import com.exception.TestRailException;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;

public class TestRailService {

    private TestRailHelper testRailHelper;
    private int testRunId;

    public TestRailService(){
        this.testRailHelper = new TestRailHelper(TestRailConfigFactory.getTestRailClient());
    }

    public TestRailService(TestRailApiClient testRailApiClient){
        this.testRailHelper = new TestRailHelper(testRailApiClient);
    }

    @SneakyThrows
    public TestRailService createTestRun(TestRun testRun){

        this.testRunId = testRailHelper
                .addTestRun(testRun)
                .getBody()
                .getObject()
                .getInt("id");

        return this;
    }
    public TestRailService updateTestRun(int testRunId, TestRun testRun){
        testRailHelper.updateTestRun(testRunId, testRun);
        this.testRunId = testRunId;

        return this;
    }

    public TestRailService updateTestRun(String testRunName, TestRun testRun){
        this.testRunId = testRailHelper
                .updateTestRun(testRunName, testRun)
                .getBody()
                .getObject()
                .getInt("id");
        return this;
    }

    public TestRailService addResultsToCases(List<Result> resultList){
        if(this.testRunId == 0) throw new TestRailException("Please create or update the existing test run" +
                "before updating the test case results");
        Results results = Results.builder()
                .result(resultList)
                .build();

        testRailHelper.addResultsToCase(this.testRunId, results);
        return this;
    }

    public void addAttachmentToTestRun(File htmlFileToAttach){
        if(this.testRunId == 0) throw new TestRailException("Please create or update the existing test run" +
                "before adding the attachment to the test run");
        testRailHelper.addAttachmentToRun(testRunId,htmlFileToAttach);
    }

}
