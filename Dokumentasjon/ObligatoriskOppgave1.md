# Obligatorisk innlevering 1
Utkast, 29.01. 
### Deloppgave 1
Som navn på gruppe/repositorium har vi valgt å foreløpig beholde “legless-crane”. 
Av tidligere kompetanse stiller gruppemedlemmene forholdsvis likt, da hovedsakelig med erfaring fra tidligere inf-emner i studieprogrammene. 
Vi ønsker å rullere på rollene underveis i prosjektet, og velger å starte med et tilfeldig utgangspunkt. 
Sindre starter som “teamlead”, Andreas som kundekontakt, Kjersti som bookingansvarlig og Hedda som sekretær. 
Til “project-board” velger vi GitHub (for å ikke spre oss over flere plattformer enn nødvendig).

### Deloppgave 2
Det overordnede målet for applikasjonen RoboRally er å skape et spill som er plattformuavhengig, gøy og lett å bruke. 
Vi vil jobbe mot å møte de kravene som kunden stiller, der vil skal ha de mest grunnleggende spillfunksjonene på plass (som å kunne opprette spiller, interagere med elementer på spillebrettet og ha anledning til å vinne eller tape). 
Grafisk framstilling er også noe som vi må jobbe mot, der vi alle har lite erfaring. 

I forbindelse med høynivåkravene som er gjennomgått i forelesning, så har vi ved nåværende tidspunkt kommet frem til følgende liste:

- ##### Prioritert liste over krav i første iterasjon
- Fastsettelse av arkitektur for brettspill
- Anvende spillebrettet i LibGDX
- Brettet skal være synlig på skjerm
- Opprette en robot (robotobjekt)
- Plassere roboten på brettet
- Spillet må kjøre på forskjellige operativsystem

- ##### Videre i spillet:
- Jobbe videre med roboten, legge til oppførsel(bevegelse, liv, død, backup)
- Opprette programkort (verdi, bevegelses info)
- Styre robotobjekt ved hjelp av programkort
- Innføre runder(utdeling av kort, valg 9 av 5 programmeringskort i prioritert rekkefølge)
- Innføre faser (5 faser, 1 programkort per fase, oppdatering av backup)
- Restart ved backup ved død (kan dø 3 ganger før du er ute)
- Hull -> dø -> restart
- Utenfor brettet -> dø -> restart
- Flere roboter(minst 2)
- Flytte flere roboter samtidig
- Legge til “FactoryItems” (manipulasjon av oppførselen/tilstanden til spilleren):
- Flagg (3 stk, stigende rekkefølge, essensielt for å kunne oppnå seier)
- Gul og blå rullebane (beveger robot)
- Tannhjul (rotasjon)
- La robot bli påvirket av FactoryItems. 
- Innføre sammenhengen mellom skade og programkort i fasene/rundene? 
- Håndtering av konflikt i bevegelser
- Høy skade -> fastbrenning av programkort -> færre kort utdelt neste runde
- Opprydding i en fase. 
- Innføre laser på robot (inkludert retning). Roboter kan skade hverandre.


### Deloppgave 3
- ##### Prosjektmetodikk: 
  Vi velger å satse på scrum som base, da spesielt med tanke på konseptene sprint, planlegging og retrospektiv     
  teknikk. Eventuelt bruk/utprøving av XP og kanban. Muligens litt parprogrammering (for å oppnå mer erfaring der).

- ##### Hjelpemetoder til prosjektarbeidet:
  Vi ønsker å dokumentere det meste som ikke er opplagt, da også eventuelt konflikter/utfordringer underveis. Ingen av oss er vant til en slik form for 
  gruppearbeid (helhetlig enighet i forbindelse med både prosjekt og kode), som vi da tenker spesielt kan by på en utfordring. Ellers vil vil sette fokus på beherskelse av git (hyppige commits) og god kommunikasjon. Vi tenker å aktivere varsler på slack, eventuelt også på mobil (for generell planlegging, fagrelaterte saker). 

- ##### Organisering av prosjektutførelse: 
  Vi ser et behov for å møtest 1-2 ganger utenom den obligatoriske gruppetimen. Foreløpig utgangspunkt blir onsdager etter kl. 12:00.
  Dette er for å holde lettere oversikt, kontinuitet og en «klarere» form for kommunikasjon. Som kommunikasjonskanal utenom gruppemøter satser vi på slack. Arbeidsfordelingen skal være jevn, der vi alle ønsker å oppnå lik erfaring fra de ulike delene av prosjektet, samt 
  ha lik ansvarsfordeling. Når det kommer til oppfølging av prosjektet, tenker vi å kjøre sprint på 2 uker (på bakgrunn av 3 ukers
  mellomrom mellom innleveringer) og retrospektiv. For deling og oppbevaring av felles dokumenter, diagram og kodebase benytter vi 
  GitHub. 
  
### Deloppgave 4
- Kode, brukerhistorier


