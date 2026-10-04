package Lectures.Lecture03_List;

/**
 * A doubly-linked-list-based Sequence.
 *
 * Operations around a known Position are O(1). Index access and conversion
 * between an index and a Position require traversal and are O(n).
 */
public class LinkedSequence<E> implements Sequence<E> {
    private final Node<E> header;
    private final Node<E> trailer;
    private int size;

    public LinkedSequence() {
        header = new Node<>(null, null, null, this);
        trailer = new Node<>(null, header, null, this);
        header.next = trailer;
    }

    private static final class Node<E> implements Position<E> {
        private E element;
        private Node<E> previous;
        private Node<E> next;
        private LinkedSequence<E> owner;

        private Node(E element, Node<E> previous, Node<E> next, LinkedSequence<E> owner) {
            this.element = element;
            this.previous = previous;
            this.next = next;
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
        return nodeAt(index).element;
    }

    @Override
    public E set(int index, E element) {
        return replace(nodeAt(index), element);
    }

    @Override
    public Position<E> add(int index, E element) {
        checkAddIndex(index);
        Node<E> successor = index == size ? trailer : nodeAt(index);
        return addBetween(element, successor.previous, successor);
    }

    @Override
    public E remove(int index) {
        return remove(nodeAt(index));
    }

    @Override
    public Position<E> first() {
        return isEmpty() ? null : header.next;
    }

    @Override
    public Position<E> last() {
        return isEmpty() ? null : trailer.previous;
    }

    @Override
    public Position<E> before(Position<E> position) {
        Node<E> checked = validate(position);
        return checked.previous == header ? null : checked.previous;
    }

    @Override
    public Position<E> after(Position<E> position) {
        Node<E> checked = validate(position);
        return checked.next == trailer ? null : checked.next;
    }

    @Override
    public Position<E> addFirst(E element) {
        return addBetween(element, header, header.next);
    }

    @Override
    public Position<E> addLast(E element) {
        return addBetween(element, trailer.previous, trailer);
    }

    @Override
    public Position<E> addBefore(Position<E> position, E element) {
        Node<E> successor = validate(position);
        return addBetween(element, successor.previous, successor);
    }

    @Override
    public Position<E> addAfter(Position<E> position, E element) {
        Node<E> predecessor = validate(position);
        return addBetween(element, predecessor, predecessor.next);
    }

    @Override
    public E set(Position<E> position, E element) {
        return replace(validate(position), element);
    }

    @Override
    public E remove(Position<E> position) {
        Node<E> removed = validate(position);
        E oldElement = removed.element;

        removed.previous.next = removed.next;
        removed.next.previous = removed.previous;
        size--;

        removed.owner = null;
        removed.previous = null;
        removed.next = null;
        removed.element = null;
        return oldElement;
    }

    @Override
    public Position<E> atIndex(int index) {
        return nodeAt(index);
    }

    @Override
    public int indexOf(Position<E> position) {
        Node<E> target = validate(position);
        int index = 0;
        for (Node<E> current = header.next; current != trailer; current = current.next) {
            if (current == target) {
                return index;
            }
            index++;
        }
        throw new IllegalArgumentException("Position is not in this sequence");
    }

    private Position<E> addBetween(E element, Node<E> predecessor, Node<E> successor) {
        Node<E> added = new Node<>(element, predecessor, successor, this);
        predecessor.next = added;
        successor.previous = added;
        size++;
        return added;
    }

    private E replace(Node<E> position, E element) {
        E oldElement = position.element;
        position.element = element;
        return oldElement;
    }

    private Node<E> nodeAt(int index) {
        checkElementIndex(index);

        if (index < size / 2) {
            Node<E> current = header.next;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }

        Node<E> current = trailer.previous;
        for (int i = size - 1; i > index; i--) {
            current = current.previous;
        }
        return current;
    }

    @SuppressWarnings("unchecked")
    private Node<E> validate(Position<E> position) {
        if (!(position instanceof Node<?>)) {
            throw new IllegalArgumentException("Position has the wrong implementation type");
        }

        Node<E> checked = (Node<E>) position;
        if (checked.owner != this || checked == header || checked == trailer) {
            throw new IllegalArgumentException("Position is invalid or belongs to another sequence");
        }
        return checked;
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
