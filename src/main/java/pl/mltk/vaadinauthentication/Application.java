package pl.mltk.vaadinauthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mltk.vaadinauthentication.backend.service.MyUserDetailsService;
import pl.mltk.vaadinauthentication.front.auth.VaadinSessionSecurityContextHolderStrategy;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application extends SpringBootServletInitializer {

	@Configuration
	@EnableGlobalMethodSecurity(securedEnabled = true)
	public static class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

		@Autowired
		private MyUserDetailsService myUserDetailsService;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(authenticationProvider());
		}

		private DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(myUserDetailsService);
			authProvider.setPasswordEncoder(encoder());
			return authProvider;
		}

		private PasswordEncoder encoder() {
			return new BCryptPasswordEncoder(11);
		}

		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return authenticationManager();
		}

		static {
			// Use a custom SecurityContextHolderStrategy
			SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<Application> applicationClass = Application.class;
}
