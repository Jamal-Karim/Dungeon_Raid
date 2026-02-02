package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.BattleContext;

public class Warrior extends Entity {

    private boolean superAttack;

    public Warrior(String name, String team) {
        super(name, 200, 50, 25, team);
        this.superAttack = false;
    }

    @Override
    public void playTurn(BattleContext context) {
        this.processEffects(this.getActiveEffects());

        context.setCurrentTarget(context.findLowestHealthEnemy(context.getEnemiesOf(this)));
        Entity target = context.getCurrentTarget();

        if(this.getHp() < 10){this.superAttack = true;}

        if(this.getHp() < 10 && this.superAttack){
            executeSuperAttack(target);
        } else if (target instanceof Tank) {
            this.attack(target, 35);
        } else{
            this.attack(target, 25);
        }
    }

    public void executeSuperAttack(Entity enemy){

//        TODO: need logic for when health is below 10 somewhere else

        if(enemy instanceof Tank){
            enemy.takeDamage(50);
        } else{
            enemy.takeDamage(this.getDamage() + 15);
        }
        this.superAttack = false;
    }

    @Override
    protected void attack(Entity enemy, int amount) {
        enemy.takeDamage(amount);
    }
}
