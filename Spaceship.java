/**
Spaceship.java
Author: Dominick Taylor
Created: 2/26/2018
Class used to classify a spaceship object. For use in the genetic algortithm for
creating random spaceships using a standard encoding.
**/

public class Spaceship {		

    public Chassis chassis;
    public Engine engine;
    public Shield shield;
    public Weapons weapons;
    public CargoHold hold;
    public Armor armor;

    public Spaceship( Chassis chassis, Engine engine, Shield shield,
        Weapons weapons, CargoHold hold, Armor armor ) {

        this.chassis = chassis;
        this.engine = engine;
        this.shield = shield;
        this.weapons = weapons;
        this.hold = hold;
        this.armor = armor;
    }

    public static void main( String[] args ) {

    }

}

