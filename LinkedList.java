/**
LinkedList.java
Author: Dominick Taylor (dxt9140@g.rit.edu)
Created: 3/2/2018
Simple LinkedList implementation.
**/

import java.lang.IndexOutOfBoundsException;

public class LinkedList<E> {

    private static class Node<E> {

        private Node prev;
        private E val;
        private Node next;

        private Node( E val ) {
            this.val = val;
            this.prev = null;
            this.next = null;
        }

        public Node getNext() {
            return this.next;
        }

        public Node getPrev() {
            return this.prev;
        }

        public void setNext( Node next ) {
            this.next = next;
        }

        public void setPrev( Node prev ) {
            this.prev = prev;
        }

    }

    private Node<E> root = null;
    private Node<E> tail = null;

    public LinkedList() {
    }

    public void append( E val ) {
        Node<E> node = new Node<E>( val );

        if ( this.root == null ) {
            this.root = node;
            this.tail = node;
            node.setPrev( null );
            node.setNext( null );
        } else {
            Node consider = this.root;
            while( consider.getNext() != null ) {
                consider = consider.getNext();
            }

            consider.setNext( node );
            node.setNext( null );
            node.setPrev( consider );
        }
        
    }

    public void insert( E val, int index ) {
        Node<E> node = new Node<E>( val );
        Node consider = this.root;
        int hops = 0;

        try {

            while ( consider != null && hops != index ) {
                if ( consider == null ) {
                    throw new IndexOutOfBoundsException();
                }

                consider = consider.getNext();
                hops++;
            }

            Node temp = consider.getPrev();
            node.setPrev( temp );
            consider.setPrev( node );
            node.setNext( consider );

            while ( this.root.getPrev() != null ) {
                this.root = this.root.getPrev();
            }
            while ( this.tail.getNext() != null ) {
                this.tail = this.tail.getNext();
            }

        } catch ( IndexOutOfBoundsException exc ) {
            System.err.println("Index out of bounds.");
            return;
        }

    }

    public Node getRoot() {
        return this.root;
    }

    public static void main( String[] args ) {

        LinkedList<Integer> intList = new LinkedList<Integer>();
        
        intList.append( 8 );
        intList.append( 7 );
        intList.insert( 4, 1 );

        Node n = intList.getRoot();
        while ( n != null ) {
            System.out.print( n.val + " " );
            n = n.next;
        }
        System.out.println();
    }

}

