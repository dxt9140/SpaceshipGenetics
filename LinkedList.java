/**
LinkedList.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 3/2/2018
Simple LinkedList implementation.
**/

import java.lang.IndexOutOfBoundsException;

/**
 * Declaration of the LinkedList class. Uses generics for flexibility.
**/
public class LinkedList<E> {

    /**
     * Private Node class for use by the LinkedList class. Should be opaque
     *  to the user.
    **/
    private static class Node<E> {

        // Previous node in the list.
        private Node<E> prev;

        // The generic value of the node.
        private E val;

        // The node in the list.
        private Node<E> next;

        /*
         * Construct an instance of a node with a given value.
         * Parameters:
         *     val - Generic value of the node.
        */
        private Node( E val ) {
            this.val = val;
            this.prev = null;
            this.next = null;
        }

        /*
         * Get the next node.
        */
        public Node<E> getNext() {
            return this.next;
        }

        /*
         * Get the previous node.
        */
        public Node<E> getPrev() {
            return this.prev;
        }

        /*
         * Set the next value of this node.
         * Parameters:
         *     next - The node to set as the next node.
        */
        public void setNext( Node<E> next ) {
            this.next = next;
        }

        /*
         * Set the previous value of this node.
         * Parameters:
         *     prev - The node to set as the previous node.
        */
        public void setPrev( Node<E> prev ) {
            this.prev = prev;
        }

        /*
         * Return the value of this node.
        */
        public E getVal() {
            return this.val;
        }

        /*
         * Convert this node to a string.
        */
        public String toString() {
            return this.val.toString();
        }

    }

    // The root value of this list. 
    private Node<E> root = null;
  
    // The tail value of this list.
    private Node<E> tail = null;

    // The number of nodes in the list.
    private int size = 0;

    // Construct the list
    public LinkedList() {
    }

    /**
     * Add a value to the linked list. The value in encapsulated in a node.
     * Parameters:
     *     val - The generic value to add to the end of the list.
    **/
    public void append( E val ) {
        Node<E> node = new Node<E>( val );

        if ( this.getRootNode() == null ) {

            this.root = node;
            this.tail = node;

        } else {

            Node<E> consider = this.getRootNode();
            while( consider.getNext() != null ) {
                consider = consider.getNext();
            }

            consider.setNext( node );
            node.setPrev( consider );
 
            this.tail = node;
        }
 
        this.size++;  
    }

    /**
     * Insert a value into a specific index of the list.
     * Parameters:
     *     val - The value to add to the list. Converted to a node.
     *     index - The index location to add to the list.
    **/
    public void insert( E val, int index ) {
        if ( index == 0 ) {
            this.prepend( val );
            return;
        }

        Node<E> node = new Node<E>( val );
        Node<E> consider = this.getRootNode();
        int hops = 0;

        try {

            while ( hops != index ) {
                consider = consider.getNext();
                hops++;

                if ( consider == null ) {
                    throw new IndexOutOfBoundsException();
                }
            }

            Node<E> temp = consider.getPrev();
            temp.setNext( node );
            consider.setPrev( node );
            node.setPrev( temp );
            node.setNext( consider );

            while ( this.root.getPrev() != null ) {
                this.root = this.root.getPrev();
            }
            while ( this.tail.getNext() != null ) {
                this.tail = this.tail.getNext();
            }

        } catch ( IndexOutOfBoundsException exc ) {
            System.err.println("Error: Error adding " + val + " to list.");
            System.err.println("Error: Index " + index + " out of bounds.");
            return;
        }

        this.size++;
    }

    /**
     * Insert a value to the beginning of the list.
     * Parameters:
     *     val - The value to add to the beginning of the list.
    **/
    public void prepend( E val ) {
        Node<E> node = new Node<E>( val );
        Node<E> root = this.getRootNode();
        node.setNext( root );
        root.setPrev( node );

        this.root = node;
        this.size++;
    }

    /**
     * Remove the node at a particular index.
     * Parameters:
     *     index - The index of the location to remove the value from.
     * Returns:
     *     Returns the value of the node at index.
    **/
    public E remove( int index ) {

        try {

            if ( index == 0 ) {
                Node<E> node = this.getRootNode();
                this.root = node.getNext();
                this.root.setPrev( null );
                return node.val;
            }

            Node<E> consider = this.getRootNode();
            int hops = 0;
            while ( hops != index ) {
                if ( consider == null ) {
                    throw new IndexOutOfBoundsException();
                }
                consider = consider.getNext();
                hops++;
            }

            Node<E> node = consider;
            Node<E> temp = consider.getPrev();
            temp.setNext( consider.getNext() );
            consider.getNext().setPrev( temp );

            while ( this.root.getPrev() != null ) {
                this.root = this.root.getPrev();
            }
            while ( this.tail.getNext() != null ) {
                this.tail = this.tail.getNext();
            }
 
            this.size--;
            return node.val;

        } catch ( IndexOutOfBoundsException exc ) {
            System.err.println("Error: Error removing element at index " + 
                index + ".");
            System.err.println("Error: Index " + index + " out of bounds.");
            return null;
        }

    }

    /**
     * Obtain the value of the node at index. The node remains in the list.
     * Parameters:
     *     index - The index location to obtain the value.
     * Returns:
     *     Returns the value contained by the node at index.
    **/
    public E getElementAt( int index ) {
        int hops = 0;
        Node<E> consider = this.getRootNode();

        try {
            while ( hops != index ) {
                consider = consider.getNext();
                hops++;
                if ( consider == null ) {
                    throw new IndexOutOfBoundsException();
                }
            }
        } catch ( Exception exc ) {
            // System.err.println("Error: Error getting element at index " +
            //    index + ".");
            // System.err.println("Error: Index " + index + " out of bounds.");
            return null;
        }

        return consider.getVal();
    }

    /**
     * Obtain the root node of this list. Should not be available to the user.
    **/
    private Node<E> getRootNode() {
        return this.root;
    }

    /**
     * Return the value of the root node.
    **/
    public E getRoot() {
        return this.getRootNode().getVal();
    }

    /**
     * Return the size of this linked list.
    **/
    public int size() {
        return this.size;
    }

    /**
     * Print the linked list in a human-readable format.
    **/
    public void printLinkedList() {
        Node<E> n = this.getRootNode();
        while ( n != null ) {
            System.out.print( n.val + " " );
            n = n.next;
        }
        System.out.println();
    }

    /*
     * Main class for unit testing.
    */
    public static void main( String[] args ) {

        LinkedList<Integer> intList = new LinkedList<Integer>();
        
        intList.append( 8 );
        intList.printLinkedList();

        intList.append( 7 );
        intList.printLinkedList();

        intList.insert( 4, 1 );
        intList.printLinkedList();

        intList.insert( 5, 0 );
        intList.printLinkedList();

        intList.insert( 99, 99 );
        intList.printLinkedList();

        intList.prepend( 1 );
        intList.printLinkedList();

        Integer removed = intList.remove( 1 );
        intList.printLinkedList();
        System.out.println( "("+ removed + ")" );

        Integer removed_2 = intList.remove( 0 );
        intList.printLinkedList();
        System.out.println( "(" + removed_2 + ")" );

        Integer removed_3 = intList.remove( 99 );
        intList.printLinkedList();
        System.out.println( "(" + removed_3 + ")" );

        LinkedList<Spaceship> shipList = new LinkedList<Spaceship>();
    
        shipList.append( Spaceship.randomShip() );
        shipList.printLinkedList();
  
        shipList.append( Spaceship.randomShip() );
        shipList.printLinkedList();

        shipList.insert( Spaceship.randomShip(), 1 );
        shipList.printLinkedList();

        shipList.remove( 1 );
        shipList.printLinkedList();

    }

}

