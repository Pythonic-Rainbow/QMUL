package Jan2022;

import java.util.Scanner;

public class Vote {
    static class User {
        String name;
        int score;
    }

    public static String getUserName(User u) {return u.name;}
    public static User setUserName(User u, String name) {
        u.name = name;
        return u;
    }
    public static int getUserScore(User u) {return u.score;}
    public static User setUserScore(User u, int score) {
        u.score = score;
        return u;
    }

    public static void main(String[] args) {
        User[] users = new User[10];

        // Set names
        for (int i = 0; i < 10; i++) {
            users[i] = new User();
            String name = readString(String.format("Name %d? ", i+1));
            setUserName(users[i], name);
        }

        // Display vote options
        System.out.print("Voting: ");
        for (int i = 0; i < 10; i++) {
            System.out.printf("%d=%s ", i+1, getUserName(users[i]));
        }
        System.out.println();

        // Get user votes
        for (User user : users) {
            int target = 0;
            while (target < 1 || target > 10) { // Ensures that input is in range
                target = readInt(getUserName(user) + " who do you vote for? ");
            }
            int ogScore = getUserScore(users[target-1]);
            setUserScore(users[target-1], ogScore + 1);
        }

        sort(users);
        // Display winners
        System.out.print("Winner(s): " + getUserName(users[0]));
        for (int i = 1; i < 10; i++) {
            if (getUserScore(users[i]) < getUserScore(users[0])) break;
            System.out.print(" " + getUserName(users[i]));
        }
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return new Scanner(System.in).nextLine();
    }

    public static int readInt(String prompt) {
        System.out.print(prompt);
        return new Scanner(System.in).nextInt();
    }

    // Bubble sort
    public static void sort(User[] users) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9 - i; j++) {
                if (getUserScore(users[j]) < getUserScore(users[j+1])) {
                    User temp = users[j];
                    users[j] = users[j+1];
                    users[j+1] = temp;
                }
            }
        }
    }
}
