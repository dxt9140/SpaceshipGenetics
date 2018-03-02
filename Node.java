/**
Node.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 3/2/2018
Simple node class to use in conjunction with the LinkedList class and the
SpaceshipGenetics program.
**/

public class Node<E> {

    public Node prev;
    public E capsule;
    public Node next;

    public Node( E capsule ) {
        this.capsule = capsule;
    }

    public void append( Node root ) {
    
    }

}
