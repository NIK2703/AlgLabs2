import java.util.ArrayList;

public class BinaryTreeNode<K extends Comparable, V> {
    private BinaryTreeNode<K, V> left, right, parent;
    private K key;
    private V value;

    public int getHeight(int currentHeight) {
        if (left != null && right != null) {
            return Math.max(left.getHeight(currentHeight + 1), right.getHeight(currentHeight + 1));
        }
        else if (left != null) {
            return left.getHeight(currentHeight + 1);
        }
        else if (right != null) {
            return right.getHeight(currentHeight + 1);
        }
        else {
            return currentHeight;
        }
    }

    public BinaryTreeNode getMinLeaf () {
        if (left != null && right != null) {
            return left.getMinLeaf().getKey().compareTo(right.getMinLeaf().getKey()) < 0 ? left : right;
        }
        else if (left != null) {
            return left.getMinLeaf();
        }
        else if (right != null) {
            return right.getMinLeaf();
        }
        else {
            return this;
        }
    }

    public ArrayList<BinaryTreeNode> getPathToNode (BinaryTreeNode node, ArrayList<BinaryTreeNode> prevPath) {
        prevPath.add(this);
        ArrayList<BinaryTreeNode> subtreeFindResult = null;
        if (left != null) {
            subtreeFindResult = left.getPathToNode(node, prevPath);
        }
        if (subtreeFindResult == null && right != null) {
            subtreeFindResult = right.getPathToNode(node, prevPath);
        }
        return subtreeFindResult;


    }

    public BinaryTreeNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public BinaryTreeNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
        left.parent = this;
    }

    public BinaryTreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
        right.parent = this;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public BinaryTreeNode<K, V> getParent() {
        return parent;
    }
}
