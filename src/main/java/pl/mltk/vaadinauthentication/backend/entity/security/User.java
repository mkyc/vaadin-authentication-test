package pl.mltk.vaadinauthentication.backend.entity.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public @Data class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Size(min = 3, max = 100, message = "Password must be longer than 3 and less than 100 characters")
	private String password;
	private boolean enabled;
	private boolean verified;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;

	@NotNull(message = "Email is required")
	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "Must be valid email")
	private String email;

	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<Role> roles;


}
