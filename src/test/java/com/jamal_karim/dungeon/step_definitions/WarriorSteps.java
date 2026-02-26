package com.jamal_karim.dungeon.step_definitions;

import com.jamal_karim.dungeon.models.entities.Warrior;
import com.jamal_karim.dungeon.utils.TestContext;
import io.cucumber.java.en.Given;

public class WarriorSteps {

    private final TestContext testContext;


    public WarriorSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("^a warrior named (\\{\\w+\\}) exists with ([1-9]{1}[0-9]{1,2}) HP$")
    public void createWarrior(String name, int hp){
        Warrior warrior = new Warrior(name, "Test_Team", hp);
        testContext.getTestVariables().store(name, warrior);
    }
}
