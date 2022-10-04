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

    // prints the basic info
    public void PrintInfo() {if (!name.equals("")) System.out.println(name + "\n");}

    // gets the name of the item
    public String GetName() {return name;}

    public float Attacked() {return 0;}  // does nothing sense this item would have no set defense

    public void Attack(Player player, BaseMob[] mobs, int strength, int dx, int dy) {}  // does nothing sense this item would have no set attack

    public void ActivateBuff() {}  // does nothing sense it isn't a buff

    public int GetLight() {return 0;}   // does nothing sense this item would have no set lighting

    public ExtendedItems.ItemTypes GetType() {return itemType;}  // gets the items type

    // attacks any nearby mobs
    public void Attack(Player player, BaseMob[] mobs, int dx, int dy) {};

    // copies the item
    public Items Copy() {return new Items(name, itemType);}
}



