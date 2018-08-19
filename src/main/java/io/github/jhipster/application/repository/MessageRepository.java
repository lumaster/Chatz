package io.github.jhipster.application.repository;

import io.github.jhipster.application.model.constant.Constant;
import io.github.jhipster.application.service.FacebookService;
import io.github.jhipster.application.web.websocket.dto.ChatMessage;
import io.github.jhipster.application.web.websocket.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MessageRepository {

    private static final Logger log = LoggerFactory.getLogger(MessageRepository.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MessageRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Customer> getCustomersByLatestMessage() {

        List<Customer> customerList = new ArrayList<>();

        log.debug("getMessages :: Query = {}" + NamedQuery.GET_ALL_CUSTOMERS);
        List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(
            NamedQuery.GET_ALL_CUSTOMERS, new EmptySqlParameterSource());

        for (Map<String, Object> row : rows) {
            Customer customer = new Customer();
            customer.setCustomerId(((Integer) row.get("customerId")).longValue());
            customer.setSocialId((String) row.get("socialId"));
            customer.setSocialName((String) row.get("socialName"));
            customer.setFirstName((String) row.get("firstName"));
            customer.setLastName((String) row.get("lastName"));
            customer.setAddress((String) row.get("address"));
            customer.setImageUrl((String) row.get("imageUrl"));
            customer.setPhone((String) row.get("phone"));
            customer.setEmail((String) row.get("email"));
            customer.setCreateDate(((Timestamp) row.get("createDate")).toLocalDateTime());
//            customer.setUpdateDate(((Timestamp) row.get("updateDate")).toLocalDateTime());
            customer.setLastMessageDateTime(((Timestamp) row.get("lastMessageDateTime")).toLocalDateTime());
            customer.setChannel((String) row.get("channel"));
            customer.setCustomerType((String) row.get("customerType"));
            customerList.add(customer);
        }

        return customerList;
    }

    public List<ChatMessage> getMessages(String customerId) {

        List<ChatMessage> messageList = new ArrayList<>();
        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValue("customerId", Long.parseLong(customerId));

        log.debug("getMessages :: Query = {}, Params = {}" + NamedQuery.GET_ALL_MESSAGE_BY_CUSTOMER_ID, sqlParams.getValues().toString());
        List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(NamedQuery.GET_ALL_MESSAGE_BY_CUSTOMER_ID, sqlParams);

        for (Map<String, Object> row : rows) {
            ChatMessage message = new ChatMessage();
            message.setType((String) row.get("type"));

            if (Constant.IMAGE.equalsIgnoreCase(message.getType())) {
                message.setContent((byte[]) row.get("message"));
            } else {
                message.setMessage(new String((byte[]) row.get("message")));
            }

            message.setStatus((String) row.get("status"));
            message.setCreateDate(((Timestamp) row.get("createDate")).toLocalDateTime());

            message.getCustomer().setChannel((String) row.get("channel"));
            message.getCustomer().setCustomerType((String) row.get("customerType"));
            messageList.add(message);
        }

        return messageList;

    }

    public Customer getCustomer(String customerId) {

        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValue("customerId", Long.parseLong(customerId));
        String query = NamedQuery.GET_CUSTOMER_BY_CUSTOMER_ID;

        Customer customer = null;
        try {
            log.debug("getCustomer :: Query = {}, Params = {}", query, sqlParams.getValues().toString());
            Map<String, Object> row = namedParameterJdbcTemplate.queryForMap(query, sqlParams);

            if (!row.isEmpty()) {
                customer = new Customer();
                customer.setCustomerId(((Integer) row.get("customerId")).longValue());
                customer.setSocialId((String) row.get("socialId"));
                customer.setSocialName((String) row.get("socialName"));
                customer.setFirstName((String) row.get("firstName"));
                customer.setLastName((String) row.get("lastName"));
                customer.setAddress((String) row.get("address"));
                customer.setImageUrl((String) row.get("imageUrl"));
                customer.setPhone((String) row.get("phone"));
                customer.setEmail((String) row.get("email"));
                customer.setLastMessageDateTime(((Timestamp) row.get("lastMessageDateTime")).toLocalDateTime());
                customer.setChannel((String) row.get("channel"));
                customer.setCustomerType((String) row.get("customerType"));
            }

            return customer;

        } catch (EmptyResultDataAccessException ex) {
            log.error("Customer is not found");
            return customer;
        }

    }

    public Customer getCustomerBySocialId(String socialId) {

        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValue("socialId", socialId);
        String query = NamedQuery.GET_CUSTOMER_BY_SOCIAL_ID;

        Customer customer = null;
        try {
            log.debug("getCustomer :: Query = {}, Params = {}", query, sqlParams.getValues().toString());
            Map<String, Object> row = namedParameterJdbcTemplate.queryForMap(query, sqlParams);

            if (!row.isEmpty()) {
                customer = new Customer();
                customer.setCustomerId(((Integer) Optional.ofNullable(row.get("customerId")).orElse("0")).longValue());
                customer.setSocialId((String) Optional.ofNullable(row.get("socialId")).orElse(""));
                customer.setSocialName((String) Optional.ofNullable(row.get("socialName")).orElse(""));
                customer.setFirstName((String) Optional.ofNullable(row.get("firstName")).orElse(""));
                customer.setLastName((String) Optional.ofNullable(row.get("lastName")).orElse(""));
                customer.setAddress((String) Optional.ofNullable(row.get("address")).orElse(""));
                customer.setImageUrl((String) Optional.ofNullable(row.get("imageUrl")).orElse(""));
                customer.setPhone((String) Optional.ofNullable(row.get("phone")).orElse(""));
                customer.setEmail((String) Optional.ofNullable(row.get("email")).orElse(""));
                customer.setChannel((String) Optional.ofNullable(row.get("channel")).orElse(""));
                customer.setCustomerType((String) Optional.ofNullable(row.get("customerType")).orElse(""));
            }

            return customer;

        } catch (EmptyResultDataAccessException ex) {
            log.error("Customer is not found");
            return customer;
        }

    }


    public Long saveCustomer(Customer customer) {

        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValue("socialId", customer.getSocialId());
        sqlParams.addValue("socialName", customer.getSocialName());
        sqlParams.addValue("imageUrl", customer.getImageUrl());
        sqlParams.addValue("channel", customer.getChannel());

        GeneratedKeyHolder customerIdHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(NamedQuery.SAVE_CUSTOMER, sqlParams, customerIdHolder);

        return ((Integer) customerIdHolder.getKeys().get("id")).longValue();

    }

    public void saveMessage(ChatMessage message) {

        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValue("customerId", message.getCustomer().getCustomerId());

        if (Constant.LINE.equalsIgnoreCase(message.getCustomer().getChannel()) && Constant.IMAGE.equalsIgnoreCase(message.getType())) {
            sqlParams.addValue("message", message.getContent());
        }else{
            sqlParams.addValue("message", message.getMessage().getBytes());
        }


        sqlParams.addValue("type", message.getType());
        sqlParams.addValue("status", message.getStatus());

        log.debug("saveMessage :: Query = {}, Params = {}", NamedQuery.SAVE_MESSAGE, sqlParams.getValues().toString());
        namedParameterJdbcTemplate.update(NamedQuery.SAVE_MESSAGE, sqlParams);

    }

    public void updateCustomer(Customer customer) {

        MapSqlParameterSource sqlParams = new MapSqlParameterSource();
        sqlParams.addValue("customerId", customer.getCustomerId());
        sqlParams.addValue("socialName", customer.getSocialName());
        sqlParams.addValue("imageUrl", customer.getImageUrl());

        log.debug("updateCustomer :: Query = {}, Params = {}", NamedQuery.UPDATE_CUSTOMER, sqlParams.getValues().toString());
        namedParameterJdbcTemplate.update(NamedQuery.UPDATE_CUSTOMER, sqlParams);

    }
}
