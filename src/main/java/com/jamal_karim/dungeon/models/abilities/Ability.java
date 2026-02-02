package com.jamal_karim.dungeon.models.abilities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;

public interface Ability {
    void execute(Entity caster, BattleContext context);

    int getManaTaken(Ability ability);
}
