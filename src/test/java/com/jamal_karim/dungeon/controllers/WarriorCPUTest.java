package com.jamal_karim.dungeon.controllers;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Tank;
import com.jamal_karim.dungeon.models.entities.Warrior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WarriorCPUTest {

    private WarriorCPU warriorCPU;
    private Warrior warrior;
    private BattleContext context;

    @BeforeEach
    void setUp() {
        warriorCPU = new WarriorCPU();
        warrior = new Warrior("TestWarrior", "TeamA");
        context = new BattleContext();
        context.addToTeam(warrior);
    }

    @Test
    void warriorUsesSuperAttackWhenConditionsMet() {
        // Condition: HP < 10 and hasSuperAttack flag is true
        Mage enemy = new Mage("TestEnemy", "TeamB");
        context.addToTeam(enemy);
        
        warrior.setHp(9);
        warrior.takeDamage(0); // This is necessary to trigger the flag
        assertTrue(warrior.hasSuperAttack(), "Pre-condition: Warrior must have super attack ready.");

        int initialEnemyHp = enemy.getHp();
        warriorCPU.decideAction(warrior, context);
        
        int expectedDamage = warrior.getDamage() + 15;
        assertEquals(initialEnemyHp - expectedDamage, enemy.getHp(), "Warrior should use super attack.");
        assertFalse(warrior.hasSuperAttack(), "Warrior should lose super attack status after using it.");
    }

    @Test
    void warriorUsesBonusDamageAttackAgainstTank() {
        // Condition: Target is a Tank, and super attack condition is not met
        Tank enemy = new Tank("TestTank", "TeamB");
        context.addToTeam(enemy);
        warrior.setHp(100); // Ensure HP is high

        int initialEnemyHp = enemy.getHp();
        warriorCPU.decideAction(warrior, context);

        assertEquals(initialEnemyHp - 35, enemy.getHp(), "Warrior should deal 35 damage to a Tank.");
    }

    @Test
    void warriorUsesNormalAttackAgainstNonTank() {
        // Condition: Target is not a Tank, and super attack condition is not met
        Mage enemy = new Mage("TestEnemy", "TeamB");
        context.addToTeam(enemy);
        warrior.setHp(100); // Ensure HP is high

        int initialEnemyHp = enemy.getHp();
        warriorCPU.decideAction(warrior, context);

        assertEquals(initialEnemyHp - warrior.getDamage(), enemy.getHp(), "Warrior should deal its base damage to a non-Tank.");
    }
}
