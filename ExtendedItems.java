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
        Ring
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
        public void Attack(Player player, BaseMob[] mobs)
        {
            // attacks a mob
        }

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
        public void Attack(Player player, BaseMob[] mobs)
        {
            // attacks a mob
        }
        // copies all info to a new object
        @Override
        public Ranged Copy() {return new Ranged(GetName(), GetType(), GetDamage(), GetRange());}
    }

    // for all lights
    static public class Light extends Items
    {
        private int glow;  // the level of glow 0 - 10

        // the constructor
        public Light(String name, ExtendedItems.ItemTypes itemType, int glow)
        {
            super(name, itemType);

            this.glow = glow;
        }

        // gets the light level
        @Override
        public int GetLight() {return glow;}

        // copies all info to a new object
        @Override
        public Light Copy() {return new Light(GetName(), GetType(), glow);}
    }

    // for all armor/shields/defensive items
    static public class Defense extends Items
    {
        private float defense;  // the defence provided from the item
        private boolean equipped;  // if the arrmor is active

        // the constructor
        public Defense(String name, ExtendedItems.ItemTypes itemType, float defense)
        {
            super(name, itemType);

            this.defense = defense;
            this.equipped = false;
        }

        // equips the armor
        public void Equip() {equipped = true;}

        // unequips the armor
        public void UnEquip() {equipped = false;}

        // damages the player (if equipped) based on defense
        @Override
        public float Attacked()
        {
            // checking if the item is active/equipped in the armor slot
            if (equipped) return defense;  // damaging the player
            return 0;
        }

        // copies all info to a new object
        @Override
        public Defense Copy() {return new Defense(GetName(), GetType(), defense);}
    }

    // an extension of defense for rings
    static public class Ring extends Defense
    {
        // all the possible effects from rings
        public enum Effects
        {
            Glow,
            Defense,
            Regen,
            Strength
        }

        // for the rings affects
        private Effects[] effects;
        private int[] strengths;

        // the constructor
        public Ring(String name, ExtendedItems.ItemTypes itemType, Effects[] effects, int[] strengths)
        {
            super(name, itemType, 0);
        }

        public Ring Copy() {return new Ring(GetName(), GetType(), effects, strengths);}
    }

}
