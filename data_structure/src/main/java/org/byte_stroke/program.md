# 🔢 PriorityQueue Sorting for Map Keys & Values — Complete Cheat Sheet

## 📌 Core Rule (Must Know)
To sort a `Map` using a `PriorityQueue`, **always store `Map.Entry<K, V>`**, not the `Map` itself.

```java
PriorityQueue<Map.Entry<K, V>> pq;
```
---

## 🔹 Base Setup
```java
Map<Integer, Integer> map = new HashMap<>();
map.put(1, 5);
map.put(2, 2);
map.put(3, 8);
```
---

## 1️⃣ Sort by **VALUE — Ascending** (Min Heap)

```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(
        (a, b) -> Integer.compare(a.getValue(), b.getValue())
    );
```

### Cleaner (Java 8+)
```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(Comparator.comparing(Map.Entry::getValue));
```

---

## 2️⃣ Sort by **VALUE — Descending** (Max Heap)

```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(
        (a, b) -> Integer.compare(b.getValue(), a.getValue())
    );
```

### Cleaner
```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(Comparator.comparing(Map.Entry::getValue).reversed());
```

---

## 3️⃣ Sort by **KEY — Ascending**

```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(
        (a, b) -> Integer.compare(a.getKey(), b.getKey())
    );
```

### Cleaner
```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(Comparator.comparing(Map.Entry::getKey));
```

---

## 4️⃣ Sort by **KEY — Descending**

```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(
        (a, b) -> Integer.compare(b.getKey(), a.getKey())
    );
```

### Cleaner
```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(Comparator.comparing(Map.Entry::getKey).reversed());
```
---
## 5️⃣ Sort by **VALUE**, then **KEY** (Tie-Breaker)

```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(
        Comparator.comparing(Map.Entry<Integer, Integer>::getValue)
                  .thenComparing(Map.Entry::getKey)
    );
```
---

## 6️⃣ Sort by **VALUE ↓**, then **KEY ↑** (Very Common)

```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(
        Comparator.comparing(Map.Entry<Integer, Integer>::getValue)
                  .reversed()
                  .thenComparing(Map.Entry::getKey)
    );
```

## 7️⃣ Custom Sorting Logic (Advanced)

```java
PriorityQueue<Map.Entry<Integer, Integer>> pq =
    new PriorityQueue<>(
        (a, b) -> (a.getKey() + a.getValue()) - (b.getKey() + b.getValue())
    );
```

---

## 8️⃣ Adding Entries to PriorityQueue

```java
for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
    pq.offer(entry);
}
```

---

## 9️⃣ Polling in Sorted Order

```java
while (!pq.isEmpty()) {
    Map.Entry<Integer, Integer> e = pq.poll();
    System.out.println(e.getKey() + " -> " + e.getValue());
}
```


# 🔢 PriorityQueue Sorting for Integer — Complete Cheat Sheet

## 1️⃣ Ascending Order (Min-Heap) — Default

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

or explicitly:

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>(Integer::compare);
```
---

## 2️⃣ Descending Order (Max-Heap)

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) -> Integer.compare(b, a));
```

### Cleaner
```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>(Comparator.reverseOrder());
```

---

## 3️⃣ Custom Comparator (Safe Comparison)

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) -> Integer.compare(a, b));
```

⚠️ Avoid:
```java
(a - b) // may overflow ❌
```

---

## 4️⃣ Sort by Absolute Value (Ascending)

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) ->
        Integer.compare(Math.abs(a), Math.abs(b))
    );
```

---

## 5️⃣ Sort by Absolute Value (Descending)

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) ->
        Integer.compare(Math.abs(b), Math.abs(a))
    );
```

---

## 6️⃣ Even Numbers First, Then Odd

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) -> {
        if ((a % 2) != (b % 2)) {
            return (a % 2) - (b % 2); // evens first
        }
        return Integer.compare(a, b);
    });
```

---

## 7️⃣ Sort by Last Digit

```java
PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) ->
        Integer.compare(a % 10, b % 10)
    );
```

---

## 8️⃣ Sort by Frequency (Using External Map)

```java
Map<Integer, Integer> freq = new HashMap<>();

PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) -> {
        int diff = freq.get(a) - freq.get(b);
        if (diff == 0) {
            return Integer.compare(a, b);
        }
        return diff;
    });
```

## 9️⃣ Sort by Distance from a Number `k`

```java
int k = 10;

PriorityQueue<Integer> pq =
    new PriorityQueue<>((a, b) ->
        Integer.compare(Math.abs(a - k), Math.abs(b - k))
    );
```


# 🔄 Java Streams: `boxed()` and `toArray()` — Complete Guide

## ✅ Correct & Common Use Cases
## 1️⃣ `int[] → Integer[]` (Most Common)

```java
Integer[] arr = Arrays.stream(l)
                      .boxed()
                      .toArray(Integer[]::new);
```

## 3️⃣ `List<Integer> → Integer[]` (Best Way)

```java
Integer[] arr = l.toArray(new Integer[0]);
```

## 4️⃣ `List<Integer> → int[]`

```java
int[] arr = l.stream()
             .mapToInt(Integer::intValue)
             .toArray();
```

## 5️⃣ `int[] → List<Integer>`

```java
List<Integer> list = Arrays.stream(l)
                           .boxed()
                           .collect(Collectors.toList());
```
