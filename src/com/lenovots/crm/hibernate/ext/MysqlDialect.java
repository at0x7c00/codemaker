package com.lenovots.crm.hibernate.ext;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class MysqlDialect extends MySQLDialect {

	public MysqlDialect() {
		super();
		registerHibernateType(Types.DECIMAL, Hibernate.BIG_DECIMAL.getName());
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}
}
