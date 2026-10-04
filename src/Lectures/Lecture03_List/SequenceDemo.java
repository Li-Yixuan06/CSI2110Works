package Lectures.Lecture03_List;

/** Demonstrates that both implementations obey the same Sequence interface. */
public class SequenceDemo {
    public static void main(String[] args) {
        demonstrate("ArraySequence", new ArraySequence<>());
        demonstrate("LinkedSequence", new LinkedSequence<>());
    }

    private static void demonstrate(String name, Sequence<String> sequence) {
        System.out.println("\n=== " + name + " ===");

        sequence.addLast("A");
        sequence.addLast("B");
        Position<String> positionC = sequence.addLast("C");
        sequence.addLast("D");
        print(sequence);                         // [A, B, C, D]

        System.out.println("get(2) = " + sequence.get(2));
        System.out.println("indexOf(positionC) = " + sequence.indexOf(positionC));

        sequence.addBefore(positionC, "X");
        print(sequence);                         // [A, B, X, C, D]

        // The index changed, but the saved Position still refers to C.
        System.out.println("positionC still stores " + positionC.getElement());
        System.out.println("its new index is " + sequence.indexOf(positionC));

        Position<String> indexOne = sequence.atIndex(1);
        sequence.addAfter(indexOne, "Y");
        print(sequence);                         // [A, B, Y, X, C, D]

        System.out.println("remove(positionC) = " + sequence.remove(positionC));
        System.out.println("remove(0) = " + sequence.remove(0));
        print(sequence);                         // [B, Y, X, D]
    }

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
