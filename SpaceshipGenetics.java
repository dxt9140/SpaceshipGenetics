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

    public static final int MAX_SHIPS = 12;

    public static final int MAX_MODULE_SIZE = 5;

    public static final int MAX_CHASSIS_SIZE = 3;

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

        int count = 0;
        Spaceship[] ships = new Spaceship[ numShips ];
        for ( int i = 1; i < args.length; i++ ) {
            String string = args[i];
            if ( Encoder.verifyEncoding( string ) ) {
                ships[count] = Encoder.stringToShip( string );
                System.out.println( Encoder.shipToString( ships[count] ) );
                count++;
            } else {
                encodingError( args[i] ); 
            }
        }

        while ( count != numShips ) {
            Spaceship ship = Spaceship.randomShip();
            ships[count] = ship;
            System.out.println( Encoder.shipToString( ship ) );
            count++;
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

