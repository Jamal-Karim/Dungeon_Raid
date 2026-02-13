package com.jamal_karim.dungeon.controllers;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Tank;

public class TankCPU implements ActionController{

    @Override
    public void decideAction(Entity user, BattleContext context) {
        Tank tank = (Tank) user;

        context.setCurrentTarget(context.findLowestHealthEnemy(context.getEnemiesOf(tank)));
        Entity target = context.getCurrentTarget();

        if(tank.getHp() < tank.getMaxHp() / 2 && tank.getMana() >= tank.getManaForShield()){
            tank.castShield();
        } else if (target instanceof Mage && tank.getMana() >= tank.getManaForStun()) {
            tank.castStun(target);
        } else if (tank.getMana() >= (tank.getManaForStun() + tank.getManaForShield())) {
            tank.castStun(target);
        } else {
            tank.attack(target, tank.getDamage());
        }
    }
}
