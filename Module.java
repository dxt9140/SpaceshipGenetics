/**
Module.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 2/26/2018
Abstract class used to define a general module.
**/

public abstract class Module {

    private int size;

    public Module( int size ) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

}

