/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualdocumentslibrary;

import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Javier_Alay
 */
public class BDEstructura extends DataBaseClass{
    
    public static List<String[]> getDataFromSub_Fondo(){
        List<String[]> data = new ArrayList<String[]>();
        try {
                //obtengo los campos de la base de datos
                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery("SELECT identificador,nombre,padre FROM sub_fondo ORDER BY nombre DESC, padre DESC;");
                metaData = (ResultSetMetaData) resultSet.getMetaData();                              
                
                
                while(resultSet.next()){
                    
                       data.add(new String[]{resultSet.getString("identificador"),resultSet.getString("nombre"),resultSet.getString("padre")});
                       
               }
  
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "<html><body><pre><p style='width: 600px;'> Error al cargar datos a la tabla : Estructura <br/>"+ex+"</p></pre></body></html>","Error en base de datos", 0);
        }
        
        return data;
        
    }
    
    public static String[] getIdentificadores(String name, String fondo){
        
        String[] identificador = {"",""};
        
        try {
            st = (Statement) connection.createStatement();
            if(fondo.equals("null") || name.equals(fondo)){
                resultSet = st.executeQuery("SELECT Identificador FROM Sub_Fondo WHERE Nombre='"+name+"' AND Fondo IS NULL;");
            }
            else{
                resultSet = st.executeQuery("SELECT Identificador FROM Sub_Fondo WHERE Nombre='"+name+"' AND Fondo='"+fondo+"';");
            }
            
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            
            while(resultSet.next()){
                
                identificador[0] = resultSet.getString("Identificador");
                       
            }
            
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM sub_fondo;");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            resultSet.last();
            identificador[1] = ""+(Integer.parseInt(resultSet.getString("idSub_Fondo"))+1);
           
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return identificador;
    }
    
    public static String[] getIdentificadoresByUser(String name){
        
        String[] identificador = {"",""};
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT Identificador_Por_Usuario FROM Sub_Fondo WHERE Nombre='"+name+"' ;");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            
            while(resultSet.next()){
                
                identificador[0] = resultSet.getString("Identificador_Por_Usuario");
                       
            }
            
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM sub_fondo;");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            resultSet.last();
            identificador[1] = ""+(Integer.parseInt(resultSet.getString("idSub_Fondo"))+1);
           
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return identificador;
    }
    
    public static String[] getChilds(String padre){
        String[] childs = null;
        
        try {
                //obtengo los campos de la base de datos
                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery("SELECT identificador FROM sub_fondo WHERE padre = '"+padre+"';");
                metaData = (ResultSetMetaData) resultSet.getMetaData();                              
                
                resultSet.last();               
                childs = new String[resultSet.getRow()];
                resultSet.beforeFirst();
                
                int contador = 0;
                
                while(resultSet.next()){
                    
                       childs[contador] = resultSet.getString("identificador");
                       contador ++;
               }
  
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "<html><body><pre><p style='width: 600px;'> Error al cargar datos a la tabla : Estructura <br/>"+ex+"</p></pre></body></html>","Error en base de datos", 0);
       }
        
        return childs;
    }
    
    
    public static int getIdSub_Fondo(String name, String fondo){
        int id = -1;
        
        try {
            st = (Statement) connection.createStatement();
            if(fondo.equals("null") || name.equals(fondo)){
                resultSet = st.executeQuery("SELECT idSub_Fondo FROM Sub_Fondo WHERE Nombre='"+name+"' AND Fondo IS NULL;");
            }
            else{
                resultSet = st.executeQuery("SELECT idSub_Fondo FROM Sub_Fondo WHERE Nombre='"+name+"' AND Fondo = '"+fondo+"';");
            }
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            
            while(resultSet.next()){
                
                id = resultSet.getInt("idSub_Fondo");
                       
            }
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id;
    }
    
    public static int getIdSub_FondoFromIdentificador(String identificador){
        int id = 0;
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT idSub_Fondo FROM Sub_Fondo WHERE Identificador='"+identificador+"';");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            
            while(resultSet.next()){
                
                id = resultSet.getInt("idSub_Fondo");
                       
            }
            
            
               
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id;
    }
    
    public static int getLastId(){
        int id = 0;
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM Sub_Fondo ;");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            resultSet.last();
            id = resultSet.getInt("idSub_Fondo");
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id;
    }
    
    public static int isSerie(String name, String fondo){
        int isSerie = -1;
         
        try {
            st = (Statement) connection.createStatement();
             if(fondo.equals("null") || name.equals(fondo)){
                 resultSet = st.executeQuery("SELECT isSerie FROM Sub_Fondo WHERE Nombre='"+name+"' AND Fondo IS NULL;");
             }
             else{
                 resultSet = st.executeQuery("SELECT isSerie FROM Sub_Fondo WHERE Nombre='"+name+"' AND Fondo = '"+fondo+"';");
             }
            
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            
            while(resultSet.next()){
                
                isSerie = resultSet.getInt("isSerie");
                       
            }
            
            
               
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isSerie;
    }
    
    public static String getMotherPath(){
        String motherPath = "";
         
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT motherPath FROM configuracion WHERE idConfiguracion=1;");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            
            while(resultSet.next()){
                
                motherPath = resultSet.getString("motherPath");
                       
            }
            
            
               
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return motherPath;
    }
    
    public static String getName(String identificador){
        String nombre = "";
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT Nombre FROM Sub_Fondo WHERE Identificador='"+identificador+"';");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            
            while(resultSet.next()){
                
                nombre = resultSet.getString("Nombre");
                       
            }
            
            
               
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nombre;
    }
    
    public static String getFondoFromFondo(String identificador){
        String fondo = "";
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT Fondo FROM Sub_Fondo WHERE Identificador='"+identificador+"';");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            
            while(resultSet.next()){
                
                fondo = resultSet.getString("Fondo");
                       
            }
            
            
               
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fondo;
    }
    
    public static int getLastIdPermiso(){
        int id = 0;
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM permiso ;");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            resultSet.last();
            id = resultSet.getInt("idPermiso");
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id;
    }
    
    public static int getPermiso(int userId, int idSubFondo){
        int permiso = -1;
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT acces, sub_fondo.idSub_Fondo from usuario_permiso, permiso, sub_fondo WHERE codigo_permiso = idpermiso AND codigo_sub_nivel = idSub_fondo AND codigo_modulo IS NULL AND  codigo_usuario = "+userId+";");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            while(resultSet.next()){
                
                int id = resultSet.getInt("idSub_Fondo");
                int access = resultSet.getInt("acces");
                if(id == idSubFondo){
                    permiso = access;
                }
                       
            }
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return permiso;
    }
    
    public static int getPermisoWithId(int userId, String idIn){
        int permiso = -1;
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT acces, sub_fondo.identificador from usuario_permiso, permiso, sub_fondo WHERE codigo_permiso = idpermiso AND codigo_sub_nivel = idSub_fondo AND codigo_modulo IS NULL AND  codigo_usuario = "+userId+";");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
            while(resultSet.next()){
                
                String id = resultSet.getString("identificador");
                int access = resultSet.getInt("acces");
                if(id.equals(idIn)){
                    permiso = access;
                }
                       
            }
            
            
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return permiso;
    }
    
    public static int[] getIdFromUsers(){
        int[] ids = null;
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT idUsuario from usuario;");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            resultSet.last();
            ids = new int[resultSet.getRow()];
            resultSet.beforeFirst();
            int pos = 0;
            while(resultSet.next()){
                
                ids[pos] = resultSet.getInt("idUsuario");
                pos++;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ids;
    }
    
    public static int getIdFromPermiso(int codigoSN){
        int id = -1;
        
        try {
            st = (Statement) connection.createStatement();
            
            resultSet = st.executeQuery("SELECT idPermiso FROM permiso WHERE codigo_sub_nivel="+codigoSN+";");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
         
            while(resultSet.next()){
                
                id = resultSet.getInt("idPermiso");
       
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id;
    }
    
    public static Deque<String> getNodesFromTheSameParent(String parent){
        Deque<String> nodes = new ArrayDeque<>();
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT Nombre FROM sub_fondo WHERE Padre='"+parent+"';");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
         
            while(resultSet.next()){
                
                nodes.push(resultSet.getString("Nombre"));
       
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nodes;
    
    }
    
    public static Deque<String> getIdsFromTheSameParent(String parent){
        Deque<String> ids = new ArrayDeque<>();
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT Identificador_Por_Usuario FROM sub_fondo WHERE Padre='"+parent+"';");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
         
            while(resultSet.next()){
                
                ids.push(resultSet.getString("Identificador_Por_Usuario"));
       
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ids;
    
    }
    
    public static String getPathFromDocument(int idIn){
        String path = "";
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT Path FROM documento WHERE idDocumento="+idIn+";");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
         
            while(resultSet.next()){
                
                path = resultSet.getString("Path");
       
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return path;
    
    }
    
    public static String getPathFromDocumentWithName(String name){
        String path = "";
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT Path FROM documento WHERE Nombre_Archivo='"+name+"';");
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
         
            while(resultSet.next()){
                
                path = resultSet.getString("Path");
       
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return path;
    
    }
    
    public static String getIdUser(String nombre, String fondo){
        String idUser = "";
        
        try {
            st = (Statement) connection.createStatement();
            if(fondo.equals("null") || nombre.equals(fondo)){
                 resultSet = st.executeQuery("SELECT Identificador_por_usuario FROM sub_fondo WHERE Nombre='"+nombre+"' AND Fondo IS NULL;");
             }
             else{
                 resultSet = st.executeQuery("SELECT Identificador_por_usuario FROM sub_fondo WHERE Nombre='"+nombre+"' AND Fondo = '"+fondo+"';");
             }
            
            metaData = (ResultSetMetaData) resultSet.getMetaData();
            
         
            while(resultSet.next()){
                
                idUser = resultSet.getString("Identificador_por_usuario");
       
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idUser;
    }
    public static int getSerieCount(String nombre, String fondo){
        int serieCount = 0;
        int idSerie = getIdSub_Fondo(nombre,fondo);
        
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT COUNT(*) AS cantidad FROM Documento WHERE Codigo_serie = "+idSerie);
             
            
            metaData = (ResultSetMetaData) resultSet.getMetaData(); 
            
         
            
            while(resultSet.next()){
                serieCount = resultSet.getInt("cantidad");
                
            }
            serieCount ++;
            
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return serieCount;
    }
}
