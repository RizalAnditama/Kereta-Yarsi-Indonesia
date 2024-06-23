package kyi_integrated;


import controller.UserController;
import view.SignUp_KYI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public class KYI_Integrated {
    public static void main(String[] args) {
        new SignUp_KYI(UserController.getInstance()).setVisible(true);
    }
}
