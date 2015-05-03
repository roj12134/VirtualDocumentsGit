/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualdocumentslibrary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author giovannirojas
 */
public class NewFilterDocument extends javax.swing.JFrame {

    
    private Connection connection = DataBaseClass.getConnection();
    
    private ResultSet resultSet = DataBaseClass.getResultSet();
   // private int codeToChange = -1;
    private Statement st = DataBaseClass.getSt();
   // private String preparedQuery;
    private boolean checksModulos[] = null; // Vector donde guardare los checksModulos
    
    public NewFilterDocument() {
        initComponents();
        //Set the icon of the app. 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png"))); 
        this.setTitle("Virtual Library");
        labelPanel.setBackground(ConfigClass.getColorApp());
        
        // Set Extras to Table, like the column Width 
        setColumnWidth();
        
        loadCheckBox();
        
        
    }
    private void setColumnWidth(){
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(1).setPreferredWidth(172);
        table.getColumnModel().getColumn(2).setPreferredWidth(93);
        table.getColumnModel().getColumn(3).setPreferredWidth(32);
    }
   
    private void loadCheckBox(){
        
        try {
        
                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery(""
                        + "SELECT acceso FROM filtro_nuevo_documento;");
                ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                
                resultSet.last();                // estas lineas se mueven al final de la consulta 
               
                int numrows=resultSet.getRow();
                int numcolumn=metaData.getColumnCount();
                
                checksModulos = new boolean [numrows];  //la cantidad de Columnas que va a salir 
                
                resultSet.beforeFirst();
                
                int pos=0;
                while(resultSet.next()){
                    boolean acces=false;
                    
                    
                    for(int i=0;i<=numcolumn;i++){
                        switch(i){
                            case 1 :
                               acces=resultSet.getBoolean(1);
                               break;
                            
                          }
                        
                    }
                    
                    checksModulos[pos]= acces;
                    pos++;
                }
                
                
             
             
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error al cargar datos en Filtro Nuevo Documento","Filtro Nuevo Documento",0);
           
        }
        
        setCheckBoxTableModulos();
        
        
    }

    private void setCheckBoxTableModulos (){
        
        int filas = table.getModel().getRowCount(); // cantidad de filas 

        for (int fila = 0; fila < filas ;fila ++){ 
             
                    table.getModel().setValueAt(checksModulos[fila], fila, 3); 
                    
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        labelPanel = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        permisoPanel = new javax.swing.JPanel();
        checkBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        submitButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel2.setText("Usuario");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelPanel.setBackground(new java.awt.Color(255, 102, 0));

        labelName.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        labelName.setText("Filtro Nuevo Documento");

        org.jdesktop.layout.GroupLayout labelPanelLayout = new org.jdesktop.layout.GroupLayout(labelPanel);
        labelPanel.setLayout(labelPanelLayout);
        labelPanelLayout.setHorizontalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(labelName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 334, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        labelPanelLayout.setVerticalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .add(23, 23, 23)
                .add(labelName)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        checkBox.setText("Seleccionar Todos / Deseleccionar Todos");
        checkBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxActionPerformed(evt);
            }
        });

        table.setModel(new ResultSetTableModel("filtro nuevo documento"));
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);

        org.jdesktop.layout.GroupLayout permisoPanelLayout = new org.jdesktop.layout.GroupLayout(permisoPanel);
        permisoPanel.setLayout(permisoPanelLayout);
        permisoPanelLayout.setHorizontalGroup(
            permisoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(permisoPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(checkBox)
                .addContainerGap())
            .add(permisoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))
        );
        permisoPanelLayout.setVerticalGroup(
            permisoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(permisoPanelLayout.createSequentialGroup()
                .add(8, 8, 8)
                .add(checkBox)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        submitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aceptar.png"))); // NOI18N
        submitButton.setText("Confirmar");
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
                .addContainerGap(303, Short.MAX_VALUE)
                .add(submitButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(8, 8, 8)
                .add(jButton2)
                .addContainerGap())
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(12, Short.MAX_VALUE)
                    .add(permisoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        layout.linkSize(new java.awt.Component[] {jButton2, submitButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(labelPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(362, 362, 362)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(submitButton)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(84, Short.MAX_VALUE)
                    .add(permisoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE)))
        );

        layout.linkSize(new java.awt.Component[] {jButton2, submitButton}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void ingresarPermisoModulos (){
        
        int filas = table.getModel().getRowCount(); // cantidad de filas 
        boolean value = false;
        int tamanioColumna = 36;
        int acces=-1;
        int codId = -1 ;
                

        for (int i = 0; i < filas ;i ++){ 
        try{    
            value = Boolean.valueOf(table.getModel().getValueAt(i, 3).toString()); // obtengo el valor del check
        }catch (Exception e){
            value = false;
        }
        if (value)
           acces=1;
        else 
           acces=0;
        
        try{    
            codId = Integer.parseInt(table.getModel().getValueAt(i, 0).toString()); // obtengo el id del filtro nuevo documento
        }catch (Exception e){
            codId = -1;
        }
        
        try{    
            tamanioColumna = Integer.parseInt(table.getModel().getValueAt(i, 2).toString()); // obtengo el id del filtro nuevo documento
        }catch (Exception e){
            tamanioColumna = 36;
        }
        
            

        try{

            int cod = 0;
            int resultado = 0;
            PreparedStatement ps = null;
            ps = (PreparedStatement) connection.prepareStatement(""
                    + "UPDATE Filtro_Nuevo_Documento SET Acceso = ?, Tamanio_Columna = ? WHERE idFiltro_Nuevo_Documento =  "+codId+";");


            ps.setInt(1, acces);
            ps.setInt(2, tamanioColumna);
           

            
            resultado = ps.executeUpdate();
            
            
        

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(rootPane, "Error al ingresar datos en Filtro Nuevo Documento \n"+ex,"Error al Ingreso de datos",0);
            
        }

        } // Fin del for 

    }
    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        
            
          
           
                ingresarPermisoModulos(); 
                
                JOptionPane.showMessageDialog(rootPane, "Dato ingresado exitosamente" );
                
                dispose();
                
            
            
            
          
            
    }//GEN-LAST:event_submitButtonActionPerformed

    private void checkBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxActionPerformed
        int filas = table.getModel().getRowCount(); // cantidad de filas 

        for (int fila = 0; fila < filas ;fila ++){ 
                if (checkBox.isSelected())
            // Esta True, entoces seleccionare todos los accesos.
                    table.getModel().setValueAt(true, fila, 3); 
                else 
                    table.getModel().setValueAt(false, fila, 3); 
                    
        }
        
        
    }//GEN-LAST:event_checkBoxActionPerformed

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
            java.util.logging.Logger.getLogger(NewFilterDocument.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewFilterDocument.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewFilterDocument.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewFilterDocument.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewFilterDocument().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelName;
    private javax.swing.JPanel labelPanel;
    private javax.swing.JPanel permisoPanel;
    private javax.swing.JButton submitButton;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
