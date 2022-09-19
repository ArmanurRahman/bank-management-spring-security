package net.armanit.springsecuritydemo.config;

import net.armanit.springsecuritydemo.filter.JWTTokenValidatorFilter;
import net.armanit.springsecuritydemo.filter.AuthoritiesLoogingAfterFilter;
import net.armanit.springsecuritydemo.filter.JWTTokenGeneratorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(auth -> auth
//                .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults());
//        return httpSecurity.build();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .cors().configurationSource(
                        new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration corsConfiguration = new CorsConfiguration();
                                corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                                corsConfiguration.setAllowCredentials(true);
                                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                                corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
                                corsConfiguration.setMaxAge(3600L);
                                return corsConfiguration;
                            }
                        }
                )
                .and().csrf().disable()
//                .ignoringAntMatchers("/contact")
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .addFilterAfter(new AuthoritiesLoogingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/myAccount").hasRole("USER")
                        .antMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                        .antMatchers("/myLoans").hasRole("ROOT")
                        .antMatchers( "/myCards", "/user").authenticated()
                        .antMatchers("/notices", "/contact").permitAll())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
////        UserDetails admin = User.withDefaultPasswordEncoder()
////                .username("admin")
////                .password("12345")
////                .authorities("admin")
////                .build();
////        UserDetails user = User.withDefaultPasswordEncoder()
////                .username("user")
////                .password("12345")
////                .authorities("read")
////                .build();
////        return new InMemoryUserDetailsManager(admin, user);
//
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
//        UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
//        userDetailsService.createUser(admin);
//        userDetailsService.createUser(user);
//        return userDetailsService;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
