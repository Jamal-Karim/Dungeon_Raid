package com.jamal_karim.dungeon.engine;

import com.jamal_karim.dungeon.models.entities.Entity;

import java.util.*;

public class TurnManager {

    private Queue<Entity> orderOfCharacters;
    private BattleContext context;

    public TurnManager(BattleContext context) {
        this.context = context;
    }

    public void setOrderOfCharacters(List<Entity> entities){
        List<Entity> sortedCopy = new ArrayList<>(entities);
        sortedCopy.sort(Comparator.comparing(Entity::getSpeed).reversed());
        this.orderOfCharacters = new LinkedList<>(sortedCopy);
    }

    public void playTurns(){

        while(!context.checkForWinner()){
            CombatLogger.logBattleStatus(context.getAllEntities(), context.getGraveyard());
            Entity e = orderOfCharacters.poll();

            if(e != null && e.isAlive()){
                CombatLogger.logStartOfTurn(e);
                e.playTurn(context);

                cleanup();

                if(e.isAlive()){
                    orderOfCharacters.add(e);
                }
            }
        }
    }

    public void cleanup(){

        context.getAllEntities().stream().filter(entity -> !entity.isAlive()).forEach(context.getGraveyard()::add);
        context.getAllEntities().removeIf(entity -> !entity.isAlive());
    }

}
