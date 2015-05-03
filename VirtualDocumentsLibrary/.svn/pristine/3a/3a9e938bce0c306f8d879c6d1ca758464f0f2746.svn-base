/**
 * ****************************************************************************
 * VirtualLibrary.java by Giovanni Rojas Mazariegos and Javier Alay
 * geovaroma@gmail.com javier.alay@gmail.com
 * 
* It is the main of the application of Virutual Library
 * ****************************************************************************
 */
package virtualdocumentslibrary;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;
import java.io.File;


import javax.swing.UIManager;

/**
 *
 * @author giovannirojas
 */
public class VirtualLibrary {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
        //llamo al look and feel
        try {
            UIManager.setLookAndFeel(new Plastic3DLookAndFeel());

        } catch (Exception e) {
        }
        
        
        
        
         // Este metodo es Estatico porque lo puedo acceder de cualquier 
        // Clase, pero no a sus atributos directamente por lo que continua siendo 
        // Encapsulada 
        if (DataBaseClass.connect(ConfigClass.configReaderInit())){
            // Cargar Backup 
            
            
            // Pruebo hacer el backup. 
            DataBaseClass.updateBackup(ConfigClass.configReaderInit(),System.getProperty("user.dir") + ""+File.separator+"src"+File.separator+"DataBase"+File.separator+"dataBackup.sql");
           
            
            // Voy a darle acceso o no al programa 
            LoginView vi = new LoginView ();
            vi.setLocationRelativeTo(null);
            vi.setVisible(true);
            
            
        }
        else {
            ConfigView va = new ConfigView();
            va.setLocationRelativeTo(null);
            va.setVisible(true);
            
        }
        


    }
}
