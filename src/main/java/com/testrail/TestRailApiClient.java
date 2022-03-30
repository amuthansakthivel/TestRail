package com.testrail;

import lombok.Builder;
import lombok.Getter;

/**
 * @author amuthansakthivel
 * @version 1.0
 */

@Getter
@Builder(setterPrefix = "set")
public class TestRailApiClient {

    private String baseURL;
    private String userName;
    private String password;
    private int projectID;

}
