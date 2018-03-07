/**
LinkedList.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 3/2/2018
Simple LinkedList implementation.
**/

import java.lang.IndexOutOfBoundsException;

public class LinkedList<E> {

    private static class Node<E> {

        private Node<E> prev;
        private E val;
        private Node<E> next;

        private Node( E val ) {
            this.val = val;
            this.prev = null;
            this.next = null;
        }

        public Node<E> getNext() {
            return this.next;
        }

        public Node<E> getPrev() {
            return this.prev;
        }

        public void setNext( Node<E> next ) {
            this.next = next;
        }

        public void setPrev( Node<E> prev ) {
            this.prev = prev;
        }

    }

    private Node<E> root = null;
    private Node<E> tail = null;

    public LinkedList() {
    }

    public void append( E val ) {
        Node<E> node = new Node<E>( val );

        if ( this.getRoot() == null ) {

            this.root = node;
            this.tail = node;

        } else {

            Node<E> consider = this.getRoot();
            while( consider.getNext() != null ) {
                consider = consider.getNext();
            }

            consider.setNext( node );
            node.setPrev( consider );
 
            this.tail = node;
        }
        
    }

    public void insert( E val, int index ) {
        if ( index == 0 ) {
            this.prepend( val );
            return;
        }

        Node<E> node = new Node<E>( val );
        Node<E> consider = this.getRoot();
        int hops = 0;

        try {

            while ( hops != index ) {
                if ( consider == null ) {
                    throw new IndexOutOfBoundsException();
                }

                consider = consider.getNext();
                hops++;
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

    }

    public void prepend( E val ) {
        Node<E> node = new Node<E>( val );
        Node<E> root = this.getRoot();
        node.setNext( root );
        root.setPrev( node );

        this.root = node;
    }

    public E remove( int index ) {

        try {

            if ( index == 0 ) {
                Node<E> node = this.getRoot();
                this.root = node.getNext();
                this.root.setPrev( null );
                return node.val;
            }

            Node<E> consider = this.getRoot();
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

            return node.val;

        } catch ( IndexOutOfBoundsException exc ) {
            System.err.println("Error: Error removing element at index " + 
                index + ".");
            System.err.println("Error: Index " + index + " out of bounds.");
            return null;
        }

    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void printLinkedList() {
        Node<E> n = this.getRoot();
        while ( n != null ) {
            System.out.print( n.val + " " );
            n = n.next;
        }
        System.out.println();
    }

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

