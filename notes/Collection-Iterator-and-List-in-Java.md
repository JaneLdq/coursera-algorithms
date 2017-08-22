# Collection, Iterator and List in Java

---

## Collection
java.util.Collection接口抽象出了集合的概念，它存储了一组类型相同的对象。很多数据结构都由Collection接口延展，例如java.util.List，java.util.Set等等都继承自该接口。
下面这段代码截取了部分Collection的关键接口定义，可以看到都是很通用的增删改查一类的方法。
```java
public interface Collection<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    boolean add(E e);
    boolean remove(Object o);
    Iterator<E> iterator();
    // ...
}
```

## Iterable and Iterator
### 在for-each中使用Iterator
在上面的代码中，Collection扩展接口Iterable接口，那么Iterable接口是做什么呢？
官方回答：实现了Iterable接口的类使得这个类型的集合可以使用for-each循环进行遍历操作，也就是可以像下面这样写for循环：
```java
public <T> void print(Collection<T> col) {
    for(T item: col) {
        System.out.println(item);
    }
}
```
↑ 还有这种操作？怎么实现的？
我们继续看源码
```java
/**
 * Implementing this interface allows an object to be the target of
 * the "for-each loop" statement.
 */
public interface Iterable<T> {
    Iterator<T> iterator();
}

/**
 * An iterator over a collection. 
 */
public interface Iterator<E> {
    // 返回集合中（尚未被遍历到的）下一个对象
    E next();
    // 判断是否存在下一个
    boolean hasNext();
    // 移除由next最新返回的项
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
```
Iterable接口定义了一个iterator方法，每次调用iterator()都会得到一个实现了Iterator接口的对象。
Iterator接口有三个主要方法next，hasNext和remove。每个返回Iterator对象将当前遍历的位置在自己内部存储起来，不同的对象间不会相互影响。

**重点来了！神奇的操作是怎么实现的呢？**
当编译器看到for-each循环的时候，会先check被遍历的对象是不是Iterable的，如果不是，会提示错误：*Can only iterate over an array or an instance of java.lang.Iterable*。否则，以之前的print方法为例，编译器会重写那段代码，如下所示：
```java
public static <T> void print(Collection<T> col) {
    Iterator<T> itr = col.iterator();
    while(itr.hasNext()) {
        T item = itr.next();
        System.out.println(item);
    }
}
```
虽然有这种神奇的操作，但其实Iterator也就用来做做简单的遍历，毕竟人家接口有限嘛。

### 单独使用Iterator
再来说说Iterator的第三个接口remove方法，调用remove将删除next最新返回的对象，此后，**我们不能再调用remove，直到next被再一次调用以后**。这里有一个值得注意的问题：当我们不是在for-each循环中使用Iterator而是直接使用它时，如果对正在被遍历的集合进行了结构上的改变（执行add, remove， clear等操作），那么这个Iterator就不再合法，如果之后再使用它会抛出ConcurrentModificationException异常。

**所以，最好是在需要立即使用一个Iterator的时才获取它，即取即用，新鲜的总是更好的~**

不过如果是通过Iterator调用自身的remove方法造成的结构变化，那么它还是合法的。

这个问题在Iterator的实现类中通常通过引入一个modcount计数来进行判断。下面这段代码截取自java.util.ArrayList类的实现：
```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable { 
    
    public Iterator<E> iterator() {
            return new Itr();
        }
    }
    
    private class Itr implements Iterator<E> {
        int expectedModCount = modCount;
        
        public E next() {
            checkForComodification();
            // ...
        }
        public void remove() {
            checkForComodification();
            // ...
        }
        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
```
如上所示， 这个modCount定义在ArrayList的父类AbstractList中，用于记录该list对象结构被改变的次数，当ArrayList执行如remove, sort等操作时都会执行modCount++。在创建Iterator对象时记录下当时的modCount的值，以后每次调用该Iterator的方法时都进行check，如果发生过改变就抛出异常。

## List
List应该是最常用的数据结构了，它使得用户可以通过访问索引idx获取对象。如前所述，在Java中java.util.List接口扩展自Collection，并在此基础上添加了List这个数据结构所具备的特有的操作比如get(int idx), set(int idx)等等。

List的最经典的两种实现方式就是数组和链表，在Java中分别由ArrayList和LinkedList两个类实现。

### ArrayList
get和set操作O(1), insert和remove则是O(N)

### LinkedList
get和set操作是O(N), i   nsert和remove在变动项位置已知的情况下是O(1)，例如表的两端。
值得注意的是，使用普通for循环进行get操作，LinkedList是O(N^2)的时间复杂度，但是如果用for-each和Iterator的话，则ArrayList和LinkedList都是O(N)，因为Iterator能够快速获取下一项。

同样，使用Iterator的remove方法能有效提高效率，但要考虑到前文所说的链表结构变化导致的异常情况。
```java
// 删除具有偶数值的项
public void wrongRemoveEvens(List<Integer> list) {
    for(Integer: x) {
        if (x % 2 == 0) {
            list.remove（x）; // 这样写是要go-die的！
        }
    }
}

public void rightRemoveEvens(List<Integer> list) {
    Iterator<Integer> itr = list.iterator();
    while(itr.hasNext()) {
        if (itr.next() % 2 == 0) {
            itr.remove(); // 这样是可以的！
        }
    }
}

```
