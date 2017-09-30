package hq.misc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.SessionFactory;

import com.google.gson.JsonObject;
@Entity
@Table(name="people")
public class basicPerson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int ID;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	
	public basicPerson(){
		
	}
	
	public basicPerson(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static JsonObject getAllFromDB() {
		return null;
	}
	
}
