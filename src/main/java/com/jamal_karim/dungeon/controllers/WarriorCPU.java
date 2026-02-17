package com.jamal_karim.dungeon.controllers;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Tank;
import com.jamal_karim.dungeon.models.entities.Warrior;

public class WarriorCPU implements ActionController{

    @Override
    public void decideAction(Entity user, BattleContext context) {
        Warrior warrior = (Warrior) user;

        context.setCurrentTarget(context.findLowestHealthEnemy(context.getEnemiesOf(warrior)));
        Entity target = context.getCurrentTarget();

        if(warrior.getHp() < 10 && warrior.hasSuperAttack()){
            warrior.executeSuperAttack(target);
        } else if (target instanceof Tank) {
            warrior.attack(target, 35);
        } else{
            warrior.attack(target, warrior.getDamage());
        }
    }
}
