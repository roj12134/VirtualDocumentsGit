/**
 * ****************************************************************************
 * VirtualLibrary.java by Giovanni Rojas Mazariegos and Javier Alay
 * <geovaroma@gmail.com> <javier.alay@gmail.com>
 * 
* Model of all the tables
 * ****************************************************************************
 */
package virtualdocumentslibrary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author giovannirojas
 */
public class ResultSetTableModel extends AbstractTableModel{

    private Object data [][] = null;
    private String [] columnNames;
    private String consulta = "";
    private String type = "";
    private int numfilas;
    private int numcolumn;
    private String extraParameters ;
    
    // Variables de la base de datos. 
    /* Jalo los valores de la base de datos para generar el sql para la tabla.  */
    private Statement st = DataBaseClass.getSt();
    private Connection connection = DataBaseClass.getConnection();
    private ResultSet resultSet = DataBaseClass.getResultSet();
    private ResultSetMetaData metaData = DataBaseClass.getMetaData();
    
    
    // arreglo de clases. 
    Class[] classArray;
    
    public ResultSetTableModel (String kind){ // Constructor del Table Model
        type = kind; // El tipo sera lo que ingrese
        
        this.extraParameters = ""; // No tengo extra parametros en la consulta. 
        buildTable(type);  // Ire a construir la tabla segun el tipo de tabla que reciba. 
        
        // le coloco la informacion en tablas especiales que lo requieran. 
        specialDataStart();
        
    
    }
    
    public ResultSetTableModel (String kind, String extraParameters){ // Constructor del Table Model
        type = kind; // El tipo sera lo que ingrese
        this.extraParameters = extraParameters; // coloco los extra Parametros. 
        buildTable(type);  // Ire a construir la tabla segun el tipo de tabla que reciba. 
        
        specialDataStart();
    }
    
    /* 
    Este constructor es especial ya que se usa para hacer reportes. 
    */
    public ResultSetTableModel (String kind, String consulta, String [] columnasNombres, String parameters){
        type = kind;
        this.extraParameters = parameters;
        this.consulta = consulta+parameters;
        
        this.columnNames = columnasNombres;
        this.numcolumn = columnNames.length;
        
        buildTable(type);
        
        specialDataStart();
    }
    public void specialDataStart(){
        
        /* 
         * newUsersTable no jala la informacion de la base de datos sino se la coloca el usuario,
         * por eso es la excepcion. 
         */
        if (!type.equalsIgnoreCase("newUsersTable")){
        try {

                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery(consulta);
                metaData = (ResultSetMetaData) resultSet.getMetaData();
           
                resultSet.last();                // estas lineas se mueven al final de la consulta 
                numfilas=resultSet.getRow();
           
                data = new Object [columnNames.length][numfilas];
                resultSet.beforeFirst();
                int j=0;
                // Este ciclo se mueve en columnas y luego en filas. 
                while(resultSet.next()){
                    for(int i=0;i<columnNames.length;i++){
                       
                        // Esta es para jalar datos a la tabla de forma normal, mas sin embargo si quisieramos 
                        // hacer una tabla se tendria que mandar el tipo y segun el tipo hara las cosas. 
                        
                        if (type.equalsIgnoreCase("permiso modulos") ){
                      
                            if(i==0)
                                data[i][j]=resultSet.getInt(1);
                            if(i==1)
                                data[i][j]=resultSet.getString(2);
                            if(i==2)
                                data[i][j]=resultSet.getString(3);
                            if(i==3){
                                
                            }
                                
                       
                        } // fin del if   
                        
                        else if(type.equals("permiso subfondo")){
                            if(i==0)
                                data[i][j]=resultSet.getInt(1);
                            if(i==1)
                                data[i][j]=resultSet.getString(2);
                            if(i==2)
                                data[i][j]=resultSet.getString(3);
                        }
                        
                        else if (type.equalsIgnoreCase("filtro nuevo documento")) {
                            
                            if(i==0)
                                data[i][j]=resultSet.getInt(1);
                            if(i==1)
                                data[i][j]=resultSet.getString(2);
                            if(i==2)
                                data[i][j]=resultSet.getInt(3);
                        }
                        
                        else if (type.equalsIgnoreCase("tabla campo")) {
                            
                            if(i==0)
                                data[i][j]=resultSet.getString(1);
                            if (i==1)
                                data [i][j]=resultSet.getString(2);
                           
                        }
                        else if (type.equalsIgnoreCase("reporte usuarios")) {
                            
                            if(i==0)
                                data[i][j]=resultSet.getString(1);
                            if (i==1)
                                data [i][j]=resultSet.getString(2);
                            if (i==2)
                                data [i][j]=resultSet.getString(3);
                           
                        }
                        else {
                             data[i][j]= resultSet.getObject(i+1);  // Es asi como obtengo 
                        // Los valores, y seran de tipo objeto porque seran 
                        // de varios tipos, de igual manera en columnClass defino que tipo 
                        //sera. 
                        }
                   
                    }
              
                    j++;
               
               }
           
        
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null, "<html><body><pre><p style='width: 600px;'> Error al cargar datos a la tabla : "+type+" <br/>"+ex+"</p></pre></body></html>","Error al cargar datos en tabla", 0);
            
                    
        }
        } // fin del if
        
    }
    public void buildTable(String kind){
        
        //Voy a construir las tablas segun 
        // el tipo que me mande 
        // La tabla de los usuarios ser llamara usuarios
        if (kind.equals("usuarios")){
            columnNames = new String [] {"Id Usuario","Nombre","Apellido","Email"};
            
            numcolumn=4;
            consulta = "SELECT idUsuario,Nombre,Apellido,email from usuario ";
            consulta += extraParameters; // La consulta tendra parametros nuevo si es el caso. 
            
        }
        else if (kind.equalsIgnoreCase("permiso modulos")){
            columnNames = new String [] {"Id Permiso","Nombre", "Descripcion","Acceso"};
            numcolumn=4;
            consulta = "Select IdPermiso,Nombre,Descripcion from permiso WHERE codigo_modulo IS NOT NULL";
            
        }
        // Tabla de Certificacion 
        else if (kind.equalsIgnoreCase("Certificar")){
            columnNames = new String [] {"<html><center><table><tr><td align=\"center\" > CUI </td></tr><tr><td align=\"center\">  </td> </tr></table></center></html>","Dependencia", "<html><center><table><tr><td align=\"center\" > Tipo </td></tr><tr><td align=\"center\"> Documental </td> </tr></table></center></html>","No. Original","No. De Registro","<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Documento </td> </tr></table></center></html>", "Folios"};
            numcolumn=7;
            consulta = "SELECT Nombre_archivo , sub_Fondo.nombre, tipo_documental.nombre, nuevo_numero_unico, nuevo_numero_unico_2, CONCAT(fecha_documento_dia,'/',fecha_documento_mes,'/',fecha_documento_anio), numero_folios";
            consulta += " FROM documento ";
            // Left Join hara que dara importancia a la tabla documento. 
            consulta += 
                        "INNER JOIN tipo_documental ON tipo_documental.idTipo_Documental = documento.Codigo_tipo_documental\n" +
                        "INNER JOIN sub_fondo ON sub_fondo.idSub_Fondo = documento.Codigo_serie\n" ;
                       
            
            consulta += extraParameters; // Asi coloco los datos necesario. 
        }
        else if (kind.equalsIgnoreCase("permiso subfondo")){
            columnNames = new String[] {"Id Permiso", "Nombre", "Descripcion", "Acceso"};
            numcolumn = 4;
            consulta = "SELECT idPermiso, nombre, descripcion FROM permiso WHERE codigo_sub_nivel IS NOT NULL";
        }
        else if (kind.equalsIgnoreCase("filtro nuevo documento")){
            columnNames = new String [] {"Codigo","Nombre", "Tamaño de Columna","Visible"};
            numcolumn=4;
            consulta = "Select IdFiltro_Nuevo_Documento,Nombre_Columna,Tamanio_Columna from filtro_nuevo_documento";
            
        }
        else if (kind.equalsIgnoreCase("tabla campo")){
            columnNames = new String [] {"Nombre campo","Tamaño columna","Visible"};
            numcolumn=3;
            consulta = "Select Nombre_Columna, Tamanio_Columna from filtro_nuevo_documento";
            
        }
        else if (kind.equalsIgnoreCase("reporte usuarios")){
            columnNames = new String [] {"Codigo Usuario","Nombre ","Usuario","Acceso"};
            numcolumn=4;
            consulta = "SELECT idUsuario,CONCAT(nombre,' ',apellido),email FROM usuario ";
            
        }
        else if (kind.equalsIgnoreCase("tipo documental")){
            columnNames = new String [] {"Id Tipo Documental","Nombre"};
            numcolumn=2;
            consulta = "Select * from tipo_documental ";
            
        }
        else if (kind.equalsIgnoreCase("local")){
            columnNames = new String [] {"Id Local","Nombre"};
            numcolumn=2;
            consulta = "Select * from Local ";
            
        }
        else if (kind.equalsIgnoreCase("area")){
            columnNames = new String [] {"Id Area","Nombre"};
            numcolumn=2;
            consulta = "Select * from area ";
            
        }
        else if (kind.equalsIgnoreCase("ambiente")){
            columnNames = new String [] {"Id Ambiente","Nombre"};
            numcolumn=2;
            consulta = "Select * from ambiente ";
            
        }
        else if (kind.equalsIgnoreCase("estanteria")){
            columnNames = new String [] {"Id Estantería","Nombre"};
            numcolumn=2;
            consulta = "Select * from estanteria ";
            
        }
        else if (kind.equalsIgnoreCase("Documentos")|| kind.equalsIgnoreCase("VerDocumento")){
            
            loadColumnClass(); // Cargo el tipo de clase de cada columna. 
            
            consulta = "SELECT idDocumento, Nombre_archivo, Fecha_hora_escaneo, usuario.email, numero_folios";
            ArrayList<String> arrayList = new ArrayList<String>(); 
            arrayList.add("<html><center><table><tr><td align=\"center\" > Correlativo </td></tr><tr><td align=\"center\"> Programa </td> </tr></table></center></html>");
            arrayList.add("CUI");
            arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha y </td></tr><tr><td align=\"cente\"> Hora Escaneo </td> </tr></table></center></html>");
            arrayList.add("<html><center><table><tr><td align=\"center\" > Usuario que </td></tr><tr><td align=\"center\"> Escaneo </td> </tr></table></center></html>");
            arrayList.add("<html><center><table><tr><td align=\"center\" > Numero de </td></tr><tr><td align=\"center\"> Folios </td> </tr></table></center></html>");
            
            /* Este momento obtendre los nombres que esten visibles de la tabla filtro_nuevo_documento. */
            try {

                    st = (Statement) connection.createStatement();
                    resultSet = st.executeQuery("SELECT * FROM filtro_nuevo_documento");
                    metaData = (ResultSetMetaData) resultSet.getMetaData();
                    int numcolumn=metaData.getColumnCount();
                    resultSet.last();                // estas lineas se mueven al final de la consulta 
                    numfilas=resultSet.getRow();

                    
                    resultSet.beforeFirst();
                    String nombre = "";
                    String nombreColumna = "";
                    int acceso = -1 ;
                    
                    while(resultSet.next()){
                        for(int i=0;i<=numcolumn;i++){
                            switch (i){
                                case 2:
                                    nombre = resultSet.getString(2);
                                    break;
                                case 3:
                                    nombreColumna = resultSet.getString(3);
                                    break;
                                case 4:
                                    acceso = resultSet.getInt(4);
                                    break;
                            }
                        }
                        
                        if (acceso == 1){
                            
                            if (nombreColumna.equalsIgnoreCase("Fecha Documento")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > del </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Documento </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                               consulta+=", Fecha_Documento_dia, fecha_documento_mes, fecha_documento_anio";
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Inicial")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               consulta+=", Fecha_extrema_dia_Inicial, fecha_extrema_mes_Inicial, fecha_extrema_anio_Inicial";
                            
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Final")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               consulta+=", Fecha_extrema_dia_Final, fecha_extrema_mes_Final, fecha_extrema_anio_Final";
                            
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Inicial")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Caja Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                               consulta+=", Fecha_extrema_caja_dia_Inicial, fecha_extrema_caja_mes_Inicial, fecha_extrema_caja_anio_Inicial";
                            
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Final")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Caja Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                               consulta+=", Fecha_extrema_caja_dia_Final, fecha_extrema_caja_mes_Final, fecha_extrema_caja_anio_Final";
                            
                            }
                                                        
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Inicial")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Legajo Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                               consulta+=", Fecha_extrema_legajo_dia_Inicial, fecha_extrema_legajo_mes_Inicial, fecha_extrema_legajo_anio_Inicial";
                            
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Final")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Legajo Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                               consulta+=", Fecha_extrema_legajo_dia_Final, fecha_extrema_legajo_mes_Final, fecha_extrema_legajo_anio_Final";
                            
                            }
                            else if (nombreColumna.equalsIgnoreCase("Otra Fecha")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Otra </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > - </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                               consulta+=", Otra_fecha_dia, Otra_fecha_mes, Otra_fecha_anio";
                            
                            }
                            else if (nombreColumna.equalsIgnoreCase("codigo_tipo_documental")){
                                arrayList.add(nombre);
                                consulta+=", tipo_documental.nombre";
                                
                            }
                            else if (nombreColumna.equalsIgnoreCase("codigo_local")){
                                arrayList.add(nombre);
                                consulta+=", local.nombre";
                                
                            }
                            else if (nombreColumna.equalsIgnoreCase("codigo_area")){
                                arrayList.add(nombre);
                                consulta+=", area.nombre";
                                
                            }
                            else if (nombreColumna.equalsIgnoreCase("codigo_ambiente")){
                                arrayList.add(nombre);
                                consulta+=", ambiente.nombre";
                                
                            }
                            else if (nombreColumna.equalsIgnoreCase("codigo_estanteria")){
                                arrayList.add(nombre);
                                consulta+=", estanteria.nombre";
                                
                            }
                            else {
                                // Solo los que su acceso sea 1 hare lo siguiente
                                arrayList.add(nombre);
                                consulta+=","+nombreColumna;
                            }
                        }
                    }


            } catch (SQLException ex) {
                
                 JOptionPane.showMessageDialog(null, "<html><body><pre><p style='width: 600px;'> Error al cargar datos a la tabla : "+type+" <br/>"+ex+"</p></pre></body></html>","Error al cargar datos en tabla", 0);
      
            }

            /*Fin del metodo. */
            Object[] ObjectList = arrayList.toArray();
            String[] StringArray = Arrays.copyOf(ObjectList,ObjectList.length,String[].class);
            
            
           
            columnNames = StringArray ;
            numcolumn=columnNames.length;
            consulta += " FROM documento ";
            // Left Join hara que dara importancia a la tabla documento. 
            consulta += "INNER JOIN usuario ON usuario.idUsuario = documento.usuario_escaneo\n" +
                        "INNER JOIN tipo_documental ON tipo_documental.idTipo_Documental = documento.Codigo_tipo_documental\n" +
                        "LEFT JOIN local ON local.idLocal = documento.Codigo_Local\n" +
                        "LEFT JOIN area ON area.idArea = documento.Codigo_Area\n" +
                        "LEFT JOIN ambiente ON ambiente.idAmbiente = documento.Codigo_Ambiente\n" +
                        "LEFT JOIN estanteria ON estanteria.idEstanteria = documento.Codigo_Estanteria";
            
            consulta += extraParameters; // Asi coloco los datos necesario. 
            
           
            
        }
        else if (kind.equalsIgnoreCase("newUsersTable")){
            
            loadColumnClass(); // Cargo el tipo de clase de cada columna. 
            
            // Esta tabla es la de ingreso de usuario
            
            ArrayList<String> arrayList = new ArrayList<String>(); 
            arrayList.add("<html><center><table><tr><td align=\"center\" > Correlativo </td></tr><tr><td align=\"center\"> Programa </td> </tr></table></center></html>");
            
            arrayList.add("CUI");
            arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha y </td></tr><tr><td align=\"cente\"> Hora Escaneo </td> </tr></table></center></html>");
            arrayList.add("<html><center><table><tr><td align=\"center\" > Usuario que </td></tr><tr><td align=\"center\"> Escaneo </td> </tr></table></center></html>");
            arrayList.add("<html><center><table><tr><td align=\"center\" > Numero de </td></tr><tr><td align=\"center\"> Folios </td> </tr></table></center></html>");
            
            /* Este momento obtendre los nombres que esten visibles de la tabla filtro_nuevo_documento. */
            try {

                    st = (Statement) connection.createStatement();
                    resultSet = st.executeQuery("SELECT * FROM filtro_nuevo_documento");
                    metaData = (ResultSetMetaData) resultSet.getMetaData();
                    int numcolumn=metaData.getColumnCount();
                    resultSet.last();                // estas lineas se mueven al final de la consulta 
                    numfilas=resultSet.getRow();

                    
                    resultSet.beforeFirst();
                    String nombre = "";
                    String nombreColumna = "";
                    int acceso = -1 ;
                    
                    while(resultSet.next()){
                        for(int i=0;i<=numcolumn;i++){
                            switch (i){
                                case 2:
                                    nombre = resultSet.getString(2);
                                    break;
                                case 3:
                                    nombreColumna = resultSet.getString(3);
                                    break;
                                case 4:
                                    acceso = resultSet.getInt(4);
                                    break;
                            }
                        }
                        
                        if (acceso == 1){
                            
                            if (nombreColumna.equalsIgnoreCase("Fecha Documento")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > del </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Documento </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Inicial")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Final")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Inicial")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Caja Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Final")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Caja Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Inicial")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Legajo Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Final")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Legajo Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Otra Fecha")){
                                

                               arrayList.add("<html><center><table><tr><td align=\"center\" > Otra </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > - </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>");
                               arrayList.add("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>");
                               
                            }
                            else {
                                // Solo los que su acceso sea 1 hare lo siguiente
                                
                                arrayList.add(nombre);
                            }
                            
                        }
                    }


            } catch (SQLException ex) {
                
                JOptionPane.showMessageDialog(null, "<html><body><pre><p style='width: 600px;'> Error al cargar datos a la tabla : "+kind+" <br/>"+ex+"</p></pre></body></html>","Error al cargar datos en tabla", 0);
      
            }

            /*Fin del metodo. */
            Object[] ObjectList = arrayList.toArray();
            String[] StringArray = Arrays.copyOf(ObjectList,ObjectList.length,String[].class);
            
            
           
            columnNames = StringArray ;
            numcolumn=columnNames.length;
            
            
            data = new Object [numcolumn][1];
            
            
        }
        
        
         
    }
    
    /*
     * Metodo para darle el tipo de columna, a cada columna. 
     * 
     */
    /* Este momento obtendre los nombres que esten visibles de la tabla filtro_nuevo_documento. */
     public void loadColumnClass(){  
         ArrayList<Class> classList = new ArrayList<Class>(); 
         try {

                    st = (Statement) connection.createStatement();
                    resultSet = st.executeQuery("SELECT * FROM filtro_nuevo_documento");
                    metaData = (ResultSetMetaData) resultSet.getMetaData();
                    int numcolumn=metaData.getColumnCount();
                    resultSet.last();                // estas lineas se mueven al final de la consulta 
                    numfilas=resultSet.getRow();

                    
                    resultSet.beforeFirst();
                    String nombre = "";
                    String nombreColumna = "";
                    int acceso = -1 ;
                    
                    while(resultSet.next()){
                        for(int i=0;i<=numcolumn;i++){
                            switch (i){
                                case 2:
                                    nombre = resultSet.getString(2);
                                    break;
                                case 3:
                                    nombreColumna = resultSet.getString(3);
                                    break;
                                case 4:
                                    acceso = resultSet.getInt(4);
                                    break;
                            }
                        }
                        
                        if (acceso == 1){
                            
                            if (nombreColumna.equalsIgnoreCase("Fecha Documento")){
                               
                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Inicial")){
                                

                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               classList.add(Integer.class); 
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Final")){
                                

                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               classList.add(Integer.class); 
                               
                            }
                            
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Inicial")){
                                

                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               classList.add(Integer.class);   
                               
                            }
                            
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Final")){
                                

                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               classList.add(Integer.class);   
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Inicial")){
                                

                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Final")){
                                

                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Otra Fecha")){
                                

                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               classList.add(Integer.class);
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Asunto")||nombreColumna.equalsIgnoreCase("Campos_Especificos")||nombreColumna.equalsIgnoreCase("Observaciones")){
                                

                               classList.add(JTextArea.class);
                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Orden_Alfabetico")||nombreColumna.equalsIgnoreCase("Orden_Correlativo")||nombreColumna.equalsIgnoreCase("Nuevo_Numero_Unico")||nombreColumna.equalsIgnoreCase("Nuevo_Numero_Unico_2")){
                                
                                classList.add(String.class);
                                
                            }
                            else {
                                // Solo los que su acceso sea 1 hare lo siguiente
                               classList.add(Integer.class);
                               
                            }
                        }
                    }


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar clase de datos a la tabla : Nuevo Documento \n"+ex,"Error al cargar datos", 0);
            }
            
            Object[] ol = classList.toArray();
            classArray = Arrays.copyOf(ol,ol.length,Class[].class);
     }
    
    /* ------------------------------------
    // Metodos propios de AbastracTableModel 
     * ------------------------------------ */
    @Override
    public Class getColumnClass (int col){ // Regresa el tipo de columna. 
        if (type.equals("permiso modulos"))
            if (col==3)
                return Boolean.class;
            else if (col==0)
                return Integer.class;
            else 
                return String.class;
        
        else if (type.equals("permiso subfondo"))
            if (col==3)
                return Boolean.class;
            else if (col==0)
                return Integer.class;
            else
                return String.class;
            
        else  if (type.equals("filtro nuevo documento"))
            if (col==3)
                return Boolean.class;
            else if (col==0 || col == 2) 
                return Integer.class;
            else 
                return String.class;
        else  if (type.equals("tabla campo"))
            if (col==2)
                return Boolean.class;
            else if (col ==1)
                return Integer.class;
            else 
                return String.class;
        else if (type.equalsIgnoreCase("reporte usuarios"))
            if (col==3)
                return Boolean.class;
            else 
                return String.class;
        else if (type.equalsIgnoreCase("newUsersTable")||type.equalsIgnoreCase("verDocumento")){
            if (col < 5 )
                return String.class;
            else{
                return classArray[col-5];
            }
                
            
        }
        else 
        return String.class; // el default sera String al menos de que el tipo lo cambie
    }
    
    @Override
    public String getColumnName(int col){
       
        return columnNames[col];  
    }
    
    @Override
    public boolean isCellEditable (int row, int col){ // dejo que editen algunas columnas (Empieza en 0)
        if (type.equalsIgnoreCase("permiso modulos"))
            if (col==3)
                return true;
            else
                return false;
        
        else if(type.equals("permiso subfondo"))
            if(col==3)
                return true;
            else
                return false;
        
        else if (type.equalsIgnoreCase("newUsersTable")||type.equalsIgnoreCase("VerDocumento"))
            if (col < 5)
                return false;
            else 
                return true;
        else if (type.equalsIgnoreCase("filtro nuevo documento"))
            if (col == 3 || col == 2)
                return true;
            else return false;
        else if (type.equalsIgnoreCase("tabla campo"))
            if (col == 1 || col == 2)
                return true;
            else return false;
        else if (type.equalsIgnoreCase("reporte usuarios"))
            if (col==3)
                return true;
            else 
                return false;
            
        
        else
            return false; // en default no tendra que editar nada, almenos que exista algun tipo y cambie esto. 
    }
    
    @Override
    public int getRowCount() {
        if (type.equalsIgnoreCase("newUsersTable"))
            return 1;
        else
            return numfilas; // no va en un try and catch por qee no ocasionara un problema
    }

    
    @Override
    public int getColumnCount() {
        
        return numcolumn;
    }

    
    @Override
    public Object getValueAt(int row, int col) {
        return data[col][row];
    }
    
    
    
    @Override
    public void setValueAt(Object value, int row, int col){
      data[col][row]= value;
      fireTableCellUpdated(row,col);
      
      
    }
    
    
    
    
    
}
