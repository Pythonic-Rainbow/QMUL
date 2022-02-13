public class Agent extends User {

    private static final int MAX_CLIENTS = 5;
    private int numClients;
    private Client[] clients = new Client[MAX_CLIENTS];

    public Agent(String name){
        super(name);
	    this.numClients = 0;
    }

    public void addClient(Client c) {
        if (numClients < MAX_CLIENTS) {
            clients[numClients] = c;
            numClients++;
        } else {
            System.err.println("This agent already has 5 clients!");
        }
    }

    public Client[] getClients() {
        return clients;
    }
}
