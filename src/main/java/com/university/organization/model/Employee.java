package com.university.organization.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

    @Column(name = "org_group")
    private String group;

    @Column(name = "position")
    private String position;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salary")
    private float salary;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "is_male")
    private Boolean isMale = false;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss_id")
    private Employee boss;

	public Employee() {

	}

	public Employee(
            String name,
            String org_group,
            String position,
            String phone,
            float salary,
            Date birthdate,
            Date hireDate,
            Boolean isMale,
            Employee boss
    ) {
		this.name = name;
		this.group = org_group;
		this.position = position;
		this.phone = phone;
		this.salary = salary;
		this.birthdate = birthdate;
		this.hireDate = hireDate;
		this.isMale = isMale;
		this.boss = boss;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    public String getGroup() { return group; }
    public void setGroup(String value) { group = value; }
    public String getPosition() { return position; }
    public void setPosition(String value) { position = value; }
    public String getPhone() { return phone; }
    public void setPhone(String value) { phone = value; }
    public float getSalary() { return salary; }
    public void setSalary(float value) { salary = value; }
    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date value) { birthdate = value; }
    public Date getHireDate() { return hireDate; }
    public void setHireDate(Date value) { hireDate = value; }

    public Employee getBoss() { return boss; }
    public void setBoss(Employee value) { boss = value; }

    public long getBossId() { return boss == null ? 0 : boss.getId(); }
    public void setBossId(long id) {
	    Employee b = new Employee();
	    b.id = id;
	    this.boss = b;
    }

    public String getBossName() {
	    return boss != null ? boss.getName() : "";
    }

    public String getSex() {
	    return isMale ? "Male" : "Female";
    }

    public boolean getIsMale() { return isMale; }
    public void setIsMale(boolean value) { isMale = value; }

	@Override
	public String toString() {
		return ""; // ""Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
	}

}
