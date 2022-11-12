package user;

import item.Item;

public class Member extends User{
    private Address[] addresses = new Address[7];
    private String userName;
    private String firstName;
    private String lastName;

    public Member(String userName) {
        this.userName = userName;
    }

    public void loan(Item item) {

    }
}
