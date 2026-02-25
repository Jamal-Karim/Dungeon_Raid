package com.jamal_karim.dungeon.models.entities;

import com.jamal_karim.dungeon.engine.CombatLogger;

public class Warrior extends Entity {

    private boolean superAttack;

    public Warrior(String name, String team) {
        super(name, 200, 50, 25, team, 50);
        this.superAttack = false;
    }

    public boolean hasSuperAttack() {
        return superAttack;
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

            int actualDamage = enemy.takeDamage(damageGiven);
            this.superAttack = false;
            CombatLogger.logAttack(this, enemy, "launches a super attack in desperation");
            CombatLogger.logDamage(enemy, actualDamage);
    }

    @Override
    public void attack(Entity enemy, int amount) {
        int actualDamage = enemy.takeDamage(amount);
        CombatLogger.logAttack(this, enemy, "launches an attack");
        CombatLogger.logDamage(enemy, actualDamage);
    }

    @Override
    public int takeDamage(int damage) {
        int actualDamage = super.takeDamage(damage);
        if(this.getHp() < 10 && this.getHp() > 0){
            this.superAttack = true;
        }
        return actualDamage;
    }
}
