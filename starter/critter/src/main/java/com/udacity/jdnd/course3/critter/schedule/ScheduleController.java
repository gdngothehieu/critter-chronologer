package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private UserService userService;

    public ScheduleController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule= userService.createSchedule(convertDTOtoSchedule(scheduleDTO));
        return convertScheduleToSTO(schedule);
    }

    private ScheduleDTO convertScheduleToSTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setEmployeeIds(getEmployeeIds(schedule.getEmployees()));
        scheduleDTO.setPetIds(getPetIds(schedule.getPets()));
        scheduleDTO.setId(schedule.getId());
        return scheduleDTO;
    }

    private List<Long> getPetIds(List<Pet> pets) {
        List<Long> list = new ArrayList<>();
        for(Pet p : pets){
            list.add(p.getId());
        }
        return list;
    }

    private List<Long> getEmployeeIds(List<Employee> employees) {
        List<Long> list = new ArrayList<>();
        for(Employee e: employees){
            list.add(e.getId());
        }
        return list;
    }

    private Schedule convertDTOtoSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());
        schedule.setId(scheduleDTO.getId());
        schedule.setEmployees(getEmployees(scheduleDTO.getEmployeeIds()));
        schedule.setPets(getPets(scheduleDTO.getPetIds()));
        return schedule;
    }

    private List<Pet> getPets(List<Long> petIds) {
        List<Pet> petList = new ArrayList<>();
        for(Long l : petIds){
            petList.add(userService.getPetById(l));
        }

        return petList;
    }

    private List<Employee> getEmployees(List<Long> employeeIds) {
        List<Employee> employeeList = new ArrayList<>();

        for(Long l : employeeIds){
            employeeList.add(userService.getEmployeeById(l));
        }

        return employeeList;
    };


    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> list = userService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule s : list){
            scheduleDTOS.add(convertScheduleToSTO(s));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> list= userService.getScheduleForPet(petId);
        System.out.println("list:="+list);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule s : list){
            scheduleDTOS.add(convertScheduleToSTO(s));
        }
        System.out.println("scheduleDTOS:="+scheduleDTOS);
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> list= userService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule s : list){
            scheduleDTOS.add(convertScheduleToSTO(s));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> list= userService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Schedule s : list){
            scheduleDTOS.add(convertScheduleToSTO(s));
        }
        return scheduleDTOS;
    }
}
