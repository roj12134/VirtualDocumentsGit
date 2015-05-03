    
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualdocumentslibrary;  

import com.mysql.jdbc.Connection;
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
public class LoginView extends javax.swing.JFrame {
    private int codigoToGo = -1;
    private User dataUser [];
    private String errorMsj = "";
    public LoginView() {
        initComponents();
        //Set the icon of the app. 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png"))); 
        this.setTitle("Virtual Archive");
        jPanel3.setBackground(ConfigClass.getColorApp());
        fillUsers(); // voy a jalar todos los usuarios y los voy a menter en mi vector tipo User
        
    }
    
    private void fillUsers(){
        Statement st = DataBaseClass.getSt();
        Connection connection = DataBaseClass.getConnection();
        ResultSet resultSet = DataBaseClass.getResultSet();
        try {
          
                st = (Statement) connection.createStatement();
                resultSet = st.executeQuery("Select idUsuario,email, contrasena from Usuario ");
                ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
                
                resultSet.last();                // estas lineas se mueven al final de la consulta 
               
                int numrows=resultSet.getRow();
                int numcolumn=metaData.getColumnCount();
                
                dataUser = new User [numrows];  //la cantidad de material que va a salir    
                
                resultSet.beforeFirst();
                
                int pos=0;
                while(resultSet.next()){
                    String nom="";
                    int cod =0;
                    String pass="";
                    
                    for(int i=0;i<=numcolumn;i++){
                        switch(i){
                            case 1 :
                               cod=resultSet.getInt(1);
                               break;
                            case 2:
                                nom=resultSet.getString(2);
                                break;
                            case 3:
                                pass=resultSet.getString(3);
                                break;
                            
                            
                          }
                    }
                    
                    dataUser[pos]= new User (cod,pass,nom);
                    pos++;
                }
                
                
             
             
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error al cargar datos en usuario \n"+ex,"Acceso Usuario",0);
           
        }
    
        
    }

    private class User {
        private int codigo;
        private String contraseña;
        private String user;

        public User(int codigo, String contraseña, String user) {
            this.codigo = codigo;
            this.contraseña = contraseña;
            this.user = user;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getContraseña() {
            return contraseña;
        }

        public String getUser() {
            return user;
        }
        
        
        
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        passField = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 102, 0));

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel10.setText("Acceso al Programa");

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 248, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(23, 23, 23)
                .add(jLabel10)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        passField.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        passField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Contraseña");

        jLabel2.setText("Usuario");

        userField.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/login.png"))); // NOI18N
        jButton1.setText("Entrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(passField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 244, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(userField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(17, 17, 17))
        );

        layout.linkSize(new java.awt.Component[] {jLabel1, jLabel2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.linkSize(new java.awt.Component[] {passField, userField}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(56, 56, 56)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(userField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(32, 32, 32)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(passField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 69, Short.MAX_VALUE)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(15, 15, 15))
        );

        layout.linkSize(new java.awt.Component[] {jButton1, jLabel1, jLabel2, passField, userField}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private boolean isUser(){
        errorMsj = "No puedo entrar porque : \n";
        codigoToGo=-1;
        for (User i:dataUser){
            if (i.getUser().equalsIgnoreCase(userField.getText())){
                codigoToGo=i.getCodigo();
                return true;
            }
        }
        
        userField.setText("");
        errorMsj+=" - El usuario no esta registrado. \n";
        return false;
    }
    private boolean correctPassword(int codigoToGo){
        
        for (User i : dataUser){
            if (i.getCodigo()==codigoToGo){
                if (i.contraseña.equals(passField.getText()))
                    return true;
            }
        }
        
        passField.setText(""); // Vacio porque ay error aca 
        errorMsj+=" - La contraseña esta incorrecta. \n";
        return false;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        
        // Ok si la contraseña esta bien, solo voy a mandar el codigo y listo 
        if (isUser() && correctPassword(codigoToGo)){
            
            MasterMainView va = new MasterMainView(ConfigClass.configReader(),codigoToGo);
            
            va.setExtendedState(6);
            va.setVisible(true);
            
            dispose();
            
        }
        else {
            JOptionPane.showMessageDialog(rootPane, errorMsj,"Acceso denegado",0);
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void passFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passFieldActionPerformed
          
        jButton1ActionPerformed(evt);
        
    }//GEN-LAST:event_passFieldActionPerformed

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
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField passField;
    private javax.swing.JTextField userField;
    // End of variables declaration//GEN-END:variables
}
