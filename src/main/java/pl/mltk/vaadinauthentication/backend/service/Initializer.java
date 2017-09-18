package pl.mltk.vaadinauthentication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.mltk.vaadinauthentication.backend.entity.security.Privilege;
import pl.mltk.vaadinauthentication.backend.entity.security.Role;
import pl.mltk.vaadinauthentication.backend.entity.security.User;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class Initializer implements ApplicationRunner {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("starting initializer");
        Privilege privilege1 = new Privilege();
        privilege1.setName("OP_USER_ADD");
        Privilege privilege2 = new Privilege();
        privilege2.setName("OP_USER_DELETE");
        Collection<Privilege> privileges = new ArrayList<>();
        privileges.add(privilege1);
        privileges.add(privilege2);



        Role role = new Role();
        role.setName("ROLE_ADMIN");
        role.setPrivileges(privileges);
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(0, passwordEncoder.encode("pass"), true, true, true, true, true, "m@m.m", roles);
        myUserDetailsService.save(user);
        System.out.println("finished initializer");
    }
}
