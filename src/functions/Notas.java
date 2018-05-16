package functions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import extra.Constantes;

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
    File[] paths;
    int num = 0;
    String[] nombres;
    JButton[] botones;
    String[] textos;
    
    public Notas(String TEXTO, String TITULO, String HORA, String FECHA) {
        texto = TEXTO;
        titulo = TITULO;
        hora = HORA;
        fecha = FECHA;
    }
    
    public void crearNota() throws IOException {
        if(!titulo.equals("")) {
            ruta = Constantes.ReminderPath + titulo + ".txt";
        } else {
            System.out.println("Cada nota necesita un titulo o nombre.");
            ruta = Constantes.ReminderPath + "SinNombre.txt";
        }
        
        File file = new File(ruta);
        if(!file.exists()) {
            file.createNewFile();
        }
        
        bw = new BufferedWriter(new FileWriter(file));
        
        if(!((fecha.equals("")) && (hora.equals("")))) {
            bw.write(hora + "\n" + fecha + "\n\n");
            bw.write("              ----------------------------------------------------------" + "\n");
        }
        
        bw.write(texto);
        bw.close();
    }
    
    public void btnPanelDerecho(JTextField TITLE,JTextArea JTX,JPanel PANEL_DERECHO) throws FileNotFoundException, IOException {
        jtx = JTX;
        panelDerecho = PANEL_DERECHO;
        title = TITLE;
        
        dir = new File(Constantes.ReminderPath);
        paths = dir.listFiles();
        textos = new String[paths.length];
        botones = new JButton[paths.length];
        nombres = new String[paths.length];
        
        for(File root:paths) {
            panelDerecho.removeAll();
            num++;
            nombres[num-1] = root.getName();
//            archivo = new File("/home/n1ght_m4re/REMINDER/" + nombres[num-1]);
//            fr = new FileReader(archivo);
//            br = new BufferedReader(fr);
//            textos[num-1] = br.readLine();
        }
        
        for(int i = 0; i < num; i++) {
            int n = i;
            botones[i] = new JButton(nombres[i].replace(".txt",""));
            
            botones[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jtx.setText("");
//                    jtx.setText(textos[n]);
//                    title.setText(nombres[n].replace(".txt",""));
//                    leerArchivo(nombres[n]);
                    try {
                        FileReader fr = new FileReader(Constantes.ReminderPath + nombres[n]);
                        BufferedReader br = new BufferedReader(fr);
                        String temp = "";
                            
                        while(temp != null) {
                            temp = br.readLine();
                            if(temp == null)
                                break;
                            //System.out.println(temp);
                            jtx.append(temp + "\n");
                        }
                    } catch(IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            });
            panelDerecho.add(botones[i]);
        }
    }
    
    public void borrarNota(JTextField TITLE, JTextArea JTX) {
        title = TITLE;
        jtx = JTX;
        String ne = (String) JOptionPane.showInputDialog(
            null, "Elija la nota a eliminar", "ELIMINAR",
            JOptionPane.DEFAULT_OPTION,null,nombres,nombres[0]
        );
        
        File archivoE = new File(Constantes.ReminderPath + ne);
        archivoE.delete();
        title.setText("");
        jtx.setText("");
        panelDerecho.removeAll();
    }
    
    public void leerArchivo(String a) {
        try {
            FileReader fr = new FileReader(Constantes.ReminderPath + a);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            
            while(temp != null) {
                temp = br.readLine();
                if(temp == null)
                    break;
//                System.out.println(temp);
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}