package com.example.Test.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;


    public Collection<Employee> getEmployees(){
        String sql = "SELECT * FROM TBL_EMPLOYEES where is_delete = 'N'";
        return  this.namedParameterJdbcTemplate.query(sql, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException{
                return Employee.builder()
                        .id(resultSet.getLong("ID"))
                        .firstName(resultSet.getString("FIRST_NAME"))
                        .lastName(resultSet.getString("LAST_NAME"))
                        .email(resultSet.getString("EMAIL"))
                        .isDelete(resultSet.getString("IS_DELETE"))
                        .build();
            }
        });

    }

    public Optional<Employee> getEmployeeById(long id) {

        try {

            String sql = "SELECT * FROM TBL_EMPLOYEES WHERE ID = :id " + "and is_delete = 'N'";
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", id);

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new RowMapper<Optional<Employee>>() {
                @Override
                public Optional<Employee> mapRow(ResultSet resultSet, int i) throws SQLException {
                    return Optional.of(Employee.builder()
                            .id(resultSet.getLong("ID"))
                            .firstName(resultSet.getString("FIRST_NAME"))
                            .lastName(resultSet.getString("LAST_NAME"))
                            .email(resultSet.getString("EMAIL"))
                            .isDelete(resultSet.getString("IS_DELETE"))
                            .build());
                }
            });
        } catch (
                EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    public int insertEmployee(EmployeeReq req) {
//
//        String sql = "insert into TBL_EMPLOYEES(first_name, last_name, email) values(?,?,?)";
//        return this.jdbcTemplate.update(sql,new Object[]{req.getFirstName(),req.getLastName(),req.getEmail()});
//
//    }
//
//    public void deleteEmployee(Long id){
//
//        String sql = "delete from TBL_EMPLOYEES WHERE ID = ?";
//        this.jdbcTemplate.update(sql,new Object[]{id});
//
//    }
//
//    public void updateEmployee(EmployeeReqUpdate reqUpdate) {
//        String sql = "update TBL_EMPLOYEES set email = ? where ID = ?";
//        jdbcTemplate.update(sql,new Object[]{reqUpdate.getEmail(),reqUpdate.getId()});
//    }

    public int saveEmployee(Employee employee) {
        String sql = "INSERT INTO TBL_EMPLOYEES (first_name, last_name, email) VALUES\n" +
                "  (:firstName, :lastName, :email);";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("firstName", employee.getFirstName());
        parameters.addValue("lastName", employee.getLastName());
        parameters.addValue("email", employee.getEmail());

        return this.namedParameterJdbcTemplate.update(sql, parameters);
    }

    public int updateEmployee(Employee employee) {
        String sql = "UPDATE TBL_EMPLOYEES SET first_name = :firstName, last_name = :lastName, email = :email\n" +
                "  WHERE ID = :id;";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", employee.getId());
        parameters.addValue("firstName", employee.getFirstName());
        parameters.addValue("lastName", employee.getLastName());
        parameters.addValue("email", employee.getEmail());

        return this.namedParameterJdbcTemplate.update(sql, parameters);
    }

    public int patchEmployee(Employee employee) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TBL_EMPLOYEES SET ID = :id");

        if (StringUtils.hasText(employee.getFirstName())) {
            sql.append(", first_name = :firstName");
            parameters.addValue("firstName", employee.getFirstName());
        }
        if (StringUtils.hasText(employee.getLastName())) {
            sql.append(", last_name = :lastName");
            parameters.addValue("lastName", employee.getLastName());
        }
        if (StringUtils.hasText(employee.getEmail())) {
            sql.append(", email = :email");
            parameters.addValue("email", employee.getEmail());
        }

        sql.append(" WHERE ID = :id;");
        parameters.addValue("id", employee.getId());

        return this.namedParameterJdbcTemplate.update(sql.toString(), parameters);
    }

    public int deleteEmployee(Long id) {

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE TBL_EMPLOYEES SET is_delete = :isDelete \r\n");
        sql.append("where id = :id \r\n");


        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("isDelete", 'Y');
        parameters.addValue("id", id);



        return this.namedParameterJdbcTemplate.update(sql.toString(), parameters);
    }
}
