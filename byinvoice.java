/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAF;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Megi
 */
public class byinvoice extends javax.swing.JPanel {

      Connection con;
   PreparedStatement ps;
   ResultSet rs;
   
    public byinvoice() {
        con = database.mycon();
        initComponents();
        load_inv_no();
       
    }
   
     String INV;
     
    public void load_inv_no(){
       try {
           Statement s = database.mycon().createStatement();
            ResultSet rs=s.executeQuery("select * from sold");
                while(rs.next()){
                    String Cate=rs.getString("invid");
                    invoicebtn.addItem(Cate);
                }
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
      public void loadreport(){
       
        INV = invoicebtn.getSelectedItem().toString();
         
         HashMap a=new HashMap();
         a.put("INVOICE", INV);
      
       
         board.removeAll();
         board.repaint();
         board.revalidate();
         
        try {
            JasperDesign jdesign= JRXmlLoader.load("src\\JAF\\report1.jrxml");
            JasperReport jreport= JasperCompileManager.compileReport(jdesign);
            
            JasperPrint jprint= JasperFillManager.fillReport(jreport, a, con);
            
            JRViewer v= new JRViewer(jprint);
            board.setLayout(new BorderLayout());
            board.add(v);
        } catch (JRException ex) {
            Logger.getLogger(report.class.getName()).log(Level.SEVERE, null, ex);
        }
         
   }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        invoicebtn = new javax.swing.JComboBox<>();
        go = new javax.swing.JButton();
        board = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(25, 150, 130));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Report by Invoice No:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 190, 40));

        invoicebtn.setBackground(new java.awt.Color(25, 150, 130));
        invoicebtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        invoicebtn.setForeground(new java.awt.Color(255, 255, 255));
        invoicebtn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(255, 255, 255)));
        invoicebtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        invoicebtn.setOpaque(false);
        invoicebtn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                invoicebtnItemStateChanged(evt);
            }
        });
        invoicebtn.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                invoicebtnPropertyChange(evt);
            }
        });
        jPanel1.add(invoicebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 260, 40));

        go.setText("Get");
        go.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goActionPerformed(evt);
            }
        });
        jPanel1.add(go, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 130, 40));

        board.setBackground(new java.awt.Color(153, 153, 153));
        board.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        board.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout boardLayout = new javax.swing.GroupLayout(board);
        board.setLayout(boardLayout);
        boardLayout.setHorizontalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1316, Short.MAX_VALUE)
        );
        boardLayout.setVerticalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );

        jPanel1.add(board, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 1320, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1496, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void invoicebtnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_invoicebtnItemStateChanged

    }//GEN-LAST:event_invoicebtnItemStateChanged

    private void invoicebtnPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_invoicebtnPropertyChange

    }//GEN-LAST:event_invoicebtnPropertyChange

    private void goActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goActionPerformed
       loadreport();
    }//GEN-LAST:event_goActionPerformed
   
    

  
     
      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel board;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton go;
    private javax.swing.JComboBox<String> invoicebtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

