import entity.Spawner;
import entity.character.Enemy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UniversePanel extends JPanel {
    static boolean viewE = true;

    UniversePanel() {
        setOpaque(true);
        setBackground(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Enemy e : EMExorcist.enemies) {
            if (e.isElectric() == viewE)
                enemies.add(e);
        }


        if (EMExorcist.isDebug) {
            for (int i = 1; i < 6; i++) {
                for (Enemy e : enemies) {
                    e.paintGField(i, g);
                }
            }
            for (Enemy e : enemies) {
                e.drawDebug(g);
            }
        }
        g.setColor(viewE ? Color.blue : Color.red);
        for (Enemy e : enemies) {
            e.draw(g);
        }
        g.setColor(Color.yellow);
        for (Spawner s : EMExorcist.spawners) {
            s.draw(g);
        }
        g.setColor(Color.green);
        EMExorcist.player.draw(g);

        if (InputProcessor.inputs.contains('q')) {
            g.setColor(viewE ? Color.red : Color.blue);
            int range = EMExorcist.player.range;
            g.drawRect(Math.round(EMExorcist.player.x - range), Math.round(EMExorcist.player.y - range), range*2, range*2);
            g.drawRect(Math.round(EMExorcist.player.x - range/5f), Math.round(EMExorcist.player.y - range/5f), range*2/5, range*2/5);
            InputProcessor.inputs.remove('q');
        }

    }
}
