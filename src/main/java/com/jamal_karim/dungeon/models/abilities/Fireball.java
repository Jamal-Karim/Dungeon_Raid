package com.jamal_karim.dungeon.models.abilities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;

public class Fireball implements Ability {
    private int manaTaken = 10;

    @Override
    public void execute(Entity caster, BattleContext context) {
        Entity target = context.getCurrentTarget();
        target.takeDamage(20);
        caster.reduceMana(manaTaken);
    }
}
