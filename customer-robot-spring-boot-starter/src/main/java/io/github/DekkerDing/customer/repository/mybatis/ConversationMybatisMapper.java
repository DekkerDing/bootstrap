package io.github.DekkerDing.customer.repository.mybatis;

import io.github.DekkerDing.customer.domain.conversation.Conversation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConversationMybatisMapper {

    @Insert("INSERT INTO customer_conversation (conversation_no, customer_id, agent_id, status, type, priority, title, start_time, end_time, created_at, updated_at) " +
            "VALUES (#{conversationNo}, #{customerId}, #{agentId}, #{status}, #{type}, #{priority}, #{title}, #{startTime}, #{endTime}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Conversation conversation);

    @Update("UPDATE customer_conversation SET customer_id = #{customerId}, agent_id = #{agentId}, status = #{status}, " +
            "type = #{type}, priority = #{priority}, title = #{title}, end_time = #{endTime}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Conversation conversation);

    @Select("SELECT * FROM customer_conversation WHERE id = #{id}")
    @ResultMap("conversationResultMap")
    Conversation findById(Long id);

    @Select("SELECT * FROM customer_conversation WHERE customer_id = #{customerId}")
    @ResultMap("conversationResultMap")
    List<Conversation> findByCustomerId(Long customerId);

    @Select("SELECT * FROM customer_conversation WHERE status = 'WAITING' ORDER BY created_at ASC")
    @ResultMap("conversationResultMap")
    List<Conversation> findWaitingConversations();
}
