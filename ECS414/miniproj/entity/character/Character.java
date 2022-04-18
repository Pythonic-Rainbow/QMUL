package entity.character;

import entity.Entity;

class Character extends Entity {
    public final float mass;

    Character(float x, float y, String name, float mass) {
        super(x, y, name);
        this.mass = mass * 1000000;
    }
}
