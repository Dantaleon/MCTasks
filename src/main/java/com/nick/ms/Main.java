package com.nick.ms;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;


@SpringBootApplication
@EntityScan(basePackages = {"com.nick.ms"})
@ComponentScan(basePackages = {"com.nick.ms"})
@EnableAutoConfiguration(exclude = { //
        DataSourceAutoConfiguration.class, //
        //DataSourceTransactionManager.class, //
        HibernateJpaAutoConfiguration.class })
public class Main {
	
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        
        
    }
    
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("Application started successfully!");
        };
    }
    
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
    	
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	
    	dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
    	dataSource.setUrl(env.getProperty("spring.datasource.url"));
    	dataSource.setUsername(env.getProperty("spring.datasource.username"));
    	dataSource.setPassword(env.getProperty("spring.datasource.password"));
    	
    	System.out.println("## getDataSource: " + dataSource);
    	
    	return dataSource;
    }
    
    @Autowired
    @Bean(name = "entityManagerFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
    	
    	Properties properties = new Properties();
    	
    	properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
    	properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
    	properties.put("hibernate.current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        
    	// Fix Postgres JPA Error:
        // Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
        // properties.put("hibernate.temp.use_jdbc_metadata_defaults",false);
    	
    	LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean(); 
    	
        factoryBean.setPackagesToScan(new String[] { "com.nick.ms.model" });
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
        
        SessionFactory sessionFactory = factoryBean.getObject();
        
        System.out.println("## getSessionFactory: " + sessionFactory);
        
        return sessionFactory;
        
    }
    
    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
    	
    	HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
    	
    	return transactionManager;
    }
    
 
}