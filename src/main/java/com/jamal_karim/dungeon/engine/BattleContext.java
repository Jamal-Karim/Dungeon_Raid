package com.jamal_karim.dungeon.engine;

import java.util.*;
import com.jamal_karim.dungeon.models.entities.Entity;

public class BattleContext {
    private List<Entity> teamA = new ArrayList<>();
    private List<Entity> teamB = new ArrayList<>();

    private Entity currentTarget;


    public BattleContext() {
    }

    public Entity getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(Entity currentTarget) {
        this.currentTarget = currentTarget;
    }

    public Entity findLowestHealthEnemy(List<Entity> enemies){

        int lowestHP = enemies.get(0).getHp();
        Entity target = enemies.get(0);

        for(Entity e : enemies){
            if(lowestHP >= e.getHp()){
                lowestHP = e.getHp();
                target = e;
            }
        }
        return target;
    }

    public void addToTeam(Entity character, String team){
        if(team.equalsIgnoreCase("hero")){
            teamA.add(character);
        } else{
            teamB.add(character);
        }
    }

    public List<Entity> getEnemiesOf(Entity character){
        return teamA.contains(character) ? teamB : teamA;
    }

    public List<Entity> getAlliesOf(Entity character){
        return teamA.contains(character) ? teamA.stream().filter(e -> e != character).toList() : teamB.stream().filter(e -> e != character).toList();
    }
}
