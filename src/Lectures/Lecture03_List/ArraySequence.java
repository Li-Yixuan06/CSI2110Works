package Lectures.Lecture03_List;

import java.util.Arrays;

/**
 * An array-based Sequence.
 *
 * Index access is O(1). Insertion and removal may shift positions and are O(n).
 * Each Position is a separate object, so a saved Position keeps referring to
 * the same entry even when that entry's index changes.
 */
public class ArraySequence<E> implements Sequence<E> {
    private static final int DEFAULT_CAPACITY = 4;

    private ArrayPosition<E>[] data;
    private int size;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ArraySequence() {
        data = (ArrayPosition<E>[]) new ArrayPosition[DEFAULT_CAPACITY];
    }

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
                throw new IllegalStateException("This position is no longer valid");
            }
            return element;
        }
    }

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
        return positionAt(index).element;
    }

    @Override
    public E set(int index, E element) {
        return replace(positionAt(index), element);
    }

    @Override
    public Position<E> add(int index, E element) {
        checkAddIndex(index);
        ensureCapacity(size + 1);

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            data[i].index = i;
        }

        ArrayPosition<E> added = new ArrayPosition<>(element, index, this);
        data[index] = added;
        size++;
        return added;
    }

    @Override
    public E remove(int index) {
        return remove(positionAt(index));
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
        return add(0, element);
    }

    @Override
    public Position<E> addLast(E element) {
        return add(size, element);
    }

    @Override
    public Position<E> addBefore(Position<E> position, E element) {
        return add(validate(position).index, element);
    }

    @Override
    public Position<E> addAfter(Position<E> position, E element) {
        return add(validate(position).index + 1, element);
    }

    @Override
    public E set(Position<E> position, E element) {
        return replace(validate(position), element);
    }

    @Override
    public E remove(Position<E> position) {
        ArrayPosition<E> removed = validate(position);
        int removedIndex = removed.index;
        E oldElement = removed.element;

        for (int i = removedIndex; i < size - 1; i++) {
            data[i] = data[i + 1];
            data[i].index = i;
        }

        data[size - 1] = null;
        size--;
        removed.owner = null;
        removed.index = -1;
        removed.element = null;
        return oldElement;
    }

    @Override
    public Position<E> atIndex(int index) {
        return positionAt(index);
    }

    @Override
    public int indexOf(Position<E> position) {
        return validate(position).index;
    }

    private E replace(ArrayPosition<E> position, E element) {
        E oldElement = position.element;
        position.element = element;
        return oldElement;
    }

    private ArrayPosition<E> positionAt(int index) {
        checkElementIndex(index);
        return data[index];
    }

    @SuppressWarnings("unchecked")
    private ArrayPosition<E> validate(Position<E> position) {
        if (!(position instanceof ArrayPosition<?>)) {
            throw new IllegalArgumentException("Position has the wrong implementation type");
        }

        ArrayPosition<E> checked = (ArrayPosition<E>) position;
        if (checked.owner != this
                || checked.index < 0
                || checked.index >= size
                || data[checked.index] != checked) {
            throw new IllegalArgumentException("Position is invalid or belongs to another sequence");
        }
        return checked;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }
}
