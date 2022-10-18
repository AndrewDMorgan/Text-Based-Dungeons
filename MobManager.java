import java.util.ArrayList;  // for dropped items
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
    private int glow = 5;  // base is 5
    private int strength = 4;  // multiplier (attackDamage *= (float)strength/4)
    private int defense = 4;  // a divisor (every +4 is 1 grater dividend)

    private boolean readyNextLevel = false;

    // 0; nothing, 1; wooden sword, 2; ancient torch, 3; ancient axe, 4; wooden spear, 5; wooden bow, 6; stone sword, 7; tiki torch, 8; stone spear, 9; Rusty Sword, 10; cross bow
    private final Items[] allItems = {
            new Items("", ExtendedItems.ItemTypes.None),  // nothing item
            new ExtendedItems.Melee ("Wooden Sword" , ExtendedItems.ItemTypes.Melee , 2, 1),
            new ExtendedItems.Light ("Ancient Torch", ExtendedItems.ItemTypes.Light ,   6),  // one better light than normal player
            new ExtendedItems.Melee ("Ancient Axe"  , ExtendedItems.ItemTypes.Melee , 3, 1),
            new ExtendedItems.Melee ("Wooden Spear" , ExtendedItems.ItemTypes.Melee , 1, 2),
            new ExtendedItems.Ranged("Wooden Bow"   , ExtendedItems.ItemTypes.Ranged, 1, 6, 1),
            new ExtendedItems.Melee ("Stone Sword"  , ExtendedItems.ItemTypes.Melee , 3, 1),
            new ExtendedItems.Light ("Tiki Torch"   , ExtendedItems.ItemTypes.Light ,   8),
            new ExtendedItems.Melee ("Stone Spear"  , ExtendedItems.ItemTypes.Melee , 2, 2),
            new ExtendedItems.Melee ("Rusty Sword"  , ExtendedItems.ItemTypes.Melee , 1, 1),
            new ExtendedItems.Ranged("Cross Bow"    , ExtendedItems.ItemTypes.Ranged, 2, 10, 2),

            // 11; health buff, 12; glow buff, 13; strength buff, 14; defense buff, 15; defender buff (strength + defense)
            new ExtendedItems.Buff  ("Health Buff"  , ExtendedItems.ItemTypes.Buff  , this,
                    new ExtendedItems.Buff.Effects[] {ExtendedItems.Buff.Effects.Health},new int[] {4}),
            new ExtendedItems.Buff  ("Glow Buff"    , ExtendedItems.ItemTypes.Buff  , this,
                    new ExtendedItems.Buff.Effects[] {ExtendedItems.Buff.Effects.Glow},new int[] {2}),
            new ExtendedItems.Buff  ("Strength Buff", ExtendedItems.ItemTypes.Buff  , this,
                    new ExtendedItems.Buff.Effects[] {ExtendedItems.Buff.Effects.Strength},new int[] {1}),
            new ExtendedItems.Buff  ("Defense Buff" , ExtendedItems.ItemTypes.Buff  , this,
                    new ExtendedItems.Buff.Effects[] {ExtendedItems.Buff.Effects.Defense},new int[] {1}),
            new ExtendedItems.Buff  ("Defender Buff", ExtendedItems.ItemTypes.Buff  , this,
                    new ExtendedItems.Buff.Effects[] {ExtendedItems.Buff.Effects.Strength, ExtendedItems.Buff.Effects.Defense}, new int[] {1, 1}),

            // legendary/defender items - 16; sword, 17; bow, 18; spear, 19; torch
            new ExtendedItems.Melee ("Defenders Sword", ExtendedItems.ItemTypes.Melee , 7, 2),
            new ExtendedItems.Ranged("Defenders Bow"  , ExtendedItems.ItemTypes.Ranged, 5, 15, 3),
            new ExtendedItems.Melee ("Defenders Spear", ExtendedItems.ItemTypes.Melee , 5, 4),
            new ExtendedItems.Light ("Defenders Torch", ExtendedItems.ItemTypes.Light , 12)
    };

    // five empty slots (player starts with a 9: rusty sword)
    //private Items[] items = {allItems[9], allItems[0], allItems[0], allItems[0], allItems[0]};  // an empty list of items
    private Items[] items = {allItems[6], allItems[0], allItems[0], allItems[0], allItems[0]};  // an empty list of items

    public boolean alive = true;  // the players state (alive or dead)

    private final Scanner scanner = new Scanner(System.in);

    // the constructor
    public Player() {}  // currently doesn't do anything

    // checks if an array of strings contains an item
    private boolean StringArContains(String[] l, String item)
    {
        for (int i = 0; i < l.length; i++)
        {
            if (l[i].equals(item)) return true;
        }
        return false;
    }

    // checks for collision based on a movement direction
    private boolean Collision(Map map, int dx, int dy)
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
        readyNextLevel = false;  // ressetting the variable

        items[hand].PrintInfo();  // prints the basic info of the weapon held

        // rendering the hearts
        for (int i = 0; i < health; i++) {System.out.print("♥");}
        for (int i = 0; i < 10-health; i++) {System.out.print("♡");}

        // getting the players input for direction
        System.out.print("\nsouth, east, north, west\nwasd\nDirection >> ");
        String dir = scanner.nextLine();
        // getting the players action type (move, attack, interact, ect..)
        System.out.print("\nn - next level\ne - use\nm - move\na - attack\ni - inventory\n1-5 - Hand\nAction >> ");
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
            if (Collision(map,  dx, dy))
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
            boolean isTable = map.CheckEnchantmentTable(x + dx, y + dy);

            // checking if a chest was interacted with
            if (isChest)
            {
                System.out.println("\n");  // add a new line so the item info renders correctly

                // getting the chests items
                Items[] chestItems = map.GetChestLoot(this, x + dx, y + dy);

                int numLoot = 0;  // the number of new items in the inventory

                // adding every item from the chest to the player
                for (int i = 0; i < chestItems.length; i++)
                {
                    // checking if the item is nothing or something
                    if (chestItems[i].GetType() != ExtendedItems.ItemTypes.None)
                    {
                        // adding the item to the inventory or dropped items
                        boolean added = AddLoot(chestItems[i]);
                        if (added) numLoot++;  // the number of new items in the
                        else map.AddDroppedItem(chestItems[i], x + dx, y + dy);
                    }
                }

                // waiting so the player can read what was picked up
                Sleep(1250 * numLoot);
            }
            else if (isTable)
            {
                // removing the tile the enchantment table is on
                map.RemoveTile(x + dx, y + dy);

                // getting the enchantment manager for the item being held
                ExtendedItems.Enchants enchantmentManager = items[hand].GetEnchantmentManager();
                // getting a random enchantment for the item being held (returns a non maxed field valid for the item)
                ExtendedItems.Enchants.EnchantTypes enchant = enchantmentManager.GetRandomEnchantment();
                // checking if the enchantment can be added (returns false if everything is maxed)
                boolean enchanted = enchantmentManager.AddEnchantment(enchant);
                if (!enchanted) System.out.println("\n\nYour " + items[hand].GetName() + " is already maxed...");
                else System.out.println("\n\nYour " + items[hand].GetName() + " was enchanted with " + enchantmentManager.GetEnchantName(enchant) + "...");
                Sleep(1000);  // waiting so you can read the message
            }
        }
        else if (act.equalsIgnoreCase("n"))
        {
            readyNextLevel = true;
        }
        else if (act.equalsIgnoreCase("pos"))  // for adding mobs
        {
            System.out.println("x: " + x + ", y: " + y);
            Sleep(1000);
        }
        // checking if the inventory is being viewed
        else if (act.equalsIgnoreCase("i"))
        {
            boolean inInventory = true;  // if the player is in the inventory
            // looping while the player is messing with the inventory
            while (inInventory)
            {
                // showing all items (for now just the names)
                for (int i = 0; i < 5; i++)
                {
                    // printing the item
                    if (!items[i].GetName().equals("")) System.out.println((i+1) + ": " + items[i].GetName());
                }

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

                        if (usrAct.equals("q"))  // dropping the item
                        {
                            map.AddDroppedItem(items[slot], x + dx, y + dy);  // adding a new dropped item
                            items[slot] = allItems[0].Copy();  // removing the item from the inventory
                        }
                        else if (usrAct.equals("d")) {items[slot] = allItems[0].Copy();}  // removing an item
                        else if (usrAct.equals("m"))  // moving/swapping items
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
        else if (act.equalsIgnoreCase("a"))
        {
            // attacking any mobs
            items[hand].Attack(this, map.GetMobs(), strength, dx, dy);
        }

        // checking if the player should pick up any items
        ArrayList<Map.DroppedItems> droppedItems = map.GetDroppedItems();  // a copy
        int numDeled = 0;  // the number of picked up items

        System.out.println("\n");  // add a new line so the item info renders correctly

        // looping through the dropped items
        for (int i = 0; i < droppedItems.size(); i++)
        {
            Map.DroppedItems item = droppedItems.get(i);
            // checking if the player is standing on the item
            if (item.x == x && item.y == y)
            {
                boolean added = AddLoot(item.item);  // adding the item to the inventory or back to the ground
                // checking if the item was added
                if (added)
                {
                    map.RemoveDroppedItem(i - numDeled);  // removing the item
                    numDeled ++;  // incrementing numDeled
                }  // removing the item
                else break;  // stopping early since no room in inventory
            }
        }

        if (numDeled > 0) Sleep(1250 * numDeled);  // giving the player time to read to picked up items

        // clearing the previous frame
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    // attacks the player
    public void Attack(int attackDamage)
    {
        // hurts the player based on armor and maybe potions (IDK anything about potions yet)
        // every 4x defense is minus 2x damage, takes the ceiling of the damage so some damage will always be done
        health -= Math.ceil(attackDamage / ((float)defense * 0.25));

        if (health <= 0) alive = false;  // the player is no longer alive
    }

    // adds an item to the inventory
    public boolean AddLoot(Items item)  // adds the item and returns true or if inventory full returns false
    {
        // checking if the item is a buff
        if (item.GetType() == ExtendedItems.ItemTypes.Buff)
        {
            // printing the basic info on the item
            item.PrintInfo();

            item.ActivateBuff();  // activating the buffs powers
            return true;  // an item was collected (but this type isn't added to the inventory and is permanent)
        }

        // looping through all slots
        for (int i = 0; i < 5; i++)
        {
            // checking if the slot is empty
            if (items[i].GetType() == ExtendedItems.ItemTypes.None)
            {
                // printing the basic info on the item
                item.PrintInfo();

                // adding the item
                items[i] = item;
                return true;
            }
        }

        // not adding any items that is causing lots of problems
        //map.AddDroppedItem(item, x, y);  // dropping the item as there was no space
        return false;
    }

    // sleeps for a set amount of milliseconds
    public void Sleep(long millSecs)
    {
        long start = System.currentTimeMillis();  // the starting cpu time
        while (System.currentTimeMillis() - start < millSecs);  // waiting for the chosen amount of time to elapse
    }

    // resets the players stats for the next level
    public void Reset(Map map)
    {
        health = 10;  // resetting the health

        // resetting the position
        x = map.GetStartX();
        y = map.GetStartY();
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
    public int GetStrength() {return strength;}
    public int GetDefense() {return defense;}

    // adds to the stats
    public void AddGlow    (int glow    ) {this.glow     += glow;}
    public void AddStrength(int strength) {this.strength += strength;}
    public void AddDefense (int defense ) {this.defense  += defense;}

    // adds health to the player
    public void AddHealth(int health) {this.health = Math.min(this.health + health, 10);}

    // returns if the player is alive or not
    public boolean GetAlive() {return alive;}

    // gets the item with the given id
    public Items IDGetItem(int id) {return allItems[id];}

    // gets if the player is ready to go onto the next level
    public boolean GetReadyNextLevel() {return readyNextLevel;}
}
