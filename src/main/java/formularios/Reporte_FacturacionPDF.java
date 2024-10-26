package formularios;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexionSQL.conexionSQL;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class Reporte_FacturacionPDF extends javax.swing.JFrame {
    conexionSQL cc = new conexionSQL();
    Connection con= cc.conexion();
    public Reporte_FacturacionPDF() {
        initComponents();
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
                    setVisible(false); 
                }
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Generar Reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No", "Todos" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Activo");

        jLabel2.setText("Rango de Fechas");

        jLabel3.setText("Desde:");

        jLabel4.setText("Hasta:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        // Ruta donde se guardará el PDF
        String rutaPDF = "C:\\Users\\pinip\\Desktop\\Nueva carpeta\\proyectoalgoritmos\\Reportes\\Facturas\\reporte_facturas.pdf";
        String rutaCarpeta = "C:\\Users\\pinip\\Desktop\\Nueva carpeta\\proyectoalgoritmos\\Reportes\\Facturas";
        String opc = jComboBox1.getSelectedItem().toString();
        String consultaSQL = null;

        // Obtener la fecha seleccionada
        java.util.Date fechaSeleccionada1 = jDateChooser1.getDate();
        java.util.Date fechaSeleccionada2 = jDateChooser2.getDate();
            
        if (fechaSeleccionada1 == null || fechaSeleccionada2 == null) {
            JOptionPane.showMessageDialog(null, "Por favor selecciona ambas fechas.");
            return; // Sale del método para evitar errores
        }
        
        java.sql.Date fecha1 = new java.sql.Date(fechaSeleccionada1.getTime());
        java.sql.Date fecha2 = new java.sql.Date(fechaSeleccionada2.getTime());
        
        switch (opc) {
            case "Si" -> // Consulta SQL
            consultaSQL = "SELECT idfactura, nofactura, fechafac, nomcliente, nitcliente, vendedor, activo, subtotal, iva, total FROM factura where activo='si' and fechafac between ? and ?";
            case "No" -> // Consulta SQL
            consultaSQL = "SELECT idfactura, nofactura, fechafac, nomcliente, nitcliente, vendedor, activo, subtotal, iva, total FROM factura where activo='no' and fechafac between ? and ?";
            case "Todos" -> // Consulta SQL
            consultaSQL = "SELECT idfactura, nofactura, fechafac, nomcliente, nitcliente, vendedor, activo, subtotal, iva, total FROM factura where fechafac between ? and ?";
        }

        try {
    // Verificar si el archivo PDF ya existe, y si es así, generar un nombre único
    File archivoPDF = new File(rutaPDF);
    int contador = 1;
    while (archivoPDF.exists()) {
        rutaPDF = rutaCarpeta + "\\reporte_facturas_" + contador + ".pdf";
        archivoPDF = new File(rutaPDF);
        contador++;
    }
    // Crear el documento PDF
    Document documento = new Document();
    PdfWriter.getInstance(documento, new FileOutputStream(rutaPDF));
    documento.open();

    // Título del reporte
    documento.add(new Paragraph("Reporte de Facturas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD)));
    documento.add(new Paragraph(" ")); // Espacio

    // Crear tabla en el PDF con 7 columnas (una para cada campo de la tabla proveedores)
    PdfPTable tabla = new PdfPTable(10);
    tabla.setWidthPercentage(100);
    tabla.setWidths(new float[]{1, 1, 1, 1, 1, 2, 1, 1, 1, 1}); // Anchos de las columnas

    // Encabezados de la tabla
    tabla.addCell(new Paragraph("ID", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    tabla.addCell(new Paragraph("No. Factura", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    tabla.addCell(new Paragraph("Fecha", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    tabla.addCell(new Paragraph("Cliente", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    tabla.addCell(new Paragraph("NIT Cliente", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
    tabla.addCell(new Paragraph("Vendedor", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    tabla.addCell(new Paragraph("Subtotal", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    tabla.addCell(new Paragraph("IVA", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    tabla.addCell(new Paragraph("Total", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
    tabla.addCell(new Paragraph("Activo", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));

    // Conectar a la base de datos y preparar la consulta
    PreparedStatement pst = con.prepareStatement(consultaSQL);
    pst.setDate(1, fecha1);  // Asigna la primera fecha en el primer parámetro
    pst.setDate(2, fecha2);  // Asigna la segunda fecha en el segundo parámetro
    ResultSet rs = pst.executeQuery();
    
    if (!rs.isBeforeFirst()) { // Verifica si el ResultSet está vacío
        JOptionPane.showMessageDialog(null, "No se encontraron registros en el rango de fechas seleccionado.");
        documento.close();
    
        if (archivoPDF.exists()) {
            archivoPDF.delete();
        }
    
        return;
    }
    
    // Llenar la tabla con los datos de la consulta
    while (rs.next()) {
        tabla.addCell(rs.getString("idfactura"));
        tabla.addCell(rs.getString("nofactura"));
        tabla.addCell(rs.getString("fechafac"));
        tabla.addCell(rs.getString("nomcliente"));
        tabla.addCell(rs.getString("nitcliente"));
        tabla.addCell(rs.getString("vendedor"));
        tabla.addCell(rs.getString("subtotal"));
        tabla.addCell(rs.getString("iva"));
        tabla.addCell(rs.getString("total"));
        tabla.addCell(rs.getString("activo"));
    }

    // Agregar la tabla al documento PDF
    documento.add(tabla);

    // Cerrar el documento
    documento.close();

    JOptionPane.showMessageDialog(null, "Reporte PDF generado correctamente.");

    File carpeta = new File(rutaCarpeta);
    // Verificar si el escritorio (Desktop) es compatible
    if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        try {
            // Verificar si el archivo/carpeta existe
            if (carpeta.exists()) {
                // Abrir la carpeta
                desktop.open(carpeta);
                System.out.println("Carpeta abierta: " + rutaCarpeta);
            } else {
                System.out.println("La carpeta no existe.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("La función no es compatible en este sistema.");
    }

    } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Error: Archivo no encontrado. " + e.getMessage());
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error de SQL: " + e.getMessage());
    } catch (DocumentException e) {
        JOptionPane.showMessageDialog(null, "Error al crear el documento PDF: " + e.getMessage());
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error de E/S: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(Reporte_FacturacionPDF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reporte_FacturacionPDF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reporte_FacturacionPDF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reporte_FacturacionPDF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reporte_FacturacionPDF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
