package assignment3;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int count = 0;

        RandomizedQueue<String> RQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();

            if (count < k) {
                RQueue.enqueue(str);
            }

            if (count >= k && StdRandom.uniform(count + 1) < k) {
                RQueue.dequeue();
                RQueue.enqueue(str);
            }

            count++;
        }

        while (k-- > 0) {
            StdOut.println(RQueue.dequeue());
        }
    }
}
