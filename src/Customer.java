import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.management.BufferPoolMXBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

class Customer {
   static double tableno=10;
    Customer(){
        Border panelborder=BorderFactory.createLineBorder(new Color(69, 10, 168, 196),1);

        Font f = new Font("Calibri", Font.BOLD, 50);
        Font f1 = new Font("Calibri", Font.BOLD, 30);
        Font f2 = new Font("Calibri", Font.BOLD, 18);

        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1550,950);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.yellow);

        JPanel titlePanel =new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(212, 212, 232));
        titlePanel.setPreferredSize(new Dimension(900,80));
        titlePanel.setBorder(panelborder);

        JLabel title=new JLabel("Restuarant",JLabel.CENTER);
        title.setFont(f);
        title.setForeground(Color.BLACK);
        titlePanel.add(title);

        JPanel leftPanel=new JPanel(new BorderLayout());

        JPanel catAlign=new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel catTitle=new JLabel("Categories");
        catTitle.setFont(f1);

        catAlign.add(catTitle);
        catAlign.setBackground(new Color(212, 212, 232));

        JPanel category=new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        category.setBackground(new Color(212, 212, 232));

        Vector<JButton> b=new Vector<>();



        String url = "jdbc:mysql://localhost:3306/restro";
        try (Connection con = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
            String sql="select * from category order by id";
            try(PreparedStatement pst=con.prepareStatement(sql)){
                ResultSet rs= pst.executeQuery();

                while(rs.next()){
                    String s1=rs.getString("name");
                    JButton b1=new JButton(s1.toUpperCase());
                    b1.setFont(f2);
                    b1.setPreferredSize(new Dimension(170,40));
                    b.add(b1);

                    category.add(b1);

                }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        leftPanel.add(catAlign,BorderLayout.NORTH);
        leftPanel.add(category,BorderLayout.CENTER);

        leftPanel.setBackground(new Color(230, 230, 250));
        leftPanel.setPreferredSize(new Dimension(250,700));
        leftPanel.setBorder(panelborder);

        JPanel bottomPanel=new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(230, 230, 250));
        bottomPanel.setPreferredSize(new Dimension(900,150));
        bottomPanel.setBorder(panelborder);

        JPanel menuhead=new JPanel(new BorderLayout());

        JLabel name=new JLabel("Name");
        name.setFont(f1);
        name.setBackground(new Color(157, 123, 233));


        JPanel nameAlign=new JPanel(new FlowLayout(FlowLayout.CENTER));
        nameAlign.add(name);
        nameAlign.setPreferredSize(new Dimension(100,30));
        nameAlign.setBackground(new Color(157, 123, 233));

        JLabel price=new JLabel("Price");
        price.setBackground(new Color(157, 123, 233));
        price.setFont(f1);


        JPanel priceAlign=new JPanel(new FlowLayout(FlowLayout.CENTER));
        priceAlign.add(price);
        priceAlign.setPreferredSize(new Dimension(50,30));
        priceAlign.setBackground(new Color(157, 123, 233));

        menuhead.add(nameAlign,BorderLayout.WEST);
        menuhead.add(priceAlign, BorderLayout.CENTER);

        menuhead.setBorder(panelborder);
        menuhead.setPreferredSize(new Dimension(700,40));


        JPanel menubox=new JPanel(new FlowLayout(FlowLayout.LEADING,0,0));

        JScrollPane menuscroll=new JScrollPane();
        menubox.setLayout(new BoxLayout(menubox,BoxLayout.Y_AXIS));// For vertical alignment of rows
        menuscroll.setViewportView(menubox);


        JPanel menu=new JPanel(new BorderLayout(10,25));
        menu.setPreferredSize(new Dimension(800,300));
        menu.add(menuhead,BorderLayout.NORTH);
        menu.add(menuscroll,BorderLayout.CENTER);


        JPanel centrePanel=new JPanel(new BorderLayout());
        centrePanel.setBackground(new Color(230, 230, 250));
        centrePanel.setBorder(panelborder);
        centrePanel.add(menu,BorderLayout.WEST);

        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(leftPanel,BorderLayout.WEST);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.add(centrePanel,BorderLayout.CENTER);

//        class item{
//            JPanel name;
//            JPanel price;
//            JPanel add;
//            int quantity=0;
//        }
//
//        Vector<item> items=new Vector<>();

        b.get(0).setBackground(new Color(174, 220, 213));
        try (Connection con = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
            String sql = "SELECT * FROM menu WHERE category=? ORDER BY id";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                menubox.add(Box.createVerticalStrut(10));
                pst.setString(1,"starter");
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String names = rs.getString("name");
                    double prices = rs.getDouble("price");
//                    item i=new item();

                    JLabel n = new JLabel(names.toUpperCase());
                    JLabel p = new JLabel(Double.toString(prices) + " /-");
                    JButton add = new JButton("+");
                    JLabel quantity=new JLabel("0");
                    JButton remove=new JButton("-");
                    p.setFont(f2);
                    n.setFont(f2);
                    add.setFont(f2);
                    quantity.setFont(f2);
                    remove.setFont(f2);
                    quantity.setVisible(false);
                    remove.setVisible(false);






                    JPanel npanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
                    npanel.add(n);
                    npanel.setPreferredSize(new Dimension(300, 20));

                    JPanel ppanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    ppanel.add(p);
                    ppanel.setPreferredSize(new Dimension(260, 20));
                    JPanel bpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
                    bpanel.add(add);
                    bpanel.add(quantity);
                    bpanel.add(remove);


                    add.addActionListener(
                            z->{
                                int d=Integer.parseInt(quantity.getText());
                                if(d==0){
                                    quantity.setText(Integer.toString(d+1));
                                    quantity.setVisible(true);
                                    remove.setVisible(true);
                                    bpanel.setBorder(panelborder);
                                }
                                else{
                                    quantity.setText(Integer.toString(d+1));
                                }
                            }
                    );

                    remove.addActionListener(
                            y->{
                                int d=Integer.parseInt(quantity.getText());
                                d--;
                                if(d==0){
                                    quantity.setText(Integer.toString(0));
                                    quantity.setVisible(false);
                                    remove.setVisible(false);
                                    bpanel.setBorder(null);

                                }
                                else{
                                    quantity.setText(Integer.toString(d));
                                }
                            }
                    );

//                  i.name=npanel;
//                  i.price=ppanel;
//                  i.add=bpanel;
//                  items.add(i);

                    JPanel row = new JPanel(new FlowLayout(FlowLayout.LEADING));
                    row.add(npanel);
                    row.add(ppanel);
                    row.add(bpanel);
                    row.setFont(f2);
                    row.setBorder(panelborder);
                    row.setMaximumSize(new Dimension(750,60));
                    menubox.add(row);
                    menubox.add(Box.createVerticalStrut(10));

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        menubox.revalidate(); // Revalidate the UI
        menubox.repaint();

       Vector<Boolean> isFirstClick=new Vector<Boolean>();

        for (JButton ditto : b) {

            ditto.addActionListener(a -> {
                ditto.setBackground(new Color(157, 77, 233));
                for (JButton bitto:b){
                    if(bitto!=ditto){
                        bitto.setBackground(new Color(228, 240, 234));
                    }
                }

                String s1 = ditto.getText().toLowerCase();
                menubox.removeAll(); // Clear old menu items
                menubox.add(Box.createVerticalStrut(10));
                try (Connection con = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
                    String sql = "SELECT * FROM menu WHERE category=? ORDER BY id";
                    try (PreparedStatement pst = con.prepareStatement(sql)) {
                        pst.setString(1, s1);

                        ResultSet rs = pst.executeQuery();
                        while (rs.next()) {
//                            item i=new item();
                            String names = rs.getString("name");
                            double prices = rs.getDouble("price");

                            JLabel n = new JLabel(names.toUpperCase());
                            JLabel p = new JLabel(Double.toString(prices) + " /-");
                            JButton add = new JButton("+");
                            JLabel quantity=new JLabel("0");
                            JButton remove=new JButton("-");



                            p.setFont(f2);
                            n.setFont(f2);
                            add.setFont(f2);
                            quantity.setFont(f2);
                            remove.setFont(f2);
                            quantity.setVisible(false);
                            remove.setVisible(false);

                            JPanel npanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
                            npanel.add(n);
                            npanel.setPreferredSize(new Dimension(300, 20));

                            JPanel ppanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            ppanel.add(p);
                            ppanel.setPreferredSize(new Dimension(260, 20));
                            JPanel bpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
                            bpanel.add(add);
                            bpanel.add(quantity);
                            bpanel.add(remove);




                            add.addActionListener(
                                    z->{
                                        int d=Integer.parseInt(quantity.getText());
                                       if(d==0){
                                        quantity.setText(Integer.toString(d+1));
                                        quantity.setVisible(true);
                                        remove.setVisible(true);
                                           bpanel.setBorder(panelborder);


                                           try (Connection con1 = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
                                               String querry="insert into currentorders(tableno,name,quantity,status) values(?,?,?,?)";
                                               try(PreparedStatement pst1=con1.prepareStatement(querry)){
                                                   pst1.setDouble(1,tableno);
                                                   pst1.setString(2,names);
                                                   pst1.setDouble(3,1.0);
                                                   pst1.setString(4,"ordered");
                                                   pst1.executeUpdate();
                                               }
                                           }catch (Exception e){
                                               JOptionPane.showMessageDialog(null,e.getMessage());
                                           }


                                       }
                                       else{
                                           quantity.setText(Integer.toString(d+1));
                                           try (Connection con1 = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
                                               String querry="update currentorders set quantity=? where name=?";
                                               try(PreparedStatement pst1=con1.prepareStatement(querry)){
                                                   pst1.setDouble(1,d+1);
                                                   pst1.setString(2,names);
                                                   pst1.executeUpdate();


                                               }
                                           }catch (Exception e){
                                               JOptionPane.showMessageDialog(null,e.getMessage());
                                           }
                                       }
                                    }
                            );

                            remove.addActionListener(
                                    y->{
                                        int d=Integer.parseInt(quantity.getText());
                                        d--;
                                        if(d==0){
                                            quantity.setText(Integer.toString(0));
                                            quantity.setVisible(false);
                                            remove.setVisible(false);
                                            bpanel.setBorder(null);

                                        }
                                        else{
                                            quantity.setText(Integer.toString(d));
                                        }
                                    }
                            );

//                            if(ditto!=b.get(0)){
//                                i.name=npanel;
//                                i.price=ppanel;
//                                i.add=bpanel;
//                                items.add(i);
//                            }

                            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEADING));
                            row.add(npanel);
                            row.add(ppanel);
                            row.add(bpanel);
                            row.setFont(f2);
                            row.setBorder(panelborder);
                            row.setMaximumSize(new Dimension(750,60));
                            menubox.add(row);
                            menubox.add(Box.createVerticalStrut(10));

                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                menubox.revalidate(); // Revalidate the UI
                menubox.repaint();
            });

        }

//        for(item ditto:items){
//            for(Component comp:ditto.add.getComponents()){
//                if(comp instanceof JButton){
//                    ((JButton) comp).addActionListener(
//                            a->{
////                            ditto.quantity++;
////                            JLabel q=new JLabel(Integer.toString(ditto.quantity));
////                            JButton b1=new JButton("-");
////                            b1.setBackground(Color.red);
////                            b1.setForeground(Color.WHITE);
////                            ditto.add.add(q);
////                            ditto.add.add(b1);
//                            }
//                    );
//                }
//            }
//        }








        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        try (Connection con1 = DriverManager.getConnection(url, "root", "Shubham1s23@")) {
                            String querry="Drop table currentorders";
                            try(PreparedStatement pst1=con1.prepareStatement(querry)){



                            }
                        }catch (Exception e1){
                            JOptionPane.showMessageDialog(null,e1.getMessage());
                        }
                    }
                }
        );


        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Customer();
    }
}
