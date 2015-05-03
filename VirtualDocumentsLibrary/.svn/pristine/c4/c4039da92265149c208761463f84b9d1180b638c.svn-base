/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualdocumentslibrary; 

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.awt.Component;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author giovannirojas
 */
public class NewUser extends javax.swing.JFrame {

    private int codigoRecienIngresado = -1;
    private Connection connection = DataBaseClass.getConnection();
    private boolean processGood = false;
    private ResultSet resultSet = DataBaseClass.getResultSet();
    private int codeToChange = -1;
    private Statement st = DataBaseClass.getSt();
    private String preparedQuery;
    private boolean checksModulos[] = null; // Vector donde guardare los checksModulos
    private String checksSubFondoNames[] = null;
    private String checksSubFondoFondo[] = null;
    private boolean checksSubFondoAccess[] = null;
    
    
    public NewUser() {
        initComponents();
        labelPanel.setBackground(ConfigClass.getColorApp());
        preparedQuery = "INSERT INTO Usuario (Nombre,Apellido,Contrasena,email) values (?,?,?,?)";
        
        reloadTreeNew();
        checksSubFondoNames = new String[fondos.getRowCount()];
        checksSubFondoAccess = new boolean[fondos.getRowCount()];
        checksSubFondoFondo = new String[fondos.getRowCount()];
        putNamesIntoArray();
        reloadTree();
    }
    
    public NewUser(int codeToChange) {
        initComponents();
        //Set the icon of the app. 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png"))); 
        this.setTitle("Virtual Archive");
        labelPanel.setBackground(ConfigClass.getColorApp());
        this.codeToChange = codeToChange;
        // Cambio el texto del usuario
        labelName.setText("Ver Usuario :\t"+codeToChange);
        submitButton.setText("Guardar");
        passLabel.setVisible(false);
        passField.setVisible(false);
        loadData();
        
        // Prepared the submit button 
        codigoRecienIngresado = codeToChange;
        preparedQuery = "UPDATE Usuario SET Nombre = ? ,  Apellido = ?,  Contrasena = ?,  email= ? where idUsuario ="+codeToChange;
        
        
        
    }
    // El info no me sirve de nada solo es para diferenciar otro tipo 
    public NewUser(int codeToChange,String info) {
        initComponents();
        //Set the icon of the app. 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png"))); 
        this.setTitle("Virtual Archive");
        labelPanel.setBackground(ConfigClass.getColorApp());
        this.codeToChange = codeToChange;
        // Cambio el texto del usuario
        labelName.setText("Editar Informacion");
        submitButton.setText("Guardar");
        pestaña.remove(1);
        pestaña.remove(1); // Se le coloca 1 porque al quitarle 1 ya tiene dos entonces es la 1 de nuevo
        
        
        loadData();
        
        // Prepared the submit button 
        codigoRecienIngresado = codeToChange;
        preparedQuery = "UPDATE Usuario SET Nombre = ? ,  Apellido = ?,  Contrasena = ?,  email= ? where idUsuario ="+codeToChange;
        
        
        
    }
    
    private void putNamesIntoArray(){
        for (int i = 0; i < checksSubFondoNames.length; i++) {
            fondos.setSelectionRow(i);
            this.checksSubFondoNames[i] = fondos.getSelectionPath().getLastPathComponent().toString();
            this.checksSubFondoAccess[i] = false;
            Object[] directorios = fondos.getSelectionPath().getPath();
            if(directorios.length>2){
                this.checksSubFondoFondo[i] = directorios[2].toString();
            }
            else{
                this.checksSubFondoFondo[i] = "null";
            }
            
        }
       
    }
    
    private void loadData(){
        try {
        
                reloadTree();
                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery("SELECT * FROM Usuario WHERE idUsuario ="+codeToChange);
                ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                
                int numcolumn=metaData.getColumnCount(); // Numero de columnas 
                
                
                int pos=0;
                while(resultSet.next()){
                    
                    
                    for(int i=0;i<=numcolumn;i++){
                        switch(i){
                            // Caso 1 es el id. no lo quiero, es decir ya lo tengo 
                            case 2:
                                nombreField.setText(""+resultSet.getString(2));
                                break;
                            case 3:
                                apellidoField.setText(""+resultSet.getString(3));
                                break;
                            case 4:
                                passField.setText(""+resultSet.getString(4));
                                break;
                            case 5:
                                mailField.setText(""+resultSet.getString(5));
                                break;
                                
                            
                            
                          }
                    }
                    
                    
                    pos++;
                }
                
                
             
             
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error al cargar datos en usuario","Ver Usuario"+codeToChange,0);
           
        }
        
        loadCheckBox();
    }
    
    private void loadCheckBox(){
        
        try {
        
                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery(""
                        + "SELECT permiso.idpermiso, permiso.nombre, permiso.descripcion, usuario_permiso.acces FROM permiso, usuario_permiso WHERE  permiso.idpermiso = usuario_permiso.codigo_permiso AND permiso.codigo_modulo is not null AND usuario_permiso.codigo_usuario = "+codeToChange);
                ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                
                resultSet.last();                // estas lineas se mueven al final de la consulta 
               
                int numrows=resultSet.getRow();
                int numcolumn=metaData.getColumnCount();
                
                checksModulos = new boolean [numrows];  //la cantidad de material que va a salir 
                
                resultSet.beforeFirst();
                
                int pos=0;
                while(resultSet.next()){
                    boolean acces=false;
                    
                    
                    for(int i=0;i<=numcolumn;i++){
                        switch(i){
                            case 4 :
                               acces=resultSet.getBoolean(4);
                               break;
                            
                          }
                        
                    }
                    
                    checksModulos[pos]= acces;
                    pos++;
                }
                
                
             
             
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error al cargar datos en usuario","Acceso Usuario UPDATE",0);
           
        }
        
        setCheckBoxTableModulos();
        
        
    }
    
    private void setCheckBoxTableModulos (){
        
        int filas = moduloPermisoTable.getModel().getRowCount(); // cantidad de filas 

        for (int fila = 0; fila < filas ;fila ++){ 
             
                    moduloPermisoTable.getModel().setValueAt(checksModulos[fila], fila, 3); 
                    
        }
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        labelPanel = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        pestaña = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        nombreField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        apellidoField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        passField = new javax.swing.JTextField();
        passLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        mailField = new javax.swing.JTextField();
        permisoPanel = new javax.swing.JPanel();
        checkBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        moduloPermisoTable = new javax.swing.JTable();
        subPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        fondos = new javax.swing.JTree();
        submitButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel2.setText("Usuario");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelPanel.setBackground(new java.awt.Color(255, 102, 0));

        labelName.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        labelName.setText("Nuevo Usuario");

        org.jdesktop.layout.GroupLayout labelPanelLayout = new org.jdesktop.layout.GroupLayout(labelPanel);
        labelPanel.setLayout(labelPanelLayout);
        labelPanelLayout.setHorizontalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(labelName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 248, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        labelPanelLayout.setVerticalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .add(23, 23, 23)
                .add(labelName)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Nombre");

        jLabel4.setText("Apellido");

        passLabel.setText("Contraseña");

        jLabel6.setText("e-mail");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(nombreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(apellidoField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(passLabel)
                            .add(jLabel6))
                        .add(29, 29, 29)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(mailField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(passField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(27, 27, 27)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(nombreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(apellidoField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(passLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(passField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(mailField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pestaña.addTab("Datos Generales", jPanel1);

        checkBox.setText("Seleccionar Todos / Deseleccionar Todos");
        checkBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxActionPerformed(evt);
            }
        });

        moduloPermisoTable.setModel(new ResultSetTableModel("permiso modulos"));
        moduloPermisoTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(moduloPermisoTable);

        org.jdesktop.layout.GroupLayout permisoPanelLayout = new org.jdesktop.layout.GroupLayout(permisoPanel);
        permisoPanel.setLayout(permisoPanelLayout);
        permisoPanelLayout.setHorizontalGroup(
            permisoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(permisoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(permisoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, permisoPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(checkBox)))
                .addContainerGap())
        );
        permisoPanelLayout.setVerticalGroup(
            permisoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(permisoPanelLayout.createSequentialGroup()
                .add(8, 8, 8)
                .add(checkBox)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        pestaña.addTab("Permiso Modulos", permisoPanel);

        fondos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fondosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(fondos);

        org.jdesktop.layout.GroupLayout subPanelLayout = new org.jdesktop.layout.GroupLayout(subPanel);
        subPanel.setLayout(subPanelLayout);
        subPanelLayout.setHorizontalGroup(
            subPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        subPanelLayout.setVerticalGroup(
            subPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(subPanelLayout.createSequentialGroup()
                .add(35, 35, 35)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
        );

        pestaña.addTab("Permiso Sub-Fondos", subPanel);

        submitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aceptar.png"))); // NOI18N
        submitButton.setText("Ingresar");
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

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(pestaña, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 511, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(submitButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(8, 8, 8)
                        .add(jButton2)))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {jButton2, submitButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(labelPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(pestaña, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 338, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(submitButton)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {jButton2, submitButton}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void ingresarUsuario()  {
       
       processGood = true;
     
        try {
            int resultado = 0;
            PreparedStatement ps = null;
            ps = (PreparedStatement) connection.prepareStatement(preparedQuery,PreparedStatement.RETURN_GENERATED_KEYS);

            // Nombre
            ps.setString(1, nombreField.getText()); 
            
            // Apelllido
            ps.setString(2, apellidoField.getText()); 
            
            // Contraseña
            ps.setString(3, passField.getText()); 
            
            // e-mail
            ps.setString(4, mailField.getText()); 
            
          

     
           
           resultado = ps.executeUpdate();
              
           resultSet = ps.getGeneratedKeys();
           while (resultSet.next()) {
                      codigoRecienIngresado = resultSet.getInt(1);   
           }
           processGood = true;
        
        } catch (SQLException e) {
           
            JOptionPane.showMessageDialog(rootPane, "Error al ingresar datos en Nuevo Usuario \n"+e,"Error al Ingreso de datos",0);
            processGood = false;
           
        } // fin del catch
       
        
    }
    private void ingresarPermisoModulos (){
        
        int filas = moduloPermisoTable.getModel().getRowCount(); // cantidad de filas 
        boolean value = false;
        boolean done = false;
        int acces=-1;

        for (int i = 0; i < filas ;i ++){ 
            try{    
                value = Boolean.valueOf(moduloPermisoTable.getModel().getValueAt(i, 3).toString()); // obtengo el valor del check
            }catch (Exception e){
                value = false;
            }
                if (value)
                acces=1;
            else 
                acces=0;


            try{

                int cod = 0;
                int resultado = 0;
                PreparedStatement ps = null;
                ps = (PreparedStatement) connection.prepareStatement("INSERT INTO Usuario_Permiso"
                        + " (Codigo_Permiso,Codigo_Usuario,Acces) values (?,?,?)");


                ps.setInt(1, Integer.parseInt(moduloPermisoTable.getModel().getValueAt(i, 0).toString()));
                ps.setInt(2, codigoRecienIngresado);
                ps.setInt(3, acces);


                resultado = ps.executeUpdate();




            }catch(SQLException ex){
                JOptionPane.showMessageDialog(rootPane, "Error al ingresar datos en Nuevo Usuario \n"+ex,"Error al Ingreso de datos",0);

            }

        } // Fin del for 

    }
    
    private void ingresarPermisoSubNivel(){
        
        try{

                for(int i=1;i<checksSubFondoNames.length;i++){
                    PreparedStatement ps = (PreparedStatement) connection.prepareStatement("INSERT INTO Usuario_Permiso"
                        + " (Codigo_Permiso,Codigo_Usuario,Acces) values (?,?,?)");

                    int codigoSN = BDEstructura.getIdSub_Fondo(checksSubFondoNames[i],checksSubFondoFondo[i]);
                    ps.setInt(1, BDEstructura.getIdFromPermiso(codigoSN));
                    ps.setInt(2, codigoRecienIngresado);
                    if(checksSubFondoAccess[i]){
                        ps.setInt(3, 1);
                    }
                    else{
                        ps.setInt(3, 0);
                    }


                    ps.executeUpdate();
                }
                




            }catch(SQLException ex){
                JOptionPane.showMessageDialog(rootPane, "Error al ingresar datos en Nuevo Usuario \n"+ex,"Error al Ingreso de Permiso Sub Nivel",0);

            }
        
    }
    
    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        
            
            // Ingreso el Usuario 
            ingresarUsuario();
            
            if (processGood){
                // Continuo Ingresando el resto
                // Antes de ingresar los permisos voy a ver si es nuevo 
                // o solo esta modificando 
                // si solo modifica elimino todo. 
                if (!(checksModulos == null)){
                    DataBaseClass.executeQuery("DELETE FROM usuario_permiso WHERE codigo_usuario = "+codeToChange+" AND codigo_permiso < 18");
                    
                }
                
                ingresarPermisoModulos(); 
                
                if(checksModulos == null){
                    // Ingreso el Permiso Sub-Nivel si es nuevo
                    ingresarPermisoSubNivel();
                }
                
                
                // Si esta editando su informacion entonces quiero que mire los cambios instantaneamente. 
                if(labelName.getText().equalsIgnoreCase("Editar Informacion")){
                    
                    MasterMainView.getNameLabel().setText(""+nombreField.getText().split(" ")[0]+" "+apellidoField.getText().split(" ")[0]);
                }
                
                
                // Confirmo que Ingreso exitosamente 
                // Si llega hasta aca quiere decir que el dato fue correctamente
                JOptionPane.showMessageDialog(rootPane, "Dato ingresado exitosamente" );
                // Luego de ingresar todo // Refresco la tabla del MasterMainView
                
                MasterMainView.refreshTable("usuarios");
                 
                dispose();
                
            
            }
            
            else {
                // No hago nada. ya mostre los errores del caso. asi que no tengo 
                // mas por hacer. 
                
            }
            
    }//GEN-LAST:event_submitButtonActionPerformed

    private void checkBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxActionPerformed
        int filas = moduloPermisoTable.getModel().getRowCount(); // cantidad de filas 

        for (int fila = 0; fila < filas ;fila ++){ 
                if (checkBox.isSelected())
            // Esta True, entoces seleccionare todos los accesos.
                    moduloPermisoTable.getModel().setValueAt(true, fila, 3); 
                else 
                    moduloPermisoTable.getModel().setValueAt(false, fila, 3); 
                    
        }
        
        
    }//GEN-LAST:event_checkBoxActionPerformed

    private void fondosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fondosMouseClicked
        // TODO add your handling code here:
        try{
            String name = fondos.getSelectionPath().getLastPathComponent().toString();
            Object directorios[] = fondos.getSelectionPath().getPath();
            
            if(codeToChange == -1){
                for(int i=0;i<checksSubFondoNames.length;i++){
                    
                    if(directorios.length>2){
                        
                        if(checksSubFondoNames[i].equals(name) && checksSubFondoFondo[i].equals(directorios[2].toString())){
                            if(checksSubFondoAccess[i]){
                                checksSubFondoAccess[i] = false;
                                
                            }
                            else{
                                checksSubFondoAccess[i] = true;
                              
                            }
                        }
                    }
                    else{
                        if(checksSubFondoNames[i].equals(name) && (checksSubFondoFondo[i].equals("null") || checksSubFondoFondo[i].equals(checksSubFondoNames[i]))){
                            if(checksSubFondoAccess[i]){
                                checksSubFondoAccess[i] = false;
                            }
                            else{
                                checksSubFondoAccess[i] = true;
                            }
                        }
                    }
                    

                }
            }
            else{
                if(directorios.length>2){
                    int idSubFondo = BDEstructura.getIdSub_Fondo(name, directorios[2].toString());
                    int access = BDEstructura.getPermiso(codeToChange, idSubFondo);
                    
                    String query = "";
                    int codigoSN = BDEstructura.getIdSub_Fondo(name, directorios[2].toString());
                    if(access == 0){

                        query = "UPDATE usuario_permiso SET acces = 1 WHERE codigo_usuario = "+codeToChange+" AND codigo_permiso = "+BDEstructura.getIdFromPermiso(codigoSN);
                        
                    }
                    else{
                        query = "UPDATE usuario_permiso SET acces = 0 WHERE codigo_usuario = "+codeToChange+" AND codigo_permiso = "+BDEstructura.getIdFromPermiso(codigoSN);
                        
                    }
                    if(codeToChange != 1){
                        DataBaseClass.executeQuery(query);
                    }
                    else{
                        query = "";
                    }
                }
                else{
                    int idSubFondo = BDEstructura.getIdSub_Fondo(name, null);
                    int access = BDEstructura.getPermiso(codeToChange, idSubFondo);
                    String query = "";
                    int codigoSN = BDEstructura.getIdSub_Fondo(name, "null");
                    if(access == 0){

                        query = "UPDATE usuario_permiso SET acces = 1 WHERE codigo_usuario = "+codeToChange+" AND codigo_permiso = "+BDEstructura.getIdFromPermiso(codigoSN);
                        System.out.println(query);
                    }
                    else{
                        query = "UPDATE usuario_permiso SET acces = 0 WHERE codigo_usuario = "+codeToChange+" AND codigo_permiso = "+BDEstructura.getIdFromPermiso(codigoSN);
                        System.out.println(query);
                    }
                    if(codeToChange != 1){
                        DataBaseClass.executeQuery(query);
                    }
                    else{
                        query = "";
                    }
                }
                
            }
        }catch(Exception e){
            
        }
        
        reloadTree();
        
    }//GEN-LAST:event_fondosMouseClicked
    
    public void reloadTreeNew() {
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Fondos");
        
        List<String[]> data = BDEstructura.getDataFromSub_Fondo();

        createTreeNodesForElement(root, getElementTreeFromPlainList(data));

        fondos.setModel(new DefaultTreeModel(root));
        fondos.setRootVisible(false);
        for (int i = 0; i < fondos.getRowCount(); i++) {
            fondos.expandRow(i);
        }
        
    }
    
    public void reloadTree() {
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Fondos");
        
        List<String[]> data = BDEstructura.getDataFromSub_Fondo();

        createTreeNodesForElement(root, getElementTreeFromPlainList(data));

        fondos.setModel(new DefaultTreeModel(root));
        fondos.setRootVisible(false);
        for (int i = 0; i < fondos.getRowCount(); i++) {
            fondos.expandRow(i);
        }
        
        //pongo los iconos
        fondos.setCellRenderer(new DefaultTreeCellRenderer() {
            private Icon checked = new ImageIcon(System.getProperty("user.dir") +File.separator+"src"+File.separator+"Imagenes"+File.separator+"checked.png");
            private Icon unchecked = new ImageIcon(System.getProperty("user.dir") +File.separator+"src"+File.separator+"Imagenes"+File.separator+"unchecked.png");
            
            @Override
            public Component getTreeCellRendererComponent(javax.swing.JTree fondos,
                    Object value, boolean selected, boolean expanded,
                    boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(fondos, value,
                        selected, expanded, isLeaf, row, focused);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                
                String s = node.getUserObject().toString();
                
                
                if(codeToChange != -1){
                    TreeNode[] directorios = node.getPath();
                    boolean guaranteedAccess = false;
                    
                    if(directorios.length>2){
                        String[] ident = BDEstructura.getIdentificadores(s, directorios[2].toString());
                        guaranteedAccess = checkNodePermisoAccess(codeToChange,ident[0]);
                        
                    }
                    else{
                        String[] ident = BDEstructura.getIdentificadores(s, "null");
                        guaranteedAccess = checkNodePermisoAccess(codeToChange,ident[0]);
                        
                    }
                    
                    if(s.equals(BDEstructura.getName("Fondos"))){
                        setIcon(checked);
                    }
                    else{
                        if (guaranteedAccess){
                            setIcon(checked);
                        }
                        else{
                            setIcon(unchecked);
                        }
                    }
                }
                else{
                    TreeNode[] directorios = node.getPath();
                    for(int i=0;i<checksSubFondoNames.length;i++){
                        if(directorios.length>2){
                            if(checksSubFondoNames[i].equals(s) && checksSubFondoFondo[i].equals(directorios[2].toString())){
                                if(checksSubFondoAccess[i]){
                                   setIcon(checked);
                                }
                                else{
                                    setIcon(unchecked);
                                }
                            }
                        }
                        else{
                            if(checksSubFondoNames[i].equals(s) && checksSubFondoFondo[i].equals("null")){
                                if(checksSubFondoAccess[i]){
                                   setIcon(checked);
                                }
                                else{
                                    setIcon(unchecked);
                                }
                            }
                        }
                        
                        
                    }
                    
                    
                }
                return c;
            }
        });
        
    }
    
    Collection<Element> getElementTreeFromPlainList(List<String[]> data) {
        // builds a map of elements object returned from store
        Map<String, Element> values = new HashMap<String, Element>();
        for (String[] s : data) {
            values.put(s[0], new Element(s[2], s[1], s[0]));
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
            //aqui debe de verificar si tiene permiso el usuario actual para crear el nodo, sino no lo crea
            boolean guaranteedAccess = checkNodePermisoView(child.getId());
            if(child.getName().equals(BDEstructura.getName("Fondos"))){
                DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
                dmtn.add(created);
                createTreeNodesForElement(created, child.getChildren());
            }
            if (guaranteedAccess){
                DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
                dmtn.add(created);
                createTreeNodesForElement(created, child.getChildren());
                
            }
            
            
        }
    }
    
    public boolean checkNodePermisoView(String idIn){
        
        if(BDEstructura.getPermisoWithId(MasterMainView.getUserIn().getCodigo(),idIn)==1){
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean checkNodePermisoAccess(int codeToChange, String idIn){
        if(BDEstructura.getPermisoWithId(codeToChange,idIn) == 1){
            return true;
        }
        else{
            return false;
        }
        
        
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
            java.util.logging.Logger.getLogger(NewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewUser().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidoField;
    private javax.swing.JCheckBox checkBox;
    private javax.swing.JTree fondos;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelName;
    private javax.swing.JPanel labelPanel;
    private javax.swing.JTextField mailField;
    private javax.swing.JTable moduloPermisoTable;
    private javax.swing.JTextField nombreField;
    private javax.swing.JTextField passField;
    private javax.swing.JLabel passLabel;
    private javax.swing.JPanel permisoPanel;
    private javax.swing.JTabbedPane pestaña;
    private javax.swing.JPanel subPanel;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
    
    public static class Element {
        private final String parent;
        private final String name;
        private final Collection<Element> children = new ArrayList<Element>();
        private final String id;
        
        public Element(final String parent, final String name, final String id) {
            super();
            this.parent = parent;
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public Collection<Element> getChildren() {
            return children;
        }
        
        public String getId(){
            return id;
        }

    }

}
