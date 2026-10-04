package Lectures.Sequence;

/** Runs the same scenario through both concrete Sequence implementations. */
public class SequenceDemo {

    public static void main(String[] args) {
        runExample("ArraySequence", new ArraySequence<>());
        runExample("LinkedSequence", new LinkedSequence<>());
    }

    /**
     * The parameter type is Sequence, not a concrete class. This demonstrates
     * programming to an interface and implementation substitutability.
     */
    private static void runExample(String name, Sequence<String> sequence) {
        System.out.println("\n=== " + name + " ===");

        sequence.addLast("A");
        sequence.addLast("B");
        Position<String> savedC = sequence.addLast("C");
        sequence.addLast("D");
        print(sequence); // [A, B, C, D]

        System.out.println("get(2): " + sequence.get(2));
        System.out.println("indexOf(savedC): " + sequence.indexOf(savedC));

        sequence.addBefore(savedC, "X");
        print(sequence); // [A, B, X, C, D]

        // savedC still identifies C, although C's index changed from 2 to 3.
        System.out.println("savedC element: " + savedC.getElement());
        System.out.println("savedC new index: " + sequence.indexOf(savedC));

        Position<String> positionOne = sequence.atIndex(1);
        sequence.addAfter(positionOne, "Y");
        print(sequence); // [A, B, Y, X, C, D]

        System.out.println("remove(savedC): " + sequence.remove(savedC));
        System.out.println("remove(0): " + sequence.remove(0));
        print(sequence); // [B, Y, X, D]
    }

    /** Traverses by Position so that it works naturally for either storage. */
    private static <E> void print(Sequence<E> sequence) {
        StringBuilder output = new StringBuilder("[");
        Position<E> current = sequence.first();

        while (current != null) {
            output.append(current.getElement());
            current = sequence.after(current);
            if (current != null) {
                output.append(", ");
            }
        }

        output.append(']');
        System.out.println(output);
    }
}
