package entity;

import java.awt.*;

public class Entity extends Vector{
    protected final String name;

    public Entity(float x, float y, String name) {
        super(x, y);  // Coordinates
        this.name = name;
    }


    public void draw(Graphics g) {
        g.drawString(name, Math.round(x), Math.round(y));
    }
}
