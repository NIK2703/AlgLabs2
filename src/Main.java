public class Main {
    public static void main(String[] args) {
        int n = 4;
        double[] weight = {4, 7, 5, 3};
        double[] value = {40, 42, 25, 12};
        BackpackTask bt = new BackpackTask(n, weight, value, 10);
        System.out.println(bt.getSolution());
    }
}
