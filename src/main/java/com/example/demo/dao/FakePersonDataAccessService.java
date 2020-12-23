package com.example.demo.dao;

import com.example.demo.model.Person;
import jdk.jfr.Description;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {
    private final static List<Person> DB = new ArrayList<Person>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> getAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> getPerson(UUID id) {
        return DB.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    /**
     * Method to update a Person in list
     * @param id id of person to update
     * @param person the new person data
     * @return an integer 1 = success, 0 = fail
     */
    @Override
    public int updatePerson(UUID id, Person person) {
        //get person
        return getPerson(id)
                //map the person
                .map(e -> {
                    //get index of person to update
                    int indexOfPersonToUpdate = DB.indexOf(e);

                    //person does not found then return 0
                    if (indexOfPersonToUpdate < 0)
                        return 0;

                    //person found then update the person data
                    DB.set(indexOfPersonToUpdate, new Person(id, person.getName()));

                    return 1; //means update success
                }).orElse(0); //otherwise return 0
    }

    /**
     * Method to delete a Person
     * @param id id of person to delete
     * @return an integer 1 = success, 0 = fail
     */
    @Override
    public int deletePerson(UUID id) {
        //find person by id
        Optional<Person> person = getPerson(id);

        //if person does not found then return 0
        if (person.isEmpty())
            return 0;

        //person found, remove the person
        DB.remove(person.get());

        return 1; //means delete success
    }
}
