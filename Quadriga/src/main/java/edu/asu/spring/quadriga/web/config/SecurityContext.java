package edu.asu.spring.quadriga.web.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import edu.asu.spring.quadriga.authentication.UserService;
import edu.asu.spring.quadriga.web.config.social.SimpleSocialUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {
    //private AuthenticationManager authManager;
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
        // Spring Security ignores request to static resources such as CSS or JS
        // files.
        .ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /*
        Refer: http://stackoverflow.com/questions/29595098/why-doesnt-my-custom-login-page-show-with-spring-security-4
        http
        .csrf().disable();
        http
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
         http
        .authorizeRequests()
        .antMatchers("/login*").anonymous()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/auth/welcome")
        .failureUrl("/loginfailed")
        //.successHandler(successHandler())
        .and()
        .logout().logoutSuccessUrl("/login")
        .invalidateHttpSession(true);
         
        */
        
        HeadersConfigurer<HttpSecurity> config = http.antMatcher("**").headers().frameOptions().sameOrigin();
    
        // Configures form login
        config.and().exceptionHandling().accessDeniedPage("/403")
                // Configures url based authorization
                .and()
                .authorizeRequests()
                // Anyone can access the urls
                .antMatchers("/auth/**", "/signin/**", "/", "/connect/**",
                        "/signup/**", "/user/register/**", "/resources/**",
                        "/rest/**","/register*","/login*","/logout*","/createaccount*","/sites/**", "/public/**","/search/**","/home/**").permitAll()
                // The rest of the our application is protected.
                .antMatchers("/users/**", "/admin/**").hasRole("QUADRIGA_USER_ADMIN")
                .antMatchers("/auth/welcome**", "/auth/home**","/auth/about**","/auth/profile/**").hasAnyRole("QUADRIGA_USER_ADMIN","QUADRIGA_USER_STANDARD","QUADRIGA_USER_COLLABORATOR")
                .antMatchers("/auth/workbench/**", "/auth/rest/**","/auth/conceptcollections/**","/auth/conceptdetails/**","/auth/transformation/**","/auth/searchitems/**","/auth/dictionaries/**","/auth/editing/**","/auth/networks/**").hasAnyRole("QUADRIGA_USER_STANDARD","QUADRIGA_USER_COLLABORATOR")
                .antMatchers("/users/**", "/admin/**","/auth/users/**","/checks/**").hasRole("QUADRIGA_USER_ADMIN")
                .antMatchers("/users/**", "/admin/**").hasRole("QUADRIGA_USER_ADMIN")
                //.antMatchers("/**").denyAll()
                .anyRequest().hasAnyRole("QUADRIGA_USER_ADMIN","QUADRIGA_USER_STANDARD","QUADRIGA_USER_COLLABORATOR")
                
                // Adds the SocialAuthenticationFilter to Spring Security's
                // filter chain.
                .and().apply(new SpringSocialConfigurer());
    }
  
    
   @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(daoAuthProvider());
        //auth.authenticationProvider(new AdminAuthenticationProvider());
        //authManager = auth.getObject();
        
    }
    
  /*  @Bean(name=BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return authManager ;
    }
    */
  

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("authSuccessHandler");
        return successHandler;
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
        return new UserService();
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthProvider() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

}