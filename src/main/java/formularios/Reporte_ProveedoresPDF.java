package formularios;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Reporte_ProveedoresPDF extends javax.swing.JFrame {
    
    conexionSQL cc = new conexionSQL();
    Connection con= cc.conexion();
    
    public Reporte_ProveedoresPDF() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jButton1)
                .addGap(67, 67, 67)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           
        // Ruta donde se guardará el PDF
        String rutaPDF = "C:\\Users\\pinip\\Desktop\\Nueva carpeta\\proyectoalgoritmos\\Reportes\\reporte_proveedores.pdf";      
        String opc = jComboBox1.getSelectedItem().toString();
        String consultaSQL = null;
                
        switch (opc) {
            case "Si" -> // Consulta SQL
                consultaSQL = "SELECT idProveedor, nombre, telefono, direccion, usuario_ingresa, activo FROM proveedores where activo='si'";
            case "No" -> // Consulta SQL
                consultaSQL = "SELECT idProveedor, nombre, telefono, direccion, usuario_ingresa, activo FROM proveedores where activo='no'";
            case "Todos" -> // Consulta SQL
                consultaSQL = "SELECT idProveedor, nombre, telefono, direccion, usuario_ingresa, activo FROM proveedores";
        }
        
        

        try {
            // Crear el documento PDF
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaPDF));
            documento.open();

            // Título del reporte
            documento.add(new Paragraph("Reporte de Proveedores", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD)));
            documento.add(new Paragraph(" ")); // Espacio

            // Crear tabla en el PDF con 6 columnas (una para cada campo de la tabla proveedores)
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[] { 1, 3, 2, 3, 2, 1 }); // Anchos de las columnas

            // Encabezados de la tabla
            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Teléfono");
            tabla.addCell("Dirección");
            tabla.addCell("Usuario");
            tabla.addCell("Activo");

            // Conectar a la base de datos
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(consultaSQL) ;
            // Llenar la tabla con los datos de la consulta
            while (rs.next()) {
                tabla.addCell(rs.getString("idProveedor"));
                tabla.addCell(rs.getString("nombre"));
                tabla.addCell(rs.getString("telefono"));
                tabla.addCell(rs.getString("direccion"));
                tabla.addCell(rs.getString("usuario_ingresa"));
                tabla.addCell(rs.getString("activo"));
            }

            // Agregar la tabla al documento PDF
            documento.add(tabla);

            // Cerrar el documento
            documento.close();

            JOptionPane.showMessageDialog(null, "Reporte PDF generado correctamente.");

        } catch (DocumentException | FileNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reporte_ProveedoresPDF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    // End of variables declaration//GEN-END:variables
}
