package io.github.DekkerDing.customer.repository.jpa;

import io.github.DekkerDing.customer.repository.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByCustomerNo(String customerNo);

    List<CustomerEntity> findByPhone(String phone);

    List<CustomerEntity> findByEmail(String email);

    List<CustomerEntity> findByStatus(String status);

    List<CustomerEntity> findBySource(String source);

    boolean existsByCustomerNo(String customerNo);

    boolean existsByPhone(String phone);
}
