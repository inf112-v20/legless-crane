# Obligatorisk innlevering 4

## Deloppgave 1 - Team og prosjekt
**Møtereferat:**
-   Alle møtereferat ligger i prosjektets wiki på gitHub. Det er noen mulige hull i referatene rundt nedstengingen av UiB pga manglende rutiner når møtene gikk over til digitale plattformer.

-   Det har også vært en del uformelle "møter" over facebook messenger og slack for gruppediskusjoner og statusoppdateringer som ikke har referat da de ikke ble ansett som reelle møter.

**Roller i teamet:**
-   Rollene i teamet har vært veldig udefinerte, til tross for et forsøk på å gjenopprette rolle-struktur etter 3 innleveringen. 

-   Uken før 3. innlevering var planen å starte å sette tydelige roller og rotere de ukentlig. Dette ble påbegynt,  tydlig beskrevet i ObligatoriskOppgave3.md, men dette falt gjennom pga manglende oppfølging av teamet generelt. Resultat er at vi i stor grad har jobbet selvstendig opp mot project board på gitHub og alle har valgt issues selv etterhvert som vi har jobbet med prosjektet.

**Oppdatering av roller:** Nei
-   Det har virket nokså greit å jobbe selvstendig nå som alle vet hva som gjenstår i prosjektet, så fram til denne sprinten er ferdig ser vi ikke noe behov for å endre på rollene. Det ville hatt liten effekt.

**Erfaringer om team og prosjektmetodikk, og valg i forholdt til dette:**
-   Prioriteringer som har blitt gjort med tanke på issues har stort sett vært veldig gode, kanskje med unntak av multiplayer som burde vært sett på for en sprint eller to siden og lagt opp til.

-   Valg med tanke på prosjektmetodikk bærer preg av at vi hadde for liten forståelse og erfaring av forskjellige prosjektmetodikker og deres styrker ved starten av prosjektet. Hadde vi satt på kunnskapen vi har i dag hadde vi nok prioritert annerledes der vi ser forbedringspotensiale.

-   Valg med tanke på teamroller har ikke alle vært like fornuftige. Fra første sprint hadde vi roller bare for å ha roller, som bookingansvarlig. Noe ansvar kunne fordeles på alle uten at det ble for mye å gjøre for enkelte.

-   Vi har ikke vært flinke nok til å holde oss til rollene vi valgte, og har sett at det for prosjektet sin del hadde vært mye bedre om vi satte en relevant rolle for hele prosjektet og ikke enkelte sprinter.

-   Dette hadde nok vært mindre hensiktsmessig med tanke på hva vi skal lære i faget, men av erfaring sånn vår gruppedynamikk har utviklet seg så tror vi at klare roller som ikke rullerte hadde gitt best resultat. Kanskje også det som er mest virkelighetsnært sammenlignet med arbeidsmarkedet ellers.

**Retrospektivt, hvordan det har gått:** 
-   Generelt er vi fornøyd med fremgangen vi har hatt på prosjektet, til tross for alle utfordringene vi har møtt på underveis.

**Retrospektivt, hva vi ville gjort annerledes:** 
-   Mer fornuftig valg av prosjektmetodikk som nevnt over, faste roller om det var opp til oss å velge. Korte sprinter og mer fortløpende oppfølging av prosjektet for å sikre fast og jevn framgang.

-   Vi hadde nok gjennomgått prosjektet sammen i starten, satt opp issues og planlegge bedre for strukturen av prosjektet. Det var mye som var ukjent i starten og som vi egentlig kunne resonnert oss fram til eller planlagt for. Og dermed unngått å måtte skrive om så mye kode.
-   Parprogrammering ser vi absolutt fordelene med, og skulle ønske vi hadde fått til bedre. Spesielt i starten av prosjektet burde vi brukt tid på dette så alle var på samme side. Hadde vi visst at det ble så utfordrende å avtale tidspunkt som passet for flere utenom gruppemøter hadde vi nok også pushet mer på dette. Lett å si “vi tar en parprogrammeringsøkt til uken” og så ikke finne tid til det litt ut i uken. Generelt sett dårlig planlegging som har skapt problemer her.

## Oppdatert prosjekttavle (8. mai):
![Image](https://raw.githubusercontent.com/inf112-v20/legless-crane/master/Deliverables/projectBoard.png?raw=true)

**Gruppedynamikk og kummunikasjon, i starten VS nå:**
-   Vi hadde et greit utgangspunkt som en gruppe. Det var grei gruppedynamikk og kommunikasjon, men vi ser nå at den dynamikken var mer egnet for sosiale settinger ikke nødvendigvis som et team på et prosjekt. Nå mot slutten har vi tidvis hatt litt for sporadisk kommunikasjon men den kommunikasjonen som har vært har vært mye mer rett frem og effektiv i forhold til arbeidet på prosjektet. Det må nevnes at det er vanskelig å se hva som har endret seg fordi vi har blitt vant til å jobbe med hverandre og hva som har endret deg kun fordi vi har vært i denne korona påvirkede situasjonen.

**Karantene og nedstengning, hvordan ble teamet og fremdriften påvirket?**
-   Det ble en markant nedgang i produktivitet etter nedstenging. I vårt team er noen av de største grunnene til denne nedgangen følgende:
    -   Hverdagen blesnudd på hodet og det har vært vanskelig å finne en ny normal.
    -   Arbeidsplassen har endrett seg, og ikke for det bedre. Det er vanskelig å være produktiv på et sted du vanligvis bruker til fritid og/eller søvn. Vi har merket fraværet av en fysisk lesesal hvor vi kan jobbe blant medstudenter og et designert sted som er ment for arbeid.  
    -   Fysisk avstand hadde også en negativ effekt på kommunikasjonen vår da den ble mye mer sporadisk etter nedstengningen. Men her har nok også den generelle paotiske tilstanden også spilt inn.
    
-   Alle endringene endte i en mye lavere arbeidsmoral og produktivitet generelt, ikke bare på dette prosjektet men i alle fag.

## Deloppgave 2 - Krav
**Hva som er gjort siden forrige gang:**
-   “Game-loop”
-   Flere spillere på brettet (AI)
-   Interaksjon på brettet: lasere på brettet er aktivert
-   Interaksjon på brettet: roboter kan pushe hverandre/ en robot per rute (bug: respawn for flere spillere på samme rute)
-   Interaksjon på brettet: skyte laser fra spiller (bug: roboten som blir skutt er grafisk kammuflert en fase)
-   Interaksjon på brettet: flagg som skiftenøkkel
-   Omprioritere kort
-   En kortstokk per runde (spillere får utdelt ulike/unike programkort før en runde)
-   Bevegelsene til robotene i en fase avgjøres av prioritet på kortene
-   Utdelte kort samsvarer med helsepoeng
-   Låste kort

**Påbegynt:**
-   Respawn (riktig helsetap og skjer på korrekt tidspunkt, men det gjenstår håndtering av flere spillere på samme rute)
-   Multiplayer

### Brukerhistorer
#### Brukerhistore #4.1: Game-loop
-   "Som spiller trenger jeg å kunne spille «runde for runde», så sant ingen spillere enten har vunnet eller tapt"

Akseptansekriterier:
-   Det er opprettet funksjonalitet for når en spiller vinner/taper (spillet stopper)
-   Spillere kan programmere robotene sine før en runde starter (ferdige bevegelser)
-   spillet går gjennom fasene i rekkefølgen som definert i regelbok

Arbeidsoppgaver:
-   Opprette en “løkke” for funksjonaliteten som skal inngå i løpet av en runde. Ikke gå til neste steg før nåværende steg er fullført. 
-   Ved endt løkke: justere programkort, verdier og vent på en clickListener i GameScreen (en spiller har programmert roboten sin på nytt) før en ny runde startes 
-   Representere hver fase i spillet med sin logikkblokk

#### Brukerhistorie #4.2: AI
-   "Som spiller ønsker jeg å kunne spille mot datamaskinen om jeg ikke har noen venner å spille med :("

Akseptansekriterier:
-   AI bør ta noe fornuftige valg og planlegge hvilke kort de bruker til en viss grad
-   Helst kunne velge mellom vanskelighetsgrad i menyen før start.
-   Dum -> tilfeldige valg av kort
-   Middels -> Velge kortene som får den nærmest neste flagg
-   Smart -> Velge kortene som får den nærmest neste flagg, og ta høyde for elementer på brettet.

Arbeidsoppgaver:
-   Dum AI kan velge tilfeldig, bør gå fint å implementere

-   For middels og smart kan man lagre alle gyldige posisjoner i en weighted graph.
    -   Vegger kan representeres ved at det ikke er en sammenkobling mellom to tiles/vertices
    -   Hull kan fjerne alle kanter som leder til de så AI ikke forsøker å gå over de
    -   Enkelte elementer kan vektlegges mer enn tomme plasser på brettet, så AI vil unngå de på middels
    -   Smart burde egentlig ta høyde for hvor et samlebånd f.eks. vil flytte dem etter fasen.

#### Brukerhistorie #4.3: Lasere på brettet
-   "Som spiller trenger jeg at roboten kan skyte laser i retninger den er stilt, slik at andre roboten kan skades"

Akseptansekriterier:
-   Det er flere spillere (flere roboter på brettet)
-   Robotene har helsepoeng som kan endres 
-   Laserskytingen skal skje etter at robotene har utført de bevegelsene de har for en gitt fase

Arbeidsoppgaver:
-   Implementere en metode som for enhver robot sjekker rute for rute i retningen den ser, og som da sjekker om en laser kan gå der (innenfor brettet og ikke en vegg) samt om det er en spiller der som kan miste et helsepoeng. 

#### Brukerhistorie #4.4: En spiller per rute
-   "Som spiller ønsker jeg at det kun står en spiller per rute på brettet dersom en spiller beveger seg inn i en rute hvor en annen spiller befinner seg."

Akseptansekriterier:
-   Roboten vil dytte en annen robot i bevegelsesretningen sin dersom en annen robot er på en rute som roboten din beveger seg til.
-   Flere roboter kan bli dyttet på en gang, men en robot kan ikke bli dyttet gjennom vegger.
-   Roboten kan dytte andre roboter utenfor brettet.

Arbeidsoppgaver:
-   GameLogic: Oppdatere validMove med dytting av spillere som funksjonalitet.

#### Brukerhistorie #4.5: Flagg som skiftenøkkel
-   “Som spiller trenger jeg at flagg jeg lander på, uansett rekkefølge, oppfører seg som en skiftenøkkel som lagrer nytt backup punkt og gir tilbake et helsepoeng”

Akseptansekriterier:
-   Skiftenøkkel funksjonaliteten gjelder uansett om flagget ikke er i riktig rekkefølge for å plukkes opp/ markeres besøkt
-   Spiller må stå på flagget ved slutten av en fase
-   Flagget blir nytt backup punkt
-   Flagget fjerner en i skade fra spiller som stopper der i slutten av en fase

Arbeidsoppgaver:
-   Oppdater CLEANUP slik at flagg gir ny backup og gir tilbake et helsepoeng

#### Brukerhistorie #4.6: Omprioritere kort
-   "Som spiller trenger jeg valgmuligheten til å kunne endre (angre) på programkortene før en runde starter"

Akseptansekriterier:
-   På skjermen er det visuelle kort som kan klikkes på
-   Et kort i GameScreen (clickListener) utløser en metode i GameLogic.
-   Når alle faser har et «gyldig programkort» (ikke default-verdi), så skal en boolean metode utløses (som igjen gjør at spilleren kan starte runden)
-   Så lenge fasene ikke er ferdig programmert, eller at en spiller angrer på et valgt kort, skal en boolean-verdi være falsk (en runde kan ikke startes)

Arbeidsoppgaver:
-   GameLogic og GameScreen skal ha samkjørende kode (visuelt representert i GameScreen og logisk representert i GameLogic).
-   GameLogic: Etter at et gitt kort blir trykket på i GameScreen, så skal en metode i GameLogic holde kontroll på antall ledige faser, hvilken fase man er på, øke faser riktig og holde kontroll på faser som blir angret på (vha. en stack).
-   GameScreen: kort som trykkes på skal bli dratt visuelt enten til en ledig fase eller tilbake til utgangspunktposisjon.

#### Brukerhistorie #4.7: En kortstokk per runde 
-   "Som spiller trenger jeg å få utdelt unike programkort (dvs. unik prioritet), slik at bevegelsene til robotene kan bli utført korrekt (rangert etter prioritet)."

Akseptansekriterier:
-   Det er innført funksjonalitet for runder/faser    
-   Det er en kortstokk fylt med unike programkort (ingen kort har like verdier)
-   Kortstokken representeres både visuelt og logisk
-   Spillerne får utdelt ulike kort av samme kortstokk (ingen spillere får tilgang til like «listeindekser») før en runde.

Arbeidsoppgaver:
-   Grafisk: lage nye programkort som viser unik prioritet
-   Logisk: opprette en liste som har alle listeindeksene til kortstokken. Stokk om på denne listen. La hver spiller få utdelt listeindekser fra ulike utgangspunkt av denne listen (9 listeindekser mellom hver spiller). Ikke forny listen (stokk om) før en hel runde er fullført (slik at alle spillere får utdelt kort fra identisk kortstokk/liste).

#### Brukerhistorie #4.8: Sortere bevegelser i en fase basert på prioritet
-   "Som spiller trenger jeg at hver robot utfører bevegelser i henhold til prioriteten på kortene. For balanse i spillet og at ingen bevegelser “skjer samtidig”

Akseptansekriterier:
-   Det er flere roboter på brettet
-   Alle robotene har gyldige bevegelser for alle fasene (er programmert) før en runde starter

Arbeidsoppgaver:
-   Sortere bevegelsene som blir lest ut fra en liste basert på prioritet. Sørge for å sortere riktig både bevegelsene og rekkefølgen av spillere. 

#### Brukerhistorie #4.9: Utdelte kort samsvarer med helsepoeng
-   "Som spiller trenger jeg å få utdelt kort i starten av en runde tilsvarende de helsepoengene roboten har etter forrige runde"

Akseptansekriterier:
-   Roboten interagerer korrekt med andre elementer på brettet slik at den mister helsepoeng som forventet dersom bevegelsene/elementene indikerer det
-   Antall kort en spiller kan velge mellom i starten av en runde avgjøres direkte av helsepoengene der og da

Arbeidsoppgaver:
-   I metoden der en spiller får utdelt kort (listeindekser), så må antall kort som blir utdelt tilsvare antall helsepoeng spilleren har.
-   Det samme må implementeres for AI’er.

#### Brukerhistorie #4.10: Låste kort 
-   "Som spiller trenger jeg at kort låses fra forrige runde dersom roboten har mindre enn 5 helsepoeng i det runden avsluttes, slik at jeg fremdeles kan utføre 5 faser til tross for få helsepoeng (utdelte kort)"

Akseptansekriterier:
-   Roboten får utdelt kort tilsvarende helsepoeng
-   Roboten sine kort fjernes ikke helt etter en runde dersom helsepoengene tilsvarer mindre enn 5 (helsepoeng og antall kort som fjernes samsvarer)
-   Visuell representasjon av fastlåste kort

Arbeidsoppgaver:
-   GameLogic: for AI’er og spilleren må det vere en metode som etter hver runde som holder kontroll på hvilke kort som skal lagres til neste runde.
-   Kortene skal lagres fra 5. fase og nedover, utfra helsepoeng som er igjen.
-   GameScreen: etter at en runde avsluttes i GameLogic, blir en metode kalt i GameScreen. Denne skal vise fastlåste kort (kort fra forrige omgang fra riktige faser, synkende rekkefølge) som ikke kan klikkes på, bare fjernes.
-   Ved en ny runde skal de fastlåste kortene telle som gydlige kort for den aktuelle runden, og ikke kunne byttes ut. 

#### Brukerhistorie #4.11: spiller skyter laser
-   "som spiller trenger jeg at brikken min kan skyte laser en gang i slutten av en fase i retningen den ser, for å kunne gi skade til medspillere"

Akseptansekriterier:
-   Laseren avfyres i slutten av hver fase etter at alle spillere har flyttet seg i henhold til
-   Laser skytes fra spillers posisjon
-   Laser skytes i samme retning som spiller
-   Laser stopper når den har truffet en annen spiller eller en vegg
-   Laseren tar et helsepoeng fra spillere som blir truffet

Arbeidsoppgaver:
-   GameLogic, ny case i utdateGameState() som behandler spillers laser
-   Sjekk plassene foran spiller om det finnes en annen spiller før brettet slutter eller laser blir stoppet av en vegg etc.

#### Brukerhistorie #?: respawn (in progress)
-   "Som spiller ønsker jeg å kunne respawne ved backup punktet mitt i henhold til reglene."

Akseptansekriterier:
-   Spiller gjenoppstår på punktet ved slutten av runden
-   Spiller blir bedt om å velge retning før de gjenoppstår
-   Spiller starter med makshelse-2
-   Spiller blir spurt om de vil sette i gang powerdown med én gang
-   Om flere spillere gjenoppstår i samme runde vil den som døde først starte på punktet, og de andre rundt på tur.

Arbeidsoppgaver:
-   Skrive om dagens død og respawn funksjon til å ikke respawne øyeblikkelig, sette respawn i CLEANUP delen av gameloop
-   Gi prompt til spiller som spør hvilken retning (rotasjon) spilleren skal stå i
-   La spiller velge om de ønsker å gå i powerdown eller ikke. (kan egentlig bare spørre om dette for alle spillere etter respawning har blitt gjort?)
-   Håndtere konflikter i respawn i samme rekkefølge som spillerne døde. (kan lagre spillertall til de som dør gradvis i løpet av en runde i arraylist

#### Brukerhistorie #?: Multiplayer (in progress)
-   "Som spiller ønsker jeg å kunne spille mot andre mennesker, for dette må jeg kunne koble til med en gitt ipadresse, eller kunne velge fra en liste av tilgjengelige spill på mitt nettverk."

Akseptansekriterier:
-   Spillere får koblet sammen over LAN med ipadresser
-   Hver spiller får bare opp sine kort
-   Utenom HUD bør skjermen vise akkurat det samme til samme tid

Arbeidsoppgaver
-   Implementere nettverk over LAN med client-server struktur
-   Sørge for peer-to-peer funksjonalitet?
-   Ta i bruk Hamachi eller lignende tredjepartsløsning for å koble til et virtuelt LAN
-   Sørge for at hver spiller kun får relevant info for deres del av spillet (ikke andres kort osv..)

#### Brukerhistorie #?: Powerdown (denne var planlagt, men ikke påbegynt)
-   "Som spiller ønsker jeg å kunne velge selv å gå i powerdown, med andre ord vil jeg kunne reparere brikken min mot å ikke kunne bevege meg en runde."

Akseptansekriterier:
-   Både spiller og AI kan kunne velge å gå i powerdown
-   Etter gjennomført powerdown vil spiller og AI kunne velge å fortsette med powerdown til neste runde også, eller å motta kort

Arbeidsoppgaver:
-   Implementere prompt i CLEANUP til spillere med helse under maks så de kan velge å gå i powerdown eller ikke
-   AI kan velge tilfeldig om de er skadet (sjanse proporsjonalt til hvor skadet de er?)

**MVP**
**Hvilke hovedkrav vi anser som MVP:**
-   Vi har i stor grad satt de samme hovedkravene for MVP som de gitt fra kunde.

**Har vi endret prioritering på MVP?** Nei
-   Men, burde prioritert utfordrende oppgaver som Multiplayer tidligere i MVP for å få en bedre forståelse for hvordan koden må legges opp for å gi oss minst problemer senere. Skulle det bli implementert nå vil det medføre en del omskriving og mye arbeid fra det vi kan se.

**Kjente bugs:**
-   Bugs skal være nevnt på README.md filen på prosjektets forside her under “known bugs”:  <https://github.com/inf112-v20/legless-crane>

## Deloppgave 3 - Produktleveranse og kodekvalitet
Hvordan prosjektet bygger, testes og kjøres er dokumnetert i README.md. Der skal man også finne badges for Travis CI og Codacy.

### Notat på bruke av Junit5 i prosjektet
-   Junit5 gir error ved forsøk på å bruke AssertTrue()/AssertFalse() - det er mest sansynlig noe i pom.xml som ikke stemmer. Vi har valgt å unngå disse assert metodene i testene våre, da vi ikke oppdaget feilen i pom.xml før innlevering

## Klassediagram
![Image](https://raw.githubusercontent.com/inf112-v20/legless-crane/master/Deliverables/umlOblig4.png?raw=true)

