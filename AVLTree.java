package company;
/**
* @Description:
* @Author:         Rongsheng Zhang
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class AVLTree {

    public AVLTreeNode insert(AVLTreeNode root, int data) {
        AVLTreeNode newNode=new AVLTreeNode(data);
        //如果插入在节点的左孩子的子树
        if (data < root.data) {
            if (root.leftChild != null) {
                insert(root.leftChild, data);
            } else {
                root.leftChild = newNode;
                root.leftChild.parent = root;
            }
            //如果插入在节点的右孩子的子树
        } else if (data > root.data) {
            if (root.rightChild != null) {
                insert(root.rightChild, data);
            } else {
                root.rightChild = newNode;
                root.rightChild.parent = root;
            }
        }
        //插入之后计算平衡因子
        root.balanceFactor = getBalanceFactor(root);
        //右旋
        if (root.balanceFactor > 1) {
            if (root.leftChild.balanceFactor == -1) {
                leftRotate(root.leftChild);
            }
            rightRotate(root);
        }
        //左旋
        if (root.balanceFactor <1){
            if (root.rightChild.balanceFactor == -1) {
                rightRotate(root.rightChild);
            }
            leftRotate(root);
        }
        //调整后重新计算平衡因子和树深度
        root.balanceFactor=getBalanceFactor(root);
        root.depth=getDepth(root);
        return newNode;
    }

    public void leftRotate(AVLTreeNode node) {
        AVLTreeNode rightChild = node.rightChild; //节点node的右孩子
        AVLTreeNode leftGrandChild = rightChild.leftChild;//节点node右孩子的左孩子
        AVLTreeNode parent = node.parent;//节点node的父节点

        rightChild.parent = parent;//右孩子的父节点是节点node的父节点
        //如果节点node的父节点不为Null，
        // 如果节点node是父节点的右孩子，则设置节点node的右孩子为节点node的父节点的右孩子
        //如果节点node是父节点的左孩子，则设置节点node的右孩子为节点node的父节点的左孩子
        if (parent != null) {
            if (node == parent.rightChild) {
                parent.rightChild = rightChild;
            } else if (node == parent.leftChild) {
                parent.leftChild = rightChild;
            }
        }

        rightChild.leftChild = node;//节点node变为右孩子的左孩子
        node.parent = rightChild;//节点node的父节点是它的右孩子
        node.rightChild = leftGrandChild;//节点node的右孩子是右孩子的左孩子
        if (leftGrandChild != null) {
            leftGrandChild.parent = node;//如果节点node的右孩子是右孩子的左孩子不为Null,则设置这个不为Null的父节点为节点node
        }
        node.depth = getDepth(node);
        node.balanceFactor = getBalanceFactor(node);
        rightChild.depth = getDepth(rightChild);
        rightChild.balanceFactor = getBalanceFactor(rightChild);
    }

    public void rightRotate(AVLTreeNode node) {
        AVLTreeNode leftChild = node.leftChild; //节点node的左孩子
        AVLTreeNode rightGrandChild = leftChild.rightChild;//节点node左孩子的右孩子
        AVLTreeNode parent = node.parent;//节点node的父节点

        leftChild.parent = parent;//左孩子的父节点改为节点node的父节点
        if (parent != null) {
            if (node == parent.rightChild) {
                parent.rightChild = leftChild;
            } else if (node == parent.leftChild) {
                parent.leftChild = leftChild;
            }
        }

        leftChild.rightChild = node;
        node.parent = leftChild;
        node.leftChild = rightGrandChild;
        if (rightGrandChild != null) {
            rightGrandChild.parent = node;
        }
        node.depth = getDepth(node);
        node.balanceFactor = getBalanceFactor(node);
        leftChild.depth = getDepth(leftChild);
        leftChild.balanceFactor = getBalanceFactor(leftChild);
    }

    /**
     * @return int
     * @throws
     * @Param [root]
     * @description 计算平衡因子
     **/
    public int getBalanceFactor(AVLTreeNode root) {
        int leftTreeDepth, rightTreeDepth;
        if (root.leftChild != null) {
            leftTreeDepth = root.leftChild.depth;
        } else {
            leftTreeDepth = 0;
        }
        if (root.rightChild != null) {
            rightTreeDepth = root.rightChild.depth;
        } else {
            rightTreeDepth = 0;
        }
        return leftTreeDepth - rightTreeDepth;
    }

    /**
     * @return int
     * @throws
     * @Param [root]
     * @description 获取节点深度
     **/
    public int getDepth(AVLTreeNode root) {
        int depth = 0;
        if (root.leftChild != null) {
            depth = root.leftChild.depth;
        }
        if (root.rightChild != null && root.rightChild.depth > depth) {
            depth = root.rightChild.depth;
        }
        return depth + 1;
    }

    class AVLTreeNode {
        private int depth;
        private AVLTreeNode parent;
        private int data;
        AVLTreeNode leftChild;
        AVLTreeNode rightChild;
        private int balanceFactor;

        public AVLTreeNode(int data) {
            this.data = data;
            this.parent = null;
            this.depth = 1;
            this.leftChild = null;
            this.rightChild = null;
            this.balanceFactor = 0;
        }
    }
}


