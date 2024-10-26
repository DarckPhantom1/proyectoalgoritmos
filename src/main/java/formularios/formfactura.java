package formularios;

import conexionSQL.conexionSQL;
import static formularios.login.nombreUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;




public class formfactura extends javax.swing.JFrame {
    conexionSQL cc = new conexionSQL();
    Connection con = cc.conexion();
    String usuario=nombreUsuario.toUpperCase();
    
    public void setId(String nit) {
        txtId.setText(nit);
    }
  
     public String getId(String id) {
        return txtproducto.getText();
    }
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
    
 public void mostrarDatos() {
    String[] titulos = {"ID PRODUCTO", "PRODUCTO", "MARCA", "UNIDAD MEDIDA", "PRECIO", "CANTIDAD"};
    String[] registros = new String[6];
    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    // Obtener el valor de txtFactura
    String noFactura = txtfactura.getText().trim();
    if (noFactura.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número de factura.");
        return;
    }

    // Preparar la consulta SQL usando el valor dinámico de noFactura
    String SQL = "SELECT idproducto, producto, marca, unidad, precio, cantidad FROM detalelfactura WHERE activa = 'Si' AND nofactura = ?";

    try (PreparedStatement pst = con.prepareStatement(SQL)) {
        pst.setString(1, noFactura);  // Asigna el valor de txtFactura al parámetro en la consulta

        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                registros[0] = rs.getString("idproducto");
                registros[1] = rs.getString("producto");
                registros[2] = rs.getString("marca");
                registros[3] = rs.getString("unidad");
                registros[4] = rs.getString("precio");
                registros[5] = rs.getString("cantidad");

                modelo.addRow(registros);
            }
        }

        // Asignar el modelo actualizado a la tabla
        tblUsuarios.setModel(modelo);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al obtener datos: " + e.getMessage());
    }
}

 
 public void insertarNuevaFactura() {
    try {
        // Obtener valores de los JTextFields
        String noFactura = txtfactura.getText().trim();
        String nit = txtNitCliente.getText().trim();
        String vendedor = jComboBox5.getSelectedItem() != null ? jComboBox5.getSelectedItem().toString() : "Vendedor1";
        String nombre = txtNombreCliente.getText().trim();
        double subtotal = Double.parseDouble(txtSubtotal.getText().trim());
        double iva = Double.parseDouble(txtIva.getText().trim());
        double total = Double.parseDouble(txtTotal.getText().trim());
   
      
      Date fechaActual = new Date(System.currentTimeMillis());

        String SQLInsert = "INSERT INTO factura (nofactura, fechafac, nitcliente, vendedor, activo, nomcliente, subtotal, iva, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstInsert = con.prepareStatement(SQLInsert)) {
            pstInsert.setString(1, noFactura);
            pstInsert.setDate(2, fechaActual);
            pstInsert.setString(3, nit);
            pstInsert.setString(4, vendedor);
            pstInsert.setString(5, "Si");
            pstInsert.setString(6, nombre);
            pstInsert.setDouble(7, subtotal);
            pstInsert.setDouble(8, iva);
            pstInsert.setDouble(9, total);
     

            // Ejecutar la inserción
            pstInsert.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro insertado con éxito");
            
            

            // Llamar al método para calcular el total
     
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al insertar registro: " + e.getMessage());
    }
}

 
public void insertarNuevoRegistro() {
    try {
        // Obtener valores de los JTextFields
        String noFactura = txtfactura.getText().trim();
        String fechaStr = txtFecha.getText().trim();
        String idProducto = txtId.getText().trim();
        String producto = txtproducto.getText().trim();
        String marca = txtMarca.getText().trim();
        String unidad = txtunidad.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        int cantidad = Integer.parseInt(jComboBox1.getSelectedItem().toString());

        double totalProducto = precio * cantidad;

        if (noFactura.isEmpty() || fechaStr.isEmpty() || idProducto.isEmpty() || producto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos requeridos.");
            return;
        }

        // Verificar la cantidad disponible en la tabla `producto`
        String verificarCantidadSQL = "SELECT unidades FROM producto WHERE idproducto = ? AND activo = 'Si'";
        try (PreparedStatement pstVerificar = con.prepareStatement(verificarCantidadSQL)) {
            pstVerificar.setString(1, idProducto);
            ResultSet rs = pstVerificar.executeQuery();

            if (rs.next()) {
                int unidadesDisponibles = rs.getInt("unidades");

                if (unidadesDisponibles < cantidad) {
                    JOptionPane.showMessageDialog(null, "Cantidad insuficiente en el inventario.");
                    return;
                } else {
                    // Restar la cantidad solicitada de `producto`
                    String actualizarUnidadesSQL = "UPDATE producto SET unidades = unidades - ? WHERE idproducto = ?";
                    try (PreparedStatement pstActualizar = con.prepareStatement(actualizarUnidadesSQL)) {
                        pstActualizar.setInt(1, cantidad);
                        pstActualizar.setString(2, idProducto);
                        pstActualizar.executeUpdate();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado o inactivo.");
                return;
            }
        }

        // Preparar la fecha actual
        Date fechaActual = new Date(System.currentTimeMillis());

        // Insertar el nuevo registro en `detalelfactura`
        String SQLInsert = "INSERT INTO detalelfactura (nofactura, fecha, idproducto, producto, marca, unidad, precio, cantidad, totalproducto, activa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstInsert = con.prepareStatement(SQLInsert)) {
            pstInsert.setString(1, noFactura);
            pstInsert.setDate(2, fechaActual);
            pstInsert.setString(3, idProducto);
            pstInsert.setString(4, producto);
            pstInsert.setString(5, marca);
            pstInsert.setString(6, unidad);
            pstInsert.setDouble(7, precio);
            pstInsert.setInt(8, cantidad);
            pstInsert.setDouble(9, totalProducto);
            pstInsert.setString(10, "Si");

            pstInsert.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro insertado con éxito");

            // Calcular el total de la factura
            calcularTotalPorFactura(noFactura);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al insertar registro: " + e.getMessage());
    }
}
 
 
  public void calcularTotalPorFactura(String noFactura) {
    try {
        String SQLSum = "SELECT SUM(totalproducto) AS total FROM detalelfactura WHERE nofactura = ?";
        try (PreparedStatement pstSum = con.prepareStatement(SQLSum)) {
            pstSum.setString(1, noFactura);

            try (ResultSet rs = pstSum.executeQuery()) {
                if (rs.next()) {
                    double total = rs.getDouble("total");
                    txtTotal.setText(String.valueOf(total));
                    double iva= total *0.12;// Mostrar el total en txtTotal
                    txtIva.setText(String.format("%.2f", iva));
                     double subtotal= total-iva;// Mostrar el total en txtTotal
                   txtSubtotal.setText(String.format("%.2f", subtotal));       
                 
                }
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al calcular el total: " + e.getMessage());
    }
}

       public void llenarvendedor(){
   String SQL = "select nombre from vendedores s where activo='Si'";

try {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(SQL);
    
    while (rs.next()) {
        String nombre = rs.getString("nombre");
        jComboBox5.addItem(nombre);
   
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error al llenar el combo box: " + e.getMessage());
}
  }
   

    public formfactura() {
        initComponents();
        mostrarDatos();
        llenarvendedor();
        initComponents();
        txtunidades.setVisible(false);
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
        txtId.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
    }
        
 
       
    public void insertarDatos() {
    try {
 
        String precio = txtPrecio.getText();
        String cantidad = txtCantidad.getText();
        String Activo = "Si";
        LocalDate fechaActual = LocalDate.now();
        int cantidadCompra = Integer.parseInt(cantidad);
        double precioUnitario = Double.parseDouble(precio);
        double total = cantidadCompra * precioUnitario;

        // Verifica si los combos están seleccionados
//        if (producto == null || marca == null || unidadMedida == null ) {
  //          JOptionPane.showMessageDialog(null, "Debe seleccionar una opción en los combo boxes");
  //          return;
  //      }

        // Consulta a la tabla producto para verificar si existe
        String sqlVerificar = "SELECT unidades FROM producto WHERE nombre = ? AND unidadmedida = ? AND marca = ? AND activo = 'Si'";
        PreparedStatement pstVerificar = con.prepareStatement(sqlVerificar);
 //       pstVerificar.setString(1, producto);
  //      pstVerificar.setString(2, unidadMedida);
   //     pstVerificar.setString(3, marca);

        ResultSet rs = pstVerificar.executeQuery();

        if (rs.next()) {
            // Producto encontrado, obtiene el valor de las unidades actuales
            int unidadesActuales = rs.getInt("unidades");

            // Suma las unidades que se están insertando en compras
            int nuevasUnidades = unidadesActuales + cantidadCompra;

            // Inserta los datos en la tabla compras
            String SQL = "INSERT INTO compras (producto, marca, unidadmedida, proveedor, preciou, unidadescompra, total, usuario, fecha, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(SQL);
    //        pst.setString(1, producto);
    //        pst.setString(2, marca);
    //        pst.setString(3, unidadMedida);
        
            pst.setString(5, precio);
            pst.setString(6, cantidad);
            pst.setDouble(7, total);
            pst.setString(8, usuario);
            pst.setDate(9, Date.valueOf(fechaActual));
            pst.setString(10, Activo);

            pst.execute();

            // Actualiza el campo unidades en la tabla producto
            String sqlActualizar = "UPDATE producto SET unidades = ? WHERE nombre = ? AND unidadmedida = ? AND marca = ?";
            PreparedStatement pstActualizar = con.prepareStatement(sqlActualizar);
            pstActualizar.setInt(1, nuevasUnidades);
  //          pstActualizar.setString(2, producto);
    //        pstActualizar.setString(3, unidadMedida);
    //        pstActualizar.setString(4, marca);

            pstActualizar.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registro guardado y unidades actualizadas con éxito");
            mostrarDatos();
            limpiarDatos();
        } else {
            // Si no existe el producto
            JOptionPane.showMessageDialog(null, "El producto no existe en la tabla productos");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Guardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        txtSubtotal = new javax.swing.JTextField();
        txtIva = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtproducto = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtunidad = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtNitCliente = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtfactura = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtunidades = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();

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

        txtId.setEditable(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdKeyReleased(evt);
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

        txtPrecio.setEditable(false);
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

        jLabel7.setText("Unidades Compra");

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

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtSubtotal.setText("0");

        txtIva.setText("0");

        txtTotal.setText("0");
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        jLabel16.setText("Subtotal");

        jLabel17.setText("Iva");

        jLabel18.setText("Total");

        txtproducto.setEditable(false);

        txtMarca.setEditable(false);

        txtunidad.setEditable(false);

        jButton3.setText("+");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(307, 307, 307)
                                .addComponent(jButton2)
                                .addGap(43, 43, 43)
                                .addComponent(Guardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16))))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(jButton3)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(txtunidad, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(jLabel6)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(70, 70, 70)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)
                        .addComponent(txtproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtunidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(Guardar)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Cliente"));

        jLabel9.setText("Nit:");

        jLabel10.setText("Direccion:");

        txtDireccion.setEditable(false);

        jLabel11.setText("Nombre:");

        txtNombreCliente.setEditable(false);

        txtNitCliente.setEditable(false);
        txtNitCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNitClienteFocusLost(evt);
            }
        });
        txtNitCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNitClienteActionPerformed(evt);
            }
        });

        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreCliente)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtNitCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNitCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(31, 31, 31))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Factura"));

        jLabel12.setText("No factura:");

        txtfactura.setEditable(false);

        jLabel14.setText("Fecha:");

        txtFecha.setEditable(false);

        jLabel13.setText("Vendedor");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfactura, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel15.setText("Facturacion");

        txtunidades.setEditable(false);
        txtunidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtunidadesActionPerformed(evt);
            }
        });

        txtCantidad.setEditable(false);
        txtCantidad.setText("0");
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(385, 385, 385)
                        .addComponent(jLabel15)
                        .addGap(69, 69, 69)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtunidades, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)
                        .addComponent(txtunidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
       insertarNuevaFactura();
       txtNombreCliente.setText("");
        txtNitCliente.setText("");
        txtDireccion.setText("");
        txtfactura.setText("");
        txtId.setText("");
        txtproducto.setText("");
        txtMarca.setText("");
        txtunidad.setText("");
        txtPrecio.setText("");
        mostrarDatos();
        this.dispose();
        

        

       
    }//GEN-LAST:event_GuardarActionPerformed

    private void txtIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyReleased
  // TODO add your handling code here:
    }//GEN-LAST:event_txtIdKeyReleased

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked

  int filaSeleccionada=tblUsuarios.rowAtPoint(evt.getPoint());       // TODO add your handling code here:
    //   txtNit.setText(tblUsuarios.getValueAt(filaSeleccionada, 0).toString());
   
      // txtPrecio.setText(tblUsuarios.getValueAt(filaSeleccionada, 4).toString());
       
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    txtNombreCliente.setText("");
        txtNitCliente.setText("");
        txtDireccion.setText("");
        txtfactura.setText("");
        txtId.setText("");
        txtproducto.setText("");
        txtMarca.setText("");
        txtunidad.setText("");
        txtPrecio.setText("");
        mostrarDatos();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        formBuscarProductos objeto= new formBuscarProductos();
        objeto.setVisible(true);  
                // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
  
    }//GEN-LAST:event_txtCantidadKeyReleased

    private void txtunidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtunidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtunidadesActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtNitClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNitClienteFocusLost
    // Si el campo no está vacío, continuamos con la ejecución del código

        // TODO add your handling code here:
    }//GEN-LAST:event_txtNitClienteFocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaActual = LocalDate.now();
        String fechaFormateada = fechaActual.format(formatter);        
     
        txtFecha.setText(fechaFormateada);
         DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        String factura = "FAC" + fechaHoraActual.format(formatter1);
        txtfactura.setText(factura);
        
          formBuscarCliente objeto= new formBuscarCliente();
        objeto.setVisible(true);  
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtNitClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNitClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNitClienteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
insertarNuevoRegistro();  
mostrarDatos();// TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(formfactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formfactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formfactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formfactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new formfactura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Guardar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCantidad;
    public static transient volatile javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFecha;
    public static transient volatile javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIva;
    public static transient volatile javax.swing.JTextField txtMarca;
    public static transient volatile javax.swing.JTextField txtNitCliente;
    public static transient volatile javax.swing.JTextField txtNombreCliente;
    public static transient volatile javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtfactura;
    public static transient volatile javax.swing.JTextField txtproducto;
    public static transient volatile javax.swing.JTextField txtunidad;
    public static transient volatile javax.swing.JTextField txtunidades;
    // End of variables declaration//GEN-END:variables
}
