package com.sungyeh.domain;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ extends com.sungyeh.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Department, String> no;
	public static volatile ListAttribute<Department, Person> persons;
	public static volatile SingularAttribute<Department, String> name;

	public static final String NO = "no";
	public static final String PERSONS = "persons";
	public static final String NAME = "name";

}

