package com.config.factory;

import com.config.TestRailConfig;
import com.testrail.TestRailApiClient;
import org.aeonbits.owner.ConfigCache;

/**
 * @author amuthansakthivel
 * @version 1.0
 */
public final class TestRailConfigFactory {

    private TestRailConfigFactory(){}

    /**
     * @return TestRailConfig
     */
    private static TestRailConfig getConfig(){
        return ConfigCache.getOrCreate(TestRailConfig.class);
    }

    /**
     * @return TestRailApiClient needed to perform api calls to test rail
     */
    public static TestRailApiClient getTestRailClient(){
        return TestRailApiClient
                .builder()
                .setBaseURL(getConfig().baseURL())
                .setUserName(getConfig().userName())
                .setPassword(getConfig().password())
                .setProjectID(getConfig().projectID())
                .build();
    }
}
