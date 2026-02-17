package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.engine.CombatLogger;
import com.jamal_karim.dungeon.models.abilities.Ability;
import com.jamal_karim.dungeon.models.abilities.Fireball;
import com.jamal_karim.dungeon.models.abilities.Poison;

public class Mage extends Entity {
    private Ability poison = new Poison();
    private Ability fireball = new Fireball();

    public Mage(String name, String team) {
        super(name, 12,200, 10, team, 10);
    }

    public void castPoison(Entity target, BattleContext context){
        if(this.getMana() > 20){
            poison.execute(this, target, context);
            this.attack(target, this.getDamage());
        } else{
            System.out.println("Not enough mana for poison");
        }
    }

    public void castFireball(Entity target, BattleContext context){
        if(this.getMana() > 10){
            fireball.execute(this, target, context);
        } else{
            System.out.println("Not enough mana for fireball");
        }
    }

    @Override
    public void attack(Entity enemy, int amount) {
        enemy.takeDamage(amount);
        CombatLogger.logAttack(this, enemy, "launches an attack");
        CombatLogger.logDamage(enemy, this.getDamage());
    }
}
