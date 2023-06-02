package com.sungyeh.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Booking.class)
public abstract class Booking_ extends com.sungyeh.domain.AbstractEntity_ {

	public static volatile SingularAttribute<Booking, LocalDateTime> dateTime;
	public static volatile SingularAttribute<Booking, LocalDateTime> createTime;
	public static volatile SingularAttribute<Booking, Integer> people;

	public static final String DATE_TIME = "dateTime";
	public static final String CREATE_TIME = "createTime";
	public static final String PEOPLE = "people";

}

