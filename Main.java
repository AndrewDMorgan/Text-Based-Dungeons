public class Main
{
    /*
    Objectives

        Mobs (rendering and basic AI)
        Main interface (links levels and stuff)
        Add attacking (mobs attack player and player attacking mobs)
        Show more item information
        Basic item being held info

     Working on

     */

    // the main program (links everything together)
    public static void main(String[] args)
    {
        // initializing the player/player controller and the map/map controller/map renderer
        Player    player = new Player();
        Map       map    = new Map();

        // the main game loop (no reason for it to stop other than pressing the stop button in the IDE)
        while (true)
        {
            // updating the map and rendering it
            map.RenderMap(player, map.GetMobs());
            // updating the player and render/getting the usr interface/input
            player.Update(map);
        }
    }
}

