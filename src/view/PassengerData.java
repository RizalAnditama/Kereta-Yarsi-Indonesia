package view;

import controller.Controller;
import model.DatabaseConnector;
import model.Passenger;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.Document;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author mraih
 * @author Muhammad Rizal Anditama Nugraha
 */
public class PassengerData extends javax.swing.JFrame implements UpdateSeat {

    class MyIntFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (test(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                // warn the user and don't allow the insert
            }
        }

        private boolean test(String text) {
            if (text.isEmpty()) {
                return true;
            }
            if (text.contains(",") || text.contains(".")) {
                return false;
            }
            try {
                Double.parseDouble(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                AttributeSet attrs) throws BadLocationException {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (test(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // warn the user and don't allow the insert
            }

        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (test(sb.toString())) {
                super.remove(fb, offset, length);
            } else {
                // warn the user and don't allow the insert
            }

        }
    }

    private Controller con = Controller.getInstance();
    private List<JLabel> labels = new ArrayList<>();
    private int penumpangTotal = con.getSession().getPenumpangTotal();
    private int[] chosenSeatIndex = new int[penumpangTotal];

    private List<JPanel> passengerPanels = new ArrayList<>();
    private List<JComboBox<String>> tipeIDCombos = new ArrayList<>();
    private List<JTextField> noIDFields = new ArrayList<>();
    private List<JTextField> namaFields = new ArrayList<>();
    private List<JLabel> kursiLabels = new ArrayList<>();
    private List<Passenger> passengers = new ArrayList<>();

    /**
     * Creates new form passengerData
     */
    public PassengerData() {
        initComponents();

        panelPenumpang1.setVisible(false);
        jButton1.setVisible(false);

        setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= penumpangTotal; i++) {
            JPanel passengerPanel = createPassengerPanel(i);
            passengerPanels.add(passengerPanel);
            mainPanel.add(passengerPanel, BorderLayout.CENTER);
        }

        for (int i = 0; i < chosenSeatIndex.length; i++) {
            chosenSeatIndex[i] = -1;
        }

        JButton button = new JButton("Pilih metode pembayaran");
        mainPanel.add(button, BorderLayout.CENTER);
        button.setAlignmentX(CENTER_ALIGNMENT);

        button.addActionListener((ActionEvent e) -> {
            Set<String> uniqueIDs = new HashSet<>();
            boolean allSeatsChosen = true;

            for (int i = 0; i < penumpangTotal; i++) {
                boolean isAdult = (i + 1) <= con.getSession().getPenumpangDewasa();

                if (isAdult) {
                    String tipeID = (String) tipeIDCombos.get(i).getSelectedItem();
                    String noID = noIDFields.get(i).getText().trim();

                    // Check if ID is unique
                    if (!uniqueIDs.add(tipeID + noID)) {
                        con.infoMessage("Mohon gunakan ID yang berbeda untuk tiap penumpang");
                        return;
                    }
                }

                if (chosenSeatIndex[i] == -1) {
                    con.infoMessage("Mohon pilih kursi untuk semua penumpang!");
                    allSeatsChosen = false;
                    break;
                }
            }

            if (allSeatsChosen) {
                con.getSession().setPassenger(getAllPassengerData());
                new PaymentMethod().setVisible(true);
                dispose();
            }
        });
    }

    @Override
    public void updateSeat(int panelIndex, String seatNumber) {
        if (panelIndex >= 0 && panelIndex < labels.size()) {
            labels.get(panelIndex).setText("Kursi " + seatNumber);
        }
    }

    @Override
    public void updateSeatIndex(int passengerIndex, int seatIndex) {
        chosenSeatIndex[passengerIndex] = seatIndex;
    }

    JPanel createPassengerPanel(int urutanPenumpang) {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(0, 102, 51, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin
        GridBagConstraints gbc = new GridBagConstraints();
        boolean isPenumpangAnakAnak = urutanPenumpang > con.getSession().getPenumpangDewasa();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Penumpang " + urutanPenumpang);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);

        if (urutanPenumpang <= con.getSession().getPenumpangDewasa()) {
            JLabel tipeIDLabel = new JLabel("Tipe ID");
            tipeIDLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            contentPanel.add(tipeIDLabel, gbc);

            JComboBox<String> tipeIDCombo = new JComboBox<>(new String[]{"NPM", "KTP"});
            gbc.gridx = 1;
            gbc.gridy = 1;
            contentPanel.add(tipeIDCombo, gbc);
            tipeIDCombos.add(tipeIDCombo);

            JLabel noIDLabel = new JLabel("No. ID");
            noIDLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 2;
            contentPanel.add(noIDLabel, gbc);

            JTextField noIDField = new JTextField(15);
            gbc.gridx = 1;
            gbc.gridy = 2;
            contentPanel.add(noIDField, gbc);
            noIDFields.add(noIDField);

            PlainDocument doc = (PlainDocument) noIDField.getDocument();
            doc.setDocumentFilter(new MyIntFilter());
        }

        JLabel namaLabel = new JLabel("Nama Lengkap");
        namaLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = urutanPenumpang <= con.getSession().getPenumpangDewasa() ? 3 : 1;
        contentPanel.add(namaLabel, gbc);
        if (isPenumpangAnakAnak) {
            namaLabel.setVisible(false);
        }

        JTextField namaField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = urutanPenumpang <= con.getSession().getPenumpangDewasa() ? 3 : 1;
        contentPanel.add(namaField, gbc);
        namaFields.add(namaField);

        // Placeholder text setup
        String placeholder = "Masukkan nama lengkap";
        namaField.setText(placeholder);
        namaField.setForeground(Color.GRAY);

        namaField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (namaField.getText().equals(placeholder)) {
                    namaField.setText("");
                    namaField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (namaField.getText().isEmpty()) {
                    namaField.setText(placeholder);
                    namaField.setForeground(Color.GRAY);
                }
            }
        });

        JLabel kursiLabel = new JLabel("Kursi ##");
        kursiLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = urutanPenumpang <= con.getSession().getPenumpangDewasa() ? 4 : 2;
        contentPanel.add(kursiLabel, gbc);
        labels.add(kursiLabel);
        if (isPenumpangAnakAnak) {
            kursiLabel.setVisible(false);
        }

        JButton pilihKursiButton = new JButton("Pilih Kursi");
        gbc.gridx = 1;
        gbc.gridy = urutanPenumpang <= con.getSession().getPenumpangDewasa() ? 4 : 2;
        contentPanel.add(pilihKursiButton, gbc);

        pilihKursiButton.addActionListener((ActionEvent e) -> {
            String fullName = namaField.getText().trim();
            boolean isPenumpangDewasa = urutanPenumpang <= con.getSession().getPenumpangDewasa();

            if (isPenumpangDewasa) {
                JTextField noIDField = noIDFields.get(urutanPenumpang - 1);
                String numberID = noIDField.getText().trim();
                if (numberID.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Isi ID terlebih dahulu", "System", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (numberID.length() < 10) {
                    JOptionPane.showMessageDialog(null, "ID kurang dari 10 karakter", "System", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            if (fullName.isEmpty() || fullName.equals(placeholder)) {
                JOptionPane.showMessageDialog(null, "Isi nama lengkap belum diisi", "System", JOptionPane.WARNING_MESSAGE);
            } else {
                new PickTrainSeat(PassengerData.this, urutanPenumpang - 1, chosenSeatIndex, urutanPenumpang - 1).setVisible(true);
            }
        });

        return contentPanel;
    }

    public List<Passenger> getAllPassengerData() {
        List<Passenger> passengers = new ArrayList<>();
        System.out.println("Starting to collect passenger data...");
        System.out.println("Number of passenger panels: " + passengerPanels.size());
        System.out.println("Number of tipeIDCombos: " + tipeIDCombos.size());
        System.out.println("Number of noIDFields: " + noIDFields.size());
        System.out.println("Number of namaFields: " + namaFields.size());
        System.out.println("Number of labels: " + labels.size());

        for (int i = 0; i < passengerPanels.size(); i++) {
            System.out.println("Processing passenger " + (i + 1));

            String namaLengkap = namaFields.get(i).getText().trim();
            String kursi = labels.get(i).getText().replace("Kursi ", "").trim();
            String tipeID = null;
            String noID = null;
            if (i < con.getSession().getPenumpangDewasa()) {
                tipeID = (String) tipeIDCombos.get(i).getSelectedItem();
                noID = noIDFields.get(i).getText().trim();
            }

            System.out.println("Raw data collected:");
            System.out.println("Tipe ID: " + tipeID);
            System.out.println("No ID: " + noID);
            System.out.println("Nama Lengkap: " + namaLengkap);
            System.out.println("Kursi: " + kursi);

            Passenger passenger = new Passenger(namaLengkap, tipeID, noID, kursi);
            passengers.add(passenger);

            System.out.println("Passenger object created:");
            System.out.println("Tipe ID: " + passenger.getTipeID());
            System.out.println("No ID: " + passenger.getNoID());
            System.out.println("Nama Lengkap: " + passenger.getNamaLengkap());
            System.out.println("Kursi: " + passenger.getKursi());
            System.out.println();
        }

        System.out.println("Finished collecting passenger data.");
        return passengers;
    }

    public void setPassengerData() {
        List<Passenger> passengers = getAllPassengerData();
        con.getSession().setPassenger(passengers);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        labelDataPenumpang = new javax.swing.JLabel();
        panelPenumpang1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbID = new javax.swing.JComboBox<>();
        labelNoID = new javax.swing.JLabel();
        textNumberID = new javax.swing.JTextField();
        labelPenumpang = new javax.swing.JLabel();
        labelNamaLengkap = new javax.swing.JLabel();
        textNamaLengkap = new javax.swing.JTextField();
        labelKursi = new javax.swing.JLabel();
        buttonPilihKursi = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Icon KYI.png")).getImage());
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(0, 102, 51));

        labelDataPenumpang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelDataPenumpang.setForeground(new java.awt.Color(255, 255, 255));
        labelDataPenumpang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDataPenumpang.setText("Data Penumpang");

        panelPenumpang1.setBackground(new java.awt.Color(0, 59, 29));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tipe ID");

        cbID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NPM", "KTP" }));

        labelNoID.setForeground(new java.awt.Color(255, 255, 255));
        labelNoID.setText("No. ID");

        labelPenumpang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelPenumpang.setForeground(new java.awt.Color(255, 255, 255));
        labelPenumpang.setText("Penumpang 1");

        labelNamaLengkap.setForeground(new java.awt.Color(255, 255, 255));
        labelNamaLengkap.setText("Nama Lengkap");

        labelKursi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelKursi.setForeground(new java.awt.Color(255, 255, 255));
        labelKursi.setText("Kursi ##");

        buttonPilihKursi.setText("Pilih Kursi");
        buttonPilihKursi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPilihKursiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPenumpang1Layout = new javax.swing.GroupLayout(panelPenumpang1);
        panelPenumpang1.setLayout(panelPenumpang1Layout);
        panelPenumpang1Layout.setHorizontalGroup(
            panelPenumpang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPenumpang1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPenumpang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPenumpang1Layout.createSequentialGroup()
                        .addGroup(panelPenumpang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelPenumpang1Layout.createSequentialGroup()
                                .addComponent(cbID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textNumberID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelNamaLengkap)
                            .addComponent(textNamaLengkap)
                            .addGroup(panelPenumpang1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(39, 39, 39)
                                .addComponent(labelNoID))
                            .addComponent(labelPenumpang))
                        .addGap(0, 112, Short.MAX_VALUE))
                    .addGroup(panelPenumpang1Layout.createSequentialGroup()
                        .addComponent(labelKursi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonPilihKursi)))
                .addContainerGap())
        );
        panelPenumpang1Layout.setVerticalGroup(
            panelPenumpang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPenumpang1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPenumpang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPenumpang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelNoID))
                .addGap(53, 53, 53)
                .addGroup(panelPenumpang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textNumberID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNamaLengkap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textNamaLengkap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPenumpang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelKursi)
                    .addComponent(buttonPilihKursi))
                .addContainerGap())
        );

        jButton1.setText("Pilih Metode Pembayaran");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPenumpang1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(labelDataPenumpang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDataPenumpang)
                .addGap(18, 18, 18)
                .addComponent(panelPenumpang1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPilihKursiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPilihKursiActionPerformed
        // TODO add your handling code here:
//        String numberID = textNumberID.getText();
//        String fullName = textNamaLengkap.getText();
//        PickTrainSeat seat = new PickTrainSeat();
//        if (numberID.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Isi ID terlebih dahulu", "System", JOptionPane.WARNING_MESSAGE);
//        } else if (numberID.length() < 10) {
//            JOptionPane.showMessageDialog(null, "ID kurang dari 10 karakter", "System", JOptionPane.WARNING_MESSAGE);
//        } else if (fullName.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Isi nama lengkap belum diisi", "System", JOptionPane.WARNING_MESSAGE);
//        } else {
//            seat.setVisible(true);
//        }

    }//GEN-LAST:event_buttonPilihKursiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setPassengerData();
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PassengerData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PassengerData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PassengerData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PassengerData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PassengerData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPilihKursi;
    private javax.swing.JComboBox<String> cbID;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelDataPenumpang;
    private javax.swing.JLabel labelKursi;
    private javax.swing.JLabel labelNamaLengkap;
    private javax.swing.JLabel labelNoID;
    private javax.swing.JLabel labelPenumpang;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelPenumpang1;
    private javax.swing.JTextField textNamaLengkap;
    private javax.swing.JTextField textNumberID;
    // End of variables declaration//GEN-END:variables

}
