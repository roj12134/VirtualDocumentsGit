/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualdocumentslibrary;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jcifs.smb.SmbFileInputStream;

/**
 *
 * @author Javier_Alay
 */
public class RemoteConnection{
    private String ip;
    private String user;
    private String pass;
    
    public RemoteConnection(String ip, String userToConnect, String password){
    
        this.ip = ip;
        this.user = userToConnect;
        this.pass = password;
        
    }
    
    public void createDir(String pathToSave) throws IOException{
        String path="smb://"+ip+getRemoteMotherPath(BDEstructura.getMotherPath())+pathToSave;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        SmbFile smbFile = new SmbFile(path,auth);
        if(!smbFile.exists()){
            smbFile.mkdirs();
        }
        System.out.println("Creado directorio con exito: "+path);
    } 
    
    public String openFile(String pathIn, int idIn) throws IOException{
        String[] pathPartido = pathIn.split("src");
        String localPath = System.getProperty("user.dir") + File.separator + "src" + pathPartido[1];
        localPath = localPath.replace(idIn+".pdf", "");
        
        File archivoLocal = new File(localPath);
        archivoLocal.mkdirs();
        String halfPath = "";
        String tmp = pathPartido[1];
        for(int i=0;i<tmp.length();i++){
            if(tmp.charAt(i) == '\\'){
                halfPath += "/";
                
            }
            else{
                halfPath += tmp.charAt(i);
            }
        }
        
        String path="smb://"+ip+getRemoteMotherPath(BDEstructura.getMotherPath())+halfPath;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        SmbFile smbFile = new SmbFile(path,auth);
        String fileToWrite = localPath+idIn+".pdf";
        if(smbFile.isFile() && smbFile.exists()){
            InputStream in = smbFile.getInputStream();
            FileOutputStream out =new FileOutputStream(fileToWrite);
            byte[] b = new byte[(int) smbFile.length()];
            in.read(b);
            out.write(b);
            out.close();
            // Codigo para abrir el archivo  
            return fileToWrite;
        }
        else
            return "";
    }
    
    public String getRemoteMotherPath(String path){
        String remotePath = "";
        
        for(int i=2;i<path.length();i++){
            if(path.charAt(i) == '\\'){
                remotePath += "/";
            }
            else{
                remotePath += path.charAt(i);
            }
        }
        
        return remotePath;
    }
    
    public void copyFile(String localPath, String remotePath, String fileName) throws IOException{
        String path="smb://"+ip+getRemoteMotherPath(BDEstructura.getMotherPath())+remotePath+"/"+fileName;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        SmbFile smbFile = new SmbFile(path,auth);
        File localFile = new File(localPath);
        FileInputStream in = new FileInputStream( localFile );
        SmbFileOutputStream out = new SmbFileOutputStream(smbFile);
        byte[] b = new byte[(int) localFile.length()];
        in.read(b);
        out.write(b);
        out.close();
        
        
    }
    
    public void copyFileInRemoteLocation(String toDeleteFile) throws IOException{
        String anulationDoc="smb://"+ip+getRemoteMotherPath(BDEstructura.getMotherPath())+"/anulado.pdf";
        String docToAnulate="smb://"+ip+getRemoteMotherPath(BDEstructura.getPathFromDocumentWithName(toDeleteFile));
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
        SmbFile remoteAnulado = new SmbFile(anulationDoc,auth);
        SmbFile remoteToAnulate = new SmbFile(docToAnulate,auth);
        remoteAnulado.copyTo(remoteToAnulate);
        
    }
    
     
}

