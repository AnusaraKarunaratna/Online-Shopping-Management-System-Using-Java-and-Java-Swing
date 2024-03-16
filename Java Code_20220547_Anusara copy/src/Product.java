// Abstract class to represent all types of product items
public abstract class Product {

    //Encapsulated variables to store product details
    private String item_ID;
    private String item_Name;
    private int item_Quantity;
    private double item_price;


    //Constructor to initialize a product with details
    public Product(String item_ID, String item_Name, int item_Quantity, double item_price) {
        this.item_ID = item_ID;
        this.item_Name = item_Name;
        this.item_Quantity = item_Quantity;
        this.item_price = item_price;
    }


    //Setter to set product id
    public void set_item_Id(String item_ID) {
        this.item_ID = item_ID;
    }


    //Getter to get product id
    public String get_Product_Id() {
        return item_ID;
    }


    //Setter to set product name
    public void set_Product_Name(String item_Name) {
        this.item_Name = item_Name;
    }


    //Getter to get product name
    public String get_Product_Name() {
        return item_Name;
    }


    //Setter to set product quantity
    public void set_Available_Items(int item_Quantity) {
        this.item_Quantity = item_Quantity;
    }


    //Getter to get product quantity
    public int get_Available_Items() {
        return item_Quantity;
    }


    //Setter to get product price
    public void set_Price(double item_price) {
        this.item_price = item_price;
    }


    //Getter to get product price
    public double get_Price() {
        return item_price;
    }


    // Abstract method to use in subclasses to get specific extra details
    public abstract void additionalInfo();


    // Abstract method to use in subclasses to get type of product
    public abstract String get_Type();


    // Override method to get string representation of the object details
    @Override
    public String toString() {
        return "\nProduct ID : " + get_Product_Id() +
                "\nProduct Name : " + get_Product_Name() +
                "\nAvailable Quantity : " + get_Available_Items() +
                "\nPrice : £" + get_Price();
    }
}
