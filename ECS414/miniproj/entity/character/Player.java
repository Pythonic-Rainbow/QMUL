package entity.character;

public class Player extends Character {
    public int hp;
    public int maxHP;
    public int range;

    public Player(int x, int y) {
        super(x, y, "U", 1);
    }

    @Override
    public String toString() {
        return String.format("Player{pos=[%.0f %.0f] maxHP=%d hp=%d range=%d}", x, y, maxHP, hp, range);
    }
}
