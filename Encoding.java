/**
Encoder.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 2/28/2018
File to provide support for creating a Spaceship out of a string and vice
versa.
**/

public class Encoder {

    Spaceship stringToShip( String encoding ) {

	chassisSize = Integer.parseInt( encoding.charAt( 1 ) );
	Chassis chassis = new Chassis( chassisSize );

	engineSize = Integer.parseInt( encoding.charAt( 3 ) );
	Engine engine = new Engine( engineSize );

	armorSize = Integer.parseInt( encoding.charAt( 5 ) );
	Armor armor = new Armor( armorSize );

        shieldSize = Integer.parseInt( encoding.charAt( 7 ) );
        Shield shield = new Shield( shieldSize );

        holdSize = Integer.parseInt( encoding.charAt( 9 ) );
	CargoHold hold = new CargoHold( holdSize );

        weaponSize = Integer.parseInt( encoding.charAt( 11 ) );
        Weapons weapons = new Weapons( weaponSize );

        Spaceship ship = new Spaceship( chassis, engine, shield, 
            weapons, hold, armor );

        return ship;

    }

    String shipToString( Spaceship ship ) {

        String encoding;
	
	encoding += "C" + ship.chassis.size.toString();
	encoding += "E" + ship.engines.size.toString();
	encoding += "A" + ship.armor.size.toString();
	encoding += "S" + ship.shield.size.toString();
	encoding += "H" + ship.hold.size.toString();
	encoding += "W" + ship.weapons.size.toString();

	return encoding;

    }

    boolean verifyEncoding( String encoding ) {
        if ( encoding.length() < 12 ) {
            return false;
        }

        for ( int i = 0; i < encoding.length(); i++ ) {

            if ( i % 2 == 0 ) {

                switch ( encoding.charAt( i ) ) {

                    case 'C':
                    case 'E':
                    case 'A':
                    case 'S':
                    case 'H':
                    case 'W':
                        continue;

                    default:
                        return false;
                }

            } else if ( i % 2 == 1 ) {
            
                int number = Integer.parseInt( encoding.charAt( i ) );
                if ( number < 1 || number > 3 ) {
                    return false;
                }

            }

        }
    } 

}

