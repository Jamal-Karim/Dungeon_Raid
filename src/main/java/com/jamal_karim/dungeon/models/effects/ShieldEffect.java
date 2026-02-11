package com.jamal_karim.dungeon.models.effects;

import com.jamal_karim.dungeon.models.entities.Entity;

public class ShieldEffect implements Effect {
    private int duration = 1;

    @Override
    public String getName() {
        return "Shield Effect";
    }

    @Override
    public void applyTick(Entity caster) {
        duration--;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }
}
