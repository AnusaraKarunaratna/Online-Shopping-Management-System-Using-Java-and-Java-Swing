//Class to represent clothing products and their details
public class Clothing extends Product{


    //Encapsulated variables to store specific clothing product details
    private String cloth_Size;
    private String cloth_Colour;


    //Constructor to initialize a clothing product with details
    public Clothing(String item_ID,String item_Name,int item_Quantity, double item_price,String cloth_Size,String cloth_Colour){
        super(item_ID, item_Name, item_Quantity, item_price);
        this.cloth_Size = cloth_Size;
        this.cloth_Colour = cloth_Colour;
    }


    //Setter to set cloth size
    public void set_Size(String cloth_Size) {
        this.cloth_Size = cloth_Size;
    }


    //Getter to get cloth size
    public String get_Size() {
        return cloth_Size;
    }


    //Setter to set cloth colour
    public void set_Colour(String cloth_Colour) {
        this.cloth_Colour = cloth_Colour;
    }


    //Getter to get cloth colour
    public String get_Colour() {
        return cloth_Colour;
    }


    //Override method from the superclass to get product type as clothing
    @Override
    public String get_Type() {
        return "Clothing";
    }


    //Override method to get string representation of the object details
    @Override
    public String toString() {
        return "\nProduct ID : "+ get_Product_Id()+
                "\nProduct Name : "+ get_Product_Name()+
                "\nAvailable Quantity : "+ get_Available_Items()+
                "\nPrice : £"+ get_Price()+
                "\nSize : "+ get_Size()+
                "\nColour : "+ get_Colour();
    }


    //Method to get additional specific details related to electronic products
    public void additionalInfo() {
        System.out.println("Type of product : cloth");
        System.out.println("Size of cloth : "+get_Size());
        System.out.println("Colour of cloth : "+get_Colour());
    }

}
