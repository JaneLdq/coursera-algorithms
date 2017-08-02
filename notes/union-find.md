# Union-Find

[TOC]

---
## 什么是并查集？
应用场景：需要将n个不同元素划分成一组不相交的集合。从单个元素开始按照一定规律进行**合并**，在此过程中则需要进行多次**查询**操作以确定某一元素属于哪个集合。
适合于描述以上问题的抽象数据类型就被称为**并查集**。

* 图像分析中对一张照片中像素的划分
* 网络中的计算机群组
* 社交中的朋友圈
* ...

并查集的基本接口
```java
public interface UnionFind {
    public int find(int p); // find which component p belongs to
    public int union(int p, int q); // connect p and q
    public boolean connected(int p, int q); // return true if p and q are in the same component
}

```

## 并查集的实现
***通常情况下，使用整数来表示元素，如有n个元素的集合，可以使用0-(n-1)来表示每个元素；采用树形结构来表示元素及其所属子集的关系，每个子集用一棵树表示，每个树上的结点表示一个元素，所有子集的全集合构成森林。***

举个栗子，假设有一个集合S={0,1,2,3,4,5,6,7,8,9}，我们对它依次进行初始化和三次合并操作Union(1,4), Union(7,4), Union(8,9)，则操作过程可由下图表示：
![并查集的树形表示][1]

那么，在计算机里，我们用数组来存储这棵树，不同的实现算法，数组元素所表示的含义有所不同。接下来依次介绍四种并查集的实现。

### Quick Find
顾名思义，QuickFind就是查询操作很快的实现方式。
如下图所示，初始化时每个数组元素的值与下标相同，表示各个元素为独立的集合，即nodes[i]=i，表示此时元素i的根节点为i。在做元素p,q的合并操作时，将所有nodes[i]=nodes[p]的元素都设为nodes[i]=nodes[q]，这样每个数组元素里保存的就是该下标结点所属子树的**根节点**，即nodes[i]=find(i)。
![Quick Find 示意图][2]

* Initialize - 数组初始化， 时间复杂度O(N)
* Find - 直接取数组下标对应的值即可，时间复杂度O(1)
* Union - 遍历数组，更新所有与被合并元素处于同一根节点的元素，时间复杂度为O(N)

缺点：

* **QuickFind的Union操作太耗时了，对于N个元素的N次Union操作要进行N^2次数组访问**
* 树的结构是平的，但是代价太高了不划算呀

### Quick Union
QuickUnion就是合并操作很快（**并不**）的实现方式了。与QuickFind不同，QuickUnion的数组nodes[i]保存的是i的父节点，每次做元素p,q的合并操作时，要先各自找到p,q的根节点，然后将p的根节点设为q的根节点。示例如下图：
![Quick Union 示意图][3]

* Initialize - 数组初始化， 时间复杂度O(N)
* Find - 要从nodes[p]开始遍历直到nodes[p]=p才算找到根节点，时间复杂度O(N)
* Union - 由于要先find各自的根节点，所以时间复杂度也为O(N)

缺点：

* 很可能形成高度很高的树
* 查询和合并的时间复杂度都是O(N)，并不快！

### Weighted Quick-Union
为了防止形成的树形结构过于不平衡，我们对QuickUnion进行一些些改进，加入对树的大小（也可以选择高度，在该算法中我们还是用size作为weight）的考虑，在每次做合并操作时，比较两棵树的大小，将size较小的树作为较大的树的子树，而不是像QuickUnion算法中默认总是将参数中前者作为后者的子树。示例如下：
![Weighted Quick Union示例图][4]
如上图所示，在进行Union(4,6)时若按照QuickUnion的做法，会将元素4的根节点设为6，形成一棵很不平衡的树，而加入Weight之后，形成的树结构要扁平很多，从而提高查询效率。

* Initialize - O(N)
* Find - O(lgN)，二叉树是最坏的情况，最高不超过lgN层
* Union - O(lgN)　

### Quick-Union with Path Compression
另一种改进方式就是在执行find操作时，计算出元素p的根节点后，将该过程中遍历到的中间结点直接移到根节点，缩短查询路径。示例如下：
![Quick-Union with Path Compression][5]
如上图所示，在查找p=9的根节点时，我们经过了6,3,1，那么在这次find操作中就顺便把6,3都直接连接到根节点0上。
实现方式：在find()中新增一个循环

* 另一种简单的实现，在find过程中，将遍历到的结点的值设为它的父节点的父节点，这样的话路径长度折半，代码也很简单
```java
public int find(int i) {
    while(i != nodes[i]) {
        // 相对于quickunion的find方法，只需要新增下面这一行代码
        nodes[i] = nodes[nodes[i]]; 
        i = nodes[i];
    }
    return i;
}
```

另外，还可以尝试在Weighted Quick Union的基础上进行Path Compression。

## 参考资料
上文提到的算法可以在[Algorithms - Case Study: Union-Find][6]中找到官方实现，以作参考。


  [1]: http://o8bxo46sq.bkt.clouddn.com/union-tree.jpg
  [2]: http://o8bxo46sq.bkt.clouddn.com/quick-find.jpg
  [3]: http://o8bxo46sq.bkt.clouddn.com/quick-union.jpg
  [4]: http://o8bxo46sq.bkt.clouddn.com/weighted-quickunion.jpg
  [5]: http://o8bxo46sq.bkt.clouddn.com/path-compression.jpg
  [6]: http://algs4.cs.princeton.edu/15uf/