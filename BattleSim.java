/**
BattleSim.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 3/07/2018
File for simulating a battle between Spaceships. For use by the
SpaceshipGenetics program. The simulated battle is used to determine the
fitness of a ship.
**/

import java.util.Random;
import java.lang.Math;

public class BattleSim {

    private static final int ROUNDS_PER_PHASE = 5;
    private static final int SMALL_ASTEROID_DODGE_NUM = 15;
    private static final int MED_ASTEROID_DODGE_NUM = 25;
    private static final int LARGE_ASTEROID_DODGE_NUM = 40;
    private static final int SMALL_ASTEROID_DAM = 3;
    private static final int MED_ASTEROID_DAM = 7;
    private static final int LARGE_ASTEROID_DAM = 10;

    public static void battle( LinkedList<Spaceship> team1, 
        LinkedList<Spaceship> team2 ) {
        
        LinkedList<Spaceship> team1Survivors = new LinkedList<Spaceship>();
        LinkedList<Spaceship> team2Survivors = new LinkedList<Spaceship>();
  
        int listIndex = 0;
        Spaceship playerOne = team1.getRoot();
        Spaceship playerTwo = team2.getRoot();

        while ( playerOne != null && playerTwo != null ) {

            System.out.println("This battle is fought by: ");
            System.out.println("\tPlayer One: " + Encoder.shipToString(playerOne));
            System.out.println("\t\tHealth: " + playerOne.getMaxHealth());
            System.out.println("\t\tSpeed: " + playerOne.getSpeed());
            System.out.println("\t\tShield Strength: " + playerOne.getShieldMod());
            System.out.println("\t\tDamage: " + playerOne.getDamage()); 
            System.out.println("\t\tAmmo: " + playerOne.getMaxAmmo());
            System.out.println();
            System.out.println("\tPlayer Two: " + Encoder.shipToString(playerTwo));
            System.out.println("\t\tHealth: " + playerTwo.getMaxHealth());
            System.out.println("\t\tSpeed: " + playerTwo.getSpeed());
            System.out.println("\t\tShield Strength: " + playerTwo.getShieldMod());
            System.out.println("\t\tDamage: " + playerTwo.getDamage()); 
            System.out.println("\t\tAmmo: " + playerTwo.getMaxAmmo());
            System.out.println();

            int battleResult = phaseOne( playerOne, playerTwo );
            switch ( battleResult ) {
                case -1:
                    team2Survivors.append( playerTwo );
                    break;
                case 0:
                    break;
                case 1:
                    team1Survivors.append( playerOne );
                    break;
                case 2:
                    team1Survivors.append( playerOne );
                    team2Survivors.append( playerTwo );
                    break;
            } 

            break;
            // playerOne = playerOne.getNext();
            // playerTwo = playerTwo.getNext();
        }

    }

    private static int phaseOne( Spaceship playerOne, Spaceship playerTwo ) {
        System.out.println("A thick asteroid field appears ahead!"
            + " Begin evasive maneuveurs...");
    
        boolean playerOneStatus = true;
        boolean playerTwoStatus = true;
    
        int oneSpeed = playerOne.getSpeed();
        int twoSpeed = playerTwo.getSpeed();

        int round = 1;
        Random random = new Random();

        while ( round != ROUNDS_PER_PHASE ) {
            int asteroidSize = Math.abs( random.nextInt() ) % 3;

            if ( asteroidSize == 0 ) {
                // Small asteroid
                System.out.println("A small asteroid is in the way!");

                System.out.println("Player One is approaching the small asteroid!");
                int oneDodge = Math.abs( random.nextInt() ) % oneSpeed;
                if ( oneDodge >= SMALL_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player One avoids the small asteroid!");
                } else {
                    System.out.println("Player One collides with the small asteroid!");
                    playerOne.takeDamage( SMALL_ASTEROID_DAM );
                }
                
                System.out.println("Player Two is approaching the small asteroid!");
                int twoDodge = Math.abs( random.nextInt() ) % twoSpeed;
                if ( twoDodge >= SMALL_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player Two avoids the small asteroid!");
                } else {
                    System.out.println("Player Two collides with the small asteroid!");
                    playerTwo.takeDamage( SMALL_ASTEROID_DAM );
                }

            } else if ( asteroidSize == 1 ) {
                // Medium Asteroid
                System.out.println("A medium asteroid is in the way!");

                System.out.println("Player One is approaching the medium asteroid!");
                int oneDodge = Math.abs( random.nextInt() ) % oneSpeed;
                if ( oneDodge >= MED_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player One avoids the medium asteroid!");
                } else {
                    System.out.println("Player One collides with the small asteroid!");
                    playerOne.takeDamage( MED_ASTEROID_DAM );
                }

                System.out.println("Player Two is approaching the medium asteroid!");
                int twoDodge = Math.abs( random.nextInt() ) % twoSpeed;
                if ( twoDodge >= MED_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player Two avoids the medium asteroid!");
                } else {
                    System.out.println("Player Two collides with the medium asteroid!");
                    playerTwo.takeDamage( MED_ASTEROID_DAM );
                }

            } else if ( asteroidSize == 2 ) {
                // Large Asteroid
                System.out.println("A large asteroid is in the way!");

                System.out.println("Player One is approaching the large asteroid!");
                int oneDodge = Math.abs( random.nextInt() ) % oneSpeed;
                if ( oneDodge >= LARGE_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player One avoids the large asteroid!");
                } else {
                    System.out.println("Player One collides with the large asteroid!");
                    playerOne.takeDamage( LARGE_ASTEROID_DAM );
                }

                System.out.println("Player Two is approaching the large asteroid!");
                int twoDodge = Math.abs( random.nextInt() ) % twoSpeed;
                if ( twoDodge >= LARGE_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player Two avoids the large asteroid!");
                } else {
                    System.out.println("Player Two collides with the large asteroid!");
                    playerTwo.takeDamage( LARGE_ASTEROID_DAM );
                }

            }
            round++;
        }

        if ( playerOne.getCurHealth() <= 0 ) {
            System.out.println("Player One is too damaged to continue...");
            playerOneStatus = false;
        }
        if ( playerTwo.getCurHealth() <= 0 ) {
            System.out.println("Player Two is too damaged to continue...");
            playerTwoStatus = false;
        }

        int status = checkStatus( playerOneStatus, playerTwoStatus );
        switch( status ) {
            case 1:
                System.out.println("Player One wins the battle!");
                return 1;
            case 2:
                System.out.println("Player Two wins the battle!"); 
                return -1;
            case 3:
                System.out.println("Both ships were destroyed..."); 
                return 0;
            case 4:
                return phaseTwo( playerOne, playerTwo );
        }

        return -99; 

    }

    private static int phaseTwo( Spaceship playerOne, Spaceship playerTwo ) {

        System.out.println("The asteroid field is thinning out!"
            + " Prepare weapons for firing!");
    
        boolean playerOneStatus = true;
        boolean playerTwoStatus = true;
    
        int oneSpeed = playerOne.getSpeed();
        int twoSpeed = playerTwo.getSpeed();
        int combinedSpeed = oneSpeed + twoSpeed;

        int round = 1;
        Random random = new Random();

        while ( round != ROUNDS_PER_PHASE ) {
            int asteroidSize = Math.abs( random.nextInt() ) % 3;

            if ( asteroidSize == 0 ) {
                // Small asteroid
                System.out.println("A small asteroid is in the way!");

                System.out.println("Player One is approaching the small asteroid!");
                int oneDodge = Math.abs( random.nextInt() ) % oneSpeed;
                if ( oneDodge >= SMALL_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player One avoids the small asteroid!");

                    if ( playerOne.getAmmo() > 0 ) {
                        System.out.println("Player One taking aim at Player Two! Firing...");
                        int damageDone = playerOne.takeShot();
                        int toHit = Math.abs( random.nextInt() ) % combinedSpeed;
                        if ( toHit >= oneSpeed ) {
                            playerTwo.takeDamage( damageDone );
                        } else {
                            System.out.println("Miss...");
                        }  
                    }

                } else {
                    System.out.println("Player One collides with the small asteroid!");
                    playerOne.takeDamage( SMALL_ASTEROID_DAM );
                }
                
                System.out.println("Player Two is approaching the small asteroid!");
                int twoDodge = Math.abs( random.nextInt() ) % twoSpeed;
                if ( twoDodge >= SMALL_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player Two avoids the small asteroid!");

                    if ( playerTwo.getAmmo() > 0 ) {
                        System.out.println("Player Two taking aim at Player One! Firing...");
                        int damageDone = playerTwo.takeShot();
                        int toHit = Math.abs( random.nextInt() ) % combinedSpeed;
                        if ( toHit >= twoSpeed ) { 
                            playerOne.takeDamage( damageDone );
                        } else {
                            System.out.println("Miss...");
                        }
                    }

                } else {
                    System.out.println("Player Two collides with the small asteroid!");
                    playerTwo.takeDamage( SMALL_ASTEROID_DAM );
                }

            } else if ( asteroidSize == 1 ) {
                // Medium Asteroid
                System.out.println("A medium asteroid is in the way!");

                System.out.println("Player One is approaching the medium asteroid!");
                int oneDodge = Math.abs( random.nextInt() ) % oneSpeed;
                if ( oneDodge >= MED_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player One avoids the medium asteroid!");

                    if ( playerOne.getAmmo() > 0 ) {
                        System.out.println("Player One taking aim at Player Two! Firing...");
                        int damageDone = playerOne.takeShot();
                        int toHit = Math.abs( random.nextInt() ) % combinedSpeed;
                        if ( toHit >= oneSpeed ) {
                            playerTwo.takeDamage( damageDone );
                        } else {
                            System.out.println("Miss...");
                        }  
                    }

                } else {
                    System.out.println("Player One collides with the small asteroid!");
                    playerOne.takeDamage( MED_ASTEROID_DAM );
                }

                System.out.println("Player Two is approaching the medium asteroid!");
                int twoDodge = Math.abs( random.nextInt() ) % twoSpeed;
                if ( twoDodge >= MED_ASTEROID_DODGE_NUM ) {
                    System.out.println("Player Two avoids the medium asteroid!");

                    if ( playerOne.getAmmo() > 0 ) {
                        System.out.println("Player Two taking aim at Player One! Firing...");
                        int damageDone = playerTwo.takeShot();
                        int toHit = Math.abs( random.nextInt() ) % combinedSpeed;
                        if ( toHit >= twoSpeed ) {
                            playerOne.takeDamage( damageDone );
                        } else {
                            System.out.println("Miss...");
                        }   
                    }

                } else {
                    System.out.println("Player Two collides with the medium asteroid!");
                    playerTwo.takeDamage( MED_ASTEROID_DAM );
                }

            } else if ( asteroidSize == 2 ) {
                // No Asteroid
                System.out.println("No asteroids spotted!");

                if ( playerOne.getAmmo() > 0 ) {
                    System.out.println("Player One taking aim at Player Two! Firing...");
                    int damageDone = playerOne.takeShot();
                    int toHit = Math.abs( random.nextInt() ) % combinedSpeed;
                    if ( toHit >= oneSpeed ) {
                        playerTwo.takeDamage( damageDone );
                    } else {
                        System.out.println("Miss...");
                    }
                }

                if ( playerTwo.getAmmo() > 0 ) {
                    System.out.println("Player Two taking aim at Player One! Firing...");
                    int damageDone = playerTwo.takeShot();
                    int toHit = Math.abs( random.nextInt() ) % combinedSpeed;
                    if ( toHit >= twoSpeed ) {
                        playerOne.takeDamage( damageDone );
                    } else {
                        System.out.println("Miss...");
                    }   
                }

            }
            round++;
        }

        if ( playerOne.getCurHealth() <= 0 ) {
            System.out.println("Player One is too damaged to continue...");
            playerOneStatus = false;
        }
        if ( playerTwo.getCurHealth() <= 0 ) {
            System.out.println("Player Two is too damaged to continue...");
            playerTwoStatus = false;
        }

        int status = checkStatus( playerOneStatus, playerTwoStatus );
        switch( status ) {
            case 1:
                System.out.println("Player One wins the battle!");
                return 1;
            case 2:
                System.out.println("Player Two wins the battle!"); 
                return -1;
            case 3:
                System.out.println("Both ships were destroyed..."); 
                return 0;
            case 4:
                return phaseThree( playerOne, playerTwo );
        } 

        return -99;

    }

    private static int phaseThree( Spaceship playerOne, Spaceship playerTwo ) {
        System.out.println("The asteroids have cleared! The ships are in" +
            " open space! Prepare for combat!");

        boolean playerOneStatus = true;
        boolean playerTwoStatus = true;

        Random random = new Random();

        int oneSpeed = playerOne.getSpeed();
        int twoSpeed = playerTwo.getSpeed();
        int combinedSpeed = oneSpeed + twoSpeed;

        while ( playerTwo.getAmmo() > 0 && playerOne.getAmmo() > 0 ) {
           
            int toHit;
            int damageDone;
 
            System.out.println("Player One taking aim at Player Two! Firing...");
            damageDone = playerOne.takeShot();
            toHit = Math.abs( random.nextInt() ) % combinedSpeed;
            if ( toHit >= oneSpeed ) {
                playerTwo.takeDamage( damageDone );
            } else {
                System.out.println("Miss...");
            }
   
            System.out.println("Player Two taking aim at Player One! Firing...");
            damageDone = playerTwo.takeShot();
            toHit = Math.abs( random.nextInt() ) % combinedSpeed;
            if ( toHit >= twoSpeed ) {
                playerOne.takeDamage( damageDone );
            } else {
                System.out.println("Miss...");
            }
   
        }

        while ( playerOne.getAmmo() > 0 ) {
            System.out.println("Player Two is out of ammo! Player One takes aim!");
            int damageDone = playerOne.takeShot();
            int toHit = Math.abs( random.nextInt() ) % combinedSpeed;
            if ( toHit >= oneSpeed ) {
                System.out.println("That's a hit!");
                playerTwo.takeDamage( damageDone );
            } else {
                System.out.println("Miss...");
            }
        }

        while ( playerTwo.getAmmo() > 0 ) {
            System.out.println("Player One is out of ammo! Player Two takes aim!");
            int damageDone = playerTwo.takeShot();
            int toHit = Math.abs( random.nextInt() ) % combinedSpeed;
            if ( toHit >= twoSpeed ) {
                System.out.println("That's a hit!");
                playerOne.takeDamage( damageDone );
            } else {
                System.out.println("Miss...");
            }
        }

        if ( playerOne.getCurHealth() <= 0 ) {
            System.out.println("Player One is too damaged to continue...");
            playerOneStatus = false;
        }
        if ( playerTwo.getCurHealth() <= 0 ) {
            System.out.println("Player Two is too damaged to continue...");
            playerTwoStatus = false;
        }

        int status = checkStatus( playerOneStatus, playerTwoStatus );
        switch( status ) {
            case 1:
                System.out.println("Player One wins the battle!");
                return 1;
            case 2:
                System.out.println("Player Two wins the battle!"); 
                return -1;
            case 3:
                System.out.println("Both ships were destroyed..."); 
                return 0;
            case 4:
                return 2;
        }

        return -99; 
    }

    /*
     Helper functions
    */

    private static int checkStatus( boolean playerOneStatus, boolean playerTwoStatus ) {
        if ( playerOneStatus && !playerTwoStatus ) {
            return 1;
        } else if ( !playerOneStatus && playerTwoStatus ) {
            return 2;
        } else if ( playerOneStatus == false && playerTwoStatus == false ) {
            return 3;
        } else if ( playerOneStatus && playerTwoStatus ) {
            return 4;
        } else {
            return -99;
        }
    }

    /*
     Main function for unit testing
    */
    public static void main( String[] args ) {
        LinkedList<Spaceship> team1 = new LinkedList<Spaceship>();
        LinkedList<Spaceship> team2 = new LinkedList<Spaceship>();

        team1.append( Spaceship.randomShip() );
        team2.append( Spaceship.randomShip() );

        battle( team1, team2 );

    }

}

