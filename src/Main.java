public class Main {
    public static void main(String[] args) {
        /*int n = 4;
        double[] weight = {4, 7, 5, 3};
        double[] value = {40, 42, 25, 12};*/

        int n = 5;
        double[] weight = {3, 4, 5, 8, 9};
        double[] value = {1, 6, 4, 7, 6};

        KnapsackProblem kp = new KnapsackProblem(n, weight, value, 13);

        System.out.println(kp.getSolution());
    }
}
