import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private final int width;
    private final JLabel status = new JLabel();
    private static final UniversePanel universe = new UniversePanel();

    Frame(int width, int height) {
        super("Electromagnetic Exorcist - ©2022 Howard Wong");
        this.width = width;

        init(height);
        addComponents();
        registerInputs();
    }

    void updateStatus(String status) {
        this.status.setText(status);
    }

    void displayLore() {
        displayDialog("Lore",
                "Biology blames illnesses on bacterias and viruses.\n" +
                        "You however can see cursed spirits travelling with Electromagnetic field.\n" +
                        "You were trained as an exorcist to eliminate such spirits.\n\n" +
                        "You are sent to different dimensions that contain \"Spawners\",\n" +
                        "abnormalities in the EM field that produce cursed spirits.\n\n" +
                        "Your goal is to destroy all spawners in the dimensions.");
    }

    void displayKeyBinds() {
        displayDialog(
                "Key binds",
                "Use arrow keys to navigate through the dimension.\n" +
                        "Press Q to eliminate spirits around you.\n" +
                        "Press E to switch to another eye (Observe electric/magnetic spirits)"
        );
    }

    void displayDialog(String title, String msg) {
        EMExorcist.block = true;
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
        EMExorcist.block = false;
    }

    void setDeadPanel() {
        universe.setBackground(Color.red);
    }

    private void init(int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addComponents() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.X_AXIS));
        JButton btnDebug = new JButton("GForce");
        btnDebug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                EMExorcist.isDebug ^= true;
            }
        });
        JButton btnLore = new JButton("Lore");
        btnLore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                Frame.this.displayLore();
            }
        });
        JButton btnKeyBind = new JButton("Keybinds");
        btnKeyBind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                Frame.this.displayKeyBinds();
            }
        });
        menu.setSize(width, 25);
        menu.add(btnDebug);
        menu.add(btnLore);
        menu.add(btnKeyBind);
        menu.add(status);

        add(menu);
        add(universe);
    }

    private void registerInputs() {
        addInputMap("pressed UP", "addW");
        addInputMap("released UP", "relW");
        addInputMap("pressed LEFT", "addA");
        addInputMap("released LEFT", "relA");
        addInputMap("pressed DOWN", "addS");
        addInputMap("released DOWN", "relS");
        addInputMap("pressed RIGHT", "addD");
        addInputMap("released RIGHT", "relD");
        addInputMap("released Q", "Q");
        addInputMap("released E", "E");
        addActionMap("addW", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.add('w');
            }
        });
        addActionMap("relW", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.remove('w');
            }
        });
        addActionMap("addA", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.add('a');
            }
        });
        addActionMap("relA", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.remove('a');
            }
        });
        addActionMap("addS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.add('s');
            }
        });
        addActionMap("relS", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.remove('s');
            }
        });
        addActionMap("addD", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.add('d');
            }
        });
        addActionMap("relD", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.remove('d');
            }
        });
        addActionMap("Q", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.add('q');
            }
        });
        addActionMap("E", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputProcessor.inputs.add('e');
            }
        });
    }

    private void addInputMap(String key, String action) {
        universe.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), action);
    }

    private void addActionMap(String action, AbstractAction a) {
        universe.getActionMap().put(action, a);
    }
}
