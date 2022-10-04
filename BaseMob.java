public class BaseMob // might not end up being inherited since the base class cover enough for the limited mobs being used
{
    // the basic stats of a monster
    private int health;
    final private int attackDamage;
    final private float attackRange;
    private boolean alive = true;

    // the position of the mob
    private int x;
    private int y;

    private int loot[];

    private String sprite;

    // setting up the mob
    public BaseMob(int x, int y, int health, int attackDamage, int attackRange, String sprite, int loot[])
    {
        // initializing the stats for the mob
        this.health       = health;
        this.attackDamage = attackDamage;
        this.attackRange  = attackRange;
        this.sprite       = sprite;  // the char used for the mob

        this.loot = loot;  // the loot you can get from the mob

        // the mobs position
        this.x = x;
        this.y = y;
    }

    // gets the distance to the player (for the MoveQuality function)
    private float Length(int x, int y, int cx, int cy)
    {
        // getting the difference in x and y
        int dx = (this.x + cx) - x;
        int dy = (this.y + cy) - y;
        // getting the hypotenuse
        return (float)Math.sqrt((dx*dx + dy*dy));
    }

    private enum Directions
    {
        Left,
        Right,
        Up,
        Down,
        None
    }

    // checks if a mob is on the position
    private boolean MobOnPos(Map map, int x, int y)
    {
        // looping through all the mobs
        BaseMob ar[] = map.GetMobs();
        for (int i = 0; i < ar.length; i++)
        {
            if (ar[i].x == x && ar[i].y == y) return true;  // mob on pos
        }
        return false;  // no mob on pos
    }

    private Directions GetMoveDir(Player player, Map map, int opx, int opy)
    {
        // checking if the mob should stay still
        int d = Math.abs(x - player.GetX()) + Math.abs(y - player.GetY());
        if (d <= 1) return Directions.None;

        // the new distances to the player
        float dl = Length(opx, opy, -1,  0);
        float dr = Length(opx, opy,  1,  0);
        float dt = Length(opx, opy,  0, -1);
        float db = Length(opx, opy,  0,  1);

        // checking if the player is in range
        int dsl=0, dsr=0, dst=0, dsb=0;
        if (dl <= 3f) dsl = 1;
        if (dr <= 3f) dsr = 1;
        if (dt <= 3f) dst = 1;
        if (db <= 3f) dsb = 1;

        // the max, min, and new maxes
        float minD = Math.min(Math.min(dl, dr), Math.min(dt, db));
        float maxD = Math.max(Math.max(dl, dr), Math.max(dt, db));
        float nMax = maxD - minD;

        // checking for walls
        int walll = map.CheckCollision(x - 1,    y    ) ? 1 : 0;
        int wallr = map.CheckCollision(x + 1,    y    ) ? 1 : 0;
        int wallt = map.CheckCollision(   x    , y - 1) ? 1 : 0;
        int wallb = map.CheckCollision(   x    , y + 1) ? 1 : 0;

        // checking if a mob is already in that spot
        walll = (MobOnPos(map, x - 1,    y    ) ? 0 : 1) * walll;
        wallr = (MobOnPos(map, x + 1,    y    ) ? 0 : 1) * wallr;
        wallt = (MobOnPos(map,    x    , y - 1) ? 0 : 1) * wallt;
        wallb = (MobOnPos(map,    x    , y + 1) ? 0 : 1) * wallb;

        // darkness
        float darkl = (15 - map.GetBrightness(x - 1,    y    )) / 15f;
        float darkr = (15 - map.GetBrightness(x + 1,    y    )) / 15f;
        float darkt = (15 - map.GetBrightness(   x    , y - 1)) / 15f;
        float darkb = (15 - map.GetBrightness(   x    , y + 1)) / 15f;

        // the final quality for the direction
        float ql = ((nMax - (dl - minD)) * dsl * 3 + darkl) * walll;
        float qr = ((nMax - (dr - minD)) * dsr * 3 + darkr) * wallr;
        float qt = ((nMax - (dt - minD)) * dst * 3 + darkt) * wallt;
        float qb = ((nMax - (db - minD)) * dsb * 3 + darkb) * wallb;

        // finding the highest value (the highest quality)
        float max = Math.max(Math.max(ql, qr), Math.max(qt, qb));

        if (max == 0) return Directions.None;

        // finding the final direction
        if (max == ql) return Directions.Left;
        if (max == qr) return Directions.Right;
        if (max == qt) return Directions.Up;
        if (max == qb) return Directions.Down;
        return Directions.None;
    }

    // updates the mob
    public void Update(Player player, Map map, int opx, int opy)
    {
        // moves the mob and attacks if its in range of the player

        /* process

        Create function to check quality of move (the best direction is the direction the mob moves)
                no just no Use A* path finding algorithm to check new distance to player (closer is better but if super far from player than it doesn't see the player)
            Check distance to player if in range of sight than move in the direction that makes the mob closest to the player
            If not going for player a darker area is better than a bright one (since the player can't see it from far away increasing the element of surprise)
            If a mob is on the player than the player takes damage
            Don't go ontop of another mob

        check which direction has the best quality/results
        move the mob in that direction
         */

        // attacking the player (if possible)
        Attack(player);

        // getting the movement direction
        Directions direction = GetMoveDir(player, map, opx, opy);

        // moving
        if      (direction == Directions.Left ) x -= 1;
        else if (direction == Directions.Right) x += 1;
        else if (direction == Directions.Up   ) y -= 1;
        else if (direction == Directions.Down ) y += 1;
    }

    // attacking the mob
    public void Attacked(Player player, int attackDamage)
    {
        health -= attackDamage;  // damaging the mob
        if (health <= 0) alive = false; // checking if the mob is dead
    }

    // attacking the player
    private void Attack(Player player)
    {
        // checking the distance to the player
        int dx = x - player.GetX();
        int dy = y - player.GetY();
        float dst = (float)Math.sqrt(dx*dx + dy*dy);

        // checking if the mob is close enough to the player
        if (dst <= attackRange+0.0001)
        {
            // attacking the player
            player.Attack(attackDamage);
        }
    }

    // gets the loot from the mob
    public Items[] GetLoot(Player player)
    {
        // getting the number of items (2 max)
        int numItems = (int)Math.round(Math.random()) + 1;
        Items[] items = new Items[numItems];
        for (int i = 0; i < numItems; i++)
        {
            // choosing a random item
            int index = (int)Math.round(Math.random() * (double)(loot.length-1));
            items[i] = player.IDGetItem(loot[index]).Copy();  // adding the item to the final items (a new instance of it)
        }
        return items;
    }

    // gets the mobs position
    public int GetX() {return x;}
    public int GetY() {return y;}

    // gets the sprite (char/string) for the mob
    public String GetChar() {return sprite;}

    // gets the state of the mob (alive or dead)
    public boolean GetAlive() {return alive;}
}
