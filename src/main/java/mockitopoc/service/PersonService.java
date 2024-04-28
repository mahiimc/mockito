package mockitopoc.service;

import java.util.Optional;

import mockitopoc.dto.Person;
import mockitopoc.repo.PersonRepository;

public class PersonService {

	private final PersonRepository repo;

	public PersonService(PersonRepository repo) {
		this.repo = repo;
	}

	public String login(String email, String password) {
		Optional<Person> optionalPerson = repo.findByEmailAndPassword(email, password);
		if (optionalPerson.isEmpty()) {
			return null;
		}
		return "abcd1234";
	}

	public Person create(String name, String email, String password) {
		Person person = new Person();
		person.setName(name);
		person.setEmail(email.toLowerCase());
		person.setPassword(password);

		Optional<Person> byEmail = repo.findByEmail(person.getEmail());
		if (byEmail.isPresent()) {
			throw new RuntimeException("Person with email '" + person.getEmail() + "' already exists");
		}
		return repo.create(person);
	}

	public Optional<Person> findByEmail(String email) {
		return repo.findByEmail(email);
	}

	public void update(Person person) {
		repo.update(person);
	}

}
