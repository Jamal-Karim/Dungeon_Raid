package com.jamal_karim.dungeon.models.effects;

import com.jamal_karim.dungeon.engine.CombatLogger;
import com.jamal_karim.dungeon.models.entities.Entity;

public class PoisonEffect implements Effect{
    private final int damagePerTurn = 5;
    private int duration = 3;

    @Override
    public String getName() {
        return "Poison Effect";
    }

    @Override
    public void applyTick(Entity target) {
        int actualDamage = target.takeDamage(this.damagePerTurn);
        CombatLogger.logEffect(target, "Poison", actualDamage);
        this.duration--;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }
}
