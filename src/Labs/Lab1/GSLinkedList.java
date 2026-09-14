package Labs.Lab1;

public class GSLinkedList<T> {

    GNode<T> llist; // single pointer


    GSLinkedList( int sz ) { // sz means size
        if ( sz <= 0 ) {
            llist = null;
        } else {
            // start with list of size 1
            llist = new GNode<T>( null, null ); // default value 0

            GNode<T> current = llist; // temp node for loop
            // add further nodes
            for ( int i=1; i<sz; ++i ) {
                // create node and attach it to the list
                GNode<T> node2Add = new GNode<T>(null, null ); // default value i == index
                current.setNext(node2Add);   // add node
                current=node2Add; // move pointer
            }
        }
    }




    /**
     * Print all the elements of the list assuming that they are Strings
     */
    public void print() {
        /* Print the list */
        GNode<T> current = llist; // point to the first node
        while (current != null) {
            System.out.print(current.getElement().toString() + " ");
            current = current.getNext(); // move to the next
        }
        System.out.println();
    }

    public void deleteFirst() {
        if ( llist != null ) {
            llist = llist.getNext();
        }
    }

    public void deleteLast() {

        if ( llist == null ) return; // no node
        GNode<T> prev = llist;
        GNode<T> current = prev.getNext();
        if ( current == null ) { // only 1 node
            llist = null;
            return;
        }

        // keeping a pointer pointing to the before one of the last one
        while ( current.getNext() != null ) { // more than 1 node
            prev = current;
            current = current.getNext();
        }
        prev.setNext( null );
        return;
    }




}
