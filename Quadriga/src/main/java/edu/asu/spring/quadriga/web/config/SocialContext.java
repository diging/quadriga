package edu.asu.spring.quadriga.web.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.utilities.IPropertiesManager;
import edu.asu.spring.quadriga.web.config.social.AdjustableGithubConnectionFactory;
import edu.asu.spring.quadriga.aspects.access.tokens.impl.GitHubChecker;


@Configuration
//@PropertySource("classpath:/social.properties") 
@EnableSocial

public class SocialContext implements SocialConfigurer  {
  
    @Autowired
    private DataSource dataSource;

    @Autowired
    private IUserManager userManager;
    
    @Autowired
    private IUserHelper userHelper;
    
    @Autowired
    private IPropertiesManager propertyManager;
    
    @Autowired
    private IIdentityProviderRegistry identityProviderRegistry;
    
    @Autowired
    private IReloadService reloadService;

    private static final Logger logger = LoggerFactory.getLogger(SocialContext.class);
    
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig,
            Environment env) {

        String githubClientId = propertyManager.getProperty(SocialProperties.GITHUB_CLIENT_ID);
        
        String githubSecret = propertyManager.getProperty(SocialProperties.GITHUB_SECRET);
        //GitHubConnectionFactory githubFactory = new GitHubConnectionFactory( githubClientId, githubSecret);
        AdjustableGithubConnectionFactory githubFactory = new AdjustableGithubConnectionFactory(githubClientId, githubSecret);
        
        cfConfig.addConnectionFactory(githubFactory);
        reloadService.addFactory(IReloadService.GITHUB, githubFactory);
        identityProviderRegistry.addProvider(githubFactory.getProviderId());
        identityProviderRegistry.addProviderTokenChecker(githubFactory.getProviderId(), GitHubChecker.ID);
    }
    
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(
            ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
                dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setConnectionSignUp(new QuadrigaConnectionSignUp(userManager, userHelper));
        return repository;
    }

    @Bean
    public ProviderSignInController providerSignInController(
            ConnectionFactoryLocator connectionFactoryLocator,
            UsersConnectionRepository usersConnectionRepository) {
        ProviderSignInController controller = new ProviderSignInController(
                connectionFactoryLocator, usersConnectionRepository,
                new SimpleSignInAdapter(userManager, userHelper));
        
        return controller;
    } 
}
