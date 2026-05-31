package io.github.DekkerDing.customer.repository.mybatis;

import io.github.DekkerDing.customer.domain.customer.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMybatisMapper {

    @Insert("INSERT INTO customer_customer (customer_no, name, phone, email, source, type, status, remarks, created_at, updated_at) " +
            "VALUES (#{customerNo}, #{name}, #{phone}, #{email}, #{source}, #{type}, #{status}, #{remarks}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Customer customer);

    @Update("UPDATE customer_customer SET name = #{name}, phone = #{phone}, email = #{email}, source = #{source}, " +
            "type = #{type}, status = #{status}, remarks = #{remarks}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Customer customer);

    @Delete("DELETE FROM customer_customer WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT * FROM customer_customer WHERE id = #{id}")
    @ResultMap("customerResultMap")
    Customer findById(Long id);

    @Select("SELECT * FROM customer_customer WHERE customer_no = #{customerNo}")
    @ResultMap("customerResultMap")
    Customer findByCustomerNo(String customerNo);

    @Select("SELECT * FROM customer_customer WHERE phone = #{phone}")
    @ResultMap("customerResultMap")
    List<Customer> findByPhone(String phone);

    @Select("SELECT * FROM customer_customer WHERE status = #{status}")
    @ResultMap("customerResultMap")
    List<Customer> findByStatus(String status);

    @Select("SELECT * FROM customer_customer")
    @ResultMap("customerResultMap")
    List<Customer> findAll();

    @Select("SELECT COUNT(*) > 0 FROM customer_customer WHERE customer_no = #{customerNo}")
    boolean existsByCustomerNo(String customerNo);

    @Select("SELECT COUNT(*) > 0 FROM customer_customer WHERE phone = #{phone}")
    boolean existsByPhone(String phone);
}
