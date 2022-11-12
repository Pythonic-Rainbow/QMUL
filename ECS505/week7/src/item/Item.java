package item;

public abstract class Item {
    private ItemState state = ItemState.LOANABLE;
    private int itemId;

    public ItemState getState() {
        return state;
    }

    public Item(int itemId) {
        this.itemId = itemId;
    }
}
