/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 * Interface for User Data Access Object.
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public interface UserDAO {

    boolean createUser(User user);

    User getUser(int userID);

    User getUserByUsername(String username);

    boolean updateUser(User user);

    boolean deleteUser(int userID);

    boolean register(User user);

    boolean login(String username, String password);
}
