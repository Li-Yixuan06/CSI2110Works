package Lectures.Lecture03_List;

/**
 * A sequence combines index-based and position-based list operations.
 *
 * @param <E> the element type
 */
public interface Sequence<E> {
    int size();

    boolean isEmpty();

    // Array-list-style operations (index based)
    E get(int index);

    E set(int index, E element);

    Position<E> add(int index, E element);

    E remove(int index);

    // Positional-list-style operations (Position based)
    Position<E> first();

    Position<E> last();

    Position<E> before(Position<E> position);

    Position<E> after(Position<E> position);

    Position<E> addFirst(E element);

    Position<E> addLast(E element);

    Position<E> addBefore(Position<E> position, E element);

    Position<E> addAfter(Position<E> position, E element);

    E set(Position<E> position, E element);

    E remove(Position<E> position);

    // Bridge operations between the two access styles
    Position<E> atIndex(int index);

    int indexOf(Position<E> position);
}
