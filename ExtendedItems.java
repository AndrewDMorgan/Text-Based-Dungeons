import javafx.scene.effect.Glow;

import java.util.*;

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

    // for all melee weapons (inherited by the ranged class)
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
        public int GetRange() {return GetEnchantmentManager().GetRange(range);}

        // gets the damage
        public int GetDamage() {return GetEnchantmentManager().GetDamage(damage);}

        // attacks any nearby mobs
        @Override
        public void Attack(Player player, BaseMob[] mobs, int strength, int dx, int dy)
        {
            // the enchantment manager
            Enchants enchantmentManager = GetEnchantmentManager();

            // getting the new stats from enchantments
            int newDamage = enchantmentManager.GetDamage(damage);
            int newRange  = enchantmentManager.GetRange(range);

            // the amount of life steal
            int lifeSteal = enchantmentManager.GetLife();

            // the crit chance
            int crit = enchantmentManager.GetCrit();

            // looping through all the mobs
            for (int i = 0; i < mobs.length; i++)
            {
                // looping through the length of the weapon
                for (int r = 1; r <= newRange; r++)
                {
                    // checking if the mobs is on the position being attacked
                    if (player.GetX() + dx * r == mobs[i].GetX() && player.GetY() + dy * r == mobs[i].GetY())
                    {
                        // checking if the attack is a crit
                        int damageBoost = 1;
                        if (Math.random() * 10 < crit) damageBoost = 2;

                        // attacking the mob
                        mobs[i].Attacked(player, (int)((float)newDamage * ((float)strength * 0.25)));

                        // checking if the mob is dead
                        if (!mobs[i].GetAlive())
                        {
                            // give health based on life steal
                            if (Math.random() * 10 < lifeSteal) player.AddHealth(1);  // giving the player health
                        }
                    }
                }
            }
        }

        // prints the basic info
        @Override
        public void PrintInfo() {System.out.println(GetName() + "\nDamage: " + GetEnchantmentManager().GetDamage(damage) + "\n" + GetEnchantmentManager().GetInfo());}

        // copies all info to a new object
        @Override
        public Melee Copy() {return new Melee(GetName(), GetType(), damage, range);}
    }

    // for all ranged weapons
    static public class Ranged extends Melee
    {
        private int piercing = 1;

        // the constructor
        public Ranged(String name, ExtendedItems.ItemTypes itemType, int damage, int range, int piercing)
        {
            super(name, itemType, damage, range);

            // initializing the stats/variables
            this.piercing = piercing;
        }

        // attacking the mobs
        @Override
        public void Attack(Player player, BaseMob[] mobs, int strength, int dx, int dy)
        {
            // the new buffs
            int newRange = GetRange();
            int newDamage = GetDamage();

            // the number of mobs the arrow went through
            int numPierced = 0;

            // the new max amount of piercing after enchantments
            int maxPierce = GetEnchantmentManager().GetPierce(piercing);

            // looping through all the mobs
            for (int i = 0; i < mobs.length; i++)
            {
                // looping through the length of the weapon
                for (int r = 1; r <= newRange; r++)
                {
                    // checking if the mobs is on the position being attacked
                    if (player.GetX() + dx * r == mobs[i].GetX() && player.GetY() + dy * r == mobs[i].GetY())
                    {
                        // attacking the mob
                        mobs[i].Attacked(player, (int)((float)newDamage * ((float)strength * 0.25)));

                        // another pierced mob
                        numPierced++;

                        // checking how many mobs the arrow hit
                        if (numPierced >= maxPierce)
                        {
                            // breaking out of the loops sense the arrow has stopped moving
                            i = mobs.length;
                            r = GetRange();
                        }
                    }
                }
            }
        }

        // prints the basic info
        @Override
        public void PrintInfo() {System.out.println(GetName() + "\nDamage: " + GetDamage() + "\nRange: " + GetRange() + "\nPiercing: " + piercing + "\n" + GetEnchantmentManager().GetInfo());}

        // copies all info to a new object
        @Override
        public Ranged Copy() {return new Ranged(GetName(), GetType(), GetDamage(), GetRange(), piercing);}
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
        public int GetLight() {return GetEnchantmentManager().GetGlow(glow);}

        // prints the basic info
        @Override
        public void PrintInfo() {System.out.println(GetName() + "\nGlow: " + GetEnchantmentManager().GetGlow(glow) + "\n" + GetEnchantmentManager().GetInfo());}

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

    // a manager for enchantments
    static public class Enchants
    {
        // the properties that can be enchanted
        public enum EnchantTypes
        {
            Damage,  // swords & bows - +damage
            Range,   // swords & bows - +range
            Crit,    // swords - +critical hit chance (2x damage)
            Life,    // swords - +chance of health after killing mob
            Pierce,  // bows - +number of mobs arrow can go through
            Glow     // anything - +glow (on anything even swords and bows)
        }

        // all the enchantments
        private ArrayList<EnchantTypes> enchants = new ArrayList<EnchantTypes>();

        // the number of each enchantment
        private int numDamage = 0;
        private int numRange  = 0;
        private int numCrit   = 0;
        private int numLife   = 0;
        private int numPierce = 0;
        private int numGlow   = 0;

        // roman numerals for the level of the enchantment (stolen from minecraft because it looks cool, sort of like the lighting)
        private final String[] romanNumerals = {"|", "||", "|||", "|V", "V"};

        // the type of the item   0; sword, 1; bow, 2; something else (so a light)
        private ItemTypes itemType;

        // the different enchantments possible on the different items
        private int[][] itemEnchantments = {
                {0, 1, 2, 3, 5},
                {0, 1, 4, 5},
                {5}};

        // the enchantment types that line up with the ids
        private EnchantTypes[] enchantLookUp = {EnchantTypes.Damage, EnchantTypes.Range, EnchantTypes.Crit, EnchantTypes.Life, EnchantTypes.Pierce, EnchantTypes.Glow};

        // the constructor
        public Enchants(ItemTypes itemType)
        {
            // initializing the inputted variables
            this.itemType = itemType;
        }

        // returns a string with all the enchantments
        public String GetInfo()
        {
            // generating the text based on the enchantments and their levels
            String enchantText = "";
            if (numDamage > 0) enchantText += "Sharpness "       + romanNumerals[numDamage - 1] + "\n";
            if (numRange  > 0) enchantText += "Enlarging "        + romanNumerals[numRange  - 1] + "\n";
            if (numCrit   > 0) enchantText += "Critical Hit " + romanNumerals[numCrit   - 1] + "\n";
            if (numLife   > 0) enchantText += "Life Steal  "  + romanNumerals[numLife   - 1] + "\n";
            if (numPierce > 0) enchantText += "Piercing "     + romanNumerals[numPierce - 1] + "\n";
            if (numGlow   > 0) enchantText += "Brightness "         + romanNumerals[numGlow   - 1] + "\n";

            return enchantText;  // the final text
        }

        // gets a random valid enchantment
        public EnchantTypes GetRandomEnchantment()
        {
            int itemTypeIndex = -1;  // should never be -1

            // checking which type of item is being held
            if      (itemType == ItemTypes.Melee ) itemTypeIndex = 0;
            else if (itemType == ItemTypes.Ranged) itemTypeIndex = 1;
            else itemTypeIndex = 2;  // a torch (for now at least)

            // getting the enchantment types for the item
            int[] enchantmentIDs = itemEnchantments[itemTypeIndex];

            // the valid enchantments for enchanting
            int numValid = 0;
            EnchantTypes[] validEnchantments = new EnchantTypes[enchantmentIDs.length];

            // the number of each enchantment lining up with the ids
            int[] numEnchants = {numDamage, numRange, numCrit, numLife, numPierce, numGlow};

            // looping through the enchantments
            for (int i = 0; i < enchantmentIDs.length; i++)
            {
                // checking if the enchantment is valid
                if (numEnchants[enchantmentIDs[i]] < 5)
                {
                    // adding the enchantment
                    validEnchantments[numValid] = enchantLookUp[enchantmentIDs[i]];
                    numValid++;  // incrementing the number of valid enchantmentsd
                }
            }

            // getting the random index for the random enchantment
            int randomIndex = Math.min((int)(Math.random() * numValid), numValid - 1);

            if (randomIndex >= 0) return validEnchantments[randomIndex];  // the final enchantment
            return null;  // no valid enchants aka your item is fully enchanted
        }

        // adds a new enchantment, returns if the enchantment was added or not
        public boolean AddEnchantment(EnchantTypes enchantment)
        {
            // adding up the total number of the enchantment
            if      (numDamage < 5 && enchantment == EnchantTypes.Damage) numDamage++;
            else if (numRange  < 5 && enchantment == EnchantTypes.Range ) numRange ++;
            else if (numCrit   < 5 && enchantment == EnchantTypes.Crit  ) numCrit  ++;
            else if (numLife   < 5 && enchantment == EnchantTypes.Life  ) numLife  ++;
            else if (numPierce < 5 && enchantment == EnchantTypes.Pierce) numPierce++;
            else if (numGlow   < 5 && enchantment == EnchantTypes.Glow  ) numGlow  ++;
            else return false;  // the enchantment was not added

            enchants.add(enchantment);  // adding the enchantment
            return true;  // the enchantment was added
        }

        // gets the name of an enchantment type
        public String GetEnchantName(EnchantTypes enchantment)
        {
            // checking which enchantment was inputted and returning its name
            if      (enchantment == EnchantTypes.Damage) return "Damage";
            else if (enchantment == EnchantTypes.Range ) return "Range";
            else if (enchantment == EnchantTypes.Crit  ) return "Critical Hit";
            else if (enchantment == EnchantTypes.Life  ) return "Life Steal";
            else if (enchantment == EnchantTypes.Pierce) return "Piercing";
            else if (enchantment == EnchantTypes.Glow  ) return "Glow";
            return "Invalid Enchantment";
        }

        // gets the new stat based on enchantments
        public int GetDamage(int damage) {return damage + numDamage;}
        public int GetRange (int range ) {return range + numRange;}
        public int GetPierce(int pierce) {return pierce + numPierce;}
        public int GetGlow  (int glow  ) {return glow + numGlow;}
        public int GetCrit() {return numCrit;}
        public int GetLife() {return numLife;}
    }
}
