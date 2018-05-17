package functions;

import extra.Constantes;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import nicon.notify.core.Notification;

/**
 *
 * @author n1ght_m4re
 */
public class Notas {
    String ruta,texto,titulo,hora,fecha,textoA;
    JTextArea jtx;
    JTextField title;
    BufferedWriter bw;
    BufferedReader br;
    JPanel panelDerecho;
    File dir = null;
    File archivo;
    FileReader fr;
    JButton[] botones;
    String[] textos;
    
    public Notas(String texto, String titulo, String hora, String fecha) {
        this.texto = texto;
        this.titulo = titulo;
        this.hora = hora;
        this.fecha = fecha;
    }
    
    public void crearNota() throws IOException {
        if(!titulo.equals("")) {
            ruta = Constantes.ReminderPath + titulo + ".txt";
            
            File file = new File(ruta);
            if(!file.exists()) {
                file.createNewFile();
            }

            bw = new BufferedWriter(new FileWriter(file));

            if(!((fecha.equals("")) && (hora.equals("")))) {
                bw.write(hora + "\n" + fecha + "\n\n");
                bw.write("----------------------------------------------------------" + "\n");
            }

            bw.write(texto);
            bw.close();
        } else {
            Notification.desktopMessage("Reminder","Cada nota necesita un titulo o nombre.",2);
        }
        
        if(title != null && jtx != null) {
            try {
                Constantes.paneles.btnPanelDerecho(title,jtx);
            } catch(IOException e) {
                System.out.println(e);
            }
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
            Constantes.paneles.btnPanelDerecho(this.title,this.jtx);
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    
    public void actualizarNota() {
        
    }
}