package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method to add new person
     * @param id new person's id
     * @param person new person's data, included name
     * @return an integer, 1 = success, 0 = fail
     */
    @Override
    public int insertPerson(UUID id, Person person) {
        //sql query
        final  String sql = "INSERT INTO person(id, name) VALUES (?, ?)";

        //execute query with jdbc
        jdbcTemplate.update(sql, id, person.getName());

        return 1; //return success status
    }

    /**
     * Method to get all people in database
     * @return list of people
     */
    @Override
    public List<Person> getAllPeople() {
        //sql query
        final  String sql = "SELECT * FROM person";

        //execute query with jdbc
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id")); //fetch id and parse to uuid
            String name = resultSet.getString("name"); //fetch person name
            return new Person(id, name); //construct and return person object
        });
    }

    /**
     * Method to get a person's data
     * @param id person's id
     * @return person's data
     */
    @Override
    public Optional<Person> getPerson(UUID id) {
        //sql query
        final  String sql = "SELECT * FROM person WHERE id = ?";

        //execute query with jdbc
        Person person = jdbcTemplate.queryForObject(
                sql,
                //inject user id to sql query
                new Object[] {id},
                (resultSet, i) -> {
                    UUID personId = UUID.fromString(resultSet.getString("id")); //fetch id and parse to uuid
                    String name = resultSet.getString("name"); //fetch person name
                    return new Person(personId, name); //construct and return person object
                });

        //return the nullable person object
        return Optional.ofNullable(person);
    }

    /**
     * Method to add new person
     * @param id person to update id
     * @param person new person's data, included name
     * @return an integer, 1 = success, 0 = fail
     */
    @Override
    public int updatePerson(UUID id, Person person) {
        String sql = "UPDATE person SET name = ? WHERE id = ?"; //sql query

        //update the selected person
        jdbcTemplate.update(sql, person.getName(), id);

        return 1; //return success status
    }

    /**
     * Method to delete person
     * @param id person to delete id
     * @return an integer, 1 = success, 0 = fail
     */
    @Override
    public int deletePerson(UUID id) {
        String sql = "DELETE FROM person WHERE id = ?"; //sql query

        //delete the selected person
        jdbcTemplate.update(sql, id);

        return 1; //return success status
    }
}
