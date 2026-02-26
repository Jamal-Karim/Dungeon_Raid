package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.CombatLogger;
import com.jamal_karim.dungeon.models.effects.ShieldEffect;
import com.jamal_karim.dungeon.models.effects.StunEffect;

public class Tank extends Entity {

    private final int manaForShield = 10;
    private final int manaForStun;

    public Tank(String name, String team) {
        super(name, 250, 100, 20, team, 10);
        this.manaForStun = (int) (100 * 0.75);
    }

    public Tank(String name, String team, int initialHp, int initialMana) {
        super(name, initialHp, initialMana, 20, team, 10);
        this.manaForStun = (int) (100 * 0.75);
    }

    public int getManaForShield() {
        return manaForShield;
    }

    public int getManaForStun() {
        return manaForStun;
    }

    public void castShield() {
        if (this.getMana() >= manaForShield) {
            ShieldEffect shield = new ShieldEffect();
            this.addEffect(shield);
            this.reduceMana(manaForShield);
            CombatLogger.logAction(this, "raises a heavy shield!");
        } else {
            throw new IllegalStateException("Cannot execute shield cast: Conditions not met (mana must be > 10).");
        }
    }

    public void castStun(Entity enemy) {
        if (this.getMana() >= manaForStun) {
            StunEffect stun = new StunEffect();
            enemy.addEffect(stun);
            this.reduceMana(manaForStun);
            CombatLogger.logAttack(this, enemy, "launches stun");
        } else {
            throw new IllegalStateException(String.format("Cannot execute stun cast: Conditions not met (mana must be > %d).", manaForStun));
        }
    }

    @Override
    public void attack(Entity enemy, int amount) {
        int actualDamage = enemy.takeDamage(amount);
        CombatLogger.logAttack(this, enemy, "launches an attack");
        CombatLogger.logDamage(enemy, actualDamage);
    }

    @Override
    public int takeDamage(int damage) {
        float damageMultiplier = 1.0f;
        if (this.getActiveEffects().stream().anyMatch(effect -> effect.getName().equals("Shield Effect"))) {
            damageMultiplier = 0.5f;
        }

        int damageToTake = (int) (damage * damageMultiplier);
        int actualDamage = Math.min(this.getHp(), damageToTake);
        this.setHp(this.getHp() - actualDamage);
        return actualDamage;
    }
}
