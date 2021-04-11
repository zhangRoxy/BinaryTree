package company;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LLRBTree<K extends Comparable<K>, V> {
    private RBTreeNode<K, V> root;// 根结点
    private int size;//非空节点个数

    private static final boolean RED = true;//红色
    private static final boolean BLACK = false;//黑色

    public LLRBTree() {
        this.root = null;
        this.size = 0;
    }

    //判断树是否为空,为空返回true，不为空返回false
    private boolean isEmpty() {
        return root == null;
    }

    /**
     * @return boolean
     * @throws
     * @Param [node]
     * @description 如果为空树的情况下，根节点设置为黑色
     **/
    private boolean isRed(RBTreeNode<K, V> node) {
        if (node == null) {
            return BLACK;
        }
        return node.color == RED;
    }

    //查找操作
    /**
    * @Param [key]
    * @description  根据key查找节点值
    * @return V
    * @throws
    **/
    public V getValue(K key) {
        return getValue(root, key);
    }

    /**
     * @return V
     * @throws
     * @Param [key]
     * @description 根据key递归查找元素
     **/
    private V getValue(RBTreeNode<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        //比较key值
        int diff = key.compareTo(node.key);
        if (diff > 0) {
            //递归左子树
            return getValue(node.right, key);
        } else if (diff < 0) {
            //递归右子树
            return getValue(node.left, key);
        } else {
            //如果key值相等，说明树种存在该值
            return node.value;
        }
    }

    //获取树的最小键
    public K getMin() {
        return getMin(root).key;
    }

    /**
     * @return company.RBTree.RBTreeNode<K, V>
     * @throws
     * @Param [node]
     * @description 寻找以node为根的树中最小节点
     **/
    private RBTreeNode<K, V> getMin(RBTreeNode<K, V> node) {
        if(node.left != null) {
            return getMin(node.left);
        } else {
            return node;
        }
    }


    //获取树的最大键
    public K getMax() {
        return getMax(root).key;
    }

    /**
     * @return company.RBTree.RBTreeNode<K, V>
     * @throws
     * @Param [node]
     * @description 寻找以node为根的树中最大节点
     **/
    private RBTreeNode<K, V> getMax(RBTreeNode<K, V> node) {
        if(node.right != null) {
            return getMax(node.right);
        } else {
            return node;
        }
    }
    //计算整个树的最大深度
    public int getDepth() {
        return maxDepth(root);
    }

    /**
    * @Param [x]
    * @description  计算指定树x的最大深度
    * @return int
    * @throws
    **/
    private int maxDepth(RBTreeNode<K, V> node) {
        if(node == null) {
            return 0;
        }

        //1.计算左子树的最大深度；
        int maxL = maxDepth(node.left);

        //2.计算右子树的最大深度；
        int maxR = maxDepth(node.right);

        return (Math.max(maxL, maxR)) + 1;
    }

    /**
     * @description 获取树中非空元素的个数
     */
    public int getSize(){
        return size;
    }
    //遍历操作
    //前序遍历
    public List<K> preOrderTraversal() {
        List<K> list = new ArrayList<>();
        preOrderTraversal(root, list);
        return list;
    }

    private void preOrderTraversal(RBTreeNode<K, V> node, List<K> keyList) {
        if (node == null) {
            return;
        }
        keyList.add(node.key);
        preOrderTraversal(node.left, keyList);
        preOrderTraversal(node.right, keyList);
    }

    //中序遍历
    public List<K> inorderTraversal() {
        List<K> list = new ArrayList<>();
        inorderTraversal(root, list);
        return list;
    }

    private void inorderTraversal(RBTreeNode<K, V> node, List<K> keyList) {
        if (node == null) {
            return;
        }
        inorderTraversal(node.left, keyList);
        keyList.add(node.key);
        inorderTraversal(node.right, keyList);
    }

    //后序遍历
    public List<K> postOrderTraversal() {
        List<K> list = new ArrayList<>();
        postOrderTraversal(root, list);
        return list;
    }

    private void postOrderTraversal(RBTreeNode<K, V> node, List<K> keyList) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.left, keyList);
        postOrderTraversal(node.right, keyList);
        keyList.add(node.key);
    }

    //层序遍历
    public List<List<K>> levelTraversal() {
        List<List<K>> keyList = new ArrayList<>();
        Queue<RBTreeNode<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<K> level = new ArrayList<>();
            int size = queue.size();
            while (size > 0) {
                RBTreeNode<K, V> node = queue.poll();
                level.add(node.key);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                size--;
            }
            keyList.add(level);
        }
        return keyList;
    }
    public Queue<K> levelTraversal2(){
        Queue<K> keys = new LinkedList<>();
        Queue<RBTreeNode<K, V>> nodes = new LinkedList<>();

        nodes.add(root);
        while(!nodes.isEmpty()) {
            RBTreeNode<K, V> node = nodes.poll();
            keys.add(node.key);

            if(node.left != null) {
                nodes.add(node.left);
            }

            if(node.right != null) {
                nodes.add(node.right);
            }
        }
        return keys;
    }

    //插入操作
    public void add(K key, V value) {
        root = addNew(root, key, value);
        root.color = BLACK;//根节点设置为黑色
    }

    /**
     * @return
     * @throws
     * @Param
     * @description 添加节点时树的旋转和颜色翻转
     **/
    private RBTreeNode<K, V> addNew(RBTreeNode<K, V> node, K key, V value) {
        //如果树为空，直接将插入的节点设置为根节点
        if (node == null) {
            size++;
            return new RBTreeNode<>(key, value);
        }
        int diff = key.compareTo(node.key);
        if (diff < 0) {//如果插入的节点key小于当前节点,到当前节点的左子树
            node.left = addNew(node.left, key, value);
        } else if (diff > 0) { //如果插入的节点key大于当前节点,到当前节点的右子树
            node.right = addNew(node.right, key, value);
        } else {//如果插入的节点的key已经存在等于当前节点
            node.value = value;//将值替换为新值
        }
        return balance(node);
    }

    /**
     * @return company.RBTree.RBTreeNode<K, V>
     * @throws
     * @Param [node]
     * @description 判断是否需要旋转和颜色调整操作
     **/
    private RBTreeNode<K, V> balance(RBTreeNode<K, V> node) {
        //当前节点的右连接是红色，左连接是黑色，左旋
        if (isRed(node.right) && (!isRed(node.left))) {
            node = leftRotate(node);
        }
        //当前节点的左连接和左子节点的左连接都是红色，右旋
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        //当前节点的左连接和右连接都是红色，颜色翻转
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }


    /**
     * @return company.RBTree.RBTreeNode<T>
     * @throws
     * @Param [node]
     *    node                          x
     *   /    \red                 red/  \
     * l1      x     =>           node   r1
     *        / \                 /  \
     *       l2  r1              l1  l2
     * @description 左旋转 1） 将节点变为right节点的left节点，将right节点的left节点变为节点的right节点
     * 2） 节点颜色变为RED,right节点变为节点的颜色
     **/
    private RBTreeNode<K, V> leftRotate(RBTreeNode<K, V> node) {
        RBTreeNode<K, V> x = node.right;
        node.right = x.left;
        x.left = node;
        //改变颜色
        x.color = node.color;
        node.color = RED;
        return x;
    }
    /**
     * @return company.RBTree.RBTreeNode<T>
     *      node                 x
     *   red/ \            red / \red
     *     x   r2    =>     l1    node
     * red/ \                     / \
     * l1  r1                   r1  r2
     * @throws
     * @Param [node]
     * @description 右旋转
     **/
    private RBTreeNode<K, V> rightRotate(RBTreeNode<K, V> node) {
        RBTreeNode<K, V> x = node.left;

        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;
        return x;
    }

    /**
     * @return void
     * black|             red|
     *      b       =>      b
     * red/ \red      black/ \black
     * a   c            a   c
     * @description 颜色翻转
     **/
    private void flipColors(RBTreeNode<K, V> node) {
        node.left.color = BLACK;
        node.right.color = BLACK;
        node.color = RED;

    }


    //删除操作
    private void deleteColorFlip(RBTreeNode<K, V> node) {
        node.color = BLACK;
        node.left.color = RED;
        node.right.color = RED;
    }

    private void colorFlip_delete(RBTreeNode<K, V> node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    /**
     * @return company.RBTree.RBTreeNode<K, V>
     * @throws
     * @Param [node]
     * @description 假设节点node为红色，node.left和node.left.left为黑色，将node.left或它的子节点之一设置为红色
     **/
    private RBTreeNode<K, V> moveRedLeft(RBTreeNode<K, V> node) {
        //flipColors(node);
        colorFlip_delete(node);
        if (isRed(node.right.left)) {
            node.right = rightRotate(node.right);
            node = leftRotate(node);
           // flipColors(node);     //颜色翻转
            colorFlip_delete(node);//颜色翻转
        }
        return node;
    }

    /**
     * @return company.RBTree.RBTreeNode<K, V>
     * @throws
     * @Param [node]
     * @description 假设节点node为红色，node.right和node.right.left为黑色，将node.right或它的子节点之一设置为红色
     **/
    private RBTreeNode<K, V> moveRedRight(RBTreeNode<K, V> node) {
        flipColors(node);
        if (isRed(node.left.left)) {
            node = rightRotate(node);
            flipColors(node);
        }
        return node;
    }

    //删除最小值
    public void deleteMin() {
        if(root == null ){
            return;
        }
        //如果root的两个子节点均为黑色，则将root设置为红色
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);
        if ( size > 0 && (root != null)) {
            root.color = BLACK;
        }
        size--;
    }

    //private RBTreeNode<K, V> deleteMin(RBTreeNode<K, V> node) {
    //    if (node.left == null) {
    //        return null;
    //    }
    //    if (!isRed(node.left) && !isRed(node.left.left) && !isRed(node.right) && !isRed(node.right.left)) {
    //        //当前节点的左子节点和它的兄弟节点都是2-节点
    //        deleteColorFlip(node);
    //    } else if (!isRed(node.left) && !isRed(node.left.left) && !isRed(node.right) && isRed(node.right.left)) {
    //        //当前节点的左子节点是2-节点而它的兄弟节点不是2-节点
    //        node.right = rightRotate(node.right);
    //        node = leftRotate(node);
    //        node.right.color = BLACK;
    //        node.left.left.color = RED;
    //    }
    //    node.left = deleteMin(node.left);
    //
    //    return balance(node);
    //}
    //private RBTreeNode<K, V> deleteMin(RBTreeNode<K, V> node) {
    //    if (node.left == null) {
    //        return null;
    //    }
    //    if (!isRed(node.left) && !isRed(node.left.left)) {
    //        //colorFlip_delete(node);
    //        flipColors(node);
    //        if(isRed(node.right.left)){
    //            node.right = rightRotate(node.right);
    //            node = leftRotate(node);
    //            //colorFlip_delete(node);
    //            flipColors(node);
    //        }
    //    }
    //    node.left = deleteMin(node.left);
    //
    //    return balance(node);
    //}
    private RBTreeNode<K, V> deleteMin(RBTreeNode<K, V> node) {
        if (node.left == null) {
            return null;
        }

        if (!isRed(node.left) && !isRed(node.left.left)) {
            node = moveRedLeft(node);
        }

        node.left = deleteMin(node.left);
        return balance(node);
    }

    //删除最大值
    public void deleteMax() {
        if(root == null ){
            return;
        }
        //如果root的两个子节点均为黑色，则将root设置为红色
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if ( size > 0 && (root != null)) {
            root.color = BLACK;
        }
        size--;
    }

    //private RBTreeNode<K, V> deleteMax(RBTreeNode<K, V> node) {
    //    if (isRed(node.left)) {
    //        //左链接是红链接
    //        node = rightRotate(node);
    //    }
    //    if (node.right == null){
    //        return null;
    //    }
    //    if (!isRed(node.left) && !isRed(node.left.left) && !isRed(node.right) && !isRed(node.right.left)) {
    //        //当前节点的右子节点和它的兄弟节点都是2-节点
    //        deleteColorFlip(node);
    //    } else if (!isRed(node.left) && isRed(node.left.left) && !isRed(node.right) && !isRed(node.right.left)) {
    //        //当前节点的右子节点是2-节点而它的兄弟节点不是2-节点
    //        node.right = rightRotate(node.right);
    //        node=leftRotate(node);
    //        node.right.color = BLACK;
    //        node.left.left.color = RED;
    //    }
    //    node.right = deleteMax(node.right);
    //
    //    return balance(node);
    //}
    private RBTreeNode<K, V> deleteMax(RBTreeNode<K, V> node) {
        if (isRed(node.left)) {
            //左链接是红链接
            node = rightRotate(node);
        }
        if (node.right == null) {
            return null;
        }
        if (!isRed(node.right) && !isRed(node.right.left)) { //右子节点不为2-节点
            node = moveRedRight(node);
        }
        node.right = deleteMax(node.right);

        return balance(node);
    }

    public void delete(K key) {
        // 如果root的两个子节点均为黑色，则将root设置为red
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = delete(root, key);
        if (size > 0) {
            root.color = BLACK;
        }
    }

    /**
     * @return company.RBTree.RBTreeNode<K, V>   新树
     * @throws
     * @Param [node, key]
     * @description 删除制定的键值
     **/
    private RBTreeNode<K, V> delete(RBTreeNode<K, V> node, K key) {
        if (key.compareTo(node.key) < 0)  {
            //如果左右子节点都是空，表示到底，没有找到要删除的值。
            if(node.left==null && node.right ==null){
                return node;
            }
            if (!isRed(node.left) && !isRed(node.left.left)) {
                // 此时h为红h.left为黑，moveRedLeft把红色推到左子树以便调用下面的delete
                node = moveRedLeft(node);
            }
            node.left = delete(node.left, key);
        }
        else {
            if (isRed(node.left)) {
                // 如果h.left为红则右旋，使h.right变红，方便从右子树里删除
                node = rightRotate(node);
            }
            // 此时h为红或者h.right为红。
            if (key.compareTo(node.key) == 0 && (node.right == null)) {
                // h.right == null所以上面的旋转没有发生
                // 右子树null，左子树不为红，只能也是null，不然黑高不平衡
                size--;
                return null;
            }
            // 要删的结点在右子树
            // 如果要删的是h，与右子树的min对调key-value后删除右子树的min
            if (!isRed(node.right) && !isRed(node.right.left)) {
                node = moveRedRight(node);
            }
            if (key.compareTo(node.key) == 0) {
                RBTreeNode<K,V> x = getMin(node.right);
                node.key = x.key;
                node.value = x.value;
                node.right = deleteMin(node.right);
            }
            else {
                node.right = delete(node.right, key);
            }
        }
        return balance(node);
    }

    static class RBTreeNode<K extends Comparable<K>, V> {
        boolean color;
        K key;
        V value;
        RBTreeNode<K, V> parent;
        RBTreeNode<K, V> left;
        RBTreeNode<K, V> right;

        public RBTreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.color = RED;
            this.parent = null;
            this.left = null;
            this.right = null;
        }

        public boolean getColor(RBTreeNode<K, V> node) {
            if (node == null) {
                return BLACK;
            }
            return node.color;
        }
    }
}


