/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAF;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Megi
 */
public class position extends javax.swing.JPanel {

    /**
     * Creates new form sig_up
     */
    public position() {
        initComponents();
        cancel_btn.setVisible(true);
        create_btn.setVisible(true);
        update_btn.setVisible(false);
        delete_btn.setVisible(false);
      
       load_table();
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        create_btn = new javax.swing.JButton();
        cancel_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        sort_input = new javax.swing.JTextField();
        delete_btn = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        search_input = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(25, 150, 130));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        create_btn.setBackground(new java.awt.Color(102, 102, 102));
        create_btn.setForeground(new java.awt.Color(255, 255, 255));
        create_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_26px_1.png"))); // NOI18N
        create_btn.setText("create");
        create_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_btnActionPerformed(evt);
            }
        });
        jPanel1.add(create_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, 140, 40));

        cancel_btn.setBackground(new java.awt.Color(102, 102, 102));
        cancel_btn.setForeground(new java.awt.Color(255, 255, 255));
        cancel_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_cancel_26px.png"))); // NOI18N
        cancel_btn.setText("cancel");
        cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_btnActionPerformed(evt);
            }
        });
        jPanel1.add(cancel_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 140, 40));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Position");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 110, 40));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Position");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, 30));

        sort_input.setBackground(new java.awt.Color(25, 150, 130));
        sort_input.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        sort_input.setForeground(new java.awt.Color(255, 255, 255));
        sort_input.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        sort_input.setCaretColor(new java.awt.Color(255, 153, 204));
        sort_input.setOpaque(false);
        jPanel1.add(sort_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 240, 40));

        delete_btn.setBackground(new java.awt.Color(102, 102, 102));
        delete_btn.setForeground(new java.awt.Color(255, 255, 255));
        delete_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_delete_trash_26px.png"))); // NOI18N
        delete_btn.setText("Delete");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });
        jPanel1.add(delete_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 140, 40));

        update_btn.setBackground(new java.awt.Color(102, 102, 102));
        update_btn.setForeground(new java.awt.Color(255, 255, 255));
        update_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_update_left_rotation_26px.png"))); // NOI18N
        update_btn.setText("Update");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });
        jPanel1.add(update_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 390, 140, 40));

        search_input.setBackground(new java.awt.Color(25, 150, 130));
        search_input.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        search_input.setForeground(new java.awt.Color(255, 255, 255));
        search_input.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        search_input.setCaretColor(new java.awt.Color(255, 153, 204));
        search_input.setOpaque(false);
        search_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                search_inputKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                search_inputKeyTyped(evt);
            }
        });
        jPanel1.add(search_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 240, 40));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_50px_1.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 70, 50));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Position"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, 380, 220));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 921, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
   
    
       
        public void add_position(){
            String sort = sort_input.getText();
            if(sort.equals("")){
                JOptionPane.showMessageDialog(this, "Please enter the sort type first !");
            }else{
                try{
                    Statement s = database.mycon().createStatement();
                    s.executeUpdate("INSERT INTO position (position) VALUES('"+sort+"')");
                     JOptionPane.showMessageDialog(null, " positon added!"); 
                     sort_input.setText(null);
                     load_table();
                }catch(Exception ex){
                      Logger.getLogger(position.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        public void cancel(){
            sort_input.setText(null);
            search_input.setText(null);
            update_btn.setVisible(false);
            delete_btn.setVisible(false);
            load_table();
        }
           public void load_table(){
           try{
               DefaultTableModel dft = (DefaultTableModel) jTable1.getModel();
               dft.setRowCount(0);
               Statement s = database.mycon().createStatement();
               ResultSet rs = s.executeQuery("SELECT * FROM position");
               
                  while(rs.next()){
                      Vector v = new Vector();
                      v.add(rs.getString(1));
                      v.add(rs.getString(2));
                      dft.addRow(v);
                      
                  }
               
           }catch(Exception e){
               System.out.print(e);
           }
       }
           public void search_inputed_key(){
                DefaultTableModel Df=(DefaultTableModel)jTable1.getModel();
         String Search=search_input.getText().toString();
         TableRowSorter<DefaultTableModel>tr=new TableRowSorter<DefaultTableModel>(Df);
         jTable1.setRowSorter(tr);
         tr.setRowFilter(RowFilter.regexFilter(Search)); 
           }
           public void search_by_id(){
                String Search = search_input.getText();
                String sort = sort_input.getText();
                
        try {
            Statement stmt=database.mycon().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM position WHERE id = '"+Search+"'");
            if(rs.next()){
                sort_input.setText(rs.getString("position"));
                
                  
                  
            }
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
               if(Search.equals("")){
                    sort_input.setText("");
                         delete_btn.setVisible(false);
                   update_btn.setVisible(false);
                   create_btn.setVisible(true);
                   load_table();
               }else if(!Search.equals("")){
                   create_btn.setVisible(false);
                    delete_btn.setVisible(true);
                   update_btn.setVisible(true); 
               }
           }
           public void update_sort(){
                int x = JOptionPane.showConfirmDialog(null, "Do you Want To Update this! ?", "Update position",JOptionPane.YES_NO_OPTION);
       if(x==0){
        String Search = search_input.getText();
        String sort = sort_input.getText();
         try{
             Statement s = database.mycon().createStatement();
             s.executeUpdate("UPDATE position SET position ='"+sort+"' WHERE id = '"+Search+"'");
             JOptionPane.showMessageDialog(this, "Item sort Updated");
             sort_input.setText(null);
             load_table();
         sort_input.setText("");
         search_input.setText("");
            delete_btn.setVisible(false);
            update_btn.setVisible(false);
            create_btn.setVisible(true);
        
         }catch(Exception e){
             System.out.print(e);
             
         }
       }
           }
           public void delete_sort(){
                    int x = JOptionPane.showConfirmDialog(null, "Do you Want To delet this! ?", "Delete sort",JOptionPane.YES_NO_OPTION);
       if(x==0){
        String Search = search_input.getText();
        try{
            Statement s = database.mycon().createStatement();
            s.executeUpdate("DELETE FROM position WHERE id = '"+Search+"'");
            JOptionPane.showMessageDialog(this, "position Deleted");
            sort_input.setText(null);
            load_table();
          sort_input.setText("");
          search_input.setText("");
      
                   delete_btn.setVisible(false);
                   update_btn.setVisible(false);
                   create_btn.setVisible(true);
        }catch(Exception e){
              System.out.print(e);
        }
       }
           }
           public void table_mouse_cliked(){
                DefaultTableModel Df=(DefaultTableModel)jTable1.getModel();
               int SelectedIndex=jTable1.getSelectedRow();
        sort_input.setText(Df.getValueAt(SelectedIndex, 1).toString());
                      update_btn.setVisible(true);
                      delete_btn.setVisible(true);
           }
           
           
  
     
      
    private void create_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_btnActionPerformed
      // add_category function adds the inputed value of category to the database.
      add_position();
      
     
    }//GEN-LAST:event_create_btnActionPerformed

    private void cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_btnActionPerformed
      // cancel function calling for cancel values writen in the space.
      cancel();
    }//GEN-LAST:event_cancel_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
    // for removing the category item
    delete_sort();
    }//GEN-LAST:event_delete_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
     // for updating or editing things
     update_sort();
        
    }//GEN-LAST:event_update_btnActionPerformed

    private void search_inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_inputKeyPressed
  // search_inputed_key();
            search_by_id();
    }//GEN-LAST:event_search_inputKeyPressed

    private void search_inputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_inputKeyTyped
           search_by_id();
             
    }//GEN-LAST:event_search_inputKeyTyped

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // when the table mouse clicked the cliked line shoud display the data on the label of name.
            //  table_mouse_cliked();
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancel_btn;
    private javax.swing.JButton create_btn;
    private javax.swing.JButton delete_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField search_input;
    private javax.swing.JTextField sort_input;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
}

