import java.util.ArrayList;

public class Map
{
    // stores a light (used in the map)
    static private class Light
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
    static public class DroppedItems
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
            {new Light(8, 3, 6)},
            {}
    };

    // the maps (in numbers to make some things easier)
    private final int[][][] levels =
            {
                {
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
                {
                    {2, 3, 3, 3, 3, 3, 3, 3, 3, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                    {2, 3, 3, 3, 3, 3, 3, 3, 3, 2}
                }
            };

    private ArrayList<DroppedItems> droppedItems = new ArrayList<DroppedItems>();

    // the loots for chests in different levels (each level has an array of valid ids for loot)
    // 0; nothing, 1; wooden sword, 2; ancient torch, 3; ancient axe, 4; wooden spear, 5; wooden bow, 6; stone sword, 7; tiki torch, 8; stone spear
    private final int[][] levelChestLoot = {{0, 1, 2, 2, 2, 8, 6}, {1, 2, 2, 3, 4, 4, 6, 6, 7, 7, 8, 0}};

    // the different tiles light goes through
    private final int[] transparent = {0, 4, 5};

    // the solid tiles
    public final int[] collisionTiles = {1, 2, 3, 4};

    // the level spawn points
    private final int[] levelSpawnsX = {4, 1};
    private final int[] levelSpawnsY = {5, 1};

    // the current level
    private final int level = 0;

    // 0; air, 1; water, 2; wall, 3; bottom wall, 4; chest, 5; torch facing ->,
    private final String[] chars = {"  ", "~~", "||", "__", "[]", ";"};

    // the different brightnesses (9 is full bright 0 is completely dark) (@%#*+=-:. )
    private final String[] charBrightness = {"##", "**", "::", ". ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "};

    private int[][] levelBrightnessMap;

    private BaseMob[][] mobs   = {
        {},
        {}
    };

    // the initializer (nothing currently)
    public Map()
    {

    }

    private void GetMap(Player player)
    {
        // finding the size of the map
        int lx = levels[level]   .length;
        int ly = levels[level][0].length;

        // looping through the map
        for (int y = 0; y < ly; y++)
        {
            for(int x = 0; x < lx; x++)
            {
                // adding the new char
                String tile = chars[levels[level][y][x]];

                // checking the brightness
                int brightness = levelBrightnessMap[y][x];
                if (tile.equals("  ") || brightness <= 1)
                {
                    // altering based on brightness
                    tile = charBrightness[brightness];
                }

                // checking if rendering the player
                if (player.GetX() == x && player.GetY() == y)
                {
                    // checking what's in the players hand
                    String hand = " ";
                    if (!player.GetHand().GetName().equals("")) hand = (player.GetHand().GetType() == ExtendedItems.ItemTypes.Light) ? ";" : "|";

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
        int mapX = levels[level]   .length;
        int mapY = levels[level][0].length;

        boolean onMap = x >= 0 && x < mapX && y >=0 && y < mapY;
        boolean notWall  = true;
        if (onMap)
        {
            notWall = IntArContains(transparent, levels[level][y][x]);
        }
        return onMap && (notWall || noWalls);
    }

    // generates a new brightness map (spreading exactly like in minecraft)
    private void GenerateBrightnessMap(int[] lightsX, int[] lightsY, int[] lightsStrength, int lights)
    {
        // finding the size of the map
        int lx = levels[level]   .length;
        int ly = levels[level][0].length;

        levelBrightnessMap = new int[ly][lx];  // creating a new light map
        for (int x = 0; x < 10; x++)
        {
            for (int y = 0; y < 10; y++) levelBrightnessMap[y][x] = 0;
        }
        // adding all the lights
        for (int i = 0; i < lights; i++)
        {
            levelBrightnessMap[lightsY[i]][lightsX[i]] = lightsStrength[i];
        }

        for (int i = 0; i < 15; i++)  // looping until light fills the entire map
        {
            for (int x = 0; x < lx; x++)
            {
                for (int y = 0; y < ly; y++)
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
    public void RenderMap(Player player, BaseMob[] mobs)
    {
        Light[] levelLights = mapLights[level];  // all the lights on this level

        // generating the lighting from player items and map lights
        int lights = levelLights.length+1;
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

    // gets the mobs
    public BaseMob[] GetMobs() {return mobs[level];}

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
            items[i] = player.IDGetItem(levelChestLoot[level][index]).Copy();  // adding the item to the final items (a new instance of it)
        }
        return items;
    }

    // adds a dropped item to the ground
    public void AddDroppedItem(Items item, int x, int y) {droppedItems.add(new DroppedItems(item, x, y));}

    // gets the dropped items on the cell
    public ArrayList<DroppedItems> GetDroppedItems() {return (ArrayList<DroppedItems>) droppedItems.clone();}

    // removes an item from the dropped items
    public void RemoveDroppedItem(int index) {droppedItems.remove(index);}

    // checking for a chest
    public boolean CheckChest(int x, int y) {return levels[level][y][x] == 4;}
}
