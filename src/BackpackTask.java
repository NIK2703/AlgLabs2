import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class BackpackTask {
    int n;
    double[] weight;
    double[] value;
    double capacity;

    public BackpackTask(int n, double[] weight, double[] value, double capacity) {
        this.n = n;
        this.weight = weight;
        this.value = value;
        this.capacity = capacity;
    }

    public int[] getSolution() {
        TreeMap<Integer, Double> unitValue = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            unitValue.put(i, value[i] / weight[i]);
        }
        int[] sortedKeys = new int[n];
        double[] sortedUnitValue = new double[n];
        Iterator iter = unitValue.entrySet().iterator();
        for (int i = 0; i < n; i++) {
            Map.Entry m = (Map.Entry)iter.next();
            sortedKeys[i] = (Integer)m.getKey();
            sortedUnitValue[i] = (Double) m.getValue();
        }
        double startValue = 0;
        for (int i = 0; i < value.length; i++) {
            startValue += value[i];
        }
        BinaryTreeNode<Integer, Double[]> treeRoot = new BinaryTreeNode<>(0, new Double[]{0.0, 0.0, startValue});
        BinaryTreeNode<Integer, Double[]> currentNode = treeRoot;
        for (int i = 0; i < n - 1; i++) {//!!!!!!!!!!!!!!!!!!!!!!!!
            currentNode.setLeft(new BinaryTreeNode<Integer, Double[]>(
                    unitValue.firstKey(),
                    new Double[]{currentNode.getValue()[0] + weight[sortedKeys[i]],
                            currentNode.getValue()[1] + value[sortedKeys[i]],
                            currentNode.getValue()[1] + value[sortedKeys[i]] + (capacity - weight[sortedKeys[i]])*(value[i + 1] / weight[i + 1])}));
            currentNode.setRight(new BinaryTreeNode<Integer, Double[]>(
                    unitValue.firstKey(),
                    new Double[]{ currentNode.getValue()[0],
                            currentNode.getValue()[1],
                            capacity *(value[i + 1] / weight[i + 1])}));
            if (currentNode.getLeft().getValue()[0] <= capacity && currentNode.getRight().getValue()[0] <= capacity) {
                if (currentNode.getLeft().getValue()[2] > currentNode.getRight().getValue()[2]) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode = currentNode.getRight();
                }
            }
            else {
                if (currentNode.getLeft().getValue()[0] > capacity && currentNode.getRight().getValue()[0] > capacity) {
                    return null;
                }
                if (currentNode.getLeft().getValue()[0] > capacity) {
                    currentNode = currentNode.getRight();
                }
                else if (currentNode.getRight().getValue()[0] > capacity) {
                    currentNode = currentNode.getLeft();
                }
            }
        }
        return null;
    }

}
