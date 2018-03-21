/**
ShipBreeder.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 3/9/2018
Class used to facilitate the simulated breeding of Spaceships.
**/

import java.util.Random;
import java.lang.Math;

public class ShipBreeder {

    /**
     * Breed two teams of Spaceships to create a next generation.
     * Parameters:
     *     team1 - The first team of ships to breed together.
     *     team2 - The second team of ships to breed together.
     * Returns:
     *     Returns a pair of LinkedLists that serve as the two teams.
    **/
    public static Pair<LinkedList<Spaceship>> nextGeneration( 
        LinkedList<Spaceship> team1, LinkedList<Spaceship> team2 ) {

        LinkedList<Spaceship> nextGen1 = new LinkedList<Spaceship>();
        LinkedList<Spaceship> nextGen2 = new LinkedList<Spaceship>();

        // If either team does not have at least 1 pair of ships, add random
        // ones for breeding purposes.
        while ( team1.size() < 2 ) {
            team1.append( Spaceship.randomShip() );
        }
        while ( team2.size() < 2 ) {
            team2.append( Spaceship.randomShip() );
        }

        auxiliaryBreed( team1, nextGen1 );
        auxiliaryBreed( team2, nextGen2 );

        return new Pair<LinkedList<Spaceship>>( nextGen1, nextGen2 );

    }

    /**
     * Intermediary method used to finalize the actual breeding of two teams.
     * Parameters:
     *     parentTeam - The previous generation of ships that participate in breeding.
     *     childTeam - The list containing the next generation of ships.
     * Preconditions:
     *     parentTeam should have at least 2 ships.
     *     childTeam should be an empty LinkedList.
     * Postconditions:
     *     childTeam will contain enough ships to match the TEAM_SIZE value of
     *         SpaceshipGenetics class.
    **/
    private static void auxiliaryBreed( 
        LinkedList<Spaceship> parentTeam, LinkedList<Spaceship> childTeam ) {

        Random random = new Random();
        while ( childTeam.size() < SpaceshipGenetics.TEAM_SIZE ) {
            int fatherIndex = Math.abs( random.nextInt() ) % parentTeam.size();
            int motherIndex = Math.abs( random.nextInt() ) % parentTeam.size();

            // Ensuring that the mother spaceship will never be the same ship as
            // the father.
            while ( motherIndex == fatherIndex ) {
                motherIndex = Math.abs( random.nextInt() ) % parentTeam.size();
            }

            Spaceship father = parentTeam.getElementAt( fatherIndex );
            Spaceship mother = parentTeam.getElementAt( motherIndex );

            Spaceship child = breed( father, mother );
            
            childTeam.append( child );
        }
    }

    /**
     * Given two Spaceships, breed them together to create a child.
     * Note that this method is not intended to make any declarations or
     *  assumptions about gender or gender roles.
     * Parameters:
     *     father - The "father" ship.
     *     mother - The "mother" ship.
     * Returns:
     *     A new Spaceship with randomly decided traits from the the mother 
     *      and father. 
    **/
    private static Spaceship breed( Spaceship father, Spaceship mother ) {

        boolean[] phenotypeArray = new boolean[6];

        Random random = new Random();
      
        // Decide which modules will be taken from which parents.  
        for ( int i = 0; i < 6; i++ ) {
            int bool = Math.abs( random.nextInt() ) % 2;
            if ( bool == 1 ) {
                phenotypeArray[i] = true;
            } else if ( bool == 0 ) {
                phenotypeArray[i] = false;
            }
        }

        Chassis chassis;
        Engine engine;
        CargoHold hold;
        Shield shield;
        Armor armor;
        Weapons weapons;

        if ( phenotypeArray[0] ) {
            chassis = new Chassis( father.chassis.getSize() );
        } else { 
            chassis = new Chassis( mother.chassis.getSize() );
        }

        if ( phenotypeArray[1] ) {
            engine = new Engine( father.engine.getSize() );
        } else { 
            engine = new Engine( mother.engine.getSize() );
        }

        if ( phenotypeArray[2] ) {
            hold = new CargoHold( father.hold.getSize() );
        } else { 
            hold = new CargoHold( mother.hold.getSize() );
        }

        if ( phenotypeArray[3] ) {
            shield = new Shield( father.shield.getSize() );
        } else { 
            shield = new Shield( mother.shield.getSize() );
        }

        if ( phenotypeArray[4] ) {
            armor = new Armor( father.armor.getSize() );
        } else { 
            armor = new Armor( mother.armor.getSize() );
        }

        if ( phenotypeArray[5] ) {
            weapons = new Weapons( father.weapons.getSize() );
        } else { 
            weapons = new Weapons( mother.weapons.getSize() );
        }

        return new Spaceship( chassis, engine, hold, shield, armor, weapons );
    }
}

