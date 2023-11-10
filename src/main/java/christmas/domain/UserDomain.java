package christmas.domain;

import christmas.enums.Menu;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDomain {

    public static int calculateTotalOrderPrice(Map<Menu,Integer> order){
        AtomicInteger totalOrderPrice = new AtomicInteger();
        order.keySet()
                .forEach(key -> totalOrderPrice.addAndGet(key.price() * order.get(key)));
        return totalOrderPrice.get();
    }

}