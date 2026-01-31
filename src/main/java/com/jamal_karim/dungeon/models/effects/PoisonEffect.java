package com.jamal_karim.dungeon.models.effects;

import com.jamal_karim.dungeon.models.entities.Entity;

public class PoisonEffect implements Effect{
    private int damagePerTurn = 5;
    private int duration = 3;

    @Override
    public void applyTick(Entity target) {
        target.takeDamage(this.damagePerTurn);
        this.duration--;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }
}
