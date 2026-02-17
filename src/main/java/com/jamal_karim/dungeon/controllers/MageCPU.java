package com.jamal_karim.dungeon.controllers;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;

public class MageCPU implements ActionController{
    @Override
    public void decideAction(Entity user, BattleContext context) {
        Mage mage = (Mage) user;

        context.setCurrentTarget(context.findLowestHealthEnemy(context.getEnemiesOf(mage)));
        Entity target = context.getCurrentTarget();

        if(target.getHp() > target.getMaxHp() / 2 && mage.getMana() > 20){
            mage.castPoison(target, context);
        } else if(mage.getMana() > 10){
            mage.castFireball(target, context);
        } else{
            mage.attack(target, mage.getDamage());
        }
    }
}
