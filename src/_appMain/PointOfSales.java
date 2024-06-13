/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package _appMain;

import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author HP
 */
public class PointOfSales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Set the Look and Feel to Metal
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        FormLogin loginFrame = new FormLogin();
        Component parentComponent = null; // Replace with the actual component reference
        loginFrame.setLocationRelativeTo(parentComponent);
        loginFrame.setVisible(true);
    }
    
}
