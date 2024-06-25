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
