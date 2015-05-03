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
public class NewField extends javax.swing.JFrame {

   
    private Connection connection = DataBaseClass.getConnection();
    private ResultSet resultSet = DataBaseClass.getResultSet();
    private Statement st = DataBaseClass.getSt();
    
    private int codeToChange = -1;
    private boolean processGood = false;
    private String preparedQuery;
    private String status ;
    private String queryData;
    
    public NewField(String status) {
        initComponents();
        //Set the icon of the app. 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png"))); 
        this.setTitle("Virtual Archive");
        labelPanel.setBackground(ConfigClass.getColorApp());
        this.status = status;
        labelName.setText("Nuevo "+status);
        
        
        // Query to Insert
        if (status.equalsIgnoreCase("tipo documental"))
            preparedQuery = "INSERT INTO Tipo_Documental (Nombre) values (?)";
        else if (status.equalsIgnoreCase("local"))
            preparedQuery = "INSERT INTO local (Nombre) values (?)";
        else if (status.equalsIgnoreCase("area"))
            preparedQuery = "INSERT INTO area (Nombre) values (?)";
        else if (status.equalsIgnoreCase("estanteria"))
            preparedQuery = "INSERT INTO estanteria (Nombre) values (?)";
        else if (status.equalsIgnoreCase("ambiente"))
            preparedQuery = "INSERT INTO ambiente (Nombre) values (?)";
        
        
    }
    
    public NewField(int codeToChange, String status) {
        initComponents();
        //Set the icon of the app. 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png"))); 
        this.setTitle("Virtual Archive");
        this.status= status;;
        labelPanel.setBackground(ConfigClass.getColorApp());
        this.codeToChange = codeToChange;
        submitButton.setText("Guardar");
        // Cambio el texto del usuario
        labelName.setText("Ver "+status+" :\t"+codeToChange);
        
        
        
        // Query to update   
        if (status.equalsIgnoreCase("tipo documental"))
            preparedQuery = "UPDATE Tipo_documental SET Nombre = ? where idtipo_documental ="+codeToChange;
        else if (status.equalsIgnoreCase("local"))
            preparedQuery = "UPDATE local SET Nombre = ? where idLocal ="+codeToChange;
        else if (status.equalsIgnoreCase("area"))
            preparedQuery = "UPDATE area SET Nombre = ? where idarea ="+codeToChange;
        else if (status.equalsIgnoreCase("estanteria"))
            preparedQuery = "UPDATE estanteria SET Nombre = ? where idestanteria ="+codeToChange;
        else if (status.equalsIgnoreCase("ambiente"))
            preparedQuery = "UPDATE ambiente SET Nombre = ? where idambiente ="+codeToChange;
        
        
        // Query Data 
        if (status.equalsIgnoreCase("tipo documental"))
            queryData = "SELECT * FROM Tipo_documental WHERE idtipo_documental ="+codeToChange;
        else if (status.equalsIgnoreCase("local"))
            queryData = "SELECT * FROM local WHERE idLocal ="+codeToChange;
        else if (status.equalsIgnoreCase("area"))
            queryData = "SELECT * FROM area WHERE idArea ="+codeToChange;
        else if (status.equalsIgnoreCase("estanteria"))
            queryData = "SELECT * FROM estanteria WHERE idEstanteria ="+codeToChange;
        else if (status.equalsIgnoreCase("ambiente"))
            queryData = "SELECT * FROM ambiente WHERE idAmbiente ="+codeToChange;
        loadData();
        
    }
    
    private void loadData(){
        try {
        
                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery(queryData);
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
                            
                                
                            
                            
                          }
                    }
                    
                    
                    pos++;
                }
                
                
             
             
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error al cargar datos en "+status+"","Ver "+status+""+codeToChange,0);
           
        }
        
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelPanel = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nombreField = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelPanel.setBackground(new java.awt.Color(255, 102, 0));

        labelName.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        labelName.setText("Nuevo Tipo Documental");

        org.jdesktop.layout.GroupLayout labelPanelLayout = new org.jdesktop.layout.GroupLayout(labelPanel);
        labelPanel.setLayout(labelPanelLayout);
        labelPanelLayout.setHorizontalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(labelName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 331, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        labelPanelLayout.setVerticalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .add(23, 23, 23)
                .add(labelName)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Nombre");

        nombreField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreFieldActionPerformed(evt);
            }
        });

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
                .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(nombreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 245, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(47, 47, 47))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(submitButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton2)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {jButton2, submitButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(labelPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(30, 30, 30)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(nombreField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 28, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(submitButton)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(14, 14, 14))
        );

        layout.linkSize(new java.awt.Component[] {jButton2, submitButton}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void ingresarNombreCampoExtra()  {
       
       processGood = true;
     
        try {
            int resultado = 0;
            PreparedStatement ps = null;
            ps = (PreparedStatement) connection.prepareStatement(preparedQuery);

            // Nombre
            ps.setString(1, nombreField.getText()); 
            
             
           resultado = ps.executeUpdate();
              
           
           processGood = true;
        
        } catch (SQLException e) {
           
            JOptionPane.showMessageDialog(rootPane, "Error al ingresar datos en Nuevo "+status+" \n"+e,"Error al Ingreso de datos",0);
            processGood = false;
           
        } // fin del catch
       
        
    }
    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed

        // Ingreso el Usuario
        ingresarNombreCampoExtra();

        if (processGood){
            
            // Confirmo que Ingreso exitosamente
            // Si llega hasta aca quiere decir que el dato fue correctamente
            JOptionPane.showMessageDialog(rootPane, "Dato ingresado exitosamente" );
            // Luego de ingresar todo // Refresco la tabla del MasterMainView

            FieldExtras.refreshTable(status);

            dispose();

        }

        

    }//GEN-LAST:event_submitButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void nombreFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreFieldActionPerformed
       submitButtonActionPerformed(evt);
    }//GEN-LAST:event_nombreFieldActionPerformed

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
            java.util.logging.Logger.getLogger(NewField.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewField.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewField.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewField.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewField("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel labelName;
    private javax.swing.JPanel labelPanel;
    private javax.swing.JTextField nombreField;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
}
