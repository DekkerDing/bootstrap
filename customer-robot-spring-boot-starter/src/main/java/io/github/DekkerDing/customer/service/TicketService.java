package io.github.DekkerDing.customer.service;

import io.github.DekkerDing.customer.domain.ticket.Ticket;
import io.github.DekkerDing.customer.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final AtomicLong ticketSequence = new AtomicLong(1000);

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        ticket.setTicketNo(generateTicketNo());
        ticket.setStatus(Ticket.Status.PENDING.name());
        ticket.setCreatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    private String generateTicketNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "TKT" + dateStr + ticketSequence.incrementAndGet();
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public Optional<Ticket> findByTicketNo(String ticketNo) {
        return ticketRepository.findByTicketNo(ticketNo);
    }

    public List<Ticket> findByCustomerId(Long customerId) {
        return ticketRepository.findByCustomerId(customerId);
    }

    public List<Ticket> findByAssignedTo(Long agentId) {
        return ticketRepository.findByAssignedTo(agentId);
    }

    public List<Ticket> findByStatus(String status) {
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> findPendingTickets() {
        return ticketRepository.findPendingTickets();
    }

    @Transactional
    public Ticket assignTo(Long ticketId, Long agentId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (!ticketOpt.isPresent()) {
            throw new IllegalArgumentException("工单不存在 / Ticket not found");
        }
        Ticket ticket = ticketOpt.get();
        ticket.assignTo(agentId);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket resolve(Long ticketId, Long resolverId, String resolution) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (!ticketOpt.isPresent()) {
            throw new IllegalArgumentException("工单不存在 / Ticket not found");
        }
        Ticket ticket = ticketOpt.get();
        ticket.resolve(resolverId, resolution);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket close(Long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (!ticketOpt.isPresent()) {
            throw new IllegalArgumentException("工单不存在 / Ticket not found");
        }
        Ticket ticket = ticketOpt.get();
        ticket.close();
        return ticketRepository.save(ticket);
    }

    public boolean isEscalatable(Ticket ticket) {
        return ticket.isEscalatable();
    }
}
