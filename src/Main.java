/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import view.LoginFrm;

import javax.swing.UIManager;

/**
 *
 * @author HoangHoangTuan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Không thể thiết lập Look and Feel.");
        }
        java.awt.EventQueue.invokeLater(() -> new LoginFrm().setVisible(true));
    }
}
