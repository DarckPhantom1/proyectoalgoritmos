package formularios;

import conexionSQL.conexionSQL;
import static formularios.login.nombreUsuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.time.LocalDate;

public class formcompras extends javax.swing.JFrame {
    conexionSQL cc = new conexionSQL();
    Connection con = cc.conexion();
    String usuario=nombreUsuario.toUpperCase();
    
    
     public void filtrardatos(String valor){
      String[] titulos= {"ID COMPRA","PRODUCTO","MARCA","UNIDAD MEDIDA","PROVEEDOR","PRECIO COMPRA","UNI COMPRA","TOTAL","USUARIO","FECHA"};
      String[]registros= new String[10];
      DefaultTableModel modelo=new DefaultTableModel(null,titulos);
      String SQL ="select idcompra,producto,marca,unidadmedida,proveedor,preciou,unidadescompra,total,usuario,fecha from compras c where activo ='Si' and producto like '%"+valor+"%'";
      
      try {
          
          Statement st=con.createStatement();
          ResultSet rs=st.executeQuery(SQL) ;
          while (rs.next()){
             registros[0]=rs.getString("idcompra");
             registros[1]=rs.getString("producto");
             registros[2]=rs.getString("marca");
             registros[3]=rs.getString("unidadmedida");
             registros[4]=rs.getString("proveedor");
             registros[5]=rs.getString("preciou");
             registros[6]=rs.getString("unidadescompra");
             registros[7]=rs.getString("total");
             registros[8]=rs.getString("usuario");
             registros[9]=rs.getString("fecha");
          
             modelo.addRow(registros);
          }
          tblUsuarios.setModel(modelo);
      } catch (Exception e){
          JOptionPane.showMessageDialog(null,"Error Registro "+e.getMessage());  
      }
  }
    
  public void mostrardatos(){
      String[] titulos= {"ID COMPRA","PRODUCTO","MARCA","UNIDAD MEDIDA","PROVEEDOR","PRECIO COMPRA","UNI COMPRA","TOTAL","USUARIO","FECHA"};
      String[]registros= new String[10];
      DefaultTableModel modelo=new DefaultTableModel(null,titulos);
      String SQL ="select idcompra,producto,marca,unidadmedida,proveedor,preciou,unidadescompra,total,usuario,fecha from compras c where activo ='Si'";
      
      try {
          
          Statement st=con.createStatement();
          ResultSet rs=st.executeQuery(SQL) ;
          while (rs.next()){
             registros[0]=rs.getString("idcompra");
             registros[1]=rs.getString("producto");
             registros[2]=rs.getString("marca");
             registros[3]=rs.getString("unidadmedida");
             registros[4]=rs.getString("proveedor");
             registros[5]=rs.getString("preciou");
             registros[6]=rs.getString("unidadescompra");
             registros[7]=rs.getString("total");
             registros[8]=rs.getString("usuario");
             registros[9]=rs.getString("fecha");
          
             modelo.addRow(registros);
          }
          tblUsuarios.setModel(modelo);
      } catch (Exception e){
          JOptionPane.showMessageDialog(null,"Error Registro "+e.getMessage());  
      }
  }
  
   public void llenarSelectmarca(){
   String SQL = "select nombre from marca s where activo='Si'";

try {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(SQL);
    
    while (rs.next()) {
        String nombre = rs.getString("nombre");
        jComboBox1.addItem(nombre);
   
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error al llenar el combo box: " + e.getMessage());
}
  }
  
   public void llenarSelectproducto(){
   String SQL = "select nombre from producto p where activo ='Si'";

try {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(SQL);
    
    while (rs.next()) {
        String nombre = rs.getString("nombre");
        jComboBox3.addItem(nombre);
   
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error al llenar el combo box: " + e.getMessage());
}
  }
   
     public void llenarSelectproveedor(){
   String SQL = "select nombre from proveedores  where activo ='Si'";

try {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(SQL);
    
    while (rs.next()) {
        String nombre = rs.getString("nombre");
        jComboBox4.addItem(nombre);
   
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error al llenar el combo box: " + e.getMessage());
}
  }
  
public void llenarunidadmedida(){
   String SQL = "select idmedida, nombre from unidadmedida u  where activo ='Si'";

try {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(SQL);
    
    while (rs.next()) {
        String nombre = rs.getString("nombre");
        jComboBox2.addItem(nombre);
   
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error al llenar el combo box: " + e.getMessage());
}
  }
    

    public formcompras() {
        initComponents();
        mostrardatos();
        llenarSelectmarca();
        llenarunidadmedida();
        llenarSelectproducto();
        llenarSelectproveedor();
        this.setLocationRelativeTo(null);
           setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
          addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(null, 
                    "¿Estás seguro de que deseas cerrar este formulario?", 
                    "Confirmar cierre", 
                    JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    setVisible(false); // Oculta el formulario
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    public void eliminarRegistros(){
              
           int filaSeleccionada=tblUsuarios.getSelectedRow();
       try{
                           
          String SQL="update compras set activo ='No' where idcompra="+tblUsuarios.getValueAt(filaSeleccionada, 0);
    
          Statement st=con.createStatement();
          int n=st.executeUpdate(SQL);
            
          if(n>=0){
           JOptionPane.showMessageDialog( null,"Registro Eliminado Correctamente");   
           limpiarDatos();
          }
                      
        }catch (Exception e){
          JOptionPane.showMessageDialog(null,"Error Eliminando Registro "+e.getMessage());
            
        }
    }
  
    
    public void limpiarDatos(){
        txtNit.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }
        
       
     public void insertarDatos(){
    
       try{
           String precio = txtPrecio.getText();
            String cantidad = txtCantidad.getText();
           String Activo="Si";
           LocalDate fechaActual= LocalDate.now();
            int cantidad1 = Integer.parseInt(txtCantidad.getText());
            double precio1 = Double.parseDouble(txtPrecio.getText());
            double total = cantidad1 * precio1;
           
           if (jComboBox1.getSelectedItem() == null || jComboBox2.getSelectedItem() == null || jComboBox3.getSelectedItem() == null || jComboBox4.getSelectedItem() == null ) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una opción en ambos combo boxes");
            return;
        }
            if (precio == null || precio.trim().isEmpty()|| cantidad == null || cantidad.trim().isEmpty()) {
            JOptionPane.showMessageDialog( null,"Nombre de Producto no puede ser vacio");
           
             
           }else{
               
        //   String passwordBase64 = Base64.getEncoder().encodeToString(nusuario.getBytes("UTF-8"));
          String SQL="insert into compras (producto,marca,unidadmedida,proveedor,preciou,unidadescompra,total,usuario,fecha,activo) values(?,?,?,?,?,?,?,?,?,?)";
          
           PreparedStatement pst = con.prepareStatement(SQL);
          
           pst.setString(1, jComboBox3.getSelectedItem().toString()); 
           pst.setString(2, jComboBox1.getSelectedItem().toString());
           pst.setString(3, jComboBox2.getSelectedItem().toString());
           pst.setString(4, jComboBox4.getSelectedItem().toString());
           pst.setString(5, txtPrecio.getText());
           pst.setString(6, txtCantidad.getText());
           pst.setDouble(7, total);
           pst.setString(8, usuario);
           pst.setDate(9, Date.valueOf(fechaActual));
           pst.setString(10, Activo);
         
 
           pst.execute();
           
           JOptionPane.showMessageDialog( null,"Regustro Guardado con exito");
           mostrardatos();
           limpiarDatos();
           }
        }catch (Exception e){
          JOptionPane.showMessageDialog(null,"Error Registro "+e.getMessage());
            
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtNit = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Guardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtNit.setEditable(false);
        txtNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNitKeyReleased(evt);
            }
        });

        jLabel2.setText("Producto");

        Guardar.setText("Guardar");
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });

        jLabel1.setText("ID");

        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });

        jLabel4.setText("Precio U Compra");

        jButton2.setText("Nuevo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Marca");

        jLabel6.setText("Unidad Medida");

        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel5.setText("Proveedor");

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        jLabel7.setText("Unidades Compra");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCantidad))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Guardar)
                .addGap(82, 82, 82))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Guardar)
                    .addComponent(jButton2))
                .addGap(91, 91, 91))
        );

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblUsuarios);

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel8.setText("Buscar compra por producto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel8)
                        .addGap(118, 118, 118)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        insertarDatos();
        limpiarDatos();
        mostrardatos();
        

       
    }//GEN-LAST:event_GuardarActionPerformed

    private void txtNitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitKeyReleased
  // TODO add your handling code here:
    }//GEN-LAST:event_txtNitKeyReleased

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked

  int filaSeleccionada=tblUsuarios.rowAtPoint(evt.getPoint());       // TODO add your handling code here:
    //   txtNit.setText(tblUsuarios.getValueAt(filaSeleccionada, 0).toString());
   
      // txtPrecio.setText(tblUsuarios.getValueAt(filaSeleccionada, 4).toString());
       
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    limpiarDatos();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        filtrardatos(txtBuscar.getText());
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarKeyReleased

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
            java.util.logging.Logger.getLogger(formcompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formcompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formcompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formcompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formcompras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Guardar;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
