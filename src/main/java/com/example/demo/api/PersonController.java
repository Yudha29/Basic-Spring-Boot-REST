package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@Valid @RequestBody Person person) {
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPerson() {
        return personService.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public Optional<Person> getPerson(@PathVariable UUID id) { return personService.getPerson(id); }

    /**
     * Controller to update person data
     * @param id path variable
     * @param person request body data
     */
    @PutMapping(path = "{id}")
    public void updatePerson(@PathVariable UUID id, @Valid @RequestBody Person person) {
        personService.updatePerson(id, person);
    }

    @DeleteMapping(path = "{id}")
    public void deletePerson(@PathVariable UUID id) {
        personService.deletePerson(id);
    }
}
