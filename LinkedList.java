/**
LinkedList.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 3/2/2018
Simple LinkedList implementation.
**/

import java.lang.IndexOutOfBoundsException;

public class LinkedList {

    Node root = null;
    Node tail = null;

    public LinkedList() {
    }

    public void append( Node node ) {
        if ( this.root == null ) {
            this.root = node;
            this.tail = node;
            node.prev = null;
            node.next = null;
        } else {
            Node consider = this.root;
            while( consider.next != null ) {
                consider = consider.next;
            }

            consider.next = node;
            node.next = null;
            node.prev = consider;
        }
        
    }

    public void insert( Node node, int index ) {
        Node consider = this.root;
        int hops = 0;

        try {

            while ( consider.next != null && hops != index ) {
                if ( consider.next == null ) {
                    throw new IndexOutOfBoundsException();
                }

                consider = consider.next;
                hops++;
            }

            Node temp = consider.next;
            consider.next = node;
            node.prev = consider;
            node.next = temp;

        } catch ( IndexOutOfBoundsException exc ) {
            System.err.println("Index out of bounds.");
            return;
        }

    }

    public static void main( String[] args ) {

    }

}

