import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FieldCommands {

    public static void generateInitiativeOrder() {
        List<Unit> allUnits = Database.getAllUnits();

        // Сортируем по убыванию атаки, при равенстве атаки порядок сохраняется
        allUnits.sort(Comparator.comparingInt((Unit u) -> u.attackDamage).reversed());

        Database.initiativeOrder = allUnits;
        Database.currentTurnIndex = 0; // Сбрасываем индекс на начало
    }

    public static void nextTurn() {
        Database.currentTurnIndex++;
        if (Database.currentTurnIndex >= Database.initiativeOrder.size()) {
            Database.currentTurnIndex = 0; // Если список кончился - начинаем с начала
        }
    }

    public static boolean isGameOngoing() {
        return !Database.player1Units.isEmpty() && !Database.player2Units.isEmpty();
    }

    public static void addUnit(String unitType, int x, int y, int player) {
        Unit.UnitType type = Unit.UnitType.valueOf(unitType);
        Unit newUnit = new Unit(type,
                Unit.baseSpeeds.get(type),
                Unit.baseAttacks.get(type),
                Unit.baseHp.get(type),
                Unit.unitCosts.get(type),
                Unit.attackRanges.get(type),
                new int[]{x + 1, y + 1},
                player);
        
        if (player == 1) {
            Database.player1Units.add(newUnit);
        } else {
            Database.player2Units.add(newUnit);
        }
        Database.board[x][y] = Database.getUnitSymbol(newUnit);
    }

    public static void deleteObject(int x, int y) {
        Database.board[x][y] = '0';
        Database.player1Units.removeIf(unit -> unit.location[0] == x-1 && unit.location[1] == y-1);
        Database.player2Units.removeIf(unit -> unit.location[0] == x-1 && unit.location[1] == y-1);
    }

    public static void placeRock(int x, int y) {
        Database.board[x-1][y-1] = '#';
    }
}
