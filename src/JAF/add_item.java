/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAF;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Megi
 */
public class add_item extends javax.swing.JPanel {

    /**
     * Creates new form add_items
     */
    String Category;
    String Sort;
    String filename = null;
    byte[] item_image = null;
    private ImageIcon format = null;
    public add_item() {
        initComponents();
        cancel_btn.setVisible(true);
        create_btn.setVisible(true);
        update_btn.setVisible(false);
        delete_btn.setVisible(false);
        load_category();
        load_sort();
     
    }
  public void load_category(){
       try {
           Statement s = database.mycon().createStatement();
            ResultSet rs=s.executeQuery("select * from category");
                while(rs.next()){
                    String Cate=rs.getString("category");
                    category_selector.addItem(Cate);
                }
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  public void load_sort(){
      try{
          Statement s = database.mycon().createStatement();
          ResultSet rs=s.executeQuery("SELECT * FROM sort");
          while(rs.next()){
              String Sort = rs.getString("sort");
              sort_selector.addItem(Sort);
          }
      }catch(Exception ex){
          Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
 
  public void cancel(){
      // making null labels, coboBoxes.
      item_name_input.setText(null);
      category_selector.setSelectedIndex(0);
      sort_selector.setSelectedIndex(0);
      buying_price_input.setText(null);
      total_amount_input.setText(null);
      imagelabel.setIcon(null);
      search_input.setText(null);
      proft_input.setText(null);
      price_sale.setText(null);
      // making dissable and enable for automation buttons.
                   update_btn.setVisible(false);
                  delete_btn.setVisible(false);
                  create_btn.setVisible(true);
  }
  public void Add_Items(){
       String Item_Name = item_name_input.getText();
       Category = category_selector.getSelectedItem().toString();
       Sort = sort_selector.getSelectedItem().toString();
       String Buying_Price = buying_price_input.getText();
       String Total_Amount = total_amount_input.getText();
       String Profit = proft_input.getText();
       String Price_sale = price_sale.getText();
       Icon Image = imagelabel.getIcon();
        
       if(Item_Name.equals("")){
           JOptionPane.showMessageDialog(this, "Enter Item Name");
       }else if(Buying_Price.equals("")){
           JOptionPane.showMessageDialog(this, "Enter buying price");
       }else if(Total_Amount.equals("")){
           JOptionPane.showMessageDialog(this, "Enter Total Amount");
       }else if(Profit.equals("")){
             JOptionPane.showMessageDialog(this, "Enter profit in percent");
       }else if(Price_sale.equals("")){
            JOptionPane.showMessageDialog(this, "price for sale is not generated");
       }
       else{
           try{
                 Statement s=database.mycon().createStatement();
                   s.executeUpdate("INSERT INTO items (item_name,category,sort,buying_price,total_amount,image,profit_percent,price_sale) VALUES('"+Item_Name+"','"+Category+"','"+Sort+"','"+Buying_Price+"','"+Total_Amount+"','"+Image+"','"+Profit+"', '"+Price_sale+"')");
                   JOptionPane.showMessageDialog(this, "Item Addes !");
                   cancel();
                   Dashboard d = new Dashboard();
                   
           }catch(Exception ex){
                Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
       
  }
  public void search_by_id(){
                String Search = search_input.getText();
        try {
            Statement stmt=database.mycon().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items WHERE id = '"+Search+"'");
            if(rs.next()){
                item_name_input.setText(rs.getString("item_name"));
                buying_price_input.setText(rs.getString("buying_price"));
                  total_amount_input.setText(rs.getString("total_amount"));
                  category_selector.setSelectedItem(rs.getString("category"));
                  
                  sort_selector.setSelectedItem(rs.getString("sort"));
                 byte[] img = rs.getBytes("image");
                 ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(imagelabel.getWidth(), imagelabel.getHeight(), Image.SCALE_SMOOTH));
                     imagelabel.setIcon(imageIcon);
                     
                    proft_input.setText(rs.getString("profit_percent"));
                    price_sale.setText(rs.getString("price_sale"));
                 
                  update_btn.setVisible(true);
                  delete_btn.setVisible(true);
                  create_btn.setVisible(false);
             
                 
                  
                  
            }
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
             /*  if(Search.equals("")){
                    usernameinput.setText("");
        buttonGroup1.clearSelection();
        useremailinput.setText("");
        userpassinput.setText("");
        userphoneinput.setText("");
        position.setSelectedIndex(0);
                   delete_btn.setVisible(false);
                   update_btn.setVisible(false);
                   create_btn.setVisible(true);
               }else if(!Search.equals("")){
                   create_btn.setVisible(false);
                    delete_btn.setVisible(true);
                   update_btn.setVisible(true); 
               }*/
  }
  public void load_image(){
      JFileChooser chooser = new JFileChooser();
      chooser.showOpenDialog(null);
      File f= chooser.getSelectedFile();
      filename = f.getAbsolutePath();
      ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(imagelabel.getWidth(), imagelabel.getHeight(), Image.SCALE_SMOOTH));
      imagelabel.setIcon(imageIcon);
      try{
          File image = new File(filename);
          FileInputStream fis = new FileInputStream(image);
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          byte[] buf = new byte[1024];
          
             for(int number;(number = fis.read(buf))!= -1;){
                 bos.write(buf, 0, number);
                 
          
      }
             item_image = bos.toByteArray();
             
      }catch(Exception e){
          
      }
      
  }
  public void edit_items(){
        int x = JOptionPane.showConfirmDialog(null, "Do you Want To Update this! ?" , "Update Items ",JOptionPane.YES_NO_OPTION);
       if(x==0){
        String Search = search_input.getText();
         String Item_Name = item_name_input.getText();
       Category = category_selector.getSelectedItem().toString();
       Sort = sort_selector.getSelectedItem().toString();
       String Buying_Price = buying_price_input.getText();
       String Total_Amount = total_amount_input.getText();
       String Persent = proft_input.getText();
       String price = price_sale.getText();
       //Icon Image = imagelabel.getIcon();
         try{
             Statement s = database.mycon().createStatement();
             s.executeUpdate("UPDATE items SET item_name ='"+Item_Name+"', category ='"+Category+"', sort ='"+Sort+"', buying_price ='"+Buying_Price+"', total_amount= '"+Total_Amount+"', profit_percent= '"+Persent+"', price_sale= '"+price+"'  WHERE id = '"+Search+"'");
             JOptionPane.showMessageDialog(this, "Item data Updated" +Item_Name);
            cancel();
        
         }catch(Exception e){
             System.out.print(e);
             
         }
       }
  }
  public void remove_items(){
       int x = JOptionPane.showConfirmDialog(null, "Do you Want To delet this! ?", "Delete Items",JOptionPane.YES_NO_OPTION);
       if(x==0){
        String Search = search_input.getText();
        try{
            Statement s = database.mycon().createStatement();
            s.executeUpdate("DELETE FROM items WHERE id = '"+Search+"'");
            JOptionPane.showMessageDialog(this, "Item Deleted");
            
              cancel();
        }catch(Exception e){
              System.out.print(e);
        }
  }
  
  }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        item_name_input = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        category_selector = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        sort_selector = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        buying_price_input = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        total_amount_input = new javax.swing.JTextField();
        immage_attacher = new javax.swing.JLabel();
        cancel_btn = new javax.swing.JButton();
        create_btn = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        search_input = new javax.swing.JTextField();
        imagelabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        proft_input = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        price_sale = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(25, 150, 130));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_open_box_100px.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, -10, 130, 110));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Items");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, 110, 40));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Item Name:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 80, 30));

        item_name_input.setBackground(new java.awt.Color(25, 150, 130));
        item_name_input.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        item_name_input.setForeground(new java.awt.Color(255, 255, 255));
        item_name_input.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        item_name_input.setCaretColor(new java.awt.Color(255, 153, 204));
        item_name_input.setOpaque(false);
        jPanel1.add(item_name_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 240, 40));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Category");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 80, 30));

        category_selector.setBackground(new java.awt.Color(25, 150, 130));
        category_selector.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        category_selector.setForeground(new java.awt.Color(255, 255, 255));
        category_selector.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(255, 255, 255)));
        category_selector.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        category_selector.setOpaque(false);
        category_selector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                category_selectorItemStateChanged(evt);
            }
        });
        category_selector.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                category_selectorPropertyChange(evt);
            }
        });
        jPanel1.add(category_selector, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 260, 40));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sort");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 80, 30));

        sort_selector.setBackground(new java.awt.Color(25, 150, 130));
        sort_selector.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        sort_selector.setForeground(new java.awt.Color(255, 255, 255));
        sort_selector.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(255, 255, 255)));
        sort_selector.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sort_selector.setOpaque(false);
        sort_selector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                sort_selectorItemStateChanged(evt);
            }
        });
        sort_selector.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                sort_selectorPropertyChange(evt);
            }
        });
        jPanel1.add(sort_selector, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 260, 40));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Buying Price");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 110, 30));

        buying_price_input.setBackground(new java.awt.Color(25, 150, 130));
        buying_price_input.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        buying_price_input.setForeground(new java.awt.Color(255, 255, 255));
        buying_price_input.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        buying_price_input.setCaretColor(new java.awt.Color(255, 153, 204));
        buying_price_input.setOpaque(false);
        jPanel1.add(buying_price_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 240, 40));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total Amount");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 120, 30));

        total_amount_input.setBackground(new java.awt.Color(25, 150, 130));
        total_amount_input.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        total_amount_input.setForeground(new java.awt.Color(255, 255, 255));
        total_amount_input.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        total_amount_input.setCaretColor(new java.awt.Color(255, 153, 204));
        total_amount_input.setOpaque(false);
        jPanel1.add(total_amount_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 240, 40));

        immage_attacher.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        immage_attacher.setForeground(new java.awt.Color(255, 255, 255));
        immage_attacher.setText("click hare to atach image");
        immage_attacher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                immage_attacherMouseClicked(evt);
            }
        });
        jPanel1.add(immage_attacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 210, 30));

        cancel_btn.setBackground(new java.awt.Color(102, 102, 102));
        cancel_btn.setForeground(new java.awt.Color(255, 255, 255));
        cancel_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_cancel_26px.png"))); // NOI18N
        cancel_btn.setText("cancel");
        cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_btnActionPerformed(evt);
            }
        });
        jPanel1.add(cancel_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, 140, 40));

        create_btn.setBackground(new java.awt.Color(102, 102, 102));
        create_btn.setForeground(new java.awt.Color(255, 255, 255));
        create_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_26px_1.png"))); // NOI18N
        create_btn.setText("create");
        create_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_btnActionPerformed(evt);
            }
        });
        jPanel1.add(create_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 580, 140, 40));

        update_btn.setBackground(new java.awt.Color(102, 102, 102));
        update_btn.setForeground(new java.awt.Color(255, 255, 255));
        update_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_update_left_rotation_26px.png"))); // NOI18N
        update_btn.setText("Update");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });
        jPanel1.add(update_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 580, 140, 40));

        delete_btn.setBackground(new java.awt.Color(102, 102, 102));
        delete_btn.setForeground(new java.awt.Color(255, 255, 255));
        delete_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_delete_trash_26px.png"))); // NOI18N
        delete_btn.setText("Delete");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });
        jPanel1.add(delete_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 580, 140, 40));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_50px_1.png"))); // NOI18N
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 120, 70, 50));

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
        jPanel1.add(search_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, 240, 40));
        jPanel1.add(imagelabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 340, 250, 210));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("profit");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 210, 80, 30));

        proft_input.setBackground(new java.awt.Color(25, 150, 130));
        proft_input.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        proft_input.setForeground(new java.awt.Color(255, 255, 255));
        proft_input.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        proft_input.setCaretColor(new java.awt.Color(255, 153, 204));
        proft_input.setOpaque(false);
        proft_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                proft_inputKeyPressed(evt);
            }
        });
        jPanel1.add(proft_input, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, 90, 40));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Price of sale");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 310, 120, 30));

        price_sale.setEditable(false);
        price_sale.setBackground(new java.awt.Color(25, 150, 130));
        price_sale.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        price_sale.setForeground(new java.awt.Color(255, 255, 255));
        price_sale.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        price_sale.setCaretColor(new java.awt.Color(255, 153, 204));
        price_sale.setOpaque(false);
        price_sale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                price_saleKeyPressed(evt);
            }
        });
        jPanel1.add(price_sale, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 340, 160, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1101, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void category_selectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_category_selectorItemStateChanged

    }//GEN-LAST:event_category_selectorItemStateChanged

    private void category_selectorPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_category_selectorPropertyChange

    }//GEN-LAST:event_category_selectorPropertyChange

    private void sort_selectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_sort_selectorItemStateChanged

    }//GEN-LAST:event_sort_selectorItemStateChanged

    private void sort_selectorPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_sort_selectorPropertyChange

    }//GEN-LAST:event_sort_selectorPropertyChange

    private void cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_btnActionPerformed
           cancel();    
       
    }//GEN-LAST:event_cancel_btnActionPerformed

    private void create_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_btnActionPerformed
       Add_Items();
       
    }//GEN-LAST:event_create_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
          edit_items();       
    }//GEN-LAST:event_update_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
              remove_items();       
    }//GEN-LAST:event_delete_btnActionPerformed

    private void search_inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_inputKeyPressed
       
    }//GEN-LAST:event_search_inputKeyPressed

    private void search_inputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_inputKeyTyped
       search_by_id();
    }//GEN-LAST:event_search_inputKeyTyped

    private void immage_attacherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_immage_attacherMouseClicked
     load_image();
    }//GEN-LAST:event_immage_attacherMouseClicked

    private void price_saleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_price_saleKeyPressed
        
    }//GEN-LAST:event_price_saleKeyPressed

    private void proft_inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proft_inputKeyPressed
     if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                           double persent =Double.valueOf(proft_input.getText());
                           double buyingp = Double.valueOf(buying_price_input.getText());
                           double profit;
                           double tot;
                           profit = (persent/100)* buyingp;
                            tot = buyingp + profit;
                            price_sale.setText(String.valueOf(tot));
    }
    }//GEN-LAST:event_proft_inputKeyPressed
  
      
          
 
      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField buying_price_input;
    private javax.swing.JButton cancel_btn;
    private javax.swing.JComboBox<String> category_selector;
    private javax.swing.JButton create_btn;
    private javax.swing.JButton delete_btn;
    private javax.swing.JLabel imagelabel;
    private javax.swing.JLabel immage_attacher;
    private javax.swing.JTextField item_name_input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField price_sale;
    private javax.swing.JTextField proft_input;
    private javax.swing.JTextField search_input;
    private javax.swing.JComboBox<String> sort_selector;
    private javax.swing.JTextField total_amount_input;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables

   
   
}
