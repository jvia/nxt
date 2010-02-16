/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 *
 * Code copied from Java 1.6 source and repurposed for use in leJOS.
 *
 * @author nah
 */
public class Collections {

  private Collections() {
  }

  /*
   * Tuning parameters for algorithms - Many of the List algorithms have
   * two implementations, one of which is appropriate for RandomAccess
   * lists, the other for "sequential."  Often, the random access variant
   * yields better performance on small sequential access lists.  The
   * tuning parameters below determine the cutoff point for what constitutes
   * a "small" sequential access list for each algorithm.  The values below
   * were empirically determined to work well for LinkedList. Hopefully
   * they should be reasonable for other sequential access List
   * implementations.  Those doing performance work on this code would
   * do well to validate the values of these parameters from time to time.
   * (The first word of each tuning parameter name is the algorithm to which
   * it applies.)
   */

  private static final int REVERSE_THRESHOLD = 18;







  /**
   * Converts the given collection to an array. Added because the .toArray() method in leJOS's ArrayList appears to be broken.
   * 
   * @param _list The collection to convert.
   * @return _list as an array of Objects.
   */
  public static Object[] toArray(Collection _list) {
    int size = _list.size();
    return toArray(_list, new Object[size]);
  }

  /**
   *Converts the given collection to an array, filling the input array which is then returned. Added because the .toArray() method in leJOS's ArrayList appears to be broken.
   *
   * @param <T> The return type for each array element.
   * @param <E> The input type for each collection element.
   * @param _list The collection to convert.
   * @param dest The array to fill.
   * @return _list as an array.
   */
  @SuppressWarnings("unchecked")
  public static <T, E> T[] toArray(Collection<E> _list, T[] dest) {
    int j = 0;
    int max = dest.length;

    //copy each element from the input collection into the array
    for (E element : _list) {
      if (j >= max) {
        throw new UnsupportedOperationException(
                "Array is too small and expanding is not supported.");
      }

      //whether elements are compatible with dest can only be checked at runtime
      dest[j++] = (T) element;
    }

    return dest;
  }

  /**
   * Sorts the specified list into ascending order, according to the
   * <i>natural ordering</i> of its elements.  All elements in the list must
   * implement the <tt>Comparable</tt> interface.  Furthermore, all elements
   * in the list must be <i>mutually comparable</i> (that is,
   * <tt>e1.compareTo(e2)</tt> must not throw a <tt>ClassCastException</tt>
   * for any elements <tt>e1</tt> and <tt>e2</tt> in the list).<p>
   *
   * This sort is guaranteed to be <i>stable</i>:  equal elements will
   * not be reordered as a result of the sort.<p>
   *
   * The specified list must be modifiable, but need not be resizable.<p>
   *
   * The sorting algorithm is a modified mergesort (in which the merge is
   * omitted if the highest element in the low sublist is less than the
   * lowest element in the high sublist).  This algorithm offers guaranteed
   * n log(n) performance.
   *
   * This implementation dumps the specified list into an array, sorts
   * the array, and iterates over the list resetting each element
   * from the corresponding position in the array.  This avoids the
   * n<sup>2</sup> log(n) performance that would result from attempting
   * to sort a linked list in place.
   *
   * @param  list the list to be sorted.
   * @throws ClassCastException if the list contains elements that are not
   *	       <i>mutually comparable</i> (for example, strings and integers).
   * @throws UnsupportedOperationException if the specified list's
   *	       list-iterator does not support the <tt>set</tt> operation.
   * @see Comparable
   */
  public static <T extends Comparable<? super T>> void sort(List<T> list) {
    Object[] a = toArray(list);
    Arrays.sort(a);
    ListIterator<T> i = list.listIterator();
    for (int j = 0; j < a.length; j++) {
      i.next();
      i.set((T) a[j]);
    }
  }

  /**
   * Sorts the specified list according to the order induced by the
   * specified comparator.  All elements in the list must be <i>mutually
   * comparable</i> using the specified comparator (that is,
   * <tt>c.compare(e1, e2)</tt> must not throw a <tt>ClassCastException</tt>
   * for any elements <tt>e1</tt> and <tt>e2</tt> in the list).<p>
   *
   * This sort is guaranteed to be <i>stable</i>:  equal elements will
   * not be reordered as a result of the sort.<p>
   *
   * The sorting algorithm is a modified mergesort (in which the merge is
   * omitted if the highest element in the low sublist is less than the
   * lowest element in the high sublist).  This algorithm offers guaranteed
   * n log(n) performance.
   *
   * The specified list must be modifiable, but need not be resizable.
   * This implementation dumps the specified list into an array, sorts
   * the array, and iterates over the list resetting each element
   * from the corresponding position in the array.  This avoids the
   * n<sup>2</sup> log(n) performance that would result from attempting
   * to sort a linked list in place.
   *
   * @param  list the list to be sorted.
   * @param  c the comparator to determine the order of the list.  A
   *        <tt>null</tt> value indicates that the elements' <i>natural
   *        ordering</i> should be used.
   * @throws ClassCastException if the list contains elements that are not
   *	       <i>mutually comparable</i> using the specified comparator.
   * @throws UnsupportedOperationException if the specified list's
   *	       list-iterator does not support the <tt>set</tt> operation.
   * @see Comparator
   */
  public static <T> void sort(List<T> list, Comparator<? super T> c) {
    Object[] a = list.toArray();
    Arrays.sort(a, (Comparator) c);
    ListIterator i = list.listIterator();
    for (int j = 0; j < a.length; j++) {
      i.next();
      i.set(a[j]);
    }
  }

  /**
   * Reverses the order of the elements in the specified list.<p>
   *
   * This method runs in linear time.
   *
   * @param  list the list whose elements are to be reversed.
   * @throws UnsupportedOperationException if the specified list or
   *         its list-iterator does not support the <tt>set</tt> operation.
   */
  public static void reverse(List<?> list) {
    int size = list.size();
    if (size < REVERSE_THRESHOLD || list instanceof RandomAccess) {
      for (int i = 0, mid = size >> 1, j = size - 1; i < mid; i++, j--) {
        swap(list, i, j);
      }
    } else {
      ListIterator fwd = list.listIterator();
      ListIterator rev = list.listIterator(size);
      for (int i = 0, mid = list.size() >> 1; i < mid; i++) {
        Object tmp = fwd.next();
        fwd.set(rev.previous());
        rev.set(tmp);
      }
    }
  }

  /**
   * Swaps the elements at the specified positions in the specified list.
   * (If the specified positions are equal, invoking this method leaves
   * the list unchanged.)
   *
   * @param list The list in which to swap elements.
   * @param i the index of one element to be swapped.
   * @param j the index of the other element to be swapped.
   * @throws IndexOutOfBoundsException if either <tt>i</tt> or <tt>j</tt>
   *         is out of range (i &lt; 0 || i &gt;= list.size()
   *         || j &lt; 0 || j &gt;= list.size()).
   * @since 1.4
   */
  public static void swap(List<?> list, int i, int j) {
    final List l = list;
    l.set(i, l.set(j, l.get(i)));
  }
}
