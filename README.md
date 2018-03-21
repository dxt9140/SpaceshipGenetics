
SpaceshipGenetics
README

This program uses a combination of a genetic algorithm and simulation
to determine several generations of Spaceships and their fitness.

Execution:
	
	$> java SpaceshipGenetics n <spaceships>

        n - The number of ships to use. Half this number will determine
            the size of the teams.
        <spaceships> - Specify from 0... n spaceships according to the
            method described below. The remainder will be randomly generated.

Encoding Format:
  
        CxExHxSxAxWx

        Each x should be a number 1-5 that determines the size of the module.
        The exception is the chassis, which should not be greater than 3.
