package company;

/**
* @Description:
* @Author:         Rongsheng Zhang
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class RBTree<K extends Comparable<K>, V> {
    private TreeNode<K, V> ROOT;//根节点
    private int size;//节点个数
    private TreeNode<K, V> NIL;//根节点
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public RBTree() {
        //创建一个伪根节点，该节点的右子支才是真正的RBtree树的根,同时该节点还作为NIL节点
        //使用伪根节点节点的目的是，对插入和删除操作递归的形式能够统一
        ROOT = new TreeNode<K, V>(BLACK, null, null, null, null, null);
        NIL = new TreeNode<K, V>(BLACK, null, null, null, null, null);
        NIL.left = NIL;
        NIL.right = NIL;
        ROOT.left = NIL;
        ROOT.right = NIL;
        ROOT.parent = ROOT;
    }

    public int size() {
        return size;
    }

    /**
     * @return boolean
     * @throws
     * @Param [X]
     * @description 判断是否存在X的key值
     **/
    public boolean containsKey(K key) {
        TreeNode<K, V> X = new TreeNode<>(key);
        if (key == null) {
            return false;
        } else {
            TreeNode<K, V> root = ROOT.right;
            return containsX(root, X);
        }
    }

    /**
     * @return boolean
     * @throws
     * @Param [X]
     * @description 判断是否存在X的节点
     **/
    public boolean containsNode(TreeNode<K, V> X) {
        if (X == NIL) {
            return false;
        } else {
            TreeNode<K, V> root = ROOT.right;
            if (root != NIL) {
                return containsX(root, X);
            } else {
                return false;
            }
        }
    }

    private boolean containsX(TreeNode<K, V> root, TreeNode<K, V> X) {
        int diff = root.key.compareTo(X.key);
        if (diff < 0) {
            if (root.right != NIL) {
                containsX(root.right, X);
            }
        } else if (diff > 0) {
            if (root.left != NIL) {
                containsX(root.left, X);
            }
        } else {
            if(X.value==root.value){
                return true;
            }
        }
        return false;
    }

    /**
     * @return void
     * @throws
     * @Param [X]
     * @description 以X为轴向右选旋转，不进行颜色变换
     **/
    private void rotateToRight(TreeNode<K, V> X) {
        TreeNode<K, V> P = X.parent;
        TreeNode<K, V> XL = X.left;
        if (P.left == X) {
            P.left = XL;
        } else {
            P.right = XL;
        }
        XL.parent = P;

        X.left = XL.right;
        if (XL.right != NIL) {
            XL.right.parent = X;
        }

        XL.right = X;
        X.parent = XL;
    }

    /**
     * @return void
     * @throws
     * @Param [X]
     * @description 以X为轴向左选旋转，不进行颜色变换
     **/
    private void rotateToLeft(TreeNode<K, V> X) {
        TreeNode<K, V> P = X.parent;
        TreeNode<K, V> XR = X.right;
        if (P.left == X) {
            P.left = XR;
        } else {
            P.right = XR;
        }
        XR.parent = P;

        X.right = XR.left;
        if (XR.left != NIL) {
            XR.left.parent = X;
        }

        XR.left = X;
        X.parent = XR;
    }

    /**
     * @return K
     * @throws
     * @Param []
     * @description 获取树的最小key
     **/
    public K min() {
        return min(ROOT.right).key;
    }

    private TreeNode<K, V> min(TreeNode<K, V> X) {
        if (X.left != NIL) {
            X = min(X.left);
        }
        return X;
    }


    public boolean insert(K key, V value) {
        TreeNode<K, V> P = ROOT;
        TreeNode<K, V> X = ROOT.right;
        int diff = 0;
        //如果根节点不为空
        while (X != NIL) {
            diff = key.compareTo(X.key);
            P = X;
            if (diff > 0) {
                X = X.right;
            } else if (diff < 0) {
                X = X.left;
            } else {
                X.value = value;//将新值替换老值
            }
        }

        TreeNode<K, V> G;
        TreeNode<K, V> U;
        X = new TreeNode<K, V>(RED, key, value, P, NIL, NIL);//插入的新节点红色
        if (diff >= 0) {//考虑到首次插入的情况，这个等号是必须的
            P.right = X;
        } else {
            P.left = X;
        }

        while (true) {
            P = X.parent;
            //红父
            if (P.isRed()) {
                G = P.parent;
                if (P == G.left) {
                    U = G.right;
                } else {
                    U = G.left;
                }

                //红叔，颜色翻转，继续向上回溯
                if (U.isRed()) {
                    P.color = BLACK;
                    U.color = BLACK;
                    G.color = RED;
                    X = G;//继续向上回溯
                } else {//黑叔
                    //P 是G的左孩子
                    if (G.left == P) {
                        //X是P的左孩子，右旋
                        if (P.left == X) {
                            rotateToRight(G);
                            P.color = BLACK;
                            G.color = RED;
                            //X是P的右孩子，先左旋再右旋
                        } else {
                            rotateToLeft(P);
                            rotateToRight(G);
                            X.color = BLACK;
                            G.color = RED;
                        }
                        //P 是G的右孩子 与上面正好相反
                    } else {
                        //X是P的右孩子，左旋
                        if (P.right == X) {
                            rotateToLeft(G);
                            P.color = BLACK;
                            G.color = RED;
                            //X是P的左孩子，先右旋再左旋
                        } else {
                            rotateToRight(P);
                            rotateToLeft(G);
                            X.color = BLACK;
                            G.color = RED;
                        }
                    }
                    break;
                }
            } else {//黑父
                break;
            }
        }
        size++;
        ROOT.right.color = BLACK;//有可能向上层进位，根节点设置黑
        return true;
    }

    /**
    * @Param
    * @description  删除节点
    * @return
    * @throws
    **/
    public boolean delete(K key) {
        TreeNode<K, V> X = ROOT.right;
        X.color = RED; //删除时，根先涂红，1.防止继续向上回溯  2.只有根节点时也方便删除
        TreeNode<K, V> P;//父节点
        TreeNode<K, V> B;//兄弟节点

        while (X != NIL) {
            int diff = key.compareTo(X.key);
            if (diff > 0) {
                X = X.right;
            } else if (diff < 0) {
                X = X.left;
            } else {
                break;
            }
        }

        if (X == NIL) {//没有找到需要删除的节点
            ROOT.right.color = BLACK;
            return false;
        }

        size--;//一定可以删除一个节点
        //交换最小节点和要删除的节点
        if (X.left != NIL && X.right != NIL) {
            TreeNode<K, V> tmp = min(X.right);
            X.key = tmp.key;
            X = tmp;
        }

        P = X.parent;
        //X的right不为空，将X的right到X的位置
        if (X.right != NIL) {
            if (X == P.left) {
                P.left = X.right;
            } else {
                P.right = X.right;
            }
            X.right.parent = P;
            X.color = BLACK;
            ROOT.right.color = BLACK;
            return true;
            //X的left不为空，将X的left到X的位置
        } else if (X.left != NIL) {
            if (X == P.left) {
                P.left = X.left;
            } else {
                P.right = X.left;
            }
            X.left.parent = P;
            X.color = BLACK;
            ROOT.right.color = BLACK;
            return true;
            //X的左右都为空节点，
        } else {
            //将X的子节点设置为空
            if (X == P.left) {
                P.left = NIL;
            } else {
                P.right = NIL;
            }
            //如果X是红色，
            if (X.isRed()) {
                ROOT.right.color = BLACK;
                return true;
            } else {
                X = NIL;
            }
        }

        //要删除的是叶子节点
        //四中情况调整
        while (true) {
            //获取兄弟节点
            if (X == P.left) {
                B = P.right;
            } else {
                B = P.left;
            }

            if (!B.isRed()) {//黑兄
                TreeNode<K, V> BL = B.left;//左侄子
                TreeNode<K, V> BR = B.right;//右侄子
                if (B.left.isRed() || B.right.isRed()) {//红侄
                    if (X == P.left) {//X是父节点的left
                        if (BR.isRed()) {//如果左侄子是红色，只需左旋转
                            rotateToLeft(P);
                            BR.color = BLACK;
                            B.color = P.color;
                            P.color = BLACK;
                        } else {//如果左侄子是黑色，先右旋再左旋
                            rotateToRight(B);
                            rotateToLeft(P);
                            BL.color = P.color;
                            P.color = BLACK;
                        }
                    } else {//X是父节点的right（镜像情况），与上面操作正好相反
                        if (BL.isRed()) {//如果左侄子是红色，只需右旋转
                            rotateToRight(P);
                            BL.color = BLACK;
                            B.color = P.color;
                            P.color = BLACK;
                        } else {//如果左侄子是黑色，先左旋再右旋
                            rotateToLeft(B);
                            rotateToRight(P);
                            BR.color = P.color;
                            P.color = BLACK;
                        }
                    }
                    break;//不需要继续向上回溯
                } else {//黑侄
                    if (P.isRed()) {//黑侄红父
                        P.color = BLACK;
                        B.color = RED;
                        break;//不需要继续向上回溯
                    } else {//黑侄黑父,继续向上回溯
                        B.color = RED;
                        X = P;
                        P = X.parent;
                    }
                }
            } else {//红兄，变换一下红黑树的形状，继续判断
                if (B == P.right) {
                    rotateToLeft(P);
                } else {
                    rotateToRight(P);
                }
                B.color = BLACK;
                P.color = RED;
                //X节点的P节点没有发生变化，但兄弟节点发生变化
            }
        }
        ROOT.right.color = BLACK;
        return true;
    }
    /**
    * @Param []
    * @description  先序遍历
    * @return void
    * @throws
    **/
    public void preorderTraverse(){
        preorderTraverse(ROOT.right);
    }

    private void preorderTraverse(TreeNode<K,V> X){
        if(X != NIL){
            System.out.print(X.key + "    " + (X.isRed() ? "RED  " : "BLACK") + "   :");
            if(X.left != NIL){
                System.out.print(X.left.key + "   ");
            }else{
                System.out.print("NIL  ");
            }

            if(X.right != NIL){
                System.out.print(X.right.key + "   ");
            }else{
                System.out.print("NIL  ");
            }
            System.out.println();
            preorderTraverse(X.left);
            preorderTraverse(X.right);
        }
    }

    private static class TreeNode<K extends Comparable<K>, V> {
        boolean color;
        K key;
        V value;
        TreeNode<K, V> parent;
        TreeNode<K, V> left;
        TreeNode<K, V> right;

        public TreeNode(boolean color, K key, V value, TreeNode<K, V> parent, TreeNode<K, V> left, TreeNode<K, V> right) {
            this.color = color;
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public TreeNode(K key) {
            this.key = key;
        }

        public boolean isRed() {
            return color;
        }
    }
}
