# Obligatorisk innlevering 3

## Deloppgave 1
Møtereferat:
-   Alle referat ligger i prosjekt wiki, men pga dagens situasjon med korona så er det noen mangler da vi ikke fikk inn nye rutiner for referat ved digitale møter.

Roller har i stor grad vært udefinert og “fluid” men vi erfarer at dette er ineffektivt for oss. Planen videre blir derfor å sette tydligere roller fra og med i dag (26.03.20) og vi kommer til å rullere på disse hver fredag fremover mot siste innlevering.
Vi har bestemt å ha en teamlead, en testansvarlig og en referent som står ansvarlig for møtereferat og github dokumentajson. Siste team medlem vil stå til disposisjon for å støtte de faste rollene ved behov.

Første rotasjon 26.03-03.04.2020 blis som følgende:
-   Teamlead: Andreas
-   Testansvarlig: Hedda
-   Referent/ Dokumentasjonsansvarlig: Kjersti

Erfaringer verdt å merke:
-   Lange sprint har vært ineffektivt for oss og gjort oss sårbare for skippertak i slutten av sprinten. Vi må derfor konkludere med at vi har valgt feil prosjekmetodikk for oss. 
-   Vi kommer derfor å gå over til kanban med fokus på kontinuerlig flyt og oppdatering av prosjekttavle.

**Fullt retrospektiv finnes i prosjekt wiki.**

Forbedringspunkt fra retrospektivet:
-   Kontinuerlig arbeid med prosjektet for å unngå skippertak og bedre flyt.
-   Tettere oppfølging av hverandre, men teamlead for uken tar hovedansvar.

#### Prioritering av oppgaver fremover:
![Image](https://raw.githubusercontent.com/inf112-v20/legless-crane/master/Deliverables/pb.png?raw=true)

Gruppedynamikk og kommunikasjon:
-   Gruppen kommer generelt godt overens men vi har manglet tydelige roller og har hatt dårlig kommunikasjon utenfor møter. Dette er noe vi nå jobber målrettet med for å forbedre.


## Deloppgave 2
Krav vi har prioritert:
-   I denne perioden, som nå er over, har vi etter samtale med gruppeleder prioritert alt som var utestående med tanke på interaksjon mellom spiller og brett. Det meste av interaksjonsmuligheter er nå på plass bortsett fra detaljer for timing av hendelser og håndtering av konflikter. Dette vil bli prioritere fremover når vi ser på å få flere spillere på brettet.

### Brukerhistorer:
#### Brukerhistore #1: Programmere en robot (påbegynnelse av faser)
-   "Som spiller trenger jeg å kunne programmere en robot via programkort, for å kunne bevege den underveis i spillet og la den interagere med resten av miljøet på spillebrettet."
 
Akseptansekriterier:
-   Robot vises visuelt på spillebrettet
-   Det er opprettet programkort og en kortstokk av programkort (nye objekter)
-   Spiller kan intuitivt se og velge ut spesifikke programkort (i første omgang et utvalg av 9 tilfeldige kort) for å kunne programmere roboten (sette utvalgte kort over som “faser”)
-   Når fasene er bestemt, utfører roboten riktige bevegelser (som forventet/planlagt)

Arbeidsoppgaver:
-   Opprette objekter for programkort og kortstokk
-   La programkortene lagre en bevegelse (og etter hvert en prioritet)
-   Kortstokken må bestå av den spesifikke fordelingen av ulike, unike programkort
-   Dele ut 9 (endret etterhvert) tilfeldige programkort fra kortstokken synlig for spilleren
-   La spilleren velge ut 5 av de 9 kortene som faser. Fasene skal lagre bevegelser (String).
-   Koble visuelle «Programkort» med spillogikk fra «GameLogic»
    (overgang fra å lese bevegelse hos programkortene til at roboten beveger seg og interagerer med miljøet på spillebrettet.
    
#### Brukerhistorie #2: Legge til tester for funksjonalitet hos programkort (i første omgang knyttet til bevegelse)
-   "Som kunde trenger jeg å kunne teste at funksjonaliteten til programkortene fungerer, for å kunne spille spillet utfra forventede regler"

Akseptansekriterier:
-   Det er opprettet objekter for programkort og kortstokk
-   Metode i «GameScreen» som organiserer utdeling av tilfeldige programkort til en spiller
-   Spiller kan lagre et utvalg av utdelte programkort som faser (programmere roboten)
-   Når fasene er avgjort (“roboten er ferdig programmert for en runde”), så vil hver enkelt fase indikere en bevegelse som roboten kan utføre på spillebrettet (her kommer flere tester underveis). 

Arbeidsoppgaver:
-   teste kortstokkstørrelse
-   teste om sammensetning av prioritet og bevegelse stemmer overens
-   faser som ikke er programmerte består av en array av “default” programkort. 
-   overgang fra ledige faser -> programmere robot/velge 5 faser -> fasene leser forventet bevegelse (Kommer tester for kobling mellom bevegelse bestemt i GameScreen og GameLogic) 

#### Brukerhistorie #3: Formål: samle flagg i riktig rekkefølge
-   "Som spiller trenger jeg at flagg jeg besøker blir registrert i riktig rekkefølge, for å kunne oppnå seier i spillet"

Akseptansekriterier:
-   Det er flagg på brettet som kan leses utfra nummer (1-4)
-   Spiller kan se flaggene visuelt (med nummer)
-   Flagg lagres om det er i riktig rekkefølge
-   Et flagg spiller har besøkt blir ikke registrert igjen.
-   Et flagg hvor spiller ikke har besøkt det foregående vil ikke blir registrert.
-   Spillet avsluttes idet en spiller har besøkt alle flaggene i riktig rekkefølge (skjermovergang: WinScreen)

Arbeidsoppgaver:
-   Opprette metoder i «Player» som holder kontroll på lagring av flagg
-   Metode i «GameLogic» og “Player” som leser/lagrer flagg dersom riktig besøkt
-   Definere et vilkår for seier: en spiller har vunnet dersom alle flaggene er besøkt i riktig rekkefølge.

#### Brukerhistorie #4: Visuell HUD
-   "Som spiller trenger jeg å følge med på hvor mange flagg, liv og helsepoeng jeg har, for å aktivt ha kontroll på progresjonen underveis i spillet."

Akseptansekriterier:
-   Roboten har instansvariabler som liv, helsepoeng og flagg ved spillstart
-   Roboten kan interagere med andre objekter på spillbrettet: tape liv og helsepoeng, samt registrere flagg (senere også bli påvirket av medspillere)

Arbeidsoppgaver:
-   Instansvariabler hos roboten må vere på plass: liv, helsepoeng og flagg
-   Variablene må kunne påvirkes ved interaksjon på spillebrettet
-   Vise i «GameScreen» oppdatert hvor mange liv, helsepoeng og flagg roboten har underveis (hente oppdaterte verdier fra den aktuelle spilleren der og da)

#### Brukerhistorie #5: Prioritering til programkort
-   "Som utvikler trenger jeg at programkortene har en unik prioriteringsgrad slik at potensielle konflikter mellom spillere enkelt kan løses"

Akseptansekriterier:
-   Hvert programkort må ha en unik verdi
-   Verdien må være tilgjengelig for spillogikken for å håndtere konflikter

Arbeidsoppgaver:
-   Legg inn prioriteringsverdi i det kortene genereres
-   Gjøre verdien tilgjengelig via en get() metode

#### Brukerhistorie #6: Test av Player objekt
-   "Som utvikler trenger jeg å teste spillers oppførsel slik at vi er trygge på at spiller oppfører seg som forventet ved interaksjoner"

Akseptansekriterier:
-   Ingen runtime-error
-   Forutsigbar oppførsel fra spiller
-   Har 3 liv, hvert liv har 9 helsepoeng
-   Mister liv ved 10 eller mer i skade

Arbeidsoppgave:
-   Automatiske tester som tester at spiller opprettes som forventet
-   Automatiske tester som sjekker skade på spiller
-   Automatiske tester som sjekker at spiller mister liv

#### Brukerhistorie #7: Flere elementer på brettet
-   "Som spiller ønsker jeg mer dybde i spillet jeg spiller, gjennom at det er flere interaksjoner mellom min brikke og brettets elementer."

Akseptansekriterie:
-   Når spiller tar skade håndteres det rett - mer skade enn liv dreper spiller og spiller gjenoppstår
-   Spiller gjenoppstår på rett plass, denne plassen skal oppdateres automatisk om spiller går på skiftenøkkel
-   Spiller tar forventet skade av de forskjellige elementene på brettet.

Arbeidsoppgaver:
-   Legge til funksjonalitet til tannhjul som roterer spiller i en gitt retning.
-   Legge til funksjonalitet til samlebånd som flytter og noen ganger roterer spiller i en gitt retning.
-   Legge til funksjonalitet til skiftenøkkel som reparer spiller, og gir et nytt sted for spiller å gjenoppstå
-   Legge til funksjonalitet for statiske lasere, som skader spillere
-   Legge til funksjonalitet for hull som dreper spillere som går over det
-   Legge til funksjonalitet så spiller dør om han/hun går utenfor brettet

#### Brukerhistorie #8: Bugs i game logic
-   "Som spiller er det essensielt at spillet oppfører seg som forventet etter reglene som er satt, og ikke gjør uventede ting som et resultat av feil."

Akseptansekriterie:
-   Dette er et løpende problem som egentlig ikke er ferdig så lenge det er bugs som forårsaker at spillet krasjer, ikke bygger eller oppfører seg på en uventet måte

Arbeidsoppgaver:
-   issue #86, #91, #73 og andre bugs som dukker opp underveis

#### Brukerhistorie #9: Dokumentasjon i JavaDoc
-   "Som utvikler trenger jeg oversiktlig dokumentasjon for å lettere forstå hvilken kode som gjør hva i prosjektet."

Akseptansekriterie:
-   Hver metode som ikke gjør helt enkle operasjoner er dokumentert med JavaDoc stil.

Arbeidsoppgaver:
-   Dokumenter hver av klassene i prosjektet grundig.
-   Hold denne dokumentasjonen oppdatert etterhvert som koden utvikler seg.



Oppgaver fremover:
-   Vi har starter tildeling av issues som har frist på tirsdag (31.03) neste uke, men brukerhistoriene er ikke på plass per dags dato.

Hovedkrav vi anser som en del av MVP:
-   Det er ikke gjort noen endringer i forhold til hovedkrav som er satt ennå. Hovedkravene baserer seg fortsatt på de mest grunnleggende reglene i spillet.

Alle kjente bugs er dokumentert i README.md

## Deloppgave 3

Hvordan prosjektet bygger, testes og kjøres er dokumnetert i README.md. Der skal man også finne badges for Travis CI og Codacy.

#### Klassediagram: 

![Image](https://raw.githubusercontent.com/inf112-v20/legless-crane/master/Deliverables/uml.png?raw=true)

Commit-fordeling:
-   Fordelingen på commits er fortsatt for skjeiv og på dette tidspunktet vil nok ikke den overordnede fordelingen over hele prosjekte bli jevn, men vi jobber mot jevnere fordeling av fremtidige commits. Slik at commits i de kommende ukene vil være mye jevnere.
-   I denne innleveringen har vi også blant annet benyttet oss av noe gruppeprogrammering (dokumnetert i wiki referat) for å få jevnere deltagelse men dette reflekteres ikke direkte i oversikten over commits på gitHub.
