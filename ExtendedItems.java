import java.util.*;
import java.io.*;

public class ExtendedItems
{
    // all the types of items
    public enum ItemTypes
    {
        None,
        Melee,
        Ranged,
        Light,
        Defense,
        Buff
    }
    
    // for all melee weapons
    static public class Melee extends Items
    {
        // the constructor
        private int damage;  // the amount of damage dealt by this weapon
        private int range;  // the range the weapon works at (in number of tiles)

        public Melee(String name, ExtendedItems.ItemTypes itemType, int damage, int range)
        {
            super(name, itemType);

            this.damage = damage;
            this.range = range;
        }

        // gets the range
        public int GetRange() {return range;}

        // gets the damage
        public int GetDamage() {return damage;}

        // attacks any nearby mobs
        @Override
        public void Attack(Player player, BaseMob[] mobs, int strength, int dx, int dy)
        {
            // looping through all the mobs
            for (int i = 0; i < mobs.length; i++)
            {
                // looping through the length of the weapon
                for (int r = 1; r <= range; r++)
                {
                    // checking if the mobs is on the position being attacked
                    if (player.GetX() + dx * r == mobs[i].GetX() && player.GetY() + dy * r == mobs[i].GetY())
                    {
                        // attacking the mob
                        mobs[i].Attacked(player, (int)((float)damage * ((float)strength * 0.25)));
                    }
                }
            }
        }

        // prints the basic info
        @Override
        public void PrintInfo() {System.out.println(GetName() + "\nDamage: " + damage + "\n");}

        // copies all info to a new object
        @Override
        public Melee Copy() {return new Melee(GetName(), GetType(), damage, range);}
    }

    // for all ranged weapons
    static public class Ranged extends Melee
    {
        // the constructor
        public Ranged(String name, ExtendedItems.ItemTypes itemType, int damage, int range)
        {
            super(name, itemType, damage, range);
        }

        // attacking the mobs
        @Override
        public void Attack(Player player, BaseMob[] mobs, int strength, int dx, int dy)
        {
            // attacks a mob
            // looping through all the mobs
            for (int i = 0; i < mobs.length; i++)
            {
                // looping through the length of the weapon
                for (int r = 1; r <= GetRange(); r++)
                {
                    // checking if the mobs is on the position being attacked
                    if (player.GetX() + dx * r == mobs[i].GetX() && player.GetY() + dy * r == mobs[i].GetY())
                    {
                        // attacking the mob
                        mobs[i].Attacked(player, (int)((float)GetDamage() * ((float)strength * 0.25)));
                        break;  // arrows only hit 1 mob at a time
                    }
                }
            }
        }

        // prints the basic info
        @Override
        public void PrintInfo() {System.out.println(GetName() + "\nDamage: " + GetDamage() + "\nRange: " + GetRange() + "\n");}

        // copies all info to a new object
        @Override
        public Ranged Copy() {return new Ranged(GetName(), GetType(), GetDamage(), GetRange());}
    }

    // for all lights
    static public class Light extends Items
    {
        private int glow;  // the level of glow 0 - 15

        // the constructor
        public Light(String name, ExtendedItems.ItemTypes itemType, int glow)
        {
            super(name, itemType);

            this.glow = glow;
        }

        // gets the light level
        @Override
        public int GetLight() {return glow;}

        // prints the basic info
        @Override
        public void PrintInfo() {System.out.println(GetName() + "\nGlow: " + glow + "\n");}

        // copies all info to a new object
        @Override
        public Light Copy() {return new Light(GetName(), GetType(), glow);}
    }

    /*  didn't end up being used, was originally planned
    // for all armor/shields/defensive items
    static public class Defense extends Items
    {
        private float defense;  // the defence provided from the item
        private boolean equipped;  // if the armor is active

        // the constructor
        public Defense(String name, ExtendedItems.ItemTypes itemType, float defense)
        {
            super(name, itemType);

            this.defense = defense;
            this.equipped = false;
        }

        // equips the armor
        public void Equip() {equipped = true;}

        // unequipped the armor
        public void UnEquip() {equipped = false;}

        // damages the player (if equipped) based on defense
        @Override
        public float Attacked()
        {
            // checking if the item is active/equipped in the armor slot
            if (equipped) return defense;  // damaging the player
            return 0;
        }

        // prints the basic info
        @Override
        public void PrintInfo() {System.out.println(GetName() + "\nDefense: " + defense + "\n");}

        // copies all info to a new object
        @Override
        public Defense Copy() {return new Defense(GetName(), GetType(), defense);}
    }
    */

    static public class Buff extends Items
    {
        // all the possible effects from rings
        public enum Effects
        {
            Glow,
            Defense,
            Strength,
            Health
        }

        // The names and types of effects of the buffs
        private String [] effectNames = {"Glow", "Defense", "Strength", "Health"};
        private Effects[] effectTypes = {Effects.Glow, Effects.Defense, Effects.Strength, Effects.Health};

        // the player
        private Player player;

        // for the rings affects
        private Effects[] effects;
        private int[] strengths;

        // the constructor
        public Buff(String name, ExtendedItems.ItemTypes itemType, Player player, Effects[] effects, int[] strengths) {
            super(name, itemType);

            this.effects = effects;
            this.strengths = strengths;

            this.player = player;
        }

        // activates the buffs powers
        public void ActivateBuff()
        {
            // doesn't get added to the inventory but does give a buff to the player when picked up
            for (int i = 0; i < this.effects.length; i++)
            {
                // checking which buff is currently being checked
                if      (effects[i] == Effects.Glow    ) player.AddGlow(strengths[i]);
                else if (effects[i] == Effects.Defense ) player.AddDefense(strengths[i]);
                else if (effects[i] == Effects.Strength) player.AddStrength(strengths[i]);
                else if (effects[i] == Effects.Health  ) player.AddHealth(strengths[i]);
            }
        }

        // gets the name of an effect
        private String GetEffectName(Effects effect)
        {
            // looping through all the effect types
            for (int i = 0; i < effectTypes.length; i++)
            {
                if (effectTypes[i] == effect) return effectNames[i];  // returning the name
            }
            return "No Effect Name Found";  // no name was found
        }

        // prints the basic info
        @Override
        public void PrintInfo()
        {
            System.out.println(GetName());  // printing the name of the ring

            // looping through all the effects
            for (int i = 0; i < effects.length; i++)
            {
                // printing the name of the effect
                System.out.println("Buff: " + GetEffectName(effects[i]) + " x " + strengths[i]);
            }

            // adding a new line so it renders correctly
            System.out.println("");
        }

        @Override
        public Buff Copy() {return new Buff(GetName(), GetType(), player, effects, strengths);}
    }
}
