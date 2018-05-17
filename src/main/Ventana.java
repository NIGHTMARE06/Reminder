package main;

import extra.Constantes;
import functions.Notas;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
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
public class Ventana extends JFrame implements ActionListener {
    JPanel panelSuperior,panelCentral;
    JButton guardar = new JButton("GUARDAR");
    JButton nuevo = new JButton("NUEVO");
    JButton eliminar = new JButton("ELIMINAR");
    JLabel HORA,FECHA,TITULO;
    JTextField fecha,hora,titulo;
    JTextArea texto = new JTextArea(10,20);
    JScrollPane scroll = new JScrollPane(texto);
    String templateHora = "";
    String templateFecha = "";
    int n;
    
    public Ventana() {
        setTitle("~ REMINDER ~");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarComponentes(this.getContentPane());
        pack();
        setSize(900,550);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(Constantes.IconPath)));
        directorio();
        
        String name = "example+12:30:14+12-04-18+.txt";
        System.out.println("IndexOf: " + name.indexOf("+"));
        System.out.println("Hora: " + name.substring(name.indexOf("+")+1,name.indexOf("+")+9));
        System.out.println("Fecha: " + name.substring(name.indexOf("+")+10,name.indexOf(".")-1));
        
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yy");
        System.out.println("HorafechaConDateFormat: " + hourFormat.format(date));
    }
    
    public void iniciarComponentes(Container contenedor) {
        panelSuperior();
        panelDerecho();
        panelCentral();
        
        contenedor.setLayout(new BorderLayout(4,4));
        
        contenedor.add(panelSuperior,BorderLayout.NORTH);
        contenedor.add(Constantes.paneles.panelDerecho,BorderLayout.EAST);
        contenedor.add(panelCentral,BorderLayout.CENTER);
    }
    
    public void panelSuperior() {
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
    
    public void panelDerecho() {
        Constantes.paneles.createPanelDerecho();
    }
    
    public void panelCentral() {
        panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(1,1));
        
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        
        panelCentral.add(scroll);
    }
    
    public void directorio() {
        File folder = new File(Constantes.ReminderPath);
        if(!folder.exists()) {
            folder.mkdir();
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
        Constantes.paneles.updatePanelDerecho();
        
        if(e.getSource() == nuevo) {
            titulo.setText("");
            hora.setText("");
            fecha.setText("");
            texto.setText("~Ingresa aqui tu nota~");
        }
        
        if(e.getSource() == eliminar) {
            Constantes.notas.borrarNota(titulo,texto);
            panelCentral.updateUI();
            Constantes.paneles.updatePanelDerecho();
        }
    }
}
