package com.jamal_karim.dungeon.step_definitions;

import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class MageSteps {

    private final TestContext testContext;

    public MageSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("^a mage named (\\{\\w+\\}) exists with ([1-9]{1}[0-9]{0,2}) HP and ([1-9]{1}[0-9]{0,2}) mana$")
    public void createMage(String name, int hp, int mana){
        Mage mage = new Mage(name, "Test_Team", hp, mana);
        testContext.getTestVariables().store(name, mage);
    }

    @When("^the mage (\\{\\w+\\}) tries to cast (poison|fireball) on (\\{\\w+\\})$")
    public void castMageSpell(String mageName, String spell, String targetName){
        Mage mage = testContext.getTestVariables().get(mageName);
        Entity target = testContext.getTestVariables().get(targetName);

        switch (spell) {
            case "poison":
                try{
                    mage.castPoison(target, testContext.getBattleContext());
                } catch (IllegalStateException e) {
                    testContext.getTestVariables().store("last_error_message", e.getMessage());
                }
                break;
            case "fireball":
                try{
                    mage.castFireball(target, testContext.getBattleContext());
                } catch (IllegalStateException e){
                    testContext.getTestVariables().store("last_error_message", e.getMessage());
                }
                break;
        }
    }
}
