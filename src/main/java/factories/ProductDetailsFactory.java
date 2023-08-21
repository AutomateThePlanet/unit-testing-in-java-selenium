package factories;

import models.ProductDetails;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsFactory {
    public static List<ProductDetails> createDefault() {
        List<ProductDetails> result = new ArrayList<>();
        var expectedProduct1 = new ProductDetails();
        expectedProduct1.setName("iPod Touch");
        expectedProduct1.setId(32);
        expectedProduct1.setPrice("$194.00");
        expectedProduct1.setModel("Product 5");
        expectedProduct1.setBrand("Apple");
        expectedProduct1.setWeight("5.00kg");
        result.add(expectedProduct1);

        var expectedProduct2 = new ProductDetails();
        expectedProduct2.setName("iPod Shuffle");
        expectedProduct2.setId(34);
        expectedProduct2.setPrice("$182.00");
        expectedProduct2.setModel("Product 7");
        expectedProduct2.setBrand("Apple");
        expectedProduct2.setWeight("5.00kg");
        result.add(expectedProduct2);

        var expectedProduct3 = new ProductDetails();
        expectedProduct3.setName("iPod Nano");
        expectedProduct3.setId(36);
        expectedProduct3.setPrice("$122.00");
        expectedProduct3.setModel("Product 9");
        expectedProduct3.setBrand("Apple");
        expectedProduct3.setWeight("5.00kg");
        result.add(expectedProduct3);

        return result;
    }
}
