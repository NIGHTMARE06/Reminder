package functions;

import extra.Constantes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    JTextField title;
    String[] nombres;
    JButton[] botones;
    
    public Paneles() {
    }
    
    public void createPanelDerecho() {
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho,BoxLayout.Y_AXIS));
    }
    
    public void btnPanelDerecho(JTextField title, JTextArea jtx) throws FileNotFoundException, IOException {
        int num = 0;
        this.jtx = jtx;
        this.title = title;
        
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
            String nombre = nombres[i].replace(".txt","");
            botones[i] = new JButton(nombre);
            
            botones[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jtx.setText("");
                    title.setText(nombre);
                    
                    jtx.append(getTextNota(nombres[n]));
                }
            });
            
            panelDerecho.add(botones[i]);
        }
    }
    
    public void updatePanelDerecho() {
        panelDerecho.updateUI();
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
