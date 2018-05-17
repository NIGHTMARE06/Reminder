package functions;

import extra.Constantes;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import nicon.notify.core.Notification;

/**
 *
 * @author n1ght_m4re
 */
public class Notas {
    String ruta,texto,textoA;
    JTextField titulo,hora,fecha;
    JTextArea jtx;
    JTextField title;
    BufferedWriter bw;
    BufferedReader br;
    File archivo;
    FileReader fr;
    JButton[] botones;
    
    public Notas(String texto, JTextField titulo, JTextField hora, JTextField fecha) {
        this.texto = texto;
        this.titulo = titulo;
        this.hora = hora;
        this.fecha = fecha;
    }
    
    public void crearNota() throws IOException {
        if(!titulo.getText().equals("")) {
            if(!fecha.getText().equals("") && !hora.getText().equals("")) {
                ruta = Constantes.ReminderPath + titulo.getText() + "+" + hora.getText() + "+" + fecha.getText() + "+" + ".txt";
            } else {
                ruta = Constantes.ReminderPath + titulo.getText() + ".txt";
            }
            
            File file = new File(ruta);
            if(!file.exists()) {
                file.createNewFile();
                System.out.println("El archivo se creo :D");
            }

            bw = new BufferedWriter(new FileWriter(file));

            if(!((fecha.getText().equals("")) && (hora.getText().equals("")))) {
                bw.write(hora.getText() + "\n" + fecha.getText() + "\n\n");
                bw.write("----------------------------------------------------------" + "\n\n");
            }

            bw.write(texto);
            bw.close();
        } else {
            Notification.desktopMessage("Reminder","Cada nota necesita un titulo o nombre.",2);
        }
    }
    
    public void borrarNota(JTextField title, JTextArea jtx) {
        this.title = title;
        this.jtx = jtx;
        String ne = (String) JOptionPane.showInputDialog(
            null, "¿Que nota quieres eliminar?", "ELIMINAR",
            JOptionPane.DEFAULT_OPTION,null,Constantes.paneles.nombres,Constantes.paneles.nombres[0]
        );
        
        int nArchivosA = Constantes.paneles.dir.list().length;
        
        File archivoE = new File(Constantes.ReminderPath + ne);
        archivoE.delete();
        title.setText("");
        jtx.setText("");
        Constantes.paneles.panelDerecho.removeAll();
        
        int nArchivosD = Constantes.paneles.dir.list().length;
        
        if(nArchivosD < nArchivosA) {
            Notification.desktopMessage("Reminder","La nota ha sido eliminada ;D",3);
        } else {
            Notification.desktopMessage("Reminder","La nota no pudo ser eliminada :'D",2);
        }
        
        try {
            Constantes.paneles.updatePanelDerecho();
            Constantes.paneles.btnPanelDerecho(this.title,hora,fecha,this.jtx);
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    
    public void actualizarNota() {
        
    }
    
    public String getTextNota(String file) {
        String text = "";
        
        try {
            FileReader fr = new FileReader(Constantes.ReminderPath + file);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";

            while(temp != null) {
                temp = br.readLine();
                if(temp == null) {
                    break;
                }
                text += temp + "\n";
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return text;
    }
}