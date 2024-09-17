package com.springsecurity.Security.config;


import com.springsecurity.Security.service.CustomeUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    private CustomeUserDetailService customeUserDetailService;
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request
                .requestMatchers("register", "login")
                .permitAll()
                .anyRequest().authenticated());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//        http.formLogin(Customizer.withDefaults()); // this is for browser
        http.httpBasic(Customizer.withDefaults()); // this is for Postman


        //imperitive approach to disable csrf
//        Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomiser = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//                customizer.disable();
//            }
//        };
//        http.csrf(csrfCustomiser);

        return http.build();
    }

//    @Bean
//    public UserDetailsService getUser(){
//        UserDetails usr1= User
//                .withDefaultPasswordEncoder()
//                .username("akash")
//                .password("@ak")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("varun")
//                .password("@va")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(usr1,user2);
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        provider.setUserDetailsService(customeUserDetailService);
//        return provider;
//    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());  // this is to do the matching without any encryption
        /* now we are using BCryptPaswordEncoder so to do matchingin order to provide authentication need to convetr raw
        * password into BCrypt one*/
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(customeUserDetailService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }


}