import entity.Spawner;
import entity.Vector;
import entity.character.Enemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class InputProcessor {
    static final HashSet<Character> inputs = new HashSet<>();
    private static final HashMap<Character, Runnable> actions = new HashMap<>();
    private static boolean removeE = false;

    static void init() {
        actions.put('w', new Runnable() {
            @Override
            public void run() {
                EMExorcist.player.y -= 2;
            }
        });
        actions.put('a', new Runnable() {
            @Override
            public void run() {
                EMExorcist.player.x -= 2;
            }
        });
        actions.put('s', new Runnable() {
            @Override
            public void run() {
                EMExorcist.player.y += 2;
            }
        });
        actions.put('d', new Runnable() {
            @Override
            public void run() {
                EMExorcist.player.x += 2;
            }
        });
        actions.put('q', new Runnable() {
            @Override
            public void run() {
                int range = EMExorcist.player.range;
                boolean rem = false;
                for (Enemy e : new ArrayList<>(EMExorcist.enemies)) {
                    if (inRange(e, range) && e.isElectric() == UniversePanel.viewE) {
                        EMExorcist.enemies.remove(e);
                        rem = true;
                    }
                }
                if (rem) EMExorcist.player.maxHP++;
                rem = false;
                for (Spawner s : new ArrayList<>(EMExorcist.spawners)) {
                    if (inRange(s, range / 5)) {
                        EMExorcist.spawners.remove(s);
                        rem = true;
                    }
                }
                if (rem) EMExorcist.player.range++;
            }
        });
        actions.put('e', new Runnable() {
            @Override
            public void run() {
                UniversePanel.viewE ^= true;
                removeE = true;
            }
        });
    }

    static void process() {
        try {
            for (char input : new ArrayList<>(inputs))
                actions.get(input).run();
        } catch (Exception ignored) {}

        if (removeE) {
            inputs.remove('e');
            removeE = false;
        }
    }

    private static boolean inRange(Vector v, int range) {
        return v.x >= EMExorcist.player.x - range &&
            v.x <= EMExorcist.player.x + range &&
            v.y >= EMExorcist.player.y - range &&
            v.y <= EMExorcist.player.y + range;
    }
}
