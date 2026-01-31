package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.abilities.Ability;
import com.jamal_karim.dungeon.models.abilities.Fireball;
import com.jamal_karim.dungeon.models.abilities.Poison;

public class Mage extends Entity {
    private Ability Poison = new Poison();
    private Ability Fireball = new Fireball();

    public Mage(String name) {
        super(name, 150, 100, 10);
    }

    @Override
    public void playTurn(BattleContext context) {
        this.processEffects(this.getActiveEffects());
        Entity target = context.getCurrentTarget();

        if(target.getHp() > target.getMaxHp() / 2 && this.getMana() > 20){
            this.attack(target, this.getDamage());
            Poison.execute(this, context);
        } else if(this.getMana() > 10){
            Fireball.execute(this, context);
        } else{
            this.attack(target, this.getDamage());
        }
    }

    @Override
    protected void attack(Entity enemy, int amount) {
        enemy.takeDamage(amount);
    }
}
