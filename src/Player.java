/**
 * Player
 *
 * Player holds the current game statistics for a player.
 *
 */

public class Player {
    private static final int DEFAULT_HEALTH = 1;
    private static final double DEFAULT_BREATH = 0;

    private int health;
    private double breath;
    private int defense;

    // constructors

    public Player() {
        this(DEFAULT_HEALTH, DEFAULT_BREATH);
    }

    public Player(int health, double breath) {
        this.health = health;
        this.breath = breath;
        this.defense = 0;  // default defense is zero
    }

    // getters

    public int getHealth() {
        return health;
    }

    public double getBreath() {
        return breath;
    }

    /**
     * Return if player is dead.
     * @return
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Reset defense at start of turn.
     */
    public void resetDefense() {defense = 0;}


    /**
     * Check the validity of move and apply its effect.
     * @param move attempted move
     * @return true if is valid, false otherwise.
     */
    public boolean handleMove(String move) {
        if (move.equals("attack")) {
            if (breath >= 1) {  // attack costs breath
                breath--;
                defense++;
                return true;
            } else {
                System.out.println("Not enough breath!");
                return false;
            }
        } else if (move.equals("defend")) {
            defense++;
            return true;
        } else if (move.equals(("breathe"))) {
            breath++;
            return true;
        } else {
            System.out.println("Invalid input; please choose between defend, attack, and breathe.");
            return false;
        }
    }

    /**
     * Apply damage according to enemy move
     * @param move
     */
    public void processOpponent(String move) {
        if (move.equals("attack")) {
            health -= Math.max(1 - defense, 0);
        }
    }

}
