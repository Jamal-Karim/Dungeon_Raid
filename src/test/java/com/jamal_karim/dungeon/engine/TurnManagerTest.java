package com.jamal_karim.dungeon.engine;

import com.jamal_karim.dungeon.engine.ui.GameUI;
import com.jamal_karim.dungeon.models.effects.StunEffect;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Warrior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class TurnManagerTest {

    private BattleContext context;
    private TurnManager turnManager;
    private GameUI testUI;

    @BeforeEach
    void setUp() {
        context = new BattleContext();
        // Create a dummy UI that does nothing, to prevent console output and blocking
        testUI = new GameUI() {
            @Override public void waitForPlayerInput() {}
            @Override public void logStartOfTurn(Entity entity) {}
            @Override public void logBattleStatus(List<Entity> actives, List<Entity> fallen) {}
            @Override public void logWinner(String teamName) {}
        };
        turnManager = new TurnManager(context, testUI);
    }

    @Test
    void turnOrderIsBasedOnSpeed() {
        // Arrange
        Warrior slowWarrior = new Warrior("Slow", "TeamA"); // Speed 50
        Warrior fastWarrior = new Warrior("Fast", "TeamB"); // Speed 50
        fastWarrior.setSpeed(100);

        List<Entity> entities = new ArrayList<>(List.of(slowWarrior, fastWarrior));
        
        // Act
        turnManager.setOrderOfCharacters(entities);
        Queue<Entity> order = turnManager.getOrderOfCharacters();

        // Assert
        assertNotNull(order);
        assertEquals(fastWarrior, order.poll(), "The faster entity should be first in the queue.");
        assertEquals(slowWarrior, order.poll(), "The slower entity should be second.");
    }

    @Test
    void turnOrderIsPredictableWithEqualSpeed() {
        // Arrange
        Warrior warrior1 = new Warrior("First", "TeamA"); // Speed 50
        Warrior warrior2 = new Warrior("Second", "TeamB"); // Speed 50
        List<Entity> entities = new ArrayList<>(List.of(warrior1, warrior2));

        // Act
        turnManager.setOrderOfCharacters(entities);
        Queue<Entity> order = turnManager.getOrderOfCharacters();

        // Assert
        // The default sort is stable, so the order of insertion should be preserved for equal elements.
        assertNotNull(order);
        assertEquals(warrior1, order.poll(), "First entity added should be first in queue with equal speed.");
        assertEquals(warrior2, order.poll(), "Second entity added should be second.");
    }
    
    @Test
    void deadEntitiesAreCleanedUp() {
        // Arrange
        Warrior warrior = new Warrior("ToDie", "TeamA");
        context.addToTeam(warrior);
        
        // Act
        warrior.takeDamage(warrior.getMaxHp()); // Kill the warrior
        assertFalse(warrior.isAlive());
        
        turnManager.cleanup();

        // Assert
        assertTrue(context.getAllEntities().isEmpty(), "Dead entities should be removed from the active list.");
        assertEquals(1, context.getGraveyard().size(), "Dead entities should be added to the graveyard.");
        assertEquals(warrior, context.getGraveyard().get(0));
    }
}
