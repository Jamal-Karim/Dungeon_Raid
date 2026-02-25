package com.jamal_karim.dungeon.controllers;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Tank;
import com.jamal_karim.dungeon.models.entities.Warrior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TankCPUTest {

    private TankCPU tankCPU;
    private Tank tank;
    private BattleContext context;

    @BeforeEach
    void setUp() {
        tankCPU = new TankCPU();
        tank = new Tank("TestTank", "TeamA");
        context = new BattleContext();
        context.addToTeam(tank);
    }

    @Test
    void tankCastsShieldWhenHealthIsLow() {
        // Condition: HP < 50% and enough mana for shield
        Warrior enemy = new Warrior("TestEnemy", "TeamB");
        context.addToTeam(enemy);
        
        tank.setHp(tank.getMaxHp() / 3); // Low health
        // Ensure just enough mana for shield but not stun, to isolate the logic
        tank.reduceMana(tank.getMana() - tank.getManaForShield());

        int initialMana = tank.getMana();
        tankCPU.decideAction(tank, context);

        assertTrue(tank.getActiveEffects().stream().anyMatch(e -> e.getName().equals("Shield Effect")), "Tank should cast shield when low on health.");
        assertEquals(initialMana - tank.getManaForShield(), tank.getMana(), "Mana should be consumed for shield.");
    }

    @Test
    void tankCastsStunOnMageTarget() {
        // Condition: HP is high, target is Mage, enough mana for stun
        Mage enemy = new Mage("TestMage", "TeamB");
        context.addToTeam(enemy);
        
        tank.setHp(tank.getMaxHp()); // High health
        // Set mana to be enough for stun (75) but not enough for the high-mana stun (85)
        tank.setMana(80);

        tankCPU.decideAction(tank, context);

        assertTrue(enemy.getActiveEffects().stream().anyMatch(e -> e.getName().equals("Stun Effect")), "Tank should stun a Mage target.");
    }

    @Test
    void tankCastsStunWhenManaIsHigh() {
        // Condition: HP is high, non-mage target, but mana is very high
        Warrior enemy = new Warrior("TestEnemy", "TeamB");
        context.addToTeam(enemy);

        tank.setHp(tank.getMaxHp()); // High health
        tank.reduceMana(0); // Max mana

        tankCPU.decideAction(tank, context);

        assertTrue(enemy.getActiveEffects().stream().anyMatch(e -> e.getName().equals("Stun Effect")), "Tank should stun any target when mana is very high.");
    }

    @Test
    void tankUsesBasicAttackAsDefault() {
        // Condition: All other conditions fail (e.g., high health, low mana)
        Warrior enemy = new Warrior("TestEnemy", "TeamB");
        context.addToTeam(enemy);

        tank.setHp(tank.getMaxHp()); // High health
        tank.reduceMana(tank.getMana()); // No mana

        int initialEnemyHp = enemy.getHp();
        tankCPU.decideAction(tank, context);

        assertEquals(initialEnemyHp - tank.getDamage(), enemy.getHp(), "Tank should use basic attack as a default action.");
    }
}
