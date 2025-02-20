import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

class AddCategory {
    AddCategory() {
        Font f = new Font("Calibri", Font.BOLD, 35);
        Font f1 = new Font("Calibri", Font.BOLD, 16);

        JLabel title = new JLabel("Add New Category");
        title.setFont(f);

        JLabel l1 = new JLabel("Enter category name:");
        JTextField t1 = new JTextField(15);
        JButton button = new JButton("Add");
        JButton back=new JButton("Back");
        back.setFont(f1);
        back.setPreferredSize(new Dimension(95, 30));

        l1.setFont(f1);
        t1.setFont(f1);
        button.setFont(f1);
        button.setPreferredSize(new Dimension(95, 30));

        JFrame frame = new JFrame("Add New Category");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margin between components

        // Adding the title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(title, gbc);

        // Adding the label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset to default
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(l1, gbc);

        // Adding the text field
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(t1, gbc);

        // Adding the button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Center button under text field and label
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Center button under text field and label
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(back, gbc);

        frame.setVisible(true);

        button.addActionListener(
                a->{
                    String s1=t1.getText().toLowerCase();
                    if(!s1.isEmpty()){
                        String url = "jdbc:mysql://localhost:3306/restro";
                        try (Connection con = DriverManager.getConnection(url, "root", "Ashish030406")) {
                            String sql="insert into category (name) values(?)";
                            try(PreparedStatement pst=con.prepareStatement(sql)){
                                pst.setString(1,s1);
                                pst.executeUpdate();
                                t1.setText("");
                                JOptionPane.showMessageDialog(null,"Category added successfully");
                            }
                        }catch (Exception e){
                            JOptionPane.showMessageDialog(null,e.getMessage());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Empty field");
                    }
                }
        );

        back.addActionListener(
                a->{
                    new editMenu();
                    frame.dispose();
                }
        );

    }

    public static void main(String[] args) {
        new AddCategory();
    }
}
