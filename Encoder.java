/**
Encoder.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 2/28/2018
File to provide support for creating a Spaceship out of a string and vice
versa.
**/

public class Encoder {

    Spaceship stringToShip( String encoding ) {

        char chassisChar = encoding.charAt( 1 );
	int chassisSize = Integer.parseInt( Character.toString( chassisChar ) );
	Chassis chassis = new Chassis( chassisSize );

        char engineChar = encoding.charAt( 3 );
	int engineSize = Integer.parseInt( Character.toString( engineChar ) );
	Engine engine = new Engine( engineSize );

        char holdChar = encoding.charAt( 5 );
        int holdSize = Integer.parseInt( Character.toString( holdChar ) );
	CargoHold hold = new CargoHold( holdSize );

        char shieldChar = encoding.charAt( 7 );
        int shieldSize = Integer.parseInt( Character.toString( shieldChar ) );
        Shield shield = new Shield( shieldSize );

        char armorChar = encoding.charAt( 9 );
	int armorSize = Integer.parseInt( Character.toString( armorChar ) );
	Armor armor = new Armor( armorSize );

        char weaponChar = encoding.charAt( 11 );
        int weaponSize = Integer.parseInt( Character.toString( weaponChar ) );
        Weapons weapons = new Weapons( weaponSize );

        Spaceship ship = new Spaceship( chassis, engine, hold, 
            shield, armor, weapons );

        return ship;

    }

    String shipToString( Spaceship ship ) {

        String encoding = new String();
	
	encoding += "C" + Integer.toString( ship.chassis.getSize() );
	encoding += "E" + Integer.toString( ship.engine.getSize() );
	encoding += "H" + Integer.toString( ship.hold.getSize() );
	encoding += "S" + Integer.toString( ship.shield.getSize() );
	encoding += "A" + Integer.toString( ship.armor.getSize() );
	encoding += "W" + Integer.toString( ship.weapons.getSize() );

	return encoding;

    }

    boolean verifyEncoding( String encoding ) {
        if ( encoding.length() < 12 ) {
            System.out.println("a");
            System.out.println( encoding.length() );
            return false;
        }

        for ( int i = 0; i < encoding.length(); i++ ) {

            if ( i % 2 == 0 ) {

                switch ( encoding.charAt( i ) ) {

                    case 'C':
                    case 'E':
                    case 'H':
                    case 'S':
                    case 'A':
                    case 'W':
                        continue;

                    default:
                        System.out.println("b");
                        return false;
                }

            } else if ( i % 2 == 1 ) {
           
                char numChar = encoding.charAt( i ); 
                int number = Integer.parseInt( Character.toString( numChar ) );
                if ( number < 1 || number > 3 ) {
                    System.out.println("c");
                    return false;
                }

            }

        }
       
        return true;
    } 

}

