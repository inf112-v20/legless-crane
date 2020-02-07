# Obligatorisk innlevering 1

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

##### Prioritert liste over krav i første iterasjon
- Fastsettelse av arkitektur for brettspill
- Anvende spillebrettet i LibGDX
- Brettet skal være synlig på skjerm
- Opprette en robot (robotobjekt)
- Plassere roboten på brettet
- Spillet må kjøre på forskjellige operativsystem

##### Videre i spillet:
- Jobbe videre med roboten, legge til oppførsel (liv, død, backup)
- Opprette programkort (verdi, bevegelses info)
- Styre robot ved hjelp av programkort
- Innføre runder (utdeling av kort, valg 9 av 5 programmeringskort i prioritert rekkefølge)
- Innføre faser (5 faser, 1 programkort per fase, oppdatering av backup)
- Restart ved backup ved død (kan dø 3 ganger før du er ute)
- Robot kan ikke gå gjennom vegger
- Hull -> dø -> restart
- Utenfor brettet -> dø -> restart
- Flere roboter (minst 2) - Alle skal få utdelt kort ved ny runde
- Flytte flere roboter samtidig
- Legge til “FactoryItems” (manipulasjon av oppførselen/tilstanden til spilleren):
- Flagg (3 stk, stigende rekkefølge, essensielt for å kunne oppnå seier)
- Gul og blå rullebane (beveger robot)
- Tannhjul (rotasjon)
- La robot bli påvirket av FactoryItems. 
- Innføre sammenhengen mellom skade og programkort i fasene/rundene
- Håndtering av konflikt i bevegelser
- Høy skade -> fastbrenning av programkort
- Opprydding i en fase. 
- Innføre laser på robot (inkludert retning). Roboter kan skade hverandre


### Deloppgave 3
Med tanke på prosjektmetodikk, så velger vi å satse på scrum som base, da spesielt med tanke på konseptene sprint, planlegging og retrospektiv teknikk. Eventuelt om vi prøver ut XP og kanban, og muligens litt parprogrammering for å oppnå mer erfaring der. For å kunne utvikle fungerende og veldokumentert programvare under prosjektet, har vi gjort oss tanker om metoder som kan hjelpe underveis i prosjektet. Vi vil forsøke å dokumentere det meste som ikke er opplagt, da også eventuelt konflikter/utfordringer underveis. Ingen av oss er vant til en slik form for gruppearbeid (helhetlig enighet i forbindelse med både prosjekt og kode), som vi da tenker spesielt kan by på en utfordring. Ellers vil vil sette fokus på beherskelse av git (hyppige commits) og god kommunikasjon. Vi tenker å aktivere varsler på slack (både pc/mobil) for generell planlegging og fagrelaterte saker.
  
Når det kommer til organiseringen av selve prosjektet, ser vi et behov for å møtest 1-2 ganger utenom den obligatoriske gruppetimen. Foreløpig utgangspunkt blir onsdager etter kl. 12:00. Formålet er å kunne holde lettere oversikt, kontinuitet og en «klarere» form for kommunikasjon. Som kommunikasjonskanal utenom gruppemøter satser vi på slack. Arbeidsfordelingen vil vi  jobbe mot skal være jevn, da vi alle ønsker å oppnå lik erfaring fra de ulike delene av prosjektet, samt kjenne på lik ansvarsfordeling. Når det kommer til oppfølging av prosjektet, tenker vi å kjøre sprint på 2 uker (på bakgrunn av 3 ukers mellomrom mellom innleveringer) og retrospektiv. For deling og oppbevaring av felles dokumenter, diagram og kodebase benytter vi GitHub (inkludert Wiki) og Google Docs.

  
### Deloppgave 4
Viser til github-repositoriumet som tilhører gruppen (legless-crane) i forbindelsen med den
delen av denne leveransen som skal være kode. Vi har kommet frem til følgende brukerhistorier og medfølgende arbeidsoppgaver:
#### Brukerhistorie 1: grafisk representering av brett
“Som spiller trenger jeg en visuell representasjon av brettet slik at jeg kan spille RoboRally uten å måtte huske hvordan brettet ser ut.”

##### Akseptansekriterier:
- Forventet størrelse og antall ruter
- Vise brettet uten elementer
- Vise brettet med elementer (spiller)
- Grafisk fremstilling stemmer overens med forretningslogikk
- Brettet har ruter tilsvarende posisjoner som en spiller kan plasseres på

##### Arbeidsoppgaver:
- Koble forretningslogikk opp mot render/spillmotor
- Opprette et robotobjekt (inkludert posisjon)
- Opprette rutenett som kan bestå av flere posisjoner

#### Brukerhistorie 2: robot på brettet
“Som spiller trenger jeg å se roboten min på brettet, slik at jeg kan strategere.”

##### Akseptansekriterier:
- Man får utdelt en robot i det man starter spillet
- Roboten må ha et basisutseende
- Roboten skal være synlig på brettet
- Roboten har forventet plassering på brett i forhold til posisjonsdata

##### Arbeidsoppgaver:
- Tildele robot-brikke til spiller av spillet (foreløpig bare en deltaker)
- Gi grafisk utseende til robot


#### Brukerhistorie 3: interaktiv robot
“Som spiller trenger jeg at roboten min kan bevege seg på brettet slik at min robot kan interagere med brettet og dets elementer.”

##### Akseptansekriterier:
- Spiller kan styre hvilken retning robot beveger seg i/hvor langt
- Robot kan bevege seg fra posisjon x1,y1 til x2,y2
- Roboten kan bevege seg opp, ned, til venstre og høyre
- Endringen i posisjon er representert grafisk

##### Arbeidsoppgaver
- Sette fast forretningslogikk for hvordan en robot påvirkes av elementer på brettet
- Styring av robot med piltaster (inntil programmeringskort er på plass)




#### Brukerhistorie 4: brukervennlighet
“Som eier av spillet trenger jeg at det funker på alle operativsystemer slik at så mange som mulig kan bruke spillet.”

##### Akseptansekriterier:
- Kjører på Windows
- Kjører på Mac
- Kjører på Linux

##### Arbeidsoppgaver
- Teste på macOS og linux at prosjektet bygger som det skal (utviklingen skjer på windows)

### Resterende
- Viser til Wiki for prosjekt-retrospektiv, dokumentasjon, møtereferater og UML
