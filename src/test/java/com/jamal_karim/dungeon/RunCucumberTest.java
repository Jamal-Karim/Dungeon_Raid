package com.jamal_karim.dungeon;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

/**
 * This class is the entry point for running Cucumber tests with JUnit 5.
 * It is configured to:
 * - Use the Cucumber test engine.
 * - Find feature files in 'src/test/resources/features'.
 * - Find Step Definitions and other automation code in the specified 'glue' packages.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "com.jamal_karim.dungeon.step_definitions, com.jamal_karim.dungeon.utils"
)
public class RunCucumberTest {
}
