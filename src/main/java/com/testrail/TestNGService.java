package com.testrail;

import com.annotation.TestRail;
import com.config.factory.TestRailConfigFactory;
import com.entity.Result;
import com.entity.TestRun;
import org.testng.IInvokedMethod;
import org.testng.ISuite;
import org.testng.ITestResult;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestNGService {

    private TestRailApiClient testRailApiClient;
    private TestRailService testRailService;

    public TestNGService(){
        this.testRailApiClient = TestRailConfigFactory.getTestRailClient();
        this.testRailService = new TestRailService(testRailApiClient);
    }
    public TestNGService(TestRailApiClient testRailApiClient){
        this.testRailApiClient = testRailApiClient;
        this.testRailService = new TestRailService(testRailApiClient);
    }

    public TestNGService addTestRun(ISuite suite, String testRunNameToBeCreated, int testRailSuiteId){
        TestRun testRun =
                TestRun.builder()
                        .setSuiteID(testRailSuiteId)
                        .setCaseIDs(getListOfCaseIDToBeIncludedInTestRun(suite))
                        .setName(testRunNameToBeCreated)
                        .setIncludeAll(false)
                        .build();

        testRailService.createTestRun(testRun);
        return this;
    }

    public TestNGService updateTestRun(ISuite suite, String testRunNameToBeCreated, int testRailSuiteId,
    int testRunToBeUpdated){
        TestRun testRun =
                TestRun.builder()
                        .setSuiteID(testRailSuiteId)
                        .setCaseIDs(getListOfCaseIDToBeIncludedInTestRun(suite))
                        .setName(testRunNameToBeCreated)
                        .setIncludeAll(false)
                        .build();

        testRailService.updateTestRun(testRunToBeUpdated, testRun);
        return this;
    }

    public TestNGService updateTestRun(ISuite suite, String testRunNameToBeCreated, int testRailSuiteId,
                                       String testRunNameToBeUpdated){
        TestRun testRun =
                TestRun.builder()
                        .setSuiteID(testRailSuiteId)
                        .setCaseIDs(getListOfCaseIDToBeIncludedInTestRun(suite))
                        .setName(testRunNameToBeCreated)
                        .setIncludeAll(false)
                        .build();

        testRailService.updateTestRun(testRunNameToBeUpdated, testRun);
        return this;
    }

    public TestNGService addResultsToCases(ISuite suite){
        List<Result> list = suite
                .getAllInvokedMethods()
                .stream()
                .filter(IInvokedMethod::isTestMethod)
                .map(testMethod -> getResultForTest(testMethod.getTestResult()))
                .collect(Collectors.toList());

        testRailService.addResultsToCases(list);
        return this;
    }

    public void addAttachmentToTestRun(File htmlFileToAttach){
        testRailService.addAttachmentToTestRun(htmlFileToAttach);
    }

    private Result getResultForTest(ITestResult result) {
        return Result.builder()
                .setCaseID(getCaseID(result))
                .setStatusId(getStatusID(result))
                .setComment(getComment(result))
                .build();
    }

    private static String getComment(ITestResult result) {
        return result.isSuccess()
                ? "Test Passed"
                : result.getThrowable().toString()
                + "/n"
                + Arrays.toString(result.getThrowable().getStackTrace());
    }

    private static int getStatusID(ITestResult result) {
         final int TEST_RAIL_PASS = 1;
         final int TEST_RAIL_FAIL = 5;
        return result.isSuccess() ? TEST_RAIL_PASS : TEST_RAIL_FAIL;
    }


    private static int getCaseID(ITestResult result) {
        return result
                .getMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(TestRail.class)
                .caseID();
    }

    private Collection<Integer> getListOfCaseIDToBeIncludedInTestRun(ISuite suite) {
        return suite
                .getAllMethods()
                .stream()
                .map(method -> method.getConstructorOrMethod().getMethod().getAnnotation(TestRail.class))
                .map(TestRail::caseID)
                .collect(Collectors.toList());
    }
}
