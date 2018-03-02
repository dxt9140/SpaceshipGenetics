/**
SpaceshipGenetics.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 2/26/2018
Main class to run the SpaceshipGenetics program. This class asks for input and
handles arguments.
**/

public class SpaceshipGenetics {

    private static final String encodingMessage =
        "Encoding format: CxExHxSxAxWx where each x is a number from 1-3";

    public static void main( String[] args ) {

        if ( args.length < 1 ) {
            usage();
        }

        Encoder encoder = new Encoder();

        Spaceship[] ships = new Spaceship[ args.length ];
        for ( int i = 0; i < args.length; i++ ) {
            String string = args[i];
            if ( encoder.verifyEncoding( string ) ) {
                ships[i] = encoder.stringToShip( string );
                System.out.println( encoder.shipToString( ships[i] ) );
            } else {
                encodingError( args[i] ); 
            }
        }

    }

    private static void usage() {
        System.err.println( "Usage: java SpaceshipGenetics [encoding]" );
        System.err.println( encodingMessage );
        System.exit(-1);
    }

    private static void encodingError( String error ) {
        System.err.println("Error: " + error + " is not a valid encoding");
        System.err.println( encodingMessage );
    }

}

