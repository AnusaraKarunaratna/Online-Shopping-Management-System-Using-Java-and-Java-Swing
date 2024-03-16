//Class to represent electronic products and their details
public class Electronics extends Product {

    //Encapsulated variables to store specific electronic product details
    private String item_Brand;
    private int warranty_Period;


    //Constructor to initialize an electronic product with details
    public Electronics(String item_ID,String item_Name,int item_Quantity, double item_price,String item_Brand,int warranty_Period) {
        super(item_ID, item_Name, item_Quantity, item_price);
        this.item_Brand = item_Brand;
        this.warranty_Period = warranty_Period;
    }

    //Setter to set electronic brand
    public void set_Brand_Of_Product(String item_Brand) {
        this.item_Brand = item_Brand;
    }


    //Getter to get electronic brand
    public String get_Brand_Of_Product() {
        return item_Brand;
    }


    //Setter to set warranty period of an electronic product
    public void set_Warranty_Period(int warranty_Period) {
        this.warranty_Period = warranty_Period;
    }


    //Getter to get warranty period of an electronic product
    public int getWarranty_Period() {
        return warranty_Period;
    }


    //Override method from the superclass to get product type as electronic
    @Override
    public String get_Type() {
        return "Electronics";
    }


    //Override method to get string representation of the object details
    @Override
    public String toString() {
        return "\nProduct ID : "+ get_Product_Id()+
                "\nProduct Name : "+ get_Product_Name()+
                "\nAvailable Quantity : "+ get_Available_Items()+
                "\nPrice : £"+ get_Price()+
                "\nBrand : "+ get_Brand_Of_Product()+
                "\nWarranty Period in months: "+ getWarranty_Period();
    }

    //Method to get additional specific details related to electronic products
    public void additionalInfo() {
        System.out.println("Type of product : Electronics");
        System.out.println("Brand of product : "+ get_Brand_Of_Product());
        System.out.println("Warranty period of product in months : "+getWarranty_Period());
    }


}
