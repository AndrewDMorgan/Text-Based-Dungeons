public class Main
{
    /*
    
    objectives:
        * finish the second level
        * add a boss level? that would be interesting:
            - every couple levels you have to beat a boss with a fun AI? that is really challenging (bows are probably powerful against bosses)
            - the boss drops really powerful loot upon death
            - the boss will drop legendary items/a new tier that is better than everything else that is only from bosses
            - you get one legendary item and multiple normal items from it
            - you get 3 random potions from the boss (could be anything)
        * balance the game:
            - make mobs hard/easier so the game plays well

        Working On:
            * boss

        Bugs:
            * attacking through walls:
                - check for collision, if collision than stop attacking

    */

    // the main program (links everything together)
    public static void main(String[] args)
    {
        // initializing the player/player controller and the map/map controller/map renderer
        Player     player = new Player();
        Map        map    = new Map();

        // the players old position
        int opx = player.GetX();
        int opy = player.GetY();

        int frame = 0;  // the mobs don't move on the first frame

        // the main game loop (no reason for it to stop other than pressing the stop button in the IDE)
        while (player.GetHealth() > 0)
        {
            if (frame != 0)  // so mobs don't move on the first frame
            {
                // updating the mobs (under this class to clean things up sense it was a lot of code)
                boolean finished = MobManager.Update(map, player, opx, opy);

                // leaving the while loop/main loop if the game is over so the player doesn't choose an action again
                if (finished) break;  // so the players code isn't run, might be cleaner than an if covering the rest of the code but IDK
            }

            // updating the map and rendering it
            map.RenderMap(player);

            // getting the old position of the player for the mobs to use (so they don't perfectly follow the player)
            opx = player.GetX();
            opy = player.GetY();

            // updating the player and render/getting the usr interface/input
            player.Update(map);

            frame++;  // incrementing the frame
        }

        // checking if the game was finished or the player died
        if (player.GetHealth() <= 0) System.out.println("You Died...");  // the player lost
    }
}

