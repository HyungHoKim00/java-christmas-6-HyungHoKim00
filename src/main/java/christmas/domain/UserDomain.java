package christmas.domain;

import christmas.enums.Menu;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDomain {
    private static final List<Integer> WEEKEND = List.of(1,2,8,9,15,16,22,23,29,30);
    private static final int CHRISTMAS_DATE = 25;
    private static final int NO_SALE = 0;
    private static final int D_DAY_BASIC_SALE = 900;
    private static final int D_DAY_BONUS_SALE = 100;

    public static int calculateTotalOrderPrice(Map<Menu,Integer> order){
        AtomicInteger totalOrderPrice = new AtomicInteger();
        order.keySet()
                .forEach(key -> totalOrderPrice.addAndGet(key.price() * order.get(key)));
        return totalOrderPrice.get();
    }

    public static int getDDaySale(int date){
        if(date<=CHRISTMAS_DATE){
            return D_DAY_BASIC_SALE+date*D_DAY_BONUS_SALE;
        }
        return NO_SALE;
    }

    public static int getWeekDaySale(int date, Map<Menu,Integer> order){
        if(!WEEKEND.contains(date)){
            AtomicInteger saleAmount = new AtomicInteger();
            order.keySet().stream()
                    .filter(key -> Objects.equals(key.type(), "디저트"))
                    .forEach(key -> saleAmount.addAndGet(2023));
            return saleAmount.get();
        }
        return NO_SALE;
    }

    public static int getWeekendSale(int date, Map<Menu,Integer> order){
        if(WEEKEND.contains(date)){
            AtomicInteger saleAmount = new AtomicInteger();
            order.keySet().stream()
                    .filter(key -> Objects.equals(key.type(), "메인"))
                    .forEach(key -> saleAmount.addAndGet(2023));
            return saleAmount.get();
        }
        return NO_SALE;
    }

}