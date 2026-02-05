package com.jamal_karim.dungeon.models.effects;

import com.jamal_karim.dungeon.engine.CombatLogger;
import com.jamal_karim.dungeon.models.entities.Entity;

public class StunEffect implements Effect{
    private int duration = 1;


    @Override
    public String getName() {
        return "Stun Effect";
    }

    @Override
    public void applyTick(Entity target) {
        CombatLogger.logAction(target, "is stunned for this round");
        duration--;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }
}
