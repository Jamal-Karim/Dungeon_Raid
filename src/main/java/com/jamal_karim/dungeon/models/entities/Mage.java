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

    @Override
    public void playTurn(BattleContext context) {
        this.processEffects(this.getActiveEffects());

        context.setCurrentTarget(context.findLowestHealthEnemy(context.getEnemiesOf(this)));
        Entity target = context.getCurrentTarget();

        if(target.getHp() > target.getMaxHp() / 2 && this.getMana() > 20){
            poison.execute(this, context);
            this.attack(target, this.getDamage());
        } else if(this.getMana() > 10){
            fireball.execute(this, context);
        } else{
            this.attack(target, this.getDamage());
            CombatLogger.logAttack(this, target, "launches an attack");
            CombatLogger.logDamage(target, this.getDamage());
        }

        if(!target.isAlive()){
            context.getEnemiesOf(this).remove(target);
        }
    }

    @Override
    protected void attack(Entity enemy, int amount) {
        enemy.takeDamage(amount);
    }
}
