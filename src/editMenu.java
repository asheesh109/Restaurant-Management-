import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class editMenu {
    editMenu(){

        Border panelborder=BorderFactory.createLineBorder(Color.blue,2);
        Font buttonFont = new Font("Calibri", Font.BOLD, 20);


        JLabel title=new JLabel("Menu");
        title.setFont(new Font("Futura", Font.BOLD, 50));

        JFrame frame=new JFrame("Menu");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);


        JPanel titlePanel=new JPanel();
        titlePanel.add(title);
        titlePanel.setLayout(new FlowLayout());

        titlePanel.setBorder(panelborder);
        titlePanel.setPreferredSize(new Dimension(100,100));

        JButton add1=new JButton("Add item");
        add1.setFont(buttonFont);


        JButton delete=new JButton("Delete item");
        delete.setFont(buttonFont);

        JButton update=new JButton("Update price");
        update.setFont(buttonFont);

        JButton addcat=new JButton("Add category");
        addcat.setFont(buttonFont);



        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        buttonPanel.setPreferredSize(new Dimension(180,100));
        buttonPanel.add(add1);
        buttonPanel.add(delete);
        buttonPanel.add(update);
        buttonPanel.add(addcat);
        buttonPanel.setBorder(panelborder);

        Label cat=new Label("Categories");
        cat.setFont(new Font("Futura", Font.BOLD, 40));

        JPanel headingPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(cat);
        headingPanel.setBorder(panelborder);
        headingPanel.setPreferredSize(new Dimension(200,70));

        JButton button1=new JButton("Starters");
        button1.setFont(buttonFont);
        JButton button2=new JButton("Main Course");
        button2.setFont(buttonFont);


        ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(button1);
        buttonGroup.add(button2);

        JPanel catButton=new JPanel(new FlowLayout(FlowLayout.LEADING,10,10));
        catButton.setBorder(panelborder);
        catButton.setPreferredSize(new Dimension(200,100));
        catButton.add(button1);
        catButton.add(button2);

        JPanel gridPanel1=new JPanel();
        JPanel gridPanel2=new JPanel();
        JPanel gridPanel3=new JPanel();
        JPanel gridPanel4=new JPanel();

        gridPanel1.setBorder(panelborder);
        gridPanel2.setBorder(panelborder);
        gridPanel3.setBorder(panelborder);
        gridPanel4.setBorder(panelborder);

        gridPanel1.setBackground(Color.yellow);
        gridPanel2.setBackground(Color.yellow);
        gridPanel3.setBackground(Color.yellow);
        gridPanel4.setBackground(Color.yellow);

        JPanel dataPanel=new JPanel(new GridLayout(2,2));
        dataPanel.setBorder(panelborder);
        dataPanel.setPreferredSize(new Dimension(100,700));
//        dataPanel.add(gridPanel1);
//        dataPanel.add(gridPanel2);
//        dataPanel.add(gridPanel3);
//        dataPanel.add(gridPanel4);


        JPanel container=new JPanel(new BorderLayout());
        container.add(headingPanel,BorderLayout.NORTH);
        container.add(catButton,BorderLayout.NORTH);
        container.add(dataPanel,BorderLayout.SOUTH);




        frame.add(titlePanel,BorderLayout.NORTH);
        frame.add(buttonPanel,BorderLayout.WEST);
        frame.add(container,BorderLayout.CENTER);

        add1.addActionListener(
                a->{
                  new addItem();



                }
        );
    }

    public static void main(String[] args) {
        new editMenu();
    }
}
