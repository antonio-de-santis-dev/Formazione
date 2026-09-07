# Sicurezza e Permessi Utente nei progetti JHipster

Analisi del funzionamento della sicurezza e della gestione dei permessi nei tre progetti caricati, con una spiegazione generale di come JHipster implementa autenticazione e autorizzazione.

| Progetto | JHipster | Autenticazione | Database | Tipo app | Client |
|----------|----------|----------------|----------|----------|--------|
| **RistoHub** | 8.11.0 | `session` (cookie + CSRF) | PostgreSQL | monolith | Angular |
| **RistoHubAlfa** | 8.11.0 | `session` (cookie + CSRF) | PostgreSQL | monolith | Angular |
| **boardroom** | 8.11.0 | `jwt` (Bearer token) | MySQL | monolith | Angular |

Tutti e tre condividono lo stesso motore di sicurezza JHipster; la differenza sostanziale è che boardroom usa JWT stateless mentre gli altri due usano sessioni HTTP con cookie.

---

## 1. Il modello di sicurezza JHipster in generale

JHipster genera sopra **Spring Security** (backend) e un livello di guardie/interceptor **Angular** (frontend). I concetti chiave sono sempre gli stessi:

### 1.1 Ruoli / Authorities

I permessi sono modellati come **authorities** (ruoli) rappresentate dalla stringa `ROLE_*`. In tutti e tre i progetti la classe `AuthoritiesConstants` definisce esattamente gli stessi tre ruoli di default:

```java
public static final String ADMIN     = "ROLE_ADMIN";
public static final String USER      = "ROLE_USER";
public static final String ANONYMOUS = "ROLE_ANONYMOUS";
```

- **ROLE_ADMIN** → accesso completo, incluse le API di amministrazione e i pannelli di management.
- **ROLE_USER** → utente autenticato standard.
- **ROLE_ANONYMOUS** → utente non autenticato (usato internamente da Spring Security).

Nessuno dei tre progetti ha introdotto ruoli custom aggiuntivi: la personalizzazione dei permessi avviene tramite regole più fini sugli endpoint e tramite logica di **ownership** (vedi boardroom).

Il modello dati collega `User` ↔ `Authority` (relazione molti-a-molti). Un utente può avere più ruoli; le authorities vengono caricate durante il login (`DomainUserDetailsService.loadUserByUsername`).

### 1.2 Due livelli di autorizzazione

JHipster protegge l'applicazione su **due livelli complementari**:

1. **Livello URL (SecurityConfiguration)** — regole su pattern di URL (`/api/admin/**`, `/api/**`, ecc.) nel `SecurityFilterChain`.
2. **Livello metodo (annotazioni)** — `@Secured`, `@PreAuthorize` sui singoli metodi di controller/servizio, abilitate da `@EnableMethodSecurity(securedEnabled = true)`.

Il frontend aggiunge un **terzo livello "cosmetico"** (route guard + direttiva `*jhiHasAnyAuthority`) che nasconde/blocca la UI, ma **non è sicurezza reale**: la protezione effettiva è sempre lato server.

---

## 2. Autenticazione

### 2.1 RistoHub e RistoHubAlfa — Sessione (cookie + CSRF)

Questi due progetti usano **autenticazione basata su sessione HTTP**:

- Login via `POST /api/authentication` (form login). In caso di successo → `200 OK`, fallimento → `401`.
- La sessione è mantenuta con un cookie di sessione lato server.
- È attiva la funzione **Remember-Me** tramite `PersistentTokenRememberMeServices` e l'entità `PersistentToken` (token persistiti su DB), con parametro `remember-me` e chiave configurata in `jHipsterProperties`.
- La password è cifrata con **BCrypt** (`BCryptPasswordEncoder`).

**Protezione CSRF attiva** (necessaria perché i cookie vengono inviati automaticamente dal browser):

- Token CSRF salvato in un cookie leggibile da JS: `CookieCsrfTokenRepository.withHttpOnlyFalse()`.
- Handler custom `SpaCsrfTokenRequestHandler` che gestisce il token in modalità BREACH-safe per Single Page Application Angular.
- In profilo `dev` la protezione CSRF è disabilitata solo per la console H2 (`/h2-console/**`).

### 2.2 boardroom — JWT (stateless)

boardroom usa **JSON Web Token** con Spring Security come *OAuth2 Resource Server*:

- Login via `POST /api/authenticate` con `LoginVM` (username/password). Risposta: un JWT nel campo `id_token` + header `Authorization: Bearer …`.
- Il token è firmato **HMAC (HS512)** con un segreto Base64 (`jhipster.security.authentication.jwt.base64-secret`).
- Il JWT contiene i claim: `sub` (login), `auth` (authorities separate da spazio), `user_id`, `iat`, `exp`.
- **Validità token: 24 ore** (`token-validity-in-seconds: 86400`); valore più lungo se `rememberMe`.
- Sessione **STATELESS** (`SessionCreationPolicy.STATELESS`): nessuno stato lato server, il token è auto-contenuto.
- **CSRF disabilitato** (`csrf.disable()`): corretto, perché con Bearer token in header il rischio CSRF non si applica.
- Password sempre cifrata con **BCrypt**.
- Errori JWT gestiti con metriche dedicate (`SecurityMetersService`: firma non valida, token scaduto, malformato).

⚠️ **Nota di sicurezza**: in `application-dev.yml` il segreto JWT è committato in chiaro. In produzione (`application-prod.yml`) è correttamente previsto che venga sovrascritto tramite variabile d'ambiente / configserver. Il segreto di sviluppo **non deve mai finire in produzione**.

---

## 3. Autorizzazione a livello di URL (SecurityConfiguration)

Le regole vengono valutate **in ordine**: la prima che corrisponde vince. Lo schema comune ai tre progetti:

### Endpoint pubblici (permitAll) — comuni a tutti
- Risorse statiche Angular: `/index.html`, `/*.js`, `/*.css`, immagini, `/app/**`, `/i18n/**`, `/content/**`.
- Swagger UI: `/swagger-ui/**`.
- Autenticazione/registrazione: `/api/authenticate` (o `/api/authentication`), `/api/register`, `/api/activate`, `/api/account/reset-password/init`, `/api/account/reset-password/finish`.
- Health/info management: `/management/health`, `/management/info`, `/management/prometheus`.

### Endpoint riservati ADMIN — comuni a tutti
```java
.requestMatchers("/api/admin/**").hasAuthority(ADMIN)
.requestMatchers("/v3/api-docs/**").hasAuthority(ADMIN)
.requestMatchers("/management/**").hasAuthority(ADMIN)
```

### Tutto il resto → autenticato
```java
.requestMatchers("/api/**").authenticated()
```

### API pubbliche custom (specifiche dei progetti)

**RistoHub / RistoHubAlfa** — espongono un menu accessibile ai clienti tramite QR code **senza login**:
```java
.requestMatchers("/api/public/**").permitAll()
```
Servito dal controller `MenuPublicResource` (menu completo, allergeni, immagini menu). È l'unica vera apertura pubblica di dati di dominio.

**boardroom** — apre selettivamente alcuni endpoint di eventi/prenotazioni al pubblico o solo in lettura, con granularità per metodo HTTP:
```java
.requestMatchers(POST, "/api/eventis/*/prenotazione-email").permitAll()
.requestMatchers(GET,  "/api/eventis/pubblici").permitAll()
.requestMatchers(GET,  "/api/eventis").permitAll()
.requestMatchers(GET,  "/api/eventis/*").permitAll()
.requestMatchers("/api/sales/disponibili").permitAll()
// scrittura eventi/prenotazioni → solo autenticati
.requestMatchers(POST, "/api/eventis").authenticated()
.requestMatchers(PUT,  "/api/eventis/**").authenticated()
```
boardroom è l'unico che sfrutta il **matching per verbo HTTP** (GET pubblico, POST/PUT autenticato), un pattern più raffinato rispetto agli altri due.

---

## 4. Autorizzazione a livello di metodo (annotazioni)

Abilitata da `@EnableMethodSecurity(securedEnabled = true)` in tutti i progetti. Conteggio degli usi trovati:

| Annotazione | RistoHub | RistoHubAlfa | boardroom |
|-------------|:--------:|:------------:|:---------:|
| `@PreAuthorize("hasAuthority(ADMIN)")` | 6 | 5 | 21 |
| `@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")` | 4 | 4 | 4 |
| `@Secured(USER)` | 2 | 9 | 0 |
| `@PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")` | 1 | 0 | 0 |
| `@PreAuthorize("isAuthenticated()")` | 0 | 0 | 3 |
| `@PreAuthorize("permitAll()")` | 1 | 0 | 0 |

Osservazioni:
- **RistoHubAlfa** protegge molte risorse a livello di servizio/controller con `@Secured(ROLE_USER)` (9 usi) — approccio più capillare sui metodi.
- **boardroom** ha il maggior numero di vincoli ADMIN espliciti (21), a conferma di un modello "admin-centrico" con eccezioni mirate per l'owner.

### 4.1 Permessi basati su ownership (boardroom)

boardroom introduce l'unica logica di autorizzazione **realmente custom** dei tre progetti: la modifica di un evento non è più solo per admin, ma anche per il **proprietario** dell'evento. In `EventiResource`:

```java
@PutMapping("/{id}")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<EventiDTO> updateEventi(...) {
    boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.ADMIN);
    if (!isAdmin) {
        boolean isOwner = eventiService.isCurrentUserOwner(id);
        if (!isOwner) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // 403
        }
    }
    // ... procede con l'update
}
```

Stessa logica applicata a `PATCH`. Questo è il pattern corretto per **autorizzazione a livello di riga/istanza** (row-level security applicativa): il controllo del ruolo da solo non basta, serve verificare la proprietà della risorsa. L'admin bypassa il controllo, l'utente normale può agire solo sui propri dati.

---

## 5. Sicurezza lato frontend (Angular)

Il frontend **non è una barriera di sicurezza** (le API sono protette lato server), ma migliora l'esperienza utente nascondendo ciò a cui non si ha accesso.

### 5.1 Route Guard — `UserRouteAccessService`
Guardia `CanActivateFn` applicata alle rotte protette:
- Se l'utente non è autenticato → redirect a `/login` (salvando l'URL richiesto).
- Se è autenticato ma non ha le authority richieste (`route.data.authorities`) → redirect a `/accessdenied`.

### 5.2 Direttiva `*jhiHasAnyAuthority`
Presente in tutti e tre i progetti (`has-any-authority.directive.ts`): mostra/nasconde elementi della UI in base ai ruoli dell'utente (es. voci di menu admin).

### 5.3 Gestione del token / sessione lato client

**boardroom (JWT)** — `state-storage.service.ts`:
- Se **rememberMe** → token salvato in `localStorage` (persistente tra sessioni del browser).
- Altrimenti → `sessionStorage` (cancellato alla chiusura).
- `AuthInterceptor` aggiunge automaticamente l'header `Authorization: Bearer <token>` a ogni richiesta verso l'API.

⚠️ Salvare il JWT in `localStorage`/`sessionStorage` è la scelta standard di JHipster ma espone il token a furto via **XSS**. Le CSP configurate (vedi sotto) mitigano il rischio, ma è un punto da tenere presente.

**RistoHub / RistoHubAlfa (sessione)** — nessun token da gestire lato client: il cookie di sessione viaggia automaticamente; l'`auth-expired.interceptor` gestisce il redirect al login su `401`.

---

## 6. Header di sicurezza HTTP (tutti i progetti)

Configurati identicamente nel `SecurityFilterChain`:

- **Content-Security-Policy (CSP)** — da `jHipsterProperties.getSecurity().getContentSecurityPolicy()`, principale difesa contro XSS/injection.
- **X-Frame-Options: SAMEORIGIN** — protezione da clickjacking (`frameOptions.sameOrigin`).
- **Referrer-Policy: strict-origin-when-cross-origin**.
- **Permissions-Policy** — disabilita camera, geolocalizzazione, microfono, pagamento, ecc. (`camera=(), geolocation=(), microphone=(), payment=() …`).
- **CORS** abilitato (`cors(withDefaults())`), configurabile via `jhipster.cors` nei file YAML.

---

## 7. Confronto e valutazione di sicurezza

| Aspetto | RistoHub / RistoHubAlfa | boardroom |
|---------|-------------------------|-----------|
| Meccanismo auth | Sessione + cookie | JWT Bearer |
| Stato server | Stateful (sessione) | Stateless |
| Protezione CSRF | ✅ attiva (necessaria) | ❌ disabilitata (corretto con JWT) |
| Rischio XSS su credenziali | Basso (cookie httpOnly di sessione) | Medio (token in web storage) |
| Ownership / row-level | ❌ assente | ✅ presente (EventiResource) |
| Granularità per verbo HTTP | ❌ | ✅ |
| API pubbliche di dominio | `/api/public/**` (menu QR) | eventi in GET, prenotazioni email |
| Remember-me | Token persistiti su DB | JWT a validità estesa |

### Punti di forza (comuni)
- Password sempre con **BCrypt**.
- Doppio livello di autorizzazione (URL + metodo).
- Header di sicurezza completi (CSP, frame options, permissions policy).
- Endpoint di management/API-docs riservati agli admin.

### Punti di attenzione
1. **boardroom**: segreto JWT committato in `application-dev.yml`. Assicurarsi che in produzione sia iniettato da variabile d'ambiente/secret manager e ruotato regolarmente.
2. **boardroom**: JWT in `localStorage` → vulnerabile a XSS. Mantenere CSP restrittiva; valutare cookie httpOnly `SameSite` come alternativa.
3. **boardroom**: token valido 24h senza meccanismo di refresh/revoca. Un token rubato resta valido fino alla scadenza (limite intrinseco dei JWT stateless).
4. **RistoHub/Alfa**: verificare che `/api/public/**` esponga **solo** dati destinati al pubblico (menu) e nessun dato sensibile, poiché è completamente aperto.
5. Controllare in produzione la configurazione **CORS** (origini consentite) e che la console H2 sia disponibile solo in `dev`.
6. La logica di ownership è implementata solo su `Eventi` in boardroom: se altre entità richiedono lo stesso vincolo "solo i propri dati", va replicata (attenzione a IDOR — accesso a risorse altrui manipolando l'ID).

---

## 8. Riepilogo del flusso di autorizzazione (end-to-end)

1. **Login** → il server verifica le credenziali (`DomainUserDetailsService` + BCrypt) e carica le authorities dell'utente.
2. **Emissione credenziale** → cookie di sessione (RistoHub/Alfa) **oppure** JWT firmato con i claim `auth`/`user_id` (boardroom).
3. **Ogni richiesta** → il server identifica l'utente (cookie o header Bearer) e ricostruisce le sue authorities.
4. **Filtro URL** (`SecurityConfiguration`) → prima barriera: pubblico / autenticato / admin.
5. **Filtro metodo** (`@PreAuthorize` / `@Secured`) → seconda barriera sul singolo endpoint.
6. **Logica applicativa** (es. `isCurrentUserOwner`) → terza barriera opzionale per l'ownership.
7. **Frontend** → guardie e direttive adattano la UI, senza sostituire i controlli server.

> In sintesi: tutti e tre i progetti seguono fedelmente il modello di sicurezza standard di JHipster 8.11.0 con i ruoli `ADMIN`/`USER`. Le personalizzazioni rilevanti sono le **API pubbliche del menu** (RistoHub/Alfa) e l'**autorizzazione basata sulla proprietà della risorsa** in boardroom, che è l'implementazione più avanzata dal punto di vista dei permessi.
