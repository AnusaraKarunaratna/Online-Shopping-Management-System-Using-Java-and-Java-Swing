import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


//To manage console menu options
public class WestminsterShoppingManager implements ShoppingManager {

    //List to store product data that stored in the system
    private List<Product> selectedProducts;

    //To set maximum limit to the system
    private static final int maximumLimit = 50;

    //Constructor to initialize objects
    public WestminsterShoppingManager(){
        this.selectedProducts = new ArrayList<>();
    }


    //Method to print console menu
    public void printMenu(){
        System.out.println("\n______Westminster Shopping Menu______\n");
        System.out.println("1.Add a new product");
        System.out.println("2.Delete a product");
        System.out.println("3.Print the list of the products");
        System.out.println("4.Save in a file");
        System.out.println("5.Read back from file");
        System.out.println("6.Open GUI");
        System.out.println("7.Exit\n");
    }

    //Method to manage process of adding product to the system
    @Override
    public void addProducts(Product selectedProduct) {

        //If the product limit is within 50 to add products to the system
        if (selectedProducts.size() < maximumLimit){
            selectedProducts.add(selectedProduct);
            System.out.println("Product added to the system successfully!");
        }

        //Otherwise to print an error message
        else {
            System.out.println( "System can have a maximum of 50 products.");
        }

    }


    //Method to remove products from the system
    @Override
    public void removeProducts(String productID) {
        int loopingIndex = 0;

        while(loopingIndex<selectedProducts.size()) {

            Product selectedProduct = selectedProducts.get(loopingIndex);

            //If the product successfully deleted from the system, to print a message and update table
            if(selectedProduct.get_Product_Id().equals(productID)){
                selectedProducts.remove(loopingIndex);
                System.out.println("\n______Deleted product details______ \n\nProduct ID : "+selectedProduct.get_Product_Id()+"\nProduct Name : "
                        +selectedProduct.get_Product_Name()+"\nProduct Price : "
                        +selectedProduct.get_Price());
                selectedProduct.additionalInfo();
                System.out.println("\nTotal number of products left in the system : " +selectedProducts.size()+"\n");
                return;

            }
        }System.out.println("No product found with product ID "+productID); //To print a message if product is not found.
    }


    //To save details to a file
    @Override
    public void saveToAFle() {

        //To make a file to store data
        try {
            FileWriter productsData = new FileWriter("Products Data");

            //To sort products according to product id
            selectedProducts.sort(Comparator.comparing(Product::get_Product_Id));

            if (selectedProducts.size() >= 1) {
                productsData.write("______________Product Details_____________"+"\n");

                //To store product details in the file according to product type
                for (int count = 0; count < selectedProducts.size(); count++) {
                    Product product = selectedProducts.get(count);

                    if (product.get_Type().equals("Clothing")) {
                        Clothing clothing = (Clothing) product;
                        productsData.write("Type: Clothing\n");
                        productsData.write("Product ID: " + clothing.get_Product_Id() + "\n");
                        productsData.write("Product name: " + clothing.get_Product_Name() + "\n");
                        productsData.write("Number of available Items: " + clothing.get_Available_Items() + "\n");
                        productsData.write("Price: " + clothing.get_Price() + "\n");
                        productsData.write("Size: " + clothing.get_Size() + "\n");
                        productsData.write("Colour: " + clothing.get_Colour() + "\n");
                    } else if (product.get_Type().equals("Electronics")) {
                        Electronics electronics = (Electronics) product;
                        productsData.write("Type: Electronics\n");
                        productsData.write("Product ID: " + electronics.get_Product_Id() + "\n");
                        productsData.write("Product name: " + electronics.get_Product_Name() + "\n");
                        productsData.write("Number of available Items: " + electronics.get_Available_Items() + "\n");
                        productsData.write("Price: " + electronics.get_Price() + "\n");
                        productsData.write("Brand: " + electronics.get_Brand_Of_Product() + "\n");
                        productsData.write("Warranty: " + electronics.getWarranty_Period() + "\n");
                    }
                    productsData.write("_______________________________________________________\n");
                }

            //If there are no products at the cart to print a message
            } else {
                productsData.write("There are no products at the cart");
            }
            productsData.close();
            System.out.println("Successfully update the file.");

        //To avoid errors
        } catch (IOException e) {
            System.out.println("There is an issue.");
        }
    }


    //To load data from file to lists
    @Override
    public void loadFile() {

        //To get file data
        try (BufferedReader reader = new BufferedReader(new FileReader("Products Data"))) {
            String line;
            Product currentProduct = null;

            //To read file data
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("_______________________________________________________\n")) {
                    if (currentProduct != null && !isProductInList(currentProduct)) {
                        selectedProducts.add(currentProduct);
                    }
                    currentProduct = null;
                    continue;
                }

                //To separate data using :
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String attributeName = parts[0].trim();
                    String attributeValue = parts[1].trim();

                    //To create instance based on the type when current product is null
                    if (currentProduct == null) {
                        switch (attributeName) {
                            case "Type":
                                if ("Electronics".equals(attributeValue)) {
                                    currentProduct = new Electronics(null, null, 0, 0.0, null, 0);
                                } else if ("Clothing".equals(attributeValue)) {
                                    currentProduct = new Clothing(null, null, 0, 0.0, null, null);
                                }
                                break;
                        }
                    } else {

                        //Switch to load data from file to list
                        switch (attributeName) {
                            case "Product ID":
                                currentProduct.set_item_Id(attributeValue);
                                break;
                            case "Product name":
                                currentProduct.set_Product_Name(attributeValue);
                                break;
                            case "Number of available Items":
                                currentProduct.set_Available_Items(Integer.parseInt(attributeValue));
                                break;
                            case "Price":
                                currentProduct.set_Price(Double.parseDouble(attributeValue));
                                break;
                            case "Brand":
                                if (currentProduct instanceof Electronics) {
                                    ((Electronics) currentProduct).set_Brand_Of_Product(attributeValue);
                                }
                                break;
                            case "Warranty":
                                if (currentProduct instanceof Electronics) {
                                    int warrantyPeriod = Integer.parseInt(attributeValue);
                                    ((Electronics) currentProduct).set_Warranty_Period(warrantyPeriod);
                                }
                                break;
                            case "Size":
                                if (currentProduct instanceof Clothing) {
                                    ((Clothing) currentProduct).set_Size(attributeValue);
                                }
                                break;
                            case "Colour":
                                if (currentProduct instanceof Clothing) {
                                    ((Clothing) currentProduct).set_Colour(attributeValue);
                                }
                                break;
                        }
                    }
                }
            }

            //To load back products to selected product arraylist
            if (currentProduct != null && !isProductInList(currentProduct)) {
                selectedProducts.add(currentProduct);
            }
            System.out.println("File loaded successfully.");

        //Exception handling to manage errors
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the txt file: " + e.getMessage());
        }
    }

    //To check the availability of product in the list
    private boolean isProductInList(Product product) {
        for (Product existingProduct : selectedProducts) {
            if (existingProduct.get_Product_Id().equals(product.get_Product_Id())) {
                return true;
            }
        }
        return false;
    }

    //To print data in the program console
    @Override
    public void printData() {

        selectedProducts.sort(Comparator.comparing(Product::get_Product_Id));

        if (selectedProducts.size() >= 1 ) {

            System.out.println("\n______Products Data List______");

            for (int count = 0; count < selectedProducts.size(); count++) {
                Product product = selectedProducts.get(count);

                System.out.println("\nProduct ID : " + product.get_Product_Id() +
                        "\nProduct Name : " + product.get_Product_Name() +
                        "\nAvailable Quantity : " + product.get_Available_Items() +
                        "\nPrice : £" + product.get_Price());
                selectedProducts.get(count).additionalInfo();
                System.out.println("_______________________________________________________");

            }
        }else{

            System.out.println("\nThere are no products at the cart");
        }
    }

    //To open GUI
    public void openGUI(){
        new ShoppingGUI(selectedProducts);
    }

    //Getter to get product item list
    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

}


