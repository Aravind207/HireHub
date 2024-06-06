package com.project.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.project.pojo.Job;
import com.project.pojo.User;
import com.project.utility.SessionFactoryUtil;


@Component
public class UserDAO {
	
	private SessionFactory sf = SessionFactoryUtil.buildSessionFactory();
	
	public void save(User user) {
	    Session session = sf.openSession();
	    Transaction transaction = session.beginTransaction();
	    try {
	        if (user.getId() == null) {  // Assuming `getId()` checks if the entity is new
	            session.persist(user);
	        } else {
	            user = (User) session.merge(user);  // Correct approach for updating existing entities
	        }
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        throw e;  // Better error handling: throw the exception
	    } finally {
	        session.close();
	    }
	}

	 
	 	
	 public User getByEmail(String email) {
		    try (Session session = sf.openSession()) {
		        String queryString = "FROM User u where u.email=:email";
		        Query<User> query = session.createQuery(queryString, User.class);
		        query.setParameter("email", email);
		       List<User> users = query.list();
	            return users.size() == 1 ? users.get(0) : null;
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return null;
		}

	 	
	 	public User getById(long id) 
		{
	       Session session = sf.openSession();
	       User user = session.get(User.class, id);
	       return user;
	     
	    }
	 	
	 	public List<User> findAll() {
	        Session session = sf.openSession();
	        try {
	            String queryString = "FROM User"; // HQL to fetch all User entities
	            Query<User> query = session.createQuery(queryString, User.class);
	            return query.list();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            session.close(); // Ensure to close the session
	        }
	        return null; // Return null or an empty list as per your error handling policy
	 	}
	 	
	 	
	 	public User findByEmailAndPassword(String email, String password) {
	 	    try (Session session = sf.openSession()) {
	 	        Query<User> query = session.createQuery("FROM User u WHERE u.email = :email AND u.password = :password", User.class);
	 	        query.setParameter("email", email);
	 	        query.setParameter("password", password);
	 	        return query.uniqueResult();  // Directly returns the user or null
	 	    } catch (Exception e) {
	 	        e.printStackTrace();
	 	    }
	 	    return null;
	 	}


	 	public void deleteById(Long id) {
	 	    Session session = sf.openSession();
	 	    Transaction transaction = null;
	 	    try {
	 	        transaction = session.beginTransaction();
	 	        User user = session.get(User.class, id);
	 	        if (user != null) {
	 	            session.remove(user);  // Using remove instead of delete
	 	        }
	 	        transaction.commit();
	 	    } catch (Exception e) {
	 	        if (transaction != null) transaction.rollback();
	 	        e.printStackTrace();
	 	    } finally {
	 	        session.close();
	 	    }
	 	}
	 	
	 	public User findByEmail(String email) {
	        try (Session session = sf.openSession()) {
	            Query<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
	            query.setParameter("email", email);
	            return query.uniqueResult();  // Directly returns the user or null
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	 	
	 	



	 
}
