package util;

import java.util.ArrayList;
import java.util.EmptyQueueException;

/**
 * A generic FIFO queue of objects.
 */
public class Queue<E> extends ArrayList<E> {

  /**
   * Creates a new empty Queue instance
   */
  public Queue() {
  }

  /**
   * pushes an object onto the Queue
   * @param anObject the object
   * @return Object the object pushed onto the Queue
   */
  public E push(E anObject) {
    // add the object to base vector
    add(anObject);
    return anObject;
  }

  /**
   * Fetches an object from the start of the Queue
   * and removes it
   * @return Object the object removed from the head of the queue.
   * @throws EmptyQueueException
   */
  public synchronized E pop() throws EmptyQueueException {
    // get object
    E popped = peek();
    // remove and return object
    remove(0);
    return popped;
  }

  /**
   * Fetches an object from the start of the Queue but does not remove it.
   * 
   * @return Object the object at the start of the queue.
   * @throws EmptyQueueException
   */
  public synchronized E peek() throws EmptyQueueException {
    // empty Queue?
    if (size() == 0) {
      throw new EmptyQueueException();
    }
    // return first element
    return get(0);
  }
} 

