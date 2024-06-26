## Creazione packages base
## Creazione classi del modello e relativi attributi, con getter, setter, equals, hashcode, toString

## Persistenza:
     Associazione tra Variant (many) <------> (1) Manga:
        Manga lo mettiamo cascade=REMOVE, perché se cancelliamo un manga cancelliamo tutte le sue relative variant
##        Il caricamento delle Variant lo mettiamo fetch=EAGER, perché un utente che seleziona un manga su un sito di variant, lo fa per vedere #        le variant
    - Associazione tra Variant (many) <------> (1) Editore:
        Editore lo mettiamo cascade=REMOVE, perché se cancelliamo un editore cancelliamo tutte le sue relative variant
        Il caricamento delle Variant lo mettiamo fetch=EAGER, perché un utente che seleziona un manga su un sito di variant, lo fa per vedere le variant

## Creazione Interfacce repository, Classi service, Classi controller
    Repository -> extends CrudRepository<tipoClasse, tipoId>
    Service ->    @Service, @Autowired repository
    Controller -> @Controller, @Autowired service

## Autenticazione:
    Creare classi User e Credentials, con repository e service
 
        User -> @Entity, @Table(name="Users")
            Long id;
            String name;
            String 
        Credentials -> @Entity
            public static final String DEFAULT_ROLE = "default";
            public static final String ADMIN_ROLE = "admin";

            Long id;
            String username;
            String password;
            String role;

            @OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
            User user;

        UserRepository -> extends CrudRepository<User, Long>

            public User findById(Long id);

        CredentialsRepository -> extends CrudRepository<Credentials, Long>

            public Credentials findByUsername(String username);

        UserService -> @Service
            @Autowired
            public UserRepository ur;

            public User getUser(Long id) {
                return this.ur.findById(id).get();
            }

            public User saveUser(User user) {
                return this.ur.save(user);
            }

        CredentialsService -> @Service
            @Autowired
            public CredentialsRepository cr;

            public Credentials getCredentials(Long id){
                return this.cr.findById().get();
            }

            public Credentials getCredentials(String username){
                return this.cr.findByUsername(username).get();
            }

            public Credentials saveCredentials(Credentials credentials) {
                return this.cr.save(credentials);
            }

    Creare AuthenticationController, GlobalController, package authentication con dentro AuthConfiguration.java

        AuthConfiguration.java -> slide siw-09 19,20
        
        GlobalController -> slide siw-09 22
            Authentication da importare è quella con core
        
        AuthenticationController -> Mapping a Registrazione, login e /
            Nello / ci va il controllo dell'autenticazione e mostrare il giusto index
        
        NOTE: La password non si hasha da sola, e va hashata manualmente nel CredentialService !!!
              Nel CredentialService, settare il ruolo di base
        NOTE: Sovrascrivere il GetMapping del /login, ma non è necessario sovrascrivere il PostMapping, l'importante è scrivere bene la POST
              perciò th:action e non un semplice html, con da inviare username e password, very basic.


# Validazione
    1) Usare le annotazioni base
        -Le usi mettendo nel metodo del controller il @Valid, BindingResult bindingResult
        -if(!bindingResult.hasErrors()) {

        }
    2) Classi validator dove serve (import org.springframework.validation.Validator;)
        - nel controller metti autowire validator
            this.movieValidator.validate(movie, bindingResult)
        - implements Validator
        - @Autowire service
        - metodo validate(Object, Errors)
            se non rispetta un vincolo:
                errors.reject("movie.duplicate")
    
    Internazionalizzazione errori:
        - Dentro Resources crea folder /messages
            - Dentro la folder 
                messages_en.properties
                messages_it.properties
            NotBlank.movie.title -> L'annotazione @NotBlank della classe Movie sulla variabile title
        application.properties:
            spring.messages.basename=messages/messages
            spring.messages.encoding=ISO-8859-1

        TIP:
            Ogni volta che si fa un metodo di validazione e si genera un messaggio di errore (es. variant.volumeTooBig)
            andare subito a metterlo nei messages.properties, cosi non ce lo dimentichiamo!

    Thymeleaf:
        <span><input type="text" th:field="*{title}" /></span>
            <span th:if="${#fields.hasErrors('titolo')}" th:errors="*{titolo}"></span>
        
        #fields.hasErrors('...'): funzione che riceve come parametro un campo, e ritorna un booleano che riporta se c'è stato un qualche errore di validazione per quel campo (in pratica accede alla variabile BindingResults).
        th:errors, uno speciale attributo che costruisce una lista di tutti gli errori del campo selezionato, separati da un tag <br /> (break row, va a capo)

# Mostrare elenco:
        Nel controller 
            @GetMapping("/elencoEditori")
	        public String showElencoEditori(Model model) {
		        Iterable<Editore> allEditori = this.editoreService.findAll();
		        model.addAttribute("allEditori", allEditori);
		        return "elencoEditori.html";
	            }

        Nel service
            public Iterable<Editore> findAll() {
		        return this.editoreRepository.findAll();
	        }
        Nel template
            <div th:if="${allEditori==null || allEditori.isEmpty()}">Non ci sono editori nel sistema!</div>
		        <ul>
			        <li th:each="editore : ${allEditori}">
				        <a th:href="@{'/editore' + '/' + ${editore.id}}" th:text="${editore.nome}">Editore Generico!</a>
			        </li>
		        </ul>
	        </div>
	       
        In import.sql
            INSERT INTO editore (id, nazione, nome) VALUES(nextval('editore_seq'), 'Italia', 'J-POP')
            - NON USARE int, boolean MA USA INTEGER (altrimenti non può settarsi a null)

# Template per elenco dettagliato

<!DOCTYPE html>
<html>

<head>
    <title>SiwVariant - Editori</title>
    <link rel="stylesheet" href="/css/stile.css" />
</head>

<body>
    <div>
        <img src="https://static.bandainamcoent.eu/high/dragon-ball/dragonball-xenoverse-2/00-page-setup/dbxv2_game-thumbnail.jpg" width="20%" />
    </div>
    <h1>Benvenuto nell'elenco editori!</h1>
    <div>
        <div th:if="${allEditori==null || allEditori.isEmpty()}">
            Non ci sono editori nel sistema!
        </div>
        <div th:unless="${allEditori==null || allEditori.isEmpty()}">
        	<ul>
            	<li th:each="editore : ${allEditori}">
                	<a th:href="@{'/editore' + '/' + ${editore.id}}" th:text="${editore.nome}">Editore Generico</a>
				
            	</li>
        	</ul>
        </div>
    </div>
</body>

</html>

# Mostrare singolo elemento
    NEL CONTROLLER:
        @GetMapping("/editore/{id})
        public String showEditore(@PathVariable("id") Long id, Model model) {
            model.addAttribute("editore", this.editoreService.findById(id));
            return "editore.html";
        }

    IN SERVICE:
        public Cuoco findById(Long id) {
		    try {
			    return this.cuocoRepository.findById(id).get();
		    }
		    catch (NoSuchElementException e) {
			    return null;
		    }
	    }

    NEL TEMPLATE:
        Niente, fai le solite cose di thymeleaf avendo già l'oggetto nel modello!
        <img th:src=${object.pathImmagine}>
        <ul>
            <li th:each="variant : ${listaVariant}">
                <a th:href="@{'/variant/' + ${variant.id}} th:text="${variant.manga.nome}">Variant generica</a>
            </li>
        </ul>
        <div th:if="${variant}"> (fai cose )</div>
        <div th:unless="${variant}"> (fai altre cose )</div>  
        Autore: <span th:text="${manga.autore}"></span>

    
