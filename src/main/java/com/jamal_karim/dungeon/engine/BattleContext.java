package com.jamal_karim.dungeon.engine;

import java.util.*;
import com.jamal_karim.dungeon.models.entities.Entity;

public class BattleContext {
    private Entity currentTarget;
    private List<Entity> allEntities = new ArrayList<>();
    private List<Entity> graveyard = new ArrayList<>();

    public BattleContext() {
    }

    public List<Entity> getAllEntities() {
        return allEntities;
    }

    public List<Entity> getGraveyard() {
        return graveyard;
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

    public void addToTeam(Entity character){
        this.allEntities.add(character);
    }

    public List<Entity> getEnemiesOf(Entity character){
        return allEntities.stream().filter(entity -> !entity.getTeam().equals(character.getTeam())).toList();
    }

    public boolean checkForWinner() {

        Set<String> activeTeams = new HashSet<>();

        for (Entity e : allEntities) {
            activeTeams.add(e.getTeam());
        }

        if (activeTeams.size() == 1) {
            CombatLogger.logBattleStatus(allEntities, graveyard);
            System.out.println("The winner is: " + activeTeams.iterator().next());
            return true;
        }
        return activeTeams.isEmpty();
    }
}
