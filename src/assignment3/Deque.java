package assignment3;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;

        private Node(Item item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }
    }

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;

        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldLast = last;
        last = new Node(item);
        last.prev = oldLast;

        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item tmp = first.item;

        if (size == 1) {
            last = null;
            first = null;
        } else {
            first = first.next;
            first.prev = null;
        }

        size--;
        return tmp;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item tmp = last.item;

        if (size == 1) {
            last = null;
            first = null;
        } else {
            last = last.prev;
            last.next = null;
        }

        size--;
        return tmp;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                Item tmp = current.item;
                current = current.next;
                return tmp;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();

            if (str.equals("-")) {
                StdOut.print(deque.removeLast());
            } else {
                deque.addFirst(str);
            }
        }
    }
}