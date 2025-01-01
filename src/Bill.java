import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class data{
    double quantity;
    double price;
}

public class Bill extends JFrame {
    HashMap<String,data> h=new HashMap<String,data>();
    Bill(double orderId) {
        Font titleFont = new Font("Futura", Font.BOLD, 40);
        Font tableFont = new Font("Calibri", Font.PLAIN, 18);
        Font buttonFont = new Font("Calibri", Font.BOLD, 20);

        JLabel title = new JLabel("Bill", JLabel.CENTER);
        title.setFont(titleFont);
        title.setForeground(new Color(255, 255, 255));
        title.setOpaque(true);
        title.setBackground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Item name", "Quantity", "Price", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setFont(tableFont);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(224, 224, 224));

        JScrollPane scrollPane = new JScrollPane(table);



        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(255, 51, 51));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JButton pay=new JButton("Pay Online");
        pay.setFont(buttonFont);
        pay.setForeground(Color.WHITE);
        pay.setBackground(new Color(94, 45, 172, 196));
        pay.setFocusPainted(false);
        pay.setBorder((BorderFactory.createEmptyBorder(10, 25, 10, 25)));

        JButton pay1=new JButton("Cash (Call waiter)");
        pay1.setFont(buttonFont);
        pay1.setForeground(Color.WHITE);
        pay1.setBackground(new Color(94, 45, 172, 196));
        pay1.setFocusPainted(false);
        pay1.setBorder((BorderFactory.createEmptyBorder(10, 25, 10, 25)));

        pay.addActionListener(
                a->{
                    new Customer();
                    dispose();
                }
        );

        backButton.addActionListener(e ->
        {

            dispose();
        });

        pay1.addActionListener(
                a->{
                    String url = "jdbc:mysql://localhost:3306/restro";
                    try (Connection con = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
                        String sql="update currentorders set billstatus='cash' where orderid=?";
                        try(PreparedStatement pst=con.prepareStatement(sql)){
                            pst.setDouble(1,orderId);
                           pst.executeUpdate();
                        }
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }

                }
        );

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 102, 204));
        topPanel.add(title, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(224, 224, 224));
        bottomPanel.add(backButton);
        bottomPanel.add(pay);
        bottomPanel.add(pay1);

        Container c = getContentPane();
        c.setLayout(new BorderLayout(20, 20));
        c.add(topPanel, BorderLayout.NORTH);
        c.add(scrollPane, BorderLayout.CENTER);
        c.add(bottomPanel, BorderLayout.SOUTH);

        String url = "jdbc:mysql://localhost:3306/restro";
        try (Connection con = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
            String sql="select * from currentorders where orderId=?";
            try(PreparedStatement pst=con.prepareStatement(sql)){
                pst.setDouble(1,orderId);


                ResultSet rs= pst.executeQuery();

                while (rs.next()){
                    String s1=rs.getString("name");
                    double quant=rs.getDouble("quantity");
                    data d=new data();
                    if (h.containsKey(s1)){
                        double previous=h.get(s1).quantity;

                        d.quantity=quant+previous;
                        h.put(s1,d);
                    }
                    else {
                        d.quantity=quant;
                        h.put(s1,d);
                    }



                }

            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        double grandTotal=0.0;

        for (HashMap.Entry<String,data> ditto:h.entrySet()){
            data d=ditto.getValue();

            try (Connection con = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
                String sql="select price from menu where name=?";
                try(PreparedStatement pst=con.prepareStatement(sql)){
                    pst.setString(1,ditto.getKey());

                    ResultSet rs= pst.executeQuery();

                   if (rs.next()){
                       d.price=rs.getDouble("price");
                   }

                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage());

            }
            double total=d.price*d.quantity;
            grandTotal=grandTotal+total;
            tableModel.addRow(new Object[]{ditto.getKey(),d.quantity,d.price,total});

        }
        tableModel.addRow(new Object[]{null,null,"Total Amount",grandTotal});


        setTitle("Bill");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Bill(0.0);
    }
}
