package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.BattleContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MageTest {

    private Mage mage;
    private Warrior target;
    private BattleContext context;

    @BeforeEach
    void setUp() {
        mage = new Mage("TestMage", "TeamA");
        target = new Warrior("TestTarget", "TeamB");
        context = new BattleContext(); // Assuming a simple constructor
    }

    @Test
    void mageConstructorSetsCorrectStats() {
        assertEquals(120, mage.getMaxHp());
        assertEquals(200, mage.getMaxMana());
        assertEquals(10, mage.getDamage());
        assertEquals(10, mage.getSpeed());
    }

    @Test
    void mageCastsFireballAndReducesMana() {
        int initialMana = mage.getMana();
        int initialTargetHp = target.getHp();

        mage.castFireball(target, context);

        assertTrue(mage.getMana() < initialMana, "Mage's mana should be reduced after casting fireball.");
        assertEquals(initialMana - 10, mage.getMana(), "Mage's mana should be reduced by 10.");
        assertTrue(target.getHp() < initialTargetHp, "Target should take damage from fireball.");
        assertEquals(initialTargetHp - 20, target.getHp(), "Target should take 20 damage from fireball.");
    }

    @Test
    void mageCannotCastFireballWithInsufficientMana() {
        // Set mana to 9, which is less than the required 10.
        mage.reduceMana(mage.getMana() - 9);
        int initialMana = mage.getMana();
        int initialTargetHp = target.getHp();

        mage.castFireball(target, context);

        assertEquals(initialMana, mage.getMana(), "Mage's mana should not change.");
        assertEquals(initialTargetHp, target.getHp(), "Target's HP should not change.");
    }

    @Test
    void mageCastsPoisonAndAppliesEffect() {
        int initialEffectCount = target.getActiveEffects().size();
        
        mage.castPoison(target, context);

        assertTrue(target.getActiveEffects().size() > initialEffectCount, "Target should have a new effect after poison is cast.");
        boolean hasPoison = target.getActiveEffects().stream()
                .anyMatch(effect -> effect.getName().equals("Poison Effect"));
        assertTrue(hasPoison, "Target should have Poison Effect.");
    }

    @Test
    void mageAlsoAttacksWhenCastingPoison() {
        int initialTargetHp = target.getHp();
        int expectedDamage = mage.getDamage();

        mage.castPoison(target, context);

        assertEquals(initialTargetHp - expectedDamage, target.getHp(), "Target should also take direct attack damage when poison is cast.");
    }

    @Test
    void mageCannotCastPoisonWithInsufficientMana() {
        // Set mana to 19, which is less than the required 20.
        mage.reduceMana(mage.getMana() - 19);
        int initialMana = mage.getMana();
        int initialTargetHp = target.getHp();
        int initialEffectCount = target.getActiveEffects().size();

        mage.castPoison(target, context);

        assertEquals(initialMana, mage.getMana(), "Mage's mana should not change.");
        assertEquals(initialTargetHp, target.getHp(), "Target's HP should not change.");
        assertEquals(initialEffectCount, target.getActiveEffects().size(), "Target's effects should not change.");
    }
}
