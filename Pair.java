/**
Pair.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 3/9/2018
It's a pair
**/

// Does this need documentation?
public class Pair<E> {

    private E first;
    private E second;

    public Pair( E first, E second ) {
        this.first = first;
        this.second = second;
    }

    public E getFirst() {
        return this.first;
    }

    public E getSecond() {
        return this.second;
    }

}

