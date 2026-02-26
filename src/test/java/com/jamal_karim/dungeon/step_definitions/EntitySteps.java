package com.jamal_karim.dungeon.step_definitions;

import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.utils.TestContext;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class EntitySteps {

    private final TestContext testContext;

    public EntitySteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("^an entity (\\{\\w+\\}) attacks (\\{\\w+\\})$")
    public void warriorAttacksEntity(String attackerName, String targetName){
        Entity attacker = testContext.getTestVariables().get(attackerName);
        Entity target = testContext.getTestVariables().get(targetName);
        attacker.attack(target, attacker.getDamage());
    }

    @When("^an entity (\\{\\w+\\}) mana is lowered to ([1-9]{1}[0-9]{0,2})$")
    public void reduceEntityMana(String entityName, int reducedMana){
        Entity entity = testContext.getTestVariables().get(entityName);
        entity.setMana(reducedMana);
    }

    @Then("^the entity (\\{\\w+\\}) should have ([1-9]{1}[0-9]{0,2}) HP$")
    public void verifyHP(String entityName, int expectedHp){
        Entity entity = testContext.getTestVariables().get(entityName);
        Assertions.assertEquals(expectedHp, entity.getHp(), entityName + " did not have the expected hp of " + expectedHp + " after attack");
    }

    @Then("^the entity (\\{\\w+\\}) should have ([0-9]{1}) active effects$")
    public void verifyActiveEffects(String entityName, int numOfEffects){
        Entity entity = testContext.getTestVariables().get(entityName);
        int actualActiveEffects = entity.getActiveEffects().size();

        Assertions.assertEquals(numOfEffects, actualActiveEffects,"Entity did not have the expected number of active effects");
    }

    @Then("^the error message thrown is \"(.*)\"$")
    public void verifyErrorMessage(String errorMessage) {
        String actualMessage = testContext.getTestVariables().get("last_error_message");
        Assertions.assertEquals(errorMessage, actualMessage);
    }
}
