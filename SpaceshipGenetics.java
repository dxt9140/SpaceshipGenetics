/**
SpaceshipGenetics.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 2/26/2018
Main class to run the SpaceshipGenetics program. This class asks for input and
handles arguments.
**/

public class SpaceshipGenetics {

    public static void main( String[] args ) {

        if ( args.length < 1 ) {
            usage();
        }

        Encoder encoder = new Encoder();

        String[] ships = new String[ args.length-1 ];
        for ( int i = 1; i < args.length; i++ ) {
            String string = args[i];
            if ( encoder.verifyEncoding( string ) ) {

            } 
        }

    }

    private static void usage() {
        System.err.println( "Usage: java SpaceshipGenetics <encoding>" );
        System.err.println( "Encoding format: CxExHxSxAxWx where each x is a" +
            " number from 1-3" );
    }

}

