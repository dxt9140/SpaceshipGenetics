/**
Module.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 2/26/2018
Abstract class used to define a general module.
**/

/**
 * Abstract class used to provide a generalization for each of the modules.
**/
public abstract class Module {

    // Size of the module
    private int size;

    // Create a module given a size.
    public Module( int size ) {
        this.size = size;
    }

    // Return the size of this Module.
    public int getSize() {
        return this.size;
    }

}

