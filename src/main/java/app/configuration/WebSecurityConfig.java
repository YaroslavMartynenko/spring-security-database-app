package app.configuration;

import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final LoginSuccessHandler loginSuccessHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/registration").not().authenticated()
                    .antMatchers("/index", "/", "/complete-registration", "/confirm-account").permitAll()
                    .antMatchers("/main-admin/**").hasAnyRole(UserRole.ROLE_ADMIN.name(), UserRole.ROLE_MODERATOR.name())
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/forbidden")
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(loginSuccessHandler)
                   //.defaultSuccessUrl("/home")
                    .failureForwardUrl("/login-error")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/");
    }
}
