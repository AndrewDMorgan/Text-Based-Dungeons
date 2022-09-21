public class Items
{
    private String name;  // the name of the item
    private ExtendedItems.ItemTypes itemType;  // the type of item

    // the items constructor
    public Items(String name, ExtendedItems.ItemTypes itemType)
    {
        this.name = name;
        this.itemType = itemType;
    }

    // gets the name of the item
    public String GetName() {return name;}

    public float Attacked() {return 0;}  // does nothing sense this item would have no set defense

    public void Attack(Player player, BaseMob[] mobs) {}  // does nothing sense this item would have no set attack

    public int GetLight() {return 0;}   // does nothing sense this item would have no set lighting

    public ExtendedItems.ItemTypes GetType() {return itemType;}  // gets the items type

    // copies the item
    public Items Copy() {return new Items(name, itemType);}
}



