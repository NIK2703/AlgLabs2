import java.util.ArrayList;

public class KnapsackProblemSolution {
    Double value;
    ArrayList<Integer> items;

    public KnapsackProblemSolution(Double value, ArrayList<Integer> items) {
        this.value = value;
        this.items = items;
    }

    public Double getValue() {
        return value;
    }

    public ArrayList<Integer> getItems() {
        return items;
    }

    public String toString() {
        return value + " - " + items;
    }
}
