package io.github.DekkerDing.customer.repository.jpa.impl;

import io.github.DekkerDing.customer.domain.ticket.Ticket;
import io.github.DekkerDing.customer.repository.TicketRepository;
import io.github.DekkerDing.customer.repository.jpa.TicketJpaRepository;
import io.github.DekkerDing.customer.repository.jpa.entity.TicketEntity;
import io.github.DekkerDing.customer.repository.jpa.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryImpl implements TicketRepository {
    private final TicketJpaRepository ticketJpaRepository;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketRepositoryImpl(TicketJpaRepository ticketJpaRepository,
                               TicketMapper ticketMapper) {
        this.ticketJpaRepository = ticketJpaRepository;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return ticketJpaRepository.findById(id)
                .map(ticketMapper::toDomain);
    }

    @Override
    public Optional<Ticket> findByTicketNo(String ticketNo) {
        return ticketJpaRepository.findByTicketNo(ticketNo)
                .map(ticketMapper::toDomain);
    }

    @Override
    public List<Ticket> findByCustomerId(Long customerId) {
        return ticketMapper.toDomainList(
                ticketJpaRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
        );
    }

    @Override
    public List<Ticket> findByAssignedTo(Long assignedTo) {
        return ticketMapper.toDomainList(
                ticketJpaRepository.findByAssignedToAndStatusNotOrderByCreatedAtDesc(assignedTo, "CLOSED")
        );
    }

    @Override
    public List<Ticket> findByStatus(String status) {
        return ticketMapper.toDomainList(
                ticketJpaRepository.findByStatusOrderByCreatedAtDesc(status)
        );
    }

    @Override
    public List<Ticket> findPendingTickets() {
        return ticketMapper.toDomainList(
                ticketJpaRepository.findByStatusOrderByCreatedAtDesc("PENDING")
        );
    }

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity entity = ticketMapper.toEntity(ticket);
        TicketEntity savedEntity = ticketJpaRepository.save(entity);
        return ticketMapper.toDomain(savedEntity);
    }
}
