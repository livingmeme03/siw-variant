package it.uniroma3.siw.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.uniroma3.siw.model.Credentials;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

	@Autowired
	private DataSource dataSource;

	/*#######################################################################################*/
	/*--------------------------------------PWD CHECKER--------------------------------------*/
	/*#######################################################################################*/
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	
	/*#######################################################################################*/
	/*----------------------------------------HASHER-----------------------------------------*/
	/*#######################################################################################*/
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	/*#######################################################################################*/
	/*-------------------------------------SECURITY CHAIN------------------------------------*/
	/*#######################################################################################*/
	
	@SuppressWarnings("removal")
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity)
			throws Exception{
	  httpSecurity
	  .csrf().and().cors().disable()
	  .authorizeHttpRequests()
	  // .requestMatchers("/**").permitAll()
	  
	  
	  // chiunque (autenticato o no) può accedere alle pagine 
	  .requestMatchers(HttpMethod.GET,"/",
			  "/elencoEditori", "/editore/**", "/ricercaEditorePerNome", "/ricercaEditorePerNazione", //Editore
			  "/elencoManga", "/manga/**", "/ricercaMangaPerTitolo", "/ricercaMangaPerAutore", //Manga
			  "/elencoVariant", "/variant/**", "/formRicercaVariant", //Variant
			  "/register","/css/**", "/images/**", "favicon.ico"
			  
	  ).permitAll() //Roba per utente non loggato
	  // chiunque (autenticato o no) può mandare richieste POST
	  
	  .requestMatchers(HttpMethod.POST,"/register", "/login", 
			  "/ricercaEditorePerNome", "/ricercaEditorePerNazione", //Editore
			  "/ricercaMangaPerTitolo", "/ricercaMangaPerAutore", //Manga
			  "/ricercaPerNomeVariant" //Variant
	  ).permitAll()
	  
	  
	  
	  .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
	  .requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
	  
	  
	  
	  // tutti gli utenti autenticati possono accere alle pagine rimanenti
	  .anyRequest().authenticated() //Roba per Editore == Utente loggato
	  // LOGIN: qui definiamo il login
	  .and().formLogin()
	  .loginPage("/login")
	  .permitAll()
	  .defaultSuccessUrl("/", true) //TODO: remove
	  .failureUrl("/login?error=true")
	  // LOGOUT: qui definiamo il logout
	  .and()
	  .logout()
	  // il logout è attivato con una richiesta GET a "/logout"
	  .logoutUrl("/logout")
	  // in caso di successo, si viene reindirizzati alla home
	  .logoutSuccessUrl("/")
	  .invalidateHttpSession(true)
	  .deleteCookies("JSESSIONID")
	  .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	  .clearAuthentication(true).permitAll();
	  
	  return httpSecurity.build();
	  
	  }
}