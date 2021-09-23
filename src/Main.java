import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
       /* int n = 4;
        double[] weight = {4, 7, 5, 3};
        double[] value = {40, 42, 25, 12};
        double capacity = 10;*/
        /*int n = 5;
        double[] weight = {3, 4, 5, 8, 9};
        double[] value = {1, 6, 4, 7, 6};
        double capacity = 13;*/
        /*int n = 3;
        double[] weight = {15, 30, 50};
        double[] value = {60, 90, 100};
        double capacity = 80;
        */
        /* KnapsackProblem kp = new KnapsackProblem(n, weight, value, capacity);
        System.out.println(kp.getSolutionBranchBound());*/
        //KnapsackProblem.test();

        //Принадлежности и требуемое их количество
        Map<String, Integer> wishList = new HashMap<>();
        wishList.put("Тетрадь", 5);
        wishList.put("Ручка", 3);
        wishList.put("Карандаш", 4);
        wishList.put("Циркуль", 1);

        //Прейкурант цен
        List<PriceListItem> priceListItems = new ArrayList<>();
        priceListItems.add(new PriceListItem("Тетрадь", 50F));
        priceListItems.add(new PriceListItem("Ручка", 20F));
        priceListItems.add(new PriceListItem("Карандаш", 15F));
        priceListItems.add(new PriceListItem("Тетрадь", 30F));
        priceListItems.add(new PriceListItem("Ручка", 10F));
        priceListItems.add(new PriceListItem("Тетрадь", 30F));
        priceListItems.add(new PriceListItem("Карандаш", 20F));
        priceListItems.add(new PriceListItem("Тетрадь", 40F));
        priceListItems.add(new PriceListItem("Циркуль", 100F));

        ShoppingListGenerator slg = new ShoppingListGenerator();
        Map<String, Integer> shoppingList = slg.getMaxShoppingList(wishList, priceListItems, 150F);
        System.out.println(shoppingList);

    }
}
