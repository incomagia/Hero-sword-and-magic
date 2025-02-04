import java.util.ArrayList;
import java.util.List;

public class Database {
    
    // Размеры поля
    public static final int ROWS = 15;
    public static final int COLS = 15;
    
    // Список инициативы (очередность ходов)
    public static List<Unit> initiativeOrder = new ArrayList<>();
    
    // Индекс текущего юнита, который сейчас ходит
    public static int currentTurnIndex = 0;

    // Массив для отображения следов передвижения юнитов
    public static char[][] movementTrail = new char[ROWS][COLS];
    
    // Поле: 0 - пусто, # - камень, A/S/K/C - юниты
    public static char[][] board = new char[ROWS][COLS];

    // Списки юнитов для каждого игрока
    public static List<Unit> player1Units = new ArrayList<>();
    public static List<Unit> player2Units = new ArrayList<>();

    static {
        generateField();
        generateUnits();
    }

    public static void generateField() {
        // Инициализация массива следов передвижения юнитов
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                movementTrail[row][col] = '0';
            }
        }
        // Инициализация поля пустыми клетками
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = '0';
            }
        }
        // Добавляем камни
        board[3][3] = '#';
        board[4][7] = '#';
        board[10][10] = '#';
    }

    public static void generateUnits() {
        // Добавление юнитов игрока 1
        player1Units.add(new Unit(Unit.UnitType.ARCHER, 3, 10, 30, 50, 5, new int[]{10, 9}, 1));
        //player1Units.add(new Unit(Unit.UnitType.SPEARMAN, 4, 15, 40, 60, 1, new int[]{10, 9}, 1));

        // Добавление юнитов игрока 2
        //player2Units.add(new Unit(Unit.UnitType.SWORDSMAN, 5, 12, 50, 70, 1, new int[]{14, 13}, 2));
        player2Units.add(new Unit(Unit.UnitType.CAVALRY, 8, 10, 35, 100, 1, new int[]{7, 9}, 2));

        // Размещение юнитов на поле
        for (Unit unit : player1Units) {
            board[unit.location[0]][unit.location[1]] = getUnitSymbol(unit);
        }
        for (Unit unit : player2Units) {
            board[unit.location[0]][unit.location[1]] = getUnitSymbol(unit);
        }
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
    
    public static List<Unit> getAllUnits() {
        List<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(player1Units);
        allUnits.addAll(player2Units);
        return allUnits;
    }

    
    public static void clearMovementTrail() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                movementTrail[row][col] = '0'; // Очищаем следы движения
            }
        }
    }
}
