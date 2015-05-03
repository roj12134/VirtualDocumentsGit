/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualdocumentslibrary;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;




/**
 *
 * @author dinuka
 */
public class MySqlBackup {

    private int STREAM_BUFFER = 512000;
    private boolean status = false;
    

    /**
     * This method is used for backup the mysql database
     *
     * @param host - hostname - localhost/127.0.0.1
     * @param port - 3306
     * @param user - username
     * @param password - password
     * @param db - database name
     * @param backupfile - file path to backup from the current location ex/
     * "/backup/"
     * @param mysqlDumpExePath - file path to mysqldump.exe from the current
     * location ex/ copy the mysqldump.exe from mysql bin in to backup folder
     * and set the path as backup ex/ "/backup/mysqldump.exe"
     * @return the status of the backup true/false
     */
    public boolean backupDatabase(String host, String port, String user, String password, String db, String backupfile, String mysqlDumpExePath) {
        try {
            // Get MySQL DUMP data
            String dump = getServerDumpData(host, port, user, password, db, mysqlDumpExePath);
            //check the backup dump
            if (status) {
                byte[] data = dump.getBytes();
                // Set backup folder
                String rootpath = System.getProperty("user.dir") + backupfile;

                // See if backup folder exists
                File file = new File(rootpath);
                if (!file.isDirectory()) {
                    // Create backup folder when missing. Write access is needed.
                    file.mkdir();
                }
                // Compose full path, create a filename as you wish
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                String filepath = rootpath + "backup(With_DB)-" + db + "-" + host + "-(" + dateFormat.format(date) + ").sql";
                // Write SQL DUMP file
                File filedst = new File(filepath);
                FileOutputStream dest = new FileOutputStream(filedst);
                dest.write(data);
                dest.close();
                System.out.println("Backup created successfully for - " + db + " " + host);
            } else {
                //when status false  
                System.out.println("Could not create the backup for - " + db + " " + host);
            }

        } catch (Exception ex) {
            System.out.println( ex.getCause());
        }

        return status;
    }

    private String getServerDumpData(String host, String port, String user, String password, String db, String mysqlDumpExePath) {
        StringBuilder dumpdata = new StringBuilder();
        String execlient = "";
        try {
            if (host != null && user != null && password != null && db != null) {
                // Set path. Set location of mysqldump 
                //  For example: current user folder and lib subfolder          

                execlient = System.getProperty("user.dir") + mysqlDumpExePath;

                // Usage: mysqldump [OPTIONS] database [tables]
                // OR     mysqldump [OPTIONS] --databases [OPTIONS] DB1 [DB2 DB3...]
                // OR     mysqldump [OPTIONS] --all-databases [OPTIONS]
                String command[] = new String[]{execlient,
                    "--host=" + host,
                    "--port=" + port,
                    "--user=" + user,
                    "--password=" + password,
                    "--skip-comments",
                    "--databases",
                    db};

                // Run mysqldump
                ProcessBuilder pb = new ProcessBuilder(command);
                Process process = pb.start();

                InputStream in = process.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                int count;
                char[] cbuf = new char[STREAM_BUFFER];

                // Read datastream
                while ((count = br.read(cbuf, 0, STREAM_BUFFER)) != -1) {
                    dumpdata.append(cbuf, 0, count);
                }

                //set the status
                int processComplete = process.waitFor();
                if (processComplete == 0) {                   
                    status = true;
                } else {
                    status = false;
                }
                // Close
                br.close();
                in.close();
            }

        } catch (Exception ex) {
            System.out.println( ex.getCause());
            return "";
        }
        return dumpdata.toString();
    }

    public boolean backupDataWithOutDatabase( String host, String port, String user, String password, String database, String backupPath) {
        String mysqldump="";
        
        if(OSValidator.isWindows())
            mysqldump = "mysqldump";
        else if (OSValidator.isMac())
            mysqldump = "/usr/local/mysql-5.6.13-osx10.7-x86_64/bin/mysqldump";
        
        boolean status = false;
        try {
            Process p = null;

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup(without_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";
            
            String batchCommand = "";
            if (!"".equals(password)) {
                //Backup with database
                batchCommand = mysqldump + " -h" + host + " --port=" + port + " -u" + user + " --password=" + password + " " + database + " -r "+ backupPath + "" + filepath ;
            } else {
                batchCommand = mysqldump + " -h" + host + " --port=" + port + " -u" + user + " " + database + " -r " + backupPath + "" + filepath  ;
            }
            

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();

            if (processComplete == 0) {
                status = true;
                System.out.println("Backup created successfully for without DB " + database + " in " + host + ":" + port);
            } else {
                status = false;
                System.out.println("Could not create the backup for without DB " + database + " in " + host + ":" + port);
            }

        } catch (IOException ioe) {
            System.out.println( ioe.getCause());
        } catch (Exception e) {
            System.out.println( e.getCause());
        }
        return status;
    }

    public boolean backupDataWithDatabase( String host, String port, String user, String password, String database, String backupPath) {
        String mysqldump="";
        
        if(OSValidator.isWindows())
            mysqldump = "mysqldump";
        else if (OSValidator.isMac())
            mysqldump = "/usr/local/mysql-5.6.13-osx10.7-x86_64/bin/mysqldump";
        
        
       
        boolean status = false;
        try {
            Process p = null;

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            
            String filepath = ".sql";
            //String filepath = "backup(with_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";

            String batchCommand = "";
            if (!"".equals(password)) {
                //Backup with database
                batchCommand = mysqldump + " -h" + host + " --port=" + port + " -u" + user + " --password=" + password + " --add-drop-database -B " + database + " -r "+ backupPath + "" + filepath ;
            } else {
                batchCommand = mysqldump + " -h" + host + " --port=" + port + " -u" + user + " --add-drop-database -B " + database + " -r " + backupPath + "" + filepath  ;
            }
            
           
            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
           
            int processComplete = p.waitFor();
            
            if (processComplete == 0) {
                status = true;
                System.out.println("Backup created successfully for with DB " + database + " in " + host + ":" + port);
                
            } else {
                status = false;
                System.out.println("Could not create the backup for with DB " + database + " in " + host + ":" + port);
                System.out.println("Executado "+batchCommand);
            }

        } catch (IOException ioe) {
            System.out.println("Error ioe" + ioe.getCause());
            
        } catch (Exception e) {
            System.out.println("Error e \n" + e.getCause());
            
        }
        return status;
    }

    public boolean backupAllDatabases(String dumpExePath, String host, String port, String user, String password, String backupPath) {
        boolean status = false;
        try {
            Process p = null;

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup(with_DB)-All-" + host + "-(" + dateFormat.format(date) + ").sql";

            String batchCommand = "";
            if (!"".equals(password)) {
                //Backup with database
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " --add-drop-database -A  -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --add-drop-database -A  -r \"" + backupPath + "" + filepath + "\"";
            }

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();


            if (processComplete == 0) {
                status = true;
                System.out.println("Backup created successfully with All DBs in " + host + ":" + port);
            } else {
                status = false;
                System.out.println("Could not create the backup for All DBs in " + host + ":" + port);
            }

        } catch (IOException ioe) {
            System.out.println( ioe.getCause());
        } catch (Exception e) {
            System.out.println( e.getCause());
        }
        return status;
    }

    /**
     * Restore the backup into a local database
     *
     * @param dbUserName - user name
     * @param dbPassword - password
     * @param source - backup file
     * @return the status true/false
     */
    public boolean restoreDatabase(String serverName,String dbUserName, String dbPassword, String source) {
        String mysql="";
        
        if(OSValidator.isWindows())
            mysql = "mysql";
        else if (OSValidator.isMac())
            mysql = "/usr/local/mysql-5.6.13-osx10.7-x86_64/bin/mysql";
        
        
            String[] executeCmd = null;
            if (!"".equals(dbPassword)) {
                //restore Database
                executeCmd = new String[]{mysql, "--host="+serverName,"--user=" + dbUserName, "--password=" + dbPassword, "-e", "source " + source};
            } else {
                
                executeCmd = new String[]{mysql, "--host="+serverName, "--user=" + dbUserName,  "-e", "source " + source};
            }
        
        Process runtimeProcess;
        try {
            
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup restored successfully with " + source);
               
                return true;
            } else {
                System.out.println("Could not restore the backup " + source);
                System.out.println("Executado : " + executeCmd[0] +" "+ executeCmd[1] +" "+ executeCmd[2] +" "+ executeCmd[3] +" "+ executeCmd[4]);
                
              
            }
        } catch (Exception ex) {
            System.out.println("Error "+ex);
        }

        return false;

    }
}
/*
 * Clase extra para saber en que sistema operativo esta corriendo. 
 */
class OSValidator {
 
	private static String OS = System.getProperty("os.name").toLowerCase();
 
	
 
	public static boolean isWindows() {
 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}
 
}
