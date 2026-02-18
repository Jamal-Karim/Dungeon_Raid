# Dungeon Raid: A Java-Based Terminal RPG ⚔️

Dungeon Raid is a turn-based, fantasy-themed RPG that runs entirely in the terminal. Developed using **Java 17** and managed with **Apache Maven**, this project serves as a comprehensive demonstration of core **Object-Oriented Programming (OOP)** principles and robust software architecture.

Assemble your team of heroes and dive into tactical battles against either another player or a cunning CPU-controlled monster squad!

## Key Features ✨

- **Strategic Turn-Based Combat:** Engage in dynamic battles where strategic decisions are paramount.
- **Three Unique Character Classes:** Choose from iconic archetypes, each with distinct abilities and playstyles:
    -   **Warrior:** A frontline brawler with high damage and a powerful "Super Attack" triggered at low health.
    -   **Mage:** A versatile spellcaster capable of inflicting damage-over-time (Poison) and burst damage (Fireball)
    -   **Tank:** A durable defender, specialized in mitigating damage with shields and controlling the battlefield with stunning attacks.
- **Versatile Opponent Modes:** Challenge your friends in Player vs. Player (PvP) combat or test your mettle against sophisticated CPU AI.
- **Dynamic AI Personalities:** Each CPU-controlled class (Warrior, Mage, Tank) possesses unique AI logic, mirroring their tactical roles.

## Architectural Design & OOP Principles 🏗️

This project was meticulously designed to highlight the application of fundamental OOP and software design principles in a practical context.

### Core Design Patterns 🧩

-   **Strategy Pattern**: This pattern is central to how entities make decisions. The `ActionController` interface defines a contract for how an entity "decides" its turn.
    -   **Example**: A `PlayerController` implementation handles human input, while `WarriorCPU`, `MageCPU`, and `TankCPU` provide distinct AI logic. By assigning different `ActionController` implementations to an `Entity`, the game seamlessly switches between player and AI control, demonstrating dynamic behavior at runtime without altering the `Entity` class itself.

-   **Composition over Inheritance**: We leverage composition to build flexible entities. Instead of creating deep and rigid inheritance hierarchies, entities are composed of various behaviors.
    -   **Example**: An `Entity` "has-a" reference to an `ActionController` (its brain) and "has-a" list of `Effect` objects (status ailments). This allows for greater flexibility, as new behaviors or effects can be added or modified independently without changing the core `Entity` structure.

### Object-Oriented Principles in Depth 🧠

-   **Abstraction**: Focuses on defining essential characteristics and behaviors while hiding implementation details.
    -   **Example**: The `Entity` abstract class defines common attributes like `hp`, `mana`, `name`, and fundamental actions such as `takeDamage()` and `attack()`. It provides a blueprint for all characters without specifying their unique class-specific details. Similarly, the `Ability` and `Effect` interfaces abstract away the specifics of different spells and status effects, defining common contracts for their `execute()` and `applyTick()` methods, respectively.

-   **Inheritance**: Enables new classes (subclasses) to derive properties and behaviors from existing classes (superclasses), promoting code reuse and specialization.
    -   **Example**: `Warrior`, `Mage`, and `Tank` are concrete classes that extend the abstract `Entity` class. They inherit all base attributes and methods from `Entity` but then specialize them by adding unique abilities (e.g., `Warrior`'s `executeSuperAttack()`), different stat distributions, and overriding common methods (e.g., each class might have a different `attack()` implementation or specific logic within its `takeDamage()` method to trigger class-specific effects).

-   **Polymorphism**: The ability of objects to take on many forms, allowing a single interface to represent different underlying types.
    -   **Example**: The `takeTurn()` method in the `Entity` class calls `controller.decideAction(this, context)`. Because `PlayerController`, `WarriorCPU`, `MageCPU`, and `TankCPU` all implement the `ActionController` interface, the game engine can interact with any of them uniformly. At runtime, the specific `decideAction` implementation (human input or AI logic) is invoked based on the type of `ActionController` assigned to the `Entity`. This "many forms" allows the game to handle diverse character control schemes seamlessly.

-   **Encapsulation**: The bundling of data (attributes) and methods (behaviors) that operate on the data into a single unit (class), and restricting direct access to some of the object's components.
    -   **Example**: In `Entity`, attributes like `hp` and `mana` are private, and access to them is controlled through public `getHp()`/`setHp()` and `getMana()`/`reduceMana()` methods. This protects the internal state from external, unauthorized modification and ensures data integrity. Similarly, the `BattleContext` encapsulates the entire state of the battle (teams, graveyard, current targets), providing controlled access to this global data through its own public methods.

## Detailed Project Structure 📁

Here's a breakdown of the key Java files and their responsibilities:

-   **`src/main/java/com/jamal_karim.dungeon/`**
    -   **`Main.java`**: The application's entry point. Initializes and starts a new `Game` instance.

-   **`src/main/java/com/jamal_karim.dungeon/engine/`**
    -   **`Game.java`**: The central orchestrator. Handles game setup (team creation, player/CPU selection), manages the main game loop, and coordinates turns by interacting with `TurnManager` and `BattleContext`.
    -   **`TurnManager.java`**: Responsible for determining the turn order of all active entities, processing each entity's turn, and managing the overall round progression.
    -   **`BattleContext.java`**: Acts as the shared knowledge base for the current battle. It holds references to all active entities, teams, the graveyard of defeated entities, and provides utility methods for finding targets (e.g., `findLowestHealthEnemy`).
    -   **`CombatLogger.java`**: A utility class dedicated to handling all console output related to combat events. It abstracts away `System.out.println` calls from the core game logic, making the code cleaner and easier to manage.

-   **`src/main/java/com/jamal_karim.dungeon/controllers/`**
    -   **`ActionController.java`**: An interface that defines the contract for how any entity will "decide" its action during its turn. This is the heart of the Strategy pattern.
    -   **`PlayerController.java`**: Implements `ActionController` for human-controlled entities. It manages command-line input, presents action menus specific to the entity's class, and validates player choices.
    -   **`MageCPU.java`**: Implements `ActionController` for AI-controlled Mages, defining their decision-making logic (e.g., prioritizing spells, healing when necessary).
    -   **`TankCPU.java`**: Implements `ActionController` for AI-controlled Tanks, dictating their defensive and crowd-control strategies.
    -   **`WarriorCPU.java`**: Implements `ActionController` for AI-controlled Warriors, focusing on aggressive targeting and utilizing their unique abilities like "Super Attack."

-   **`src/main/java/com/jamal_karim.dungeon/models/`**
    -   **`entities/`**
        -   **`Entity.java`**: The abstract base class for all combatants. It defines core attributes (HP, Mana, Name, Team, Speed) and fundamental behaviors applicable to all characters.
        -   **`Mage.java`**: Extends `Entity` to represent a Mage character, adding specific spells and mana management.
        -   **`Tank.java`**: Extends `Entity` to represent a Tank character, featuring defensive abilities and higher HP.
        -   **`Warrior.java`**: Extends `Entity` to represent a Warrior character, specializing in physical damage and a unique "Super Attack" mechanic.
    -   **`abilities/`**
        -   **`Ability.java`**: An interface defining the common structure for any active ability a character can use, including an `execute()` method and mana cost.
        -   **`Fireball.java`**: Implements `Ability` for a direct damage spell.
        -   **`Poison.java`**: Implements `Ability` for a damage-over-time spell.
    -   **`effects/`**
        -   **`Effect.java`**: An interface defining the common structure for any status effect that can be applied to an entity, including `applyTick()` and `getDuration()`.
        -   **`PoisonEffect.java`**: Implements `Effect` to represent ongoing poison damage.
        -   **`ShieldEffect.java`**: Implements `Effect` to represent a temporary damage reduction.
        -   **`StunEffect.java`**: Implements `Effect` to represent a turn-skipping crowd control effect.

## How to Run 🚀

1.  Ensure you have **Java 17 (JDK)** and **Apache Maven** installed and configured on your system.
2.  Clone the repository to your local machine:
    ```sh
    git clone <repository-url>
    ```
3.  Navigate to the project directory and build the project using Maven:
    ```sh
    mvn clean compile
    ```
4.  Run the application directly from the terminal:
    ```sh
    mvn exec:java
    ```

## Testing Strategy 🧪

The project is configured with **JUnit 5** for unit testing and **Cucumber** for behavior-driven development (BDD). This two-pronged approach ensures both granular component validation and high-level scenario verification:

-   **Unit Tests (JUnit):** Focus on isolating and testing individual methods and classes, such as damage calculations, mana consumption, ability cooldowns, and the precise application of status effects.
-   **Behavioral Tests (Cucumber):** Utilize Gherkin syntax to define and test complex, end-to-end combat scenarios from a user's perspective, ensuring the game logic behaves as expected across various interactions.
