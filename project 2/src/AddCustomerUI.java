import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class AddCustomerUI {
    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtName = new JTextField(10);
    public JTextField txtPhone = new JTextField(10);
    public JTextField txtAddress = new JTextField(10);

    public JLabel labID = new JLabel("CustomerID: ");
    public JLabel labName = new JLabel("Customer Name: ");

    public JLabel labPhone = new JLabel("Customer Phone: ");
    public JLabel labAddress = new JLabel("Customer Address: ");



    ProductModel product;
    PurchaseModel purchase;
    CustomerModel customer;

    public AddCustomerUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add Customer");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("Customer ID "));
        line.add(txtCustomerID);
        line.add(labID);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Name "));
        line.add(txtName);
        line.add(labName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Phone "));
        line.add(txtPhone);
        line.add(labPhone);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Address "));
        line.add(txtAddress);
        line.add(labAddress);
        view.getContentPane().add(line);


        line = new JPanel(new FlowLayout());
        line.add(btnAdd);
        line.add(btnUpdate);
        line.add(btnCancel);
        view.getContentPane().add(line);


        txtCustomerID.addFocusListener(new CustomerIDFocusListener());


        btnAdd.addActionListener(new AddButtonListerner());
    }

    public static void main(String[] args) {
        AddCustomerUI ui = new AddCustomerUI();
        ui.view.setVisible(true);
    }

    public void run() {
        customer = new CustomerModel();
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
            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                labID.setText("Customer ID: ");
                return;
            }

            System.out.println("CustomerID = " + s);

            try {
                purchase.mProductID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            product = StoreManager.getInstance().getDataAdapter().loadProduct(purchase.mProductID);

            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No customer with ID = " + purchase.mProductID , "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labID.setText("Customer ID: ");

                return;
            }

            labID.setText("Customer ID: " + customer.mName);


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
            String s = txtName.getText();

            if (s.length() == 0) {
                labName.setText("Customer Name: ");
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
                labName.setText("Customer Name: ");

                return;
            }

            labName.setText("Customer Name: " + customer.mName);

        }

    }




    class AddButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
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
