import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Order;


import static org.junit.jupiter.api.Assertions.*;

//Class to do Junit testing
class WestminsterShoppingManagerTest {

    private static final String fileName = "ProductsData.txt";

    @Order(1)
    @Test
        //Test case to check add product method
    void addProducts() {
        System.out.println("_________________________________Test case 01___________________________________\n");
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        //To create new product to check status
        Product product = new Electronics("w1", "Bulb", 10, 1, "Orange", 1);
        shoppingManager.addProducts(product);
        //To check if the expected result matches the actual result
        assertEquals(1, shoppingManager.getSelectedProducts().size());
        System.out.println("_______________________________________________________________________________\n");
    }

    @Order(2)
    @Test
        //Test case to check remove product method
    void removeProducts() {
        System.out.println("_________________________________Test case 02___________________________________\n");
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Product product = new Clothing("w2", "TShirt", 10, 4.10, "S", "Red");
        shoppingManager.addProducts(product);

        //To delete added product from the system
        shoppingManager.removeProducts("w2");

        //To check if the expected result matches the actual result
        assertEquals(0, shoppingManager.getSelectedProducts().size());
        System.out.println("_______________________________________________________________________________\n");
    }

    @Order(3)
    @Test
        //To check save to file option
    void saveToAFle() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        System.out.println("_________________________________Test case 03___________________________________\n");
        //To add some products to the shopping manager
        Product clothingProduct = new Clothing("w11", "Shirt", 10, 8.0, "M", "Blue");
        Product electronicsProduct = new Electronics("W12", "Camera", 5, 320.00, "Nikon", 12);

        shoppingManager.addProducts(clothingProduct);
        shoppingManager.addProducts(electronicsProduct);

        //To call save to file method
        shoppingManager.saveToAFle();

        try {
            List<String> lines = Files.readAllLines(Path.of("Products Data"));
            //To check if there is any line in data
            assertTrue(lines.size() > 0);

            //To check product details according to type
            assertTrue(lines.contains("Type: Clothing"));
            assertTrue(lines.contains("Product ID: w11"));
            assertTrue(lines.contains("Product name: Shirt"));
            assertTrue(lines.contains("Number of available Items: 10"));
            assertTrue(lines.contains("Price: 8.0"));
            assertTrue(lines.contains("Size: M"));
            assertTrue(lines.contains("Colour: Blue"));
            assertTrue(lines.contains("_______________________________________________________"));
            assertTrue(lines.contains("Type: Electronics"));
            assertTrue(lines.contains("Product ID: W12"));
            assertTrue(lines.contains("Product name: Camera"));
            assertTrue(lines.contains("Number of available Items: 5"));
            assertTrue(lines.contains("Price: 320.0"));
            assertTrue(lines.contains("Brand: Nikon"));
            assertTrue(lines.contains("Warranty: 12"));
            assertTrue(lines.contains("_______________________________________________________"));


            //To add exception handling
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }


    @Order(4)
    @Test
    //Test case to check load back from file method
    void loadFile() {
        System.out.println("_________________________________Test case 04___________________________________\n");
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        //To save a product to the file
        Product product = new Clothing("w4", "Shoes", 10, 7.00, "M", "White");
        shoppingManager.addProducts(product);
        shoppingManager.saveToAFle();

        //To get load back file data
        WestminsterShoppingManager newShoppingManager = new WestminsterShoppingManager();
        newShoppingManager.loadFile();

        //To check if the expected result matches the actual result
        assertEquals(shoppingManager.getSelectedProducts().size(), newShoppingManager.getSelectedProducts().size());
        System.out.println("_______________________________________________________________________________\n");
    }


    @Test
    //To Test GUI
    void testOpenGUI() {

        //To create new product
        Product testProduct = new Electronics("w19", "Mobile phone", 3, 100.00, "Apple", 10);

        // To create an instance of WestminsterShoppingManager
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.getSelectedProducts().add(testProduct);

        //To call the GUI
        shoppingManager.openGUI();
    }
}



