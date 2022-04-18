package entity;

import entity.character.Enemy;

import java.util.concurrent.ThreadLocalRandom;

public class Spawner extends Entity {
    public Spawner(float x, float y) {
        super(x, y, "S");
    }

    public Enemy spawn(int size) {
        if (ThreadLocalRandom.current().nextFloat() < 1f/size)
            return new Enemy(
                    x - 25 + ThreadLocalRandom.current().nextFloat() * 50,
                    y - 25 + ThreadLocalRandom.current().nextFloat() * 50,
                    ThreadLocalRandom.current().nextFloat() < 0.5
            );
        return null;
    }
}
