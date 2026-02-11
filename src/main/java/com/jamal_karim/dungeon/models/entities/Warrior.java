package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.engine.CombatLogger;

public class Warrior extends Entity {

    private boolean superAttack;

    public Warrior(String name, String team) {
        super(name, 200, 50, 25, team, 50);
        this.superAttack = false;
    }

    @Override
    public void playTurn(BattleContext context) {
        int damageGiven;
        context.setCurrentTarget(context.findLowestHealthEnemy(context.getEnemiesOf(this)));
        Entity target = context.getCurrentTarget();

        if(this.getHp() < 10){this.superAttack = true;}

        if(this.getHp() < 10 && this.superAttack){
            damageGiven = executeSuperAttack(target);
        } else if (target instanceof Tank) {
            this.attack(target, 35);
            damageGiven = 35;
        } else{
            this.attack(target, 25);
            damageGiven = 25;
        }
        CombatLogger.logDamage(target, damageGiven);
    }

    public int executeSuperAttack(Entity enemy){
        int damageGiven = 0;
//        TODO: need logic for when health is below 10 somewhere else

        if(enemy instanceof Tank){
            enemy.takeDamage(50);
            damageGiven = 50;
        } else{
            enemy.takeDamage(this.getDamage() + 15);
            damageGiven = this.getDamage() + 15;
        }
        this.superAttack = false;
        CombatLogger.logAttack(this, enemy, "launches a super attack in desperation");
        return damageGiven;
    }

    @Override
    protected void attack(Entity enemy, int amount) {
        enemy.takeDamage(amount);
        CombatLogger.logAttack(this, enemy, "launches an attack");
    }
}
