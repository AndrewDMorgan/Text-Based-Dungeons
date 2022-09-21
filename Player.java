import java.util.ArrayList;
import java.util.Scanner;  // for player input

public class Player
{
    private int hand = 0;  // the first slot of five slots

    // the players position
    private int x = 2;
    private int y = 2;

    // the players' health (10 is default max)
    private int health = 10;

    // different stats
    private int glow = 4;
    private int regen = 5;  // turns before +1 heart (default is 5 so really slow)
    private int strength = 1;  // multiplier (attackDamage *= (float)strength/4)

    // 0; nothing, 1; wooden sword, 2; ancient torch, 3; ancient axe, 4; wooden spear, 5; wooden bow, 6; stone sword, 7; tiki torch, 8; stone spear
    final private Items[] allItems = {
            new Items("", ExtendedItems.ItemTypes.None),  // nothing item
            new ExtendedItems.Melee ("Wooden Sword" , ExtendedItems.ItemTypes.Melee , 2, 1),
            new ExtendedItems.Light ("Ancient Torch", ExtendedItems.ItemTypes.Light ,   5),  // one better light than normal player
            new ExtendedItems.Melee ("Ancient Axe"  , ExtendedItems.ItemTypes.Melee , 3, 1),
            new ExtendedItems.Melee ("Wooden Spear" , ExtendedItems.ItemTypes.Melee , 1, 2),
            new ExtendedItems.Ranged("Wooden Bow"   , ExtendedItems.ItemTypes.Ranged, 2, 6),
            new ExtendedItems.Melee ("Stone Sword"  , ExtendedItems.ItemTypes.Melee , 3, 1),
            new ExtendedItems.Light ("Tiki Torch"   , ExtendedItems.ItemTypes.Light ,   7),
            new ExtendedItems.Melee ("Stone Spear"  , ExtendedItems.ItemTypes.Melee , 2, 2)
    };

    // five empty slots
    private Items[] items = {allItems[0], allItems[0], allItems[0], allItems[0], allItems[0]};  // an empty list of items

    private Scanner scanner = new Scanner(System.in);

    // the constructor
    public Player()
    {

    }

    private boolean StringArContains(String[] l, String item)
    {
        for (int i = 0; i < l.length; i++)
        {
            if (l[i].equals(item)) return true;
        }
        return false;
    }

    private boolean Colision(Map map, int dx, int dy)
    {
        if (map.CheckBounds(x + dx, y + dy, true))
        {
            int tile = map.GetTile(x + dx, y + dy);
            for (int i = 0; i < map.collisionTiles.length; i++)
            {
                if (map.collisionTiles[i] == tile) return false;
            }
            return true;
        }
        return false;
    }

    public void Update(Map map)  // updates the player (controls the user interface to)
    {
        // getting the players input for direction
        System.out.print("south, east, north, west\nwasd\nDirection >> ");
        String dir = scanner.nextLine();
        // getting the players action type (move, attack, interact, ect..)
        System.out.print("\ne - use\nm - move\na - attack\ni - inventory\n1-5 - Hand\nAction >> ");
        String act = scanner.nextLine();

        // the directional based change in position for the x and y (delta x and delta y)
        int dx = 0;
        int dy = 0;

        // getting the direction inputted if inputted
        dir = dir.toLowerCase();
        if (StringArContains(new String[] {"north", "w"}, dir)) dy = -1;
        if (StringArContains(new String[] {"east" , "d"}, dir)) dx =  1;
        if (StringArContains(new String[] {"south", "s"}, dir)) dy =  1;
        if (StringArContains(new String[] {"west" , "a"}, dir)) dx = -1;

        // checking for movement
        if (act.equalsIgnoreCase("m"))
        {
            // attempting to move the player
            if (Colision(map,  dx, dy))
            {
                // moving the player
                x += dx;
                y += dy;
            }
        }
        try  // checking if a number was chosen (non int string to int throws an error)
        {
            int newHand = Integer.parseInt(act);
            // checking if the number is within the range of the inventory
            if (newHand <= 5 && newHand >= 1) hand = newHand - 1;
        }
        catch (Exception e) {}  // does nothing sense the action being checked was not found

        // checking if interacting
        if (act.equalsIgnoreCase("e"))
        {
            // checking for a chest
            boolean isChest = map.CheckChest(x + dx, y + dy);

            // checking if a chest was interacted with
            if (isChest)
            {
                // getting the chests items
                Items[] chestItems = map.GetChestLoot(this, x + dx, y + dy);
                // adding every item from the chest to the player
                for (int i = 0; i < chestItems.length; i++)
                {
                    // adding the item to the inventory or dropped items
                    AddLoot(chestItems[i], map, x + dx, y + dy);
                }
            }
        }
        // checking if the inventory is being viewed
        else if (act.equalsIgnoreCase("i"))
        {
            // showing all items (for now just the names)
            for (int i = 0; i < 5; i++)
            {
                if (!items[i].GetName().equals(""))
                {
                    System.out.println((i+1) + ": " + items[i].GetName());
                }
            }

            boolean inInventory = true;  // if the player is in the inventory
            // looping while the player is messing with the inventory
            while (inInventory)
            {
                // showing the actions that can be done
                System.out.print("q - drop\nd - delete/remove\nm - move item\n > ");
                String usrAct = scanner.nextLine().toLowerCase();

                // checking for an action
                if (StringArContains(new String[] {"q", "d", "m"}, usrAct))
                {
                    // finding the slot for the action to take place in
                    System.out.println("Slot number 1 - 5");
                    String slotNumChoice = scanner.nextLine();

                    // the slot inputted
                    int slot;

                    try  // checking if a number was chosen (non int string to int throws an error)
                    {
                        slot = Integer.parseInt(slotNumChoice) - 1;
                        // checking if the number is within the range of the inventory
                        if (slot > 4 || slot < 0) throw new Exception();

                        if      (usrAct.equals("q"))
                        {
                            map.AddDroppedItem(items[slot], x, y);  // adding a new dropped item
                            items[slot] = allItems[0].Copy();  // removing the item from the inventory
                        }
                        else if (usrAct.equals("d")) {items[slot] = allItems[0].Copy();}  // removing an item from the inventory
                        else if (usrAct.equals("m"))  // moving/swaping items
                        {
                            // checking the second index
                            try
                            {
                                // getting the second slot
                                System.out.println("Slot number 1 - 5");
                                slotNumChoice = scanner.nextLine();

                                int slot2 = Integer.parseInt(slotNumChoice) - 1;
                                if (slot2 > 4 || slot2 < 0) throw new Exception();  // checking if its within bounds

                                // getting the two items
                                Items i1 = items[slot ];
                                Items i2 = items[slot2];

                                // swapping the items
                                items[slot ] = i2;
                                items[slot2] = i1;
                            }
                            catch (Exception e) {}  // does nothing
                        }
                    }
                    catch (Exception e) {}  // does nothing sense the action being checked was not found
                }
                else inInventory = false;  // ends the loop
            }
        }

        // checking if the player should pick up any items
        ArrayList<Map.DroppedItems> droppedItems = map.GetDroppedItems();
        // looping through the dropped items
        for (int i = 0; i < droppedItems.size(); i++)
        {
            Map.DroppedItems item = droppedItems.get(i);
            // checking if the player is standing on the item
            if (item.x == x && item.y == y)
            {
                boolean added = AddLoot(item.item, map, x, y);  // adding the item to the inventory or back to the ground
                if (added) map.RemoveDroppedItem(i);  // removing the item
                else break;  // stopping early since no room in inventory
            }
        }

        // clearing the previous frame
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    // attacks the player
    public void Attack(int attackDamage)
    {
        // hurts the player based on armor and maybe potions (idk anything about potions yet)
    }

    // gets an item from the inventory at the given index
    public Items GetItem(int i) {return items[i];}

    // gets the item currently being held
    public Items GetHand() {return items[hand];}

    // gets the players health
    public int GetHealth() {return health;}
    
    // gets the position of the player
    public int GetX() {return x;}
    public int GetY() {return y;}

    // gets the stats (mostly for rings)
    public int GetGlow () {return glow ;}
    public int GetRegen() {return regen;}
    public int GetStrength() {return strength;}

    // sets the stats (mostly for rings)
    public void SetGlow (int glow ) {this.glow  = glow ;}
    public void SetRegen(int regen) {this.regen = regen;}
    public void SetStrength(int strength) {this.strength = strength;}

    // gets the item with the given id
    public Items IDGetItem(int id) {return allItems[id];}

    // adds an item to the inventory
    public boolean AddLoot(Items item, Map map, int x, int y)  // adds the item and returns true or if inventory full returns false
    {
        // looping through all slots
        for (int i = 0; i < 5; i++)
        {
            // checking if the slot is empty
            if (items[i].GetType() == ExtendedItems.ItemTypes.None)
            {
                // adding the item
                items[i] = item;
                return true;
            }
        }
        map.AddDroppedItem(item, x, y);  // dropping the item as there was no space
        return false;
    }

}
