package com.webapp.ygsschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig  {

    /**
     * From Spring Security 5.7, the WebSecurityConfigurerAdapter is deprecated to encourage users
     * to move towards a component-based security configuration. It is recommended to create a bean
     * of type SecurityFilterChain for security related configurations.
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        /**
         * Default configurations which will secure all the requests
         */
		/*((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).
				authenticated();
		http.formLogin();
		http.httpBasic();
		return (SecurityFilterChain)http.build();*/

        /**
         * Custom configurations as per our requirement
         * Redirecting to my custom login page with formLogin
         */
        http
                .authorizeHttpRequests( (auth)->auth
                    .mvcMatchers("/holidays","/courses","/dashboard").authenticated()
                    .mvcMatchers("/","/index","/about","/login","/register","/contact").permitAll()
                   //     .mvcMatchers("/","/index","/about","/login","/register","/holidays","/courses","/contact").permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form ->form
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll());

        return http.build();

        /**
         * Configuration to deny all the requests
         */
		/*http.authorizeHttpRequests( (auth)->auth
				.anyRequest().denyAll())
				.httpBasic(Customizer.withDefaults());
		return http.build();*/

        /**
         * Configuration to permit all the requests
         */
		/*http.authorizeHttpRequests( (auth)->auth
						.anyRequest().permitAll())
				.httpBasic(Customizer.withDefaults());
		return http.build();*/
    }

    //Creating our own inmemory database
    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user@gmail.com")
                .password("1234")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("admin@gmail.com")
                .password("4321")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
