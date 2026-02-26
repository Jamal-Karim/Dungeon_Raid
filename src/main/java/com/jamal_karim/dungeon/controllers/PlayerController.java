package com.jamal_karim.dungeon.controllers;

import com.jamal_karim.dungeon.engine.BattleContext;
import com.jamal_karim.dungeon.models.entities.Entity;
import com.jamal_karim.dungeon.models.entities.Mage;
import com.jamal_karim.dungeon.models.entities.Tank;
import com.jamal_karim.dungeon.models.entities.Warrior;

import java.util.*;
import java.util.Scanner;

public class PlayerController implements ActionController{

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void decideAction(Entity user, BattleContext context) {
        displayCurrentStats(user);

        if(user instanceof Warrior){
            handleWarriorTurn((Warrior) user, context);
        } else if(user instanceof Mage){
            handleMageTurn((Mage) user, context);
        } else if(user instanceof Tank){
            handleTankTurn((Tank) user, context);
        }
    }

    private void displayCurrentStats(Entity user){
        System.out.printf("\n================ %s STATUS ================\n", user.getName().toUpperCase());
        System.out.printf(user.getHp() + "/" + user.getMaxHp() + " HP, " + user.getMana() + " Mana\n");

        System.out.println("================ Choose your move ================");
    }

    private int getValidChoice(int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= max) return choice;
            } catch (NumberFormatException e) { }
            System.out.println("Not a valid move. Try again.");
        }
    }

    private Entity chooseEnemy(Entity user, BattleContext context){

        System.out.println("\n================ LIST OF ENEMIES ================");

        List<Entity> enemies = context.getEnemiesOf(user);

        while (true) {
            System.out.println("Select Target:");
            for (int i = 0; i < enemies.size(); i++) {
                Entity e = enemies.get(i);
                System.out.printf("[%d] %s (%d/%d HP)\n", i + 1, e.getName(), e.getHp(), e.getMaxHp());
            }
            System.out.println("[0] Go Back");

            try {
                int index = Integer.parseInt(scanner.nextLine()) - 1;
                if(index == -1){
                    return null;
                }
                if (index >= 0 && index < enemies.size()) {
                    return enemies.get(index);
                } else {
                    System.out.println("That enemy doesn't exist. Please enter a number next to an enemy");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection. Please enter the number next to the enemy.");
            }
        }
    }

    private void handleWarriorTurn(Warrior warrior, BattleContext context) {
        while(true){
            System.out.println("[1] Standard Attack");
            int maxChoice = 1;

            if (warrior.canExecuteSuperAttack()) {
                System.out.println("[2] Super Attack");
                maxChoice = 2;
            }

            int choice = getValidChoice(maxChoice);
            Entity target = chooseEnemy(warrior, context);

            if (target == null) {
                System.out.println("Returning to action menu...");
                continue;
            }

            if (choice == 1) {
                warrior.attack(target, warrior.getDamage());
            } else {
                warrior.executeSuperAttack(target);
            }

            break;
        }
    }

    private void handleTankTurn(Tank tank, BattleContext context) {
        int maxChoice = 3;

        boolean validMove = false;

        while (!validMove) {

            System.out.println("[1] Standard Attack");

            if (tank.getMana() >= tank.getManaForStun()) {
                System.out.printf("[2] Stun Enemy (%d Mana)\n", (int) (tank.getMaxMana() * 0.75));
            } else {
                System.out.println("[X] Stun Enemy (Not enough mana)");
            }

            if (tank.getMana() >= tank.getManaForShield()) {
                System.out.printf("[3] Cast Shield (%d Mana)\n", tank.getManaForShield());
            } else {
                System.out.println("[X] Cast Shield (Not enough mana)");
            }

            int choice = getValidChoice(maxChoice);

            if (choice == 2 && tank.getMana() < tank.getManaForStun()) {
                System.out.println("Insufficient mana for Stun");
                continue;
            }
            if (choice == 3 && tank.getMana() < tank.getManaForShield()) {
                System.out.println("Insufficient mana for Shield");
                continue;
            }

            Entity target;

            switch (choice){
                case 1:
                    target = chooseEnemy(tank, context);
                    if (target == null) {
                        System.out.println("Returning to action menu...");
                        continue;
                    }
                    tank.attack(target, tank.getDamage());
                    break;
                case 2:
                    target = chooseEnemy(tank, context);
                    if (target == null) {
                        System.out.println("Returning to action menu...");
                        continue;
                    }
                    tank.castStun(target);
                    break;
                case 3:
                    tank.castShield();
                    break;
            }
            validMove = true;
        }
    }

    private void handleMageTurn(Mage mage, BattleContext context){
        int maxChoice = 3;

        boolean validMove = false;

        while(!validMove){

            System.out.println("[1] Standard Attack");

            if (mage.getMana() >= 20) {
                System.out.println("[2] Cast Poison (20 Mana)");
            } else {
                System.out.println("[X] Cast Poison (Not enough mana)");
            }

            if (mage.getMana() >= 10) {
                System.out.println("[3] Cast Fireball (10 Mana)");
            } else {
                System.out.println("[X] Cast Fireball (Not enough mana)");
            }

            int choice = getValidChoice(maxChoice);

            if (choice == 2 && mage.getMana() < 20) {
                System.out.println("Insufficient mana for Poison");
                continue;
            }
            if (choice == 3 && mage.getMana() < 10) {
                System.out.println("Insufficient mana for Fireball");
                continue;
            }

            Entity target = chooseEnemy(mage, context);

            if (target == null) {
                System.out.println("Returning to action menu...");
                continue;
            }

            switch (choice){
                case 1:
                    mage.attack(target, mage.getDamage());
                    break;
                case 2:
                    mage.castPoison(target, context);
                    break;
                case 3:
                    mage.castFireball(target, context);
                    break;
            }
            validMove = true;
        }
    }

}
