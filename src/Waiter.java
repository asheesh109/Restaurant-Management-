import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class Waiter {
   static HashSet<Double> h=new HashSet<Double>();
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
        ordercontainer.setPreferredSize(new Dimension(600,400));


        JPanel bills=new JPanel();
        bills.setLayout(new BoxLayout(bills,BoxLayout.Y_AXIS));

        JScrollPane billscroll=new JScrollPane();
        billscroll.setBorder(panelborder);
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
        frame.add(box,BorderLayout.CENTER);

        java.util.Timer timer = new Timer();

        String url = "jdbc:mysql://localhost:3306/restro";

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run(){
                try(Connection con= DriverManager.getConnection(url,"root","Ashish030406"))
                {
                    String sql="select * from currentorders where status='Completed'";
                    try(PreparedStatement pst=con.prepareStatement(sql)){
                        ResultSet rs=pst.executeQuery();
                        orders.removeAll();
                        while (rs.next()){
                            int Tableno=rs.getInt("tableno");
                            String name=rs.getString("name");
                            int id=rs.getInt("id");

                            JPanel row=new JPanel(new FlowLayout(FlowLayout.LEADING));
                            row.setMaximumSize(new Dimension(650,69));
                            row.setBorder(new CompoundBorder(margin,panelborder));

                            JLabel tableno=new JLabel("Table No:"+Integer.toString(Tableno),JLabel.LEFT);
                            tableno.setFont(f1);
                            tableno.setPreferredSize(new Dimension(100,30));

                            JLabel names=new JLabel(name.toUpperCase(),JLabel.CENTER);
                            names.setFont(f1);
                            names.setPreferredSize(new Dimension(300,30));

                            JButton Served=new JButton("Served");
                            Served.setFont(f1);
                            Served.setPreferredSize(new Dimension(100,30));
                            Served.setBorder(panelborder);
                            Served.setBackground(new Color(157, 123, 233));
                            Served.setForeground(new Color(19, 25, 18));

                            Served.addActionListener(
                                    a->{
                                        Served.setBackground(new Color(181, 56, 217));
                                        try(Connection con1=DriverManager.getConnection(url,"root","Ashish030406")) {
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
                bills.removeAll();
                try(Connection con=DriverManager.getConnection(url,"root","Ashish030406")){
                    String querry="select * from currentorders where billstatus!='u' and billstatus!='collected'";
                    try(PreparedStatement pst= con.prepareStatement(querry)){
                        ResultSet rs=pst.executeQuery();


                        while (rs.next()){

                            double d=rs.getDouble("tableno");
                            String s1=rs.getString("billstatus")+" from Table no "+d;
                            double o=rs.getDouble("orderid");

                            if(!h.contains(o)) {
                                h.add(o);

                                JPanel row = new JPanel(new FlowLayout());
                                row.setMaximumSize(new Dimension(650, 69));

                                JLabel info = new JLabel(s1, JLabel.LEFT);
                                info.setPreferredSize(new Dimension(400, 50));
                                info.setFont(f1);

                                JButton collected = new JButton("Collected");
//                                collected.setPreferredSize(new Dimension(150, 50));
                                collected.setFont(f1);

                                collected.addActionListener(
                                        a->{

                                            try(Connection con1=DriverManager.getConnection(url,"root","Ashish030406")){
                                               String sql="update currentorders set billstatus=? where orderid=?" ;
                                               try(PreparedStatement pst1=con1.prepareStatement(sql)){
                                                   pst1.setString(1,"collected");
                                                   pst1.setDouble(2,o);
                                                   pst1.executeUpdate();
                                                   h.remove(o);
                                                   JOptionPane.showMessageDialog(null,"Collected");
                                                   row.setVisible(false);
                                               }
                                            }catch (Exception e){
                                                JOptionPane.showMessageDialog(null,e.getMessage());
                                            }
                                        }
                                );

                                row.add(info);
                                row.add(collected);
                                row.setBorder(new CompoundBorder(margin, panelborder));

                                bills.add(row);


                                bills.revalidate();
                                bills.repaint();
                            }
                        }
                    }

                }catch (Exception e){
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
