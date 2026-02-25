package com.jamal_karim.dungeon.models.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {

    @Test
    void entityConstructorSetsNameCorrectly() {
        // Arrange (set up the test data)
        String expectedName = "TestEntity";

        // For an abstract class like Entity, you'll need to instantiate a concrete subclass.
        // Let's use Warrior for now, as it extends Entity.
        // We'll need to adjust this if Warrior's constructor changes.
        Warrior entity = new Warrior(expectedName, "TestTeam");

        // Act (perform the action to be tested)
        String actualName = entity.getName();

        // Assert (check if the outcome is as expected)
        assertEquals(expectedName, actualName, "The entity's name should match the name provided in the constructor.");
    }
}
