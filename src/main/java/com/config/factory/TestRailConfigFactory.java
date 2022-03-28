package com.config.factory;

import com.config.TestRailConfig;
import com.testrail.TestRailApiClient;
import org.aeonbits.owner.ConfigCache;

public final class TestRailConfigFactory {

    private TestRailConfigFactory(){}

    public static TestRailConfig getConfig(){
        return ConfigCache.getOrCreate(TestRailConfig.class);
    }

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
