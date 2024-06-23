/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.sql.ResultSet;

/**
 *
 * @author Muhammad Rizal Anditama Nugraha
 */
public interface TicketDAO {

    public boolean createTicket(Ticket ticket);

    public ResultSet getTicket(String ticketId);

    public ResultSet getAllJoin(String ticketId);
}
