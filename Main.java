public class Main
{
    /*
    Objectives

        Winning/completing levels

     Working on

*/

    // the main program (links everything together)
    public static void main(String[] args)
    {
        // initializing the player/player controller and the map/map controller/map renderer
        Player     player = new Player();
        Map        map    = new Map();

        int opx = player.GetX();
        int opy = player.GetY();

        int frame = 0;  // the mobs don't move on the first frame

        boolean alive = true;  // the main loop runs while the player is still alive

        // the main game loop (no reason for it to stop other than pressing the stop button in the IDE)
        while (alive)
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
            alive = player.GetAlive();

            frame++;  // incrementing the frame
        }

        // checking if the game was finished or the player died
        if (!alive) System.out.println("You Died...");  // the player lost
    }
}

