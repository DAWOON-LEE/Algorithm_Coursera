package assignment3;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int DEFAULT_CAPACITY = 8;

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        items[size++] = item;
        randomSwap();
        resize();
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item tmp = items[--size];
        items[size] = null;

        resize();
        return tmp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(size);

        return items[index];
    }

    // return an independent iterator over items in random order
//    public Iterator<Item> iterator() {
//        return new ListIterator();
//    }
//
//    private class ListIterator implements Iterator<Item> {
//        RandomizedQueue<Item> copy;
//
//        public ListIterator() {
//            copy = new RandomizedQueue<Item>();
//
//            copy.items = (Item[]) new Object[items.length];
//            System.arraycopy(items, 0, copy.items, 0, size);
//
//            copy.size = size;
//        }
//
//        @Override
//        public boolean hasNext() {
//            return copy.size > 0;
//        }
//
//        @Override
//        public void remove() {
//            throw new UnsupportedOperationException();
//        }
//
//        @Override
//        public Item next() {
//            if (!hasNext()) {
//                throw new NoSuchElementException();
//            }
//
//            return copy.dequeue();
//        }
//    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int current;
        private int[] randomIndices;

        public ArrayIterator() {
            current = 0;

            randomIndices = new int[size];
            for (int j = 0; j < size; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return items[randomIndices[current++]];
        }
    }

    private void resize() {
        int capacity = items.length;
        float loadFactor = calculateLoadFactor();

        if (loadFactor >= 1) {
            Item[] newItems = (Item[]) new Object[2 * capacity];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        } else if (loadFactor < 0.25) {
            Item[] newItems = (Item[]) new Object[capacity / 2];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        }
    }

    private void randomSwap() {
        int randomIndex = StdRandom.uniform(size);

        Item tmp = items[randomIndex];
        items[randomIndex] = items[size - 1];
        items[size - 1] = tmp;
    }

    private float calculateLoadFactor() {
        return (float) size / items.length;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();

            if (s.equals("-")) {
                StdOut.print(rq.dequeue());
            }
            else {
                rq.enqueue(s);
            }
        }
    }
}
