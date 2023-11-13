import com.epam.tamentoring.bo.DiscountUtility;
import com.epam.tamentoring.bo.OrderService;
import com.epam.tamentoring.bo.Product;
import com.epam.tamentoring.bo.ShoppingCart;
import com.epam.tamentoring.bo.UserAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    DiscountUtility discountUtility;
    @InjectMocks
    OrderService orderService;

    @Test
    public void getPriceWithDiscountTest() {
        UserAccount testUser = new UserAccount(
                "John",
                "Smith",
                "1990/10/10",
                new ShoppingCart(new ArrayList<>())
        );
        Product testProduct = new Product(1, "Coffee machine", 100.0, 1);
        double discount = 5.0;
        testUser.getShoppingCart().addProductToCart(testProduct);
        Mockito.when(discountUtility.calculateDiscount(testUser)).thenReturn(discount);
        Assertions.assertEquals(testProduct.getPrice() - discount, orderService.getOrderPrice(testUser));
        Mockito.verify(discountUtility, Mockito.only()).calculateDiscount(testUser);
    }

}
