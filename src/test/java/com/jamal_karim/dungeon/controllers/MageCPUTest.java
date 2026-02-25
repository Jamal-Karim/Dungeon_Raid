package com.jamal_karim.dungeon.controllers;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Warrior;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MageCPUTest {

    private MageCPU mageCPU;
    private Mage mage;
    private Warrior enemy;
    private BattleContext context;

    @BeforeEach
    void setUp() {
        mageCPU = new MageCPU();
        mage = new Mage("TestMage", "TeamA");
        enemy = new Warrior("TestEnemy", "TeamB");
        context = new BattleContext();
        context.addToTeam(mage);
        context.addToTeam(enemy);
    }

    @Test
    void mageCastsPoisonOnHighHealthEnemyWithSufficientMana() {
        // Condition: Target HP > 50% and Mage Mana > 20
        enemy.setHp(enemy.getMaxHp()); // Full health
        mage.reduceMana(mage.getMana() - 30); // Ensure mana is high but not full, e.g., 170

        mageCPU.decideAction(mage, context);

        boolean hasPoison = enemy.getActiveEffects().stream()
                .anyMatch(effect -> effect.getName().equals("Poison Effect"));
        assertTrue(hasPoison, "Mage should cast Poison on a high-health enemy.");
    }

    @Test
    void mageCastsFireballOnLowHealthEnemyWithSufficientMana() {
        // Condition: Target HP < 50% and Mage Mana > 10
        enemy.setHp(enemy.getMaxHp() / 3); // Low health
        mage.reduceMana(mage.getMana() - 20); // Mana is sufficient for Fireball

        int initialEnemyHp = enemy.getHp();
        int initialMageMana = mage.getMana();

        mageCPU.decideAction(mage, context);

        assertEquals(initialEnemyHp - 20, enemy.getHp(), "Mage should cast Fireball, dealing 20 damage.");
        assertEquals(initialMageMana - 10, mage.getMana(), "Casting Fireball should consume 10 mana.");
    }
    
    @Test
    void mageCastsFireballOnHighHealthEnemyWithLowManaForPoison() {
        // Condition: Target HP > 50% but Mage Mana is > 10 and <= 20
        enemy.setHp(enemy.getMaxHp()); // Full health
        mage.reduceMana(mage.getMana() - 15); // Mana sufficient for Fireball, not Poison
        
        int initialEnemyHp = enemy.getHp();
        int initialMageMana = mage.getMana();

        mageCPU.decideAction(mage, context);

        assertEquals(initialEnemyHp - 20, enemy.getHp(), "Mage should fall back to Fireball if mana is too low for Poison.");
        assertEquals(initialMageMana - 10, mage.getMana(), "Casting Fireball should consume 10 mana.");
    }

    @Test
    void mageUsesBasicAttackWhenManaIsLow() {
        // Condition: Mage Mana < 10
        mage.reduceMana(mage.getMana() - 9); // Set mana to 9

        int initialEnemyHp = enemy.getHp();
        int initialMageMana = mage.getMana();

        mageCPU.decideAction(mage, context);

        assertEquals(initialEnemyHp - mage.getDamage(), enemy.getHp(), "Mage should use basic attack when mana is too low for spells.");
        assertEquals(initialMageMana, mage.getMana(), "Basic attack should not consume mana.");
    }
}
