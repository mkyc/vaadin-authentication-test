package pl.mltk.vaadinauthentication.backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mltk.vaadinauthentication.backend.entity.security.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Role findByNameIgnoreCaseContaining(String nameFilter);

	Role save(Role role);
}
