import java.util.Scanner;

public class CommandProcessor {

    public static void processCommand(String commandLine) {
        String[] parts = commandLine.trim().split(" ");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "help":
                System.out.println("Available commands:");
                System.out.println("start - Display the initial game field.");
                System.out.println("gen - Generate unit turn order.");
                System.out.println("a X Y - Attack the target at coordinates X Y.");
                System.out.println("m X Y - Move the current unit to coordinates X Y.");
                System.out.println("skip - Skip the current turn.");
                System.out.println("add TYPE X Y P - Add a unit of TYPE at X Y for player P.");
                System.out.println("del X Y - Remove an object at X Y.");
                System.out.println("rock X Y - Place a rock at X Y.");
                System.out.println("units - Display all unit characteristics.");
                System.out.println("help - Show this help message.");
                break;

            case "start":
                Graphikdraw.drawChessBoard();
                break;

            case "gen":
                FieldCommands.generateInitiativeOrder();
                System.out.println("Initiative order generated.");
                break;

            case "a":
                if (parts.length < 3) {
                    System.out.println("Invalid command. Usage: a X Y");
                    return;
                }
                try {
                    int targetX = Integer.parseInt(parts[1]);
                    int targetY = Integer.parseInt(parts[2]);
                    if (UnitCommands.attackUnit(targetX, targetY)) {
                        System.out.println("Attack successful.");
                    } else {
                        System.out.println("Attack failed.");
                    }
                    FieldCommands.nextTurn();
                    Graphikdraw.drawChessBoard();
                    Database.clearMovementTrail();
                    if (!FieldCommands.isGameOngoing()) {
                        System.out.println("Game Over!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid coordinates. Usage: a X Y");
                }
                break;

            case "m":
                if (parts.length < 3) {
                    System.out.println("Invalid command. Usage: m X Y");
                    return;
                }
                try {
                    int moveX = Integer.parseInt(parts[1]);
                    int moveY = Integer.parseInt(parts[2]);
                    if (UnitCommands.moveUnit(moveX, moveY)) {
                        System.out.println("Move successful.");
                    } else {
                        System.out.println("Move failed.");
                    }
                    FieldCommands.nextTurn();
                    Graphikdraw.drawChessBoard();
                    Database.clearMovementTrail();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid coordinates. Usage: m X Y");
                }
                break;

            case "skip":
                Database.clearMovementTrail();
                UnitCommands.skipTurn();
                FieldCommands.nextTurn();
                Graphikdraw.drawChessBoard();
                break;

            case "add":
                if (parts.length < 5) {
                    System.out.println("Invalid command. Usage: add TYPE X Y P");
                    return;
                }
                try {
                    String unitType = parts[1].toUpperCase();
                    int x = Integer.parseInt(parts[2]);
                    int y = Integer.parseInt(parts[3]);
                    int player = Integer.parseInt(parts[4]);
                    FieldCommands.addUnit(unitType, x, y, player);
                    System.out.println("Unit added successfully.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid parameters. Usage: add TYPE X Y P");
                }
                break;

            case "del":
                if (parts.length < 3) {
                    System.out.println("Invalid command. Usage: del X Y");
                    return;
                }
                try {
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    FieldCommands.deleteObject(x, y);
                    System.out.println("Object removed successfully.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid parameters. Usage: del X Y");
                }
                break;

            case "rock":
                if (parts.length < 3) {
                    System.out.println("Invalid command. Usage: rock X Y");
                    return;
                }
                try {
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    FieldCommands.placeRock(x, y);
                    System.out.println("Rock placed successfully.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid parameters. Usage: rock X Y");
                }
                break;

              case "units":
                  System.out.println("=== Unit Characteristics ===");
                  for (Unit.UnitType type : Unit.UnitType.values()) {
                      System.out.println(type + ":");
                      System.out.println("  HP: " + Unit.baseHp.get(type));
                      System.out.println("  Attack: " + Unit.baseAttacks.get(type));
                      System.out.println("  Speed: " + Unit.baseSpeeds.get(type));
                      System.out.println("  Range: " + Unit.attackRanges.get(type));
                      System.out.println("  Cost: " + Unit.unitCosts.get(type));
                      System.out.println();
                  }
                break;


            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
                break;
        }
    }
}
