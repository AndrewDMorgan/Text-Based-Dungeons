public class Boss extends BaseMob
{
    // the constructor
    public Boss(int x, int y, int health, int attackDamage, int attackRange, String sprite, int[] loot)
    {
        super(x, y, health, attackDamage, attackRange, sprite, loot);
    }

    // updates the mob
    @Override
    public void Update(Player player, Map map, int opx, int opy)
    {
        // attacking the player (if possible)
        Attack(player);

        // getting the movement direction
        Directions direction = GetMoveDir(player, map, opx, opy);

        // moving
        if (Math.random() > 4./5.)  // 1/5 chance of moving so its not crazy fast sense its so strong
        if      (direction == Directions.Left ) SetX(GetX() - 1);
        else if (direction == Directions.Right) SetX(GetX() + 1);
        else if (direction == Directions.Up   ) SetY(GetY() - 1);
        else if (direction == Directions.Down ) SetY(GetY() + 1);
    }

    // gets the loot from the mob
    @Override
    public Items[] GetLoot(Player player)
    {
        int[] loot = GetLootTable();

        // getting the number of items (2 max)
        int numItems = (int)Math.round(Math.random() * 3) + 3;  // 3 - 5 items
        Items[] items = new Items[numItems + 1];
        for (int i = 0; i < numItems; i++)
        {
            // choosing a random item
            int index = (int)Math.round(Math.random() * (double)(loot.length-1));
            items[i] = player.IDGetItem(loot[index]).Copy();  // adding the item to the final items (a new instance of it)
        }

        // adding on a random legendary item
        // legendary/defender items - 16; sword, 17; bow, 18; spear, 19; torch
        int randItem = Math.min((int)(Math.random() * 3 + 16), 19);
        items[numItems] = player.IDGetItem(randItem);  // getting the random defender

        return items;
    }

}
