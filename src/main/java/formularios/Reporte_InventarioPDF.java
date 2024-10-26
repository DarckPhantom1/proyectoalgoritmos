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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Reporte_InventarioPDF extends javax.swing.JFrame {
    conexionSQL cc = new conexionSQL();
    Connection con= cc.conexion();
    public Reporte_InventarioPDF() {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reporte del Inventario");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)))
                .addGap(70, 70, 70)
                .addComponent(jButton1)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        // Ruta donde se guardará el PDF
        String rutaPDF = "C:\\Users\\pinip\\Desktop\\Nueva carpeta\\proyectoalgoritmos\\Reportes\\Productos\\reporte_productos.pdf";
        String rutaCarpeta = "C:\\Users\\pinip\\Desktop\\Nueva carpeta\\proyectoalgoritmos\\Reportes\\Productos";
        String opc = jComboBox1.getSelectedItem().toString();
        String consultaSQL = null;

        switch (opc) {
            case "Si" -> // Consulta SQL
            consultaSQL = "SELECT nombre, unidadmedida, precioventa, activo, idproducto, marca, unidades FROM producto where activo='si'";
            case "No" -> // Consulta SQL
            consultaSQL = "SELECT nombre, unidadmedida, precioventa, activo, idproducto, marca, unidades FROM producto where activo='no'";
            case "Todos" -> // Consulta SQL
            consultaSQL = "SELECT nombre, unidadmedida, precioventa, activo, idproducto, marca, unidades FROM producto";
        }

        try {
            // Verificar si el archivo PDF ya existe, y si es así, generar un nombre único
            File archivoPDF = new File(rutaPDF);
            int contador = 1;
            while (archivoPDF.exists()) {
                rutaPDF = rutaCarpeta + "\\reporte_productos_" + contador + ".pdf";
                archivoPDF = new File(rutaPDF);
                contador++;
            }
            // Crear el documento PDF
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(rutaPDF));
            documento.open();

            // Título del reporte
            documento.add(new Paragraph("Reporte de Productos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Font.BOLD)));
            documento.add(new Paragraph(" ")); // Espacio

            // Crear tabla en el PDF con 7 columnas (una para cada campo de la tabla proveedores)
            PdfPTable tabla = new PdfPTable(7);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[] { 1, 2, 2, 1, 2, 2, 1 }); // Anchos de las columnas

            // Encabezados de la tabla
            tabla.addCell(new Paragraph("ID", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
            tabla.addCell(new Paragraph("Nombre", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
            tabla.addCell(new Paragraph("Marca", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
            tabla.addCell(new Paragraph("Unidad Medida", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
            tabla.addCell(new Paragraph("Precio Venta", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
            tabla.addCell(new Paragraph("Unidades", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
            tabla.addCell(new Paragraph("Activo", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));

            // Conectar a la base de datos
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(consultaSQL) ;
            // Llenar la tabla con los datos de la consulta
            while (rs.next()) {
                
                tabla.addCell(rs.getString("idproducto"));
                tabla.addCell(rs.getString("nombre"));
                tabla.addCell(rs.getString("marca"));
                tabla.addCell(rs.getString("unidadmedida"));
                tabla.addCell(rs.getString("precioventa"));
                tabla.addCell(rs.getString("unidades"));
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

        } catch (DocumentException | FileNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage());
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
            java.util.logging.Logger.getLogger(Reporte_InventarioPDF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reporte_InventarioPDF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reporte_InventarioPDF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reporte_InventarioPDF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reporte_InventarioPDF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
