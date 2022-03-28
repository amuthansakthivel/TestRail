package com.testrail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "set")
public class TestRailApiClient {

    private String baseURL;
    private String userName;
    private String password;
    private int projectID;

}
