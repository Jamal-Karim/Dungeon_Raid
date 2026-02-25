package com.jamal_karim.dungeon.engine.ui;

import com.jamal_karim.dungeon.engine.CombatLogger;
import com.jamal_karim.dungeon.models.entities.Entity;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements GameUI {
    private final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void waitForPlayerInput() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    @Override
    public void logStartOfTurn(Entity entity) {
        CombatLogger.logStartOfTurn(entity);
    }

    @Override
    public void logBattleStatus(List<Entity> actives, List<Entity> fallen) {
        CombatLogger.logBattleStatus(actives, fallen);
    }

    @Override
    public void logWinner(String teamName) {
        System.out.println("The winner is: " + teamName);
    }
}
