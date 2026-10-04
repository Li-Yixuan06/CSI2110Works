package Lectures.Sequence;

/**
 * A linear collection supporting both index-based and position-based access.
 *
 * <p>This is the union of the Array List and Positional List APIs, plus bridge
 * methods that convert between an index and a Position.</p>
 *
 * @param <E> element type
 */
public interface Sequence<E> {

    // ---------- Basic queries ----------

    int size();

    boolean isEmpty();

    // ---------- Array-list-style operations ----------

    E get(int index);

    E set(int index, E element);

    Position<E> add(int index, E element);

    E remove(int index);

    // ---------- Positional-list-style accessors ----------

    Position<E> first();

    Position<E> last();

    Position<E> before(Position<E> position);

    Position<E> after(Position<E> position);

    // ---------- Positional-list-style updates ----------

    Position<E> addFirst(E element);

    Position<E> addLast(E element);

    Position<E> addBefore(Position<E> position, E element);

    Position<E> addAfter(Position<E> position, E element);

    E set(Position<E> position, E element);

    E remove(Position<E> position);

    // ---------- Bridge methods ----------

    /** Converts an index to a Position. */
    Position<E> atIndex(int index);

    /** Returns the current index of a Position. */
    int indexOf(Position<E> position);
}
