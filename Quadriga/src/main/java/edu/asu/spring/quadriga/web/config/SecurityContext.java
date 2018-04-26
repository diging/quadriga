package edu.asu.spring.quadriga.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import edu.asu.spring.quadriga.utilities.IPropertiesManager;
import edu.asu.spring.quadriga.web.config.social.SimpleSocialUserDetailsService;

/**
 * 
 * Security configurations to manage authentication and authorization.
 * 
 * @author Chiraag Subramanian
 *
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityContext {
    
    @Configuration
    @Order(3)
    public static class StatefulSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier("userService")
        private UserDetailsService userService;

        @Autowired
        @Qualifier("authSuccessHandler")
        private SavedRequestAwareAuthenticationSuccessHandler authSuccessHandler;
        
        @Autowired
        private IPropertiesManager propertyManager;

        @Override
        public void configure(WebSecurity web) throws Exception {
         // Spring Security ignores request to static resources such  as CSS or JS files.
            web.ignoring().antMatchers("/static/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            HeadersConfigurer<HttpSecurity> config = http.antMatcher("**").headers().frameOptions().sameOrigin();
            config.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                    // Configures url based authorization
                    .and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/auth/welcome")
                    .successHandler(authSuccessHandler).failureUrl("/loginfailed").and().authorizeRequests()
                    // Anyone can access the urls
                    .antMatchers("/signin/**", "/connect/**", "/signup/**", "/user/register/**",
                            "/resources/**", "/register*", "/login*", "/logout*", "/createaccount*", "/sites/**",
                            "/public/**", "/search/**", "/home/**", "/sociallogin*", "/forbidden")
                    .permitAll()
                    // The rest of the our application is protected.
                    .antMatchers("/users/**", "/admin/**").hasRole("QUADRIGA_USER_ADMIN")
                    .antMatchers("/", "/auth/welcome**", "/auth/home**", "/auth/about**", "/auth/profile/**",
                            "/rest/**")
                    .hasAnyRole("QUADRIGA_USER_ADMIN", "QUADRIGA_USER_STANDARD", "QUADRIGA_USER_COLLABORATOR")
                    .antMatchers("/auth/workbench/**", "/auth/rest/**", "/auth/conceptcollections/**",
                            "/auth/conceptdetails/**", "/auth/transformation/**", "/auth/searchitems/**",
                            "/auth/dictionaries/**", "/auth/editing/**", "/auth/networks/**")
                    .hasAnyRole("QUADRIGA_USER_STANDARD", "QUADRIGA_USER_COLLABORATOR")
                    .antMatchers("/users/**", "/admin/**", "/auth/users/**", "/checks/**")
                    .hasRole("QUADRIGA_USER_ADMIN").anyRequest()
                    .hasAnyRole("QUADRIGA_USER_ADMIN", "QUADRIGA_USER_STANDARD", "QUADRIGA_USER_COLLABORATOR")
                    .antMatchers("/**").denyAll()
                    // Adds the SocialAuthenticationFilter to Spring Security's
                    // filter chain.
                    .and().apply(new SpringSocialConfigurer());

        }

        @Autowired
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(daoAuthProvider());
            auth.eraseCredentials(false);
            auth.inMemoryAuthentication().withUser(propertyManager.getProperty("admin_username")).password(propertyManager.getProperty("admin_password")).roles("QUADRIGA_USER_ADMIN");
            super.configure(auth);
            
        }

        @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(10);
        }

        @Bean
        public SocialUserDetailsService socialUserDetailsService() {
            return new SimpleSocialUserDetailsService(userDetailsService());
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return userService;
        }

        @Bean
        public DaoAuthenticationProvider daoAuthProvider() throws Exception {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder());
            provider.setUserDetailsService(userDetailsService());
            return provider;
        }
        
        @Bean
        public AccessDeniedHandler accessDeniedHandler(){
            return new QuadrigaAccessDeniedHandler();
        }

    }

    // Security Configuration for REST end-points
    @Configuration
    @Order(1)
    public static class RESTSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            HeadersConfigurer<HttpSecurity> restConfig = http.antMatcher("/rest/**").headers().frameOptions()
                    .sameOrigin();
            restConfig.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            restConfig.and().authorizeRequests().antMatchers("/**").hasAnyRole("QUADRIGA_USER_ADMIN",
                    "QUADRIGA_USER_STANDARD", "QUADRIGA_USER_COLLABORATOR");
            restConfig.and().httpBasic();
            restConfig.and().csrf().disable();
            
            
        }
    }
    
    
    // Security Configuration for Public end-points
    @Configuration
    @Order(2)
    public static class PublicSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            HeadersConfigurer<HttpSecurity> restConfig = http.antMatcher("/public/**").headers().frameOptions()
                    .sameOrigin();
            restConfig.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            restConfig.and().authorizeRequests().antMatchers("/**").permitAll();
            restConfig.and().httpBasic();
            restConfig.and().csrf().disable();
            
            
        }

    }

}