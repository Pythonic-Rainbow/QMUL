package entity.character;

import entity.Vector;

import java.awt.*;

public class Enemy extends Character {
    public final Vector vel = new Vector(0, 0);  // Velocity
    private Vector force;
    private final boolean electric;

    public Enemy(float x, float y, boolean isElectric) {
        super(x, y, isElectric ? "E" : "M", 20);
        electric = isElectric;
    }

    public void tick(Character player) {
        Vector posDelta = displacement(player);
        double r2 = posDelta.pythSqr();
        double f = 6.67408e-11 * mass * player.mass / r2;

        double angle = posDelta.angle();
        force = new Vector(f * Math.cos(angle), f * Math.sin(angle));
        vel.add(force);
        add(vel);
    }

    public void drawDebug(Graphics g) {
        Vector line = new Vector(x + force.x*1000, y + force.y*1000);
        g.setColor(Color.white);
        fillCircle(g, x, y, 20);
        g.drawLine(Math.round(x), Math.round(y), Math.round(line.x), Math.round(line.y));
    }

    public void paintGField(int i, Graphics g) {
        int color = 30*i;
        g.setColor(new Color(color, color, color));
        fillCircle(g, x, y, Math.sqrt(6.67408e-5*mass/i));
    }

    private static void fillCircle(Graphics g, float x, float y, double r) {
        x -= (r / 2);
        y -= (r / 2);
        g.fillOval(Math.round(x), Math.round(y), (int)r, (int)r);
    }

    public boolean isElectric() {
        return electric;
    }

    @Override
    public String toString() {
        return String.format("%s{pos=%s, vel=%s}", super.name, super.toString(), vel);
    }
}
