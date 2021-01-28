package com.example.Test.Customer;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Customer> inquiryCustomer(CustomerReqModel customerRepository) {

        try {

            String sql = "SELECT * FROM CUSTOMER \n" +
                    "WHERE user_name = :userName  \n" +
                    "   and password = :password \n" +
                    "   and lock_customer = 'N' ;";

            MapSqlParameterSource parameterSource = new MapSqlParameterSource();

            parameterSource.addValue("userName", customerRepository.getUserName());
            parameterSource.addValue("password", customerRepository.getPassword());

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new RowMapper<Optional<Customer>>() {
                @Override
                public Optional<Customer> mapRow(ResultSet rs, int i) throws SQLException {

                    return Optional.of(Customer.builder()
                            .id(rs.getLong("ID"))
                            .userName(rs.getString("USER_NAME"))
                            .email(rs.getString("EMAIL"))
                            .password(rs.getString("PASSWORD"))
                            .lockCustomer(rs.getString("LOCK_CUSTOMER"))
                            .build());
                }
            });
        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();
        }
    }

    public int UpdateCountCustomer(Long id,int count){
        StringBuilder sql = new StringBuilder();

        sql.append("update CUSTOMER  set count_invalid = :count \r\n");
        sql.append("where id = :id \r\n");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("count", count);
        parameterSource.addValue("id", id);

        return this.namedParameterJdbcTemplate.update(sql.toString(),parameterSource);


    }

    public int lockCustomer(Long id){
        StringBuilder sql = new StringBuilder();

        sql.append("update CUSTOMER  set lock_customer= :lockCustomer \r\n");
        sql.append("where id = :id \r\n");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("lockCustomer", 'Y');
        parameterSource.addValue("id", id);

        return this.namedParameterJdbcTemplate.update(sql.toString(),parameterSource);


    }

    public Optional<Customer> reqCustomerByUserName(CustomerReqModel customerReqModel) {
        try {

            String sql = "SELECT * FROM CUSTOMER \n" +
                    "WHERE user_name = :userName \n" +
                    "   and lock_customer = 'N' ;";

            MapSqlParameterSource parameterSource = new MapSqlParameterSource();

            parameterSource.addValue("userName", customerReqModel.getUserName());

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new RowMapper<Optional<Customer>>() {
                @Override
                public Optional<Customer> mapRow(ResultSet resultSet, int i) throws SQLException {
                    return Optional.of(Customer.builder()
                            .id(resultSet.getLong("ID"))
                            .userName(resultSet.getString("USER_NAME"))
                            .email(resultSet.getString("EMAIL"))
                            .password(resultSet.getString("PASSWORD"))
                            .lockCustomer(resultSet.getString("LOCK_CUSTOMER"))
                            .countInvalid(resultSet.getInt("COUNT_INVALID"))
                            .build());
                }
            });


        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();
        }
    }
}
