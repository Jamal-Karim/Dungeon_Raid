package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.models.abilities.Ability;
import com.jamal_karim.dungeon.models.effects.Effect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    private Warrior entity;

    @BeforeEach
    void setUp() {
        entity = new Warrior("TestEntity", "TestTeam");
    }

    @Test
    void entityConstructorSetsNameCorrectly() {
        assertEquals("TestEntity", entity.getName(), "The entity's name should match the name provided in the constructor.");
    }

    @Test
    void isAliveShouldReturnTrueWhenHpIsPositive() {
        assertTrue(entity.isAlive());
    }

    @Test
    void isAliveShouldReturnFalseWhenHpIsZero() {
        entity.setHp(0);
        assertFalse(entity.isAlive());
    }

    @Test
    void isAliveShouldReturnFalseWhenHpIsNegative() {
        entity.setHp(-10);
        assertFalse(entity.isAlive());
    }

    @Test
    void reduceManaShouldDecreaseMana() {
        int initialMana = entity.getMana();
        int manaToReduce = 10;
        entity.reduceMana(manaToReduce);
        assertEquals(initialMana - manaToReduce, entity.getMana());
    }

    @Test
    void canAffordAbilityShouldReturnTrueWhenManaIsSufficient() {
        Ability testAbility = new Ability() {
            @Override
            public void execute(Entity caster, Entity target, com.jamal_karim.dungeon.engine.BattleContext context) {}
            @Override
            public int getManaTaken(Ability ability) {
                return 10;
            }
        };
        entity.setMana(20);
        assertTrue(entity.canAffordAbility(entity, testAbility));
    }

    @Test
    void canAffordAbilityShouldReturnFalseWhenManaIsInsufficient() {
        Ability testAbility = new Ability() {
            @Override
            public void execute(Entity caster, Entity target, com.jamal_karim.dungeon.engine.BattleContext context) {}
            @Override
            public int getManaTaken(Ability ability) {
                return 30;
            }
        };
        entity.setMana(20);
        assertFalse(entity.canAffordAbility(entity, testAbility));
    }

    @Test
    void addEffectShouldAddNewEffect() {
        Effect testEffect = new TestEffect();
        entity.addEffect(testEffect);
        assertEquals(1, entity.getActiveEffects().size());
        assertEquals("Test Effect", entity.getActiveEffects().get(0).getName());
    }

    @Test
    void addEffectShouldReplaceExistingEffectOfSameType() {
        Effect effect1 = new TestEffect();
        Effect effect2 = new TestEffect();
        entity.addEffect(effect1);
        entity.addEffect(effect2);
        assertEquals(1, entity.getActiveEffects().size());
    }

    @Test
    void processEffectsShouldApplyTickAndRemoveExpiredEffects() {
        TestEffect testEffect = new TestEffect(); // duration = 2
        entity.addEffect(testEffect);
        assertEquals(1, entity.getActiveEffects().size());

        // 1st call to processEffects
        entity.processEffects(entity.getActiveEffects());
        assertEquals(1, testEffect.getApplyTickCount());
        assertEquals(1, entity.getActiveEffects().size());
        assertEquals(1, testEffect.getDuration());

        // 2nd call to processEffects
        entity.processEffects(entity.getActiveEffects());
        assertEquals(2, testEffect.getApplyTickCount());
        assertEquals(0, entity.getActiveEffects().size());
    }
    
    // Helper class for testing effects
    private static class TestEffect implements Effect {
        private int duration = 2;
        private int applyTickCount = 0;

        @Override
        public String getName() {
            return "Test Effect";
        }

        @Override
        public void applyTick(Entity caster) {
            duration--;
            applyTickCount++;
        }

        @Override
        public int getDuration() {
            return duration;
        }

        public int getApplyTickCount() {
            return applyTickCount;
        }
    }
}
