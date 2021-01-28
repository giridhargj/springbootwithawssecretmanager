package com.example.springbootwithawssecretmanager;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @RequestMapping("/employees")
    public List<Employee> getAllEmp() {
        return empService.getAllEmployees();
    }

    @RequestMapping("/employees/{id}")
    public Optional<Employee> getEmployee(@PathVariable String id)
    {
        return empService.getEmployee(id);
    }

    @RequestMapping(method = RequestMethod.POST, value="/employee" )
    public void addEmplyee(@RequestBody Employee employee)
    {
        empService.addEmplyee(employee);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/employee/{id}")
    public void updateEmployee(@RequestBody Employee employee, @PathVariable String id)
    {
        empService.update(employee, id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/employee/{id}")
    public void deleteEmplyee(@PathVariable String id)
    {
        empService.deleteEmplyee(id);
    }
}
