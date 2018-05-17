package functions;

import extra.Constantes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author n1ght_m4re
 */
public class Paneles {
    JTextArea jtx;
    public JPanel panelDerecho;
    File[] paths;
    File dir = null;
    String[] textos;
    JTextField titulo,hora,fecha;;
    String[] nombres;
    JButton[] botones;
    
    public Paneles() {
    }
    
    public void createPanelDerecho() {
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho,BoxLayout.Y_AXIS));
    }
    
    public void btnPanelDerecho(JTextField titulo,JTextField hora,JTextField fecha,JTextArea jtx) throws FileNotFoundException, IOException {
        int num = 0;
        this.jtx = jtx;
        this.titulo = titulo;
        this.hora = hora;
        this.fecha = fecha;
        
        dir = new File(Constantes.ReminderPath);
        paths = dir.listFiles();
        textos = new String[paths.length];
        botones = new JButton[paths.length];
        nombres = new String[paths.length];
        
        for(File root:paths) {
            panelDerecho.removeAll();
            num++;
            nombres[num-1] = root.getName();
        }
        
        for(int i = 0; i < num; i++) {
            int n = i;
            
            /*if(nombres[i].contains("+")) {
                info = nombres[i].split(Pattern.quote("+"));
                nombre = info[0];
            } else {
                nombre = nombres[i].replace(".txt","");
            }*/
            String[] info = nombres[i].split(Pattern.quote("+"));
            String nombre = info[0].replace(".txt","");
            
            botones[i] = new JButton(nombre);
            
            botones[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jtx.setText("");
                    
                    titulo.setText(nombre);
                    
                    if(info.length > 1) {
                        hora.setText(info[1]);
                        fecha.setText(info[2]);
                    } else {
                        hora.setText("");
                        fecha.setText("");
                    }
                    
                    jtx.append(Constantes.notas.getTextNota(nombres[n]));
                }
            });
            
            panelDerecho.add(botones[i]);
        }
    }
    
    public void updatePanelDerecho() {
        panelDerecho.updateUI();
    }
}
