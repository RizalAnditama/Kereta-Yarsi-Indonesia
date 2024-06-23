package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import controller.Controller;
import controller.Session;
import model.Passenger;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author mraih
 * @author Muhammad Rizal Anditama Nugraha
 */
public class Ticket extends javax.swing.JFrame {

    private Controller con = Controller.getInstance();
    private controller.Session session = con.getSession();
    private List<Passenger> passengers;
    private String ticketCode;
    JPanel detailPenumpangPanel = new JPanel();

    public Ticket(List<Passenger> passengers, String ticketCode) {
        this.passengers = passengers;
        this.ticketCode = ticketCode;
        initComponents();
        setupUI();
    }

    // Constructor with passengers only
    public Ticket(List<Passenger> passengers) {
        this.passengers = passengers;
        this.ticketCode = generateTicketCode();
        initComponents();
        setupUI();
    }

    private void setupUI() {
        getContentPane().setBackground(new Color(0, 102, 51, 255));
        setExtendedState(MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = createGridBagConstraints();
        addComponentsToBackground(gbc);
        setPassengerDetails(gbc);
        addScrollPane();
        addConfirmationButton();
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        return gbc;
    }

    private void addComponentsToBackground(GridBagConstraints gbc) {
        this.add(background, gbc);
        jPanel1.setVisible(false);
        jButton1.setVisible(false);

        Timestamp timestampDepart = session.getTrain().getDepartingTime();
        Timestamp timestampArrive = session.getTrain().getArrivingTime();
        String stasiunAsal = session.getTrain().getStartingStation();
        String stasiunAkhir1 = session.getTrain().getEndingStation();
        String namaKereta1 = session.getTrain().getName();
        String username = session.getUser().getUsername();

        jamBerangkat.setText("Jam " + formatTimestamp(timestampDepart, "hh:mm"));
        tanggalBerangkat.setText(formatTimestamp(timestampDepart, "dd MMMM YYYY"));
        jamTiba.setText("Jam " + formatTimestamp(timestampArrive, "hh:mm"));
        tanggalTiba.setText(formatTimestamp(timestampArrive, "dd MMMM YYYY"));

        stasiunAwal.setText(stasiunAsal.toUpperCase());
        stasiunAkhir.setText(stasiunAkhir1.toUpperCase());
        namaKereta.setText(namaKereta1.toUpperCase());
        kodePemesanan.setText(ticketCode);

        Dimension size = header.getSize();
        ticket.setPreferredSize(new Dimension(header.getWidth() + 85, (int) ticket.getPreferredSize().getHeight()));
    }

    private String formatTimestamp(Timestamp timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(timestamp);
    }

    private void setPassengerDetails(GridBagConstraints gbc) {
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.ipadx = 110;
        gbc.ipady = 19;

        for (int i = 0; i < passengers.size(); i++) {
            GridBagConstraints gbcClone = (GridBagConstraints) gbc.clone();
            gbcClone.gridx = 0;
            gbcClone.gridy = i + 3;
            detailPenumpangPanel = createDetailPenumpangPanel(passengers.get(i));
            background.add(detailPenumpangPanel, gbcClone);
        }
    }

    private void addScrollPane() {
        JScrollPane jScrollPane1 = new JScrollPane(background);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setViewportView(background);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    }

    private void addConfirmationButton() {
        JButton confirmationButton = new JButton("OK");
        confirmationButton.setBorder(BorderFactory.createEmptyBorder(20, detailPenumpangPanel.getWidth() / 2, 20, detailPenumpangPanel.getWidth() / 2));
        background.add(confirmationButton, Box.CENTER_ALIGNMENT);
        confirmationButton.setAlignmentX(Box.CENTER_ALIGNMENT);
        confirmationButton.addActionListener(evt -> {
            new MainMenu_KYI().setVisible(true);
            dispose();
        });
    }

    private String generateTicketCode() {
        String username = session.getUser().getUsername().toUpperCase();
        if (username.length() > 4) {
            username = username.substring(0, 4);
        }
        String part1 = username.substring(0, 2);
        String part2 = username.length() > 2 ? username.substring(2) : "";
        return part1 + new SimpleDateFormat("ddMMyykms").format(new Date()) + part2;
    }

    private JPanel createDetailPenumpangPanel(Passenger passenger) {
        JPanel detailPanel = new JPanel();
        detailPanel.setBackground(new java.awt.Color(0, 59, 29));
        detailPanel.setMaximumSize(new java.awt.Dimension(550, 90));
        detailPanel.setSize(header.getSize());
        detailPanel.setLayout(new java.awt.GridBagLayout());
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel namaPenumpangLabel = new JLabel(passenger.getNamaLengkap());
        JLabel jenisIDAndNoIDPenumpangLabel = new JLabel(passenger.getTipeID() != null ? passenger.getTipeID() + " - " + passenger.getNoID() : "Tanpa ID");
        JLabel jenisUmurPenumpangLabel = new JLabel(passenger.getTipeUmur());
        JLabel noKursiPenumpangLabel = new JLabel("Kursi " + passenger.getKursi());

        namaPenumpangLabel.setForeground(Color.WHITE);
        namaPenumpangLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        jenisIDAndNoIDPenumpangLabel.setForeground(Color.WHITE);
        jenisIDAndNoIDPenumpangLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jenisUmurPenumpangLabel.setForeground(Color.WHITE);
        jenisUmurPenumpangLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        noKursiPenumpangLabel.setForeground(Color.WHITE);
        noKursiPenumpangLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 10); // Top, Left, Bottom, Right
        gbc.gridx = 0;
        gbc.gridy = 0;
        detailPanel.add(namaPenumpangLabel, gbc);

        gbc.insets = new Insets(0, 100, 0, 100); // Top, Left, Bottom, Right
        if (passenger.getNamaLengkap().length() > 21) {
            gbc.insets = new Insets(0, 50, 0, 50); // Top, Left, Bottom, Right
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        JPanel dumpPanel = new JPanel();
        dumpPanel.setBackground(new java.awt.Color(0, 59, 29, 255));
        detailPanel.add(dumpPanel, gbc);

        gbc.insets = new Insets(0, 10, 10, 0); // Top, Left, Bottom, Right
        gbc.gridx = 2;
        gbc.gridy = 0;
        detailPanel.add(jenisUmurPenumpangLabel, gbc);

        gbc.insets = new Insets(10, 0, 0, 10); // Top, Left, Bottom, Right
        gbc.gridx = 0;
        gbc.gridy = 1;
        detailPanel.add(jenisIDAndNoIDPenumpangLabel, gbc);

        gbc.insets = new Insets(0, 100, 0, 100); // Top, Left, Bottom, Right
        if (passenger.getNamaLengkap().length() > 21) {
            gbc.insets = new Insets(0, 50, 0, 50); // Top, Left, Bottom, Right
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        detailPanel.add(dumpPanel, gbc);

        gbc.insets = new Insets(10, 10, 0, 0); // Top, Left, Bottom, Right
        gbc.gridx = 2;
        gbc.gridy = 1;
        detailPanel.add(noKursiPenumpangLabel, gbc);

        Dimension size = header.getSize();
        detailPanel.setPreferredSize(new Dimension(header.getWidth() + 85, (int) detailPanel.getPreferredSize().getHeight()));
        detailPanel.setAlignmentX(Box.CENTER_ALIGNMENT);

        return detailPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        background = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        LOGO = new javax.swing.JLabel();
        labelKodePemesanan = new javax.swing.JLabel();
        kodePemesanan = new javax.swing.JLabel();
        ticket = new javax.swing.JPanel();
        jamBerangkat = new javax.swing.JLabel();
        jamTiba = new javax.swing.JLabel();
        tanggalBerangkat = new javax.swing.JLabel();
        tanggalTiba = new javax.swing.JLabel();
        stasiunAwal = new javax.swing.JLabel();
        stasiunAkhir = new javax.swing.JLabel();
        namaKereta = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        labelPenumpang = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jenisID2 = new javax.swing.JLabel();
        namaPenumpang2 = new javax.swing.JLabel();
        namaPenumpang4 = new javax.swing.JLabel();
        jenisID4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Icon KYI.png")).getImage());
        setResizable(false);

        background.setBackground(new java.awt.Color(0, 102, 51));
        background.setLayout(new javax.swing.BoxLayout(background, javax.swing.BoxLayout.Y_AXIS));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setLayout(new java.awt.GridBagLayout());

        LOGO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Logo KYI.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 17, 0, 0);
        header.add(LOGO, gridBagConstraints);

        labelKodePemesanan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelKodePemesanan.setForeground(new java.awt.Color(0, 0, 0));
        labelKodePemesanan.setText("Kode Pemesanan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(41, 135, 0, 0);
        header.add(labelKodePemesanan, gridBagConstraints);

        kodePemesanan.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        kodePemesanan.setForeground(new java.awt.Color(0, 0, 0));
        kodePemesanan.setText("########");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 105, 0, 29);
        header.add(kodePemesanan, gridBagConstraints);

        background.add(header);

        ticket.setBackground(new java.awt.Color(0, 59, 29));
        ticket.setLayout(new java.awt.GridBagLayout());

        jamBerangkat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jamBerangkat.setForeground(new java.awt.Color(255, 255, 255));
        jamBerangkat.setText("Jam B");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 14, 0, 0);
        ticket.add(jamBerangkat, gridBagConstraints);

        jamTiba.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jamTiba.setForeground(new java.awt.Color(255, 255, 255));
        jamTiba.setText("Jam B");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 14, 0, 0);
        ticket.add(jamTiba, gridBagConstraints);

        tanggalBerangkat.setForeground(new java.awt.Color(255, 255, 255));
        tanggalBerangkat.setText("Tanggal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 14, 0, 0);
        ticket.add(tanggalBerangkat, gridBagConstraints);

        tanggalTiba.setForeground(new java.awt.Color(255, 255, 255));
        tanggalTiba.setText("Tanggal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 14, 11, 0);
        ticket.add(tanggalTiba, gridBagConstraints);

        stasiunAwal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        stasiunAwal.setForeground(new java.awt.Color(255, 255, 255));
        stasiunAwal.setText("STASIUN AWAL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 101, 0, 0);
        ticket.add(stasiunAwal, gridBagConstraints);

        stasiunAkhir.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        stasiunAkhir.setForeground(new java.awt.Color(255, 255, 255));
        stasiunAkhir.setText("STASIUN AKHIR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(33, 101, 11, 0);
        ticket.add(stasiunAkhir, gridBagConstraints);

        namaKereta.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        namaKereta.setForeground(new java.awt.Color(255, 255, 255));
        namaKereta.setText("NAMA KERETA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 160, 0, 162);
        ticket.add(namaKereta, gridBagConstraints);

        background.add(ticket);

        jSeparator2.setMinimumSize(new java.awt.Dimension(50, 30));
        jSeparator2.setPreferredSize(new java.awt.Dimension(50, 30));
        background.add(jSeparator2);

        labelPenumpang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelPenumpang.setForeground(new java.awt.Color(255, 255, 255));
        labelPenumpang.setText("Penumpang");
        labelPenumpang.setFocusTraversalPolicyProvider(true);
        labelPenumpang.setFocusable(false);
        labelPenumpang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        background.add(labelPenumpang);
        labelPenumpang.setAlignmentX(Box.CENTER_ALIGNMENT);

        jPanel1.setBackground(new java.awt.Color(0, 59, 29));
        jPanel1.setMaximumSize(new java.awt.Dimension(550, 90));
        jPanel1.setPreferredSize(new java.awt.Dimension(550, 90));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jenisID2.setForeground(new java.awt.Color(255, 255, 255));
        jenisID2.setText("Jenis ID - Nomor ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 25, 0);
        jPanel1.add(jenisID2, gridBagConstraints);

        namaPenumpang2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        namaPenumpang2.setForeground(new java.awt.Color(255, 255, 255));
        namaPenumpang2.setText("Nama Penumpang");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        jPanel1.add(namaPenumpang2, gridBagConstraints);

        namaPenumpang4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        namaPenumpang4.setForeground(new java.awt.Color(255, 255, 255));
        namaPenumpang4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        namaPenumpang4.setText("Dewasa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 316, 0, 6);
        jPanel1.add(namaPenumpang4, gridBagConstraints);

        jenisID4.setForeground(new java.awt.Color(255, 255, 255));
        jenisID4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jenisID4.setText("Tipe Tike - No Kursi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 279, 25, 6);
        jPanel1.add(jenisID4, gridBagConstraints);

        background.add(jPanel1);

        jSeparator1.setMinimumSize(new java.awt.Dimension(50, 30));
        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 30));
        background.add(jSeparator1);

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        background.add(jButton1);

        getContentPane().add(background, java.awt.BorderLayout.CENTER);
        background.getAccessibleContext().setAccessibleName("");
        background.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MainMenu_KYI menu = new MainMenu_KYI();
        menu.setVisible(true);
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
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        List<Passenger> passengers = List.of(
                new Passenger("NPM", "1402023044", "Muhammad Rizal Anditama Nugraha", "A1"),
                new Passenger(null, null, "Jane Doe", "A2"),
                new Passenger(null, null, "Bane Doe", "A3"),
                new Passenger("KTP", "1234567890", "John Doe", "A1"),
                new Passenger("NPM", "0987654321", "Jane Doe", "A2"),
                new Passenger(null, null, "Bane Doe", "A3"),
                new Passenger(null, null, "Lone Doe", "A4")
        );

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Ticket(passengers).setVisible(true);
//                new Ticket().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LOGO;
    private javax.swing.JPanel background;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel jamBerangkat;
    private javax.swing.JLabel jamTiba;
    private javax.swing.JLabel jenisID2;
    private javax.swing.JLabel jenisID4;
    private javax.swing.JLabel kodePemesanan;
    private javax.swing.JLabel labelKodePemesanan;
    private javax.swing.JLabel labelPenumpang;
    private javax.swing.JLabel namaKereta;
    private javax.swing.JLabel namaPenumpang2;
    private javax.swing.JLabel namaPenumpang4;
    private javax.swing.JLabel stasiunAkhir;
    private javax.swing.JLabel stasiunAwal;
    private javax.swing.JLabel tanggalBerangkat;
    private javax.swing.JLabel tanggalTiba;
    private javax.swing.JPanel ticket;
    // End of variables declaration//GEN-END:variables
}
