package pl.mltk.vaadinauthentication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mltk.vaadinauthentication.backend.entity.security.Privilege;
import pl.mltk.vaadinauthentication.backend.entity.security.Role;
import pl.mltk.vaadinauthentication.backend.entity.security.User;
import pl.mltk.vaadinauthentication.backend.repository.security.PrivilegeRepo;
import pl.mltk.vaadinauthentication.backend.repository.security.RoleRepo;
import pl.mltk.vaadinauthentication.backend.repository.security.UserRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	PrivilegeRepo privilegeRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        User user = userRepo.findByEmailIgnoreCaseContaining(email);
		if (user == null) {
			return new org.springframework.security.core.userdetails.User(" ", " ", true, true, true, true,
					getAuthorities(Arrays.asList(roleRepo.findByNameIgnoreCaseContaining("ROLE_USER"))));
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for (Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	public User save(User user) {
		Collection<Role> newRoles = new ArrayList<>();
		for(Role r : user.getRoles()) {
			Collection<Privilege> newPrivileges = new ArrayList<>();
			for(Privilege p : r.getPrivileges()) {
				p = privilegeRepo.save(p);
				newPrivileges.add(p);
			}
			r.setPrivileges(newPrivileges);
			r = roleRepo.save(r);
			newRoles.add(r);
		}
		user.setRoles(newRoles);
		return userRepo.save(user);
	}
}
