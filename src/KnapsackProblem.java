import java.util.*;

public class KnapsackProblem {
    int n;
    double[] weight;
    double[] value;
    double capacity;

    public KnapsackProblem(int n, double[] weight, double[] value, double capacity) {
        this.n = n;
        this.weight = weight;
        this.value = value;
        this.capacity = capacity;
    }

    public KnapsackProblemSolution getSolutionBranchBound() {
        double resultValue = 0;
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
        //[0] - weigh; [1] - value; [2] - up bound
        PriorityQueue<BinaryTreeNode<Integer, Double[]>> seekLeafs = new PriorityQueue<>(
                (x, y) -> (int) Math.round(y.getValue()[2] - x.getValue()[2])
        );
        seekLeafs.add(treeRoot);
        BinaryTreeNode<Integer, Double[]> currentNode;
        BinaryTreeNode<Integer, Double[]> solutionNode = null;
        do {
            currentNode = seekLeafs.poll();
            for (int i = currentNode.getRemoteness(); i < n; i++) {
                currentNode.setLeft(new BinaryTreeNode<Integer, Double[]>(
                        sortedKeys[i],
                        new Double[]{currentNode.getValue()[0] + weight[sortedKeys[i]],
                                currentNode.getValue()[1] + value[sortedKeys[i]],
                                currentNode.getValue()[1] + value[sortedKeys[i]] +
                                        (capacity - currentNode.getValue()[0] - weight[sortedKeys[i]]) * (
                                                i < n - 1 ? sortedUnitValue[i + 1] : 0
                                        )}));
                currentNode.setRight(new BinaryTreeNode<Integer, Double[]>(
                        sortedKeys[i],
                        new Double[]{currentNode.getValue()[0],
                                currentNode.getValue()[1],
                                currentNode.getValue()[1] + (capacity - currentNode.getValue()[0]) * (
                                        i < n - 1 ? sortedUnitValue[i + 1] : 0
                                )}));
                if (currentNode.getLeft().getValue()[0] <= capacity && currentNode.getRight().getValue()[0] <= capacity) {
                    if (currentNode.getLeft().getValue()[2] > currentNode.getRight().getValue()[2]) {
                        seekLeafs.add(currentNode.getRight());
                        currentNode = currentNode.getLeft();
                    } else {
                        seekLeafs.add(currentNode.getLeft());
                        currentNode = currentNode.getRight();
                    }
                } else {
                    if (currentNode.getLeft().getValue()[0] > capacity && currentNode.getRight().getValue()[0] > capacity) {
                        return null;
                    }
                    if (currentNode.getLeft().getValue()[0] > capacity) {
                        currentNode = currentNode.getRight();
                    } else if (currentNode.getRight().getValue()[0] > capacity) {
                        currentNode = currentNode.getLeft();
                    }
                }
            }
            if (solutionNode == null || currentNode.getValue()[2] > solutionNode.getValue()[2]) {
                solutionNode = currentNode;
            }
        }
        while (seekLeafs != null && solutionNode.getValue()[2] < seekLeafs.peek().getValue()[2]);
        // по достижению крайней вершины ветви решения происходит отсечение заведомо худших ветвей решений по сравнению с найденной

        ArrayList<Integer> items = new ArrayList<>();
        BinaryTreeNode<Integer, Double[]> node = solutionNode;
        while (node.getParent() != null) {
            if (!node.getValue()[1].equals(node.getParent().getValue()[1])) {
                items.add(0, node.getKey());
                resultValue += value[node.getKey()];
            }
            node = node.getParent();
        }
        return new KnapsackProblemSolution(resultValue, items);
    }

    public KnapsackProblemSolution getSolutionGreedy() {
        double maxValue = Double.MAX_VALUE;
        int maxValueIndex = 0;
        for(int  i = 0; i < n; i++) {
            if(maxValue < value[i]) {
                maxValue = value[i];
                maxValueIndex = i;
            }
        }

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

        double currCapacity = 0;
        double itemsValue = 0;
        ArrayList<Integer> items = new ArrayList<>();
        for(int  i = 0; i < n; i++) {
            if(currCapacity + weight[sortedKeys[i]] <= capacity) {
                items.add(sortedKeys[i]);
                currCapacity += weight[sortedKeys[i]];
                itemsValue += value[sortedKeys[i]];
            }
        }

        if(value[maxValueIndex] > itemsValue) {
            ArrayList<Integer> item = new ArrayList<>();
            item.add(maxValueIndex);
            return new KnapsackProblemSolution(maxValue, item);
        }

        return new KnapsackProblemSolution(itemsValue, items);
    }

    public static void test () {
        int testsNum = 10000;
        int itemNum = 100;
        double knapsackCapacity = 100.0;
        double maxValue = 100;
        double itemWeightCoeff = 0.25;
        Random rand = new Random();
        long testsTimeBranchBound = 0;
        long testsTimeGreedy = 0;

        double algAccuracySum = 0;
        double algAccuracy = 0;
        long timeStart, timeEnd;

        for (int i = 0; i < testsNum; i++) {
            double[] weight = new double[itemNum];
            double[] value = new double[itemNum];
            for (int j = 0; j < itemNum; j++) {
                weight[j] = Math.round(rand.nextDouble() * (knapsackCapacity * itemWeightCoeff));
                value[j] = Math.round(rand.nextDouble() * maxValue);
            }
            KnapsackProblem kp = new KnapsackProblem(itemNum, weight, value, knapsackCapacity);
            timeStart = (new Date()).getTime();
            KnapsackProblemSolution branchBoundSolution = kp.getSolutionBranchBound();
            timeEnd = (new Date()).getTime();
            testsTimeBranchBound += timeEnd - timeStart;

            timeStart = (new Date()).getTime();
            KnapsackProblemSolution greedySolution = kp.getSolutionGreedy();
            timeEnd = (new Date()).getTime();
            testsTimeGreedy += timeEnd - timeStart;

            algAccuracySum += greedySolution.getValue() / branchBoundSolution.getValue();
        }
        algAccuracy = (double) algAccuracySum / testsNum;
        System.out.println("Среднее время работы программы для " + itemNum +
                " предметов на основе " +  testsNum + " тестов:");
        System.out.println("Метод ветвей и границ: " + (double)testsTimeBranchBound / testsNum + " мс");
        System.out.println("Жадный алгоритм: " + (double)testsTimeGreedy / testsNum + " мс");
        System.out.println("Точность: " + algAccuracy * 100 + "%" );
    }

}
