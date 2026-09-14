package Labs.Lab1;

/**
 * A simple node class for a singly-linked list.  Each node has a
 * reference to a stored element and a next node.
 * This class is based on the <code>DNode</code> class by Roberto Tamassia.
 *
 * @author Jochen Lang
 */

public class Node {

    private Object element; // the value kept in the Node
    private Node next; // singly linked node with single pointer to the next Node

    // default constructor
    Node() { this(null, null); }

    // normal constructor
    Node(Object e, Node n) {
        element = e;
        next = n;
    }

    // element setter
    public void setElement(Object newElem) { element = newElem; }

    // next setter
    public void setNext(Node newNext) { next = newNext; }

    // element getter
    public Object getElement() { return element; }

    // next getter
    public Node getNext() { return next; }

}
