package search.proto;

import java.util.EmptyQueueException;
import java.util.Vector;

/**
 * A FIFO Queue of objects, more-or-less copied from the leJOS class but in a new package so NetBeans can actually use it.
 */
public class Queue extends Vector {

  /**
   * creates a new Queue instance
   */
  public Queue() {
    // do nothing
    } // Queue()

  /**
   * pushes an object onto the Queue
   * @param anObject the object
   * @return Object the object pushed onto the Queue
   */
  public Object push(Object anObject) {
    // add the object to base vector
    addElement(anObject);
    return anObject;
  }

  /**
   * fetches an object from the start of the Queue
   * and removes it
   * @return Object the object removed from the start of the stock
   * @throws EmptyQueueException
   */
  public synchronized Object pop() throws EmptyQueueException {
    // get object
    Object popped = peek();
    // remove and return object
    removeElementAt(0);
    return popped;
  }

  /**
   * fetches an object from the start of the Queue
   * <br>does not remove it!
   * @return Object the object at the start of the Queue
   * @throws EmptyQueueException
   */
  public synchronized Object peek() throws EmptyQueueException {
    // empty Queue?
    if (size() == 0) {
      throw new EmptyQueueException();
    }
    // return first element
    return elementAt(0);
  }
}
