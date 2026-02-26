package com.jamal_karim.dungeon.step_definitions;

import com.jamal_karim.dungeon.controllers.*;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Tank;
import com.jamal_karim.dungeon.models.entities.Warrior;
import com.jamal_karim.dungeon.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class GameSteps {
    private final TestContext testContext;
    private final ActionController WarriorCPU = new WarriorCPU();
    private final ActionController MageCPU = new MageCPU();
    private final ActionController TankCPU = new TankCPU();
    private final ActionController Player = new PlayerController();


    public GameSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("^the raid exists with the following entities:$")
    public void setUpRaid(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> cols : rows) {
            String entityType = cols.get("entity").toLowerCase();
            String name = cols.get("name");
            String team = cols.get("team");
            String user = cols.get("user").toLowerCase();
            // Extract optional HP and Mana, default to null if not present
            String hpStr = cols.get("hp");
            String manaStr = cols.get("mana");

            Integer hp = (hpStr != null) ? Integer.parseInt(hpStr) : null;
            Integer mana = (manaStr != null) ? Integer.parseInt(manaStr) : null;

            Entity newEntity = createAndConfigureEntity(entityType, name, team, user, hp, mana);
            testContext.getBattleContext().addToTeam(newEntity);
            testContext.getTestVariables().store(name, newEntity);
        }
        List<Entity> allEntitiesInRaid = testContext.getBattleContext().getAllEntities();
        testContext.getTurns().setOrderOfCharacters(allEntitiesInRaid);
    }

    @When("^the game progresses one turn$")
    public void gameProgressesOneTurn() {
        testContext.getTurns().playNextTurn();
    }

    @Then("^(\\w+) should be the entity that acted$")
    public void entityShouldBeTheEntityThatActed(String expectedEntityName) {
        Entity lastActor = testContext.getTurns().getLastEntityToAct();
        assertNotNull(lastActor, "No entity acted in the last turn.");
        assertEquals(expectedEntityName, lastActor.getName(), "The wrong entity acted.");
    }

    private Entity createAndConfigureEntity(String entityType, String name, String team, String userType, Integer hp, Integer mana) {
        Entity entity;
        ActionController controller;

        switch (entityType) {
            case "warrior":
                entity = (hp != null) ? new Warrior(name, team, hp) : new Warrior(name, team);
                controller = userType.equals("player") ? Player : WarriorCPU;
                break;
            case "mage":
                if (hp != null && mana != null) {
                    entity = new Mage(name, team, hp, mana);
                } else {
                    entity = new Mage(name, team);
                    if (hp != null) {
                        entity.setHp(hp);
                    }
                    if (mana != null) {
                        entity.setMana(mana);
                    }
                }
                controller = userType.equals("player") ? Player : MageCPU;
                break;
            case "tank":
                if (hp != null && mana != null) {
                    entity = new Tank(name, team, hp, mana);
                } else {
                    entity = new Tank(name, team);
                    if (hp != null) {
                        entity.setHp(hp);
                    }
                    if (mana != null) {
                        entity.setMana(mana);
                    }
                }
                controller = userType.equals("player") ? Player : TankCPU;
                break;
            default:
                throw new IllegalArgumentException("Unknown entity type: " + entityType);
        }
        entity.setController(controller);
        return entity;
    }
}
