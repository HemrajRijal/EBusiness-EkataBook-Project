package com.mvc.util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;;

 
public class DBConnector {
 
    private static  SessionFactory sessionFactory;
 
    static {
        try {
        	
                
 /****OR You can Use Below code as well****/
                
        	String hibernatePropsFilePath = "C:/Users/Hemraj/workspace/ekataBookStore/src/hibernate.cfg1.xml";
        	File hibernatePropsFile = new File(hibernatePropsFilePath);
        	Configuration configuration = new Configuration();
        	configuration.configure(hibernatePropsFile);
        	configuration.addResource("Person4.hbm.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build(); 
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            //throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static Session openSession() {
        return sessionFactory.openSession();
    	//return sessionFactory.getCurrentSession();
    }
}
