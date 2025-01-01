import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

public class Waiter {
    Waiter(){
        Font f = new Font("Calibri", Font.BOLD, 55);
        Font f1 = new Font("Calibri", Font.BOLD, 20);

        Border panelborder=BorderFactory.createLineBorder(new Color(69, 10, 168, 196),1);
        Border margin=new EmptyBorder(5,5,5,5);

        JFrame frame=new JFrame();
        frame.setLayout(new BorderLayout(10,10));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1200,900));
        frame.setLocationRelativeTo(null);

        JPanel title=new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel titlelabel=new JLabel("Restuarent",JLabel.CENTER);
        titlelabel.setFont(f);
        title.add(titlelabel);
        title.setBorder(panelborder);

        JPanel display=new JPanel(new BorderLayout());
//        display.setMaximumSize(new Dimension(900,700));



        JPanel orders=new JPanel();
        orders.setLayout(new BoxLayout(orders,BoxLayout.Y_AXIS));
//        orders.setPreferredSize(new Dimension(500,600));


        JScrollPane orderscroll=new JScrollPane();
        orderscroll.setViewportView(orders);
//        orderscroll.setPreferredSize(new Dimension(600,1900));


        JLabel ordertitle=new JLabel(" Serve Orders",JLabel.CENTER);
        ordertitle.setFont(f);



        JPanel ordercontainer=new JPanel();
        ordercontainer.setLayout(new BorderLayout(10,10));
        ordercontainer.add(ordertitle,BorderLayout.NORTH);
        ordercontainer.add(orderscroll,BorderLayout.CENTER);
        ordercontainer.setPreferredSize(new Dimension(500,400));


        JPanel bills=new JPanel();
        bills.setLayout(new BoxLayout(bills,BoxLayout.Y_AXIS));

        JScrollPane billscroll=new JScrollPane();
        billscroll.setViewportView(bills);

        JLabel billtitle=new JLabel("Collect Bills",JLabel.CENTER);
        billtitle.setFont(f);

        JPanel billscontainer=new JPanel(new BorderLayout(10,10));
        billscontainer.add(billtitle,BorderLayout.NORTH);
        billscontainer.add(billscroll,BorderLayout.CENTER);

        JPanel box=new JPanel(new BorderLayout());
        box.add(ordercontainer,BorderLayout.WEST);
        box.add(billscontainer,BorderLayout.CENTER);






        frame.add(title,BorderLayout.NORTH);
        frame.add(box);

        java.util.Timer timer = new Timer();

        String url = "jdbc:mysql://localhost:3306/restro";

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run(){
                try(Connection con= DriverManager.getConnection(url,"root","Shubham1s23@"))
                {
                    String sql="select * from currentorders where status='Completed'";
                    try(PreparedStatement pst=con.prepareStatement(sql)){
                        ResultSet rs=pst.executeQuery();
                        orders.removeAll();
                        while (rs.next()){
                            int Tableno=rs.getInt("tableno");
                            String name=rs.getString("name");
                            int id=rs.getInt("id");

                            JPanel row=new JPanel(new FlowLayout());
                            row.setMaximumSize(new Dimension(500,50));
                            row.setBorder(new CompoundBorder(margin,panelborder));

                            JLabel tableno=new JLabel("Table No:"+Integer.toString(Tableno));
                            tableno.setFont(f1);
                            tableno.setPreferredSize(new Dimension(100,30));

                            JLabel names=new JLabel(name.toUpperCase(),JLabel.CENTER);
                            names.setFont(f1);
                            names.setPreferredSize(new Dimension(190,30));

                            JButton Served=new JButton("Served");
                            Served.setFont(f1);
                            Served.setPreferredSize(new Dimension(100,30));
                            Served.setBorder(panelborder);
                            Served.setBackground(new Color(157, 123, 233));
                            Served.setForeground(new Color(19, 25, 18));

                            Served.addActionListener(
                                    a->{
                                        Served.setBackground(new Color(181, 56, 217));
                                        try(Connection con1=DriverManager.getConnection(url,"root","Shubham1s23@")) {
                                            String querry = "update currentorders set status='finished' where name=? and id=?";
                                            try (PreparedStatement pst1 = con1.prepareStatement(querry)) {
                                                pst1.setString(1, name);
                                                pst1.setDouble(2, id);
                                                pst1.executeUpdate();
                                                row.setVisible(false);
                                                JOptionPane.showMessageDialog(null,name+" is Served");

                                            }
                                        }catch (Exception e){
                                            JOptionPane.showMessageDialog(null,e.getMessage());
                                        }
                                    }
                            );

                            row.add(tableno);
                            row.add(names);
                            row.add(Served);

                            orders.add(row);


                        orders.revalidate();
                        orders.repaint();
                        }


                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(null,e.getMessage());

                }
            }

        }, 0, 10000);

        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new Waiter();
    }
}
