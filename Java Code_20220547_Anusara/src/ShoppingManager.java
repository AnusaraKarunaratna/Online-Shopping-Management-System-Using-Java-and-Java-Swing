public interface ShoppingManager {

    void addProducts(Product selectedProduct);
    void removeProducts(String productID);
    void saveToAFle();
    void loadFile();
    void printData();
}
