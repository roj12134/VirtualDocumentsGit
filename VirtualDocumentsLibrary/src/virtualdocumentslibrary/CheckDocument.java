 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualdocumentslibrary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import static virtualdocumentslibrary.MasterMainView.setColumnWidthDocuments;

/**
 *
 * @author giovannirojas
 */
public class CheckDocument extends javax.swing.JFrame {

    private TipoDocumental dataTipoDocumentalList [];
    private Local dataLocalList [];
    private Area dataAreaList [];
    private Ambiente dataAmbienteList [];
    private Estanteria dataEstanteriaList [];
    
    
    private boolean processGood = false;
    
    
    // Id que esta ingresado, util para guardarlo. 
    private int idIn = -1;
    
    
    private Statement st = DataBaseClass.getSt();
    private Connection connection = DataBaseClass.getConnection();
    private ResultSet resultSet = DataBaseClass.getResultSet();
    
    
    // Aqui creare los combo box para tipo Documental, local, area, ambiente, estanteria.
    private JComboBox tipo_Documental_Box = new JComboBox();
    private JComboBox area_Box = new JComboBox();
    private JComboBox ambiente_Box = new JComboBox();
    private JComboBox estanteria_Box = new JComboBox();
    private JComboBox local_Box = new JComboBox();
    
    // Fin de las variables del combo box 
    
    //variables para el arbol
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    private String[] identificador;
    private String identificadorActual;
    private Deque<String[]> children;
    private Deque<Integer> contadorP;
    //fin variables para el árbol
    
    
    // Variable del path 
    private String pathFile = "";
    private String nameFile = "";
    
    private String preparedQuery;
    
    private int code = -1;
    
    //variable para conexion remota
    private RemoteConnection remoteConnection;
    
    public CheckDocument(int num) {
        initComponents();
        //inicializo la variable para conexion remota
        String[] datosFromConfig = ConfigClass.configReader();
        remoteConnection = new RemoteConnection(datosFromConfig[0],datosFromConfig[10],datosFromConfig[11]);
        // el codigo a cambiar 
        this.idIn = num;
        Object[] data = new DataBaseClass().giveData("SELECT Path, nombre_archivo FROM Documento WHERE idDocumento = "+num);
        pathFile = data[0].toString();
        nameFile = data[1].toString();
        
        //Set the icon of the app. 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png"))); 
        this.setTitle("Virtual Archive");
        
        // Configuro el color de la pantalla 
        labelPanel.setBackground(ConfigClass.getColorApp());
        labelName.setText("Ver documento codigo : "+num);
       
        newTable.setModel(new ResultSetTableModel("verDocumento"," WHERE idDocumento ="+num)); // la tabla de nuevos
        
        this.code = num;
        // Tamanio de las columnas 
        setColumnWidth();
        
        
        setDefaultData();
        
        //Columnas especiales que son comboBox de la tabla de nuevos. 
        setComboBoxColumn();
        
        
        //Columnas especiales del JTextArea 
        setTextArea();
        
        // Coloco el filtro a los dias del mes, anio y dias. 
        setFilterDays();
        
        
        
        
        
        // fin de configuracion de tabla. 
        
        
        
        
        // Query to Insert 
        preparedQuery = "UPDATE Documento SET IdDocumento=?,Path=?,Nombre_Archivo=?,Fecha_Hora_Escaneo=?,Usuario_Escaneo=?,Numero_Folios=? WHERE idDocumento = "+num;
    
    
    }

    private CheckDocument() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    /*
     *  Lo que hace esto es que busca las tablas que son TextArea y si existe entonces coloca
     *  un TextArea
     */
    public void setTextArea(){
        for (int i=0; i!= newTable.getColumnCount(); i++){
            if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Asunto")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Campos </td></tr><tr><td align=\"center\"> Especificos </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("Observaciones")){
                newTable.getColumnModel().getColumn(i).setCellEditor(new TextAreaCell(newTable));
                newTable.getColumnModel().getColumn(i).setCellRenderer(new TextAreaCellView());
            }
        }
        
    }
    
    
    /*
     * Lo que hace este metodo es que coloca un filtro de 
     * limite de numeros por dia, mes y año. 
     */
    private void setFilterDays(){
        for (int i=0; i!= newTable.getColumnCount(); i++){
            if (newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Otra </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>")){
                newTable.getColumnModel().getColumn(i).setCellEditor(new FilterDaysNumbers(0, 31));
                int dia = -1;
                try {
                    dia = Integer.parseInt(newTable.getModel().getValueAt(0, i).toString());
                }catch (Exception e){
                    dia = 0;
                    
                }
                if (dia==0) // Si es cero coloco cero, sino no coloco cero 
                    newTable.getModel().setValueAt(0, 0, i);
                
                
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > del </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > - </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>")){
                newTable.getColumnModel().getColumn(i).setCellEditor(new FilterDaysNumbers(0, 12));
                int mes = -1;
                try {
                    mes = Integer.parseInt(newTable.getModel().getValueAt(0, i).toString());
                }catch (Exception e){
                    mes = 0;
                    
                }
                if (mes==0) // Si es cero coloco cero, sino no coloco cero 
                    newTable.getModel().setValueAt(0, 0, i);
                
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Documento </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Caja Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Caja Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Legajo Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Legajo Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")||newTable.getModel().getColumnName(i).equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")){
                newTable.getColumnModel().getColumn(i).setCellEditor(new FilterDaysNumbers(1000, 9999));
                int anio = -1;
                try {
                    anio = Integer.parseInt(newTable.getModel().getValueAt(0, i).toString());
                }catch (Exception e){
                    anio = 0;
                    
                }
                if (anio==0) // Si es cero coloco cero, sino no coloco cero 
                    newTable.getModel().setValueAt(0, 0, i);
                
            }
            
            // Aqui tambien le pongo filtros a los enteros. 
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Legajo")||newTable.getModel().getColumnName(i).equalsIgnoreCase("Caja")){
                newTable.getColumnModel().getColumn(i).setCellEditor(new FilterDaysNumbers(0, Integer.MAX_VALUE));
                newTable.getModel().setValueAt(0, 0, i);
            }
        }
        
    }
    
    
    /*
     * Coloca comboBox aquellas columnas que lo requieran. 
     */
    private void setComboBoxColumn () {
        
        for (int i=0; i!= newTable.getColumnCount(); i++){
            if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Tipo Documental")){
                tipoDocumentalList(i);
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Area")){
                 areaList(i);
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Local")){
                 localList(i);
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Ambiente")){
                 ambienteList(i);
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Estanteria")){
                 estanteriaList(i);
            }
        }
      
    }
    /*
     * Este metodo lo que hace es que 
     * coloca el tamanio de las columnas 
     * para que sea mejor apreciado al 
     * momento de ingresar datos
     */
    public void setColumnWidth(){
        newTable.getColumnModel().getColumn(0).setPreferredWidth(83);
        newTable.getColumnModel().getColumn(1).setPreferredWidth(85);
        newTable.getColumnModel().getColumn(2).setPreferredWidth(144);
        newTable.getColumnModel().getColumn(3).setPreferredWidth(199);
        newTable.getColumnModel().getColumn(4).setPreferredWidth(92);
        
       
        
        /* Aqui voy a empezar a generar el tamanio de las columnas segun los cheques de la 
         tabla filtro nuevo documento */
        
        // Index column
        int indexColumn = 5;
        /* Este momento obtendre los nombres que esten visibles de la tabla filtro_nuevo_documento. */
        try {

                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery("SELECT * FROM filtro_nuevo_documento");
                ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                int numcolumn=metaData.getColumnCount();
                
                int tamanio = -1;
                String nombreColumna = "";
                int acceso = -1 ;

                while(resultSet.next()){
                    for(int i=0;i<=numcolumn;i++){
                        switch (i){
                            
                            case 3:
                                nombreColumna = resultSet.getString(3);
                                break;
                            case 4:
                                acceso = resultSet.getInt(4);
                                break;
                            case 5:
                                tamanio = resultSet.getInt(5);
                                break;
                        }
                    }

                    if (acceso == 1){

                        if (nombreColumna.equalsIgnoreCase("Fecha Documento")){


                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           
                        }
                        else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Inicial")){


                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           
                        }
                        else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Final")){


                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           
                        }
                        else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Inicial")){


                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           
                        }
                        else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Final")){


                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           
                        }
                        else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Inicial")){


                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                        }
                        else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Final")){


                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                        }
                        else if (nombreColumna.equalsIgnoreCase("Otra Fecha")){


                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                           newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                           indexColumn++;
                        }
                        else {
                             
                             newTable.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                             indexColumn ++ ;
                        }
                    }
                    
                } // Fin del while


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos a la tabla : Nuevo Documento \n"+ex,"Error en base de datos", 0);
        }
        
       
    }
    
    
    
    /*
     * Este metodo coloca los datos que debe de tener la tabla de parte del programa. 
     */
    private void setDefaultData() {
        
            newTable.getModel().setValueAt(getCurrentDate(), 0, 2);
        
            newTable.getModel().setValueAt(MasterMainView.getUserIn().getCodigo(), 0, 3);
        
            newTable.getModel().setValueAt(getPDFCountPages(), 0, 4);
        
                    
    }

    private class TipoDocumental {
        private int codigo;
        private String nombre;

        public TipoDocumental(int codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        
        
    }
    
    
    private void tipoDocumentalList (int p){
    
        
     
            try {

                        st = (Statement) connection.createStatement();
                        resultSet = st.executeQuery("Select idTipo_Documental, nombre from Tipo_Documental ORDER BY Nombre");
                        ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

                        resultSet.last();                // estas lineas se mueven al final de la consulta 

                        int numrows=resultSet.getRow();
                        int numcolumn=metaData.getColumnCount();

                        dataTipoDocumentalList = new TipoDocumental [numrows];  //la cantidad de material que va a salir 

                        resultSet.beforeFirst();

                        int pos=0;
                        while(resultSet.next()){
                            String nom="";
                            int cod =0;
                            String pre="";

                            for(int i=0;i<=numcolumn;i++){
                                switch(i){
                                    case 2 :
                                       nom=resultSet.getString(2);
                                       break;
                                    case 1:
                                        cod=resultSet.getInt(1);
                                        break;
                                    


                                  }
                            }

                            dataTipoDocumentalList[pos]= new TipoDocumental (cod,nom);
                            pos++;
                        }


                     tipo_Documental_Box.removeAllItems();  // limpio el comboBox
                     
                     for(int i=0;i<=dataTipoDocumentalList.length-1;i++){
                        tipo_Documental_Box.addItem(dataTipoDocumentalList[i].getNombre());
                    }


                } catch (SQLException ex) {
                    Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
                }


                
                AutoCompleteDecorator.decorate(tipo_Documental_Box);
                
                Object[] data = new DataBaseClass().giveData("SELECT tipo_documental.nombre FROM Documento LEFT JOIN tipo_documental ON tipo_documental.idTipo_Documental = documento.codigo_tipo_Documental WHERE idDocumento = "+code);
                
               
                
                tipo_Documental_Box.setSelectedIndex(0);
                newTable.getColumnModel().getColumn(p).setCellEditor(new ComboBoxCellEditor(tipo_Documental_Box));
                
                 //Set up tool tips for the sport cells.
                DefaultTableCellRenderer renderer;
                renderer = new DefaultTableCellRenderer();
                renderer.setToolTipText("Click para cambiar el Tipo Documental");
                newTable.getColumnModel().getColumn(p).setCellRenderer(renderer);
    
    }
    
    private class Local {
        private int codigo;
        private String nombre;

        public Local(int codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        
        
    }
    
    private void localList (int p){
    
        
     
            try {

                        st = (Statement) connection.createStatement();
                        resultSet = st.executeQuery("Select idLocal, nombre from Local ORDER BY Nombre");
                        ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

                        resultSet.last();                // estas lineas se mueven al final de la consulta 

                        int numrows=resultSet.getRow();
                        int numcolumn=metaData.getColumnCount();

                        dataLocalList = new Local [numrows];  //la cantidad de material que va a salir 

                        resultSet.beforeFirst();

                        int pos=0;
                        while(resultSet.next()){
                            String nom="";
                            int cod =0;
                            String pre="";

                            for(int i=0;i<=numcolumn;i++){
                                switch(i){
                                    case 2 :
                                       nom=resultSet.getString(2);
                                       break;
                                    case 1:
                                        cod=resultSet.getInt(1);
                                        break;
                                    


                                  }
                            }

                            dataLocalList[pos]= new Local (cod,nom);
                            pos++;
                        }


                     local_Box.removeAllItems();  // limpio el comboBox
                     local_Box.addItem("Nulo");
                     for(int i=0;i<=dataLocalList.length-1;i++){
                        local_Box.addItem(dataLocalList[i].getNombre());
                    }


                } catch (SQLException ex) {
                    Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
                }


                local_Box.setSelectedIndex(0);
                AutoCompleteDecorator.decorate(local_Box);
                newTable.getColumnModel().getColumn(p).setCellEditor(new ComboBoxCellEditor(local_Box));
      
                 //Set up tool tips for the sport cells.
                DefaultTableCellRenderer renderer;
                renderer = new DefaultTableCellRenderer();
                renderer.setToolTipText("Click para cambiar Local");
                newTable.getColumnModel().getColumn(p).setCellRenderer(renderer);
    
    }
    
    private class Area {
        private int codigo;
        private String nombre;

        public Area(int codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        
        
    }
    
    private void areaList (int p){
    
        
     
            try {

                        st = (Statement) connection.createStatement();
                        resultSet = st.executeQuery("Select idArea, nombre from Area ORDER BY Nombre");
                        ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

                        resultSet.last();                // estas lineas se mueven al final de la consulta 

                        int numrows=resultSet.getRow();
                        int numcolumn=metaData.getColumnCount();

                        dataAreaList = new Area [numrows];  //la cantidad de material que va a salir 

                        resultSet.beforeFirst();

                        int pos=0;
                        while(resultSet.next()){
                            String nom="";
                            int cod =0;
                            String pre="";

                            for(int i=0;i<=numcolumn;i++){
                                switch(i){
                                    case 2 :
                                       nom=resultSet.getString(2);
                                       break;
                                    case 1:
                                        cod=resultSet.getInt(1);
                                        break;
                                    


                                  }
                            }

                            dataAreaList[pos]= new Area (cod,nom);
                            pos++;
                        }


                     area_Box.removeAllItems();  // limpio el comboBox
                     area_Box.addItem("Nulo");
                     for(int i=0;i<=dataAreaList.length-1;i++){
                        area_Box.addItem(dataAreaList[i].getNombre());
                    }


                } catch (SQLException ex) {
                    Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
                }


                area_Box.setSelectedIndex(0);
                AutoCompleteDecorator.decorate(area_Box);
                newTable.getColumnModel().getColumn(p).setCellEditor(new ComboBoxCellEditor(area_Box));
      
                 //Set up tool tips for the sport cells.
                DefaultTableCellRenderer renderer;
                renderer = new DefaultTableCellRenderer();
                renderer.setToolTipText("Click para cambiar el Area");
                newTable.getColumnModel().getColumn(p).setCellRenderer(renderer);
    
    }
    
    private class Ambiente {
        private int codigo;
        private String nombre;

        public Ambiente(int codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        
        
    }
    
    private void ambienteList (int p){
    
        
     
            try {

                        st = (Statement) connection.createStatement();
                        resultSet = st.executeQuery("Select idAmbiente, nombre from Ambiente ORDER BY Nombre");
                        ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

                        resultSet.last();                // estas lineas se mueven al final de la consulta 

                        int numrows=resultSet.getRow();
                        int numcolumn=metaData.getColumnCount();

                        dataAmbienteList = new Ambiente [numrows];  //la cantidad de material que va a salir 

                        resultSet.beforeFirst();

                        int pos=0;
                        while(resultSet.next()){
                            String nom="";
                            int cod =0;
                            String pre="";

                            for(int i=0;i<=numcolumn;i++){
                                switch(i){
                                    case 2 :
                                       nom=resultSet.getString(2);
                                       break;
                                    case 1:
                                        cod=resultSet.getInt(1);
                                        break;
                                    


                                  }
                            }

                            dataAmbienteList[pos]= new Ambiente (cod,nom);
                            pos++;
                        }


                     ambiente_Box.removeAllItems();  // limpio el comboBox
                     ambiente_Box.addItem("Nulo");
                     for(int i=0;i<=dataAmbienteList.length-1;i++){
                        ambiente_Box.addItem(dataAmbienteList[i].getNombre());
                    }


                } catch (SQLException ex) {
                    Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
                }


                ambiente_Box.setSelectedIndex(0);
                AutoCompleteDecorator.decorate(ambiente_Box);
                newTable.getColumnModel().getColumn(p).setCellEditor(new ComboBoxCellEditor(ambiente_Box));
      
                 //Set up tool tips for the sport cells.
                DefaultTableCellRenderer renderer;
                renderer = new DefaultTableCellRenderer();
                renderer.setToolTipText("Click para cambiar el Ambiente");
                newTable.getColumnModel().getColumn(p).setCellRenderer(renderer);
    
    }
    
    private class Estanteria {
        private int codigo;
        private String nombre;

        public Estanteria(int codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }

        public int getCodigo() {
            return codigo;
        }

        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        
        
    }
    
    private void estanteriaList (int p){
    
        
     
            try {

                        st = (Statement) connection.createStatement();
                        resultSet = st.executeQuery("Select idEstanteria, nombre from Estanteria ORDER BY Nombre");
                        ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

                        resultSet.last();                // estas lineas se mueven al final de la consulta 

                        int numrows=resultSet.getRow();
                        int numcolumn=metaData.getColumnCount();

                        dataEstanteriaList = new Estanteria [numrows];  //la cantidad de material que va a salir 

                        resultSet.beforeFirst();

                        int pos=0;
                        while(resultSet.next()){
                            String nom="";
                            int cod =0;
                            String pre="";

                            for(int i=0;i<=numcolumn;i++){
                                switch(i){
                                    case 2 :
                                       nom=resultSet.getString(2);
                                       break;
                                    case 1:
                                        cod=resultSet.getInt(1);
                                        break;
                                    


                                  }
                            }

                            dataEstanteriaList[pos]= new Estanteria (cod,nom);
                            pos++;
                        }


                     estanteria_Box.removeAllItems();  // limpio el comboBox
                     estanteria_Box.addItem("Nulo");
                     for(int i=0;i<=dataEstanteriaList.length-1;i++){
                        estanteria_Box.addItem(dataEstanteriaList[i].getNombre());
                    }


                } catch (SQLException ex) {
                    Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
                }


                estanteria_Box.setSelectedIndex(0);
                AutoCompleteDecorator.decorate(estanteria_Box);
                newTable.getColumnModel().getColumn(p).setCellEditor(new ComboBoxCellEditor(estanteria_Box));
      
                 //Set up tool tips for the sport cells.
                DefaultTableCellRenderer renderer;
                renderer = new DefaultTableCellRenderer();
                renderer.setToolTipText("Click para cambiar Estanteria");
                newTable.getColumnModel().getColumn(p).setCellRenderer(renderer);
    
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        labelPanel = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        scroll2 = new javax.swing.JScrollPane();
        // El tamanio del scroll de las tablas
        scroll2.setColumnHeader(new JViewport() {
            @Override public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height = 50;
                return d;
            }
        });
        newTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jLabel4.setText("Tipo documental");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelPanel.setBackground(new java.awt.Color(255, 102, 0));

        labelName.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        labelName.setText("Ver Documento ");

        org.jdesktop.layout.GroupLayout labelPanelLayout = new org.jdesktop.layout.GroupLayout(labelPanel);
        labelPanel.setLayout(labelPanelLayout);
        labelPanelLayout.setHorizontalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(labelName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 554, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        labelPanelLayout.setVerticalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .add(23, 23, 23)
                .add(labelName)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/open.png"))); // NOI18N
        jButton1.setText("Abrir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        submitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aceptar.png"))); // NOI18N
        submitButton.setText("Cambiar");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelar.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Documento"));

        newTable.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        newTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        newTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        newTable.setCellSelectionEnabled(true);
        newTable.setDropMode(javax.swing.DropMode.ON);
        newTable.setRowHeight(100);
        newTable.setSelectionBackground(new java.awt.Color(255, 255, 255));
        newTable.setSelectionForeground(new java.awt.Color(0, 102, 204));
        newTable.setSurrendersFocusOnKeystroke(true);
        newTable.getTableHeader().setReorderingAllowed(false);
        scroll2.setViewportView(newTable);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(scroll2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(scroll2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );

        jMenu1.setText("Comandos");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/open.png"))); // NOI18N
        jMenuItem2.setText("Abrir Documento");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/regresar.png"))); // NOI18N
        jMenuItem3.setText("Salir");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(jButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(submitButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButton2))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {jButton1, jButton2, submitButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(labelPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(20, 20, 20)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(submitButton)
                    .add(jButton1))
                .add(8, 8, 8))
        );

        layout.linkSize(new java.awt.Component[] {jButton1, jButton2, submitButton}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try{
            
        
            //verifico si lo tengo que ir a abrir local o remotamente, obtengo donde estoy
            String[] datosFromConfig = ConfigClass.configReader();        
            //si estoy en localhost entonces abro el directorio normalmente
            if(datosFromConfig[0].equals("localhost")){            
                // Codigo para abrir el archivo  
                if (Desktop.isDesktopSupported()){
                    try {
                        // esto verifica si puede abrir archivos la computadora 
                        Desktop.getDesktop().open(new File(BDEstructura.getPathFromDocument(idIn)));
                    } catch (IOException ex) {
                        Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
                    }


                }
                
            }
            //si no estoy en localhost entonces estoy en otra computadora, voy a abrir en la computadora madre el directorio
            else{
                try {
                    
                    //ahora creo el directorio remotamente
                    String path = remoteConnection.openFile(BDEstructura.getPathFromDocument(idIn),idIn);
                    if (Desktop.isDesktopSupported()){
                    
                        // esto verifica si puede abrir archivos la computadora 
                        Desktop.getDesktop().open(new File(path));
                    


                    }
                } catch (IOException ex) {
                    Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null,"No se pudo crear el archivo Remoto :\n"+ex,"Error en conexión",0);
                }
            }


           
        
        }catch(Exception e){
            
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed
  
    public void copyFileToRemoteLocation(){
        /*
        try {
            String localPathFile = getPathToSave(jTreeSelection.getSelectionPath().toString())+idIn+".pdf";
            String remotePath = getPathToRemoteSave(jTreeSelection.getSelectionPath().toString());
            remoteConnection.copyFile(localPathFile, remotePath, idIn+".pdf");
        } catch (IOException ex) {
            Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"No se pudo copiar el archivo al computador remoto :\n"+ex,"Error en conexión",0);
        }
        */
        
    }
    
    private static java.sql.Timestamp getCurrentDate() {
    java.util.Date today = new java.util.Date();
    
    return new java.sql.Timestamp(today.getTime());
}
    
    private void ingresarDocumento()  {
       
       processGood = true;
     
        try {
            int resultado = 0;
            PreparedStatement ps = null;
            ps = (PreparedStatement) connection.prepareStatement(preparedQuery,PreparedStatement.RETURN_GENERATED_KEYS);

            // Ingresar el nombre unico del documento 
            ps.setInt(1, idIn);
            
            // Path
            ps.setString(2, pathFile); 
            
            // Unico
            ps.setString(3, nameFile); 
            
            // Fecha y hora de escaneo 
            ps.setTimestamp(4, getCurrentDate()); 
            
            // getCodigo in 
            ps.setInt(5, MasterMainView.getUserIn().getCodigo());
            
            
            // numero de folios 
            ps.setInt(6,getPDFCountPages() ); 
            
            
          

     
           
           resultado = ps.executeUpdate();
              
           resultSet = ps.getGeneratedKeys();
           while (resultSet.next()) {
                      //codigoRecienIngresado = resultSet.getInt(1);   
           }
           processGood = true;
        
        } catch (SQLException e) {
           
            JOptionPane.showMessageDialog(rootPane, "Error al ingresar datos en Nuevo Documento \n"+e,"Error al Ingreso de datos",0);
            processGood = false;
           
        } // fin del catch
       
        
    }
    private void ingresarComboBox(){
        for (int i=0; i!= newTable.getColumnCount(); i++){
            if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Tipo Documental")){
                //if (tipo_Documental_Box.getSelectedIndex()!=0) // si no es cero, ingreso el codigo. 
                    DataBaseClass.executeQuery("UPDATE documento SET codigo_tipo_documental="+dataTipoDocumentalList[tipo_Documental_Box.getSelectedIndex()].getCodigo()+" WHERE idDocumento = "+idIn+";");
                
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Area")){
                 if (area_Box.getSelectedIndex()!=0) // si no es cero, ingreso el codigo. 
                    DataBaseClass.executeQuery("UPDATE documento SET codigo_area="+dataAreaList[area_Box.getSelectedIndex()-1].getCodigo()+" WHERE idDocumento = "+idIn+";");
                 else 
                    DataBaseClass.executeQuery("UPDATE documento set codigo_area = NULL WHERE idDocumento = "+idIn);
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Local")){
                 if (local_Box.getSelectedIndex()!=0) // si no es cero, ingreso el codigo. 
                    DataBaseClass.executeQuery("UPDATE documento SET codigo_local="+dataLocalList[local_Box.getSelectedIndex()-1].getCodigo()+" WHERE idDocumento = "+idIn+";");
                 else 
                    DataBaseClass.executeQuery("UPDATE documento SET codigo_local = null WHERE idDocumento = "+idIn);
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Ambiente")){
                 if (ambiente_Box.getSelectedIndex()!=0) // si no es cero, ingreso el codigo. 
                    DataBaseClass.executeQuery("UPDATE documento SET codigo_ambiente="+dataAmbienteList[ambiente_Box.getSelectedIndex()-1].getCodigo()+" WHERE idDocumento = "+idIn+";");
                 else 
                    DataBaseClass.executeQuery("UPDATE documento SET codigo_ambiente= NULL WHERE idDocumento = "+idIn);
            }
            else if (newTable.getModel().getColumnName(i).equalsIgnoreCase("Estanteria")){
                 if (estanteria_Box.getSelectedIndex()!=0) // si no es cero, ingreso el codigo. 
                     DataBaseClass.executeQuery("UPDATE documento SET codigo_estanteria="+dataEstanteriaList[estanteria_Box.getSelectedIndex()-1].getCodigo()+" WHERE idDocumento = "+idIn+";");
                 else 
                     DataBaseClass.executeQuery("UPDATE documento SET codigo_estanteria = NULL WHERE idDocumento = "+idIn);
            }
        } 
    }
    /*
     * Lo que hace este metodo es de que en orden de la tabla del filtro de 
     * nuevo documento ve cuales son fechas y las agrega segun corresponda 
     * y listo.  
     */
    private void ingresarFechas(){
        int numfilas = -1;
        try {

                    st = (Statement) connection.createStatement();
                    resultSet = st.executeQuery("SELECT * FROM filtro_nuevo_documento");
                    ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                    int numcolumn=metaData.getColumnCount();
                    resultSet.last();                // estas lineas se mueven al final de la consulta 
                    numfilas=resultSet.getRow();

                    
                    resultSet.beforeFirst();
                    String nombre = "";
                    String nombreColumna = "";
                    int acceso = -1 ;
                    int j=5;
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
                               
                               DataBaseClass.executeQuery("UPDATE documento SET Fecha_Documento_Dia="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                               j++;
                               DataBaseClass.executeQuery("UPDATE documento SET Fecha_Documento_Mes="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                               j++;
                               DataBaseClass.executeQuery("UPDATE documento SET Fecha_Documento_Anio="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                               j++;
                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Inicial")){
                                
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Dia_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Mes_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Anio_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;

                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Final")){
                                
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Dia_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Mes_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Anio_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;

                               
                               
                            }
                            
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Inicial")){
                                
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Caja_Dia_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Caja_Mes_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Caja_Anio_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                  
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Final")){
                                
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Caja_Dia_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Caja_Mes_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Caja_Anio_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                  
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Inicial")){
                                
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Legajo_Dia_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Legajo_Mes_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Legajo_Anio_Inicial="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;

                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Final")){
                                
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Legajo_Dia_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Legajo_Mes_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Fecha_Extrema_Legajo_Anio_Final="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;

                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Otra Fecha")){
                                
                                DataBaseClass.executeQuery("UPDATE documento SET Otra_Fecha_Dia="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Otra_Fecha_Mes="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                                DataBaseClass.executeQuery("UPDATE documento SET Otra_Fecha_Anio="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento = "+idIn+";");
                                j++;
                               
                               
                            }
                            else 
                                j++;
                            
                            
                        }
                    }


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar clase de datos a la tabla : Nuevo Documento \n"+ex,"Error al cargar datos", 0);
            }
    }
    private void ingresarTheRest(){
            int numfilas= -1;
            try {

                    st = (Statement) connection.createStatement();
                    resultSet = st.executeQuery("SELECT * FROM filtro_nuevo_documento");
                    ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                    int numcolumn=metaData.getColumnCount();
                    resultSet.last();                // estas lineas se mueven al final de la consulta 
                    numfilas=resultSet.getRow();

                    
                    resultSet.beforeFirst();
                    String nombre = "";
                    String nombreColumna = "";
                    int acceso = -1 ;
                    int j=5;
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
                               
                               j+=3;
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Inicial")){
                                

                               j+=3;
                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Final")){
                                

                               j+=3;
                               
                               
                            }
                            
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Inicial")){
                                

                               j+=3;
                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Final")){
                                

                               j+=3;
                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Inicial")){
                                

                               j+=3;
                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Final")){
                                

                               j+=3;
                               
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Otra Fecha")){
                                

                               j+=3; 
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Asunto")||nombreColumna.equalsIgnoreCase("Campos_Especificos")||nombreColumna.equalsIgnoreCase("Observaciones")||nombreColumna.equalsIgnoreCase("Orden_Alfabetico")||nombreColumna.equalsIgnoreCase("Orden_Correlativo")||nombreColumna.equalsIgnoreCase("Nuevo_Numero_Unico")||nombreColumna.equalsIgnoreCase("Nuevo_Nuemero_Unico_2")){
                               String  textIn = "";
                               try {
                                textIn= newTable.getValueAt(0, j).toString().replaceAll("<br>", "\n");
                                       textIn = textIn.replaceAll("\\<.*?\\>", "");
                                       textIn = textIn.replaceAll("'", "´");
                                       textIn = textIn.replaceAll("\"", "´´");
                                DataBaseClass.executeQuery("UPDATE documento SET "+nombreColumna+"='"+textIn+"' WHERE idDocumento = "+idIn+";");
                               
                               }
                               catch (Exception e){
                                        /*  Esta vacia, entonces no hago nada. y continuo el ciclo. */
                                       
                               }
                               
                                   
                               j++; // esto lo hago siempre. 
                               
                            }
                            else if (nombreColumna.equalsIgnoreCase("Caja")||nombreColumna.equalsIgnoreCase("Legajo")){
                               
                               DataBaseClass.executeQuery("UPDATE documento SET "+nombreColumna+"="+newTable.getValueAt(0, j).toString()+" WHERE idDocumento ="+idIn+";");
                               j++;
                                
                            }
                            
                            else {
                                j++;
                               
                            }
                        }
                    }


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar clase de datos a la tabla : Nuevo Documento \n"+ex,"Error al cargar datos", 0);
            }
    }
    
    private boolean isScanned(){
        File f = new File(pathFile);
        if (f.isFile())
            return true;
        else {
            JOptionPane.showMessageDialog(null, "<html><body><pre><p style='width: 600px;'> Error, no existe el documento en la ruta especificada <br/>"+pathFile+".pdf"+"</p></pre></body></html>","Error no existe el documento", 0);
      
            return false;
            
        }
        
    }
    
    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        setDefaultData();  // Antes de guardar volver a cambiar la inforamcion 
        if (checkWhereToSave() && isScanned() ){ // Si estas dos son verdaderas, entonces ingreso, sino no. 
            // Ingreso el Documento
            ingresarDocumento();

            if (processGood){
                
                // Ingresare por partes segun sea el caso. 
                // ComboBox
                // Fechas 
                // Campos faltantes
                ingresarComboBox();
                ingresarFechas();
                ingresarTheRest();

                // Confirmo que Ingreso exitosamente
                // Si llega hasta aca quiere decir que el dato fue correctamente
                JOptionPane.showMessageDialog(rootPane, "Dato ingresado exitosamente" );
                
               
                // Actualizo la tabla de documentos . 
                MasterMainView.refreshTable("Documentos");
                MasterMainView.setColumnWidthDocuments();
                
                dispose();



            }
        }
        String[] datosFromConfig = ConfigClass.configReader();
        if(!datosFromConfig[0].equals("localhost")){
            copyFileToRemoteLocation();
        }
    }//GEN-LAST:event_submitButtonActionPerformed
    
   
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       /* no voy hacer nada, y lo sacare de aca. */
        this.dispose();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        jButton1ActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed


    
    
    Collection<Element> getElementTreeFromPlainList(List<String[]> data) {
        // builds a map of elements object returned from store
        Map<String, Element> values = new HashMap<String, Element>();
        for (String[] s : data) {
            values.put(s[0], new Element(s[2], s[1]));
        }

        // creates a result list
        Collection<Element> result = new ArrayList<Element>();

        // for each element in the result list that has a parent, put it into it
        // otherwise it is added to the result list
        for (Element e : values.values()) {
            if (e.parent != null) {
                values.get(e.parent).getChildren().add(e);
            } else {
                result.add(e);
            }
        }

        return result;
    }
    
    void createTreeNodesForElement(final DefaultMutableTreeNode dmtn, final Collection<Element> elements) {
        // for each element a tree node is created
        for (Element child : elements) {
            DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
            dmtn.add(created);
            createTreeNodesForElement(created, child.getChildren());
            
        }
    }
    
    public String getUniqueNameByPath(String path){
        //valor que voy a devolver
        String uniqueName = "";
        //valor temporal para hacer los cambios
        String temporal = "";
        //quito las llaves que trae el path
        for(int i=1;i<path.length()-1;i++){
            temporal += path.charAt(i);
            
        }
        
        //Divido el path por secciones
        String[] splitPath = temporal.split(",");
        //elimino el espacio que queda al principio de cada seccion del path, exceptuando la primer seccion de path que no trae espacio
        for(int i=1;i<splitPath.length;i++){
            String tmp = "";
            for(int j=1;j<splitPath[i].length();j++){
                tmp += splitPath[i].charAt(j);
            }
            splitPath[i] = tmp;   
        }
        //Formo el nombre segun los identificadores a partir de la tercera posicion 
        for(int i=2;i<splitPath.length;i++){
            String[] id = BDEstructura.getIdentificadoresByUser(splitPath[i]);
            uniqueName+=id[0];
        }
        
        return uniqueName;
        
    }
    
    public String getPathToSave(String path){
        //Valor que voy a devolver
        String Path = "";
        //valor temporal para hacer los cambios
        String temporal = "";
        //quito las llaves que trae el path
        for(int i=1;i<path.length()-1;i++){
            temporal += path.charAt(i);
            
        }
        
        //Divido el path por secciones
        String[] splitPath = temporal.split(",");
        //elimino el espacio que queda al principio de cada seccion del path, exceptuando la primer seccion de path que no trae espacio
        for(int i=1;i<splitPath.length;i++){
            String tmp = "";
            for(int j=1;j<splitPath[i].length();j++){
                tmp += splitPath[i].charAt(j);
            }
            splitPath[i] = tmp;   
        }
        //Formo el Path a partir del fondo donde esta 
        for(int i=2;i<splitPath.length;i++){            
            Path+=File.separator+splitPath[i];
        }
        Path += File.separator;
        String pathToGive = System.getProperty("user.dir") +File.separator+"src"+File.separator+"Fondos" + Path;
        return pathToGive;
        
    }
    
    public String getPathToRemoteSave(String path){
        //Valor que voy a devolver
        String Path = "";
        //valor temporal para hacer los cambios
        String temporal = "";
        //quito las llaves que trae el path
        for(int i=1;i<path.length()-1;i++){
            temporal += path.charAt(i);
            
        }
        
        //Divido el path por secciones
        String[] splitPath = temporal.split(",");
        //elimino el espacio que queda al principio de cada seccion del path, exceptuando la primer seccion de path que no trae espacio
        for(int i=1;i<splitPath.length;i++){
            String tmp = "";
            for(int j=1;j<splitPath[i].length();j++){
                tmp += splitPath[i].charAt(j);
            }
            splitPath[i] = tmp;   
        }
        //Formo el Path a partir del fondo donde esta 
        for(int i=2;i<splitPath.length;i++){            
            Path+="/"+splitPath[i];
        }
        Path += "/";
        String pathToGive = "/Fondos" + Path;
        return pathToGive;
        
    }
    
    public boolean checkWhereToSave(){
     /*   String nameSerie = "";
        Object[] directorio = null;
        int isSerie = 0;
        boolean isCorrect = false;
        
        try{
           // nameSerie = jTreeSelection.getSelectionPath().getLastPathComponent().toString();
            directorio = jTreeSelection.getSelectionPath().getPath();
            if(directorio.length>2){
                isSerie = BDEstructura.isSerie(nameSerie,directorio[2].toString());
            }
            else{
                isSerie = BDEstructura.isSerie(nameSerie,"null");
            }
        }catch(Exception e){
            isCorrect = false;
            isSerie=0; // sucedio un error. 
            
            
        }
        
        if (isSerie == 0){
            isCorrect = false;
            JOptionPane.showMessageDialog(null,"Para ingresar un documento debe seleccionar una serie del árbol de fondos","Error en Estructura",0);
        }
        else if(isSerie == 1){
            isCorrect = true;
        }
        
        return isCorrect;
        */
        return true;
    }
    
    
    public int getPDFCountPages(){
        
        int count = -1;
        String path = "";
        PDDocument doc = null;
        try {
            String[] datos = ConfigClass.configReader();
            if(datos[0].equals("localhost")){            
                // Codigo para abrir el archivo  
               
                doc = PDDocument.load(pathFile);
                
                
            }
            //si no estoy en localhost entonces estoy en otra computadora, voy a abrir en la computadora madre el directorio
            else{
                try {
                    
                    //ahora creo el directorio remotamente
                    
                    path = remoteConnection.openFile(BDEstructura.getPathFromDocument(idIn),idIn);
                    
                    doc = PDDocument.load(path);
                } catch (IOException ex) {
                    Logger.getLogger(CheckDocument.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null,"No se pudo crear el archivo Remoto :\n"+ex,"Error en conexión",0);
                }
            }
            count = doc.getNumberOfPages();
            doc.close();
        } catch (Exception ex) {
            System.out.println("<html><body><pre><p style='width: 600px;'> Error, no existe el documento en la ruta especificada <br/>"+ex+"</p></pre></body></html>");
        }
        
        return count;  
        
    }
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
            java.util.logging.Logger.getLogger(CheckDocument.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckDocument.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckDocument.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckDocument.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CheckDocument().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelName;
    private javax.swing.JPanel labelPanel;
    private javax.swing.JTable newTable;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables

    public static class Element {
        private final String parent;
        private final String name;
        private final Collection<Element> children = new ArrayList<Element>();

        public Element(final String parent, final String name) {
            super();
            this.parent = parent;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Collection<Element> getChildren() {
            return children;
        }

    }
}
