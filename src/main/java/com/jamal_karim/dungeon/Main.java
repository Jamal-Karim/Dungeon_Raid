package com.jamal_karim.dungeon;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.engine.TurnManager;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Tank;
import com.jamal_karim.dungeon.models.entities.Warrior;

public class Main {
    public static void main(String[] args) {
        Entity warrior = new Warrior("warrior", "hero");
        Entity warrior2 = new Warrior("warrior2", "hero");
        Entity mage = new Mage("mage", "monster");
        Entity tank = new Tank("tank", "monster");

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