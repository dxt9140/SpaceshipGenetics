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


/**
 * Declaration of the BattleSim class.
**/
public class BattleSim {

    /*
     * Static values used by the BattleSim     
    */
    private static final int ROUNDS_PER_PHASE = 5;
    private static final int SMALL_ASTEROID_DODGE_NUM = 15;
    private static final int MED_ASTEROID_DODGE_NUM = 25;
    private static final int LARGE_ASTEROID_DODGE_NUM = 40;
    private static final int SMALL_ASTEROID_DAM = 3;
    private static final int MED_ASTEROID_DAM = 7;
    private static final int LARGE_ASTEROID_DAM = 10;

    /**
     * Conduct a battle between two teams of Spaceships.
     * Parameters:
     *     team1 - First team of spaceships.
     *     team2 - Second team of spaceships.
     * Preconditions:
     *     team1 and team2 should be of equal size. This is enforced by higher
     *     methods.
     * Postconditions:
     *     The two survivor teams may be of any size between 0 and the original
     *     size of the team. They need not be equal.
    **/
    public static Pair<LinkedList<Spaceship>> battle( 
        LinkedList<Spaceship> team1, LinkedList<Spaceship> team2 ) {
        
        LinkedList<Spaceship> team1Survivors = new LinkedList<Spaceship>();
        LinkedList<Spaceship> team2Survivors = new LinkedList<Spaceship>();
  
        int listIndex = 0;
        Spaceship playerOne = team1.getElementAt( listIndex );
        Spaceship playerTwo = team2.getElementAt( listIndex );

        while ( playerOne != null || playerTwo != null ) {

            System.out.println("\nThis battle is fought by: ");
            playerOne.printStatBlock();
            playerTwo.printStatBlock();

            int battleResult = phaseOne( playerOne, playerTwo );
            switch ( battleResult ) {
                case -1:
                    // playerTwo survived, playerOne did not
                    team2Survivors.append( playerTwo );
                    break;
                case 0:
                    // Neither player survived
                    break;
                case 1:
                    // playerOne survived, playerTwo did not
                    team1Survivors.append( playerOne );
                    break;
                case 2:
                    // Both players survived
                    team1Survivors.append( playerOne );
                    team2Survivors.append( playerTwo );
                    break;
            } 

            listIndex++;
            playerOne = team1.getElementAt( listIndex );
            playerTwo = team2.getElementAt( listIndex );
        }

        // lol generics
        return new Pair<LinkedList<Spaceship>>( team1Survivors, team2Survivors );

    }

    /**
     * Conduct phase one. If there is no clear winner, proceed to phase two.
     * Parameters:
     *     playerOne - Spaceship from team1
     *     playerTwo - Spaceship from team2
    **/
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
            int asteroidDam, asteroidDodge;
            String astName;
            int asteroidSize = Math.abs( random.nextInt() ) % 3;
            if ( asteroidSize == 0 ) {
                asteroidDam = SMALL_ASTEROID_DAM;
                asteroidDodge = SMALL_ASTEROID_DODGE_NUM;
                astName = "small";
            } else if ( asteroidSize == 1 ) {
                asteroidDam = MED_ASTEROID_DAM;
                asteroidDodge = MED_ASTEROID_DODGE_NUM;
                astName = "medium";
            } else if ( asteroidSize == 2 ) {
                asteroidDam = LARGE_ASTEROID_DAM;
                asteroidDodge = LARGE_ASTEROID_DODGE_NUM;
                astName = "large";
            } else {
                return -99;
            }

            System.out.println("A " + astName + " asteroid is in the way!");

            System.out.println("Player One is approaching the " + astName + " asteroid!");
            int oneDodge = Math.abs( random.nextInt() ) % oneSpeed;
            if ( oneDodge >= asteroidDodge ) {
                System.out.println("Player One avoids the " + astName + " asteroid!");
            } else {
                System.out.println("Player One collides with the " + astName + " asteroid!");
                playerOne.takeDamage( asteroidDam );
            }
                
            System.out.println("Player Two is approaching the " + astName + " asteroid!");
            int twoDodge = Math.abs( random.nextInt() ) % twoSpeed;
            if ( twoDodge >= asteroidDodge ) {
                System.out.println("Player Two avoids the " + astName + " asteroid!");
            } else {
                System.out.println("Player Two collides with the " + astName + " asteroid!");
                playerTwo.takeDamage( asteroidDam );
            }


            if ( playerOne.getCurHealth() <= 0 ) {
                System.out.println("Player One is too damaged to continue...");
                playerOneStatus = false;
                break;
            }
            if ( playerTwo.getCurHealth() <= 0 ) {
                System.out.println("Player Two is too damaged to continue...");
                playerTwoStatus = false;
                break;
            }
            round++;
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
                System.out.println("Proceeding to the next battle..."); 
                return 0;
            case 4:
                return phaseTwo( playerOne, playerTwo );
        }

        // Shouldn't happen, but will result in an error code.
        return -99; 
    }

    /**
     * Conduct phase two of the battle wherein the ships get their first
     *  opportunity to fire at the enemy ship.
     * Parameters:
     *     playerOne - Spaceship from team1
     *     playerTwo - Spaceship from team2
    **/
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
            int asteroidDam, asteroidDodge;
            String astName;
            if ( asteroidSize == 0 ) {
                asteroidDam = SMALL_ASTEROID_DAM;
                asteroidDodge = SMALL_ASTEROID_DODGE_NUM;
                astName = "small";
            } else if ( asteroidSize == 1 ) {
                asteroidDam = MED_ASTEROID_DAM;
                asteroidDodge = MED_ASTEROID_DODGE_NUM;
                astName = "medium";
            } else {
                return -99;
            }
                
            if ( asteroidSize == 2 ) {
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

            } else {
                System.out.println("A " + astName + " asteroid is in the way!");

                System.out.println("Player One is approaching the " + astName + " asteroid!");
                int oneDodge = Math.abs( random.nextInt() ) % oneSpeed;
                if ( oneDodge >= asteroidDodge ) {
                    System.out.println("Player One avoids the " + astName + " asteroid!");

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
                    System.out.println("Player One collides with the " + astName + " asteroid!");
                    playerOne.takeDamage( asteroidDam );
                }
                
                System.out.println("Player Two is approaching the " + astName + " asteroid!");
                int twoDodge = Math.abs( random.nextInt() ) % twoSpeed;
                if ( twoDodge >= asteroidDodge ) {
                    System.out.println("Player Two avoids the " + astName + " asteroid!");
 
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
                    System.out.println("Player Two collides with the " + astName + " asteroid!");
                    playerTwo.takeDamage( asteroidDam );
                }
 
                if ( playerOne.getCurHealth() <= 0 ) {
                    System.out.println("Player One is too damaged to continue...");
                    playerOneStatus = false;
                    break;
                }
                if ( playerTwo.getCurHealth() <= 0 ) {
                    System.out.println("Player Two is too damaged to continue...");
                    playerTwoStatus = false;
                    break;
                }
          
            }
            round++;
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
                System.out.println("Proceeding to the next battle..."); 
                return 0;
            case 4:
                return phaseThree( playerOne, playerTwo );
        } 

        return -99;

    }

    /**
     * Conduct phase three of the battle. Here, the ships only exchange shots 
     *  and do not worry about asteroids.
     * Parameters:
     *     playerOne - Spaceship from team1
     *     playerTwo - Spaceship from team2
    **/
    private static int phaseThree( Spaceship playerOne, Spaceship playerTwo ) {
        System.out.println("The asteroids have cleared! The ships are in" +
            " open space! Prepare for combat!");

        boolean playerOneStatus = true;
        boolean playerTwoStatus = true;

        Random random = new Random();

        int oneSpeed = playerOne.getSpeed();
        int twoSpeed = playerTwo.getSpeed();
        int combinedSpeed = oneSpeed + twoSpeed;

        // Exchange shots until either player runs out of ammo.
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

        // Deplete the ammo of playerOne
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

        // Deplete the ammo if playerTwo
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

            if ( playerOne.getCurHealth() <= 0 ) {
                System.out.println("Player One is too damaged to continue...");
                playerOneStatus = false;
                break;
            }
            if ( playerTwo.getCurHealth() <= 0 ) {
                System.out.println("Player Two is too damaged to continue...");
                playerTwoStatus = false;
                break;
            }
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
                System.out.println("Proceeding to the next battle..."); 
                return 0;
            case 4:
                System.out.println("Both ships survived!");
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
        team1.append( Spaceship.randomShip() );
        team1.append( Spaceship.randomShip() );

        team2.append( Spaceship.randomShip() );
        team2.append( Spaceship.randomShip() );
        team2.append( Spaceship.randomShip() );

        battle( team1, team2 );
    }

}

