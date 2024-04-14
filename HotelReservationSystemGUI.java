import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
// import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.sql.*;
import java.awt.BorderLayout;

public class HotelReservationSystemGUI {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "omayersql23@";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        JFrame frame = new JFrame("Hotel Reservation System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton reserveButton = new JButton("Reserve a room");
        reserveButton.setBounds(50, 50, 150, 30);
        frame.add(reserveButton);

        JButton viewButton = new JButton("View Reservations");
        viewButton.setBounds(200, 50, 150, 30);
        frame.add(viewButton);

        JButton getRoomButton = new JButton("Get Room Number");
        getRoomButton.setBounds(50, 100, 150, 30);
        frame.add(getRoomButton);

        JButton updateButton = new JButton("Update Reservations");
        updateButton.setBounds(200, 100, 150, 30);
        frame.add(updateButton);

        JButton deleteButton = new JButton("Delete Reservations");
        deleteButton.setBounds(125, 150, 150, 30);
        frame.add(deleteButton);

        reserveButton.addActionListener(e -> reserveRoom());
        viewButton.addActionListener(e -> viewReservations());
        getRoomButton.addActionListener(e -> getRoomNumber());
        updateButton.addActionListener(e -> updateReservation());
        deleteButton.addActionListener(e -> deleteReservation());

        frame.setVisible(true);
    }

    private static void reserveRoom() {
        JFrame reserveFrame = new JFrame("Reserve a Room");
        reserveFrame.setSize(400, 300);
        reserveFrame.setLayout(null);
    
        JLabel nameLabel = new JLabel("Guest Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        reserveFrame.add(nameLabel);
    
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        reserveFrame.add(nameField);
    
        JLabel roomLabel = new JLabel("Room Number:");
        roomLabel.setBounds(50, 100, 100, 30);
        reserveFrame.add(roomLabel);
    
        JTextField roomField = new JTextField();
        roomField.setBounds(150, 100, 200, 30);
        reserveFrame.add(roomField);
    
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setBounds(50, 150, 100, 30);
        reserveFrame.add(contactLabel);
    
        JTextField contactField = new JTextField();
        contactField.setBounds(150, 150, 200, 30);
        reserveFrame.add(contactField);
    
        JButton reserveButton = new JButton("Reserve");
        reserveButton.setBounds(150, 200, 100, 30);
        reserveFrame.add(reserveButton);
    
        reserveButton.addActionListener(e -> {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String guestName = nameField.getText();
                int roomNumber = Integer.parseInt(roomField.getText());
                String contactNumber = contactField.getText();
    
                String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";
    
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, guestName);
                    statement.setInt(2, roomNumber);
                    statement.setString(3, contactNumber);
    
                    int affectedRows = statement.executeUpdate();
    
                    if (affectedRows > 0) {
                        int choice = JOptionPane.showConfirmDialog(reserveFrame, "Reservation successful! Do you want to reserve another room?", "Success", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            reserveFrame.dispose(); // Close the current frame
                            reserveRoom(); // Open the reservation frame again
                        } else {
                            reserveFrame.dispose(); // Close the current frame
                        }
                    } else {
                        JOptionPane.showMessageDialog(reserveFrame, "Reservation failed.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    
        reserveFrame.setVisible(true);
    }
    


    private static void viewReservations() {
            JFrame viewFrame = new JFrame("View Reservations");
            viewFrame.setSize(600, 400);
            viewFrame.setLayout(new BorderLayout());

            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);

            model.addColumn("Reservation ID");
            model.addColumn("Guest Name");
            model.addColumn("Room Number");
            model.addColumn("Contact Number");
            model.addColumn("Reservation Date");

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";

                try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                    while (resultSet.next()) {
                        int reservationId = resultSet.getInt("reservation_id");
                        String guestName = resultSet.getString("guest_name");
                        int roomNumber = resultSet.getInt("room_number");
                        String contactNumber = resultSet.getString("contact_number");
                        String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                        model.addRow(new Object[]{reservationId, guestName, roomNumber, contactNumber, reservationDate});
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            JScrollPane scrollPane = new JScrollPane(table);
            viewFrame.add(scrollPane, BorderLayout.CENTER);

            viewFrame.setVisible(true);
        }


        private static void getRoomNumber() {
            JFrame getRoomFrame = new JFrame("Get Room Number");
            getRoomFrame.setSize(400, 200);
            getRoomFrame.setLayout(null);
        
            JLabel idLabel = new JLabel("Reservation ID:");
            idLabel.setBounds(50, 50, 100, 30);
            getRoomFrame.add(idLabel);
        
            JTextField idField = new JTextField();
            idField.setBounds(150, 50, 200, 30);
            getRoomFrame.add(idField);
        
            JLabel nameLabel = new JLabel("Guest Name:");
            nameLabel.setBounds(50, 100, 100, 30);
            getRoomFrame.add(nameLabel);
        
            JTextField nameField = new JTextField();
            nameField.setBounds(150, 100, 200, 30);
            getRoomFrame.add(nameField);
        
            JButton getButton = new JButton("Get Room Number");
            getButton.setBounds(150, 150, 200, 30);
            getRoomFrame.add(getButton);
        
            getButton.addActionListener(e -> {
                try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    int reservationId = Integer.parseInt(idField.getText());
                    String guestName = nameField.getText();
        
                    String sql = "SELECT room_number FROM reservations " +
                            "WHERE reservation_id = ? AND guest_name = ?";
        
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, reservationId);
                        statement.setString(2, guestName);
        
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                int roomNumber = resultSet.getInt("room_number");
                                JOptionPane.showMessageDialog(getRoomFrame, "Room number for Reservation ID "
                                        + reservationId + " and Guest " + guestName + " is: " + roomNumber);
                            } else {
                                JOptionPane.showMessageDialog(getRoomFrame, "Reservation not found for the given ID and guest name.");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        
            getRoomFrame.setVisible(true);
        }
    
    private static void updateReservation() {
        JFrame updateFrame = new JFrame("Update Reservation");
        updateFrame.setSize(400, 300);
        updateFrame.setLayout(null);
    
        JLabel idLabel = new JLabel("Reservation ID:");
        idLabel.setBounds(50, 50, 100, 30);
        updateFrame.add(idLabel);
    
        JTextField idField = new JTextField();
        idField.setBounds(150, 50, 200, 30);
        updateFrame.add(idField);
    
        JLabel nameLabel = new JLabel("New Guest Name:");
        nameLabel.setBounds(50, 100, 100, 30);
        updateFrame.add(nameLabel);
    
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 100, 200, 30);
        updateFrame.add(nameField);
    
        JLabel roomLabel = new JLabel("New Room Number:");
        roomLabel.setBounds(50, 150, 100, 30);
        updateFrame.add(roomLabel);
    
        JTextField roomField = new JTextField();
        roomField.setBounds(150, 150, 200, 30);
        updateFrame.add(roomField);
    
        JLabel contactLabel = new JLabel("New Contact Number:");
        contactLabel.setBounds(50, 200, 120, 30);
        updateFrame.add(contactLabel);
    
        JTextField contactField = new JTextField();
        contactField.setBounds(170, 200, 180, 30);
        updateFrame.add(contactField);
    
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(150, 250, 100, 30);
        updateFrame.add(updateButton);
    
        updateButton.addActionListener(e -> {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                int reservationId = Integer.parseInt(idField.getText());
                String newGuestName = nameField.getText();
                int newRoomNumber = Integer.parseInt(roomField.getText());
                String newContactNumber = contactField.getText();
    
                String sql = "UPDATE reservations SET guest_name = ?, " +
                        "room_number = ?, " +
                        "contact_number = ? " +
                        "WHERE reservation_id = ?";
    
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, newGuestName);
                    statement.setInt(2, newRoomNumber);
                    statement.setString(3, newContactNumber);
                    statement.setInt(4, reservationId);
    
                    int affectedRows = statement.executeUpdate();
    
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(updateFrame, "Reservation updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(updateFrame, "Reservation update failed.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    
        updateFrame.setVisible(true);
    }
    
    private static void deleteReservation() {
        JFrame deleteFrame = new JFrame("Delete Reservation");
        deleteFrame.setSize(300, 150);
        deleteFrame.setLayout(null);
    
        JLabel idLabel = new JLabel("Reservation ID:");
        idLabel.setBounds(50, 50, 100, 30);
        deleteFrame.add(idLabel);
    
        JTextField idField = new JTextField();
        idField.setBounds(150, 50, 100, 30);
        deleteFrame.add(idField);
    
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(100, 100, 100, 30);
        deleteFrame.add(deleteButton);
    
        deleteButton.addActionListener(e -> {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                int reservationId = Integer.parseInt(idField.getText());
    
                String sql = "DELETE FROM reservations WHERE reservation_id = ?";
    
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, reservationId);
    
                    int affectedRows = statement.executeUpdate();
    
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(deleteFrame, "Reservation deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(deleteFrame, "Reservation deletion failed.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    
        deleteFrame.setVisible(true);
    }
    
}
