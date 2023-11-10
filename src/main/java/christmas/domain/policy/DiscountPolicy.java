package christmas.domain.policy;

import christmas.domain.Category;
import java.time.LocalDate;

public interface DiscountPolicy {
    int discount(Category category, LocalDate date);
}
