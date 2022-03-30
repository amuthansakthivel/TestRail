package com.testrail;

import com.config.factory.TestRailConfigFactory;
import com.entity.Result;
import com.entity.Results;
import com.entity.TestRun;
import com.exception.TestRailException;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;

/**
 * @author amuthansakthivel
 * @version 1.0
 */
public class TestRailService {

    private TestRailHelper testRailHelper;
    private int testRunId;

    /**
     * Default constructor used to initialise helper with value from testrail.properties
     */
    public TestRailService(){
        this.testRailHelper = new TestRailHelper(TestRailConfigFactory.getTestRailClient());
    }

    /**
     * Used to initialise helper with value created and passed by user
     * @param testRailApiClient
     */
    public TestRailService(TestRailApiClient testRailApiClient){
        this.testRailHelper = new TestRailHelper(testRailApiClient);
    }

    /**
     * Create a new test run in test rail
     * @param testRun
     * @return
     */
    @SneakyThrows
    public TestRailService createTestRun(TestRun testRun){
        this.testRunId = testRailHelper
                .addTestRun(testRun)
                .getBody()
                .getObject()
                .getInt("id");

        return this;
    }

    /**
     * Update an existing test run using the existing testRunId
     * @param existingTestRunId
     * @param testRun
     * @return
     */
    public TestRailService updateTestRun(int existingTestRunId, TestRun testRun){
        testRailHelper.updateTestRun(existingTestRunId, testRun);
        this.testRunId = existingTestRunId;
        return this;
    }

    /**
     * Update an existing test run using the existing existingTestRunName
     * @param existingTestRunName
     * @param testRun
     * @return
     */
    public TestRailService updateTestRun(String existingTestRunName, TestRun testRun){
        this.testRunId = testRailHelper
                .updateTestRun(existingTestRunName, testRun)
                .getBody()
                .getObject()
                .getInt("id");
        return this;
    }

    /**
     * Adds the results to each test case in the suite
     * @param resultList
     * @return
     */
    public TestRailService addResultsToCases(List<Result> resultList){
        if(this.testRunId == 0) throw new TestRailException("Please create or update the existing test run" +
                "before updating the test case results");
        Results results = Results.builder()
                .result(resultList)
                .build();
        testRailHelper.addResultsToCase(this.testRunId, results);
        return this;
    }

    /**
     * Adds the html report to the corresponding test run
     * @param htmlFileToAttach
     */
    public void addAttachmentToTestRun(File htmlFileToAttach){
        if(this.testRunId == 0) throw new TestRailException("Please create or update the existing test run" +
                "before adding the attachment to the test run");
        testRailHelper.addAttachmentToRun(testRunId,htmlFileToAttach);
    }

}
