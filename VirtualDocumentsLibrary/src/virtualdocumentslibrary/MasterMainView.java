
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualdocumentslibrary;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author giovannirojas
 */
public class MasterMainView extends javax.swing.JFrame {

    private static String status = "";
    private static Statement st = DataBaseClass.getSt();
    private static Connection connection = DataBaseClass.getConnection();
    private static ResultSet resultSet = DataBaseClass.getResultSet();
    private static User userIn = null;

    private static int codeIn = -1;

    // Variables usadas para las tablas de mysql 
    private static String orderParameters = "";
    private static String findParameter = "";
    private static String serieParameter = "";

    // Variables para poder Borrar 
    private boolean borrar = false;
    private boolean entro = false;
    private boolean erase = false;
    private int codigoToDelete;
    private String nombreToDelete;
    private String errorQuery;
    private int row = -1;
    // Fin de las variables de borrar 

    //variables para el arbol
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    private String[] identificador;
    private String identificadorActual;
    private Deque<String[]> children;
    private Deque<Integer> contadorP;
    private String[] ids;

    private String nameChecks[];
    private String fondoChecks[];
    private boolean selectedChecks[];

    private Deque<String> hijosFondo;
    private Deque<String> FondosEnFondo;
    private Deque<String> seriesEnFondo;
    //fin variables para el árbol

    // Variables para poder modificar
    private boolean edit = false;
    // Fin de variables de modificar

    public MasterMainView(String[] datos, int codeUser) { // Constructor cuando inicie el programa 
        initComponents();

        //Set the icon of the app. 
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Imagenes/iconApp.png")));
        this.setTitle("Virtual Archive");

        // inicializacion variables del arbol
        identificador = null;
        identificadorActual = null;
        children = new ArrayDeque<String[]>();
        contadorP = new ArrayDeque<Integer>();

        hijosFondo = new ArrayDeque<String>();
        FondosEnFondo = new ArrayDeque<String>();
        seriesEnFondo = new ArrayDeque<String>();
        //fin variables del arbol

        this.codeIn = codeUser;
        buildView(codeUser);
        internalFrame.setVisible(false);

        labelPanel.setBackground(ConfigClass.getColorApp());
        jPanel3.setBackground(ConfigClass.getColorApp());
        panelEstructura.setBackground(ConfigClass.getColorApp());
        panelEstructura1.setBackground(ConfigClass.getColorApp());
        setProgramIcon();
        institutionName.setText(datos[8]);

        // Oculto el tab Panel 
        tabPanels.setVisible(false);

        //Cargo datos del arbol
        //reloadTree();
        // Le voy a dar el metodo para que funcione el click del header. 
        tableMain.getTableHeader().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedColumnIdx = tableMain.getTableHeader().columnAtPoint(e.getPoint());
                String colName = tableMain.getColumnName(tableMain.getTableHeader().columnAtPoint(e.getPoint()));

                orderParameters = "";
                if (status.equalsIgnoreCase("usuarios")) {

                    switch (selectedColumnIdx) {
                        case 0:
                            orderParameters = "ORDER BY idusuario";
                            break;
                        case 1:
                            orderParameters = "ORDER BY nombre";
                            break;
                        case 2:
                            orderParameters = "ORDER BY apellido";
                            break;
                        case 3:
                            orderParameters = "ORDER BY email";
                            break;

                    }

                    refreshTable(status, findParameter + orderParameters); // Ver Usuarios
                    tableMain.setAutoResizeMode(2);
                    tableMain.setRowHeight(30);

                } else if (status.equalsIgnoreCase("Documentos")) {

                    if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Correlativo </td></tr><tr><td align=\"center\"> Programa </td> </tr></table></center></html>")) {
                        orderParameters = " ORDER BY idDocumento";
                    } else if (colName.equalsIgnoreCase("CUI")) {
                        orderParameters = " ORDER BY nombre_archivo";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha y </td></tr><tr><td align=\"cente\"> Hora Escaneo </td> </tr></table></center></html>")) {
                        orderParameters = " ORDER BY fecha_hora_escaneo";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Usuario que </td></tr><tr><td align=\"center\"> Escaneo </td> </tr></table></center></html>")) {
                        orderParameters = " ORDER BY usuario.nombre";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Numero de </td></tr><tr><td align=\"center\"> Folios </td> </tr></table></center></html>")) {
                        orderParameters = " ORDER BY numero_folios";
                    } else if (colName.equalsIgnoreCase("Tipo Documental")) {
                        orderParameters = " ORDER BY tipo_documental.nombre ";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center></html>")) {
                        orderParameters = " ORDER BY fecha_documento_dia";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > del </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center></html>")) {
                        orderParameters = " ORDER BY fecha_documento_mes";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Documento </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center></html>")) {
                        orderParameters = " ORDER BY fecha_documento_anio";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_dia_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_mes_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_anio_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_dia_final";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_mes_final";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_anio_final ";
                    } else if (colName.equalsIgnoreCase("Asunto")) {
                        orderParameters = " ORDER BY ASUNTO ";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Campos </td></tr><tr><td align=\"center\"> Especificos </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY campos_especificos";
                    } else if (colName.equalsIgnoreCase("Observaciones")) {
                        orderParameters = " ORDER BY Observaciones";
                    } else if (colName.equalsIgnoreCase("Local")) {
                        orderParameters = " ORDER BY local.nombre";
                    } else if (colName.equalsIgnoreCase("Area")) {
                        orderParameters = " ORDER BY area.nombre";
                    } else if (colName.equalsIgnoreCase("Ambiente")) {
                        orderParameters = " ORDER BY ambiente.nombre";
                    } else if (colName.equalsIgnoreCase("Estanteria")) {
                        orderParameters = " ORDER BY estanteria.nombre";
                    } else if (colName.equalsIgnoreCase("Caja")) {
                        orderParameters = " ORDER BY caja";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_caja_dia_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_caja_mes_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Caja Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_caja_anio_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_caja_dia_final";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_caja_mes_final";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Caja Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_caja_anio_final";
                    } else if (colName.equalsIgnoreCase("Legajo")) {
                        orderParameters = " ORDER BY legajo";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_legajo_dia_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_legajo_mes_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Legajo Inicial </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_legajo_anio_inicial";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_legajo_dia_final";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Extrema </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_legajo_mes_final";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Legajo Final </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY fecha_extrema_legajo_anio_final";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Orden </td></tr><tr><td align=\"center\"> Alfabetico </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY orden_alfabetico";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Orden </td></tr><tr><td align=\"center\"> Correlativo </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY ordern_correlativo";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Otra </td></tr><tr><td align=\"center\"> Dia </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY Otra_fecha_dia";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > - </td></tr><tr><td align=\"center\"> Mes </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY Otra_fecha_mes";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > Fecha </td></tr><tr><td align=\"center\"> Año </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY Otra_fecha_anio";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > ID Documento  </td></tr><tr><td align=\"center\"> 1 </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY Nuevo_numero_unico";
                    } else if (colName.equalsIgnoreCase("<html><center><table><tr><td align=\"center\" > ID Documento </td></tr><tr><td align=\"center\"> 2 </td> </tr></table></center><html>")) {
                        orderParameters = " ORDER BY Nuevo_numero_unico_2";
                    }

                    refreshTable(status, serieParameter + findParameter + orderParameters); // Ver Documentos 

                }
                /* else {
            
                 // Falta la tabla de documento. 
                 orderParameters = ""; // por el momento
                
                 String nombreColumn = "\n\n";
                 for (int i=0; i!= tableMain.getColumnCount(); i++){
                 nombreColumn += 
                 ""+
                 "                    else if (colName.equalsIgnoreCase("+tableMain.getColumnName(i)+"))\n "+
                 "                        orderParameters = \" \" ;\n ";
            
                     
                 }
                 System.out.println(nombreColumn);
                 }*/
            }
        ;

        });
        
        reloadTreeInit(certifiedTree);
        nameChecks = new String[certifiedTree.getRowCount()];
        fondoChecks = new String[certifiedTree.getRowCount()];
        selectedChecks = new boolean[certifiedTree.getRowCount()];
        putNamesIntoArrays(certifiedTree);
        reloadTree(certifiedTree);

    }

    private MasterMainView() {

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class User {

        private int codigo;
        private String nombre;
        private String apellido;
        private String contraseña;
        private String email;

        public User(int codigo, String nombre, String apellido, String contraseña, String email) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.apellido = apellido;
            this.contraseña = contraseña;
            this.email = email;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public String getContraseña() {
            return contraseña;
        }

        public String getEmail() {
            return email;
        }

    }

    private void buildView(int codeUser) {

        // primero que nada oculto todos 
        usuariosLabel.setVisible(false);
        configLabel.setVisible(false);
        estructuraLabel.setVisible(false);
        documentosLabel.setVisible(false);
        reportesLabel.setVisible(false);

        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT DISTINCT acces, modulo.nombre from usuario_permiso, permiso, modulo WHERE codigo_permiso = idpermiso AND codigo_modulo = idModulos AND codigo_sub_nivel IS NULL AND  codigo_usuario = " + codeUser + ";");
            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

            resultSet.last();                // estas lineas se mueven al final de la consulta 

            int numrows = resultSet.getRow();
            int numcolumn = metaData.getColumnCount();

            String modulo = "";
            int acces = -1;
            resultSet.beforeFirst();

            int pos = 0;
            while (resultSet.next()) {
                for (int i = 0; i <= numcolumn; i++) {
                    switch (i) {
                        case 1:
                            acces = resultSet.getInt(1);
                            break;

                        case 2:
                            modulo = resultSet.getString(2);
                    }
                }
                pos++;
                showOrHide(acces, modulo);
            }

            setLabelName(codeUser);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al extraer datos de la Base de Datos \n " + ex, "Error en Obtener Permisos", 0);
        }

    }

    public void setLabelName(int codeUser) {
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT * from usuario  WHERE idusuario  = " + codeUser + ";");
            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

            resultSet.last();                // estas lineas se mueven al final de la consulta 

            int numrows = resultSet.getRow();
            int numcolumn = metaData.getColumnCount();

            String nombre = "";
            int code = -1;
            String apellido = "";
            String pass = "";
            String user = "";
            resultSet.beforeFirst();

            int pos = 0;
            while (resultSet.next()) {
                for (int i = 0; i <= numcolumn; i++) {
                    switch (i) {
                        case 1:
                            code = resultSet.getInt(1);
                            break;

                        case 2:
                            nombre = resultSet.getString(2);
                            break;

                        case 3:
                            apellido = resultSet.getString(3);
                            break;

                        case 4:
                            pass = resultSet.getString(4);
                            break;

                        case 5:
                            user = resultSet.getString(5);
                            break;
                    }
                }
                pos++;
                userIn = new User(code, nombre, apellido, pass, user);

            }

            nameLabel.setText("" + nombre.split(" ")[0] + " " + apellido.split(" ")[0]);

        } catch (SQLException ex) {
            Logger.getLogger(MasterMainView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /* 
     Get the nameLabel
     */
    public static JLabel getNameLabel() {
        return nameLabel;
    }

    private void showOrHide(int acces, String modulo) {
        /*
         * De por si ya todos estan visibles 
         * entonces solo me queda ocultar los modulos que la persona no tiene acceso. 
         */

        if (acces == 1) {

            if (modulo.equalsIgnoreCase("Usuarios")) {
                usuariosLabel.setVisible(true);
            } else if (modulo.equalsIgnoreCase("Configuraciones")) {
                configLabel.setVisible(true);
            } else if (modulo.equalsIgnoreCase("Estructura de Datos")) {
                estructuraLabel.setVisible(true);
            } else if (modulo.equalsIgnoreCase("Documentos")) {
                documentosLabel.setVisible(true);
            } else if (modulo.equalsIgnoreCase("Reportes")) {
                reportesLabel.setVisible(true);
            }
        }
        // Si el acces no es cero y es cualquier numero entonces lo va a dejar visible

        // Le doy pack 
        repaint();
        pack();
    }

    public static void refreshTable(String sts) {
        // Load Data of tables 
        tableMain.setModel(new ResultSetTableModel(sts));

        // Configura el tamanio de las tablas. 
        if (sts.equalsIgnoreCase("Documentos")) {
            refreshTable(status, serieParameter + findParameter + orderParameters); // Ver Documentos 
            // Este de arriba es necesario para que cuando cree o elimine mantenga la forma de la tabla. 

            setColumnWidthDocuments();
            tableMain.setAutoResizeMode(0);
            tableMain.setRowHeight(50);
        } else if (sts.equalsIgnoreCase("Usuarios")) {
            refreshTable(status, findParameter + orderParameters); // Ver Usuarios 
            // Este de arriba es necesario para que cuando cree o elimine mantenga la forma de la tabla. 
            tableMain.setAutoResizeMode(2);
            tableMain.setRowHeight(30);

        }
    }

    public static void refreshTable(String sts, String par) {
        // Load Data of tables 
        tableMain.setModel(new ResultSetTableModel(sts, par));

        // Configura el tamanio de las tablas. 
        if (sts.equalsIgnoreCase("Documentos")) {
            setColumnWidthDocuments();
            tableMain.setAutoResizeMode(0);
            tableMain.setRowHeight(50);
        } else if (sts.equalsIgnoreCase("Usuarios")) {

            tableMain.setAutoResizeMode(2);
            tableMain.setRowHeight(30);

        }
    }

    private void toDelete() {
        erase = true;

        if (status.equalsIgnoreCase("usuarios")) {
            errorQuery = "DELETE FROM usuario WHERE idUsuario != 1 AND idusuario =";
        } else if (status.equalsIgnoreCase("Documentos")) {
            errorQuery = "DELETE FROM documento WHERE idDocumento = ";
        }

    }

    public void reDrawFrame(String[] datos) {

        setProgramIcon();
        institutionName.setText(datos[8]);
        labelPanel.setBackground(ConfigClass.getColorApp());
        jPanel3.setBackground(ConfigClass.getColorApp());
        panelEstructura.setBackground(ConfigClass.getColorApp());
        panelEstructura1.setBackground(ConfigClass.getColorApp());

        repaint();
    }

    public static User getUserIn() {
        return userIn;
    }

    private void setProgramIcon() {

        Object data[] = new DataBaseClass().giveData("SELECT logo FROM configuracion where idConfiguracion = 1 ;");
        // Foto
        byte[] bits = null;
        bits = (byte[]) data[0];

        ImageIcon imagef;

        if (bits != null) {
            imagef = new ImageIcon(bits);

             // creare una condicion cuando la imagen sea demasiado grande
            if (imagef.getIconWidth() > 400 || imagef.getIconHeight() > 286) {
                ImageIcon imaescala = new ImageIcon(imagef.getImage().getScaledInstance(512, 366, Image.SCALE_AREA_AVERAGING));
                programIcon.setIcon(imaescala);
            } else {
                programIcon.setIcon(imagef);
            }
        } else {
            programIcon.setIcon(null);
        }

    }

    public void eraseChilds(String[] childrens) {
        String[] hijos = childrens;
        int contador = 0;
        while (contador < hijos.length) {
            //obtiene el primer hijo 
            identificadorActual = hijos[contador];
            //determino si tiene hijos el hijo
            String[] secondChildrens = BDEstructura.getChilds(identificadorActual);
            // si tiene entonces:
            if (secondChildrens.length > 0) {
                //meto a la pila los hijos actuales y por donde voy 
                children.push(hijos);
                contadorP.push(contador);
                //borro los hijos de los hijos 
                eraseChilds(secondChildrens);
            } else {
                //si los hijos no tienen hijos
                //entonces borro el ultimo con todo y su permiso
                String consulta = "DELETE FROM permiso WHERE Nombre = 'Permiso de id" + BDEstructura.getIdSub_FondoFromIdentificador(identificadorActual) + "';";
                System.out.println(consulta);
                DataBaseClass.executeQuery(consulta);
                consulta = "DELETE FROM sub_fondo WHERE idsub_fondo != 1 AND identificador='" + identificadorActual + "';";
                DataBaseClass.executeQuery(consulta);
            }
            //aumento el contador de la posicion de los hijos 
            contador++;
            //si hay algo en la pila quiere decir que habian hijos anteriores pendientes 
            // entonces regreso a esos hijos para borrarlos 
            if (!children.isEmpty()) {
                hijos = children.pop();
                contador = contadorP.pop();
            }

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionsUser = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        fondoPopMenu = new javax.swing.JPopupMenu();
        nuevoFondo = new javax.swing.JMenuItem();
        nuevaSerie = new javax.swing.JMenuItem();
        eliminar = new javax.swing.JMenuItem();
        modificar = new javax.swing.JMenuItem();
        programIcon = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        configLabel = new javax.swing.JLabel();
        usuariosLabel = new javax.swing.JLabel();
        documentosLabel = new javax.swing.JLabel();
        reportesLabel = new javax.swing.JLabel();
        estructuraLabel = new javax.swing.JLabel();
        tabPanels = new javax.swing.JTabbedPane();
        panel1 = new javax.swing.JPanel();
        internalFrame = new javax.swing.JInternalFrame();
        //Set the icon of the app.
        internalFrame.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iconApp.png"))); // NOI18N
        internalFrame.setTitle("Virtual Archive");
        jPanel3 = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        findField = new javax.swing.JTextField();
        tableContainer = new javax.swing.JScrollPane();
        tableMain = new javax.swing.JTable();
        nuevoBoton = new javax.swing.JButton();
        trashLabel = new javax.swing.JLabel();
        treeContainer = new javax.swing.JScrollPane();
        treeDocuments = new javax.swing.JTree();
        panel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fondos = new javax.swing.JTree();
        panelEstructura = new javax.swing.JPanel();
        labelName1 = new javax.swing.JLabel();
        filtrosButton = new javax.swing.JButton();
        filtroDocumento = new javax.swing.JButton();
        reportesTab = new javax.swing.JPanel();
        reportesContainer = new javax.swing.JTabbedPane();
        reporteTab = new javax.swing.JPanel();
        configurarButton = new javax.swing.JButton();
        excelButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        reporteTable = new javax.swing.JTable();
        certificarTab = new javax.swing.JPanel();
        nuevoBoton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        certifiedTree = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        certificarTable = new javax.swing.JTable();
        panelEstructura1 = new javax.swing.JPanel();
        labelName2 = new javax.swing.JLabel();
        labelPanel = new javax.swing.JPanel();
        institutionName = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();

        optionsUser.setLabel("Usuario");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1340865665_application_form_edit.png"))); // NOI18N
        jMenuItem1.setText("Editar Informacion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        optionsUser.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/end.png"))); // NOI18N
        jMenuItem2.setText("Salir del Sistema");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        optionsUser.add(jMenuItem2);

        nuevoFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoSubfondo.png"))); // NOI18N
        nuevoFondo.setText("Nuevo Fondo/Sub Fondo");
        nuevoFondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoFondoActionPerformed(evt);
            }
        });
        fondoPopMenu.add(nuevoFondo);

        nuevaSerie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/serie.png"))); // NOI18N
        nuevaSerie.setText("Nueva Serie");
        nuevaSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaSerieActionPerformed(evt);
            }
        });
        fondoPopMenu.add(nuevaSerie);

        eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/delete.png"))); // NOI18N
        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        fondoPopMenu.add(eliminar);

        modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1340865769_custom-reports.png"))); // NOI18N
        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });
        fondoPopMenu.add(modificar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Virtual Library");
        setUndecorated(true);

        programIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        programIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/apple.png"))); // NOI18N
        programIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        configLabel.setBackground(new java.awt.Color(255, 255, 255));
        configLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        configLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/config2.png"))); // NOI18N
        configLabel.setText("Configuraciones");
        configLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        configLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        configLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                configLabelMouseClicked(evt);
            }
        });

        usuariosLabel.setBackground(new java.awt.Color(255, 255, 255));
        usuariosLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usuariosLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/userLogo.png"))); // NOI18N
        usuariosLabel.setText("Usuarios");
        usuariosLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        usuariosLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        usuariosLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usuariosLabelMouseClicked(evt);
            }
        });

        documentosLabel.setBackground(new java.awt.Color(255, 255, 255));
        documentosLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        documentosLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/filesLogo.png"))); // NOI18N
        documentosLabel.setText("Documentos");
        documentosLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        documentosLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        documentosLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                documentosLabelMouseClicked(evt);
            }
        });

        reportesLabel.setBackground(new java.awt.Color(255, 255, 255));
        reportesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportesLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/reportsLogo.png"))); // NOI18N
        reportesLabel.setText("Reportes");
        reportesLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reportesLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        reportesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportesLabelMouseClicked(evt);
            }
        });

        estructuraLabel.setBackground(new java.awt.Color(255, 255, 255));
        estructuraLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        estructuraLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/estructura.png"))); // NOI18N
        estructuraLabel.setText("Estructura");
        estructuraLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        estructuraLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        estructuraLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                estructuraLabelMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(6, 6, 6)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(configLabel)
                    .add(usuariosLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(estructuraLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(documentosLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(reportesLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 103, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {configLabel, usuariosLabel}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(23, 23, 23)
                .add(estructuraLabel)
                .add(42, 42, 42)
                .add(documentosLabel)
                .add(56, 56, 56)
                .add(reportesLabel)
                .add(57, 57, 57)
                .add(usuariosLabel)
                .add(47, 47, 47)
                .add(configLabel)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {configLabel, usuariosLabel}, org.jdesktop.layout.GroupLayout.VERTICAL);

        tabPanels.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabPanels.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tabPanels.setToolTipText("");

        internalFrame.setClosable(true);
        internalFrame.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        internalFrame.setVisible(true);
        internalFrame.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                internalFrameInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 255, 102));

        labelName.setFont(new java.awt.Font("Agency FB", 0, 36)); // NOI18N
        labelName.setText("Usuarios");

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(labelName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 371, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(23, 23, 23)
                .add(labelName)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel11.setText("Buscar :");

        findField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                findFieldKeyReleased(evt);
            }
        });

        tableMain.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableMain.getTableHeader().setReorderingAllowed(false);
        tableMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMainMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableMainMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableMainMouseReleased(evt);
            }
        });
        tableMain.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tableMainMouseDragged(evt);
            }
        });
        tableContainer.setViewportView(tableMain);

        nuevoBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1316277158_filenew.png"))); // NOI18N
        nuevoBoton.setText("Nuevo");
        nuevoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoBotonActionPerformed(evt);
            }
        });

        trashLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/empty.png"))); // NOI18N
        trashLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                trashLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                trashLabelMouseExited(evt);
            }
        });

        treeDocuments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeDocumentsMouseClicked(evt);
            }
        });
        treeContainer.setViewportView(treeDocuments);

        org.jdesktop.layout.GroupLayout internalFrameLayout = new org.jdesktop.layout.GroupLayout(internalFrame.getContentPane());
        internalFrame.getContentPane().setLayout(internalFrameLayout);
        internalFrameLayout.setHorizontalGroup(
            internalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(internalFrameLayout.createSequentialGroup()
                .add(internalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(internalFrameLayout.createSequentialGroup()
                        .add(17, 17, 17)
                        .add(internalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(nuevoBoton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 108, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(internalFrameLayout.createSequentialGroup()
                                .add(19, 19, 19)
                                .add(trashLabel))))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, internalFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(treeContainer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(internalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(tableContainer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                    .add(internalFrameLayout.createSequentialGroup()
                        .add(findField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 337, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))))
        );

        internalFrameLayout.linkSize(new java.awt.Component[] {jLabel11, nuevoBoton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        internalFrameLayout.setVerticalGroup(
            internalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(internalFrameLayout.createSequentialGroup()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(14, 14, 14)
                .add(internalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(findField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(internalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(internalFrameLayout.createSequentialGroup()
                        .add(tableContainer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(internalFrameLayout.createSequentialGroup()
                        .add(nuevoBoton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(treeContainer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .add(18, 18, 18)
                        .add(trashLabel)
                        .add(19, 19, 19))))
        );

        internalFrameLayout.linkSize(new java.awt.Component[] {findField, jLabel11}, org.jdesktop.layout.GroupLayout.VERTICAL);

        org.jdesktop.layout.GroupLayout panel1Layout = new org.jdesktop.layout.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 777, Short.MAX_VALUE)
            .add(panel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(internalFrame)
                    .addContainerGap()))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 621, Short.MAX_VALUE)
            .add(panel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(panel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(internalFrame)
                    .addContainerGap()))
        );

        tabPanels.addTab("Internal Frame", panel1);

        fondos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fondosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fondosMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fondosMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(fondos);

        panelEstructura.setBackground(new java.awt.Color(204, 255, 102));

        labelName1.setFont(new java.awt.Font("Agency FB", 0, 36)); // NOI18N
        labelName1.setText("Estructura de Documentos ");

        org.jdesktop.layout.GroupLayout panelEstructuraLayout = new org.jdesktop.layout.GroupLayout(panelEstructura);
        panelEstructura.setLayout(panelEstructuraLayout);
        panelEstructuraLayout.setHorizontalGroup(
            panelEstructuraLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEstructuraLayout.createSequentialGroup()
                .addContainerGap()
                .add(labelName1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 515, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(256, Short.MAX_VALUE))
        );
        panelEstructuraLayout.setVerticalGroup(
            panelEstructuraLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEstructuraLayout.createSequentialGroup()
                .add(23, 23, 23)
                .add(labelName1)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        filtrosButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1340865769_custom-reports.png"))); // NOI18N
        filtrosButton.setText("Campos Predefinidos");
        filtrosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrosButtonActionPerformed(evt);
            }
        });

        filtroDocumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/newFilter.png"))); // NOI18N
        filtroDocumento.setText(" Filtro Nuevo Documento");
        filtroDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroDocumentoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout panel2Layout = new org.jdesktop.layout.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEstructura, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(panel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(filtrosButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 139, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(filtroDocumento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 139, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panel2Layout.createSequentialGroup()
                .add(panelEstructura, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(28, 28, 28)
                .add(panel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panel2Layout.createSequentialGroup()
                        .add(filtrosButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(filtroDocumento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabPanels.addTab("Estructura de Documentos", panel2);

        reportesContainer.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        reportesContainer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportesContainerMouseClicked(evt);
            }
        });

        configurarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/config.png"))); // NOI18N
        configurarButton.setText("Configurar");
        configurarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configurarButtonActionPerformed(evt);
            }
        });

        excelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1340308507_application-vnd.ms-excel.png"))); // NOI18N
        excelButton.setText("Exportar");
        excelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelButtonActionPerformed(evt);
            }
        });

        reporteTable.setModel(new javax.swing.table.DefaultTableModel(
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
        reporteTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(reporteTable);

        org.jdesktop.layout.GroupLayout reporteTabLayout = new org.jdesktop.layout.GroupLayout(reporteTab);
        reporteTab.setLayout(reporteTabLayout);
        reporteTabLayout.setHorizontalGroup(
            reporteTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(reporteTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(reporteTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(configurarButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(excelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );
        reporteTabLayout.setVerticalGroup(
            reporteTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(reporteTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(reporteTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                    .add(reporteTabLayout.createSequentialGroup()
                        .add(configurarButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(excelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        reportesContainer.addTab("Reporte", reporteTab);

        nuevoBoton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1340865665_application_form_edit.png"))); // NOI18N
        nuevoBoton3.setText("Certificar");
        nuevoBoton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoBoton3ActionPerformed(evt);
            }
        });

        certifiedTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                certifiedTreeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(certifiedTree);

        certificarTable.setModel(new javax.swing.table.DefaultTableModel(
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
        certificarTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(certificarTable);

        org.jdesktop.layout.GroupLayout certificarTabLayout = new org.jdesktop.layout.GroupLayout(certificarTab);
        certificarTab.setLayout(certificarTabLayout);
        certificarTabLayout.setHorizontalGroup(
            certificarTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(certificarTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(certificarTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(nuevoBoton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 117, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
        );
        certificarTabLayout.setVerticalGroup(
            certificarTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(certificarTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(certificarTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(certificarTabLayout.createSequentialGroup()
                        .add(jScrollPane3)
                        .addContainerGap())
                    .add(certificarTabLayout.createSequentialGroup()
                        .add(nuevoBoton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                        .add(76, 76, 76))))
        );

        reportesContainer.addTab("Certificado", certificarTab);

        panelEstructura1.setBackground(new java.awt.Color(204, 255, 102));

        labelName2.setFont(new java.awt.Font("Agency FB", 0, 36)); // NOI18N
        labelName2.setText("Reportes de Documentos");

        org.jdesktop.layout.GroupLayout panelEstructura1Layout = new org.jdesktop.layout.GroupLayout(panelEstructura1);
        panelEstructura1.setLayout(panelEstructura1Layout);
        panelEstructura1Layout.setHorizontalGroup(
            panelEstructura1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEstructura1Layout.createSequentialGroup()
                .addContainerGap()
                .add(labelName2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 515, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEstructura1Layout.setVerticalGroup(
            panelEstructura1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelEstructura1Layout.createSequentialGroup()
                .add(23, 23, 23)
                .add(labelName2)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout reportesTabLayout = new org.jdesktop.layout.GroupLayout(reportesTab);
        reportesTab.setLayout(reportesTabLayout);
        reportesTabLayout.setHorizontalGroup(
            reportesTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(reportesTabLayout.createSequentialGroup()
                .addContainerGap()
                .add(reportesContainer)
                .addContainerGap())
            .add(panelEstructura1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        reportesTabLayout.setVerticalGroup(
            reportesTabLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, reportesTabLayout.createSequentialGroup()
                .add(panelEstructura1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(reportesContainer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPanels.addTab("Reportes", reportesTab);

        labelPanel.setBackground(new java.awt.Color(153, 255, 51));

        institutionName.setFont(new java.awt.Font("Agency FB", 0, 48)); // NOI18N
        institutionName.setText("Nombre Institución");

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLabel.setText("Jorge Villagran");
        nameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameLabelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                nameLabelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                nameLabelMouseReleased(evt);
            }
        });

        org.jdesktop.layout.GroupLayout labelPanelLayout = new org.jdesktop.layout.GroupLayout(labelPanel);
        labelPanel.setLayout(labelPanelLayout);
        labelPanelLayout.setHorizontalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(institutionName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 579, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(nameLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 311, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        labelPanelLayout.setVerticalGroup(
            labelPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanelLayout.createSequentialGroup()
                .add(21, 21, 21)
                .add(nameLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(labelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(institutionName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(labelPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .add(6, 6, 6)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(tabPanels)
                    .add(layout.createSequentialGroup()
                        .add(13, 13, 13)
                        .add(programIcon, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(7, 7, 7)))
                .add(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(labelPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(12, 12, 12)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(3, 3, 3)
                        .add(programIcon, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(54, 54, 54))
                    .add(layout.createSequentialGroup()
                        .add(tabPanels)
                        .add(33, 33, 33))))
        );

        programIcon.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void configLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_configLabelMouseClicked
        // Si puede ver o bien modificar configuraciones le saldra esto. 
        if (able(5) || able(6)) {
            ConfigView c = new ConfigView(ConfigClass.configReader(), this, able(6)); // le mando si es posible modificar configuraciones
            c.setLocationRelativeTo(null);
            c.setVisible(true);
            this.repaint();
        }
    }//GEN-LAST:event_configLabelMouseClicked

    public static JTable getReporteTable() {
        return reporteTable;
    }

    private void usuariosLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usuariosLabelMouseClicked
        // When somebody Click on Usuarios Label
        status = "usuarios";
        labelName.setText("" + "Usuarios");
        internalFrame.setVisible(true);
        findParameter = "";
        orderParameters = "";

        // Oculto el tab Panel 
        tabPanels.setVisible(true);
        tabPanels.removeAll();
        tabPanels.add(panel1);

        findField.setText("");  // Vacio el campo. 
        // Oculto el arbol 
        treeContainer.setVisible(false);

        if (able(4)) {
            trashLabel.setVisible(true);
            toDelete(); // Prepara para borrar
        } else {
            trashLabel.setVisible(false);

        }

        if (able(2)) {
            nuevoBoton.setVisible(true);  // Crear
        } else {
            nuevoBoton.setVisible(false);
        }

        if (able(1)) {
            refreshTable(status); // Ver Usuarios
            tableMain.setVisible(true);

            //setColumnWidth();
            tableMain.setAutoResizeMode(2);
            tableMain.setRowHeight(30);

        } else {
            refreshTable(status);
            tableMain.setVisible(false);

        }

        edit = able(3); // Modificar true o false

    }//GEN-LAST:event_usuariosLabelMouseClicked

    /*
     * Este metodo se utiliza ya que la tabla de documento es un poco grande. 
     */
    public static void setColumnWidthDocuments() {
        tableMain.getColumnModel().getColumn(0).setPreferredWidth(83);
        tableMain.getColumnModel().getColumn(1).setPreferredWidth(85);
        tableMain.getColumnModel().getColumn(2).setPreferredWidth(144);
        tableMain.getColumnModel().getColumn(3).setPreferredWidth(199);
        tableMain.getColumnModel().getColumn(4).setPreferredWidth(92);

        /* Aqui voy a empezar a generar el tamanio de las columnas segun los cheques de la 
         tabla filtro nuevo documento */
        // Index column
        int indexColumn = 5;
        /* Este momento obtendre los nombres que esten visibles de la tabla filtro_nuevo_documento. */
        try {

            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM filtro_nuevo_documento");
            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
            int numcolumn = metaData.getColumnCount();

            int tamanio = -1;
            String nombreColumna = "";
            int acceso = -1;

            while (resultSet.next()) {
                for (int i = 0; i <= numcolumn; i++) {
                    switch (i) {

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

                if (acceso == 1) {

                    if (nombreColumna.equalsIgnoreCase("Fecha Documento")) {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;

                    } else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Inicial")) {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;

                    } else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Final")) {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;

                    } else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Inicial")) {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;

                    } else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Caja Final")) {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;

                    } else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Inicial")) {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                    } else if (nombreColumna.equalsIgnoreCase("Fecha Extrema Legajo Final")) {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                    } else if (nombreColumna.equalsIgnoreCase("Otra Fecha")) {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                    } else {

                        tableMain.getColumnModel().getColumn(indexColumn).setPreferredWidth(tamanio);
                        indexColumn++;
                    }
                }

            } // Fin del while

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos a la tabla : Nuevo Documento \n" + ex, "Error en base de datos", 0);
        }

    }

    /*
     * Metodo que verifica si es posible o no 
     * segun el codigo de permiso que reciba y 
     * devuelve un true o false, segun sea el caso. 
     * */
    public static boolean able(int permiso) {
        try {
            st = (Statement) connection.createStatement();
            resultSet = st.executeQuery("SELECT acces from usuario_permiso, permiso WHERE codigo_permiso = idpermiso AND codigo_sub_nivel IS NULL AND codigo_usuario = " + codeIn + " AND codigo_permiso = " + permiso + ";");
            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();

            resultSet.last();                // estas lineas se mueven al final de la consulta 

            int numrows = resultSet.getRow();
            int numcolumn = metaData.getColumnCount();

            int acces = -1;
            resultSet.beforeFirst();

            int pos = 0;
            while (resultSet.next()) {
                for (int i = 0; i <= numcolumn; i++) {
                    switch (i) {
                        case 1:
                            acces = resultSet.getInt(1);
                            break;
                    }
                }
                pos++;
                if (acces == 0) {
                    return false;
                } else {
                    return true;
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al extraer datos de la Base de Datos \n " + ex, "Error en Obtener Permiso : " + permiso, 0);
        }
        return false;
    }
    private void documentosLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_documentosLabelMouseClicked

        // When somebody Click on Documentos Label
        status = "Documentos";
        labelName.setText("" + "Documentos");
        internalFrame.setVisible(true);
        // Oculto el tab Panel 
        tabPanels.setVisible(true);
        tabPanels.removeAll();
        tabPanels.add(panel1);
        serieParameter = "";
        findParameter = "";
        orderParameters = "";
        findField.setText("");

        // Borrar 
        if (able(14)) {
            trashLabel.setVisible(true);
            toDelete(); // Prepara para borrar
        } else {
            trashLabel.setVisible(false);

        }

        // Crear 
        if (able(12)) {
            nuevoBoton.setVisible(true);  // Crear
        } else {
            nuevoBoton.setVisible(false);
        }

        // Ver
        if (able(11)) {
            //Muestro el arbol
            treeContainer.setVisible(true);
            reloadTree(treeDocuments);
            treeDocuments.setSelectionRow(0);
            refreshTable("Documentos" + serieParameter + findParameter + orderParameters);
            //refreshTable(status); // Lo tendrias que quitar cuando ya funcione perfectamente . 
            tableMain.setVisible(true);

            setColumnWidthDocuments();
            tableMain.setAutoResizeMode(0);
            tableMain.setRowHeight(50);
        } else {
            tableMain.setVisible(false);
        }

        // Editar 
        edit = able(13); // Modificar true o false


    }//GEN-LAST:event_documentosLabelMouseClicked

    private void reportesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportesLabelMouseClicked
        // When somebody Click on reportes Label
        status = "reportes";
        labelName.setText("" + "Reportes");
        internalFrame.setVisible(true);

        // Oculto el tab Panel 
        tabPanels.setVisible(true);
        tabPanels.removeAll();
        tabPanels.add(reportesTab);

        // Inicialmente le quitare todo los tabs a reportesContainer, luego los ire agregando si es el caso
        reportesContainer.removeAll();
        // Ver reportes
        if (able(15)) {
            reportesContainer.add(reporteTab);
            reportesContainer.setTitleAt(0, "Reportes");
            reporteTable.setModel(new ResultSetTableModel("reporte", "SELECT idDocumento FROM documento WHERE idDocumento = -1", new String[]{"<html><center><table><tr><td align=\"center\" > Tabla sin Configurar </td></tr><tr><td align=\"center\"> Virtual Archive </td> </tr></table></center></html>"}, "")); // Have to work on it "Change the type of table. "
            reporteTable.setAutoResizeMode(2);
        } else {
            reporteTable.setVisible(false);
        }

        // Crear reportes 
        if (able(16)) {
            configurarButton.setVisible(true);
            // si puede crear reportes podra crear certificados de lo contrario no. ! 
            reportesContainer.add(certificarTab);
            reportesContainer.setTitleAt(1, "Certificaciones");

            // De aqui en adelante configurare lo de certificacion. !  
            certificarTable.setModel(new ResultSetTableModel("Certificar", " WHERE idDocumento = -1"));
            certificarTable.setAutoResizeMode(2);

        } else {
            configurarButton.setVisible(false);
        }
        // exportart reportes 
        if (able(17)) {
            excelButton.setVisible(true);
        } else {
            excelButton.setVisible(false);
        }

        // Have to work on it 
    }//GEN-LAST:event_reportesLabelMouseClicked

    private void estructuraLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estructuraLabelMouseClicked
        // When somebody Click on estructura Label
        status = "estructura";

        // Oculto el tab Panel 
        tabPanels.setVisible(true);
        tabPanels.removeAll();
        tabPanels.add(panel2);
        labelName.setText("" + "Estructura de Documentos");
        reloadTree(this.fondos);

        // Primero que nada quito todas las opciones de estructura de documentos
        fondoPopMenu.removeAll();

        // Ver estructura de datos, si no es verdadero lo oculto. 
        if (!able(7)) {
            fondos.setVisible(false);
            filtrosButton.setVisible(false);
            filtroDocumento.setVisible(false);
        }

        // Crear estructura Datos 
        if (able(8)) {
            fondoPopMenu.add(nuevoFondo);
            fondoPopMenu.add(nuevaSerie);
        }
        // Modificar estructura Documentos
        if (able(9)) {
            fondoPopMenu.add(modificar);
        }

        //Eliminar estructura de Documentos
        if (able(10)) {
            fondoPopMenu.add(eliminar);
        }


    }//GEN-LAST:event_estructuraLabelMouseClicked

    private void nameLabelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameLabelMousePressed
        if (evt.isPopupTrigger()) {

            optionsUser.show(nameLabel, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_nameLabelMousePressed

    private void nameLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameLabelMouseReleased
        if (evt.isPopupTrigger()) {

            optionsUser.show(nameLabel, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_nameLabelMouseReleased

    private void nameLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameLabelMouseClicked
        if (evt.isPopupTrigger()) {

            optionsUser.show(nameLabel, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_nameLabelMouseClicked

    private void tableMainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMainMousePressed

        row = tableMain.rowAtPoint(evt.getPoint());

        borrar = true;

        // accion qee va hacer siempre
        if (row > -1) {

            Object objeto = tableMain.getModel().getValueAt(row, 0);

            String ja = objeto.toString();
            // leo el tipo
            objeto = tableMain.getModel().getValueAt(row, 1);
            nombreToDelete = objeto.toString();

            codigoToDelete = Integer.parseInt(ja);

        }

    }//GEN-LAST:event_tableMainMousePressed

    private void tableMainMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMainMouseReleased
        if (erase) { // si es igual a true
            // accion del release
            internalFrame.setCursor(null);
            if (borrar == true && entro == true) {
                if (!status.equalsIgnoreCase("Documentos")) {
                    int messageType = JOptionPane.QUESTION_MESSAGE;
                    String[] options = {"Continuar", "Cancelar"};
                    int code = JOptionPane.showInternalOptionDialog(this.getContentPane(),
                            "¿Desea realmente borrar a " + nombreToDelete + "?",
                            "Confirmación", 1, messageType,
                            null, options, "Cancelar");

                    if (code == 0) {
                        DataBaseClass.executeQuery(errorQuery + codigoToDelete);
                        // reconfiguro todo

                        refreshTable(status);

                    } else if (code == 1) {
                        internalFrame.setCursor(null);
                    }
                } else {
                    // Este es el caso que sean documentos 
                    // por requerimiento del cliente se pondra desabilitado. 
                    String[] option = {"Continuar", "Cancelar"};

                    int codeSelected = JOptionPane.showInternalOptionDialog(this.getContentPane(),
                            "¿Desea realmente anular el documento " + nombreToDelete + "?", "Anular Documento", 1, JOptionPane.QUESTION_MESSAGE, null, option, "Cancelar");
                    if (codeSelected == 0) {
                        // Lo voy a desabilitar
                        String reason = JOptionPane.showInternalInputDialog(this.getContentPane(), "Razon de anular el documento " + nombreToDelete + " : ", "Anular Documento", 3);

                        DataBaseClass.executeQuery("UPDATE DOCUMENTO SET asunto = 'Anulado por : " + reason + "', observaciones = 'Anulado por : " + reason + "' , campos_especificos = 'Anulado por : " + reason + "' WHERE idDocumento =" + codigoToDelete);
                        DataBaseClass.executeQuery("UPDATE DOCUMENTO SET codigo_tipo_documental = 1, fecha_documento_dia =0, fecha_documento_mes=0, fecha_documento_anio=0, fecha_extrema_dia_Inicial =0 , fecha_extrema_mes_inicial =0, fecha_extrema_anio_inicial =0, fecha_extrema_dia_final =0, fecha_extrema_mes_final =0 , fecha_extrema_anio_final =0, codigo_local = null, codigo_area = null, codigo_ambiente = null, codigo_estanteria = null, caja = 0, fecha_extrema_caja_dia_inicial = 0, fecha_extrema_caja_mes_inicial =0 , fecha_extrema_caja_anio_inicial =0 , fecha_extrema_caja_dia_final =0, fecha_extrema_caja_mes_final =0, fecha_extrema_caja_anio_final =0, legajo = 0, fecha_extrema_legajo_dia_inicial=0, fecha_extrema_legajo_mes_inicial =0, fecha_extrema_legajo_anio_inicial =0, fecha_extrema_legajo_dia_final =0,fecha_extrema_legajo_mes_final =0, fecha_extrema_legajo_anio_final =0, orden_alfabetico = '', orden_correlativo = '', otra_fecha_dia = 0, otra_fecha_mes =0, otra_fecha_anio=0, nuevo_numero_unico = '', nuevo_numero_unico_2 = '' WHERE idDocumento = " + codigoToDelete);

                        // ALAY ACA CREA EL ARCHIVO DE ANULAR DOCUMENTO. 
                        // ENTONCES ACA ALAY CREA EL ARCHIVO DE ANULAR DOCUMENTO
                        String[] datos = ConfigClass.configReader();
                        if (datos[0].equals("localhost")) {
                            String documentPath = BDEstructura.getPathFromDocumentWithName(nombreToDelete);
                            String anuladoPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "ANULADO.pdf";
                            boolean success = copyAnulation(anuladoPath, documentPath);
                            if (success) {
                                JOptionPane.showMessageDialog(null, nombreToDelete + " anulado con éxito");
                            } else {
                                JOptionPane.showMessageDialog(null, "Anulación incorrecta");
                            }
                        } else {
                            //anulo remotamente:
                            RemoteConnection rc = new RemoteConnection(datos[0], datos[10], datos[11]);
                            try {
                                rc.copyFileInRemoteLocation(nombreToDelete);
                                JOptionPane.showMessageDialog(null, nombreToDelete + " anulado con éxito");
                            } catch (IOException ex) {
                                Logger.getLogger(MasterMainView.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, "Anulación incorrecta\n " + ex);
                            }

                        }

                        refreshTable(status);
                        setColumnWidthDocuments();
                    } else {
                        internalFrame.setCursor(null);
                    }
                }

                borrar = false;
                entro = false;
            }

            trashLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/empty.png")));
            borrar = false;

        }
    }//GEN-LAST:event_tableMainMouseReleased

    public boolean copyAnulation(String in, String out) {

        try {
            FileInputStream fs = new FileInputStream(in);
            int b;
            FileOutputStream os = new FileOutputStream(out);
            while ((b = fs.read()) != -1) {
                os.write(b);
            }
            os.close();
            fs.close();
            return true;
        } catch (Exception E) {
            E.printStackTrace();
            return false;
        }
    }
    private void tableMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMainMouseClicked
        /* Esta parte la puedo generalizar
         * ya que se repite varias veces
         */
        int num = -1;  // Inicializo la variables // Como siempre estara en la primera columna el codigo entonces siempre num sera el codigo del cual yo estoy apachando
        int fila = tableMain.rowAtPoint(evt.getPoint());
        if (fila > -1) {

            Object ob = tableMain.getModel().getValueAt(fila, 0);
            num = Integer.parseInt(ob.toString());
        }

        // Termina el proceso que se repetira siempre que den click
        if (edit) {  // Si edit es verdadero entonces podra editar sino no 
            if (status.equalsIgnoreCase("usuarios")) {

                NewUser va = new NewUser(num);
                va.setLocationRelativeTo(null);
                va.setVisible(true);

            } else if (status.equalsIgnoreCase("Documentos")) {

                CheckDocument va = new CheckDocument(num);

                va.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, 450);
                va.setLocationRelativeTo(null);
                va.setVisible(true);

            }
        }

    }//GEN-LAST:event_tableMainMouseClicked

    private void tableMainMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMainMouseDragged
        if (erase) {
            if (borrar == true && entro == false) {

                Toolkit tk = Toolkit.getDefaultToolkit();
                Image imagen = tk.getImage(getClass().getResource("/Imagenes/basura.png"));
                Point hotSpot = new Point(0, 0);
                Cursor cursor = tk.createCustomCursor(imagen, hotSpot, "Basura");
                internalFrame.setCursor(cursor);

            }
        }

    }//GEN-LAST:event_tableMainMouseDragged

    private void nuevoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoBotonActionPerformed
        // Aqui lo que hare que hare un como switch sobre en que estado esta

        if (status.equals("usuarios")) {
            NewUser va = new NewUser();
            va.setLocationRelativeTo(null);
            va.setVisible(true);

        } else if (status.equalsIgnoreCase("documentos")) {
            NewDocument va = new NewDocument();
            va.setExtendedState(6);
            va.setVisible(true);

        }

    }//GEN-LAST:event_nuevoBotonActionPerformed

    private void trashLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trashLabelMouseExited
        if (erase) {
            entro = false;
            borrar = false;
            trashLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/empty.png")));
        }
    }//GEN-LAST:event_trashLabelMouseExited

    private void trashLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trashLabelMouseEntered
        if (erase) {
            if (borrar == true) {
                entro = true;
                trashLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/full.png")));
            }
        }
    }//GEN-LAST:event_trashLabelMouseEntered

    private void filtrosButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrosButtonActionPerformed
        FieldExtras va = new FieldExtras();
        va.setLocationRelativeTo(null);
        va.setVisible(true);
    }//GEN-LAST:event_filtrosButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        NewUser va = new NewUser(userIn.getCodigo(), "Editar Informacion");
        va.setLocationRelativeTo(null);
        va.setVisible(true);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        DataBaseClass.commitBackup(ConfigClass.configReader());
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void nuevoFondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoFondoActionPerformed
        // TODO add your handling code here:

        NewFondo nf = new NewFondo(ConfigClass.configReader(), fondos, this, userIn.getCodigo());
        nf.setLocationRelativeTo(null);
        nf.setVisible(true);

        fondos.updateUI();

    }//GEN-LAST:event_nuevoFondoActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO add your handling code here:

        int deleteAnswer = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar? Se borrará todo el contenido", "Confirmar Eliminacion", 0);

        if (deleteAnswer == 0) {
            String[] child = null;
            //obtengo el identificador del que voy a borrar en la posicion 0
            Object[] directorios = fondos.getSelectionPath().getPath();
            if (directorios.length > 2) {
                identificador = BDEstructura.getIdentificadores(fondos.getSelectionPath().getLastPathComponent().toString(), directorios[2].toString()); //obtengo el que voy a borrar
            } else {
                identificador = BDEstructura.getIdentificadores(fondos.getSelectionPath().getLastPathComponent().toString(), "null"); //obtengo el que voy a borrar
            }
            // lo marco como el identificador actual
            identificadorActual = identificador[0];
            //lo borro
            //no puede borrar la raiz

            String consulta = "DELETE FROM permiso WHERE Nombre = 'Permiso de id" + BDEstructura.getIdSub_FondoFromIdentificador(identificadorActual) + "';";

            DataBaseClass.executeQuery(consulta);
            consulta = "DELETE FROM sub_fondo WHERE idsub_fondo != 1 AND identificador='" + identificadorActual + "';";
            DataBaseClass.executeQuery(consulta);

            //obtengo sus hijos
            child = BDEstructura.getChilds(identificadorActual);

            //si tiene hijos entonces los borra, sino continua
            if (child.length > 0) {

                identificadorActual = child[0];
                eraseChilds(child);

            }
            reloadTree(this.fondos);

        }

        fondos.updateUI();
    }//GEN-LAST:event_eliminarActionPerformed

    private void internalFrameInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_internalFrameInternalFrameClosing
        tabPanels.setVisible(false);
    }//GEN-LAST:event_internalFrameInternalFrameClosing

    private void fondosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fondosMouseClicked
        // TODO add your handling code here:
        String nombre = fondos.getSelectionPath().getLastPathComponent().toString();
        Object[] directorios = fondos.getSelectionPath().getPath();
        String fondo;
        if (directorios.length > 2) {
            fondo = directorios[2].toString();
        } else {
            fondo = "null";
        }

        if (evt.isPopupTrigger()) {

            fondoPopMenu.show(fondos, evt.getX(), evt.getY());
        }

    }//GEN-LAST:event_fondosMouseClicked

    private void fondosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fondosMousePressed
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {

            fondoPopMenu.show(fondos, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_fondosMousePressed

    private void fondosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fondosMouseReleased
        // TODO add your handling code here:
        if (evt.isPopupTrigger()) {

            fondoPopMenu.show(fondos, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_fondosMouseReleased

    private void filtroDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtroDocumentoActionPerformed
        NewFilterDocument va = new NewFilterDocument();
        va.setLocationRelativeTo(null);
        va.setVisible(true);
    }//GEN-LAST:event_filtroDocumentoActionPerformed

    private void nuevaSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaSerieActionPerformed
        // TODO add your handling code here:
        NewFondo nf = new NewFondo(ConfigClass.configReader(), fondos, this, 1, userIn.getCodigo());
        nf.setLocationRelativeTo(null);
        nf.setVisible(true);

        fondos.updateUI();

    }//GEN-LAST:event_nuevaSerieActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO add your handling code here:
        NewFondo nf;
        Object[] directorios = fondos.getSelectionPath().getPath();
        if (directorios.length > 2) {
            nf = new NewFondo(ConfigClass.configReader(), fondos, this, fondos.getSelectionPath().getLastPathComponent().toString(), directorios[2].toString(), userIn.getCodigo());
        } else {
            nf = new NewFondo(ConfigClass.configReader(), fondos, this, fondos.getSelectionPath().getLastPathComponent().toString(), "null", userIn.getCodigo());
        }

        nf.setLocationRelativeTo(null);
        nf.setVisible(true);
    }//GEN-LAST:event_modificarActionPerformed

    public String getPath(String path, javax.swing.JTree arbol) {
        //Directorios del path
        Object[] directorios = arbol.getSelectionPath().getPath();
        //Valor que voy a devolver
        String Path = "";
        //valor temporal para hacer los cambios
        String temporal = "";
        //quito las llaves que trae el path
        for (int i = 1; i < path.length() - 1; i++) {
            temporal += path.charAt(i);

        }

        //Divido el path por secciones
        String[] splitPath = temporal.split(",");
        //elimino el espacio que queda al principio de cada seccion del path, exceptuando la primer seccion de path que no trae espacio
        for (int i = 1; i < splitPath.length; i++) {
            String tmp = "";
            for (int j = 1; j < splitPath[i].length(); j++) {
                tmp += splitPath[i].charAt(j);
            }
            splitPath[i] = tmp;
        }
        //Formo el Path a partir del fondo donde esta 
        for (int i = 2; i < splitPath.length; i++) {
            if (directorios.length > 2) {
                String[] ids = BDEstructura.getIdentificadores(splitPath[i], directorios[2].toString());
                Path += File.separator + ids[0];
            } else {
                String[] ids = BDEstructura.getIdentificadores(splitPath[i], "null");
                Path += File.separator + ids[0];
            }

        }
        Path += File.separator;
        String pathToGive = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Fondos" + Path;
        return pathToGive;

    }
    private void treeDocumentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeDocumentsMouseClicked
        // TODO add your handling code here:
        //select documents only from the serie is selecting 

        String seleccion = null;
        String nombre = null;
        Object[] directorio;
        try {
            seleccion = treeDocuments.getSelectionPath().toString();
            nombre = treeDocuments.getSelectionPath().getLastPathComponent().toString();
            directorio = treeDocuments.getSelectionPath().getPath();
            String[] datosFromConfig = ConfigClass.configReader();
            String fondo;
            int serie;
            if (datosFromConfig[0].equals("localhost")) {
                String path = getPath(seleccion, treeDocuments);

                if (directorio.length > 2) {
                    serie = BDEstructura.isSerie(nombre, directorio[2].toString());
                    fondo = directorio[2].toString();

                } else {
                    serie = BDEstructura.isSerie(nombre, "null");
                    fondo = "null";

                }
                if (serie == 1) {
                    serieParameter = " WHERE codigo_serie = " + BDEstructura.getIdSub_Fondo(nombre, fondo) + " ";
                    // Alay, este metodo posiblemente lo cambies, solo recordad que este string debe de 
                    // empezar con WHERE codigo serie = ...... OR .... OR ... OR ... final      . 

                    refreshTable("Documentos", serieParameter + findParameter + orderParameters);
                } else {
                    //Si seleccionó un fondo entonces selecciona las series posibles dentro de ese fondo
                    //*******************************
                    String[] idnt = BDEstructura.getIdentificadores(nombre, fondo);
                    String[] arreglo = getSeriesFromFondo(idnt[0]);
                    seriesEnFondo.clear();
                    if (arreglo.length > 0) {
                        serieParameter = " WHERE codigo_serie = ";
                        for (int i = 0; i < arreglo.length; i++) {
                            int id = BDEstructura.getIdSub_FondoFromIdentificador(arreglo[i]);
                            serieParameter += id + " ";
                            if (i != (arreglo.length - 1)) {
                                serieParameter += "OR codigo_serie = ";
                            }
                        }
                        //********************************   
                    } else {
                        serieParameter = " WHERE codigo_serie = -1";
                    }
                    refreshTable("Documentos", serieParameter + findParameter + orderParameters);

                }

            } else {
                String[] pathPartido = getPath(seleccion, treeDocuments).split("src");
                String tmp = pathPartido[1];
                String remotePath = BDEstructura.getMotherPath() + tmp;
                if (directorio.length > 2) {
                    serie = BDEstructura.isSerie(nombre, directorio[2].toString());
                    fondo = directorio[2].toString();
                } else {
                    serie = BDEstructura.isSerie(nombre, "null");
                    fondo = "null";
                }
                if (serie == 1) {
                    serieParameter = " WHERE codigo_serie = " + BDEstructura.getIdSub_Fondo(nombre, fondo) + " ";

                    refreshTable("Documentos", serieParameter + findParameter + orderParameters);
                } else {
                    //*******************************
                    String[] idnt = BDEstructura.getIdentificadores(nombre, fondo);
                    String[] arreglo = getSeriesFromFondo(idnt[0]);
                    seriesEnFondo.clear();
                    if (arreglo.length > 0) {
                        serieParameter = " WHERE codigo_serie = ";
                        for (int i = 0; i < arreglo.length; i++) {
                            int id = BDEstructura.getIdSub_FondoFromIdentificador(arreglo[i]);
                            serieParameter += id + " ";
                            if (i != (arreglo.length - 1)) {
                                serieParameter += "OR codigo_serie = ";
                            }
                        }
                        //********************************   
                    } else {
                        serieParameter = "WHERE codigo_serie = -1 ";
                    }
                    refreshTable("Documentos", serieParameter + findParameter + orderParameters);
                }
            }

        } catch (Exception E) {

        }

    }//GEN-LAST:event_treeDocumentsMouseClicked

    public String[] getSeriesFromFondo(String idntfcdr) {
        Object[] series = null;
        String childs[] = BDEstructura.getChilds(idntfcdr);
        for (int i = 0; i < childs.length; i++) {
            hijosFondo.push(childs[i]);
        }
        do {
            if (!hijosFondo.isEmpty()) {
                String fondo = hijosFondo.pop();
                FondosEnFondo.push(fondo);
                String childs2[] = BDEstructura.getChilds(fondo);
                if (childs2.length > 0) {
                    getSeriesFromFondo(fondo);
                }
            }
        } while (!hijosFondo.isEmpty());
        series = FondosEnFondo.toArray();
        for (int i = 0; i < series.length; i++) {
            String nombre = BDEstructura.getName(series[i].toString());
            String fondo = BDEstructura.getFondoFromFondo(series[i].toString());
            if (fondo == null) {
                fondo = "null";

            }
            int isSerie = BDEstructura.isSerie(nombre, fondo);
            if (isSerie == 1) {
                seriesEnFondo.push(series[i].toString());
            }
        }

        Object[] series2 = seriesEnFondo.toArray();

        String[] returnArray = new String[series2.length];
        for (int i = 0; i < series2.length; i++) {
            returnArray[i] = series2[i].toString();
        }
        FondosEnFondo.clear();
        return returnArray;

    }
    private void configurarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configurarButtonActionPerformed
        ConfigReport va = new ConfigReport();
        va.setLocationRelativeTo(null);
        va.setVisible(true);
    }//GEN-LAST:event_configurarButtonActionPerformed

    private void excelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelButtonActionPerformed
        JOptionPane.showMessageDialog(null, "Aún no disponible", "Pendiente de terminar esto", 2);
    }//GEN-LAST:event_excelButtonActionPerformed

    private void nuevoBoton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoBoton3ActionPerformed
        /*
         // Boton certificar. Ira a seleccionar el destino donde se van a hacer las carpetas
         File archivoSeleccionado = null;
        
         JFileChooser fc = new JFileChooser(); // despliego un menu file seleccion
        
         fc.setAcceptAllFileFilterUsed(true); // acepto todos los archivos
        
         int returnValue = fc.showDialog(null,"Seleccionar");
        
         if (returnValue == JFileChooser.APPROVE_OPTION){
         archivoSeleccionado = fc.getSelectedFile(); // obtengo el archivo seleccionado
         }
         else {
         // quiere decir que dio cancelar al archivo entoncesno hago nadaa
         }

         fc.setSelectedFile(null); // esto es para indicar al file chooser que para la proxima empezara de cero.

         if (archivoSeleccionado != null) {
         JOptionPane.showMessageDialog(null,archivoSeleccionado.getAbsolutePath());
         String path = archivoSeleccionado.getAbsolutePath();
            

         }
         else {
         // Muestro esta ventana de Error indicando que hay error en cargar el archivo
         JOptionPane.showMessageDialog(null, "La dirección que se brinda no es válida", "Error en obtener dirección", 0);
         }
         */
        toExcelCertificar();

    }//GEN-LAST:event_nuevoBoton3ActionPerformed
    // Este metodo lo que haces pasar a excel la certificacion 
    private void toExcelCertificar() {
        ToExcel excel = new ToExcel();
        excel.certificarFile();

    }
    private void reportesContainerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportesContainerMouseClicked
        // TODO add your handling code here:
        reloadTree(certifiedTree);
        certifiedTree.setSelectionRow(1);
    }//GEN-LAST:event_reportesContainerMouseClicked

    private void certifiedTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_certifiedTreeMouseClicked

        String seleccion = null;
        String nombre = null;
        Object[] directorio;
        try {
            seleccion = certifiedTree.getSelectionPath().toString();
            nombre = certifiedTree.getSelectionPath().getLastPathComponent().toString();
            directorio = certifiedTree.getSelectionPath().getPath();
            String[] datosFromConfig = ConfigClass.configReader();
            String fondo;
            int serie;
            if (datosFromConfig[0].equals("localhost")) {
                String path = getPath(seleccion, certifiedTree);

                if (directorio.length > 2) {
                    serie = BDEstructura.isSerie(nombre, directorio[2].toString());
                    fondo = directorio[2].toString();

                } else {
                    serie = BDEstructura.isSerie(nombre, "null");
                    fondo = "null";

                }
                if (serie == 1) {
                    serieParameter = " WHERE codigo_serie = " + BDEstructura.getIdSub_Fondo(nombre, fondo) + " ";
                    // certificarTable.setModel(new ResultSetTableModel("Certificar",serieParameter));
                    //JOptionPane.showMessageDialog(null, "Esto es 1 ");
                } else {
                    //Si seleccionó un fondo entonces selecciona las series posibles dentro de ese fondo
                    //*******************************
                    String[] idnt = BDEstructura.getIdentificadores(nombre, fondo);
                    String[] arreglo = getSeriesFromFondo(idnt[0]);
                    seriesEnFondo.clear();
                    if (arreglo.length > 0) {
                        serieParameter = " WHERE codigo_serie = ";
                        for (int i = 0; i < arreglo.length; i++) {
                            int id = BDEstructura.getIdSub_FondoFromIdentificador(arreglo[i]);
                            serieParameter += id + " ";
                            if (i != (arreglo.length - 1)) {
                                serieParameter += "OR codigo_serie = ";
                            }
                        }
                        //********************************   
                    } else {
                        serieParameter = " WHERE codigo_serie = -1 ";
                    }
                    certificarTable.setModel(new ResultSetTableModel("Certificar", serieParameter));
                    //JOptionPane.showMessageDialog(null, "Esto es 2 ");

                }

            } else {
                String[] pathPartido = getPath(seleccion, certifiedTree).split("src");
                String tmp = pathPartido[1];
                String remotePath = BDEstructura.getMotherPath() + tmp;
                if (directorio.length > 2) {
                    serie = BDEstructura.isSerie(nombre, directorio[2].toString());
                    fondo = directorio[2].toString();
                } else {
                    serie = BDEstructura.isSerie(nombre, "null");
                    fondo = "null";
                }
                if (serie == 1) {
                    serieParameter = " WHERE codigo_serie = " + BDEstructura.getIdSub_Fondo(nombre, fondo) + " ";

                    //certificarTable.setModel(new ResultSetTableModel("Certificar",serieParameter));
                    //JOptionPane.showMessageDialog(null, "Esto es 3 ");
                } else {
                    //*******************************
                    String[] idnt = BDEstructura.getIdentificadores(nombre, fondo);
                    String[] arreglo = getSeriesFromFondo(idnt[0]);
                    seriesEnFondo.clear();
                    if (arreglo.length > 0) {
                        serieParameter = " WHERE codigo_serie = ";
                        for (int i = 0; i < arreglo.length; i++) {
                            int id = BDEstructura.getIdSub_FondoFromIdentificador(arreglo[i]);
                            serieParameter += id + " ";
                            if (i != (arreglo.length - 1)) {
                                serieParameter += "OR codigo_serie = ";
                            }
                        }
                        //********************************   
                    } else {
                        serieParameter = " WHERE codigo_serie = -1";
                    }
                    //certificarTable.setModel(new ResultSetTableModel("Certificar",serieParameter));
                    //JOptionPane.showMessageDialog(null, "Esto es 4 ");
                }
            }

        } catch (Exception E) {

        }

        String name = certifiedTree.getSelectionPath().getLastPathComponent().toString();
        Object directorios[] = certifiedTree.getSelectionPath().getPath();
        for (int i = 0; i < nameChecks.length; i++) {

            if (directorios.length > 2) {

                if (nameChecks[i].equals(name) && fondoChecks[i].equals(directorios[2].toString())) {
                    if (selectedChecks[i]) {
                        selectedChecks[i] = false;

                    } else {
                        selectedChecks[i] = true;

                    }
                } else {
                    selectedChecks[i] = false;
                }
            } else {
                if (nameChecks[i].equals(name) && (fondoChecks[i].equals("null") || fondoChecks[i].equals(nameChecks[i]))) {
                    if (selectedChecks[i]) {
                        selectedChecks[i] = false;
                    } else {
                        selectedChecks[i] = true;
                    }
                } else {
                    selectedChecks[i] = false;
                }
            }

        }
        reloadTree(certifiedTree);

    }//GEN-LAST:event_certifiedTreeMouseClicked

    private void findFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_findFieldKeyReleased
        String findText = "";
        findText = findField.getText();

        if (status.equalsIgnoreCase("usuarios")) {
            findParameter = "WHERE CAST(idUsuario AS CHAR) LIKE '%" + findText + "%' OR nombre LIKE '%" + findText + "%' OR apellido LIKE '%" + findText + "%' OR email LIKE '%" + findText + "%' ";
            refreshTable(status, findParameter + orderParameters); // Ver Usuarios
            tableMain.setAutoResizeMode(2);
            tableMain.setRowHeight(30);
        } else if (status.equalsIgnoreCase("Documentos")) {

            findParameter = " AND (CAST(idDocumento AS CHAR)  LIKE '%" + findText + "%'  OR  Nombre_archivo  LIKE '%" + findText + "%' OR  CAST(Fecha_hora_escaneo AS CHAR)  LIKE '%" + findText + "%' OR  usuario.email  LIKE '%" + findText + "%' OR CAST(numero_folios AS CHAR)  LIKE '%" + findText + "%' OR  tipo_documental.nombre  LIKE '%" + findText + "%' OR  CAST(Fecha_Documento_dia AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_documento_mes AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_documento_anio AS CHAR)  LIKE '%" + findText + "%' OR  CAST(Fecha_extrema_dia_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_mes_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_anio_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(Fecha_extrema_dia_Final AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_mes_Final AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_anio_Final AS CHAR)  LIKE '%" + findText + "%' OR Asunto  LIKE '%" + findText + "%' OR Campos_Especificos  LIKE '%" + findText + "%' OR Observaciones  LIKE '%" + findText + "%' OR  local.nombre  LIKE '%" + findText + "%' OR  area.nombre  LIKE '%" + findText + "%' OR  ambiente.nombre  LIKE '%" + findText + "%' OR  estanteria.nombre  LIKE '%" + findText + "%'  OR CAST(Caja AS CHAR)  LIKE '%" + findText + "%' OR  CAST(Fecha_extrema_caja_dia_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_caja_mes_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_caja_anio_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(Fecha_extrema_caja_dia_Final AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_caja_mes_Final AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_caja_anio_Final AS CHAR)  LIKE '%" + findText + "%' OR CAST(Legajo AS CHAR)  LIKE '%" + findText + "%' OR  CAST(Fecha_extrema_legajo_dia_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_legajo_mes_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_legajo_anio_Inicial AS CHAR)  LIKE '%" + findText + "%' OR  CAST(Fecha_extrema_legajo_dia_Final AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_legajo_mes_Final AS CHAR)  LIKE '%" + findText + "%' OR  CAST(fecha_extrema_legajo_anio_Final AS CHAR)  LIKE '%" + findText + "%' OR Orden_Alfabetico  LIKE '%" + findText + "%' OR Orden_Correlativo  LIKE '%" + findText + "%' OR  CAST(Otra_fecha_dia AS CHAR)  LIKE '%" + findText + "%' OR  CAST(Otra_fecha_mes AS CHAR)  LIKE '%" + findText + "%' OR  CAST(Otra_fecha_anio AS CHAR)  LIKE '%" + findText + "%' OR CAST(Nuevo_Numero_Unico AS CHAR)  LIKE '%" + findText + "%' OR CAST(Nuevo_Numero_Unico_2 AS CHAR) )";
            refreshTable(status, serieParameter + findParameter + orderParameters);
        }
    }//GEN-LAST:event_findFieldKeyReleased

    public String getUniqueNameByPath(String path) {
        //valor que voy a devolver
        String uniqueName = "";
        //valor temporal para hacer los cambios
        String temporal = "";
        //quito las llaves que trae el path
        for (int i = 1; i < path.length() - 1; i++) {
            temporal += path.charAt(i);

        }

        //Divido el path por secciones
        String[] splitPath = temporal.split(",");
        //elimino el espacio que queda al principio de cada seccion del path, exceptuando la primer seccion de path que no trae espacio
        for (int i = 1; i < splitPath.length; i++) {
            String tmp = "";
            for (int j = 1; j < splitPath[i].length(); j++) {
                tmp += splitPath[i].charAt(j);
            }
            splitPath[i] = tmp;
        }
        //Formo el nombre segun los identificadores a partir de la tercera posicion 
        for (int i = 2; i < splitPath.length; i++) {
            String[] id = BDEstructura.getIdentificadoresByUser(splitPath[i]);
            uniqueName += id[0];
        }

        return uniqueName;

    }

    private void putNamesIntoArrays(javax.swing.JTree arbol) {

        for (int i = 0; i < nameChecks.length; i++) {
            arbol.setSelectionRow(i);
            this.nameChecks[i] = arbol.getSelectionPath().getLastPathComponent().toString();
            this.selectedChecks[i] = false;
            Object[] directorios = arbol.getSelectionPath().getPath();
            if (directorios.length > 2) {
                this.fondoChecks[i] = directorios[2].toString();
            } else {
                this.fondoChecks[i] = "null";
            }
        }

    }

    public void reloadTreeInit(javax.swing.JTree arbol) {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Fondos");

        List<String[]> data = BDEstructura.getDataFromSub_Fondo();

        createTreeNodesForElement(root, getElementTreeFromPlainList(data));

        arbol.setModel(new DefaultTreeModel(root));
        arbol.setRootVisible(false);
        for (int i = 0; i < arbol.getRowCount(); i++) {
            arbol.expandRow(i);
        }

    }

    public void reloadTree(javax.swing.JTree arbol) {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Fondos");

        List<String[]> data = BDEstructura.getDataFromSub_Fondo();
        createTreeNodesForElement(root, getElementTreeFromPlainList(data));
        arbol.setModel(new DefaultTreeModel(root));
        arbol.setRootVisible(false);
        for (int i = 0; i < arbol.getRowCount(); i++) {
            arbol.expandRow(i);

        }

        //pongo los iconos
        arbol.setCellRenderer(new DefaultTreeCellRenderer() {
            private Icon serie = new ImageIcon(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Imagenes" + File.separator + "serie.png");
            private Icon subFondo = new ImageIcon(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Imagenes" + File.separator + "fondoSubfondo.png");
            private Icon checked = new ImageIcon(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Imagenes" + File.separator + "checked.png");
            private Icon unchecked = new ImageIcon(System.getProperty("user.dir") + File.separator + "src" + File.separator + "Imagenes" + File.separator + "unchecked.png");

            @Override
            public Component getTreeCellRendererComponent(javax.swing.JTree jTreeSelection,
                    Object value, boolean selected, boolean expanded,
                    boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(jTreeSelection, value,
                        selected, expanded, isLeaf, row, focused);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                String s = node.getUserObject().toString();
                TreeNode[] directorios = node.getPath();
                int isSerie = 0;
                if (directorios.length > 2) {
                    isSerie = BDEstructura.isSerie(s, directorios[2].toString());
                } else {
                    isSerie = BDEstructura.isSerie(s, "null");
                }

                if (isSerie == 0) {
                    if (jTreeSelection == certifiedTree) {
                        if (directorios.length == 3) {
                            for (int i = 0; i < nameChecks.length; i++) {
                                if (nameChecks[i].equals(s) && fondoChecks[i].equals(directorios[2].toString())) {
                                    if (selectedChecks[i]) {
                                        setIcon(checked);
                                    } else {
                                        setIcon(unchecked);
                                    }
                                }
                            }
                        } else {
                            setIcon(subFondo);
                        }
                    } else {
                        setIcon(subFondo);
                    }
                } else {
                    setIcon(serie);
                }
                return c;
            }
        });

    }

    Collection<Element> getElementTreeFromPlainList(List<String[]> data) {
        // builds a map of elements object returned from store
        Map<String, Element> values = new HashMap<String, Element>();
        // get the ids to get their permiso
        this.ids = new String[data.size()];
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
            boolean guaranteedAccess = checkNodePermiso(child.getId());

            if (child.getName().equals(BDEstructura.getName("Fondos"))) {
                DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
                dmtn.add(created);
                createTreeNodesForElement(created, child.getChildren());
            }
            if (guaranteedAccess) {
                DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
                dmtn.add(created);
                createTreeNodesForElement(created, child.getChildren());

            }

        }
    }

    public boolean checkNodePermiso(String idIn) {

        if (BDEstructura.getPermisoWithId(userIn.getCodigo(), idIn) == 1) {
            return true;
        } else {
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
            java.util.logging.Logger.getLogger(MasterMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MasterMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MasterMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MasterMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MasterMainView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel certificarTab;
    private javax.swing.JTable certificarTable;
    private javax.swing.JTree certifiedTree;
    private javax.swing.JLabel configLabel;
    private javax.swing.JButton configurarButton;
    private javax.swing.JLabel documentosLabel;
    private javax.swing.JMenuItem eliminar;
    private javax.swing.JLabel estructuraLabel;
    private javax.swing.JButton excelButton;
    private javax.swing.JButton filtroDocumento;
    private javax.swing.JButton filtrosButton;
    private javax.swing.JTextField findField;
    private javax.swing.JPopupMenu fondoPopMenu;
    private javax.swing.JTree fondos;
    private javax.swing.JLabel institutionName;
    private javax.swing.JInternalFrame internalFrame;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelName1;
    private javax.swing.JLabel labelName2;
    private javax.swing.JPanel labelPanel;
    private javax.swing.JMenuItem modificar;
    private static javax.swing.JLabel nameLabel;
    private javax.swing.JMenuItem nuevaSerie;
    private javax.swing.JButton nuevoBoton;
    private javax.swing.JButton nuevoBoton3;
    private javax.swing.JMenuItem nuevoFondo;
    private javax.swing.JPopupMenu optionsUser;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panelEstructura;
    private javax.swing.JPanel panelEstructura1;
    private javax.swing.JLabel programIcon;
    private javax.swing.JPanel reporteTab;
    private static javax.swing.JTable reporteTable;
    private javax.swing.JTabbedPane reportesContainer;
    private javax.swing.JLabel reportesLabel;
    private javax.swing.JPanel reportesTab;
    private javax.swing.JTabbedPane tabPanels;
    private javax.swing.JScrollPane tableContainer;
    private static javax.swing.JTable tableMain;
    private javax.swing.JLabel trashLabel;
    private javax.swing.JScrollPane treeContainer;
    private javax.swing.JTree treeDocuments;
    private javax.swing.JLabel usuariosLabel;
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

        public String getId() {
            return id;
        }

    }

}
