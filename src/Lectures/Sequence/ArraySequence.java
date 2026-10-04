package Lectures.Sequence;

import java.util.Arrays;

/**
 * Sequence implemented with an extendable array of Position objects.
 *
 * <p>Typical complexities:</p>
 * <ul>
 *   <li>get/set/atIndex/indexOf: O(1)</li>
 *   <li>insertion/removal: O(n), because references are shifted and stored
 *       indices must be updated</li>
 * </ul>
 */
public class ArraySequence<E> extends AbstractSequence<E> {

    private static final int DEFAULT_CAPACITY = 8;

    /**
     * The entry object is independent from its array slot. Moving this object
     * inside the array changes its index, not its identity.
     */
    private static final class ArrayPosition<E> implements Position<E> {
        private E element;
        private int index;
        private ArraySequence<E> owner;

        private ArrayPosition(E element, int index, ArraySequence<E> owner) {
            this.element = element;
            this.index = index;
            this.owner = owner;
        }

        @Override
        public E getElement() {
            if (owner == null) {
                throw new IllegalStateException("This Position has been removed");
            }
            return element;
        }
    }

    private ArrayPosition<E>[] data;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ArraySequence() {
        data = (ArrayPosition<E>[]) new ArrayPosition[DEFAULT_CAPACITY];
    }

    @Override
    public Position<E> first() {
        return isEmpty() ? null : data[0];
    }

    @Override
    public Position<E> last() {
        return isEmpty() ? null : data[size - 1];
    }

    @Override
    public Position<E> before(Position<E> position) {
        ArrayPosition<E> checked = validate(position);
        return checked.index == 0 ? null : data[checked.index - 1];
    }

    @Override
    public Position<E> after(Position<E> position) {
        ArrayPosition<E> checked = validate(position);
        return checked.index == size - 1 ? null : data[checked.index + 1];
    }

    @Override
    public Position<E> addFirst(E element) {
        return insertAt(0, element);
    }

    @Override
    public Position<E> addLast(E element) {
        return insertAt(size, element);
    }

    @Override
    public Position<E> addBefore(Position<E> position, E element) {
        return insertAt(validate(position).index, element);
    }

    @Override
    public Position<E> addAfter(Position<E> position, E element) {
        return insertAt(validate(position).index + 1, element);
    }

    @Override
    public E set(Position<E> position, E element) {
        ArrayPosition<E> checked = validate(position);
        E previous = checked.element;
        checked.element = element;
        return previous;
    }

    @Override
    public E remove(Position<E> position) {
        ArrayPosition<E> removed = validate(position);
        int removedIndex = removed.index;
        E previous = removed.element;

        // Shift left to fill the hole and update every moved Position.
        for (int i = removedIndex; i < size - 1; i++) {
            data[i] = data[i + 1];
            data[i].index = i;
        }

        data[size - 1] = null; // release the unused reference
        size--;

        // A removed Position must no longer be accepted by this Sequence.
        removed.owner = null;
        removed.index = -1;
        removed.element = null;
        return previous;
    }

    @Override
    public Position<E> atIndex(int index) {
        checkElementIndex(index);
        return data[index];
    }

    @Override
    public int indexOf(Position<E> position) {
        return validate(position).index;
    }

    private Position<E> insertAt(int index, E element) {
        checkAddIndex(index);
        ensureCapacity(size + 1);

        // Shift right to open one slot and update every moved Position.
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            data[i].index = i;
        }

        ArrayPosition<E> added = new ArrayPosition<>(element, index, this);
        data[index] = added;
        size++;
        return added;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    /**
     * Confirms that a Position has the right runtime type, is still present,
     * and belongs to this exact Sequence object.
     */
    @SuppressWarnings("unchecked")
    private ArrayPosition<E> validate(Position<E> position) {
        if (!(position instanceof ArrayPosition<?>)) {
            throw new IllegalArgumentException("Wrong Position implementation");
        }

        ArrayPosition<E> checked = (ArrayPosition<E>) position;
        if (checked.owner != this
                || checked.index < 0
                || checked.index >= size
                || data[checked.index] != checked) {
            throw new IllegalArgumentException(
                    "Position is invalid or belongs to another Sequence"
            );
        }
        return checked;
    }
}
