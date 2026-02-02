package com.jamal_karim.dungeon.models.abilities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;

public class Fireball implements Ability {
    private int manaTaken = 10;

    @Override
    public void execute(Entity caster, BattleContext context) {
        if(caster.canAffordAbility(caster, this)){
            caster.reduceMana(manaTaken);
            Entity target = context.getCurrentTarget();
            target.takeDamage(20);
        } else {
            System.out.println("Caster does not have enough mana");
        }
    }

    @Override
    public int getManaTaken(Ability ability) {
        return this.manaTaken;
    }
}
