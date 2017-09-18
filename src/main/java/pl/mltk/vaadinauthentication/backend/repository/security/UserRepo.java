package pl.mltk.vaadinauthentication.backend.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mltk.vaadinauthentication.backend.entity.security.User;


public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmailIgnoreCaseContaining(String nameFilter);

	User save(User user);
}
