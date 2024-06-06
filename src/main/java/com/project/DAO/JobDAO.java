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
public class JobDAO {

    private SessionFactory sf = SessionFactoryUtil.buildSessionFactory();

    public void save(Job job) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            if (job.getJobId() == null) {  // If JobID is null, assume it's a new Job
                session.persist(job);
            } else {
                job = (Job) session.merge(job);  // For updating existing Job
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public Job getByJobId(Long jobId) {
        try (Session session = sf.openSession()) {
            return session.get(Job.class, jobId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Job> findAll() {
        Session session = sf.openSession();
        try {
            String queryString = "FROM Job";  // HQL to fetch all Job entities
            Query<Job> query = session.createQuery(queryString, Job.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void deleteById(Long jobId) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Job job = session.get(Job.class, jobId);
            if (job != null) {
                // Remove job from all users' set of jobs
                for (User user : job.getUsers()) {
                    user.getJobs().remove(job);
                    session.saveOrUpdate(user); // Ensure that changes are persisted
                }
                session.remove(job); // Now safe to remove the job
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    
    
    public boolean applyToJob(Long userId, Long jobId) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            User user = session.get(User.class, userId);
            Job job = session.get(Job.class, jobId);
            if (user != null && job != null) {
                if (!user.getJobs().contains(job)) {
                    user.getJobs().add(job); // Add the job to the user's set of jobs
                    session.save(user); // This might not be necessary depending on your cascade settings
                    tx.commit();
                    return true; // Application successful
                } else {
                    return false; // User already applied to this job
                }
            } else {
                return false; // Either user or job is not found
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false; // Exception occurred
        } finally {
            session.close();
        }
    }


}
