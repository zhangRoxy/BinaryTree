package company;

import java.util.List;
/**
* @Description:
* @Author:         Rongsheng Zhang
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class Main {

    public static void main(String[] args) {

        //BinarySearchTree b = new BinarySearchTree();
        //b.insert(3);b.insert(8);b.insert(1);b.insert(4);b.insert(6);
        //b.insert(2);b.insert(10);b.insert(9);b.insert(20);b.insert(25);
        //
        //// 打印二叉树
        //b.toString(b.root);
        //System.out.println();
        //
        //// 是否存在节点值10
        //TreeNode node01 = b.search(10);
        //System.out.println("是否存在节点值为10 " + node01.value);
        //// 是否存在节点值11
        //TreeNode node02 = b.search(11);
        //System.out.println("是否存在节点值为11" + node02);
        //
        //// 删除节点8
        //TreeNode node03 = b.delete(8);
        //System.out.println("删除节点8 " + node03.value);
        //b.toString(b.root);
        //TreeNode node04=b.search(10);
        //System.out.println("节点3的值 " + node04.value);
        //System.out.println("节点3的左孩子 " + node04.left);
        //System.out.println("节点3的右孩子  " + node04.right.value);
        //左倾红黑树
       // LLRBTree<Integer,Integer> rbTree= new LLRBTree<Integer, Integer>();
       // rbTree.add(10,10);
       // rbTree.add(40,40);
       // rbTree.add(70,70);
       // rbTree.add(30,30);
       // rbTree.add(60,60);
       // rbTree.add(20,20);
       // rbTree.add(50,50);
       // rbTree.add(80,80);
       //List< List<Integer>> list= rbTree.levelTraversal();
       //System.out.println(list);
       // System.out.println("size："+rbTree.getSize());
       // System.out.println("depth："+rbTree.getDepth());
       //
       // System.out.println("最大值："+rbTree.getMax());
       // rbTree.delete(10);
       // System.out.println(rbTree.getValue(10));
       // System.out.println("删除10后的size："+rbTree.getSize());
       // System.out.println("删除10后的depth："+rbTree.getDepth());
       // System.out.print("删除后的最小值与最大值：");
       // System.out.println(rbTree.getValue(rbTree.getMin()) + "  "  + rbTree.getValue(rbTree.getMax()));
       // List< List<Integer>> list1= rbTree.levelTraversal();
       // System.out.println(list1);

        RBTree<Integer,Integer> rbt=new RBTree<>();
        rbt.insert(50,50);
        rbt.insert(25,25);
        rbt.insert(75,75);
        rbt.insert(10,10);
        rbt.insert(30,30);
        rbt.insert(27,27);
        rbt.insert(35,35);
        rbt.insert(40,40);
        rbt.insert(31,31);
        rbt.insert(55,55);
        rbt.insert(80,80);
        rbt.insert(90,90);
        rbt.delete(10);
        rbt.preorderTraverse();
        System.out.println();
        System.out.println("size: " + rbt.size());
        System.out.println(rbt.containsKey(40));

    }
}
