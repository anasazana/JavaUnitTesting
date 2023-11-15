import com.epam.tamentoring.bo.Product;
import com.epam.tamentoring.bo.ShoppingCart;
import com.epam.tamentoring.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartTest {

    ShoppingCart testCart;

    @BeforeEach
    public void initCart() {
        testCart = new ShoppingCart(new ArrayList<>());
    }

    @Test
    public void singleProductCanBeAddedTest() {
        Product testProduct = createBallPen();
        testCart.addProductToCart(testProduct);
        Assertions.assertEquals(1, testCart.getProducts().size());
        Assertions.assertTrue(testCart.getProducts().contains(testProduct));
        Assertions.assertEquals(testProduct, testCart.getProductById(testProduct.getId()));
    }

    @Test
    public void moreItemsOfExistingCartProductCanBeAddedTest() {
        Product testProduct = createBallPen();
        testCart.addProductToCart(testProduct);
        testCart.addProductToCart(testProduct);
        Assertions.assertEquals(1, testCart.getProducts().size());
        Assertions.assertTrue(testCart.getProducts().contains(testProduct));
        testProduct.setQuantity(2);
        Assertions.assertEquals(testProduct, testCart.getProductById(testProduct.getId()));
    }

    @Test
    public void differentProductsCanBeAddedTest() {
        Product testProduct = createBallPen();
        Product testProduct2 = createNotebook();
        testCart.addProductToCart(testProduct);
        testCart.addProductToCart(testProduct2);
        List<Product> actualProducts = testCart.getProducts();
        Assertions.assertEquals(2, actualProducts.size());
        Assertions.assertTrue(actualProducts.contains(testProduct));
        Assertions.assertEquals(testProduct, testCart.getProductById(testProduct.getId()));
        Assertions.assertEquals(testProduct2, testCart.getProductById(testProduct2.getId()));
    }

    @Test
    public void oneOfProductsCanBeRemovedTest() {
        Product testProductToRemove = createBallPen();
        Product testProduct = createNotebook();
        testCart.addProductToCart(testProductToRemove);
        testCart.addProductToCart(testProduct);
        Assertions.assertEquals(2, testCart.getProducts().size());
        testCart.removeProductFromCart(testProductToRemove);
        Assertions.assertEquals(1, testCart.getProducts().size());
        Assertions.assertEquals(testProduct, testCart.getProductById(testProduct.getId()));
        Assertions.assertThrows(ProductNotFoundException.class, () ->
                testCart.getProductById(testProductToRemove.getId())
        );
    }

    @Test
    public void allProductsCanBeRemovedTest() {
        Product testProduct = createBallPen();
        testCart.addProductToCart(testProduct);
        Assertions.assertEquals(1, testCart.getProducts().size());
        testCart.removeProductFromCart(testProduct);
        Assertions.assertEquals(0, testCart.getProducts().size());
        Assertions.assertThrows(ProductNotFoundException.class, () -> testCart.getProductById(testProduct.getId()));
    }

    @Test
    public void getTotalPriceOfShoppingCartTest() {
        Product testProduct = createBallPen();
        Product testProduct2 = createNotebook();
        testCart.addProductToCart(testProduct);
        testCart.addProductToCart(testProduct2);
        double expectedTotalPrice = testProduct.getPrice() * testProduct.getQuantity()
                + testProduct2.getPrice() * testProduct2.getQuantity();
        Assertions.assertEquals(expectedTotalPrice, testCart.getCartTotalPrice());
    }

    @Test
    public void getEmptyCartTotalPriceTest() {
        Assertions.assertEquals(0, testCart.getCartTotalPrice());
    }

    @Test
    public void nullProductsListConstructor() {
        testCart = new ShoppingCart(null);
        Product testProduct = createBallPen();
        testCart.addProductToCart(testProduct);
        Assertions.assertEquals(1, testCart.getProducts().size());
        Assertions.assertTrue(testCart.getProducts().contains(testProduct));
        Assertions.assertEquals(testProduct, testCart.getProductById(testProduct.getId()));
    }

    private Product createBallPen() {
        return new Product(1, "Ball pen", 1.2, 1);
    }

    private Product createNotebook() {
        return new Product(2, "Notebook", 3.8, 2);
    }

}
