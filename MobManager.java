import java.util.ArrayList;

public class MobManager
{
    static public boolean Update(Map map, Player player, int opx, int opy)
    {
        // the new mobs (all the alive mobs)
        ArrayList<BaseMob> newMobs = new ArrayList<BaseMob>();

        // if a mob was killed or not
        boolean killed = false;

        // updating all the mobs (second, so they don't perfectly move with the player)
        BaseMob[] mobs = map.GetMobs();
        // looping through all the mobs
        for (int i = 0; i < mobs.length; i++) {
            if (mobs[i].GetAlive())  // checking if the mob is still alive
            {
                mobs[i].Update(player, map, opx, opy);  // updating the mobs
                newMobs.add(mobs[i]);  // adding the mob sense It's still alive
            }
            else
            {
                killed = true;  // a mob was killed

                // dropping the mobs loot
                Items[] loot = mobs[i].GetLoot(player);

                // looping through all the items
                for (int itemIndex = 0; itemIndex < loot.length; itemIndex++)
                {
                    // adding the dropped item to the ground
                    if (loot[itemIndex].GetType() != ExtendedItems.ItemTypes.None) map.AddDroppedItem(loot[itemIndex], mobs[i].GetX(), mobs[i].GetY());
                }
            }
        }

        // checking if the level has been beaten and the player is ready
        if (newMobs.size() == 0 || player.GetReadyNextLevel())
        {
            boolean finished = false;  // if the game is finished or not
            boolean msg = false;  // if the screen needs clearing

            // checking for the combanations of mobs dead and player being ready
            if (player.GetReadyNextLevel() && newMobs.size() != 0)
            {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nYou cannot go onto the next level until all mobs are killed...");
                msg = true;
            }
            else if (newMobs.size() == 0)
            {
                map.SetMobs((new BaseMob[0]));  // no mobs left

                if (player.GetReadyNextLevel())
                {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nLevel Beaten!!!!!");

                    // updating the levels
                    finished = map.NextLevel(player);
                    player.Reset(map);  // resetting the players health for the next level

                    msg = true;
                }
                else if (killed)
                {
                    System.out.print("\nAll mobs are dead, action n to go on");
                    msg = true;
                }
            }

            if (msg)
            {
                // the player has won
                Sleep(2000);  // waiting two seconds
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            }
            return finished;  // returning if the game has been completed or not
        }
        else
        {
            // getting an array (not list) of the new mobs (not dead mobs)
            BaseMob[] newMobsAr = new BaseMob[newMobs.size()];
            for (int i = 0; i < newMobsAr.length; i++)
            {
                newMobsAr[i] = newMobs.get(i);
            }

            // updating the mobs
            map.SetMobs(newMobsAr);
        }
        return false;  // the game hasn't been completed
    }

    // sleeps for a set amount of milliseconds
    static public void Sleep(long millSecs)
    {
        long start = System.currentTimeMillis();  // the starting cpu time
        while (System.currentTimeMillis() - start < millSecs);  // waiting for the chosen amount of time to elapse
    }

}
