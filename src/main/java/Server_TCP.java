/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eliazith-zeledon
 */
import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server_TCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Socket Connect = null;

        File[] Lista = null;

        String NameFileRecive;

        String User = System.getProperty("user.name");
        String Ruta = "/home/" + User + "/Documentos";

        File Directory = new File(Ruta);

        ServerSocket Server = null;
        BufferedOutputStream EnviarClienteFile;
        try {
            Server = new ServerSocket(1099);
        } catch (Exception e) {
            System.out.println("Error en el Server Socket: ");
        }
        if (!Directory.exists()) {
            JOptionPane.showMessageDialog(null, "Creando el directorio: " + Directory.exists());

            try {
                Directory.mkdir();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al crear directorio " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Servidor listo");

            /*Se sacan los ficheros del directorio*/
            try {
                Lista = Directory.listFiles();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error  en la lista" + e.getMessage());
            }

        }
    while(true)
        try {
            byte[] Bufer;
            Connect = Server.accept();
            JOptionPane.showMessageDialog(null, "Se hizo una conexion con : " + Connect.getInetAddress());

            EnviarClienteFile = new BufferedOutputStream(Connect.getOutputStream());

            DataOutputStream EscribirEncliente = new DataOutputStream(Connect.getOutputStream());
            DataInputStream LeerCliente = new DataInputStream(Connect.getInputStream());

            EscribirEncliente.writeUTF("Conexion Aceptada");

            NameFileRecive = LeerCliente.readUTF();
            File ArchivoEnviar = null;
            if (Lista != null) {
                for (File file : Lista) {

                    if (NameFileRecive.equals(file.getName())) {
                        ArchivoEnviar = file;
                    }

                }
            }

            if (!ArchivoEnviar.exists() || ArchivoEnviar == null) {
                EscribirEncliente.writeUTF("El archivo no existe");
            } else {
                EscribirEncliente.writeUTF("Descargando...");
                BufferedInputStream LeeFile = new BufferedInputStream(new FileInputStream(ArchivoEnviar));
                int TamByte = 0;
                Bufer = new byte[1024];
                EscribirEncliente.writeUTF(ArchivoEnviar.getName());
                while ((TamByte = LeeFile.read(Bufer)) != -1) {
                    if (TamByte == -1) {
                        break;
                    }
                    EnviarClienteFile.write(Bufer, 0, TamByte);
                }
                LeeFile.close();
                EnviarClienteFile.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "server : " + e.getMessage());
        }

    }

}
