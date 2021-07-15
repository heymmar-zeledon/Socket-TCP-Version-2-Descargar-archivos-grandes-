/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eliazith-zeledon
 */
import java.net.Socket;
import java.io.*;
import javax.swing.JOptionPane;

public class Cliente_TCP {

    /**
     * @param args the command line arguments
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String IpServe;
        String NameFile;
        String MensajeServe;
        String MensajeClient;
        int a = 0;

        IpServe = JOptionPane.showInputDialog("Direccion Ip del servidor: ");
        while (true) {
            try {
                Socket Connect = new Socket(IpServe, 1099);
                DataInputStream InputCliente = new DataInputStream(Connect.getInputStream());
                DataOutputStream OupoutServe = new DataOutputStream(Connect.getOutputStream());
                BufferedInputStream RecibirArchivo = new BufferedInputStream(Connect.getInputStream());
                MensajeClient = InputCliente.readUTF();
                JOptionPane.showMessageDialog(null, MensajeClient);

                NameFile = JOptionPane.showInputDialog("Ingresa el nombre del archivo a descargar");
                MensajeServe = NameFile;
                OupoutServe.writeUTF(MensajeServe);

                MensajeClient = InputCliente.readUTF();
                byte[] Bufer;
                if (MensajeClient.equals("El archivo no existe")) {
                    JOptionPane.showMessageDialog(null, "El archivo ya no existe en el server");
                } else {
                    JOptionPane.showMessageDialog(null, MensajeClient);

                    int i = 0;
                    Bufer = new byte[1024];
                    String Name = InputCliente.readUTF();
                    BufferedOutputStream Guardando = new BufferedOutputStream(new FileOutputStream(Name));
                    while ((i = RecibirArchivo.read(Bufer)) != -1) {
                        if (i == -1) {
                            break;
                        }
                        Guardando.write(Bufer, 0, i);
                    }
                    JOptionPane.showMessageDialog(null,"Descarga Completa");
                    RecibirArchivo.close();
                    Guardando.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cliente: " + e.getMessage());
            }
        }
    }
}
