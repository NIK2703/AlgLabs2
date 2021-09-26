import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingListGenerator {
    Map<String, Integer> getMaxShoppingList(Map<String, Integer> wishList,
                                            List<PriceListItem> priceList, Float budget) {

        List<PriceListItem> sortedPriceList = priceList.stream()
                .filter(e -> wishList.containsKey(e.name)) // фильтрация прейскуранта в соответствии со списком желаний
                .sorted((a, b) -> {
                    return a.cost.compareTo(b.cost); // сортировка по возрастанию сторимости
                })
                .collect(Collectors.toList());

        Map<String, Integer> shopList = new HashMap<>();
        List<PriceListItem> skippedPriceList = new ArrayList<>();

        for (PriceListItem e : sortedPriceList) { // составление максимального ассортимента
            if (shopList.getOrDefault(e.name, 0) == 0) {
                if (e.cost <= budget) {
                    wishList.put(e.name, wishList.get(e.name) - 1);
                    budget -= e.cost;
                    shopList.put(e.name, 1);
                } else {
                    break;
                }
            }
            else {
                skippedPriceList.add(e);
            };
        }

        for (PriceListItem e : skippedPriceList) { // докупка предметов из собранного ассортимента
            if (wishList.get(e.name) > 0) {
                if (e.cost <= budget) {
                    wishList.put(e.name, wishList.get(e.name) - 1);
                    budget -= e.cost;
                    shopList.put(e.name, shopList.get(e.name) + 1);
                } else {
                    break;
                }
            };
        }

        return shopList;
    }
}
