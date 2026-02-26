package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.CombatLogger;

public class Warrior extends Entity {

    public Warrior(String name, String team) {
        super(name, 200, 50, 25, team, 50);
    }

    public Warrior(String name, String team, int initialHp) {
        super(name, initialHp, 50, 25, team, 50);
    }

    public boolean canExecuteSuperAttack(){
        return this.getHp() > 0 && this.getHp() < 10;
    }

    public void executeSuperAttack(Entity enemy){

        if (!canExecuteSuperAttack()) {
            throw new IllegalStateException("Cannot execute Super Attack: Conditions not met (HP must be < 10).");
        }

            int damageGiven;

            if(enemy instanceof Tank){
                damageGiven = 50;
            } else{
                damageGiven = this.getDamage() + 15;
            }

            int actualDamage = enemy.takeDamage(damageGiven);
            CombatLogger.logAttack(this, enemy, "launches a super attack in desperation");
            CombatLogger.logDamage(enemy, actualDamage);
    }

    @Override
    public void attack(Entity enemy, int amount) {
        int actualDamage = enemy.takeDamage(amount);
        CombatLogger.logAttack(this, enemy, "launches an attack");
        CombatLogger.logDamage(enemy, actualDamage);
    }

}
