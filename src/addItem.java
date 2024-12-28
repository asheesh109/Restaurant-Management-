import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

class addItem {

   addItem() {
        Font labelFont = new Font("Calibri", Font.BOLD, 20);
        // Create the main frame
        JFrame frame = new JFrame("Add Menu Item");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Create a panel to hold form components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name label and text field
        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
       nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Price label and text field
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(priceLabel, gbc);

        JTextField priceField = new JTextField(20);
        priceField.setPreferredSize(new Dimension(200,30));
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        // Category label and combo box
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(categoryLabel, gbc);

       Vector<String> category =new Vector<>();


       String url = "jdbc:mysql://localhost:3306/restro";
       try (Connection con = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
           String sql="select * from category order by id";
           try(PreparedStatement pst=con.prepareStatement(sql)){
               ResultSet rs= pst.executeQuery();

               while(rs.next()){
                   String s1=rs.getString("name");
                   category.add(s1.toUpperCase());

               }
           }
       }catch (Exception e){
           JOptionPane.showMessageDialog(null,e.getMessage());
       }


        JComboBox<String> categoryComboBox = new JComboBox<>(category);
        gbc.gridx = 1;
        panel.add(categoryComboBox, gbc);

        // Add button
        JButton addButton = new JButton("Add Item");
        addButton.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);
       // Back button
       JButton backButton = new JButton("Back");
        backButton.setFont(labelFont);

       gbc.gridx = 0;
       gbc.gridy = 4;
       gbc.gridwidth = 2;
       panel.add(backButton,gbc);


       // Add panel to frame
        frame.add(panel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);

        addButton.addActionListener(
                a->{
                    String s1=nameField.getText();
                    String s2=priceField.getText();
                    String s3=categoryComboBox.getSelectedItem().toString().toLowerCase();
                    if(!(s1.isEmpty()&&s2.isEmpty())) {
                        double d1=Double.parseDouble(s2);
                        try (Connection con = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
                            String sql = "insert into menu (name,price,category) values(?,?,?)";
                            try (PreparedStatement pst = con.prepareStatement(sql)) {
                                pst.setString(1,s1);
                                pst.setDouble(2,d1);
                                pst.setString(3,s3);

                                pst.executeUpdate();
                                nameField.setText("");
                                priceField.setText("");
                                categoryComboBox.setSelectedItem(null);
                                JOptionPane.showMessageDialog(null,"Item added Sucessfully");

                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Empty field");
                    }
                }
        );

        backButton.addActionListener(
                a->{
                    new editMenu();
                    frame.dispose();
                }
        );
    }

    public static void main(String[] args) {
        new addItem();
    }
}
