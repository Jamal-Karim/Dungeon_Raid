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
        context.setCurrentTarget(context.findLowestHealthEnemy(context.getEnemiesOf(this)));
        Entity target = context.getCurrentTarget();

        if(this.getHp() < 10 && this.superAttack){
            executeSuperAttack(target);
        } else if (target instanceof Tank) {
            this.attack(target, 35);
        } else{
            this.attack(target, 25);
        }
    }

    private boolean canExecuteSuperAttack(){
        return superAttack && this.getHp() < 10;
    }

    public void executeSuperAttack(Entity enemy){

        if (!canExecuteSuperAttack()) {
            System.out.println("Condition not met for Super Attack!");
            return;
        }

            int damageGiven;

            if(enemy instanceof Tank){
                damageGiven = 50;
            } else{
                damageGiven = this.getDamage() + 15;
            }

            enemy.takeDamage(damageGiven);
            this.superAttack = false;
            CombatLogger.logAttack(this, enemy, "launches a super attack in desperation");
            CombatLogger.logDamage(enemy, damageGiven);
    }

    @Override
    protected void attack(Entity enemy, int amount) {
        enemy.takeDamage(amount);
        CombatLogger.logAttack(this, enemy, "launches an attack");
        CombatLogger.logDamage(enemy, amount);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if(this.getHp() < 10 && this.getHp() > 0){
            this.superAttack = true;
        }
    }
}
