package mockitopoc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mockitopoc.dto.Person;
import mockitopoc.repo.PersonRepository;
import mockitopoc.service.PersonService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(value = MethodName.class)
class PersonServiceTest {

	@Mock
	PersonRepository personRepository;
	
	@InjectMocks
	PersonService personService;

//	@BeforeEach
//	void setup() {
//		personRepository = mock(PersonRepository.class);
//		personService = new PersonService(personRepository);
//	}

	@Test
	void a1_testLogin() {
		
		// Arrange
		Person person = new Person();
		person.setEmail("mahesh@test.com");
		person.setPassword("mahesh");
		person.setName("mahesh");
		when(personRepository.findByEmailAndPassword(eq("mahesh@test.com"), anyString())).thenReturn(Optional.of(person));
	
		//Act
		String token = personService.login("mahesh@test.com", "mahesh");
		
		//Assert
		assertThat(token).isNotNull();
	}
	
	@Test
	void a2_testLogin() {
		
		// Arrange
		when(personRepository.findByEmailAndPassword(anyString(),anyString())).thenReturn(Optional.empty());
	
		//Act
		String token = personService.login("mahesh@test.com", "mahesh");
		
		//Assert
		assertThat(token).isNull();
	}
	
	@Test
	void a3_createPerson() {
		Person person = new Person();
		person.setEmail("mahesh@test.com");
		person.setName("Mahesh");
		person.setPassword("password");
		
//		when(personRepository.create(any(Person.class))).thenReturn(person);
		
		when(personRepository.create(any(Person.class))).thenAnswer(ans -> ans.getArguments()[0]);
		
		personService.create("mahesh", "mahesh@test.com", "password");
		
		ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
		verify(personRepository).create(argumentCaptor.capture());
		
		Person person2 =  argumentCaptor.getValue();
		
		assertThat(person2).isNotNull();
		assertThat(person2.getEmail()).isEqualTo("mahesh@test.com");
		assertThat(person2.getName()).isEqualTo("mahesh");
		assertThat(person2.getPassword()).isEqualTo("password");
	
		
	}
	
	@Test
	void a4_updatePerson() {
		
		Person person = new Person(1L,"mahesh","mahesh@test.com","password");
		doNothing().when(personRepository).update(person);
		
		personService.update(person);
		
		verify(personRepository,times(1)).update(any());
		verify(personRepository,atLeastOnce()).update(any());
//		verify(personRepository,atLeast(1)).update(any());
//		verify(personRepository,atMost(1)).update(any());
	}

}
