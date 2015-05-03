/**
 * ****************************************************************************
 * VirtualLibrary.java by Giovanni Rojas Mazariegos and Javier Alay
 * geovaroma@gmail.com javier.alay@gmail.com
 * 
* It is all the stuff of Configuration Stuff
 * ****************************************************************************
 */
package virtualdocumentslibrary;

import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static virtualdocumentslibrary.DataBaseClass.st;

/**
 *
 * @author giovannirojas
 */
public class ConfigClass extends DataBaseClass{
    
    private static File archivo = null;
    private static FileReader fr = null;
    private static BufferedReader br = null;
    
    public static boolean fileExists() {
        try {
            archivo = new File(System.getProperty("user.dir") + ""+File.separator+"src"+File.separator+"Configuraciones"+File.separator+"config.txt");

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static String [] configReader (){
        String [] datos = null;
        if (fileExists()){
            try {

                String linea;
                String cadena = "";
                while ((linea = br.readLine()) != null) {
                    cadena += linea.toString()+"~";
                }

                datos = null;

                datos = cadena.split("~");


            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        else {

                    ConfigView cf = new ConfigView();
                    cf.setLocationRelativeTo(null);
                    cf.setVisible(true);


            }
        //Ahora obtengo los datos de la base de datos
        String[] dataFromDB = ConfigClass.getDataFromConfiguracion();
        //antes de regresar los datos los ordeno
        String [] datosReturn = {datos[0],datos[1],datos[2],datos[3],dataFromDB[0],dataFromDB[1],dataFromDB[2],datos[4],dataFromDB[3],dataFromDB[4],dataFromDB[5],dataFromDB[6],datos[5]};
        return datosReturn;
    }
    
    public static String [] configReaderInit(){
        String [] datos = null;
        if (fileExists()){
            try {

                String linea;
                String cadena = "";
                while ((linea = br.readLine()) != null) {
                    cadena += linea.toString()+"~";
                }

                datos = null;

                datos = cadena.split("~");


            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        else {

                    ConfigView cf = new ConfigView();
                    cf.setLocationRelativeTo(null);
                    cf.setVisible(true);


            }
        
        return datos;
    }
    
    public static Color getColorApp (){
        
        String [] datos = ConfigClass.configReader(); 
        
        int Red = Integer.parseInt(datos[4]);
        int Green = Integer.parseInt(datos[5]);
        int Blue = Integer.parseInt(datos[6]);
        
        return  new Color(Red,Green,Blue);
        
        
    }
    
    public static String[] getDataFromConfiguracion(){
        String[] data = null;
        try {
                //obtengo los datos de la base de datos
                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery("SELECT * FROM configuracion where idConfiguracion=1;");
                metaData = (ResultSetMetaData) resultSet.getMetaData();                              
                
                
                while(resultSet.next()){
                    
                       data = new String[]{resultSet.getString("colorRed"),resultSet.getString("colorGreen"),resultSet.getString("colorBlue"),resultSet.getString("institutionName"),resultSet.getString("motherPath"),resultSet.getString("motherUser"),resultSet.getString("motherPassword")};
                       
               }
  
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "<html><body><pre><p style='width: 600px;'> Error al cargar datos a la tabla : Configuracion <br/>"+ex+"</p></pre></body></html>","Error en base de datos", 0);
      }
        
        return data;
        
    }
}
