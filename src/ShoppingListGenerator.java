import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingListGenerator {
    Map<String, Integer> getMaxShoppingList(Map<String, Integer> wishList,
                                            List<PriceListItem> priceList, Float budget) {

        List<PriceListItem> sortedPriceList = priceList.stream()
                .filter(e -> wishList.containsKey(e.name))
                .sorted((a, b) -> {
                    return a.cost.compareTo(b.cost);
                })
                .collect(Collectors.toList());

        Map<String, Integer> shopList = new HashMap<>();
        for(PriceListItem e : sortedPriceList) {
            if(wishList.get(e.name) > 0 && e.cost <= budget) {
                wishList.put(e.name, wishList.get(e.name) - 1);
                budget -= e.cost;
                shopList.put(e.name, shopList.getOrDefault(e.name, 0) + 1);
            };
            if (e.cost > budget) {
                break;
            }
        }

        return shopList;
    }
}
