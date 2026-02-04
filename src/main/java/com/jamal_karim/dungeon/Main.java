package com.jamal_karim.dungeon;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.engine.TurnManager;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Warrior;

public class Main {
    public static void main(String[] args) {
        Entity warrior = new Warrior("warrior", "hero");
        Entity mage = new Mage("mage", "monster");

        BattleContext context = new BattleContext();
        TurnManager turns = new TurnManager(context);

        context.addToTeam(warrior, warrior.getTeam());
        context.addToTeam(mage, mage.getTeam());

        turns.setOrderOfCharacters(context.getTeamA(), context.getTeamB());

        turns.playTurns();
    }
}