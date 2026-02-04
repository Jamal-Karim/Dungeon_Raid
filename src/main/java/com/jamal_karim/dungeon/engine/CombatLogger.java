package com.jamal_karim.dungeon.engine;

import com.jamal_karim.dungeon.models.entities.Entity;

import java.util.*;
import java.util.stream.Collectors;

public class CombatLogger {

    public static void logBattleStatus(List<Entity> actives, List<Entity> fallen) {

        Map<String, List<Entity>> teams = actives.stream().collect(Collectors.groupingBy(Entity::getTeam));
        Map<String, List<Entity>> deadTeammates = fallen.stream().collect(Collectors.groupingBy(Entity::getTeam));

        System.out.println("\n================ BATTLE STATUS ================");

        Set<String> allTeamNames = new HashSet<>(teams.keySet());
        allTeamNames.addAll(deadTeammates.keySet());

        for (String team : allTeamNames) {
            System.out.println("\n------------ [" + team.toUpperCase() + "] -----------------");

            for (Entity e : teams.getOrDefault(team, List.of())) {
                System.out.println("  [ALIVE] " + e.getName() + ": " + e.getHp() + "/" + e.getMaxHp() + " HP, " + e.getMana() + " Mana");
            }

             for (Entity e : deadTeammates.getOrDefault(team, List.of())) {
                System.out.println("  [DEAD]  " + e.getName());
            }
        }
        System.out.println("\n===============================================\n");
    }

    public static void logStartOfTurn(Entity entity){
        System.out.println("--- [" + entity.getName() + "] turn ---");
    }

    public static void logAction(Entity entity, String message){
        System.out.println("-> [" + entity.getName() + "]: " + message);
    }

    public static void logAttack(Entity user, Entity target, String message){
        System.out.println("-> [" + user.getName() + "]: " + message + " on " + target.getName());
    }

    public static void logDamage(Entity target, int amount) {
        System.out.println("   * " + target.getName() + " takes " + amount + " damage!");
        if (!target.isAlive()) {
            System.out.println("   *** " + target.getName() + " has been defeated! ***");
        }
    }

    public static void logEffect(Entity target, String effectName, int amount) {
        System.out.println("   ! " + target.getName() + " is affected by " + effectName + " and takes " + amount + " damage");
    }
}
