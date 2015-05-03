/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualdocumentslibrary;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author giovannirojas
 */
class Utils{
    
    
    private  final static String [] rp = {"jpg","png","exe","sql"};

    public static String[] getRp() {
        return rp;
    }
    
    
   
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    // esto sera para el segundo proyecto 
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No puede encontrar el Archivo .. " + path);
            return null;
        }
    }
}
