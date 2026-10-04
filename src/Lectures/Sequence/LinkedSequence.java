package Lectures.Sequence;

/**
 * Sequence implemented with a doubly linked list and two sentinels.
 *
 * <p>Typical complexities:</p>
 * <ul>
 *   <li>operations around an already known Position: O(1)</li>
 *   <li>get/set/atIndex/indexOf: O(n), because traversal is required</li>
 * </ul>
 */
public class LinkedSequence<E> extends AbstractSequence<E> {

    /** Nodes are private implementation objects that also serve as Positions. */
    private static final class Node<E> implements Position<E> {
        private E element;
        private Node<E> previous;
        private Node<E> next;
        private LinkedSequence<E> owner;

        private Node(E element, Node<E> previous, Node<E> next,
                     LinkedSequence<E> owner) {
            this.element = element;
            this.previous = previous;
            this.next = next;
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

    /** Sentinels never represent real elements and simplify edge operations. */
    private final Node<E> header;
    private final Node<E> trailer;

    public LinkedSequence() {
        header = new Node<>(null, null, null, this);
        trailer = new Node<>(null, header, null, this);
        header.next = trailer;
    }

    @Override
    // 如果不是空的，返回第一个Node
    public Position<E> first() {
        return isEmpty() ? null : header.next;
    }

    @Override
    // 如果不是空的，返回最后一个Node
    public Position<E> last() {
        return isEmpty() ? null : trailer.previous;
    }

    @Override
    // 返回一个有效Node前面的Node，如果是第一个有效Node返回null
    public Position<E> before(Position<E> position) {
        Node<E> checked = validate(position);
        return checked.previous == header ? null : checked.previous;
    }

    @Override
    // 返回一个有效Node后面的Node，如果是最后一个有效Node返回null
    public Position<E> after(Position<E> position) {
        Node<E> checked = validate(position);
        return checked.next == trailer ? null : checked.next;
    }

    @Override
    // 在LinkedSequence头部新增一个Node存放element
    public Position<E> addFirst(E element) {
        return addBetween(element, header, header.next);
    }

    @Override
    // // 在LinkedSequence尾部新增一个Node存放element
    public Position<E> addLast(E element) {
        return addBetween(element, trailer.previous, trailer);
    }

    @Override
    // 在position前新增
    public Position<E> addBefore(Position<E> position, E element) {
        Node<E> successor = validate(position);
        return addBetween(element, successor.previous, successor);
    }

    @Override
    // 在position后新增
    public Position<E> addAfter(Position<E> position, E element) {
        Node<E> predecessor = validate(position);
        return addBetween(element, predecessor, predecessor.next);
    }

    @Override
    // 更换某个有效Node的element并返回原有值
    public E set(Position<E> position, E element) {
        Node<E> checked = validate(position);
        E previous = checked.element;
        checked.element = element;
        return previous;
    }

    @Override
    // 移除某个有效Node并返回其值
    public E remove(Position<E> position) {
        Node<E> removed = validate(position);
        E previous = removed.element;

        removed.previous.next = removed.next;
        removed.next.previous = removed.previous;
        size--; // size在abstract class中

        // Break links and ownership so that this Position becomes invalid.
        removed.owner = null;
        removed.previous = null;
        removed.next = null;
        removed.element = null;
        return previous;
    }

    @Override
    // 返回在某index的Node
    public Position<E> atIndex(int index) {
        return nodeAt(index);
    }

    @Override
    public int indexOf(Position<E> position) {
        Node<E> target = validate(position);
        int index = 0;

        for (Node<E> current = header.next;
             current != trailer;
             current = current.next) {
            if (current == target) {
                return index;
            }
            index++;
        }

        // validate should make this line unreachable.
        throw new IllegalArgumentException("Position was not found");
    }

    // 在本LinkedSequence中新增一个Node，其位置位于predecessor和successor之间（但是没有检查predecessor和successor是否顺序相邻）
    private Position<E> addBetween(E element, Node<E> predecessor,
                                   Node<E> successor) {
        Node<E> added = new Node<>(element, predecessor, successor, this);
        predecessor.next = added;
        successor.previous = added;
        size++;
        return added;
    }

    /**
     * Finds an entry by index. Traversal starts at the nearer end, but its
     * worst-case time remains O(n).
     */
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
    // 判断一个Position是否是本LinkedSequence中的非dummy的Node，如果是则返回该Node
    private Node<E> validate(Position<E> position) {
        if (!(position instanceof Node<?>)) {
            throw new IllegalArgumentException("Wrong Position implementation");
        }

        Node<E> checked = (Node<E>) position;
        if (checked.owner != this || checked == header || checked == trailer) {
            throw new IllegalArgumentException(
                    "Position is invalid or belongs to another Sequence"
            );
        }
        return checked;
    }
}
