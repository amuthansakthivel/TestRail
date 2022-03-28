package com.config;

import org.aeonbits.owner.Config;
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:${user.dir}/src/main/resources/testrail.properties",
        "file:${user.dir}/src/test/resources/testrail.properties"
})
public interface TestRailConfig extends Config {

    @Key("testrail.baseurl")
    String baseURL();

    @Key("testrail.username")
    String userName();

    @Key("testrail.password")
    String password();

    @Key("testrail.projectid")
    int projectID();

}
