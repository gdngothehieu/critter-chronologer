package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        Customer customer = userService.saveCustomer(convertDTOtoCustomer(customerDTO));
        CustomerDTO theCustomerDTO = convertCustomerToDTO(customer);
        return theCustomerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> allCustomersDTO = new ArrayList<>();
        List<Customer> allCustomers = userService.getAllCustomers();
        for(Customer c: allCustomers){
            allCustomersDTO.add(convertCustomerToDTO(c));
        }
        return allCustomersDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = userService.getOwnerByPet(petId);
        return convertCustomerToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDTOtoEmployee(employeeDTO);
        employee = userService.saveEmployee(employee);
        return convertEmployeetoDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeetoDTO(userService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        return convertEmployeetoDTO(userService.setAvailability(daysAvailable,employeeId));
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employeesDTO = new ArrayList<>();
        List<Employee> employees = userService.findEmployeesForService(employeeDTO);
        for(Employee e: employees){
            employeesDTO.add(convertEmployeetoDTO(e));
        }
        return employeesDTO;
    }

    private CustomerDTO convertCustomerToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setId(customer.getId());
        if(customer.getPets().size()!=0){
            customerDTO.setPetIds(getPetIds(customer.getPets()));
        }/*else{
            customerDTO.setPetIds(new ArrayList<Long>());
        }*/
        return customerDTO;
    }



    private Customer convertDTOtoCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        //if(customerDTO.getPetIds()!=null){
        //    customer.setPets(getPets(customerDTO.getPetIds()));
        //}
        return customer;
    }


    private Employee convertDTOtoEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        return employee;
    }

    private EmployeeDTO convertEmployeetoDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        employeeDTO.setSkills(employee.getSkills());
        return employeeDTO;
    }

    private List<Long> getPetIds(List<Pet> pets) {
        List<Long> list = new ArrayList<>();
        if(pets != null){
            for(Pet p : pets){
                list.add(p.getId());
                System.out.println("Inside UserController.getPetIds()");
                System.out.println("p:="+p+", p.getId():="+p.getId());
            }
        }

        return list;
    }

    private List<Pet> getPets(List<Long> petIds) {
        List<Pet> pets = new ArrayList<>();
        if(petIds!=null){
            for(Long petId : petIds){
                pets.add(userService.getPetById(petId));
            }
        }
        System.out.println("************pets*******="+pets);
        return pets;
    }

}
