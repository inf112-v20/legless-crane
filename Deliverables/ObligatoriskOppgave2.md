# Obligatorisk innlevering 2

## Deloppgave 1
Vi fungerer relativt greit i rollene vi satte første sprint. Kommer til å rotere på roller ved neste sprint og sannsynligvis de følgende etter det igjen. For denne sprinten endte vi opp i en slags overgangsfase hvor rollene ble litt mindre definerte.

Dette er det de ulike rollene innebar i første spirnt og til dels i andre sprint:
-   Teamleder: lede teamet ved å prøve å sette mål for de ulike sprintene, forsøke å spre arbeidsoppgaver og få alle til å ta ansvar for én del av sprinten.
-   Kundekontakt: overordnet ansvar for kommunikasjon mellom gruppen og kunde (gruppeleder)
-   Bookingansvarlig: booke grupperom for møter/arbeid slik at teamet har er greit sted å jobbe sammen.
-   Sekretær: utforme referat, overordnet ansvar for å formulere den obligatorisk oppgaven

Vi har sett at det blir aktuelt å sette en av oss som ansvarlig for testing. Som da sørger for at vi tester prosjektet tilstrekkelig, og at vi tester relevante deler av prosjektet. Alle har ansvar for at testene blir gjennomført ved commit, og helst består. Da vi får inn en ny rolle ser for oss at Bookingasvarlig frafaller, og hvor det da blir gruppens felles ansvar at vi har et sted å jobbe ved møter/parprogrammering.

Våre erfaringer med tanke på teamet har to hovedpunkter.
Sindre sto for en uproporsjonal andel av commits og kode i første sprint, det er en enighet om at dette ansvaret burde ha fordelt seg mer tidligere slik at enkelte ikke henger etter i endringene som har blitt gjort.
For å hjelpe med dette avtaler vi tider for samarbeid og parprogrammering i løpet av denne sprinten slik at alle ser noe av koden som vi implementerer og selvsagt også bidrar til den.
Prosjektmetodikk har vært relativ grei så langt, vi velger å vektlegge mer tid på parprogrammering enn vi gjorde i første sprint, og vi ønsker også å få på plass mer relevante tester. Så litt mer fokus på flere og bedre enhetstester hvor mulig, og manuelle tester hvor ikke (se retorspektiv i wiki for oppdatering på hvordan dette har gått.)

Både gruppedynamikk og kommunikasjon fungerer ganske godt, det er lav terskel for å bidra i gruppetimer og i våre møter utenfor onsdagen om man har innspill, spørsmål eller tanker til prosjektet. Her deltar alle aktivt, hvor sekretær og teamleder har sørget for at vi holder oss konkrete til sprinten, og ikke for generelt til prosjektet.

Når det gjelder prosjektstruktur endte vi opp med å planlegge mye i første sprint i fellesskap og ble enige om en plan, som nevnt tidligere ble det til at Sindre sto for alt for mye av implementasjonen av disse planene og commits til prosjektet generelt. 
Referat fra møter ligger i github prosjektet vårt under Wiki -> Møtereferater

Forbedringspunkter:
-   Jevne ut fordelingen av commits fra alle på gruppen (hovedproblemet til gruppen for nå)
-   Vi skal sette av tid til parprogrammering som et virkemiddel for å hjelpe med første punkt.
-   Revurdere strukturen i prosjektet vårt så langt, lener oss nok mer på libGDX enn vi originalt hadde tenkt.
-   Jobbe en del med prosjekttavlen, fordele issues, rydde i issues vi har liggende på tavlen og annet arbeid som trengs.

## Deloppgave 2
#### Brukerhistorier
##### Brukerhistorie #2.1: Spillet må kunne bygge
-   “Som kunde trenger jeg at applikasjonen jeg har bestilt lar seg bygge, det bør ikke være nødvendig å kjøre fra en IDE”

Akseptansekriterier:
-   Skal kunne vite hvordan man bygger applikasjonen
-   Pom.xml innholdere elementene som kreves for en rikitg og komplett Build
-   Dette skal være oversiktlig forklart i github(readme.md)

Arbeidsoppgaver:
-   Oppdater Pom.xml
-   Dokumentere prosjektet bedre i readme.md fra forsiden

##### Brukerhistorie #2.2: Spillet må kunne kjøre
-   “Som kunde trenger jeg å kunne kjøre applikasjonen jeg har spilt”

Akseptansekriterier:
-   Krasjer ikke ved oppstart
-   Krasjer ikke mens det kjører

Arbeidsoppgaver:
-   Utføre manuelle og automatiske tester av spillet, sørge for at det kjører relativt problemfritt.

##### Brukerhistorie #2.3: Spillet bør ha tester
-   “Som utvikler trenger jeg å kunne teste at oppførselen er som forventet ”

Akseptansekriterier:
-   Spillet ikke har runtime-error
-   Forutsigbar oppførsel fra kode

Arbeidsoppgaver:
-   Opprette manuelle og/eller automatiske tester som sjekker at spillets kode oppfører seg som forventet.

##### Brukerhistorie #2.4: Man bør kunne bevege brikke via GUI
-   “Som spiller trenger jeg å kunne velge bevegelsen som roboten skal gjøre på skjermen via knapper fremfor keyboard, for å senere kunne programmere roboten via programkort.”

Akseptansekriterier:
-   Spillebrettet (tmx-fil) og robot må kunne vises/være interaktive i “GameScreen”.
-   Spiller må kunne bevege seg via knapper fremfor keyboard

Arbeidsoppgaver:
-   Overføre spillebrett og robot fra “Renderer”-klassen til “GameScreen”-klassen.
-   Sette seg inn i hvordan man går fra “KeyListener”-input til “Button”-input
-   Implementere/eksperimentere med overgang

##### Brukerhistorie #2.5: Knapper/kort bør ikke overlappe kartet
-   “Som spiller må jeg kunne se hele kartet, det bør ikke blokkeres av knapper fra grensesnittet”

Akseptansekriterier:
-   GUI elementer som knapper bør ikke overlappe
-   Bør se relativt ryddig ut ved siden av spillerkartet

Arbeidsoppgaver:
-   Dele GameScreen inn i to felt, et for brett og et for styringssenter
-   Undersøke muligheter for å få utvidet det som renderes for spiller, for å få plass til knapper utenfor kartet.

##### Brukerhistorie #2.6: Spillet bør ha runder og faser (neste sprint)
-   “Som spiller ønsker jeg å kunne se en meny før jeg begynner selve spillet, slik at jeg har mulighet til å navigere mellom ulike stadier av et spill (meny, start, avslutt).”

Akseptansekriterier:
-   Implementere forskjellige skjermer som meny og spillskjerm
-   Implementere overgang mellom skjermer via knapper
-   Trekke “Renderer”-klassen vekk fra spillogikk.

Arbeidsoppgaver:
-   Sette seg inn i konsepter om skjermer/hvordan håndtere overganger.
-   La “Renderer”-klassen ha et overordnet ansvar for å kjøre de ulike skjermene
-   Implementere/eksperimentere med kode

### Resterende
Vi har prioritert oppgaver som er relatert til å slå sammen spillogikk og brukergrensesnitt. Å få en prototype av spillet til å fungere etter de kravene vi har satt for denne og forrige sprint. 

Hva ser vi i ettertid? At vi muligens må omstille oss når det kommer til måten vi skal bygge opp spillet. Nytte oss mer av konseptene som allerede er tilgjengelige i LibGDX. 

Det er ikke gjort noen endringer i forhold til hovedkrav som er satt ennå. Hovedkravene baserer seg fortsatt på de mest grunnleggende reglene i spillet.

I ettertid har det ikke vært prioritert å gjennomføre flere krav, men derimot å sette seg mer inn i LibGDX samt legge om programmet i forbindelse med input og skjermer. Siden sist er det innført “button-input” for å styre brikken, med hensikt om å lettere kunne gjøre overgang til programkort senere (“trykke på knapper”). Det er også lagt til en enkel hovedmeny med anledning til navigasjon mellom meny og spill (inkludert avslutning).

Kjente bugs? 
-   Skjermutviding. Slik som det er nå vil ikke spillet kjøre så sant skjermen justeres
-   "Boards" med mer enn et element på en rute leses foreløpig ikke riktig i "Board.java" (bare det siste elementet)
-   Ved å rotere brikken mot venstre kan frem-og tilbakeknappene miste funksjon. Det går fint dersom man roterer mot høyre (igjen). 

## Deloppgave 3
Viser til readme.md for teknisk teknisk produktoppsett, samt Retrospektiv på Wiki.

### UML
![Image](https://raw.githubusercontent.com/inf112-v20/legless-crane/master/Deliverables/uml2.png?raw=true)

### Manuelle Tester Menuscreen
Test Case: Trykk på "Quit"
-   Expected: spillet avslutter

-   Actual: spillet avslutter

-   PASS
 
Test Case: Trykk på "Lets Play"
-   Expected: Går inn i GameScreen

-   Actual: Går inn i GameScreen

-   PASS
 
### Manuelle Tester GameScreen
 
Test Case: Gå fra GameScreen til MenuScreen med "Main menu"
-   Expected: Går inn i MenuScreen

-   Actual: Går inn i MenuScreen

-   PASS
 
Test Case: roter 360 grader med rotate left, trykk 4 ganger
-   Expected: roterer 360

-   Actual: roterer 360

-   PASS

Test Case: roter 360 grader med rotate right, trykk 4 ganger
-   Expected: roterer 360

-   Actual: roterer 360

-   PASS

Test Case: Brikke vendt mot sør beveger seg 1 back så 1 ahead
-   Expected: Brikke går 1 bak, så en frem

-   Actual: Brikke går 1 bak, så en frem

-   PASS

Test Case: Brikke vendt mot vest (1 rotate right) beveger seg 1 back så 1 ahead
-   Expected: Brikke går 1 bak, så en frem

-   Actual: Brikke går 1 bak, så en frem

-   PASS

Test Case: Brikke vendt mot nord (2 rotate right) beveger seg 1 ahead så 1 back
-   Expected: Brikke går 1 frem, så en tilbake

-   Actual: Brikke går 1 frem, så en tilbake

-   PASS

Test Case: Brikke vendt mot nord (3 rotate right) beveger seg 1 ahead så 1 back
-   Expected: Brikke går 1 frem, så en tilbake

-   Actual: Brikke går 1 frem, så en tilbake

-   PASS

Test Case: brikke (starter på x:6, y:2) vendt mot sør, beveg brikke 13 back til nordlig kant på brettet og forsøk å gå utenfor
-   Expected: Brikke går 12 steg bak men stopper der pga nordlig bretkant (spiller går ikke utenfor)

-   Actual: Brikke går 12 steg bak men stopper der pga nordlig bretkant (spiller går ikke utenfor)

-   PASS

Test Case: brikke starter på x:6, 7:2 vendt mot sør, roter venstre, 1 frem, roter høyre, 2 frem, forsøker å gå utenfor den sørlige brettkanten
-   Expected: Brikke går til sørlig kant men ikke lengre (spiller går ikke utenfor)

-   Actual: Brikke går til sørlig kant men ikke lengre (spiller går ikke utenfor)

-   PASS

Test Case: brikke starter på x:6, 7:2 vendt mot sør, roter venstre, 1 frem, roter høyre, 1 frem, roter venstre, 5 frem, forsøker å gå utenfor den østlige brettkanten
-   Expected: Brikke går til østlig kant men ikke lengre (spiller går ikke utenfor)

-   Actual: Brikke går til østlig kant men ikke lengre (spiller går ikke utenfor)

-   PASS

Test Case: brikke starter på x:6, 7:2 vendt mot sør, roter høyre, 2 frem, roter 3 høyre, 1 frem, roter høyre, 5 frem, forsøker å gå utenfor den vestlige brettkanten
-   Expected: Brikke går til vestlige kant men ikke lengre (spiller går ikke utenfor)

-   Actual: Brikke går til vestlige kant men ikke lengre (spiller går ikke utenfor)

-   PASS

Test Case: brikke starter på x:6, 7:2 vendt mot sør, roter høyre, 2 frem, roter venstre, 1 frem, roter høyre, 5 frem, forsøker å gå utenfor den vestlige brettkanten
-   Expected: Brikke går til vestlige kant men ikke lengre (spiller går ikke utenfor)

-   Actual: Spiller får ikke gå frem etter venstre rotate

-   FAIL

Test Case: brikke starter på x:6, 7:2 vendt mot sør, 1 ahead, mot brettfelt med vegg
-   Expected: Brikke skal ikke bevege seg inn på feltet (Vil endres seinere til kun å ikke gå gjennom kanten på feltet)

-   Actual: Brikke går ikke inn på feltet
-   PASS 