import javax.swing.*;
import java.awt.*;

class Landing extends JFrame {
    Landing() {
        Font f = new Font("futura", Font.BOLD, 40);
        Font f2 = new Font("Calibri", Font.PLAIN, 22);

        JLabel l1 = new JLabel("Restaurant Management System", JLabel.CENTER);
        JButton b1 = new JButton("Admin");
        JButton b2 = new JButton("Customer");
        JButton b3 = new JButton("Chef");
        JButton b4 = new JButton("Waiter");

        l1.setFont(f);
        b1.setFont(f2);
        b2.setFont(f2);
        b3.setFont(f2);
        b4.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

        l1.setBounds(150, 50, 500, 50);
        b1.setBounds(300, 150, 200, 50);
        b2.setBounds(300, 230, 200, 50);
        b3.setBounds(300, 310, 200, 50);
        b4.setBounds(300,390,200,50);

        c.add(l1);

        c.add(b1);  b1.addActionListener(a->{
            new editMenu();
            dispose();
        });
        c.add(b2);
        b2.addActionListener(a->{
            new Customer();
            dispose();
        });
        c.add(b3);
        b3.addActionListener(a->{
            new ChefPage();
            dispose();
        });
        c.add(b4);
        b4.addActionListener(
                a->{
                    new Waiter();
                    dispose();
                }
        );

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Landing Page");
    }

    public static void main(String[] args) {
        Landing a = new Landing();
    }
}
