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

    public Chassis chassis;
    public Engine engine;
    public Shield shield;
    public Weapons weapons;
    public CargoHold hold;
    public Armor armor;

    public Spaceship( Chassis chassis, Engine engine, CargoHold hold,
        Shield shield, Armor armor, Weapons weapons ) {

        this.chassis = chassis;
        this.engine = engine;
        this.shield = shield;
        this.weapons = weapons;
        this.hold = hold;
        this.armor = armor;
    }

    public static Spaceship randomShip() {

        Random random = new Random();
        
        Chassis chassis;
        Engine engine;
        CargoHold hold;
        Shield shield;
        Armor armor;
        Weapons weapons;

        int chassisSize = Math.abs( random.nextInt() ) % 3 + 1;
        int engineSize = Math.abs( random.nextInt() ) % 3 + 1;
        int holdSize = Math.abs( random.nextInt() ) % 3 + 1;
        int shieldSize = Math.abs( random.nextInt() ) % 3 + 1;
        int armorSize = Math.abs( random.nextInt() ) % 3 + 1;
        int weaponSize = Math.abs( random.nextInt() ) % 3 + 1;

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
        return new Encoder().shipToString( this );
    }

    public static void main( String[] args ) {
        Spaceship ship = Spaceship.randomShip();
        System.out.println( ship );
    }

}

