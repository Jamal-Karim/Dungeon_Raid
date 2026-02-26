package com.jamal_karim.dungeon.step_definitions;

import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Tank;
import com.jamal_karim.dungeon.utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class TankSteps {

    private final TestContext testContext;


    public TankSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("^a tank named (\\{\\w+\\}) exists with ([1-9]{1}[0-9]{0,2}) HP and ([1-9]{1}[0-9]{0,2}) mana$")
    public void createTank(String name, int hp, int mana){
        Tank tank = new Tank(name, "Test_Team", hp, mana);
        testContext.getTestVariables().store(name, tank);
    }

    @When("^the tank (\\{\\w+\\}) tries to cast stun on (\\{\\w+\\})$")
    public void castStun(String tankName, String targetName){
        Tank tank = testContext.getTestVariables().get(tankName);
        Entity target = testContext.getTestVariables().get(targetName);
        try{
            tank.castStun(target);
        } catch (IllegalStateException e) {
            testContext.getTestVariables().store("last_error_message", e.getMessage());
        }
    }

    @When("^the tank (\\{\\w+\\}) tries to cast shield$")
    public void castShield(String tankName){
        Tank tank = testContext.getTestVariables().get(tankName);
        try{
            tank.castShield();
        } catch (IllegalStateException e) {
            testContext.getTestVariables().store("last_error_message", e.getMessage());
        }

    }


}
