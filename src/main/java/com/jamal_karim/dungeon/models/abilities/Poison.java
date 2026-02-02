package com.jamal_karim.dungeon.models.abilities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.effects.PoisonEffect;
import com.jamal_karim.dungeon.models.entities.Entity;

public class Poison implements Ability{
    private int manaTaken = 20;

    @Override
    public void execute(Entity caster, BattleContext context) {

        if(caster.canAffordAbility(caster, this)){
            PoisonEffect poisonEffect = new PoisonEffect();

            caster.reduceMana(manaTaken);
            Entity target = context.getCurrentTarget();
            target.addEffect(poisonEffect);
        } else {
            System.out.println("Caster does not have enough mana");
        }
    }

    @Override
    public int getManaTaken(Ability ability) {
        return this.manaTaken;
    }
}
