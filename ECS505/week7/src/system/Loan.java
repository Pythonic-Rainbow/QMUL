package system;

import item.Item;
import user.Member;

import java.util.Calendar;

public class Loan {
    private Item item;
    private Member member;
    private Calendar loanDate;
    private Calendar dueDate;

    public Loan(Item item, Member member, Calendar dueDate) {
        this.item = item;
        this.member = member;
        this.dueDate = dueDate;
    }
}
