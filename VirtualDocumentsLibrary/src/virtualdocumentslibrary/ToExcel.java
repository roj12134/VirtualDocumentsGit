
package virtualdocumentslibrary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.report.ReportManager;
import net.sf.jxls.report.ReportManagerImpl;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;




public class ToExcel {
    
      // Variables de base de datos 
   private Statement st = DataBaseClass.getSt();
   private Connection connection = DataBaseClass.getConnection();
   private ResultSet resultSet = DataBaseClass.getResultSet();

    public void Open (String file){
        // este metodo lo que hace es que abre el archivo luego de aberlo creado. 
         try
            {
               if (Desktop.isDesktopSupported()){  
                    
                        // esto verifica si puede abrir archivos la computadora 
                        Desktop.getDesktop().open(new File(file));
                    
               } 
               
            }
            catch (Exception e)
            {
               JOptionPane.showMessageDialog(null, "<html><body><pre><p style='width: 600px;'>Error al abrir archivo "+file+"<br>"+e.getMessage()+" </p></pre></body></html>","Error al abrir archivo ", 0);
            
            }
         
    }
    
    public void TipoSueldo (String []arrayFecha,String plantilla) throws SQLException, ParsePropertyException, IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException{
                       // tipo a va hacer cuando crea el archivo con una consulta y dos parametros en este 
                        // caso fecha inicial y final, y luego el nombre de la plantilla que es directamente a el nombre de la plantilla. 
                                   Map beans = new HashMap();
                                   ReportManager rm = new ReportManagerImpl( connection, beans );
  
                                    beans.put("rm", rm);
                                    beans.put("fechaInicial",arrayFecha[0]);
                                    beans.put("fechaFinal",arrayFecha[1]);
                                    
                                    XLSTransformer transformer = new XLSTransformer();
            
                                    transformer.transformXLS(System.getProperty("user.dir")+"\\src\\Plantillas\\"+plantilla+".xls", beans, System.getProperty("user.dir")+"\\src\\ArchivosGeneradosExcel\\"+plantilla+".xls");
           
                                    String file = System.getProperty("user.dir")+"\\src\\ArchivosGeneradosExcel\\"+plantilla+".xls";
                                    
                                Open(file);  // abro el archivo. 
              
           
           
          
        }
    
    public void TipoBlockCosto (String []arrayFecha,String plantilla, double igss, double imn, double luz) throws SQLException, ParsePropertyException, IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException{
                    // tipo a va hacer cuando crea el archivo con una consulta y dos parametros en este 
                        // caso fecha inicial y final, y luego el nombre de la plantilla que es directamente a el nombre de la plantilla. 
                                   Map beans = new HashMap();
                                   ReportManager rm = new ReportManagerImpl( connection, beans );
  
                                    beans.put("rm", rm);
                                    beans.put("fechaInicial",arrayFecha[0]);
                                    beans.put("fechaFinal",arrayFecha[1]);
                                    beans.put("IGSS",igss);
                                    beans.put("Imn",imn);
                                    beans.put("Luz",luz);
                                    
                                    
                                    XLSTransformer transformer = new XLSTransformer();
            
                                    transformer.transformXLS(System.getProperty("user.dir")+"\\src\\Plantillas\\"+plantilla+".xls", beans, System.getProperty("user.dir")+"\\src\\ArchivosGeneradosExcel\\"+plantilla+".xls");
           
                                    String file = System.getProperty("user.dir")+"\\src\\ArchivosGeneradosExcel\\"+plantilla+".xls";
                                    
                                Open(file);  // abro el archivo. 
      
        
    }
    public void certificarFile () {
       try {
           Map beans = new HashMap();
           ReportManager rm = new ReportManagerImpl( connection, beans );
           
           beans.put("rm", rm);
           beans.put("nombre","Giovanni Rojas");
           beans.put("inicial","Alfa-001");
           beans.put("final","Omega-0020");
           
           XLSTransformer transformer = new XLSTransformer();
           
           transformer.transformXLS(System.getProperty("user.dir")+"\\src\\Plantillas\\certificacionPlantilla.xls", beans, System.getProperty("user.dir")+"\\src\\ArchivosGeneradosExcel\\certificacion.xls");
           
           String file = System.getProperty("user.dir")+"\\src\\ArchivosGeneradosExcel\\certificacion.xls";
           
           Open(file);  // abro el archivo. 
       } catch (ParsePropertyException ex) {
           Logger.getLogger(ToExcel.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(ToExcel.class.getName()).log(Level.SEVERE, null, ex);
       } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException ex) {
           Logger.getLogger(ToExcel.class.getName()).log(Level.SEVERE, null, ex);
       }
    }        
                
                   
       
      
    }

