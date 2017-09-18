package pl.mltk.vaadinauthentication.backend.repository.security;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.mltk.vaadinauthentication.backend.entity.security.Privilege;

public interface PrivilegeRepo extends JpaRepository<Privilege, Long> {

	Privilege save(Privilege privilege);
}
