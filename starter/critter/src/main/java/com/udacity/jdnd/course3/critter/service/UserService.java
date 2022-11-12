package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserService {

    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private PetRepository petRepository;
    private ScheduleRepository scheduleRepository;

    @Autowired
    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository, ScheduleRepository scheduleRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Customer saveCustomer(Customer customer) {
        Customer  savedCustomer= customerRepository.save(customer);
        /*if(savedCustomer.getPets()!=null){
            for(Pet p : savedCustomer.getPets()){
                p.setOwner(savedCustomer);
            }
        }*/
        System.out.println("Inside UserService.saveCustomer(Customer customer)");
        System.out.println("savedCustomer:="+savedCustomer);
        return savedCustomer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        /*List<Pet> pets = petRepository.findAll();
        for(int i=0;i<customers.size();i++){
            Customer c = customers.get(i);
            for(int j=0;j<pets.size();j++){
                Pet p = pets.get(i);
                if(p.getOwner().equals(c)){
                    //c.getPets().add(p);
                }

            }
        }*/
        return customers;
    }

    public Customer getCustomerById(Long customerId) {
        System.out.println("Inside public  Customer getCustomerById(Long customerId)");
        System.out.println("customerRepository.findById(customerId)=" + customerRepository.findById(customerId));
        System.out.println("customerRepository.findAll()=" + customerRepository.findAll());
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            throw new CustomerNotFoundException("Customer with id=" + customerId + " not found");
        }
    }


    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new EmployeeNotFoundException("Employee with id=" + employeeId + " not found.");
        }
    }

    public Pet savePet(Pet pet) {
        /*Customer owner = pet.getOwner();
        System.out.println("owner is: " + owner);
        System.out.println("Inside UserService.savePet(Pet pet)");
        if (owner != null) {
            System.out.println("Owner returned by pet.getOwner() is not null");
            {
                owner.getPets().add(pet);
            }
            Customer customer = customerRepository.save(owner);

            System.out.println("customer:-"+customer);
        } else {
            System.out.println("Owner returned by pet.getOwner() is null");
            System.out.println("Owner is not associated with pet:=" + pet);
        }
        return petRepository.save(pet);*/
        Customer owner = pet.getOwner();
        System.out.println("owner is: " + owner);
        System.out.println("Inside UserService.savePet(Pet pet)");
        Pet savedPet = petRepository.save(pet);
        if (owner != null) {
            System.out.println("Owner returned by pet.getOwner() is not null");
            {
                owner.getPets().add(savedPet);
            }
            Customer customer = customerRepository.save(owner);
            //System.out.println("customer:-"+customer);
        }
        return savedPet;
    }

    public Pet getPetById(long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if (optionalPet.isPresent()) {
            System.out.println("optionalPet.get():=" + optionalPet.get());
            return optionalPet.get();
        } else {
            System.out.println("optionalPet.get():= throwing PetNotFoundException");
            throw new PetNotFoundException("Pet with petId=" + petId + " not found");
        }
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Long getOwnerByPetId(long petId) {
        Pet pet = getPetById(petId);
        return pet.getOwner().getId();
    }

    public Customer getOwnerByPet(long petId) {
        Customer customer = petRepository.getOne(petId).getOwner();
        if (customer.getPets() == null) {
            customer.setPets(new ArrayList<Pet>());
            List<Pet> petList = petRepository.findAll();
            for (int i = 0; i < petList.size(); i++) {
                if (petList.get(i).getOwner() == customer) {
                    customer.getPets().add(petList.get(i));
                }
            }
        }
        return customer;
    }

    public Employee setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Optional<Employee> optionalEmployeee = employeeRepository.findById(employeeId);
        if (optionalEmployeee.isPresent()) {
            Employee employee =  optionalEmployeee.get();
            employee.setDaysAvailable(daysAvailable);
            return employeeRepository.save(employee);
        } else {
            throw new EmployeeNotFoundException("Employee with id=" + employeeId + " not found.");
        }
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        System.out.println("Inside userService.findEmployeesForService");

        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        List<Employee> allEmployees = employeeRepository.findAll();

        System.out.println("day:="+day);
        System.out.println("skills:="+skills);

        List<Employee> returnEmployees = new ArrayList<>();
        for(Employee e : allEmployees){
            System.out.println("e.getDaysAvailable():="+e.getDaysAvailable());
            System.out.println("e.getSkills():="+e.getSkills());
            if(e.getDaysAvailable().contains(day)){

                System.out.println(e + " is available on " + day);
                if(e.getSkills().containsAll(skills)){
                    returnEmployees.add(e);
                    System.out.println(e+" has skills"+skills);
                }
            }
        }
        System.out.println("returnEmployees:="+returnEmployees);
        return returnEmployees;
    }

    public Schedule createSchedule(Schedule schedule) {
            return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        System.out.println("Inside UserService.getScheduleForPet");
        List<Schedule> schedules= scheduleRepository.findAll();
        System.out.println("schedules:="+schedules);
        List<Schedule> returnSchedule = new ArrayList<>();
        for(Schedule s: schedules){
            if(s.getPets().contains(petRepository.getOne(petId))){
                returnSchedule.add(s);
            }
        }
        return returnSchedule;
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {

        List<Schedule> schedules= scheduleRepository.findAll();

        List<Schedule> returnSchedule = new ArrayList<>();
        for(Schedule s: schedules){
            if(s.getEmployees().contains(employeeRepository.getOne(employeeId))){
                returnSchedule.add(s);
            }
        }
        return returnSchedule;
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        List<Schedule> schedules= scheduleRepository.findAll();
        List<Schedule> returnSchedule = new ArrayList<>();
        for(Schedule s: schedules){
            List<Pet> petList = s.getPets();
            for(Pet p : petList){
                if(p.getOwner().getId() == customerId){
                    returnSchedule.add(s);
                }
            }
        }
        return returnSchedule;
    }
}
