package com.jamal_karim.dungeon.models.effects;

import com.jamal_karim.dungeon.models.entities.Entity;

public interface Effect {
    String getName();
    void applyTick(Entity target);
    int getDuration();
}
