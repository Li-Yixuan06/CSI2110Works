package Lectures.Sequence;

/**
 * A stable handle to one entry in a positional data structure.
 *
 * <p>A Position is not an integer index. Inserting another element before this
 * position may change its index, but the Position should still refer to the
 * same entry.</p>
 *
 * @param <E> element type
 */
public interface Position<E> {

    /** Returns the element stored at this position. */
    E getElement();
}
