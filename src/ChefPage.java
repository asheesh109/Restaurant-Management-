import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;

public class ChefPage {

    private DefaultTableModel tableModel;

    public ChefPage() {
        JFrame frame = new JFrame("Chefpage");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Current Orders", SwingConstants.CENTER);
        title.setFont(new Font("Futura", Font.BOLD, 50));
        title.setForeground(Color.white);
        title.setOpaque(true);
        title.setBackground(new Color(100, 10, 193));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(title, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "Quantity", "Status", "Actions"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Calibri", Font.PLAIN, 18));
        table.setRowHeight(50);
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 20));
        table.getTableHeader().setBackground(new Color(100, 10, 193));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(224, 224, 224));

        // Adjust column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(300);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 550));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.white, 15));
        frame.add(scrollPane, BorderLayout.CENTER);


        table.getColumn("Actions").setCellRenderer(new ButtonRenderer(table));
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JTextField(), table));

        loadOrders();


        Timer timer = new Timer(1000, e -> loadOrders());
        timer.start();

        frame.setVisible(true);
    }

    void loadOrders() {
        String url = "jdbc:mysql://localhost:3306/restro";
        String user = "root";
        String password = "Shubham1s23@";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT orderId, name, quantity, status FROM currentorders WHERE status = 'ordered' OR status = 'Accepted'";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                ResultSet rs = pst.executeQuery();



                while (rs.next()) {
                    int id = rs.getInt("orderId");
                    String name = rs.getString("name");
                    double quantity = rs.getDouble("quantity");
                    String status = rs.getString("status");


                    boolean exists = false;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if ((int) tableModel.getValueAt(i, 0) == id) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        tableModel.addRow(new Object[]{id, name, quantity, status, ""});
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    static class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JTable table;

        public ButtonRenderer(JTable table) {
            this.table = table;
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


            acceptButton.addActionListener(a ->
            {
                updateStatus("Accepted", row);
            });
            rejectButton.addActionListener(a ->
            {
                updateStatus("Rejected", row);
            });
            completeButton.addActionListener(a ->
            {
                String status = table.getValueAt(row, 3).toString();
                if ("Accepted".equalsIgnoreCase(status)) {
                    updateStatus("Completed", row);
                } else {
                    JOptionPane.showMessageDialog(null, "Order is not Accepted.");
                }
            });

            add(acceptButton);
            add(rejectButton);
            add(completeButton);

            return this;
        }

        void updateStatus(String status, int row) {

            if (row == -1) {
                row = table.getEditingRow();
            }

//

            int id = (int) table.getValueAt(row, 0);

            String url = "jdbc:mysql://localhost:3306/restro";
            String user = "root";
            String password = "Shubham1s23@";

            try (Connection con = DriverManager.getConnection(url, user, password)) {
                String updateSql = "UPDATE currentorders SET status = ? WHERE orderId = ?";
                try (PreparedStatement pst = con.prepareStatement(updateSql)) {
                    pst.setString(1, status);
                    pst.setInt(2, id);
                    pst.executeUpdate();

                    table.setValueAt(status, row, 3);

                    JOptionPane.showMessageDialog(null, "Status updated to " + status);


                    if ("Completed".equals(status)) {
                        ((DefaultTableModel) table.getModel()).removeRow(row);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }


    static class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JTable table;

        public ButtonEditor(JTextField textField, JTable table) {
            super(textField);
            this.table = table;
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


            acceptButton.addActionListener(a -> updateStatus("Accepted", table.getEditingRow()));
            rejectButton.addActionListener(a -> updateStatus("Rejected", table.getEditingRow()));
            completeButton.addActionListener(a -> {
                int row = table.getEditingRow();
                String status = table.getValueAt(row, 3).toString();
                if ("Accepted".equalsIgnoreCase(status)) {
                    updateStatus("Completed", row);
                } else {
                    JOptionPane.showMessageDialog(null, "Order is not Accepted.");
                }
            });

            panel.add(acceptButton);
            panel.add(rejectButton);
            panel.add(completeButton);
        }

        void updateStatus(String status, int row) {
            if (row == -1) {
                row = table.getEditingRow();
            }

            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row first.");
                return;
            }

            int id = (int) table.getValueAt(row, 0);

            String url = "jdbc:mysql://localhost:3306/restro";
            String user = "root";
            String password = "Shubham1s23@";

            try (Connection con = DriverManager.getConnection(url, user, password)) {
                String updateSql = "UPDATE currentorders SET status = ? WHERE orderId = ?";
                try (PreparedStatement pst = con.prepareStatement(updateSql)) {
                    pst.setString(1,status);
                    pst.setInt(2,id);
                    pst.executeUpdate();

                    table.setValueAt(status, row, 3);

                    JOptionPane.showMessageDialog(null, "Status updated to " + status);


                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }


                    if ("Completed".equals(status) || "Rejected".equals(status)) {
                        ((DefaultTableModel) table.getModel()).removeRow(row);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }


        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            return panel;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChefPage::new);
    }
}