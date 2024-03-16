import java.util.Scanner;


public class ViewClass {
    public static void main(String[] args) {


        //Scanner to get user inputs
        Scanner scanner = new Scanner(System.in);
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();


        while (true) {

            shoppingManager.printMenu();  //Method to print menu items

            //To get and store user option input
            System.out.print("Enter the option you want : ");
            try {
                int option = scanner.nextInt();

                //Methods to work on user options
                if (option == 1) {
                    addProductToSystem(scanner, shoppingManager);

                } else if (option == 2) {
                    DeleteProduct(scanner, shoppingManager);

                } else if (option == 3) {
                    shoppingManager.printData();

                } else if (option == 4) {
                    shoppingManager.saveToAFle();

                } else if (option == 5) {
                    shoppingManager.loadFile();
                } else if (option == 6) {
                    shoppingManager.openGUI();
                } else if (option == 7) {
                    System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Invalid input type. Please enter an integer type input according to the menu.");
                scanner.next();
            }
        }
    }





    //Method to get inputs to add a product to the system
    public static void addProductToSystem(Scanner scanner,WestminsterShoppingManager shoppingManager1){

        try{
        //To get and store product details
        System.out.print("\nEnter product ID :");
        String item_ID = scanner.next();
            if (checkStringAlphabeticalValue(item_ID)) {
                System.out.println("The input for product ID has to have at least one alphabetical value.");
                return;
            }

        System.out.print("Enter product name : ");
        String item_Name = scanner.next();
            if (checkStringAlphabeticalValue(item_Name)) {
                System.out.println("The input for product name has to have at least one alphabetical value.");
                return;
            }

        System.out.print("Enter no of products available : ");
        int item_Quantity = scanner.nextInt();

        System.out.print("Enter price of product : £");
        double item_price = scanner.nextDouble();

        System.out.print("\nEnter the product type - If electronic item enter 01 , If clothing item enter 02 :");
        int type = scanner.nextInt();

        //If user selects product type as electronics, to get and store details specific to electronic products
        if (type == 1) {

            System.out.print("Enter brand of products : ");
            String brand_Of_Item = scanner.next();
            if (checkStringAlphabeticalValue(brand_Of_Item)) {
                System.out.println("The input for brand of product has to have at least one alphabetical value.");
                return;
            }

            System.out.print("Enter warranty period in months : ");
            int warranty_Period = scanner.nextInt();

            //To create an instance of electronics and pass it to the WestminsterShoppingManager class
            shoppingManager1.addProducts(new Electronics(item_ID,item_Name,item_Quantity,item_price,brand_Of_Item,warranty_Period));

        }

        //If user selects product type as clothing, to get and store details specific to clothing products
        else if (type == 2) {

            System.out.print("Enter size of cloth : ");
            String size_Of_Cloth = scanner.next();
            if (checkStringAlphabeticalValue(size_Of_Cloth)) {
                System.out.println("The input for size has to be alphabetical input.");
                return;
            }

            System.out.print("Enter colour of cloth : ");
            String colour_Of_Cloth = scanner.next();
            if (checkStringAlphabeticalValue(colour_Of_Cloth)) {
                System.out.println("The input for colour has to be alphabetical input.");
                return;
            }

            //To create an instance of electronics and pass it to the WestminsterShoppingManager class
            shoppingManager1.addProducts(new Clothing(item_ID,item_Name,item_Quantity,item_price,size_Of_Cloth,colour_Of_Cloth));

        }

        else {
            //If the user entered an invalid product type, to print an error message
            System.out.print("Entered product type is invalid. Please enter 01 for electric items and enter 02 for clothing items.");
        }}catch (Exception e){
            System.out.println("Invalid input.Please enter a valid input.");
            scanner.nextLine();
        }

    }

    //Method to get inputs to remove a product from the system
    public static void DeleteProduct(Scanner scanner,WestminsterShoppingManager Object1){

        System.out.print("Enter the ID of the product you want to delete : ");
        String product_To_Delete = scanner.next();

        Object1.removeProducts(product_To_Delete);

    }

    //To check the entered input has an alphabetical character or no
    public static boolean checkStringAlphabeticalValue(String input) {
        //To check each character
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            //To check whether c is a digit
            if (Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

}

