package com.jamal_karim.dungeon.step_definitions;

import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Warrior;
import com.jamal_karim.dungeon.utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class WarriorSteps {

    private final TestContext testContext;

    public WarriorSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("^a warrior named (\\{\\w+\\}) exists with ([1-9]{1}[0-9]{0,2}) HP$")
    public void createWarrior(String name, int hp){
        Warrior warrior = new Warrior(name, "Test_Team", hp);
        testContext.getTestVariables().store(name, warrior);
    }

    @When("^the warrior (\\{\\w+\\}) tries to execute a super attack on (\\{\\w+\\})$")
    public void executeSuperAttack(String warriorName, String targetName){
        Warrior warrior = testContext.getTestVariables().get(warriorName);
        Entity target = testContext.getTestVariables().get(targetName);
        try{
            warrior.executeSuperAttack(target);
        } catch (IllegalStateException e) {
            testContext.getTestVariables().store("last_error_message", e.getMessage());
        }
    }
}
