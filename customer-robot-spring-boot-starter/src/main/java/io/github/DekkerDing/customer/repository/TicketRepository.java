package io.github.DekkerDing.customer.repository;

import io.github.DekkerDing.customer.domain.ticket.Ticket;
import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> findById(Long id);
    Optional<Ticket> findByTicketNo(String ticketNo);
    List<Ticket> findByCustomerId(Long customerId);
    List<Ticket> findByAssignedTo(Long assignedTo);
    List<Ticket> findByStatus(String status);
    List<Ticket> findPendingTickets();
    Ticket save(Ticket ticket);
}
