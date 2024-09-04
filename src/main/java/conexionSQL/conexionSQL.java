
package conexionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class conexionSQL {
    Connection conectar=null;
    String usuario="root";
    String password="mysql1";
    String puerto="3306";
    String ip="localhost";
    String Bdd="inventariofacturacion";
    String cadena="jdbc:mysql://"+ip+":"+puerto+"/"+Bdd;
    
    public Connection conexion(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar=DriverManager.getConnection(cadena,usuario,password);
            JOptionPane.showMessageDialog(null,"Conexion exitosa a Base de datos");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error de Conexion "+e.getMessage());
            
        }
         return conectar;
    }
    
    

}
