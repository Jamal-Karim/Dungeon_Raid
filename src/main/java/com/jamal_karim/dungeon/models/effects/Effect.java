package com.jamal_karim.dungeon.models.effects;

import com.jamal_karim.dungeon.models.entities.Entity;

public interface Effect {
    void applyTick(Entity target);
    int getDuration();
}
