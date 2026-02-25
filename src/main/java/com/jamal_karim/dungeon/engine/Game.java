package com.jamal_karim.dungeon.engine;

import com.jamal_karim.dungeon.controllers.*;
import com.jamal_karim.dungeon.engine.ui.ConsoleUI;
import com.jamal_karim.dungeon.engine.ui.GameUI;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Warrior;
import com.jamal_karim.dungeon.models.entities.Tank;

import java.util.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private BattleContext context = new BattleContext();
    private final GameUI ui = new ConsoleUI();
    private TurnManager turns = new TurnManager(context, ui);
    private final ActionController WarriorCPU = new WarriorCPU();
    private final ActionController MageCPU = new MageCPU();
    private final ActionController TankCPU = new TankCPU();
    private final ActionController Player = new PlayerController();
    private final Scanner scanner = new Scanner(System.in);
    private final List<Entity> entitiesReadyToPlay = new ArrayList<>();

    public void start(){
        boolean playing = true;
        while(playing) {
            setUpGame();
            setUpContext();
            playTurns();

            System.out.println("The raid is over! Play again? [1] Yes [2] No");
            if(getValidChoice(2) == 2) playing = false;
            else resetGame();
        }
    }

    private void resetGame(){
        entitiesReadyToPlay.clear();
        context = new BattleContext();
        turns = new TurnManager(context, ui);
    }

    private void setUpGame(){
        System.out.println("\n================ Dungeon Raid ================");

        System.out.println("How many characters will be on each team? Max limit of 3");

        int numOfEntitiesPerTeam = getValidChoice(3);

        setUpUserTeam(numOfEntitiesPerTeam);

        System.out.println("Choose how player 2 will play, enter 1 or 2:");
        System.out.println("[1] User");
        System.out.println("[2] CPU");

        int choice = getValidChoice(2);

        if(choice == 1){
            setUpUserTeam(numOfEntitiesPerTeam);
        } else if(choice == 2){
            setUpCPUTeam(numOfEntitiesPerTeam);
        }

        System.out.println("Press Enter to show team comparison");
        scanner.nextLine();
    }

    private void setUpContext(){
        for(Entity e : entitiesReadyToPlay){
            context.addToTeam(e);
        }

        CombatLogger.logBattleStatus(context.getAllEntities(), context.getGraveyard());

        System.out.println("Press Enter to begin the raid");
        scanner.nextLine();
    }

    private void playTurns(){
        turns.setOrderOfCharacters(context.getAllEntities());
        turns.playTurns();
    }

    private void setUpCPUTeam(int numOfEntities){

        Random random = new Random();
        int threeDigitInt = random.nextInt(900) + 100;

        String teamName = "Monsters_" + threeDigitInt;

        for(int i = 0; i < numOfEntities; i++){
            int randomEntityChoice = ThreadLocalRandom.current().nextInt(1, 4);
            int entitySuffix = random.nextInt(900) + 100;

            switch (randomEntityChoice){
                case 1:
                    Entity warrior = new Warrior("Warrior_" + entitySuffix, teamName);
                    warrior.setController(WarriorCPU);
                    entitiesReadyToPlay.add(warrior);
                    System.out.printf("%s enters the raid with %s\n", warrior.getName(), teamName);
                    break;
                case 2:
                    Entity mage = new Mage("Mage_" + entitySuffix, teamName);
                    mage.setController(MageCPU);
                    entitiesReadyToPlay.add(mage);
                    System.out.printf("%s enters the raid with %s\n", mage.getName(), teamName);
                    break;
                case 3:
                    Entity tank = new Tank("Tank_" + entitySuffix, teamName);
                    tank.setController(TankCPU);
                    entitiesReadyToPlay.add(tank);
                    System.out.printf("%s enters the raid with %s\n", tank.getName(), teamName);
                    break;
            }
        }

    }

    private void setUpUserTeam(int numOfEntities){
        System.out.println("Choose your team name");
        String teamName = scanner.nextLine();

        for(int i = 0; i < numOfEntities; i++){
            System.out.println("What is the first entity you want to recruit:");
            System.out.println("[1]: Warrior");
            System.out.println("[2]: Mage");
            System.out.println("[3]: Tank");

            int choice = getValidChoice(3);
            String entityName;

            switch (choice){
                case 1:
                    System.out.println("Give your Warrior a name");
                    entityName = scanner.nextLine();
                    Entity warrior = new Warrior(entityName, teamName);
                    warrior.setController(Player);
                    entitiesReadyToPlay.add(warrior);
                    System.out.printf("%s enters the raid with %s\n", warrior.getName(), teamName);
                    break;
                case 2:
                    System.out.println("Give your Mage a name");
                    entityName = scanner.nextLine();
                    Entity mage = new Mage(entityName, teamName);
                    mage.setController(Player);
                    entitiesReadyToPlay.add(mage);
                    System.out.printf("%s enters the raid with %s\n", mage.getName(),teamName);
                    break;
                case 3:
                    System.out.println("Give your Tank a name");
                    entityName = scanner.nextLine();
                    Entity tank = new Tank(entityName, teamName);
                    tank.setController(Player);
                    entitiesReadyToPlay.add(tank);
                    System.out.printf("%s enters the raid with %s\n", tank.getName(), teamName);
                    break;
            }
        }
    }

    private int getValidChoice(int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= max) return choice;
            } catch (NumberFormatException e) { }
            System.out.println("Not a valid choice. Try again.");
        }
    }

}
