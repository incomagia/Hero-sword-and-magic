public class Graphikdraw {

    public static void drawChessBoard() {
        System.out.println("  +----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+");
        for (int row = Database.ROWS - 1; row >= 0; row--) {
            System.out.print((row + 1) + (row + 1 < 10 ? " |" : "|"));
            for (int col = 0; col < Database.COLS; col++) {
                String cell = getCellContent(row, col);
                System.out.print(cell + "|");
            }
            System.out.println("\n  +----+----+----+----+----+----+----+----+----+----+----+----+----+----+----+");
        }
        System.out.print("   ");    for (int colNum = 1; colNum <= Database.COLS; colNum++) {
        System.out.print(colNum < 10 ? "  " + colNum + "  " : " " + colNum + "  ");
    }
        System.out.println();

        // Отображение статуса инициативы
        if (Database.initiativeOrder.isEmpty()) {
            System.out.println("[INFO] Initiative not set - game in preparation.");
        } else {
            Unit currentUnit = Database.initiativeOrder.get(Database.currentTurnIndex);
            System.out.println("[INFO] Player " + currentUnit.playerOwnership + "'s turn - "
                    + currentUnit.type + " at position: "
                    + (currentUnit.location[0] + 1) + "," + (currentUnit.location[1] + 1) + "");
        }

      
        // Отображение информации о юнитах
        System.out.println("\nPlayer 1:");
        for (Unit unit : Database.player1Units) {
            System.out.println(unit.type + " HP:" + unit.health + " Position:" + (unit.location[0] + 1) + "," + (unit.location[1] + 1) + " Speed:" + unit.speed);
        }
        
        System.out.println("\nPlayer 2:");
        for (Unit unit : Database.player2Units) {
            System.out.println(unit.type + " HP:" + unit.health + " Position:" + (unit.location[0] + 1) + "," + (unit.location[1] + 1) + " Speed:" + unit.speed);
        }
    }

    public static String getCellContent(int row, int col) {
        if (Database.movementTrail[row][col] == 'x') {
            return "  x ";
        }
        for (Unit unit : Database.player1Units) {
            if (unit.location[0] == row && unit.location[1] == col) {
                return " " + getUnitSymbol(unit) + "1 ";
            }
        }
        for (Unit unit : Database.player2Units) {
            if (unit.location[0] == row && unit.location[1] == col) {
                return " " + getUnitSymbol(unit) + "2 ";
            }
        }
        return Database.board[row][col] == '#' ? "####" : "    ";
    }

    public static char getUnitSymbol(Unit unit) {
        switch (unit.type) {
            case ARCHER: return 'A';
            case SPEARMAN: return 'S';
            case SWORDSMAN: return 'K';
            case CAVALRY: return 'C';
            default: return '?';
        }
    }
}
