package com.testrail;

import com.encode.EncodeUtils;
import com.entity.Results;
import com.entity.TestRun;
import com.exception.TestRailException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.ContentType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import lombok.SneakyThrows;
import okhttp3.*;

import java.io.File;

import static kong.unirest.Unirest.post;

/**
 * @author amuthansakthivel
 * @version 1.0
 */
public class TestRailHelper {

    private TestRailApiClient testRailApiClient;

    /**
     * @param testRailApiClient
     */
    public TestRailHelper(TestRailApiClient testRailApiClient){
        this.testRailApiClient = testRailApiClient;
        setUnirestConfig();
    }

    private void setUnirestConfig(){
        Unirest.config()
                .defaultBaseUrl(testRailApiClient.getBaseURL() + "/index.php?/api/v2/")
                .setDefaultBasicAuth(testRailApiClient.getUserName(), testRailApiClient.getPassword());
    }

    HttpResponse<JsonNode>  addTestRun(TestRun testRun) {
        HttpResponse<JsonNode> response;
        try {
            response = Unirest
                    .post("add_run/" + testRailApiClient.getProjectID())
                    .contentType(ContentType.APPLICATION_JSON.toString())
                    .body(new ObjectMapper().writeValueAsString(testRun))
                    .asJson();
        }
        catch (Exception exception){
            throw new TestRailException("Error while creating Test Run", exception);
        }
        return response;
    }

    HttpResponse<JsonNode> updateTestRun(int testRunId, TestRun testRun) {
        HttpResponse<JsonNode> response;
        try {
            response = post("update_run/" + testRunId)
                    .contentType(ContentType.APPLICATION_JSON.toString())
                    .body(new ObjectMapper().writeValueAsString(testRun))
                    .asJson();
        }
        catch (Exception exception){
            throw new TestRailException("Error while updating Test Run", exception);
        }
        return response;
    }

    HttpResponse<JsonNode>  updateTestRun(String testRunName, TestRun testRun) {
        int idForTestRunName = getTestRunIdForTestRunName(testRunName, testRailApiClient.getProjectID());
        if(idForTestRunName != -1) return updateTestRun(idForTestRunName, testRun);
        else throw new TestRailException("No TestRunName found in the project. Please verify again");
    }

    HttpResponse<JsonNode> addResultsToCase(int testRunID, Results results) {
        HttpResponse<JsonNode> response;
        try {
            response = post("/add_results_for_cases/" + testRunID)
                    .contentType(ContentType.APPLICATION_JSON.toString())
                    .body(new ObjectMapper().writeValueAsString(results))
                    .asJson();
        }
        catch (Exception exception){
            throw new TestRailException("Error while updating test case results in the test run", exception);
        }
        return response;
    }

    @SneakyThrows
    Response addAttachmentToRun(int runID, File fileToAttach) {

        RequestBody body =
                new okhttp3.MultipartBody.Builder()
                        .setType(okhttp3.MultipartBody.FORM)
                        .addFormDataPart(
                                "attachment",
                                "index.html",
                                RequestBody.create(fileToAttach,MediaType.parse("application/octet-stream")))
                        .build();
        Request request =
                new Request.Builder()
                        .url(testRailApiClient.getBaseURL() + "/index.php?/api/v2/add_attachment_to_run/" + runID)
                        .method("POST", body)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addHeader(
                                "Authorization",
                                "Basic "
                                        + EncodeUtils.getBase64EncodedString(
                                        testRailApiClient.getUserName(), testRailApiClient.getPassword()))
                        .build();
        return new OkHttpClient().newBuilder().build().newCall(request).execute();
    }

    private int getTestRunIdForTestRunName(String testRunName, int projectID){
        JSONArray jsonArray = Unirest.get("get_runs/" + projectID).asJson().getBody().getArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).getString("name").equalsIgnoreCase(testRunName)) {
                return jsonArray.getJSONObject(i).getInt("id");
            }
        }
        return -1;
    }
}
