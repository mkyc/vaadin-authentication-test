package pl.mltk.vaadinauthentication.front.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.mltk.vaadinauthentication.backend.service.MyUserDetailsService;

import java.util.Collection;

@Service
public class SecurityUtils {
	
	@Autowired
	MyUserDetailsService myUserDetailsService;

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && authentication.isAuthenticated();
	}
	public boolean hasRole(String role) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// return authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority(role));
		return authentication != null  &&
				isRolePresent(myUserDetailsService.loadUserByUsername(authentication.getName()).getAuthorities(), role);
	}

	  private boolean isRolePresent(Collection<? extends GrantedAuthority> collection, String role) {
	    for (GrantedAuthority grantedAuthority : collection) {
	      if(grantedAuthority.getAuthority().equals(role)) {
	    	  return true;
	      }
	    }
	      return false;
	  }
}