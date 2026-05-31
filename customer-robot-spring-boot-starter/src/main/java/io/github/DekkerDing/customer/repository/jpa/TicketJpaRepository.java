package io.github.DekkerDing.customer.repository.jpa;

import io.github.DekkerDing.customer.repository.jpa.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {
    Optional<TicketEntity> findByTicketNo(String ticketNo);
    List<TicketEntity> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    List<TicketEntity> findByAssignedToAndStatusNotOrderByCreatedAtDesc(Long assignedTo, String status);
    List<TicketEntity> findByStatusOrderByCreatedAtDesc(String status);
    List<TicketEntity> findByPriorityAndStatusOrderByCreatedAtDesc(String priority, String status);
}
