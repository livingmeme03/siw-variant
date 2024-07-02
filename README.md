# Siw-Variant
Sistema informativo su web che gestisce Variant di Manga, i loro Editori e i Manga associati


## Informazioni di dominio ed utilizzo:

### Cos'è un Editore?

    Per Editore, si fa riferimento a un ente associabile ad una Casa Editrice, in questo caso, di Manga.

### Cos'è un Manga?

    Per Manga si fa riferimento ai Manga della "vita reale", letteralmente storie a fumetti giapponesi.

### Cos'è una Variant?

    Per Variant, nel mondo del collezionismo e in questo contesto, si intende un "pezzo raro" di una certa serie Manga.
    Un Manga può avere n Variant, e come nella vita reale, l'Editore del Manga non è vincolante nell'essere anche l'editore della Variant di quella serie.
    
### Chi utilizza il sistema?

    Il sistema è composto da tre tipi di utenti:  

    - L'utente generico (non loggato),  
    - L'Editore (utente registrato, corrisponde a un oggetto "Editore" nel sistema),  

    Nota: L'utente, quando si registra, inserisce anche le credenziali dell'Editore che "Rappresenta".
    L'idea è che un Utente abbia un nome e cognome, e che sia inteso come "Titolare" di una "Casa Editrice", che qui è rappresentata dalla classe Editore.
    Se l'Editore esiste già (in quanto creato dall'Admin), inserire Nome e Nazione di quell'Editore lo associa DIRETTAMENTE al nuovo utente.

    - L'amministratore 

### Cosa può fare un utente generico?

    - Visualizzare l'elenco di Variant
    - Visualizzare una singola Variant
    - Ricerca Variant per Nome
    - Visualizzare l'elenco di Editori
    - Visualizzare un singolo Editore
    - Ricerca Editori per Nome
    - Ricerca Editori per Nazione
    - Visualuzzare l'elenco di Manga
    - Visualizzare un singolo Manga
    - Ricerca Manga per Titolo
    - Ricerca Manga per Autore


### Cosa può fare un utente registrato (Editore)?

    - Tutto quello che può fare l'utente generico
    - Ogni Editore ha un suo Elenco di Variant pubblicate
    - Può aggiungere una nuova Variant a suo nome
    - Può rimuovere una delle sue Variant dal sistema
    - Può assegnare un Manga ad una delle sue Variant

### Cosa può fare un admin?

    - Tutto quello che possono fare gli altri
    - Aggiungere un Editore
    - Rimuovere un Editore
    - Modificare quali Variant un Editore ha pubblicato
    - Modificare quali Variant sono associate a un Manga
