package com.jamal_karim.dungeon;

import com.jamal_karim.dungeon.controllers.ActionController;
import com.jamal_karim.dungeon.controllers.MageCPU;
import com.jamal_karim.dungeon.controllers.TankCPU;
import com.jamal_karim.dungeon.controllers.WarriorCPU;
import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.engine.TurnManager;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Tank;
import com.jamal_karim.dungeon.models.entities.Warrior;

public class Main {
    public static void main(String[] args) {

        ActionController WarriorCPU = new WarriorCPU();
        ActionController MageCPU = new MageCPU();
        ActionController TankCPU = new TankCPU();

        Entity warrior = new Warrior("warrior", "hero");
        warrior.setController(WarriorCPU);

        Entity warrior2 = new Warrior("warrior2", "hero");
        warrior2.setController(WarriorCPU);

        Entity mage = new Mage("mage", "monster");
        mage.setController(MageCPU);

        Entity tank = new Tank("tank", "monster");
        tank.setController(TankCPU);

        BattleContext context = new BattleContext();
        TurnManager turns = new TurnManager(context);

        context.addToTeam(warrior);
        context.addToTeam(mage);
        context.addToTeam(warrior2);
        context.addToTeam(tank);

        turns.setOrderOfCharacters(context.getAllEntities());

        turns.playTurns();
    }
}