/**
SpaceshipGenetics.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 2/26/2018
Main class to run the SpaceshipGenetics program. This class asks for input and
handles arguments.
**/

import java.lang.NumberFormatException;

public class SpaceshipGenetics {

    private static final String encodingMessage =
        "Encoding format: CxExHxSxAxWx where each x is a number from 1-"
        + SpaceshipGenetics.MAX_MODULE_SIZE + ".";

    public static final int MAX_SHIPS = 24;
    public static final int MAX_TEAM_SIZE = 12;
    private static final int NUM_GENERATIONS = 50;

    public static final int MAX_MODULE_SIZE = 5;

    public static final int MAX_CHASSIS_SIZE = 3;

    public static int TEAM_SIZE;

    public static void main( String[] args ) {

        if ( args.length < 1 ) {
            usage();
        }

        int numShips = MAX_SHIPS;
        try {
            numShips = Integer.parseInt( args[0] );
            if ( numShips > MAX_SHIPS ) {
                usage(); 
            }

        } catch ( NumberFormatException exc ) {
            usage();
        }

        if ( numShips % 2 == 1 ) {
            numShips += 1;
        }

        TEAM_SIZE = numShips / 2;

        int count = 0;
        Spaceship[] ships = new Spaceship[ numShips ];
        for ( int i = 1; i < args.length; i++ ) {
            String string = args[i];
            if ( Encoder.verifyEncoding( string ) ) {
                ships[count] = Encoder.stringToShip( string );
                count++;
            } else {
                encodingError( args[i] ); 
            }
        }

        while ( count != numShips ) {
            Spaceship ship = Spaceship.randomShip();
            ships[count] = Spaceship.randomShip();
            count++;
        }

        LinkedList<Spaceship> team1 = new LinkedList<Spaceship>();
        LinkedList<Spaceship> team2 = new LinkedList<Spaceship>();

        for ( int i = 0; i < numShips; i++ ) {
            if ( i % 2 == 0 ) {
                team1.append( ships[i] ); 
            } else {
                team2.append( ships[i] );
            }
        }
 
        team1.printLinkedList();

        int generation = 0;
        while ( generation < NUM_GENERATIONS ) {
            System.out.println("Beginning the battle!");
            Pair<LinkedList<Spaceship>> survivors = BattleSim.battle( team1, team2 );
            System.out.println("Battle completed... time to rebuild.");

            LinkedList<Spaceship> team1Survivors = survivors.getFirst();
            if ( team1Survivors.size() == 0 ) {
                System.out.println("No survivors!? Generating more...");
                while ( team1Survivors.size() < TEAM_SIZE ) {
                    team1Survivors.append( Spaceship.randomShip() );
                }
            }

            LinkedList<Spaceship> team2Survivors = survivors.getSecond();
            if ( team2Survivors.size() == 0 ) {
                System.out.println("No survivors!? Generating more...");
                while ( team2Survivors.size() < TEAM_SIZE ) {
                    team2Survivors.append( Spaceship.randomShip() );
                }
            }

            System.out.println("Commencing ship breeding.");
            Pair<LinkedList<Spaceship>> nextGeneration =
                ShipBreeder.nextGeneration( team1Survivors, team2Survivors );
            team1 = nextGeneration.getFirst();
            team2 = nextGeneration.getSecond(); 
            System.out.println("Next generation completed. Who will reign supreme?");
            generation++;
        }

    }

    private static void usage() {
        System.err.println( "Usage: java n SpaceshipGenetics [encoding]" );
        System.err.println( "Usage: n in the number of ships to generate, must"
            + " not be greater than " + MAX_SHIPS + " ." );
        System.err.println( encodingMessage );
        System.exit(-1);
    }

    private static void encodingError( String error ) {
        System.err.println("Error: " + error + " is not a valid encoding");
        System.err.println( encodingMessage );
    }

}

