package com.jamal_karim.dungeon.models.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WarriorTest {

    @Test
    void warriorConstructorSetsCorrectStats() {
        Warrior warrior = new Warrior("TestWarrior", "TeamA");

        assertEquals(200, warrior.getMaxHp(), "Warrior should have 200 Max HP.");
        assertEquals(200, warrior.getHp(), "Warrior should have 200 current HP.");
        assertEquals(50, warrior.getMaxMana(), "Warrior should have 50 Max Mana.");
        assertEquals(50, warrior.getMana(), "Warrior should have 50 current Mana.");
        assertEquals(25, warrior.getDamage(), "Warrior should have 25 damage.");
        assertEquals(50, warrior.getSpeed(), "Warrior should have 50 speed.");
    }

    @Test
    void warriorStandardAttackDealsCorrectDamage() {
        Warrior attacker = new Warrior("Attacker", "TeamA");
        Warrior target = new Warrior("Target", "TeamB");
        int initialTargetHp = target.getHp();
        int attackDamage = attacker.getDamage();

        attacker.attack(target, attackDamage);

        assertEquals(initialTargetHp - attackDamage, target.getHp(), "Target's HP should be reduced by the attacker's damage.");
    }

    @Test
    void warriorExecutesSuperAttackWhenConditionsAreMet() {
        Warrior attacker = new Warrior("Attacker", "TeamA");
        Warrior target = new Warrior("Target", "TeamB");
        int initialTargetHp = target.getHp();

        // Set attacker's HP to be low enough for super attack
        attacker.setHp(9);

        attacker.executeSuperAttack(target);

        int expectedDamage = attacker.getDamage() + 15;
        assertEquals(initialTargetHp - expectedDamage, target.getHp(), "Super attack should deal base damage + 15 to a non-Tank.");
    }

    @Test
    void warriorExecutesSuperAttackDealsBonusDamageToTank() {
        Warrior attacker = new Warrior("Attacker", "TeamA");
        Tank target = new Tank("TargetTank", "TeamB");
        int initialTargetHp = target.getHp();

        // Set attacker's HP to be low enough for super attack
        attacker.setHp(9);

        attacker.executeSuperAttack(target);

        int expectedDamage = 50;
        assertEquals(initialTargetHp - expectedDamage, target.getHp(), "Super attack should deal 50 damage to a Tank.");
    }

    @Test
    void warriorCannotExecuteSuperAttackWhenHpIsHigh() {
        Warrior attacker = new Warrior("Attacker", "TeamA");
        Warrior target = new Warrior("Target", "TeamB");
        int initialTargetHp = target.getHp();

        // HP is high, so super attack condition is not met
        attacker.setHp(100);

        attacker.executeSuperAttack(target);

        assertEquals(initialTargetHp, target.getHp(), "Super attack should not execute and deal no damage when HP is high.");
    }
}
