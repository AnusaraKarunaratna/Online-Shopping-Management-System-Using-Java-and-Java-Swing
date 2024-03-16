import java.util.ArrayList;
import java.util.List;

//Class to represent each customer's shopping cart
public class ShoppingCart {

    //List to store cart items
    private List<CartItem> cartItems;   //To store cart items
    private List<String> categories;    //To store categories of the product items
    private List<Integer> categoryQuantities;  //To store quantities of products in each category

    //Constructor to initialize a product to the shopping cart class
    public ShoppingCart() {

        this.cartItems = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.categoryQuantities = new ArrayList<>();
    }


    //Method to add an item to the shopping cart
    public void addProduct(Product selectedProduct) {

        //to check if the item is already in the shopping cart to update the item quantity
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(selectedProduct)) {
                cartItem.updateQuantity();
                updateCategoryCount(selectedProduct.get_Type(), 1);
                return;
            }
        }

        //If not, to add the product to the shopping cart as a new item
        cartItems.add(new CartItem(selectedProduct));
        updateCategoryCount(selectedProduct.get_Type(), 1);
    }


    //To update the number of items in a category
    public void updateCategoryCount(String category, int quantity) {
        int index = findCategoryIndex(category);
        if (index == -1) {
            categories.add(category);
            categoryQuantities.add(quantity);
        } else {
            int updatedQuantity = categoryQuantities.get(index) + quantity;
            categoryQuantities.set(index, updatedQuantity);
        }
    }


    //To calculate the total cost of the item by removing the discount values
    public double calculateTotalCostWithDiscount() {
        double totalCost = calculateTotalCost();
        double discount = 0.0;

        //Variable to store total quantity of items

        for (ShoppingCart.CartItem cartItem : cartItems) {
            String category = cartItem.getProduct().get_Type();
            int categoryQuantity = findCategoryQuantity(category);

            //To update the total quantity
            if (categoryQuantity >= 3) {
                //To give 20% of discount if there are at least three products in the same category
                discount += cartItem.getTotalPrice() * 0.20;

            }

        }


        //To return the final price by cutting down the discount value
        return totalCost - discount;
    }


    //To get the corresponding product type number by their input
    private int findCategoryIndex(String category) {
        return categories.indexOf(category);
    }


    //To get the quantity of items in the category of the corresponding product
    public int findCategoryQuantity(String category) {
        int categoryQuantity = 0;

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().get_Type().equals(category)) {
                categoryQuantity += cartItem.getQuantity();
            }
        }

        return categoryQuantity;
    }


    //To get the total cost of the products in the shopping cart
    public double calculateTotalCost() {
        double sum = 0;

        for (CartItem cartItem : cartItems) {
            sum += cartItem.getTotalPrice();
        }

        return sum;
    }


    //To get the items in the cart
    public List<CartItem> getCartItems() {
        return cartItems;
    }


    //To get the specified cart item for the selected product
    public CartItem getCartItem(Product selectedProduct) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(selectedProduct)) {
                return cartItem;
            }
        }
        return null;
    }


    //Class to represent shopping cart items
    public static class CartItem {


        //Encapsulated variables to store cart product item details
        private Product product;
        private int quantity;


        //Constructor to initialize a cart item with details
        public CartItem(Product product) {
            this.product = product;
            this.quantity = 1;
        }

        //Getter to get item
        public Product getProduct() {
            return product;
        }


        //Getter to get item quantity
        public int getQuantity() {
            return quantity;
        }


        //To update item count in a cart item
        public void updateQuantity() {
            quantity++;
        }


        //Getter to get total price item in the cart
        public double getTotalPrice() {
            return product.get_Price() * quantity;
        }
    }


}
