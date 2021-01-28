package com.example.springbootwithawssecretmanager;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    private String id;
    private String name;
    

    public Employee(String id, String name)
    {
        this.id  =  id;
        this.name  =  name;
    }

    public Employee()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
