package com.jamal_karim.dungeon.models.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TankTest {

    private Tank tank;
    private Warrior target;

    @BeforeEach
    void setUp() {
        tank = new Tank("TestTank", "TeamA");
        target = new Warrior("TestTarget", "TeamB");
    }

    @Test
    void tankConstructorSetsCorrectStats() {
        assertEquals(250, tank.getMaxHp());
        assertEquals(100, tank.getMaxMana());
        assertEquals(20, tank.getDamage());
        assertEquals(10, tank.getSpeed());
    }

    @Test
    void tankTakesReducedDamageWithShieldEffect() {
        int initialHp = tank.getHp();
        tank.castShield(); // Assumes enough mana initially
        int damage = 50;

        tank.takeDamage(damage);

        assertEquals(initialHp - (damage / 2), tank.getHp(), "Tank should take 50% reduced damage with shield effect.");
    }

    @Test
    void tankTakesFullDamageWithoutShieldEffect() {
        int initialHp = tank.getHp();
        int damage = 50;

        tank.takeDamage(damage);

        assertEquals(initialHp - damage, tank.getHp(), "Tank should take full damage without shield effect.");
    }

    @Test
    void tankCastsShieldAndReducesMana() {
        int initialMana = tank.getMana();
        
        tank.castShield();

        assertEquals(initialMana - tank.getManaForShield(), tank.getMana(), "Tank's mana should be reduced after casting shield.");
        boolean hasShield = tank.getActiveEffects().stream()
                .anyMatch(effect -> effect.getName().equals("Shield Effect"));
        assertTrue(hasShield, "Tank should have Shield Effect after casting it.");
    }

    @Test
    void tankCannotCastShieldWithInsufficientMana() {
        tank.reduceMana(tank.getMana()); // Set mana to 0
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            tank.castShield();
        });

        assertEquals("Cannot execute shield cast: Conditions not met (mana must be > 10).", exception.getMessage());
    }

    @Test
    void tankCastsStunAndAppliesEffect() {
        int initialTargetEffects = target.getActiveEffects().size();
        int initialMana = tank.getMana();
        // The default stun cost is 75% of max mana, so the tank can afford it initially.

        tank.castStun(target);

        assertEquals(initialMana - tank.getManaForStun(), tank.getMana(), "Tank's mana should be reduced after casting stun.");
        assertTrue(target.getActiveEffects().size() > initialTargetEffects, "Target should have a new effect.");
        boolean hasStun = target.getActiveEffects().stream()
                .anyMatch(effect -> effect.getName().equals("Stun Effect"));
        assertTrue(hasStun, "Target should have Stun Effect.");
    }

    @Test
    void tankCannotCastStunWithInsufficientMana() {
        tank.reduceMana(tank.getMana() - 1); // Make mana insufficient
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            tank.castStun(target);
        });

        assertEquals(String.format("Cannot execute stun cast: Conditions not met (mana must be > %d).", tank.getManaForStun()), exception.getMessage());
    }
}
