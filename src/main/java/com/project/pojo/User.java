package com.project.pojo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.*;
import java.util.Set;


@Entity
@Table(name = "UserTable")
public class User {


		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		
		private String firstname;
		private String lastname;
		private String email;
		private String password;
		
		
		public User() {
			super();
		}
		
		@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER) // MERGE typically includes updates
		@JoinTable(
		    name = "user_job",
		    joinColumns = @JoinColumn(name = "user_id"),
		    inverseJoinColumns = @JoinColumn(name = "job_id")
		)
		
	    private Set<Job> jobs;

	    // Getters and setters
	    public Set<Job> getJobs() {
	        return jobs;
	    }

	    public void setJobs(Set<Job> jobs) {
	        this.jobs = jobs;
	    }
		
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}


		@Override
		public String toString() {
			return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
					+ ", password=" + password + "]";
		}
		
		
}
