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
    int n;
    
    public Ventana() {
        setTitle("~ REMINDER ~");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarComponentes(this.getContentPane());
        pack();
        setSize(800,550);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(Constantes.IconPath)));
        directorio();
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
        Notas notas = new Notas(texto.getText(),titulo.getText(),hora.getText(),fecha.getText());
        
        if(e.getSource() == guardar) {
            try {
                //if(notas.leerNota(texto.getText()) != null) {
                    
                //}
                notas.crearNota();
            } catch(IOException ex) {}
        }
        
        try {
            Constantes.paneles.btnPanelDerecho(titulo,texto);
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
            notas.borrarNota(titulo,texto);
            panelCentral.updateUI();
            Constantes.paneles.updatePanelDerecho();
        }
    }
}
