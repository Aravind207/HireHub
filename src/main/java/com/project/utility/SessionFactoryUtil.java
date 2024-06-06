package com.project.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {
	
	public static SessionFactory buildSessionFactory()
	{
		Configuration cfg = new Configuration();
		org.hibernate.SessionFactory sf = cfg.configure().buildSessionFactory();
		
		
		return sf;
		
	}

}
