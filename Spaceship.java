/**
Spaceship.java
Author: Dominick Taylor
Created: 2/26/2018
Class used to classify a spaceship object. For use in the genetic algortithm for
creating random spaceships using a standard encoding.
**/

public class Spaceship {		

    private Chassis chassis;
    private Engine engine;
    private Shield shield;
    private Weapons weapons;
    private CargoHold hold;
    private Armor armor;

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

