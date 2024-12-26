import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class ChefPage {
    static Timestamp lastfetch = new Timestamp(System.currentTimeMillis());

    public ChefPage() {

        JFrame frame = new JFrame("Chefpage");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());



        JLabel title = new JLabel("Restaurant", SwingConstants.CENTER);
        title.setFont(new Font("Futura", Font.BOLD, 50));
        title.setForeground(Color.white);
        title.setOpaque(true);
        title.setBackground(new Color(100, 10, 193));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(title, BorderLayout.NORTH);



        String[] columnNames = {"Name", "Quantity", "Status", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Calibri", Font.PLAIN, 18));
        table.setRowHeight(50);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 20));
        table.getTableHeader().setBackground(new Color(100, 10, 193));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(224, 224, 224));


        table.getColumnModel().getColumn(3).setPreferredWidth(400);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 550));
        scrollPane.setBorder(BorderFactory.createLineBorder( Color.white, 15));
        frame.add(scrollPane, BorderLayout.CENTER);


        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JTextField(), tableModel));


        String url = "jdbc:mysql://localhost:3306/restro";
        String user = "root";
        String password = "Shubham1s23@";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM currentorders WHERE status = 'ordered'";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    double quantity = rs.getDouble("quantity");
                    String status = rs.getString("status");
                    tableModel.addRow(new Object[]{name, quantity, status, ""});

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        java.util.Timer timer = new Timer();
        final Timestamp[] lastfetch = {new Timestamp(System.currentTimeMillis())};


        // Schedule the task to run every 1 second
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//
//
//
//            }
//        }, 0, 1000);




        frame.setVisible(true);
    }


    static class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setLayout(new GridLayout(1, 3, 10, 0));
            setBackground(new Color(245, 245, 245));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();

            JButton acceptButton = new JButton("Accept");
            JButton rejectButton = new JButton("Reject");
            JButton completeButton = new JButton("Complete");

            acceptButton.setBackground(Color.WHITE);
            acceptButton.setForeground(new Color(76, 175, 80));
            rejectButton.setBackground(Color.WHITE);
            rejectButton.setForeground(new Color(244, 67, 54));
            completeButton.setBackground(Color.WHITE);
            completeButton.setForeground(new Color(33, 100, 243));



            add(acceptButton);
            add(rejectButton);
            add(completeButton);

            return this;
        }
    }


    static class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JTable table;
        private DefaultTableModel tableModel;

        public ButtonEditor(JTextField textField, DefaultTableModel tableModel) {
            super(textField);

            this.tableModel = tableModel;
            panel = new JPanel(new GridLayout(1, 3, 10, 0));
            panel.setBackground(new Color(245, 245, 245));

            JButton acceptButton = new JButton("Accept");
            JButton rejectButton = new JButton("Reject");
            JButton completeButton = new JButton("Complete");

            acceptButton.setBackground(Color.WHITE);
            acceptButton.setForeground(new Color(76, 175, 80));
            rejectButton.setBackground(Color.WHITE);
            rejectButton.setForeground(new Color(244, 67, 54));
            completeButton.setBackground(Color.WHITE);
            completeButton.setForeground(new Color(33, 100, 243));

            acceptButton.addActionListener(a ->
            {
                updateStatus("Accepted", false);
            });
            rejectButton.addActionListener(a ->
            {
                updateStatus("Rejected", true);
            });
            completeButton.addActionListener(a -> {
                String status = tableModel.getValueAt(table.getEditingRow(), 2).toString();
                if ("Accepted".equalsIgnoreCase(status)) {
                    updateStatus("Completed", true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Order is not Accepted.");
                }
            });

            panel.add(acceptButton);
            panel.add(rejectButton);
            panel.add(completeButton);
        }

        void updateStatus(String status, boolean remove) {
            int row = table.getEditingRow();
            String name = tableModel.getValueAt(row, 0).toString();

            String url = "jdbc:mysql://localhost:3306/restro";
            String user = "root";
            String password = "Shubham1s23@";

            try (Connection con = DriverManager.getConnection(url, user, password)) {
                String updateSql = "UPDATE currentorders SET status = ? WHERE name = ?";
                try (PreparedStatement pst = con.prepareStatement(updateSql)) {
                    pst.setString(1, status);
                    pst.setString(2, name);
                    pst.executeUpdate();


                    tableModel.setValueAt(status, row, 2);
                    JOptionPane.showMessageDialog(null, "Status updated to " + status + " for " + name);

                    if (remove) {
                        tableModel.removeRow(row);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }
    }

    public static void main(String[] args) {
        new ChefPage();
    }
}