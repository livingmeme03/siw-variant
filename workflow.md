### Creazione packages base
### Creazione classi del modello e relativi attributi, con getter, setter, equals, hashcode, toString

## Persistenza:
     Associazione tra Variant (many) <------> (1) Manga:
        Manga lo mettiamo cascade=REMOVE, perché se cancelliamo un manga cancelliamo tutte le sue relative variant

        Il caricamento delle Variant lo mettiamo fetch=EAGER, perché un utente che seleziona un manga su un sito di variant, lo fa per vedere le variant

    - Associazione tra Variant (many) <------> (1) Editore:
        Editore lo mettiamo cascade=REMOVE, perché se cancelliamo un editore cancelliamo tutte le sue relative variant
        Il caricamento delle Variant lo mettiamo fetch=EAGER, perché un utente che seleziona un manga su un sito di variant, lo fa per vedere le variant

## GESTIONE MAPPE IN SPRING BOOT

    @ElementCollection
	@CollectionTable(name = "RicettaIngrediente2Quantità", joinColumns = @JoinColumn(name = "ricetta_id"))
    @MapKeyColumn(name = "ingrediente_id")
    @Column(name = "quantità")
	private Map<Ingrediente, Integer> ingrediente2quantity;


    RIMOZIONE:
        Quando faccio il delete di una delle cose dentro la mappa, devo necessariamente andare a mano a svuotare le row in cui era incluso.

        NEL SERVICE:
            public void delete(Ingrediente ingrediente) {
		    ingrediente = this.ingredienteRepository.findByNome(ingrediente.getNome());

		    try { this.ingredienteRepository.deleteRowsWithIngredienteFromRicettaIngrediente2Quantità(ingrediente.getId()); }
		    catch (Exception e) {} //Se l'ingrediente non è associato a nessuna ricetta, se provo a rimuovere una row alza un'exception, ma va bene cosi.
		
		    this.ingredienteRepository.delete(ingrediente);
	        }
        NEL REPOSITORY:
            @Query(value = "DELETE FROM ricetta_ingrediente2quantità WHERE ingrediente2quantity_key = :idIngrediente", nativeQuery = true)
	        public void deleteRowsWithIngredienteFromRicettaIngrediente2Quantità(@Param("idIngrediente") Long id);

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
```
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
```
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
        <img th:src="'/' + ${object.pathImmagine}">
        <ul>
            <li th:each="variant : ${listaVariant}">
                <a th:href="@{'/variant/' + ${variant.id}} th:text="${variant.manga.nome}">Variant generica</a>
            </li>
        </ul>
        <div th:if="${variant}"> (fai cose )</div>
        <div th:unless="${variant}"> (fai altre cose )</div>  
        Autore: <span th:text="${manga.autore}"></span>
---------

# Aggiungere singolo elemento
//TODO: ADD PATH TRAVERSAL BASIC DEFENSE

    @GetMapping("/aggiungiEditore)
    public String showFormAggiungiEditore(Model model) {
        model.addAttribute("nuovoEditore", new Editore());
        return "formAggiungiEditore.html";
    }

    @PostMapping("/aggiungiEditore")
    public String newEditore(@Valid @ModelAttribute("nuovoEditore") Editore editore, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "formAggiungiEditore.html";
        }
        else {
            this.editoreService.save(editore);
            return "redirect:editore/"+editore.getId();
        }
    }

    VALIDAZIONE DOPPIONI:
        Classe Validator
        1) IN VALIDATOR NEL METODO validate()
            Vedi se i campi dell'equals sono null (es title!=null && year!=null), se si fai service.existsByTitleAndYear(title,year);
            In questo IF, se la condizione di duplicità si verifica fai errors.reject("movie.duplicato");
        2) NEL SERVICE
            public boolean existsByTitleAndYear(String title, int year) {
                return this.repository.existsByTitleAndYear(title,year);
            }
        3) NELLA REPOSITORY
            public boolean existsByTitleAndYear(String title, int year);
        4) NEL CONTROLLER
            @Autowire
            private MovieValidator mv;

            NEL METODO DEL CONTROLLER
                this.mv.validate(movie, bindingResult);
                if bindingResult.hasErrors() {
                    return "formAggiungiEditore.html";
                }
        5) IN messages.properties
            movie.duplicato=FILM DUPLICATO, MALISSIMO!!
        6) IN TEMPLATE THYMELEAF
            <div th:if="${#fields.hasGlobalErrors()}">
                <p th:each="err : ${#fields.globalErrors()}" th:text="${err}"></p>
            </div>


# Gestione errori
    <span th:if="${fields.hasErrors('nome')}" th:errors="*{nome}"></span>

    ERRORE CON INFORMAZIONI EXTRA DA PASSARE AL MODELLO
        1) Nel controller, nell'if del bindingResult, aggiungi al modello l'attributo che contiene l'info da mostrare
        2) Nel template:
            <div th:if="${#fields.hasGlobalErrors()}">
			    <p th:each="err : ${#fields.globalErrors()}">
				    <span th:text="${err}"></span>
				    <span th:if="${err=='Inserire un numero di volume inferiore a'}">
					    <span th:text="' ' + ${manga.numeroVolumi}"> bho?</span>
				    </span>
			    </p>
		    </div>
    
    System.out.println(bindingResult.getAllErrors().toString());

# Ottenere un elenco ordinato per X criterio
    public List<Ricetta> findAllByOrderByNomeRicettaAsc();
        Questo ordina per NomeRicetta, in ordine alfabetico (Asc)
    public List<Ricetta> findAllByOrderByMangaTitolo();
        Questo ordina per titolo del manga, in ordine alfabetico


# Implementa la validazione per registrazione utente 
    (no dups, magari anche un controllo password figa? Da fare alla fine!)

# Thymeleaf more than 1 bindingResult
Controllo errori su entrambi, e aggiungi 
    if(bindingResultUser.hasErrors() || bindingResultCredentials.hasErrors()) {
			model.addAttribute("userErrors", bindingResultUser);
			return "formRegister.html";
		}

    <span th:if="${userErrors != null and userErrors.hasFieldErrors('cognome')}" th:errors="${user.cognome}"></span>

NB:
    IL BINDINRESULT E' FATTO COSI:
    [Field error in object 'user' on field 'nome']
    Quindi su hasFieldErrors() mettici il field, NON user.nome!!


# Rimuovi un elemento
NEL CONTROLLER:

	@GetMapping("/rimuoviEditore")
	public String showFormrimuoviEditore(Model model) {
		model.addAttribute("editoreDaRimuovere", new Editore());
		return "formRimuoviEditore.html";
	}

	@PostMapping("/rimuoviEditore")
	public String rimuoviEditore(@Valid @ModelAttribute("editoreDaRimuovere") Editore editore, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) { //Significa che la variant esiste oppure ci sono altri errori
			if(bindingResult.getAllErrors().toString().contains("variant.duplicata")) { 
				this.variantService.delete(variant);
				return "redirect:elencoVariant"; //Unico caso funzionante!
			}
			return "formRimuoviVariant.html"; //Ho problemi ma non il variant.duplicata, quindi lo user ha toppato
		}

		bindingResult.reject("variant.nonEsiste");
		return "formRimuoviVariant.html"; //Ha inserito una variant che non esiste
	}

NEL SERVICE:

    	public void delete(Variant variant) {
		Variant del = this.variantRepository.findBySomething(variant.getSomething());
		this.variantRepository.delete(del);
	}

THYMELEAF TEMPLATE:
    Letteralmente come aggiungi, ma solo coi parametri che fanno da identificatore all'oggetto da rimuovere

# Thymeleaf img src solo se c'è
    <span th:if="${manga.pathImmagine != null}">
		<img th:src="${manga.pathImmagine}" width="20%">
	</span>

# Aggiornare un determinato oggetto
    1) Form html con elenco di tutto gli oggetti e accanto un link con scritto tipo "aggiorna editore" (formModificaVariantEditore)

        ```
        <div>
        <img src="https://static.bandainamcoent.eu/high/dragon-ball/dragonball-xenoverse-2/00-page-setup/dbxv2_game-thumbnail.jpg" width="20%" />
    </div>
	<h2>Variant dell'editore <a th:href="@{'/editore' + '/' + ${editore.id}}" th:text="${editore.nome}"></a></h2>
    <div>
        <div th:if="${allVariantMesse==null || allVariantMesse.isEmpty()}">
            Questo editore non ha pubblicato nessuna variant
        </div>
        <div th:unless="${allVariantMesse==null || allVariantMesse.isEmpty()}">
        	<ul>
            	<li th:each="variant : ${allVariantMesse}">
                	<a th:href="@{'/removeVariant' + '/' + ${editore.id} + '/' + ${variant.id}}" th:text="${variant.nomeVariant}">Goku Variant</a>
            	</li>
        	</ul>
        </div>
    </div>
	<h2>Variant disponibili</h2>
	<div>
	       <div th:if="${allVariantDisponibili==null || allVariantDisponibili.isEmpty()}">
	            Questo editore ha pubblicato tutte le variant nel sistema!
	        </div>
	        <div th:unless="${allVariantDisponibili==null || allVariantDisponibili.isEmpty()}">
	        	<ul>
	            	<li th:each="variant : ${allVariantDisponibili}">
	                	<a th:href="@{'/addVariant' + '/' + ${editore.id} + '/' + ${variant.id}}" th:text="${variant.nomeVariant}">Goku Variant</a>
	            	</li>
	        	</ul>
	        </div>
	    </div>
        ```
        
    2) Aggiorna editore, se cliccato, reindirizza su /impostaEditoreAVariant/{idVariant}
        Qui prendo il primo ID con cui manipolare la Variant
    3) In quel form, ho elenco degli EDITORI su cui, se ci clicco, va verso /impostaEditoreAVariant/{idVariant}/{idEditore}
    4) Questo metodo è un mapping che fa tutto. Prende dal db variant con ID, editore con ID, fa il set di editore nella variant e poi, importante, il SAVE della variant cambiata
    5) Redirect alla pagina dell'oggetto che è cambiato per vederlo /variant/{idVariant}

# Menu a tendina thymeleaf

    <select th:field="${cuoco.nome}">
		<option value="Nessun cuoco" text="Nessun cuoco">Nessun cuoco</option>
		<option th:each="line : ${elencoNomeCognomeData}" th:value="${line}" th:text="${line}">Nome - Cognome - DataNascita</option>
	</select>

# Form di ricerca
    1) Nel controller 
        @GetMapping("/formRicercaX")
        public String showFormRicercaX(Model model) {

        }
    2) Template showFormRicercaX.html
        Fai un form ognuno con un input diverso per i vari tipi di ricerca, ognuno con un postMapping diverso, e sfrutta elencoVariant che hai già!!!

## Associare qualcosa a credenziali
    0) Fare un metodo statico su authcontroller che 
    1) Associare allo "User" la classe relativa, tipo Editore (OneToOne bidirezionale)
    2) Dentro il metodo che mostra la roba che può fare solo una certa persona, faccio:
        Prendo dalle credenziali l'editoreId, e dalla variant l'editoreId.
            Credenziali: 
                user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
                String username = user.getUsername();
                Credentials currentCredentials = this.credentialsService.findByUsername(username);
            
        Mostro solo quelle che combaciano

# Operazioni e permessi:  

    PER TUTTI
        ElencoEditori,ElencoManga,ElencoVariant,Register,Login,Index
        Tutte le ricerche

    PER LOGGATI
            (quando ti registri, se non esistevi già come editore nel db si crea un oggetto Credentials, User, Editore) 
            (possibilità di registrarsi con un editore già presente senza user, sulla base di nome e nazione)
        AggiungiVariant(qui si autofilla il campo editore con sé stesso)
        ModificaVariant(modificando manga, solo le sue) -> è elencoAggiornaVariant senza la parte di editore
        RimuoviVariant(solo le sue)

    PER ADMIN
        Aggiungi editore, aggiungi manga, aggiungi variant
        ModificaVariant(editore e manga) -> è elencoAggiornaVariant
        RimuoviVariant(tutte), RimuoviManga, RimuoviEditore
        ElencoAggiornaManga
        modificaVariantEditore

    - Aggiustare la securityChain e mettere bene gli URL
    - Registrarsi e nel db modificare a mano il permesso in "ADMIN"
    - Togliere il refresh del db da application.properties
    - Mettere i path dell'admin dentro una cartella admin, e magari stessa cosa anche per l'utente loggato?
    - Nei controller ci va /admin/template.html


# Mettere nome in alto a dx

    Il globalcontroller fa tutto, per lo username fai userDetails.username

# REST controllers
    Duplica i controller degli oggetti, e annotali @RestController
    L'idea è di fornire a un'app esterna, che fa da utente non loggato, le informazioni accessibili a tutti, quindi elenchi e oggetti singoli!