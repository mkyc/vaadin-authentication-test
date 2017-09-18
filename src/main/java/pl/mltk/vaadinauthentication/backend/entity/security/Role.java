package pl.mltk.vaadinauthentication.backend.entity.security;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@SuppressWarnings("serial")
@Entity
public class Role implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
 
    @ManyToMany(fetch=FetchType.EAGER)
    private Collection<Privilege> privileges;  	
    
	public Role() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}
}
