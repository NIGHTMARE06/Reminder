package notes;

import extra.Constantes;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import nicon.notify.core.Notification;

/**
 *
 * @author n1ght_m4re
 */
public class Paneles implements ActionListener {
    JTextArea jtx;
    public JPanel panelDerecho,panelSuperior,panelCentral;
    File[] paths;
    File dir = null;
    String[] textos;
    JLabel HORA,FECHA,TITULO;
    JTextField fecha,hora,titulo;
    String[] nombres;
    JButton[] botones;
    JButton guardar = new JButton("GUARDAR");
    JButton nuevo = new JButton("NUEVO");
    JButton eliminar = new JButton("ELIMINAR");
    JTextArea texto = new JTextArea(10,20);
    JScrollPane scroll = new JScrollPane(texto);
    
    public Paneles() {
        createPanelDerecho();
    }
    
    public void createPanelDerecho() {
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho,BoxLayout.Y_AXIS));
    }
    
    public void createPanelSuperior() {
        panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(3,3,3,3));
        
        JLabel HORA = new JLabel("         Ingresa la hora:");
        HORA.setFont(new Font("Helvetica",Font.BOLD,16));
        hora = new JTextField();
        JLabel FECHA = new JLabel("         Ingresa la fecha:");
        FECHA.setFont(new Font("Helvetica",Font.BOLD,16));
        fecha = new JTextField();
        JLabel TITULO = new JLabel("         Ingresa el titulo:");
        TITULO.setFont(new Font("Helvetica",Font.BOLD,16));
        titulo = new JTextField();
        guardar.addActionListener(this);
        nuevo.addActionListener(this);
        eliminar.addActionListener(this);
        
        panelSuperior.add(guardar);
        panelSuperior.add(TITULO);
        panelSuperior.add(titulo);
        panelSuperior.add(nuevo);
        panelSuperior.add(HORA);
        panelSuperior.add(hora);
        panelSuperior.add(eliminar);
        panelSuperior.add(FECHA);
        panelSuperior.add(fecha);
    }
    
    public void createPanelCentral() {
        panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(1,1));
        
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        
        panelCentral.add(scroll);
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Constantes.notas = new Notas(texto.getText(),titulo,hora,fecha);
        
        if(e.getSource() == guardar) {
            try {
                Constantes.notas.crearNota();
            } catch(IOException ex) {}
        }
        
        try {
            Constantes.paneles.btnPanelDerecho(titulo,hora,fecha,texto);
        } catch(IOException ex) {
            Notification.desktopMessage("Reminder","No se ha podido mostrar tus notas.",2);
        }
        
        panelCentral.updateUI();
        panelDerecho.updateUI();
        
        if(e.getSource() == nuevo) {
            titulo.setText("");
            hora.setText("");
            fecha.setText("");
            texto.setText("~Ingresa aqui tu nota~");
        }
        
        if(e.getSource() == eliminar) {
            Constantes.notas.borrarNota(titulo,texto);
            panelCentral.updateUI();
            panelDerecho.updateUI();
        }
    }
}
