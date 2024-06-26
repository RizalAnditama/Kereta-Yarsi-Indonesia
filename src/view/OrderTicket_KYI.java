package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import model.DatabaseConnector;
import controller.Controller;
import com.toedter.calendar.JCalendar;
import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author mraih
 * @author Muhammad Rizal Anditama Nugraha
 */
public class OrderTicket_KYI extends javax.swing.JFrame {

    /**
     * Creates new form OrderTicket_KYI
     */
    Controller controller = Controller.getInstance();
    int stationAmount = controller.getRowCount("station");
    String[] stationName = new String[stationAmount];
    String[] stationCode = new String[stationAmount];
    String[] stationCity = new String[stationAmount];

    /**
     * Creates new form OrderTicket_KYI
     */
    public OrderTicket_KYI() {
        initComponents();
        tanggalPergi.setDateFormatString("dd-MM-yyyy");
        tanggalPulang.setDateFormatString("dd-MM-yyyy");
        tanggalPergi.getJCalendar().setMinSelectableDate(new Date());
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 102, 51, 255));
        setExtendedState(MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(jPanel1, gbc);
        pPulang.setVisible(false);
        
        try {
            controller.setRs(controller.findAll("station"));
            int i = 0;
            while (controller.getRs().next()) {
                stationCode[i] = controller.getRs().getString("station_code");
                stationName[i] = controller.getRs().getString("name");
                stationCity[i] = controller.getRs().getString("city");
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderTicket_KYI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        asal.removeAllItems();
        tujuan.removeAllItems();
        asal.addItem("Pilih Stasiun Keberangkatan");
        tujuan.addItem("Pilih Stasiun Tujuan");
        for (int i = 0; i < stationAmount; i++) {
            String nameFormat = stationName[i] + " - " + stationCity[i];
            asal.addItem(nameFormat);
            tujuan.addItem(nameFormat);
        }
    }
    
    void setPenumpang(String text) {
        jLabel12.setText(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        asal = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        tujuan = new javax.swing.JComboBox<>();
        pPergi = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pulangPergi = new javax.swing.JCheckBox();
        jLabel14 = new javax.swing.JLabel();
        tanggalPergi = new com.toedter.calendar.JDateChooser();
        pPassanger = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        pPulang = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        tanggalPulang = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Icon KYI.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 102, 51));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Logo Header KYI.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 59, 29));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tiket Kereta Yarsi");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Asal");

        asal.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        asal.setForeground(new java.awt.Color(255, 255, 255));
        asal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gambir - Jakarta", "Bojong Soang - Bandung", "Gubeng - Surabaya" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Tujuan");

        jButton1.setText("Tukar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tujuan.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        tujuan.setForeground(new java.awt.Color(255, 255, 255));
        tujuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gambir - Jakarta", "Bojong Soang - Bandung", "Gubeng - Surabaya" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(asal, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))))
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(97, 97, 97))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(asal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(325, 325, 325))
        );

        pPergi.setBackground(new java.awt.Color(0, 59, 29));
        pPergi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pPergi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pPergiMouseClicked(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Tanggal Pergi");

        pulangPergi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pulangPergiActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Pulang Pergi");

        tanggalPergi.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tanggalPergiPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout pPergiLayout = new javax.swing.GroupLayout(pPergi);
        pPergi.setLayout(pPergiLayout);
        pPergiLayout.setHorizontalGroup(
            pPergiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPergiLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(pPergiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pPergiLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addGroup(pPergiLayout.createSequentialGroup()
                        .addComponent(tanggalPergi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pulangPergi)))
                .addGap(16, 16, 16))
        );
        pPergiLayout.setVerticalGroup(
            pPergiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPergiLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pPergiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pPergiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pulangPergi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tanggalPergi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pPassanger.setBackground(new java.awt.Color(0, 59, 29));
        pPassanger.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pPassanger.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pPassangerMouseClicked(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Penumpang");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("1 Dewasa");

        javax.swing.GroupLayout pPassangerLayout = new javax.swing.GroupLayout(pPassanger);
        pPassanger.setLayout(pPassangerLayout);
        pPassangerLayout.setHorizontalGroup(
            pPassangerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPassangerLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(pPassangerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pPassangerLayout.setVerticalGroup(
            pPassangerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPassangerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setText("CARI TIKET KERETA YARSI");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        pPulang.setBackground(new java.awt.Color(0, 59, 29));
        pPulang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pPulang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pPulangMouseClicked(evt);
            }
        });

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Tanggal Pulang");

        tanggalPulang.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tanggalPulangPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout pPulangLayout = new javax.swing.GroupLayout(pPulang);
        pPulang.setLayout(pPulangLayout);
        pPulangLayout.setHorizontalGroup(
            pPulangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPulangLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(pPulangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggalPulang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pPulangLayout.setVerticalGroup(
            pPulangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPulangLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalPulang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel1)
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pPassanger, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pPergi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pPulang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pPergi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(pPulang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pPassanger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pPassangerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pPassangerMouseClicked
        // TODO add your handling code here:
        AddPassengers_KYI passengers = new AddPassengers_KYI(OrderTicket_KYI.this);
        passengers.setVisible(true);
    }//GEN-LAST:event_pPassangerMouseClicked

    private void pulangPergiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pulangPergiActionPerformed
        // TODO add your handling code here:
        if (pulangPergi.isSelected()) {
            pPulang.setVisible(true);
        } else {
            pPulang.setVisible(false);
        }
    }//GEN-LAST:event_pulangPergiActionPerformed

    private void pPergiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pPergiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pPergiMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String asal = String.valueOf(this.asal.getSelectedItem()).trim();
        String tujuan = String.valueOf(this.tujuan.getSelectedItem()).trim();
        
        if (asal.equals(tujuan)) {
            controller.infoMessage("Anda tidak dapat pergi dari dan ke stasiun yang sama!");
        } else if (asal.equalsIgnoreCase("Pilih Stasiun Keberangkatan")) {
            controller.infoMessage("Harap pilih stasiun keberangkatan anda!");
        } else if (tujuan.equalsIgnoreCase("Pilih Stasiun Tujuan")) {
            controller.infoMessage("Harap pilih stasiun tujuan anda!");
        } else if (tanggalPergi.getDate() == null) {
            controller.infoMessage("Harap pilih tanggal keberangkatan anda!");
        } else if (pulangPergi.isSelected() && tanggalPulang.getDate() == null) {
            controller.infoMessage("Harap pilih tanggal pulang anda!");
        } else {
            controller.setFirstStuff(asal.split(" - ")[0].trim(), tujuan.split(" - ")[0].trim(), tanggalPergi.getDate(), tanggalPulang.getDate());
            this.dispose();
            new SelectTrain_KYI().setVisible(true);
//            new PilihKereta_KYI(controller.findSpecificTrain(asal.split(" - ")[0], tujuan.split(" - ")[0], controller.getTanggalPergi())).setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void pPulangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pPulangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pPulangMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Object pastAsal = asal.getSelectedItem();
        asal.setSelectedItem(tujuan.getSelectedItem());
        tujuan.setSelectedItem(pastAsal);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tanggalPergiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tanggalPergiPropertyChange
        Date startDate = tanggalPergi.getDate();
        Date endDate = tanggalPulang.getDate();
        tanggalPulang.getJCalendar().setMinSelectableDate(startDate);
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            controller.infoMessage("Waktu berangkat tidak bisa setelah waktu pulang!");
            tanggalPulang.setDate(startDate);
        }
    }//GEN-LAST:event_tanggalPergiPropertyChange

    private void tanggalPulangPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tanggalPulangPropertyChange
        if ("date".equals(evt.getPropertyName())) {
            Date startDate = tanggalPergi.getDate();
            Date endDate = tanggalPulang.getDate();
            if (startDate != null && endDate != null && endDate.before(startDate)) {
                controller.infoMessage("Waktu pulang tidak bisa mendahului waktu berangkat!");
                tanggalPulang.setDate(startDate);
            }
        }
    }//GEN-LAST:event_tanggalPulangPropertyChange

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
            java.util.logging.Logger.getLogger(OrderTicket_KYI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderTicket_KYI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderTicket_KYI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderTicket_KYI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderTicket_KYI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> asal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel pPassanger;
    private javax.swing.JPanel pPergi;
    private javax.swing.JPanel pPulang;
    private javax.swing.JCheckBox pulangPergi;
    private com.toedter.calendar.JDateChooser tanggalPergi;
    private com.toedter.calendar.JDateChooser tanggalPulang;
    private javax.swing.JComboBox<String> tujuan;
    // End of variables declaration//GEN-END:variables
}
