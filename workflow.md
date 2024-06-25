- Creazione packages base
- Creazione classi del modello e relativi attributi, con getter, setter, equals, hashcode, toString

Persistenza:
    - Associazione tra Variant (many) <------> (1) Manga:
        Manga lo mettiamo cascade=REMOVE, perché se cancelliamo un manga cancelliamo tutte le sue relative variant
        Il caricamento delle Variant lo mettiamo fetch=EAGER, perché un utente che seleziona un manga su un sito di variant, lo fa per vedere le variant
    - Associazione tra Variant (many) <------> (1) Editore:
        Editore lo mettiamo cascade=REMOVE, perché se cancelliamo un editore cancelliamo tutte le sue relative variant
        Il caricamento delle Variant lo mettiamo fetch=EAGER, perché un utente che seleziona un manga su un sito di variant, lo fa per vedere le variant

- Creazione Interfacce repository, Classi service, Classi controller
    Repository -> extends CrudRepository<tipoClasse, tipoId>
    Service ->    @Service, @Autowired repository
    Controller -> @Controller, @Autowired service

- Autenticazione:
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
                