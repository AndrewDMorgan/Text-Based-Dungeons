public class Items
{
    private String name;  // the name of the item
    private ExtendedItems.ItemTypes itemType;  // the type of item

    // the items enchantments
    private ExtendedItems.Enchants enchants;

    // the items constructor
    public Items(String name, ExtendedItems.ItemTypes itemType)
    {
        // initializing the parameters
        this.name = name;
        this.itemType = itemType;

        // creating the enchantment manager for the item
        enchants = new ExtendedItems.Enchants(itemType);
    }

    // prints the basic info
    public void PrintInfo() {if (!name.equals("")) System.out.println(name + "\n" + enchants.GetInfo());}

    // gets the name of the item
    public String GetName() {return name;}

    // gets the enchantment manager
    public ExtendedItems.Enchants GetEnchantmentManager() {return enchants;}

    public float Attacked() {return 0;}  // does nothing sense this item would have no set defense

    public void Attack(Player player, BaseMob[] mobs, int strength, int dx, int dy) {}  // does nothing sense this item would have no set attack

    public void ActivateBuff() {}  // does nothing sense it isn't a buff

    public int GetLight() {return enchants.GetGlow(5);}   // returns whatever light level is assigned from any enchants (0 if no enchantments)

    public ExtendedItems.ItemTypes GetType() {return itemType;}  // gets the items type

    // attacks any nearby mobs
    public void Attack(Player player, BaseMob[] mobs, int dx, int dy) {};

    // copies the item
    public Items Copy() {return new Items(name, itemType);}
}

