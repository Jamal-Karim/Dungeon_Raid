# Dungeon Raid: Terminal RPG

A robust, turn-based terminal RPG developed in Java to demonstrate advanced **Object-Oriented Programming (OOP)** principles and clean architectural design.

## 🛠 Project Architecture
This project implements the **Strategy Design Pattern** to decouple character logic from entity data, allowing for seamless switching between Human and AI control.

### Core OOP Implementations
* **Abstraction**: Utilized an abstract `Entity` class to define the essential blueprint of a character while preventing direct instantiation of a generic "Entity".
* **Inheritance**: Specialized classes (`Warrior`, `Mage`, `Tank`) extend the base `Entity` class, inheriting core attributes like HP and Mana while implementing unique class-specific logic.
* **Polymorphism**: 
    * **Dynamic Binding**: The `decideAction` method in the `ActionController` interface allows the game engine to treat `PlayerController` and various `CPUControllers` identically at runtime.
    * **Method Overriding**: Entities override base methods to provide specialized behavior, such as unique attack logic or damage calculations.
* **Encapsulation**: Internal states of entities and the battle environment are protected. Objects like `BattleContext` manage global state through controlled accessors (getters/setters), ensuring data integrity.
* **Composition**: Implemented a "Has-A" relationship where each `Entity` contains an `ActionController` "brain" and a list of `Effect` objects, providing greater flexibility than deep inheritance trees.

---

## 🏗 Package Breakdown

### `models`
* **`entities`**: Contains the `Entity` base class and archetypes.
    * **Warrior**: High damage; features a "Super Attack" threshold when HP is critically low.
    * **Mage**: High mana; manages complex spell interactions (Fireball, Poison) and supportive healing logic.
    * **Tank**: High HP; utilizes damage mitigation (Shield) and crowd control (Stun).
* **`abilities`**: An interface-driven system for spells. This allows mana-based actions to be universally applied to any entity.
* **`effects`**: A modular status effect system (`Poison`, `Shield`, `Stun`) that modifies entity behavior over multiple turns.

### `controllers`
* **`ActionController`**: The strategy interface for turn decision-making.
* **`PlayerController`**: A robust CLI-based controller featuring input validation, error handling, and a "Back" navigation system for menu depth.
* **`CPU Controllers`**: Archetype-specific AI logic (e.g., Tank targets Mages with stuns, Warrior prioritizes low-health targets).

### `engine`
* **`Game`**: The central orchestrator for initialization, team setup, and the main game loop.
* **`BattleContext`**: The "Shared Knowledge" object that tracks the battlefield state, teams, and graveyard.
* **`TurnManager`**: Handles the tick-based logic of rounds and turn order execution.
* **`CombatLogger`**: Separates I/O logic from game logic to ensure clean, readable terminal output.

---

## 🧪 Testing Roadmap (TODO)
* **Unit Testing (JUnit 5)**: Focused on isolating entity math (HP/Mana calculations) and effect triggers.
* **Behavioral Testing (Cucumber)**: Using Gherkin syntax to verify complex battle scenarios and end-to-end gameplay flow.

---

## 🚀 How to Run
1. Ensure you have **JDK 17** and **Maven** installed.
2. Clone the repository.
3. Run `mvn clean compile` to build the project.
4. Run the application via your IDE or `mvn exec:java -Dexec.mainClass="com.jamal_karim.dungeon.Main"`.

---
