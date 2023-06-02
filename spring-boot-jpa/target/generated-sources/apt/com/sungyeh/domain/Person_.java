package com.sungyeh.domain;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ extends com.sungyeh.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Person, String> password;
	public static volatile ListAttribute<Person, Role> roles;
	public static volatile SingularAttribute<Person, Department> department;
	public static volatile SingularAttribute<Person, String> username;

	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	public static final String DEPARTMENT = "department";
	public static final String USERNAME = "username";

}

