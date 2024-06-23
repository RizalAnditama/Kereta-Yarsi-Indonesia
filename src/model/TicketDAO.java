/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.sql.ResultSet;

/**
 * The {@code TicketDAO} interface provides methods for performing operations on
 * ticket data in the database. Implementations of this interface should provide
 * the actual database interactions.
 * <p>
 * This interface defines methods to create a ticket, retrieve a ticket by its
 * ID,
 * and retrieve a joined result set of all related ticket data.
 * </p>
 * 
 * @see Ticket
 * @see ResultSet
 * @see controller.TicketController
 * 
 * @version 1.0
 * @since 2024-06-23
 * 
 * @author Muhammad Rizal Anditama Nugraha
 */
public interface TicketDAO {

    /**
     * Creates a new ticket in the database.
     * 
     * @param ticket the {@code Ticket} object containing ticket details
     * @return {@code true} if the ticket was successfully created; {@code false}
     *         otherwise
     */
    public boolean createTicket(Ticket ticket);

    /**
     * Retrieves a ticket from the database by its ID.
     * 
     * @param ticketId the ID of the ticket to retrieve
     * @return a {@code ResultSet} containing the ticket data, or {@code null} if no
     *         ticket was found
     */
    public ResultSet getTicket(String ticketId);

    /**
     * Retrieves all related ticket data from the database in a joined result set.
     * 
     * @param ticketId the ID of the ticket to retrieve related data for
     * @return a {@code ResultSet} containing the joined data of the ticket and its
     *         related entities
     */
    public ResultSet getAllJoin(String ticketId);
}
