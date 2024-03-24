package src.main.java;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppointmentBookingUI extends JFrame {
    private JLabel titleLabel;
    private JLabel serviceNameLabel;
    private JComboBox<String> serviceComboBox;
    private JLabel dateLabel;
    private JTextField dateTextField;
    private JLabel timeLabel;
    private JComboBox<String> timeComboBox;
    private JButton bookButton;

    public AppointmentBookingUI() {
        setTitle("Reservar cita en la peluquería");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("Peluquería VyA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        serviceNameLabel = new JLabel("Servicio:");
        serviceComboBox = new JComboBox<>(new String[] { "Corte de pelo", "Tinte", "Peinado", "Otros" });
        dateLabel = new JLabel("Fecha:");
        dateTextField = new JTextField(20);
        timeLabel = new JLabel("Hora:");
        timeComboBox = new JComboBox<>(
                new String[] { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" });
        bookButton = new JButton("Reservar cita");
        bookButton.setEnabled(false); // Disabled by default

        setLayout(new BorderLayout(10, 10));
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panel.add(titleLabel);
        panel.add(new JLabel());
        panel.add(serviceNameLabel);
        panel.add(serviceComboBox);
        panel.add(dateLabel);
        panel.add(dateTextField);
        panel.add(timeLabel);
        panel.add(timeComboBox);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(bookButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Enable the button initially
        enableButton();

        // DocumentListener to enable/disable the button based on the date text field
        dateTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableButton();
            }
        });

        // ActionListener for the book button
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedService = (String) serviceComboBox.getSelectedItem();
                String selectedDate = dateTextField.getText();
                String selectedTime = (String) timeComboBox.getSelectedItem();
                
                // Validate the date format
                try {
                    if (!isValidDateFormat(selectedDate)) {
                        JOptionPane.showMessageDialog(AppointmentBookingUI.this,
                                "El formato de fecha debe ser: dd-MM-yyyy", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Reserva reserva = new Reserva(selectedService, selectedDate, selectedTime);
                    CalendarQuickstart.reservar(reserva);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(AppointmentBookingUI.this,
                            "El formato de fecha debe ser: dd-MM-yyyy", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e1) {
            
                } catch (GeneralSecurityException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    // Method to enable/disable the button based on the validity of the date
    private void enableButton() {
        String date = dateTextField.getText();
        boolean isValid = isValidDateFormat(date);
        bookButton.setEnabled(!date.isEmpty() && isValid);
    }

    // Method to validate the date format
    private boolean isValidDateFormat(String date) {
        String regex = "\\d{2}-\\d{2}-\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppointmentBookingUI appointmentBookingUI = new AppointmentBookingUI();
                appointmentBookingUI.setVisible(true);
            }
        });
    }
}
