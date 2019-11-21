import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class AddProductUI {
    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtProductID = new JTextField(10);
    public JTextField txtPrice = new JTextField(10);
    public JTextField txtQuantity = new JTextField(10);



    public JLabel labID = new JLabel("Date of Purchase: ");

    public JLabel labPrice = new JLabel("Customer Name: ");
    public JLabel labQuantity = new JLabel("Product Name: ");

    public JLabel labCost = new JLabel("Cost: $0.00 ");
    public JLabel labTax = new JLabel("Tax: $0.00");
    public JLabel labTotalCost = new JLabel("Total Cost: $0.00");

    ProductModel product;
    PurchaseModel purchase;
    CustomerModel customer;

    public AddProductUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add Product");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("ProductID: "));
        line.add(txtProductID);

        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Price: "));
        line.add(txtPrice);

        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity: "));
        line.add(txtQuantity);

        view.getContentPane().add(line);



        line = new JPanel(new FlowLayout());
        line.add(btnAdd);
        line.add(btnUpdate);
        line.add(btnCancel);
        view.getContentPane().add(line);


        txtProductID.addFocusListener(new CustomerIDFocusListener());


        btnAdd.addActionListener(new AddButtonListerner());
    }

    public static void main(String[] args) {
        AddProductUI ui = new AddProductUI();
        ui.view.setVisible(true);
    }

    public void run() {
        purchase = new PurchaseModel();
        purchase.mDate = Calendar.getInstance().getTime().toString();
        labID.setText("Date of purchase: " + purchase.mDate);
        view.setVisible(true);
    }

    private class ProductIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtProductID.getText();

            if (s.length() == 0) {
                labPrice.setText("Product Name: [not specified!]");
                return;
            }

            System.out.println("ProductID = " + s);

            try {
                purchase.mProductID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid ProductID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);

            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labPrice.setText("Product Name: ");

                return;
            }

            labPrice.setText("Product Name: " + product.mName);
            purchase.mPrice = product.mPrice;
            labPrice.setText("Product Price: " + product.mPrice);

        }

    }

    private class CustomerIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtProductID.getText();

            if (s.length() == 0) {
                labQuantity.setText("Customer Name: [not specified!]");
                return;
            }

            System.out.println("CustomerID = " + s);

            try {
                purchase.mCustomerID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            customer = StoreManager.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);

            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labQuantity.setText("Customer Name: ");

                return;
            }

            labQuantity.setText("Product Name: " + customer.mName);

        }

    }


    class AddButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String id = txtProductID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

             int res = StoreManager.getInstance().getDataAdapter().savePurchase(purchase);
             if (res == SQLiteDataAdapter.PURCHASE_SAVE_FAILED)
                JOptionPane.showMessageDialog(null, "Purchase NOT added successfully! Duplicate product ID!");
             else
               JOptionPane.showMessageDialog(null, "Purchase added successfully!" + purchase);
        }
    }

}
