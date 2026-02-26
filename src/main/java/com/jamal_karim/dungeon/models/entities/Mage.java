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
        super(name, 120,200, 10, team, 10);
    }

    public Mage(String name, String team, int initialHp, int initialMana) {
        super(name, initialHp, initialMana, 10, team, 50);
    }

    public void castPoison(Entity target, BattleContext context){
        if(this.getMana() > 20){
            poison.execute(this, target, context);
            this.attack(target, this.getDamage());
        } else{
            throw new IllegalStateException("Cannot execute poison cast: Conditions not met (mana must be > 20).");
        }
    }

    public void castFireball(Entity target, BattleContext context){
        if(this.getMana() > 10){
            fireball.execute(this, target, context);
        } else{
            throw new IllegalStateException("Cannot execute fireball cast: Conditions not met (mana must be > 10).");
        }
    }

    @Override
    public void attack(Entity enemy, int amount) {
        int actualDamage = enemy.takeDamage(amount);
        CombatLogger.logAttack(this, enemy, "launches an attack");
        CombatLogger.logDamage(enemy, actualDamage);
    }
}
