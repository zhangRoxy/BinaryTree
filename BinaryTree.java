package company;


import java.util.*;

/**
* @Description:  二叉树遍历方式
* @Author:         Rongsheng Zhang
* @UpdateUser:     Rongsheng Zhang
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class BinaryTree {
    /**
     * @description 递归方式前序遍历
     **/
    public List<Integer> preorderTraverse(BinaryTreeNode node) {
        List<Integer> result = new ArrayList<>();
        this.traverse(node, result);
        return result;
    }

    public void traverse(BinaryTreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.data);
        traverse(node.leftChild, result);
        traverse(node.rightChild, result);
    }

    /**
     * @description 非递归方式前序遍历
     **/
    public List<Integer> preorderTraverse2(BinaryTreeNode node) {
        BinaryTreeNode currentNode = node;
        List<Integer> result = new ArrayList<>();
        Stack<BinaryTreeNode> stack = new Stack<>();


        while (currentNode != null || !stack.isEmpty()) {
            if (currentNode != null) {
                result.add(currentNode.data);
                stack.push(currentNode);
                currentNode = currentNode.leftChild;
            } else {
                currentNode = stack.pop();
                currentNode = currentNode.rightChild;
            }

        }
        return result;
    }

    /**
     * @description 递归方式中序遍历
     **/
    public List<Integer> inorderTraverse(BinaryTreeNode node) {
        List<Integer> result = new ArrayList<>();
        this.traverse2(node, result);
        return result;
    }

    public void traverse2(BinaryTreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        traverse2(node.leftChild, result);
        result.add(node.data);
        traverse2(node.rightChild, result);
    }

    /**
     * @description 非递归方式中序遍历
     **/
    public List<Integer> inorderTraverse2(BinaryTreeNode node) {
        BinaryTreeNode currentNode = node;
        List<Integer> result = new ArrayList<>();
        Stack<BinaryTreeNode> stack = new Stack<>();
        while (currentNode != null || !stack.isEmpty()) {
            if (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.leftChild;
            } else {
                currentNode = stack.pop();
                result.add(currentNode.data);
                currentNode = currentNode.rightChild;
            }
        }
        return result;
    }


    /**
     * @description 递归方式后序遍历
     **/
    public List<Integer> postOrderTraverse(BinaryTreeNode node) {
        List<Integer> result = new ArrayList<>();
        this.traverse3(node, result);
        return result;
    }

    public void traverse3(BinaryTreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        traverse3(node.leftChild, result);
        traverse3(node.rightChild, result);
        result.add(node.data);
    }

    /**
     * @description 非递归方式后序遍历
     **/
    public List<Integer> postOrderTraverse2(BinaryTreeNode node) {
        BinaryTreeNode currentNode = node;
        List<Integer> result = new ArrayList<>();
        Stack<BinaryTreeNode> stack = new Stack<>();
        BinaryTreeNode recent = null;
        while (currentNode != null || !stack.isEmpty()) {
            if (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.leftChild;
            } else {
                currentNode = stack.peek();//获取栈顶元素
                //如果存在右孩子并且右孩子和最近访问输出的不同则入栈，否则已经入栈
                if (currentNode.rightChild != null && recent != currentNode.rightChild) {
                    currentNode = currentNode.rightChild;
                    stack.push(currentNode);
                    currentNode = currentNode.leftChild;
                    //如果没有左右孩子则访问输出该节点
                } else {
                    currentNode = stack.pop();
                    result.add(currentNode.data);
                    recent = currentNode;//最近输出的节点
                    currentNode = null;//当前访问节点置空

                }
            }
        }
        return result;
    }


    /**
    * @Param [node]
    * @description  层序遍历
    * @return java.util.List<java.util.List<java.lang.Integer>>
    * @throws
    **/
    public List< List< Integer > > levelTraverse(BinaryTreeNode node) {
        List< List< Integer > > result = new ArrayList<>();
        //如果node为空，直接返回
        if (node == null) {
            return result;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(node);//将第一层根节点入队列
        while (!queue.isEmpty()) {//只要队列不为空，执行循环
            List<Integer> level = new ArrayList<>();
            //记录此时队列的长度，此时的size代表了某一层一共有多少个节点，防止被后边入队的节点个数影响输出这一层的节点
            int size = queue.size();
            while (size > 0) {
                BinaryTreeNode binaryTreeNode = queue.poll();//取出这一层第一个节点
                level.add( binaryTreeNode.data );//将节点值保存输出
                if (binaryTreeNode.leftChild != null) {
                    queue.offer(binaryTreeNode.leftChild);//将此节点的左子树根节点入队列
                }
                if (binaryTreeNode.rightChild != null) {
                    queue.offer(binaryTreeNode.rightChild);//将此节点的右子树根节点入队列
                }
                size--;
            }
            result.add(level);
        }
        return result;
    }
}

class BinaryTreeNode {
    int data;
    BinaryTreeNode leftChild;
    BinaryTreeNode rightChild;

    public BinaryTreeNode(int data) {
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
    }
}
