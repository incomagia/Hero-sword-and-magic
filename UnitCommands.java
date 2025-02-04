import java.util.*;

public class UnitCommands {

    public static char getUnitSymbol(Unit unit) {
        switch (unit.type) {
            case ARCHER: return 'A';
            case SPEARMAN: return 'S';
            case SWORDSMAN: return 'K';
            case CAVALRY: return 'C';
            default: return '?';
        }
    }

    public static Unit getUnitAtPosition(int[] position) {
        for (Unit unit : Database.player1Units) {
            if (Arrays.equals(unit.location, position)) return unit;
        }
        for (Unit unit : Database.player2Units) {
            if (Arrays.equals(unit.location, position)) return unit;
        }
        return null;
    }

    public static boolean moveUnit(int targetX, int targetY) {
        Unit unit = Database.initiativeOrder.get(Database.currentTurnIndex);
        int[] targetPos = {targetX - 1, targetY - 1};
        int[][] path = findOptimalPath(unit, targetPos);
        if (path.length == 0) return false;

        int maxSteps = unit.speed;
        int steps = Math.min(maxSteps, path.length);

        for (int i = 0; i < steps; i++) {
            int[] newPos = path[i];
            unit.location = newPos;
            Database.board[newPos[0]][newPos[1]] = getUnitSymbol(unit);
        }
        //printMovementTrail();
        return true;
    }

    public static boolean attackUnit(int targetX, int targetY) {
        Unit attacker = Database.initiativeOrder.get(Database.currentTurnIndex);
        int[] targetPos = {targetX - 1, targetY - 1};
        Unit target = getUnitAtPosition(targetPos);
        if (target == null) return false;

        int[] attackPos = findClosestAttackPosition(attacker, target);
        if (attackPos == null) return false;

        moveUnit(attackPos[0] + 1, attackPos[1] + 1);
        //printMovementTrail();

        if (isInRange(attacker, target)) {
            target.health -= attacker.attackDamage;
            if (target.health <= 0) {
                Database.board[target.location[0]][target.location[1]] = '0';
                if (target.playerOwnership == 1) Database.player1Units.remove(target);
                else Database.player2Units.remove(target);
            }
            return true;
        }
        return false;
    }

    public static void skipTurn() {
        // Просто фиксация пропуска хода
    }

    public static boolean isInRange(Unit attacker, Unit target) {
        int dx = Math.abs(attacker.location[0] - target.location[0]);
        int dy = Math.abs(attacker.location[1] - target.location[1]);
        return dx + dy <= attacker.attackRange;
    }

    public static int[] findClosestAttackPosition(Unit attacker, Unit target) {
        int minDistance = Integer.MAX_VALUE;
        int[] bestPosition = null;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        for (int[] dir : directions) {
            for (int i = 1; i <= attacker.attackRange; i++) {
                int newX = target.location[0] + dir[0] * i;
                int newY = target.location[1] + dir[1] * i;
                if (newX >= 0 && newX < Database.ROWS && newY >= 0 && newY < Database.COLS &&
                    Database.board[newX][newY] == '0') {
                    int distance = Math.abs(attacker.location[0] - newX) + Math.abs(attacker.location[1] - newY);
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestPosition = new int[]{newX, newY};
                    }
                }
            }
        }
        return bestPosition;
    }

    public static int[][] findOptimalPath(Unit unit, int[] targetPos) {
        int[][] distances = new int[Database.ROWS][Database.COLS];
        for (int[] row : distances) Arrays.fill(row, -1);
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{unit.location[0], unit.location[1], 0});
        distances[unit.location[0]][unit.location[1]] = 0;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int[] dir : directions) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];
                if (newRow >= 0 && newRow < Database.ROWS && newCol >= 0 && newCol < Database.COLS &&
                    Database.board[newRow][newCol] == '0' && distances[newRow][newCol] == -1) {
                    distances[newRow][newCol] = current[2] + 1;
                    queue.add(new int[]{newRow, newCol, current[2] + 1});
                    //Database.movementTrail[newRow][newCol] = 'x';
                }
            }
        }
        return reconstructPath(unit.location, targetPos, distances);
    }

    public static int[][] reconstructPath(int[] start, int[] target, int[][] distances) {
        List<int[]> path = new ArrayList<>();
        int[] current = target;
    
        // Вычисляем, сколько шагов юнит реально пройдет
        Unit currentUnit = Database.initiativeOrder.get(Database.currentTurnIndex);
        int totalPathLength = distances[target[0]][target[1]]; // Полная длина пути
        int stepsToSkip = totalPathLength - currentUnit.speed; // Сколько шагов пропустить (чтобы не закрасить юнита)
    
        while (distances[current[0]][current[1]] > 0) {
            path.add(current);
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    
            for (int[] dir : directions) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];
    
                if (newRow >= 0 && newRow < Database.ROWS && newCol >= 0 && newCol < Database.COLS) {
                    if (distances[newRow][newCol] == distances[current[0]][current[1]] - 1) {
                        current = new int[]{newRow, newCol};
    
                        if (stepsToSkip <= 0) {
                            Database.movementTrail[newRow][newCol] = 'x';
                        } else {
                            stepsToSkip--;
                        }
    
                        break;
                    }
                }
            }
        }
    
        Collections.reverse(path);
        return path.toArray(new int[path.size()][]);
    }



    public static void printMovementTrail() {
        System.out.println("\n[DEBUG] Movement Trail:");
        for (int i = 0; i < Database.ROWS; i++) {
            for (int j = 0; j < Database.COLS; j++) {
                System.out.print(Database.movementTrail[i][j] + " ");
            }
            System.out.println();
        }
    }
}
