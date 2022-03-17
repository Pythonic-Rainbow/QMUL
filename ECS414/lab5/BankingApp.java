/**
 * ECS414U - Object Oriented Programming
 * Queen Mary University of London, 2021/22.
 * <p>
 * Week 5 lab session.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingApp extends Frame {

    /*
     * We will use this to print messages to the user.
     */
    private static TextArea infoArea = new TextArea("BankingApp 0.5");

    public static void print(String text) {
        infoArea.setText(text);
    }
    //---


    private Agent agent;
    private Panel clientButtonsPanel;


    /**
     * This method prints the names of all clients.
     */
    public void printClients() {
        String text = agent.getListOfClientNames();
        print(text);
    }

    /**
     * This method prints the information of the client with the given index.
     */
    public void printClientInfo(int index) {
        String text = agent.getClientInfo(index);
        print(text);
    }

    /**
     * This method takes all the necessary steps when a client is added. 
     */
    public void addClient(String name) {
        agent.addClient(new Client(name));

        int numClients = agent.getNumberOfClients();
        Button btn = new Button("Client " + numClients);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printClientInfo(numClients - 1);
            }
        });
        clientButtonsPanel.add(btn);
        this.setVisible(true); // Just to refresh the frame, so that the button shows up
    }

    public BankingApp() {

        this.agent = new Agent();
        this.setLayout(new FlowLayout());

        // Make this button work
        Button reportButton = new Button("Print client list");
		reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printClients();
            }
        });
        this.add(reportButton);

        // Make this button work
        Button addClientButton = new Button("Add client");
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Prompt acp = new Prompt();
                JTextField text = new JTextField();

                acp.add(new JLabel("Enter client name"));
                acp.add(text);
                acp.addSubmitListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addClient(text.getText());
                    }
                });
                acp.activate();
            }
        });

        this.add(addClientButton);

        // Make this button work
        Button depositButton = new Button("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField();
                JTextField amountField = new JTextField();


                Prompt prompt = new Prompt();
                prompt.add(new JLabel("Name:"));
                prompt.add(nameField);
                prompt.add(new JLabel("Deposit amount:"));
                prompt.add(amountField);

                prompt.addSubmitListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        int amount = Integer.parseInt(amountField.getText());
                        print(agent.deposit(name, amount) ? "Success" : "Failure");
                    }
                });

                prompt.activate();

            }
        });
        this.add(depositButton);

        // Make this button work
        Button withdrawButton = new Button("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nameField = new JTextField();
                JTextField amountField = new JTextField();


                Prompt prompt = new Prompt();
                prompt.add(new JLabel("Name:"));
                prompt.add(nameField);
                prompt.add(new JLabel("Deposit amount:"));
                prompt.add(amountField);

                prompt.addSubmitListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        int amount = Integer.parseInt(amountField.getText());
                        print(agent.deposit(name, -amount) ? "Success" : "Failure");
                    }
                });

                prompt.activate();

            }
        });
        this.add(withdrawButton);

        Button removeButton = new Button("Remove client");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField indexField = new JTextField();

                Prompt prompt = new Prompt();
                prompt.add(new JLabel("Enter client ID to remove:"));
                prompt.add(indexField);

                prompt.addSubmitListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int i = Integer.parseInt(indexField.getText());
                        agent.removeClient(i-1);
                        clientButtonsPanel.remove(agent.getNumberOfClients());
                    }
                });

                prompt.activate();
            }
        });
        this.add(removeButton);


        // Output console
        infoArea.setEditable(false);
        this.add(infoArea);

        // Client button panel
        clientButtonsPanel = new Panel();
        clientButtonsPanel.setLayout(new GridLayout(0,1));
        clientButtonsPanel.setVisible(true);
        this.add(clientButtonsPanel);


        // We add a couple of clients of testing purposes
        this.addClient("Alice Alison");
        this.addClient("Bob Robertson");

        // This is just so the X button closes our app
        WindowCloser wc = new WindowCloser();
        this.addWindowListener(wc);

        this.setSize(500, 500);// Self explanatory
        this.setLocationRelativeTo(null); // Centers the window on the screen
        this.setVisible(true);// Self explanatory

    }

    public static void main(String[] args) {
        new BankingApp();
    }
}
