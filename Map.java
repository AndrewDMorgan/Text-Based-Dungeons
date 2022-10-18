import java.util.ArrayList;

public class Map
{
    // stores a light (used in the map)
    private static class Light
    {
        public final int x;
        public final int y;
        public final int glow;

        public Light(int x, int y, int glow)
        {
            this.x = x;
            this.y = y;
            this.glow = glow;
        }
    }

    // stores an item and position
    public static class DroppedItems
    {
        public Items item;
        public int x;
        public int y;

        // the constructor
        public DroppedItems(Items item, int x, int y)
        {
            this.item = item;
            this.x = x;
            this.y = y;
        }
    }

    // all the lights in the different maps
    private Light[][] mapLights = {
            {
                new Light(8, 3, 6)
            },
            {
                new Light(9 , 7 , 6),
                new Light(9 , 10, 6),
                new Light(18, 2 , 8),
                new Light(1 , 13, 8),
                new Light(17, 11, 6)
            },
            {}
    };

    // the maps (in numbers to make some things easier)
    private final int[][][] levels =
            {
                {   // 10 x 10
                    {2, 3, 3, 3, 3, 3, 3, 3, 3, 2},
                    {2, 4, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 2, 0, 0, 0, 2, 0, 2},
                    {2, 0, 0, 2, 0, 0, 0, 2, 5, 2},
                    {2, 3, 0, 2, 0, 0, 3, 3, 3, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 3, 3, 3, 3, 3, 0, 0, 2},
                    {2, 0, 0, 2, 4, 0, 0, 0, 0, 2},
                    {2, 0, 0, 2, 0, 0, 0, 0, 0, 2},
                    {2, 3, 3, 3, 3, 3, 3, 3, 3, 2}
                },
                {   // 20 x 18
                    {2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2},
                    {2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 5, 2},
                    {2, 0, 0, 6, 3, 3, 3, 3, 6, 0, 0, 6, 3, 0, 2, 0, 0, 2, 0, 2},
                    {2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 2, 4, 0, 2, 0, 0, 2, 0, 2},
                    {2, 0, 0, 2, 0, 2, 7, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 2},
                    {2, 0, 0, 2, 0, 2, 4, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 2},
                    {2, 0, 0, 2, 0, 3, 3, 3, 6, 5, 0, 6, 3, 3, 6, 0, 0, 2, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 3, 3, 3, 3, 3, 3, 3, 6, 5, 0, 6, 3, 3, 6, 0, 6, 3, 3, 2},
                    {2, 7, 0, 0, 4, 2, 0, 4, 2, 0, 0, 2, 4, 0, 2, 0, 2, 5, 4, 2},
                    {2, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 2},
                    {2, 5, 0, 0, 0, 2, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 2, 0, 0, 2},
                    {2, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 0, 2},
                    {2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2},
                    {2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2}
                },
                {   // 10 x 10
                    {2, 3, 3, 3, 3, 3, 3, 3, 3, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 4, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 4, 0, 0, 0, 0, 0, 0, 4, 2},
                    {2, 3, 3, 3, 3, 3, 3, 3, 3, 2}
                }
            };

    // 0 🧟: zombie, 1 ఠఠ: slime, 2 ☠): skeleton, 3; boss loot
    private final int[][] mobLoot = {
            {0, 0, 0, 1, 2},
            {0, 0, 0, 0, 11},
            {0, 0, 0, 2, 5},
            {12, 13, 14, 15}
    };

    // the mobs in each level
    private BaseMob[][] mobs = {
            {
                /*new BaseMob(5, 3, 1, 1, 1, "\uD83E\uDDDF", mobLoot[0]),
                new BaseMob(8, 2, 1, 1, 1, "ఠఠ"          , mobLoot[1]),
                new BaseMob(6, 8, 3, 1, 1, "\uD83E\uDDDF", mobLoot[0]),
                new BaseMob(6, 5, 2, 1, 1, "☠)"          , mobLoot[2])*/
            },
            {
                /*new BaseMob(5 , 3 , 1, 1, 1, "\uD83E\uDDDF", mobLoot[0]),
                new BaseMob(7 , 12, 1, 1, 1, "☠)"          , mobLoot[2]),
                new BaseMob(7 , 16, 1, 1, 1, "ఠఠ"          , mobLoot[1]),
                new BaseMob(3 , 15, 2, 2, 1, "\uD83E\uDDDF", mobLoot[0]),
                new BaseMob(2 , 16, 2, 1, 1, "☠)"          , mobLoot[2]),
                new BaseMob(1 , 14, 3, 2, 1, "ఠఠ"          , mobLoot[1]),
                new BaseMob(3 , 13, 2, 2, 1, "\uD83E\uDDDF", mobLoot[0]),
                new BaseMob(2 , 11, 3, 1, 1, "\uD83E\uDDDF", mobLoot[0]),
                new BaseMob(16, 16, 1, 1, 1, "ఠఠ"          , mobLoot[1]),
                new BaseMob(18, 12, 2, 1, 1, "\uD83E\uDDDF", mobLoot[0]),
                new BaseMob(18, 7 , 1, 1, 1, "☠)"          , mobLoot[2]),
                new BaseMob(12, 5 , 2, 2, 1, "ఠఠ"          , mobLoot[1]),
                new BaseMob(10, 1 , 1, 1, 1, "\uD83E\uDDDF", mobLoot[0]),
                new BaseMob(2 , 5 , 2, 1, 1, "ఠఠ"          , mobLoot[1]),
                new BaseMob(7 , 5 , 3, 2, 1, "ఠఠ"          , mobLoot[1]),
                new BaseMob(5 , 4 , 5, 3, 2, "☠)"          , mobLoot[2]),
                new BaseMob(4 , 4 , 2, 1, 1, "\uD83E\uDDDF", mobLoot[0])*/
            },
            {
                new Boss(5, 5, 15, 1, 2, "\uD83E\uDDDF", mobLoot[3])
            }
    };

    private ArrayList<DroppedItems> droppedItems = new ArrayList<DroppedItems>();

    // the loots for chests in different levels (each level has an array of valid ids for loot)
    // 0; nothing, 1; wooden sword, 2; ancient torch, 3; ancient axe, 4; wooden spear, 5; wooden bow, 6; stone sword, 7; tiki torch, 8; stone spear, 9; Rusty Sword, 10; cross bow
    // 11; health buff, 12; glow buff, 13; strength buff, 14; defense buff, 15; defender buff (strength + defense)
    // legendary/defender items - 16; sword, 17; bow, 18; spear, 19; torch
    private final int[][] levelChestLoot = {
            {1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 4, 4, 4, 4, 5, 5, 5, 11, 11, 11, 14},
            {2, 2, 2, 2, 3, 3, 3, 3, 5, 5, 5, 5, 6, 6, 7, 7, 8, 8, 10, 10, 11, 11, 11, 11, 12, 13, 14, 15},
            {11, 11, 11, 11, 11, 11, 12, 13, 14}
    };

    // the different tiles light goes through
    private final int[] transparent = {0, 4, 5, 7};

    // the solid tiles
    public final int[] collisionTiles = {1, 2, 3, 4, 6, 7};

    // the tile that represents enchantment tables
    public final int enchantmentTableTile = 7;

    // the level spawn points
    private final int[] levelSpawnsX = {4, 10, 1};
    private final int[] levelSpawnsY = {5, 9, 1};

    // the current level
    private int level = 0;

    // 0; air, 1; water, 2; wall, 3; bottom wall, 4; chest, 5; torch facing ->, 6; corner wall, 7; enchantment table
    private final String[] chars = {"  ", "~~", "||", "--", "==", "; ", "++", "\uD83D\uDCD6"};

    // the different brightnesses (14 or 15 is full bright 0 is completely dark) (@%#*+=-:. )
    //private final String[] charBrightness = {"##", "**", "::", ". ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "};
    private final String[] charBrightness = {"██", "▓▓", "▒▒", "░░", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "};

    private int[][] levelBrightnessMap;

    // the initializer (nothing currently)
    public Map()
    {
        // the size of the first level
        int lx = levels[level]   .length;
        int ly = levels[level][0].length;

        // initializing the brightness map
        levelBrightnessMap = new int[lx][ly];

        // setting up the brightness map
        for (int x = 0; x < lx; x++)
        {
            for (int y = 0; y < ly; y++) levelBrightnessMap[x][y] = 0;
        }
    }

    private void GetMap(Player player)
    {
        // finding the size of the map
        int lx = levels[level]   .length;
        int ly = levels[level][0].length;

        // looping through the map
        for (int y = 0; y < lx; y++)
        {
            for(int x = 0; x < ly; x++)
            {
                // adding the new char
                String tile = chars[levels[level][y][x]];

                // checking the brightness
                int brightness = levelBrightnessMap[y][x];
                if (tile.equals("  ") || brightness <= 1)
                {
                    // altering based on brightness
                    tile = charBrightness[Math.min(brightness, 15)];
                }

                // checking if rendering the player
                if (player.GetX() == x && player.GetY() == y)
                {
                    // checking what's in the players hand
                    String hand = " ";
                    if (!player.GetHand().GetName().equals(""))
                    {
                        if      (player.GetHand().GetType() == ExtendedItems.ItemTypes.Light ) hand = ";";
                        else if (player.GetHand().GetType() == ExtendedItems.ItemTypes.Ranged) hand = ")";
                        else if (player.GetHand().GetType() == ExtendedItems.ItemTypes.Melee ) hand = "|";
                    }

                    // checking if the player is low health
                    if (player.GetHealth() >= 5) tile = "☺" + hand;
                    else tile = "☹" + hand;
                }
                else
                {
                    // checking for a dropped item & looping over all the items
                    for (int i = 0; i < droppedItems.size(); i++)
                    {
                        // checking if it's on the item
                        if (droppedItems.get(i).x == x && droppedItems.get(i).y == y)
                        {
                            // checking if the item is visible or if its to dark
                            if (levelBrightnessMap[y][x] > 1) tile = "%%";  // setting the tile to the dropped items tile
                            break;  // ending the loop to reduce unnecessary calculations
                        }
                    }
                }

                // renders over the player so you know if a mob is on you
                // looping through all the mobs
                for (int i = 0; i < mobs[level].length; i++)
                {
                    // checking if the mob should be on the board
                    if (mobs[level][i].GetX() == x && mobs[level][i].GetY() == y)
                    {
                        // checking if the mob is visible (or if its to dark)
                        if (levelBrightnessMap[y][x] > 2) tile = mobs[level][i].GetChar();
                        break;
                    }
                }

                // rendering the tile
                System.out.print(tile);
            }
            System.out.println("");
        }
    }

    private boolean IntArContains(int[] l, int item)
    {
        for (int i = 0; i < l.length; i++)
        {
            if (l[i] == item) return true;
        }
        return false;
    }

    // checks if a point is in bounds of rectangle (or 2D array)
    public boolean CheckBounds(int x, int y, boolean noWalls)
    {
        int mapY = levels[level]   .length;
        int mapX = levels[level][0].length;

        boolean onMap = x >= 0 && x < mapX && y >=0 && y < mapY;
        boolean notWall  = true;
        if (onMap)
        {
            notWall = IntArContains(transparent, levels[level][y][x]);
        }
        return onMap && (notWall || noWalls);
    }

    // checks for a collision
    public boolean CheckCollision(int x, int y) {return !IntArContains(collisionTiles, levels[level][y][x]);}

    // generates a new brightness map (spreading exactly like in minecraft)
    private void GenerateBrightnessMap(int[] lightsX, int[] lightsY, int[] lightsStrength, int lights)
    {
        // finding the size of the map
        int lx = levels[level]   .length;
        int ly = levels[level][0].length;

        // adding all the lights
        for (int i = 0; i < lights; i++)
        {
            levelBrightnessMap[lightsY[i]][lightsX[i]] = lightsStrength[i];
        }

        for (int i = 0; i < 15; i++)  // looping until light fills the entire map
        {
            for (int x = 0; x < ly; x++)
            {
                for (int y = 0; y < lx; y++)
                {
                    int maxLight = 0;

                    // checking for the brightest light that's on the map
                    if (CheckBounds(x-1, y, false) && levelBrightnessMap[y][x-1] > maxLight) maxLight = levelBrightnessMap[y][x-1];
                    if (CheckBounds(x+1, y, false) && levelBrightnessMap[y][x+1] > maxLight) maxLight = levelBrightnessMap[y][x+1];
                    if (CheckBounds(x, y-1, false) && levelBrightnessMap[y-1][x] > maxLight) maxLight = levelBrightnessMap[y-1][x];
                    if (CheckBounds(x, y+1, false) && levelBrightnessMap[y+1][x] > maxLight) maxLight = levelBrightnessMap[y+1][x];

                    // spreading the light
                    if (maxLight > levelBrightnessMap[y][x])
                    {
                        levelBrightnessMap[y][x] = maxLight - 1;
                    }
                }
            }
        }
    }

    // renders a map including the mobs
    public void RenderMap(Player player)
    {
        Light[] levelLights = mapLights[level];  // all the lights on this level

        // generating the lighting from player items and map lights
        int lights = levelLights.length + 1;
        int[] lightsX = new int[lights];
        int[] lightsY = new int[lights];
        int[] lightsStrength = new int[lights];

        // checking if the item being held provides brightness
        int glow = player.GetHand().GetLight();
        lightsX[levelLights.length] = player.GetX();
        lightsY[levelLights.length] = player.GetY();
        lightsStrength[levelLights.length] = Math.max(glow, player.GetGlow());

        // adding all map lights
        for (int i = 0; i < levelLights.length; i++)
        {
            lightsX[i] = levelLights[i].x;
            lightsY[i] = levelLights[i].y;

            lightsStrength[i] = levelLights[i].glow;
        }

        // generating the brightness map
        GenerateBrightnessMap(lightsX, lightsY, lightsStrength, lights);

        // rendering the map
        GetMap(player);
    }

    // gets the tile at a given position
    public int GetTile(int x, int y) {return levels[level][y][x];}

    // gets and sets the mobs
    public BaseMob[] GetMobs() {return mobs[level];}
    public void SetMobs(BaseMob[] mobs) {this.mobs[level] = mobs;}

    // gets the loots from a chest
    public Items[] GetChestLoot(Player player, int x, int y)
    {
        levels[level][y][x] = 0;  // removing the chest (so you can't get loot twice)

        // getting the number of items (3 max)
        int numItems = (int)Math.round(Math.random() * 2) + 1;
        Items[] items = new Items[numItems];
        for (int i = 0; i < numItems; i++)
        {
            // choosing a random item
            int index = (int)Math.round(Math.random() * (double)(levelChestLoot[level].length-1));
            Items item = player.IDGetItem(levelChestLoot[level][index]).Copy();
            items[i] = item;  // adding the item to the final items (a new instance of it)
        }
        return items;
    }

    // goes to the next level
    public boolean NextLevel(Player player)
    {
        int numLevels = levels.length;  // the number of levels
        level++;  // going to the next level

        // removing all dropped items
        droppedItems = new ArrayList<DroppedItems>();

        if (level >= numLevels)
        {
            System.out.println("You Finished The Game!!!\nGreat Job!!");  // the player has beat all levels
            level--;  // going back a level to stop any crashes
            return true;  // the game was beaten
        }

        // the size of the new map
        int lx = levels[level]   .length;
        int ly = levels[level][0].length;

        // creating a new map with the correct size
        levelBrightnessMap = new int[lx][ly];

        // resetting the brightness map for the new level
        for (int x = 0; x < lx; x++)
        {
            for (int y = 0; y < ly; y++) levelBrightnessMap[x][y] = 0;
        }

        return false;  // nothing happened
    }

    // adds a dropped item to the ground
    public void AddDroppedItem(Items item, int x, int y) {droppedItems.add(new DroppedItems(item, x, y));}

    // gets the dropped items on the cell
    public ArrayList<DroppedItems> GetDroppedItems() {return (ArrayList<DroppedItems>) droppedItems.clone();}

    // removes an item from the dropped items
    public void RemoveDroppedItem(int index) {droppedItems.remove(index);}

    // checking for a chest
    public boolean CheckChest(int x, int y) {return levels[level][y][x] == 4;}

    // gets the brightness at the given point
    public int GetBrightness(int x, int y) {return levelBrightnessMap[y][x];}

    // checks if the position is on an enchantment table
    public boolean CheckEnchantmentTable(int x, int y) {return levels[level][y][x] == enchantmentTableTile;}

    // gets the starting x and y for a map
    public int GetStartX() {return levelSpawnsX[level];}
    public int GetStartY() {return levelSpawnsY[level];}

    // removes a tile from the map
    public void RemoveTile(int x, int y) {levels[level][y][x] = 0;}
}
