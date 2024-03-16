import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.JTableHeader;



//Class to represent GUI components
public class ShoppingGUI {

    //Encapsulated variables to store GUI components
    private JFrame guiFrame;
    private JTable productDetailsTable;
    private  JTextArea detailsPanel;
    private ShoppingCart shoppingCart;
    private DefaultTableModel productDetailsTableModel;
    private ShoppingCartTableModel cartDetailsTableModel;


    //Constructor to initialize the GUI
    public ShoppingGUI(List<Product> productList) {

            //To create the main JFrame,layout manager and design dimensions
            guiFrame = new JFrame("Shopping GUI");
            guiFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            guiFrame.setSize(600, 700);
            guiFrame.setLayout(new BorderLayout());

            //To call shopping cart object to add shopping cart JFrame
            shoppingCart = new ShoppingCart();

            //To initialize product details table and cart product details table
            productDetailsTableModel = new DefaultTableModel(productList);
            cartDetailsTableModel = new ShoppingCartTableModel(shoppingCart.getCartItems());

            // To add a JPanel to the frame and add border layout to it
            JPanel topPanel = new JPanel(new BorderLayout());

            //Array to store the product types of the scrolling menu
            String[] itemCategories = {"All", "Electronics", "Clothing"};

            //To create a JComboBox to represent the menu scrollbar
            JComboBox<String> menuPanel = new JComboBox<>(itemCategories);

            //To add ActionListener to work according to user selection
            menuPanel.addActionListener(e -> {
                String selectedType = menuPanel.getSelectedItem().toString();
                filterProductsOnType(productList, selectedType);
            });

            productDetailsTable = new JTable(); //To create new JTable instance for product detail table

            productDetailsTable.setModel(productDetailsTableModel); //To set the table according to the product details table model


            //To set exact dimensions to the table(This will show the scrollbar when the table size exceeds the dimensions)
            productDetailsTable.setPreferredScrollableViewportSize(new Dimension(400, 200));

            productDetailsTable.setRowHeight(50); //To set a row height between rows


            //To add cell renderer to design table cells
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                    //To get default cell renderer component by calling supper class
                    Component selectedCell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    //To set cell colour and letter colour according to availability
                    if (row < productList.size()) {
                        Product product = productList.get(row);
                        if (product.get_Available_Items() < 3) {
                            selectedCell.setBackground(Color.RED);
                            selectedCell.setForeground(Color.WHITE);
                        } else {
                            selectedCell.setBackground(table.getBackground());
                            selectedCell.setForeground(table.getForeground());
                        }
                    }

                    setHorizontalAlignment(JLabel.CENTER);  //To get cell items to its center

                    return selectedCell;  //To return the customized cell renderer component
                }
            };

            //To design product details tables according to setDefaultRenderer method
            productDetailsTable.setDefaultRenderer(Object.class, cellRenderer);

            Border emptyBorder = new EmptyBorder(40, 5, 5, 5);  //To create empty border

            JScrollPane tableScrollPane = new JScrollPane(productDetailsTable); //To create scroll bar to product details table

            Border border = BorderFactory.createLineBorder(Color.white.brighter()); ///To create a bright light layer for clear view

            //To combine both borders to set the scrollbar border
            Border compoundBorder = BorderFactory.createCompoundBorder(border, emptyBorder);
            tableScrollPane.setBorder(compoundBorder);

            //To add selection lister to each row of the ProductDetails table
            productDetailsTable.getSelectionModel().addListSelectionListener(e -> updateSelectedProductDetails(productList));


            //To make the width of the "info" column larger than the other columns
            int infoColWidth = 200;
            TableColumn columnInfo = productDetailsTable.getColumnModel().getColumn(4);
            columnInfo.setPreferredWidth(infoColWidth);

            //To add "add to cart" button and "shopping cart" button
            JButton addToCartButton = new JButton("Add to Cart");
            JButton shoppingCartButton = new JButton("Shopping Cart");

            //To add event listeners to "add to cart" button and "shopping cart" button
            addToCartButton.addActionListener(e -> addToCart(productList));
            shoppingCartButton.addActionListener(e -> displayShoppingCart());

            //To add JPanel to display "shopping cart" button and scrolling menu with label
            JPanel menuAndCartPanel = new JPanel(new FlowLayout());
            menuAndCartPanel.add(new JLabel("Product Type: "));
            menuAndCartPanel.add(menuPanel);
            menuAndCartPanel.add(shoppingCartButton);

            //To add menu cart panel and table scroll pane to top panel
            topPanel.add(menuAndCartPanel, BorderLayout.NORTH);
            topPanel.add(tableScrollPane, BorderLayout.CENTER);

            JPanel panel2 = new JPanel(new BorderLayout()); //To add another JPanel to the JFrame

            detailsPanel = new JTextArea();  //To add text area to display product details

            detailsPanel.setEditable(false);  //To set the text panel to not allow editing

            //To add a border to text panel
            Border textAreaBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(20, 20, 0, 0),
                    detailsPanel.getBorder()
            );


            detailsPanel.setBorder(textAreaBorder);

            //To add the scroll pane to the detail panel and to center it
            panel2.add(new JScrollPane(detailsPanel), BorderLayout.CENTER);

            //To add the top panel to the north side and panel 2 to the middle of the GUI frame
            guiFrame.add(topPanel, BorderLayout.NORTH);
            guiFrame.add(panel2, BorderLayout.CENTER);

            //To create a panel to add place 'add to cart' button
            JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            //To add 'add to cart' button to the created panel
            cartButtonPanel.add(addToCartButton);

            //To set button size
            addToCartButton.setPreferredSize(new Dimension(100, 30));

            //To place the created button panel at the bottom of the GUI frame
            guiFrame.add(cartButtonPanel, BorderLayout.SOUTH);
            guiFrame.setVisible(true);

            // Customize header row color for both tables
            setTableHeaderColor(productDetailsTable, Color.lightGray);
    }



    // To manage the process of adding products to the product cart
    private void addToCart(List<Product> productList) {
        // To get the product row details selected by the customer
        int selectedRow = productDetailsTable.getSelectedRow();

        // To check if the selected row is valid or not and if valid, to get specific details for that row
        if (selectedRow >= 0) {
            Product selectedProduct = productList.get(selectedRow);

            // To get selected item details from 'shopping cart class' to GUI
            ShoppingCart.CartItem selectedItem = shoppingCart.getCartItem(selectedProduct);

        /* To check if the selected item is already in the cart, if it's already in the cart,
        increase the quantity, otherwise add as a new product */
            if (selectedProduct.get_Available_Items() > 0) {
                if (selectedItem != null) {
                    selectedItem.updateQuantity(); // Decrease the quantity by 1
                    shoppingCart.updateCategoryCount(selectedProduct.get_Type(), 1); // Decrease the category count by 1
                } else {
                    shoppingCart.addProduct(selectedProduct);
                    shoppingCart.updateCategoryCount(selectedProduct.get_Type(), 1);
                }

                // To minimize the number of products available in the products added to the cart
                selectedProduct.set_Available_Items(selectedProduct.get_Available_Items() - 1);
                updateSelectedProductDetails(productList);

                //To update the product details table model to reflect changes
                productDetailsTableModel.fireTableRowsUpdated(selectedRow, selectedRow);

                // To display the respective product added to the cart in the products table of the shopping cart frame
                List<ShoppingCart.CartItem> updatedCartItems = shoppingCart.getCartItems();

                //To real time update
                cartDetailsTableModel.updateDataForCart(updatedCartItems);


                // To display a message about the success of the product collection process
                JOptionPane.showMessageDialog(guiFrame, "Product added to cart!");
            } else {
                JOptionPane.showMessageDialog(guiFrame, "Sorry, this product is sold out.");
            }
        } else {
            // To display a message if the user did not select any product
            JOptionPane.showMessageDialog(guiFrame, "Please select a product.");
        }
    }




    //To filter products according to the category selected in the scroll menu
    private void filterProductsOnType(List<Product> productList, String productType) {


        //To print all available products if user doesn't select any category
        if ("All".equals(productType)) {
            productDetailsTableModel.updateData(productList);
        } else {

            //If user selects a category, to show only that category products
            List<Product> filteredProducts = new ArrayList<>();
            for (Product product : productList) {
                if (productType.equals(product.get_Type())) {
                    filteredProducts.add(product);
                }
            }

            //To update the product details table according to the selected category products
            productDetailsTableModel.updateData(filteredProducts);
        }
    }


    //To display the shopping cart details in a new JFrame
    private void displayShoppingCart() {

        //To create a separate JFrame and set its dimensions
        JFrame cartFrame = new JFrame("Shopping Cart");
        cartFrame.setSize(500, 400);
        cartFrame.setLayout(new BorderLayout());

        //To create new table model to display cart item details
        ShoppingCartTableModel cartTableModel = new ShoppingCartTableModel(shoppingCart.getCartItems());

        //To create a new table and scroll pane
        JTable cartProductTable = new JTable(cartTableModel);
        JScrollPane cartTableScrollPane = new JScrollPane(cartProductTable);

        // To adjust borders of cart scroll pane
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        cartTableScrollPane.setBorder(BorderFactory.createCompoundBorder(cartTableScrollPane.getBorder(), emptyBorder));
        cartTableScrollPane.setViewportBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //To center added scroll pane
        cartFrame.add(cartTableScrollPane, BorderLayout.CENTER);

        //To create a new JPanel and add a JTextArea to that panel
        JPanel additionalInfoPanel = new JPanel(new BorderLayout());
        JTextArea additionalInfoArea = new JTextArea();
        additionalInfoArea.setEditable(false);

        //To set the dimensions to the created text area
        additionalInfoArea.setPreferredSize(new Dimension(400, 200));

        //To add top and left borders to text area details
        Border textAreaBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK), // Top and Left Borders
                BorderFactory.createEmptyBorder(40, 100, 0, 0)
        );
        additionalInfoArea.setBorder(textAreaBorder);


        //To add JScrollPane with additionalInfoArea to additionalInfoPanel
        additionalInfoPanel.add(new JScrollPane(additionalInfoArea), BorderLayout.CENTER);

        //Call methods to display price details and to add labels
        JLabel totalCostLabel = new JLabel("Total Cost              :   $" + shoppingCart.calculateTotalCost());
        JLabel discountLabel = new JLabel("Total Cost with Discount:   $" + shoppingCart.calculateTotalCostWithDiscount());

        //to place labels correctly
        cartFrame.add(totalCostLabel, BorderLayout.SOUTH);
        cartFrame.add(discountLabel, BorderLayout.SOUTH);
        cartFrame.add(additionalInfoPanel, BorderLayout.SOUTH);

        // To update additional information in the JTextArea
        updateAdditionalInfo(additionalInfoArea);
        cartFrame.setVisible(true);
    }


    //To manage additional details displayed in JTextArea
    private void updateAdditionalInfo(JTextArea additionalInfoArea) {

        //Variables to store additional price details
        double totalCostWithoutDiscount = shoppingCart.calculateTotalCost();
        double discountValue = totalCostWithoutDiscount - shoppingCart.calculateTotalCostWithDiscount();
        double priceAfterDiscount = shoppingCart.calculateTotalCostWithDiscount();

        String additionalInfo = "Total                                                                         : $" + totalCostWithoutDiscount + "\n" +
                "First purchase discount (10%)                                   : $" + 0.0 + "\n" +
                "Three items in same category discount(20%)            : $" + discountValue + "\n" +
                "Final total                                                                  : $" + priceAfterDiscount;

        //To display details in JTextArea
        additionalInfoArea.setText(additionalInfo);
    }

    //To update the product details area according to the user selected product table row
    private void updateSelectedProductDetails(List<Product> productList) {
        int selectedRow = productDetailsTable.getSelectedRow();
        if (selectedRow >= 0) {
            Product selectedProduct = productList.get(selectedRow);
            detailsPanel.setText("Product Details:\n" + selectedProduct.toString());
        } else {
            detailsPanel.setText("");
        }
    }

    // Method to set header row color for a JTable
    private void setTableHeaderColor(JTable table, Color color) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(color);
        header.setForeground(Color.black);
        header.setFont(new Font("Arial", Font.BOLD, 12));

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
    }


    // To create a DefaultTableModel class that extends AbstractTableModel(To manage product details table)
    private static class DefaultTableModel extends AbstractTableModel {

        //To store list of products
        private List<Product> productList;

        //To store filtering category
        private String selectedCategory;

        // Constructor for the class
        public DefaultTableModel(List<Product> productList) {
            this.productList = productList;
        }

        //To get the number of table rows
        @Override
        public int getRowCount() {
            return productList.size();
        }

        //To get the number of table columns
        @Override
        public int getColumnCount() {
            return 5;
        }

        // Override method to set data for each cell of the table
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            // To check if the row index is in the limit
            if (rowIndex >= 0 && rowIndex < productList.size()) {
                Product product = productList.get(rowIndex);

                // To check if the product's category matches the selected category
                if (selectedCategory == null || selectedCategory.equals(product.get_Type())) {
                    // Switch to update each product detail column by calling the respective methods
                    switch (columnIndex) {
                        case 0:
                            return product.get_Product_Id();
                        case 1:
                            return product.get_Product_Name();
                        case 2:
                            return product.get_Type();
                        case 3:
                            return product.get_Price();
                        case 4:
                            // To update info column as per product type details (these details depend on product type)
                            if (product instanceof Electronics) {
                                Electronics electronics = (Electronics) product;
                                return electronics.get_Brand_Of_Product() + ", " + electronics.getWarranty_Period() + " months warranty";
                            } else if (product instanceof Clothing) {
                                Clothing clothing = (Clothing) product;
                                return "Size: " + clothing.get_Size() + ", Color: " + clothing.get_Colour();
                            }
                            return "";
                        default:
                            return null;
                    }
                }
            }
            return null;
        }


        //To update each column header name
        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "Product ID";
            } else if (column == 1) {
                return "Product Name";
            } else if (column == 2) {
                return "Category";
            } else if (column == 3) {
                return "Price";
            } else if (column == 4) {
                return "Info";
            }
            return null;
        }

        //To update the list of available products
        public void updateData(List<Product> updatedProducts) {
            this.productList = updatedProducts;
            fireTableDataChanged();   //To redraw the table according to the product list data
        }
    }

    // To create a DefaultTableModel class that extends AbstractTableModel(To manage shopping cart product details table)
    public static class ShoppingCartTableModel extends AbstractTableModel {

        //List to store shopping cart products
        private List<ShoppingCart.CartItem> cartItems;

        //Constructor to initialize new shopping cart products objects to create instances
        public ShoppingCartTableModel(List<ShoppingCart.CartItem> cartItems) {
            this.cartItems = cartItems;
        }

        //To get row count of the shopping cart table
        @Override
        public int getRowCount() {
            return cartItems.size();
        }

        //To get no of column count of the table
        @Override
        public int getColumnCount() {
            return 3;
        }

        //To update table cells
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ShoppingCart.CartItem cartItem = cartItems.get(rowIndex);

            if (columnIndex == 0) {
                //To Customize product name column for clothing and electronics
                Product product = cartItem.getProduct();
                String productName;
                if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    productName = String.format("%s (%s, %s)", clothing.get_Product_Name(), clothing.get_Size(), clothing.get_Colour());
                } else if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    productName = String.format("%s (%s, %s )", electronics.get_Product_Name(),
                            electronics.get_Brand_Of_Product(), electronics.getWarranty_Period());
                } else {
                    productName = product.get_Product_Name();
                }
                return productName;
            } else if (columnIndex == 1) {
                return cartItem.getQuantity();
            } else if (columnIndex == 2) {
                return cartItem.getTotalPrice();
            }
            return null;
        }


        //Override method to set column names of the table
        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "Product Details";
            } else if (column == 1) {
                return "Quantity";
            } else if (column == 2) {
                return "Total Price";
            }
            return null;
        }

        //To update the product list according to the products addShopped to the cart
        public void updateDataForCart(List<ShoppingCart.CartItem> cartItems) {
            this.cartItems = cartItems;
            fireTableDataChanged();   //To redraw the table according to the cart items data
        }
    }

}



