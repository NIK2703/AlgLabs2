public class Main {
    public static void main(String[] args) {
        int n = 4;
        double[] weight = {4, 7, 5, 3};
        double[] value = {40, 42, 25, 12};
        double capacity = 10;

        /*int n = 5;
        double[] weight = {3, 4, 5, 8, 9};
        double[] value = {1, 6, 4, 7, 6};
        double capacity = 13;*/

        /*int n = 3;
        double[] weight = {15, 30, 50};
        double[] value = {60, 90, 100};
        double capacity = 80;
        */

        KnapsackProblem kp = new KnapsackProblem(n, weight, value, capacity);
        System.out.println(kp.getSolutionBranchBound());

        KnapsackProblem.test();
    }
}
