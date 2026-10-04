package Lectures.Sequence;

/**
 * Shared skeletal implementation of the Sequence interface.
 *
 * <p>The concrete classes implement positional operations and bridge methods.
 * This class derives the four index-based operations from them, avoiding code
 * duplication.</p>
 */
public abstract class AbstractSequence<E> implements Sequence<E> {

    /** Number of real entries. Sentinels, if any, are not included. */
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int index) {
        return atIndex(index).getElement();
    }

    @Override
    public E set(int index, E element) {
        return set(atIndex(index), element);
    }

    @Override
    public Position<E> add(int index, E element) {
        checkAddIndex(index);

        // index == size means insertion immediately after the last entry.
        if (index == size) {
            return addLast(element);
        }
        return addBefore(atIndex(index), element);
    }

    @Override
    public E remove(int index) {
        return remove(atIndex(index));
    }

    /** Valid range for accessing an existing entry: 0 <= index < size. */
    protected void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "index=" + index + ", size=" + size
            );
        }
    }

    /** Valid range for insertion: 0 <= index <= size. */
    protected void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "index=" + index + ", size=" + size
            );
        }
    }
}
