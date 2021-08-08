/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JAF;

import java.awt.BorderLayout;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Megi
 */
public class POS extends javax.swing.JFrame {

    /**
     * Creates new form POS
     */
    public POS() {
        initComponents();
         showDate();
         invoice();
         sold.setEnabled(false);
    }

    POS(String User_Name) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          initComponents();
            jLabel1.setText(User_Name);
            showDate();
            invoice();
            sold.setEnabled(false);
    }
    public void search_by_id(){
                String Search = id_search.getText();
        try {
            Statement stmt=database.mycon().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items WHERE id = '"+Search+"'");
            if(rs.next()){
                product_Name.setText(rs.getString("item_name"));
                sort.setText(rs.getString("sort"));
                  price.setText(rs.getString("price_sale"));
                  
            }
        } catch (SQLException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
            
  }
    public void calculate(){
        
           try {
                String Search_id =id_search.getText();
                
                 Statement s = database.mycon().createStatement();
                 ResultSet rs = s.executeQuery("SELECT * FROM items WHERE id = '"+Search_id+"'");
               
                   
                       while(rs.next())
                       {
                           double tqty;
                           tqty=rs.getDouble("total_amount");
                           double Price =Double.valueOf(price.getText());
                           double qty = Double.valueOf(qnty.getText());
                           double tot;
                           tot=Price*qty;
                           
                           if(qty>tqty){
                            
                               JOptionPane.showMessageDialog(this, "Avialivable QTY is" + "=" +tqty);
                               cancel_order_generator();
                               
                           }
                           else{
                               totalorder.setText(String.valueOf(tot));
                           }
                           
                       }
                
            } catch (SQLException ex) {
                Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
            }
                               
    }
    public void cancel_order_generator(){
        id_search.setText(null);
        product_Name.setText(null);
        sort.setText(null);
        price.setText(null);
        qnty.setText(null);
        totalorder.setText(null);
    }
      public void Add_Order(){
         String Product_ID=id_search.getText();
         String Product_Name=product_Name.getText();
         String Sort=sort.getText();
         String Price = price.getText();
         String QNTY = qnty.getText();
         String Total = totalorder.getText();
         if(Product_ID.equals("")){
             JOptionPane.showMessageDialog(this, "Please enter the product Code first !");
         }else if(QNTY.equals("")){
             JOptionPane.showMessageDialog(this, "Please enter the quantity wanted !");
         }else if(Total.equals("")){
              JOptionPane.showMessageDialog(this, "total Amount is not calculated!");
         }
         else{
              DefaultTableModel Model=(DefaultTableModel)jTable1.getModel();   
                    Model.addRow(new Object[]
                    {
                        id_search.getText(),
                        product_Name.getText(),
                        sort.getText(),
                        price.getText(),
                        qnty.getText(),
                        totalorder.getText(),
                    
                    });
                    
               cancel_order_generator();
         }
     }
      public void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-YYYY");
        date.setText(s.format(d));
    }
       public void total_qty_selected(){
      
             int source=jTable1.getRowCount();
             double qty=0;
             for(int i=0; i<source; i++){
                 double value =Double.valueOf(jTable1.getValueAt(i, 4).toString());
                 qty+=value;
             }
             total_qnty_field.setText(Double.toString(qty));
     }
       public void total_Money(){
      
             int source=jTable1.getRowCount();
             double tm=0;
             for(int i=0; i<source; i++){
                 double value =Double.valueOf(jTable1.getValueAt(i, 5).toString());
                 tm+=value;
             }
             total_fee.setText(Double.toString(tm));
     }
       public void cancel_selected_item(){
             try{
        DefaultTableModel dt=(DefaultTableModel)jTable1.getModel();
       int row=jTable1.getSelectedRow();
       dt.removeRow(row);
      }catch(Exception e){
          
      } 
      total_qty_selected();
       total_Money();
       }
       public void cancel_all_order(){
            DefaultTableModel dt=(DefaultTableModel)jTable1.getModel();
             dt.setRowCount(0);
       total_qty_selected();
       total_Money();
       }
       public void tot(){
           
         double total = Double.valueOf(total_fee.getText());
        double Price = Double.valueOf(pay.getText());
         //int Price = Integer.parseInt(pay.getText());

        
         if(Price>=total){
             sold.setEnabled(true);
         }else if(Price<=total){
         sold.setEnabled(false);
       }
       }
       public void invoice(){
        
    
        try {
           
             Statement stmt=database.mycon().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(invid) FROM sold");
            rs.next();
            rs.getString("MAX(invid)");
            if (rs.getString("MAX(invid)") == null) {
                txtid.setText("E-0000001");     
            } else {
                long id = Long.parseLong(rs.getString("MAX(invid)").substring(2, rs.getString("MAX(invid)").length()));
                id++;
                txtid.setText("E-" + String.format("%07d", id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }      
    
       }
       
        public void sales(){
        
        
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyy-MM-dd");
        LocalDateTime now= LocalDateTime.now();
        String date=dtf.format(now);
        
        String Total_Amount=total_fee.getText();
        String Pay=pay.getText();
        String Seller = jLabel1.getText();
        String Inv = txtid.getText();
       
        int lastinserted=0;
        //Tax=tax.getSelectedItem().toString();
        
                 
        try {
            Class.forName("com.mysql.jdbc.Driver");
             Class.forName("com.mysql.jdbc.Driver");
              con=DriverManager.getConnection("jdbc:mysql://192.168.43.128/jaf","Admin","123");
        
                int row=jTable1.getRowCount();
                 String query2="insert into `sold`(`Product_ID`,`Product_Name`,`sort`,`qnty`,`price`,`total`,`date`, `invid`,`seller`)Values(?,?,?,?,?,?,?,?,?)";
                 ps=con.prepareStatement(query2);
                 
                 String Product_Code="";
                 String Product_Name="";
                 String Price="";
                 String Sort="";
                 String Qty="";
                 String Total="";
                 for(int i=0; i < jTable1.getRowCount(); i++){
                     Product_Code=(String)jTable1.getValueAt(i, 0);
                      Product_Name=(String)jTable1.getValueAt(i, 1);
                       Sort=(String)jTable1.getValueAt(i, 2);
                        Price=(String)jTable1.getValueAt(i, 3);
                         Qty=(String)jTable1.getValueAt(i, 4);
                          Total=(String)jTable1.getValueAt(i, 5);
                          
                          ps.setString(1, Product_Code);
                          ps.setString(2,Product_Name);
                          ps.setString(3, Sort);
                          ps.setString(4, Qty);
                          ps.setString(5, Price);
                          ps.setString(6, Total);
                          ps.setString(7, date);
                          ps.setString(8, Inv);
                          ps.setString(9, Seller);
                          ps.executeUpdate();
                 }
                 
                 
                 String query3="update items set total_amount= total_amount-? where id=?";
                 ps=con.prepareStatement(query3);
                 
                  for(int i=0; i<jTable1.getRowCount(); i++){
                     Product_Code=(String)jTable1.getValueAt(i, 0);
                     
                         Qty=(String)jTable1.getValueAt(i, 4);
                          
                          
                        
                          ps.setString(2,Product_Code);
                          
                          ps.setString(1, Qty);
                        
                
                       ps.execute();
                     
                 }
                 
                 
                ps.addBatch();
                JOptionPane.showMessageDialog(this, "Record Saved !");
                        print_recipt();
                        invoice();
                        
                         cancel_all_order();
                         cancel_selected_item();
                         cancel_order_generator();
                
                
                
                
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        

        
    }
        public void print_recipt(){
            String get_Inv = txtid.getText();
            HashMap a=new HashMap();
         a.put("INVOICE", get_Inv);
         try {
            JasperDesign jdesign= JRXmlLoader.load("src\\JAF\\receipt1.jrxml");
            JasperReport jreport= JasperCompileManager.compileReport(jdesign);
            
            JasperPrint jprint= JasperFillManager.fillReport(jreport, a, con);
            
             JasperPrintManager.printReport(jprint, true);
              if(jprint.equals("")){
                  JOptionPane.showMessageDialog(null, "Please check your connection");
              }else if(!jprint.equals("")){
                    JOptionPane.showMessageDialog(null, "Please wait ...");
              }

            
        } catch (JRException ex) {
            Logger.getLogger(POS.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
       
       
       
       
       
       
       
       
       
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        exit = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtid = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        id_search = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        product_Name = new javax.swing.JTextField();
        sort = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        price = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        qnty = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        totalorder = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        add = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        total_qnty_field = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        total_fee = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        pay = new javax.swing.JTextField();
        cancel_all_process = new javax.swing.JButton();
        sold = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(25, 150, 130));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        exit.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        exit.setForeground(new java.awt.Color(255, 0, 0));
        exit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exit.setText("X");
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Name");

        date.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        date.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("User");

        txtid.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtid.setForeground(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Invoice:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exit))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 452, Short.MAX_VALUE)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(207, 207, 207))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(821, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(289, 289, 289)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(exit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 90));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("ID");

        id_search.setBackground(new java.awt.Color(204, 204, 204));
        id_search.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        id_search.setForeground(new java.awt.Color(0, 0, 0));
        id_search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        id_search.setCaretColor(new java.awt.Color(255, 153, 204));
        id_search.setOpaque(false);
        id_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                id_searchKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Product Name");

        product_Name.setEditable(false);
        product_Name.setBackground(new java.awt.Color(204, 204, 204));
        product_Name.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        product_Name.setForeground(new java.awt.Color(0, 0, 0));
        product_Name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        product_Name.setCaretColor(new java.awt.Color(255, 153, 204));
        product_Name.setOpaque(false);
        product_Name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                product_NameKeyPressed(evt);
            }
        });

        sort.setEditable(false);
        sort.setBackground(new java.awt.Color(204, 204, 204));
        sort.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        sort.setForeground(new java.awt.Color(0, 0, 0));
        sort.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        sort.setCaretColor(new java.awt.Color(255, 153, 204));
        sort.setOpaque(false);
        sort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sortKeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Sort");

        price.setEditable(false);
        price.setBackground(new java.awt.Color(204, 204, 204));
        price.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        price.setForeground(new java.awt.Color(0, 0, 0));
        price.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        price.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        price.setCaretColor(new java.awt.Color(255, 153, 204));
        price.setOpaque(false);
        price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                priceKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Price");

        qnty.setBackground(new java.awt.Color(204, 204, 204));
        qnty.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        qnty.setForeground(new java.awt.Color(51, 51, 51));
        qnty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        qnty.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        qnty.setCaretColor(new java.awt.Color(255, 153, 204));
        qnty.setOpaque(false);
        qnty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                qntyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qntyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qntyKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("QNTY");

        totalorder.setEditable(false);
        totalorder.setBackground(new java.awt.Color(204, 204, 204));
        totalorder.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        totalorder.setForeground(new java.awt.Color(0, 0, 0));
        totalorder.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalorder.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        totalorder.setCaretColor(new java.awt.Color(255, 153, 204));
        totalorder.setOpaque(false);
        totalorder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                totalorderKeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Total");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(id_search, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(product_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(sort))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(price))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qnty, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalorder, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(totalorder, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(qnty, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(sort, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(product_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 1000, -1));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_26px.png"))); // NOI18N
        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_cancel_26px.png"))); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancel, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(add)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 100, 160, 110));

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Sort", "Price", "Qnty", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 780, 430));

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TOTAL QNTY");

        total_qnty_field.setEditable(false);
        total_qnty_field.setBackground(new java.awt.Color(204, 204, 204));
        total_qnty_field.setForeground(new java.awt.Color(0, 0, 0));
        total_qnty_field.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TOTAL AMOUNT");

        total_fee.setEditable(false);
        total_fee.setBackground(new java.awt.Color(204, 204, 204));
        total_fee.setForeground(new java.awt.Color(0, 0, 0));
        total_fee.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("PAID");

        pay.setBackground(new java.awt.Color(204, 204, 204));
        pay.setForeground(new java.awt.Color(0, 0, 0));
        pay.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                payKeyReleased(evt);
            }
        });

        cancel_all_process.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_cancel_26px.png"))); // NOI18N
        cancel_all_process.setText("Cancel");
        cancel_all_process.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_all_processActionPerformed(evt);
            }
        });

        sold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_26px_1.png"))); // NOI18N
        sold.setText("Sold");
        sold.setEnabled(false);
        sold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                soldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
            .addComponent(pay)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancel_all_process, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(total_qnty_field)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(total_fee)
                    .addComponent(sold, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(total_qnty_field, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(total_fee, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pay, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(sold)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancel_all_process)
                .addContainerGap())
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 240, 270, 430));

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_cancel_26px.png"))); // NOI18N
        jButton1.setText("Remove All");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_cancel_26px.png"))); // NOI18N
        jButton2.setText("Remove");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(17, 17, 17))
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 670, 160, 130));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
      PreparedStatement ps;
      Connection con;
    private void exitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitMouseClicked

    private void product_NameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_product_NameKeyPressed

    }//GEN-LAST:event_product_NameKeyPressed

    private void sortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sortKeyPressed

    }//GEN-LAST:event_sortKeyPressed

    private void priceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceKeyPressed

    }//GEN-LAST:event_priceKeyPressed

    private void qntyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qntyKeyPressed
                          //calculate();
    }//GEN-LAST:event_qntyKeyPressed

    private void totalorderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalorderKeyPressed

    }//GEN-LAST:event_totalorderKeyPressed

    private void id_searchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_id_searchKeyTyped
        search_by_id();
    }//GEN-LAST:event_id_searchKeyTyped

    private void qntyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qntyKeyReleased
        calculate();
    }//GEN-LAST:event_qntyKeyReleased

    private void qntyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qntyKeyTyped
 
    }//GEN-LAST:event_qntyKeyTyped

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        cancel_order_generator();
    }//GEN-LAST:event_cancelActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
     Add_Order();
     total_qty_selected();
     total_Money();
    }//GEN-LAST:event_addActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      cancel_selected_item();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     cancel_all_order();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cancel_all_processActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_all_processActionPerformed
       cancel_all_order();
         cancel_selected_item();
             cancel_order_generator();
    }//GEN-LAST:event_cancel_all_processActionPerformed

    private void payKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_payKeyReleased
       tot();
    }//GEN-LAST:event_payKeyReleased

    private void soldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soldActionPerformed
      sales();
    }//GEN-LAST:event_soldActionPerformed

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
            java.util.logging.Logger.getLogger(POS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new POS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton cancel;
    private javax.swing.JButton cancel_all_process;
    private javax.swing.JLabel date;
    private javax.swing.JLabel exit;
    private javax.swing.JTextField id_search;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField pay;
    private javax.swing.JTextField price;
    private javax.swing.JTextField product_Name;
    private javax.swing.JTextField qnty;
    private javax.swing.JButton sold;
    private javax.swing.JTextField sort;
    private javax.swing.JTextField total_fee;
    private javax.swing.JTextField total_qnty_field;
    private javax.swing.JTextField totalorder;
    private javax.swing.JLabel txtid;
    // End of variables declaration//GEN-END:variables
}
