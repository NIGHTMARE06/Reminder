package main;

import extra.Constantes;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;

/**
 *
 * @author n1ght_m4re
 */
public class Ventana extends JFrame {
    String templateHora = "";
    String templateFecha = "";
    int n;
    
    public Ventana() {
        setTitle("~ REMINDER ~");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        iniciarComponentes(this.getContentPane());
        pack();
        setSize(800,550);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(Constantes.IconPath)));
        directorio();
        
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yy");
        System.out.println("HorafechaConDateFormat: " + hourFormat.format(date));
    }
    
    public void iniciarComponentes(Container contenedor) {
        Constantes.paneles.createPanelSuperior();
        Constantes.paneles.createPanelDerecho();
        Constantes.paneles.createPanelCentral();
        
        contenedor.setLayout(new BorderLayout(4,4));
        
        contenedor.add(Constantes.paneles.panelSuperior,BorderLayout.NORTH);
        contenedor.add(Constantes.paneles.panelDerecho,BorderLayout.EAST);
        contenedor.add(Constantes.paneles.panelCentral,BorderLayout.CENTER);
    }
    
    public void directorio() {
        File folder = new File(Constantes.ReminderPath);
        if(!folder.exists()) {
            folder.mkdir();
        }
    }
}
