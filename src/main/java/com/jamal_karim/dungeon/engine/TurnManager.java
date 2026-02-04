package com.jamal_karim.dungeon.engine;

import com.jamal_karim.dungeon.models.entities.Entity;

import java.util.*;

public class TurnManager {

    private Queue<Entity> orderOfCharacters;
    private BattleContext context;

    public TurnManager(BattleContext context) {
        this.context = context;
    }

    public void setOrderOfCharacters(List<Entity> teamA, List<Entity> teamB){
        List<Entity> tmpList = new ArrayList<>();
        tmpList.addAll(teamA);
        tmpList.addAll(teamB);
        tmpList.sort(Comparator.comparing(Entity::getSpeed).reversed());

        this.orderOfCharacters = new LinkedList<>(tmpList);
    }

    public void playTurns(){

        while(!context.checkForWinner()){
            Entity e = orderOfCharacters.poll();

            if(e != null && e.isAlive()){
                e.playTurn(context);

                if(e.isAlive()){
                    orderOfCharacters.add(e);
                }
            }
        }
    }

}
