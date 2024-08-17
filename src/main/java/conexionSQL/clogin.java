/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexionSQL;

import formularios.formMenuPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Rony
 */
public class clogin {
    public void validarUsuario(JTextField usuario, JPasswordField pass){
        
         try{
             ResultSet rs=null;
             PreparedStatement ps=null;
             
            conexionSQL cc = new conexionSQL();
            Connection con = cc.conexion();        
           
            String consulta="select * from usuarios where  usuarios.usuario  =(?) and usuarios.pass = (?)";
                     
            ps=cc.conexion().prepareStatement(consulta);
           
            String contra= String.valueOf(pass.getPassword());
              
            ps.setString(1, usuario.getText());
            ps.setString(2, contra);
            rs= ps.executeQuery();
            
            if (rs.next()){
            JOptionPane.showMessageDialog(null,"ha ingresado correctamente");   
            formMenuPrincipal objetoMenu= new formMenuPrincipal();
            objetoMenu.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Acceso incorrecto");  
            }
             
          
            
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error de Conexion"+e.getMessage());
            
        }
        
    }
}
