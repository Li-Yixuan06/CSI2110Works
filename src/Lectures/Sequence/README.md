# Sequence 标准结构示例

## 1. 文件层次

```text
Position<E>                       interface：单个 entry 的稳定位置
Sequence<E>                       interface：完整 ADT 合同
    ↑
AbstractSequence<E>               abstract class：共享代码
    ├── ArraySequence<E>           extendable array 实现
    └── LinkedSequence<E>          doubly linked list 实现

SequenceDemo                      对两种实现执行同一组操作
```

## 2. 每一层的职责

### `Position<E>`

- 只公开 `getElement()`。
- 不公开 Node 的 `prev`、`next` 或数组内部结构。
- Position 表示 entry 的身份，不等于当前 index。

### `Sequence<E>`

- 声明 index-based API。
- 声明 Position-based API。
- 声明 `atIndex` 和 `indexOf` 两个 bridge methods。
- 不包含具体存储字段和算法。

### `AbstractSequence<E>`

- 保存两个实现共有的 `size`。
- 实现 `size()`、`isEmpty()` 和 index validation。
- 通过 `atIndex` 和 Position API 实现通用的 `get/set/add/remove(index)`。
- 仍然是 abstract，因为它不知道 Position 实际存储在哪里。

### `ArraySequence<E>`

- 数组中保存的是独立 Position 对象的引用。
- Position 保存 element、当前 index 和 owner。
- 插入/删除时移动引用，并同步更新 Position 的 index。
- 删除后清除 owner，使旧 Position 失效。

### `LinkedSequence<E>`

- 使用 header/trailer sentinels 的双向链表。
- 内部 Node 实现 Position。
- 已知 Position 时，只修改相邻结点链接即可插入或删除。
- 删除后清除 owner 和链接，使旧 Position 失效。

## 3. 主要复杂度

| 操作 | ArraySequence | LinkedSequence |
|---|---:|---:|
| `get(i)` / `set(i,e)` | O(1) | O(n) |
| `atIndex(i)` | O(1) | O(n) |
| `indexOf(p)` | O(1) | O(n) |
| 已知 Position 后插入 | O(n) | O(1) |
| 已知 Position 后删除 | O(n) | O(1) |

LinkedSequence 的 O(1) 插入/删除以“已经持有合法 Position”为前提。

## 4. 阅读顺序

1. `Position.java`
2. `Sequence.java`
3. `AbstractSequence.java`
4. `SequenceDemo.java`
5. `ArraySequence.java`
6. `LinkedSequence.java`

先理解合同和使用方式，再阅读底层实现，会比直接从结点或数组移动开始更清楚。

## 5. 与 Lab 的关系

这是放在 `Lectures` 下的自学示例。Lab 4 如果提供了自己的 interface、abstract class、
方法名称或返回类型，应严格使用 Lab 4 的 starter code，而不是直接复制这里的签名。
