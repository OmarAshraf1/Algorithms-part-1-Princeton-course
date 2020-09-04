/**
 * Name: omar
 * Date:8/7/2020
 * Description:deque impl
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    private Node<Item> head = null;
    private Node<Item> tail = null;

    private static class Node<Item> {
        Item val;
        Node<Item> next;
        Node<Item> prev;


    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can't be null");
        }
        Node<Item> newnode = new Node<>();
        newnode.val = item;
        if (head == null) {
            head = newnode;
            tail = newnode;
        }
        else {
            newnode.next = head;
            head.prev = newnode;
            head = newnode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can't be null");
        }
        Node<Item> newnode = new Node<>();
        newnode.val = item;
        if (head == null) {
            head = newnode;
            tail = newnode;
        }
        else {
            tail.next = newnode;
            newnode.prev = tail;
            tail = newnode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("queue is empty");
        }
        Item res = head.val;
        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return res;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (head == null) {
            throw new NoSuchElementException("queue is empty");
        }
        Item res = head.val;
        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return res;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node<Item> temp = head;

            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("no more elements");
                }
                Item i = temp.val;
                temp = temp.next;
                return i;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void print() {
        System.out.print("[");
        for (Item i : this) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
        System.out.println("size: " + size());
        System.out.println(isEmpty());
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> que = new Deque<>();
        que.addFirst(1);
        que.print();
        que.removeFirst();
        que.print();

        que.addLast(2);
        que.print();
        que.removeLast();
        que.print();

        que.addFirst(3);
        que.print();
        que.removeLast();
        que.print();

        que.addLast(4);
        que.print();
        que.removeFirst();
        que.print();


        System.out.println("size: " + que.size());
        System.out.println(que.isEmpty());
        que.addFirst(2);
        que.addFirst(3);
        que.addFirst(4);
        que.addFirst(5);
        que.addLast(6);
        que.addLast(7);
        que.addLast(8);
        que.addLast(9);
        que.addLast(10);
        que.addFirst(0);
        que.print();
        System.out.println("size: " + que.size());

        System.out.println(que.removeFirst());
        System.out.println(que.removeFirst());
        System.out.println(que.removeFirst());
        que.print();
        System.out.println("size: " + que.size());

        System.out.println(que.removeLast());
        System.out.println(que.removeLast());
        System.out.println(que.removeLast());
        que.print();
        System.out.println("size: " + que.size());
    }

}
