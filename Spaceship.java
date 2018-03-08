/**
Spaceship.java
Author: Dominick Taylor
Created: 2/26/2018
Class used to classify a spaceship object. For use in the genetic algortithm for
creating random spaceships using a standard encoding.
**/

import java.util.Random;
import java.lang.Math;

public class Spaceship {

    /*
     Numbers used to change modifiers and such.The values are equal to the
     base value given per unit of chassis size. For example, base health is
     calculated by multiplying BASE_HEALTH_MOD * chassis size, otherwise,
     a multiple of 20. This, a ship with chassis size 3 would have a base
     health of 60, subject to be changed by other values.
    */
    private static final int BASE_HEALTH_MOD = 20;
    private static final int ARMOR_HEALTH_BONUS = 10;
    private static final int BASE_WEAPON_DAMAGE = 10;
    private static final double BASE_SHIELD_MOD = 0.20;
    private static final double CHASSIS_SHIELD_PEN = 0.05;	
    private static final int BASE_SPEED_MOD = 40;
    private static final int SPEED_MOD = 30;
    private static final int CHASSIS_SPEED_PEN = 8;
    private static final int ARMOR_SPEED_PEN = 5;
    private static final int BASE_AMMO_MOD = 3;
    private static final int AMMO_CARGO_BONUS = 2;

    private static final int BASE_NUM_MODULES = 4;
    private static final int BONUS_MODULES = 3;

    /*
     Modules that the ship is equipped with.
    */
    public Chassis chassis;
    public Engine engine;
    public Shield shield;
    public Weapons weapons;
    public CargoHold hold;
    public Armor armor;

    /*
     Statistics used by the battle sim. Calculated during object 
     initialization, depends on the ship's modules.
    */
    private int speed;
    private double max_health;
    private double cur_health;
    private double shield_modifier;
    private int damage_per_shot;
    private int max_ammunition;
    private int cur_ammunition;

    public Spaceship( Chassis chassis, Engine engine, CargoHold hold,
        Shield shield, Armor armor, Weapons weapons ) {

        this.chassis = chassis;
        this.engine = engine;
        this.shield = shield;
        this.weapons = weapons;
        this.hold = hold;
        this.armor = armor;

        // Initialize some of the statistics used by the BattleSim.
        
        this.max_health = (chassis.getSize() * BASE_HEALTH_MOD) +
            (armor.getSize() * ARMOR_HEALTH_BONUS); 
        this.cur_health = max_health;
        this.speed = (engine.getSize() * BASE_SPEED_MOD) -
            (chassis.getSize() * CHASSIS_SPEED_PEN) - 
            (armor.getSize() * ARMOR_SPEED_PEN) + SPEED_MOD; 
        this.shield_modifier = (shield.getSize() * BASE_SHIELD_MOD) -
            (chassis.getSize() * CHASSIS_SHIELD_PEN);
        this.damage_per_shot = (weapons.getSize() * BASE_WEAPON_DAMAGE);
        this.max_ammunition = (weapons.getSize() * BASE_AMMO_MOD) +
            (hold.getSize() * AMMO_CARGO_BONUS);
        this.cur_ammunition = max_ammunition;

    }

    public double getMaxHealth() {
        return this.max_health;
    }

    public double getCurHealth() {
        return this.cur_health;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getDamage() {
        return this.damage_per_shot;
    }

    public int getMaxAmmo() {
        return this.max_ammunition;
    }

    public int getAmmo() {
        return this.cur_ammunition;
    }

    public double getShieldMod() {
        return this.shield_modifier;
    }

    public int takeShot() {
        this.cur_ammunition--;
        return this.damage_per_shot;
    }


    public void takeDamage( int damage ) {
        double damage_taken = damage * (1-this.shield_modifier);
        System.out.printf("%.2f damage taken!", damage_taken);
        this.cur_health -= damage_taken;
    }

    public static Spaceship randomShip() {

        Random random = new Random();
        
        Chassis chassis;
        Engine engine;
        CargoHold hold;
        Shield shield;
        Armor armor;
        Weapons weapons;

        int max_chassis = SpaceshipGenetics.MAX_CHASSIS_SIZE;
        int max = SpaceshipGenetics.MAX_MODULE_SIZE;

        int chassisSize = Math.abs( random.nextInt() ) % max_chassis + 1;
     
        int module_slots = chassisSize * BASE_NUM_MODULES + BONUS_MODULES;
        int modules_remaining = 5;

        int range = min( max, module_slots-modules_remaining );

        int engineSize = Math.abs( random.nextInt() ) % range + 1;
        modules_remaining--;
        module_slots -= engineSize;
        range = min( max, module_slots-modules_remaining );

        int holdSize = Math.abs( random.nextInt() ) % range + 1;
        modules_remaining--;
        module_slots -= holdSize;
        range = min( max, module_slots-modules_remaining );

        int shieldSize = Math.abs( random.nextInt() ) % range + 1;
        modules_remaining--;
        module_slots -= shieldSize;
        range = min( max, module_slots-modules_remaining );

        int armorSize = Math.abs( random.nextInt() ) % range + 1;
        modules_remaining--;
        module_slots -= armorSize;
        range = min( max, module_slots-modules_remaining );

        int weaponSize = Math.abs( random.nextInt() ) % range + 1;
        modules_remaining--;
        module_slots -= weaponSize;

        chassis = new Chassis( chassisSize );
        engine = new Engine( engineSize );
        hold = new CargoHold( holdSize );
        shield = new Shield( shieldSize );
        armor = new Armor( armorSize );
        weapons = new Weapons( weaponSize );

        Spaceship ship = new Spaceship( chassis, engine, hold, shield,
            armor, weapons );

        return ship;

    }

    public String toString() {
        return Encoder.shipToString( this );
    }

    /*
     Private helper functions
    */
    private static int min( int a, int b ) {
        if ( a < b ) {
            return a;
        } else {
            return b;
        }
    }

    public static void main( String[] args ) {
        Spaceship ship = Spaceship.randomShip();
        System.out.println( ship );
    }

}

