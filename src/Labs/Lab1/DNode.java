package Labs.Lab1;

// Doubly Node for DLL
public class DNode {

    DNode prev;
    DNode next;
    Object element;

    // default constructor
    DNode() { this(null, null, null); }

    // normal constructor
    DNode(Object element, DNode prev, DNode next) {
        this.element = element;
        this.prev = prev;
        this.next = next;
    }

    // element setter
    public void setElement(Object element) {this.element = element;}
    // element getter
    public Object getElement() {return element;}

    // prev setter
    public void setPrev(DNode prev) {this.prev = prev;}
    // prev getter
    public DNode getPrev() {return prev;}

    // next setter
    public void setNext(DNode next) {this.next = next;}
    // next getter
    public DNode getNext() {return next;}


}
