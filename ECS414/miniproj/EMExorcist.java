import entity.Spawner;
import entity.Vector;
import entity.character.Enemy;
import entity.character.Player;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class EMExorcist {
    static boolean isDebug = false; // Whether to display field lines or not
    final static Player player = new Player(750, 400);
    static ArrayList<Enemy> enemies = new ArrayList<>(); // Enemies in the game
    static ArrayList<Spawner> spawners = new ArrayList<>(); // Spawners in the game
    static boolean block = false;
    static int dim = 1;

    public static void main(String[] args) {
        Frame frame = new Frame(1500, 800);

        // Tries to read data from file
        File file = new File("data.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            player.hp  = player.maxHP = Integer.parseInt(br.readLine());
            player.range = Integer.parseInt(br.readLine());
        }  catch (IOException e) { // Default stats
            player.hp = player.maxHP = 200;
            player.range = 50;
            frame.displayLore();
            frame.displayKeyBinds();
        }

        InputProcessor.init(); // Register callbacks for the Input Processor

        // Tries to play BGM
        try {
            String soundName = "CT01.wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(-1);
        } catch (Exception e) {
            frame.displayDialog("Error", "Unable to play BGM! Have you plugged in your headphones?");
        }

        // Gameplay loop
        boolean gameOver = false;
        while (!gameOver) {
            player.hp = player.maxHP; // Restores player health

            // Sets spawners and enemies
            spawners = new ArrayList<>();
            enemies = new ArrayList<>();
            for (int i = 0; i < (player.hp/100*(player.range/10-4)); i++) {
                spawners.add(new Spawner(
                        100 + ThreadLocalRandom.current().nextFloat()*1300,
                        100 + ThreadLocalRandom.current().nextFloat()*600
                ));
            }

            // Render loop
            boolean finish = false;
            while (!finish) {
                while (block) {
                    // Freezes the entire program
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException ignored) {}
                }

                InputProcessor.process();

                // Spawns enemies
                for (Spawner s : spawners)
                    if (enemies.size() < player.hp * player.range) {
                        Enemy e = s.spawn(spawners.size());
                        if (e != null) enemies.add(e);
                    }

                // Ticks each enemy and remove if out of range
                for (Enemy e : new ArrayList<>(enemies)) {
                    e.tick(player);
                    if (e.x < 0 || e.x > 1500 || e.y < 0 || e.y > 800)
                        enemies.remove(e);
                }

                // Update frame
                frame.updateStatus(String.format("%s Enemies: %d Spawners: %d Dimension: %d",
                        player, enemies.size(), spawners.size(), dim
                ));
                frame.repaint();

                // Frame delay
                try {
                    TimeUnit.MILLISECONDS.sleep(15);
                } catch (InterruptedException ignored) {}

                // Deducts HP if enemy hits player
                for (Enemy e : new ArrayList<>(enemies)) {
                    final int rHit = 5;
                    Vector hitBox = e.displacement(player);
                    if (Math.abs(hitBox.x) < rHit && Math.abs(hitBox.y) < rHit)
                        player.hp--;
                }

                // Is round finish / player dead?
                finish = (spawners.size() == 0 && enemies.size() == 0);
                if (player.hp <= 0) {
                    frame.displayDialog("GAME OVER", "You are drown in a ridiculous amount of cursed spirits.");
                    gameOver = finish = true;
                    frame.setDeadPanel();
                }

            }

            // Saves file if player is not dead
            if (!gameOver) {
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(String.format("%d\n%d", player.maxHP, player.range));
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Can't write to file!!!");
                    e.printStackTrace();
                }
                dim++;
            }
        }

        // Deletes file if player is dead
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }
}
