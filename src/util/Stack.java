/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;
import java.util.ArrayList;
import java.util.EmptyStackException;
/**
 * 
 * @author Jeremiah Via
 */
public class Stack<E> extends ArrayList<E>
{
    public Stack(){}

    public void push(E e){
        add(0, e);
    }

    public synchronized E pop() throws EmptyStackException {
        E popped = peek();
        remove(0);
        return popped;
    }

    public synchronized E peek() throws EmptyStackException {
        if ( size() == 0){
            throw new EmptyStackException();
        }

        return get(0);
    }
}
