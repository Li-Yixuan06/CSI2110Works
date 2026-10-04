# Sequence demo

This self-study example models the `Sequence` ADT from Lecture 3.

## Structure

```text
Position<E>
Sequence<E>
├── ArraySequence<E>
└── LinkedSequence<E>
```

- `Sequence` combines index-based and position-based operations.
- `ArraySequence` stores stable `Position` objects in an extendable array.
- `LinkedSequence` uses nodes of a doubly linked list as positions.
- `SequenceDemo` performs the same operations through the common interface.

## Main performance trade-off

| Operation | ArraySequence | LinkedSequence |
|---|---:|---:|
| `get(i)`, `atIndex(i)` | O(1) | O(n) |
| `indexOf(p)` | O(1) | O(n) |
| insert/remove around a known Position | O(n) | O(1) |

The linked implementation only has O(1) insertion/removal when a valid
`Position` is already available. Finding a position from an index still takes
O(n).
