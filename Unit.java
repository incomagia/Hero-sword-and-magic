import java.util.Map;

public class Unit {
    public enum UnitType {
        ARCHER, SPEARMAN, SWORDSMAN, CAVALRY
    }

    public UnitType type;
    public int speed;
    public int attackDamage;
    public int health;
    public int cost;
    public boolean isAlive;
    public int attackRange;
    public int[] location; // [row, col] coordinates
    public int playerOwnership; // 1 for Player 1, 2 for Player 2

    // Constructor
    public Unit(UnitType type, int speed, int attackDamage, int health, int cost, int attackRange, int[] location, int playerOwnership) {
        this.type = type;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.health = health;
        this.cost = cost;
        this.isAlive = true; // All units start as alive
        this.attackRange = attackRange;
        this.location = location;
        this.playerOwnership = playerOwnership;
    }
    public static final Map<UnitType, Integer> baseSpeeds = Map.of(
        UnitType.ARCHER, 3,
        UnitType.SPEARMAN, 4,
        UnitType.SWORDSMAN, 5,
        UnitType.CAVALRY, 8
    );
    
    public static final Map<UnitType, Integer> baseAttacks = Map.of(
        UnitType.ARCHER, 10,
        UnitType.SPEARMAN, 15,
        UnitType.SWORDSMAN, 12,
        UnitType.CAVALRY, 10
    );
    
    public static final Map<UnitType, Integer> baseHp = Map.of(
        UnitType.ARCHER, 30,
        UnitType.SPEARMAN, 40,
        UnitType.SWORDSMAN, 50,
        UnitType.CAVALRY, 35
    );
    
    public static final Map<UnitType, Integer> unitCosts = Map.of(
        UnitType.ARCHER, 50,
        UnitType.SPEARMAN, 60,
        UnitType.SWORDSMAN, 70,
        UnitType.CAVALRY, 100
    );
    
    public static final Map<UnitType, Integer> attackRanges = Map.of(
        UnitType.ARCHER, 5,  // Дальний бой
        UnitType.SPEARMAN, 1,
        UnitType.SWORDSMAN, 1,
        UnitType.CAVALRY, 1
    );

    // Factory method for creating units with default values
    public static Unit createUnit(UnitType type, int[] location, int playerOwnership) {
        switch (type) {
            case ARCHER:
                return new Unit(UnitType.ARCHER, 3, 10, 30, 50, 5, location, playerOwnership);
            case SPEARMAN:
                return new Unit(UnitType.SPEARMAN, 4, 15, 40, 60, 1, location, playerOwnership);
            case SWORDSMAN:
                return new Unit(UnitType.SWORDSMAN, 5, 12, 50, 70, 1, location, playerOwnership);
            case CAVALRY:
                return new Unit(UnitType.CAVALRY, 8, 10, 35, 100, 1, location, playerOwnership);
            default:
                throw new IllegalArgumentException("Unknown unit type");
        }
    }

    @Override
    public String toString() {
        return "Unit{" +
                "type=" + type +
                ", speed=" + speed +
                ", attackDamage=" + attackDamage +
                ", health=" + health +
                ", cost=" + cost +
                ", isAlive=" + isAlive +
                ", attackRange=" + attackRange +
                ", location=[" + location[0] + ", " + location[1] + "]" +
                ", playerOwnership=" + playerOwnership +
                '}';
    }
}
