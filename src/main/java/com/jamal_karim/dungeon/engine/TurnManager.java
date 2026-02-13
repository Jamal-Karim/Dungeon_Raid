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
        CombatLogger.logBattleStatus(context.getAllEntities(), context.getGraveyard());

        while(!context.checkForWinner()){
            Entity e = orderOfCharacters.poll();

            if(e != null && e.isAlive()){
                CombatLogger.logStartOfTurn(e);

                boolean isStunned = e.getActiveEffects().stream().anyMatch(effect -> effect.getName().equals("Stun Effect"));

                if(isStunned){
                    e.processEffects(e.getActiveEffects());
                } else{
                    e.processEffects(e.getActiveEffects());
                    e.takeTurn(context);
                }

                cleanup();

                if(e.isAlive()){
                    orderOfCharacters.add(e);
                }
                if (context.checkForWinner()) {
                    break;
                }
                CombatLogger.logBattleStatus(context.getAllEntities(), context.getGraveyard());
            }
        }
    }

    public void cleanup(){

        context.getAllEntities().stream().filter(entity -> !entity.isAlive()).forEach(context.getGraveyard()::add);
        context.getAllEntities().removeIf(entity -> !entity.isAlive());
    }

}
