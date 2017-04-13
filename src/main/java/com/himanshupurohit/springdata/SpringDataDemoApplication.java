package com.himanshupurohit.springdata;

import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import java.util.List;

@SpringBootApplication
public class SpringDataDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(EmployeeRepository employeeRepository,ManagerRepository managerRepository){
		return args -> {
			Manager m = managerRepository.save(new Manager("Goldstar"));
			employeeRepository.save(new Employee("Clark","Kent","SuperMan",m));
			employeeRepository.save(new Employee("Bruce","Wayne","BatMan",m));
			employeeRepository.save(new Employee("Tony","Stark","IronMan",m));
		};
	}
}

@Data
@Entity
class Manager{
	@Id @GeneratedValue Long Id;
	String name;

	@OneToMany(mappedBy = "manager")
	List<Employee> team;

	public Manager(String name) {
		this.name = name;
	}

	private Manager(){};


}
@Data
@Entity
class Employee{
	private Employee(){ /* This is BS */};

	public Employee(String firstName, String lastName, String role, Manager manager) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.manager = manager;
	}

	@Id @GeneratedValue Long Id;
	String firstName;
	String lastName;
	String role;

	@ManyToOne
	Manager manager;
}


    @RepositoryRestResource
    interface EmployeeRepository extends CrudRepository<Employee,Long>{

	List<Employee> findByLastName(@Param("l") String lastName);
	List<Employee> findByFirstName(@Param("f") String firstName);
	}

	@RepositoryRestResource
    interface ManagerRepository extends CrudRepository<Manager,Long>{

	}