package util;

public class Arrays {

  /**
   * Sorts the specified array of objects into ascending order, according to
   * the {@linkplain Comparable natural ordering}
   * of its elements.  All elements in the array
   * must implement the {@link Comparable} interface.  Furthermore, all
   * elements in the array must be <i>mutually comparable</i> (that is,
   * <tt>e1.compareTo(e2)</tt> must not throw a <tt>ClassCastException</tt>
   * for any elements <tt>e1</tt> and <tt>e2</tt> in the array).<p>
   *
   * This sort is guaranteed to be <i>stable</i>:  equal elements will
   * not be reordered as a result of the sort.<p>
   *
   * The sorting algorithm is a modified mergesort (in which the merge is
   * omitted if the highest element in the low sublist is less than the
   * lowest element in the high sublist).  This algorithm offers guaranteed
   * n*log(n) performance.
   *
   * @param a the array to be sorted
   * @throws  ClassCastException if the array contains elements that are not
   *		<i>mutually comparable</i> (for example, strings and integers).
   */
  public static void sort(Object[] a) {
    Object[] aux = (Object[]) a.clone();
    mergeSort(aux, a, 0, a.length, 0);
  }


    /**
     * Sorts the specified array of objects according to the order induced by
     * the specified comparator.  All elements in the array must be
     * <i>mutually comparable</i> by the specified comparator (that is,
     * <tt>c.compare(e1, e2)</tt> must not throw a <tt>ClassCastException</tt>
     * for any elements <tt>e1</tt> and <tt>e2</tt> in the array).<p>
     *
     * This sort is guaranteed to be <i>stable</i>:  equal elements will
     * not be reordered as a result of the sort.<p>
     *
     * The sorting algorithm is a modified mergesort (in which the merge is
     * omitted if the highest element in the low sublist is less than the
     * lowest element in the high sublist).  This algorithm offers guaranteed
     * n*log(n) performance.
     *
     * @param a the array to be sorted
     * @param c the comparator to determine the order of the array.  A
     *        <tt>null</tt> value indicates that the elements'
     *        {@linkplain Comparable natural ordering} should be used.
     * @throws  ClassCastException if the array contains elements that are
     *		not <i>mutually comparable</i> using the specified comparator.
     */
    public static <T> void sort(T[] a, Comparator<? super T> c) {
	T[] aux = (T[])a.clone();
        if (c==null)
            mergeSort(aux, a, 0, a.length, 0);
        else
            mergeSort(aux, a, 0, a.length, 0, c);
    }

  /**
   * Swaps x[a] with x[b].
   */
  private static void swap(Object[] x, int a, int b) {
    Object t = x[a];
    x[a] = x[b];
    x[b] = t;
  }

  /**
   * Tuning parameter: list size at or below which insertion sort will be
   * used in preference to mergesort or quicksort.
   */
  private static final int INSERTIONSORT_THRESHOLD = 7;

  /**
   * Src is the source array that starts at index 0
   * Dest is the (possibly larger) array destination with a possible offset
   * low is the index in dest to start sorting
   * high is the end index in dest to end sorting
   * off is the offset to generate corresponding low, high in src
   */
  private static void mergeSort(Object[] src,
          Object[] dest,
          int low,
          int high,
          int off) {
    int length = high - low;

    // Insertion sort on smallest arrays
    if (length < INSERTIONSORT_THRESHOLD) {
      for (int i = low; i < high; i++) {
        for (int j = i; j > low &&
                ((Comparable) dest[j - 1]).compareTo(dest[j]) > 0; j--) {
          swap(dest, j, j - 1);
        }
      }
      return;
    }

    // Recursively sort halves of dest into src
    int destLow = low;
    int destHigh = high;
    low += off;
    high += off;
    int mid = (low + high) >>> 1;
    mergeSort(dest, src, low, mid, -off);
    mergeSort(dest, src, mid, high, -off);

    // If list is already sorted, just copy from src to dest.  This is an
    // optimization that results in faster sorts for nearly ordered lists.
    if (((Comparable) src[mid - 1]).compareTo(src[mid]) <= 0) {
      System.arraycopy(src, low, dest, destLow, length);
      return;
    }

    // Merge sorted halves (now in src) into dest
    for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
      if (q >= high || p < mid && ((Comparable) src[p]).compareTo(src[q]) <= 0) {
        dest[i] = src[p++];
      } else {
        dest[i] = src[q++];
      }
    }
  }

   /**
     * Src is the source array that starts at index 0
     * Dest is the (possibly larger) array destination with a possible offset
     * low is the index in dest to start sorting
     * high is the end index in dest to end sorting
     * off is the offset into src corresponding to low in dest
     */
    private static void mergeSort(Object[] src,
				  Object[] dest,
				  int low, int high, int off,
				  Comparator c) {
	int length = high - low;

	// Insertion sort on smallest arrays
	if (length < INSERTIONSORT_THRESHOLD) {
	    for (int i=low; i<high; i++)
		for (int j=i; j>low && c.compare(dest[j-1], dest[j])>0; j--)
		    swap(dest, j, j-1);
	    return;
	}

        // Recursively sort halves of dest into src
        int destLow  = low;
        int destHigh = high;
        low  += off;
        high += off;
        int mid = (low + high) >>> 1;
        mergeSort(dest, src, low, mid, -off, c);
        mergeSort(dest, src, mid, high, -off, c);

        // If list is already sorted, just copy from src to dest.  This is an
        // optimization that results in faster sorts for nearly ordered lists.
        if (c.compare(src[mid-1], src[mid]) <= 0) {
           System.arraycopy(src, low, dest, destLow, length);
           return;
        }

        // Merge sorted halves (now in src) into dest
        for(int i = destLow, p = low, q = mid; i < destHigh; i++) {
            if (q >= high || p < mid && c.compare(src[p], src[q]) <= 0)
                dest[i] = src[p++];
            else
                dest[i] = src[q++];
        }
    }
}
