package com.example.springbootwithawssecretmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

    private List<Employee> employees = new ArrayList<>(Arrays.asList(new Employee("1", "Joe"), new Employee("2", "Tom")

    ));

    public List<Employee> getAllEmployees() {
        List<Employee> empList = new ArrayList<>();
        empRepo.findAll().forEach(empList::add);

        return empList;
    }

    public Optional<Employee> getEmployee(String id)
    {
        return empRepo.findById(id);
        //return employees.stream().filter(e -> e.getId().equals(id)).findFirst().get();
    }

    public void addEmplyee(Employee employee)
    {
        empRepo.save(employee);
        //employees.add(employee);
	}

    public void update(Employee employee, String id)
    {
        empRepo.save(employee);
        // for (int i = 0 ; i < employees.size(); i++)
        // {
        //     Employee emp =  employees.get(i);
        //     if(emp.getId().equals(id))
        //     {
        //         employees.set(i, employee);
        //     }
        // }
	}

    public void deleteEmplyee(String id) 
    {
        empRepo.deleteById(id);
        //employees.removeIf(e -> e.getId().equals(id));
	}

}
