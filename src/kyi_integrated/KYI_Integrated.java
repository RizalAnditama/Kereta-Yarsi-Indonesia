package kyi_integrated;

import controller.UserController;
import view.SignUp_KYI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * The {@code KYI_Integrated} class serves as the entry point for the KYI
 * Integrated application. It initializes the user interface by creating an
 * instance of {@code SignUp_KYI} and making it visible.
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>
 *     java -jar KYI_Integrated.jar
 * </pre>
 *
 * @see controller.UserController
 * @see view.SignUp_KYI
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class KYI_Integrated {

    /**
     * The main method serves as the entry point for the application. It creates
     * an instance of {@code SignUp_KYI} with the {@code UserController} and
     * sets it visible.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        new SignUp_KYI(UserController.getInstance()).setVisible(true);
    }
}
