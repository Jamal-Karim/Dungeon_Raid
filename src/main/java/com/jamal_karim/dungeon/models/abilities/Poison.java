package com.jamal_karim.dungeon.models.abilities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.effects.PoisonEffect;
import com.jamal_karim.dungeon.models.entities.Entity;

public class Poison implements Ability{
    private int manaTaken = 20;

    @Override
    public void execute(Entity caster, BattleContext context) {
        PoisonEffect poisonEffect = new PoisonEffect();

        Entity target = context.getCurrentTarget();
        target.addEffect(poisonEffect);
        caster.reduceMana(manaTaken);
    }
}
