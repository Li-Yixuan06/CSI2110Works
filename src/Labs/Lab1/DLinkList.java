package Labs.Lab1;

// DLL using DNode
public class DLinkList {

    DNode head;
    DNode tail;

    DLinkList(int sz) {

        head = new DNode();
        tail = new DNode();
        head.setNext(tail);
        head.setPrev(tail);
        tail.setNext(head);
        tail.setPrev(head);

        DNode pointer = head; // temporary pointer for the loop

        if (sz > 0) {
            for (int i = 0; i < sz; i++) {
                DNode newNode = new DNode(i, pointer, tail);
                pointer.setNext(newNode);
                tail.setPrev(newNode);
                pointer = pointer.getNext();
            }
        }
    }

    public void print() {

        DNode pointer = head;
        while (pointer.getNext() != tail) {
            System.out.print((String)pointer.getNext().getElement());
            pointer = pointer.getNext();
        }
        System.out.println();

    }

    public void deleteFirst() {
        if (head.getNext() != tail) {
            head.setNext(head.getNext().getNext());
            head.getNext().setPrev(head);
        }
    }

    public void deleteLast() {
        if (tail.getPrev() != head) {
            tail.setPrev(tail.getPrev().getPrev());
            tail.getPrev().setNext(tail);
        }
    }


}
