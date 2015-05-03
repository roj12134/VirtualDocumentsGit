/*
 * Universidad del Valle de Guatemla 
 * Giovanni Rojas Mazariegos 12134
 * 
 * 
 * Clase FiltroArchivo: Esta clase se dedica solo a filtrar el archivo de entrada. 
 * Este archivo fue de documentos previos crados por el alumno Giovanni Rojas 
 */

package virtualdocumentslibrary;

import java.io.File;
import javax.swing.filechooser.FileFilter;



// esta clase es utilizada para el filtrar archivos en el Jfile Dialog. 

public class FiltroArchivo extends FileFilter {
    private String descripcion = "";
    private String tipo = "";
    /* Constructor */ 
    public FiltroArchivo (String tipo) {
        this.tipo = tipo;
        if (tipo.equalsIgnoreCase("exe")){
            descripcion = "Adobe Reader (.exe)";
        }
        else if (tipo.equalsIgnoreCase("imagen")){
            descripcion = "Archivos de imagen (.jpg y .png)";
            
        }
    
    }
    
    @Override
    public boolean accept(File file) {
        
        if (file.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(file);
        if (extension != null) {
            if (tipo.equalsIgnoreCase("imagen")){
                if (extension.equals(Utils.getRp()[1]) ||  extension.equals(Utils.getRp()[0])) {
                        return true;
                } else {
                    return false;
                }
            }
            else if (tipo.equalsIgnoreCase("exe")){
                if (extension.equals(Utils.getRp()[2])) {
                        return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return descripcion;
    }
    
}
