# Obligatorisk innlevering 2

## Deloppgave 1
Vi fungerer relativt greit i rollene vi satte første sprint. Kommer til å rotere på roller ved neste sprint og sannsynligvis de følgende etter det igjen. For denne sprinten endte vi opp i en slags overgangsfase hvor rollene ble litt mindre definerte.

Dette innebærer de ulike rollene i semesterprosjektet:
- Teamleder: lede teamet ved å prøve å sette mål for de ulike sprintene, forsøke å spre arbeidsoppgaver og få alle til å ta ansvar for én del av sprinten.
- Kundekontakt: overordnet ansvar for kommunikasjon mellom gruppen og gruppeleder
- Bookingansvarlig: booke grupperom for møter/arbeid slik at teamet har er greit sted å jobbe sammen.
- Sekretær: utforme referat, overordnet ansvar for å formulere den obligatorisk oppgaven

Vi har sett at det blir aktuelt å sette en av oss som ansvarlig for testing. Som da sørger for at vi tester prosjektet tilstrekkelig, og at vi tester relevante deler av prosjektet. Alle har ansvar for at testene blir gjennomført ved commit, og helst består.

Våre erfaringer med tanke på teamet har to hovedpunkter.
Sindre sto for en uproporsjonal andel av commits og kode i første sprint, det er en enighet om at dette ansvaret burde ha fordelt seg mer tidligere slik at enkelte ikke henger etter i endringene som har blitt gjort.
For å hjelpe med dette avtaler vi tider for samarbeid og parprogrammering i løpet av denne sprinten slik at alle ser noe av koden som vi implementerer og selvsagt også bidrar til den.
Prosjektmetodikk har vært relativ grei så langt, vi velger å vektlegge mer tid på parprogrammering enn vi gjorde i første sprint, og vi ønsker også å få på plass mer relevante tester. Så litt mer fokus på flere og bedre enhetstester hvor mulig, og manuelle tester hvor ikke.

Både gruppedynamikk og kommunikasjon fungerer ganske godt, det er lav terskel for å bidra i gruppetimer og i våre møter utenfor onsdagen om man har innspill, spørsmål eller tanker til prosjektet. Her deltar alle aktivt, hvor sekretær og teamleder har sørget for at vi holder oss konkrete til sprinten, og ikke for generelt til prosjektet.

Når det gjelder prosjektstruktur endte vi opp med å planlegge mye i første sprint i fellesskap og ble enige om en plan, som nevnt tidligere ble det til at Sindre sto for alt for mye av implementasjonen av disse planene og commits til prosjektet generelt.
Referat fra møter ligger i github prosjektet vårt under Wiki -> Møtereferater

Forbedringspunkter:
- Jevne ut fordelingen av commits fra alle på gruppen (hovedproblemet til gruppen for nå)
- Vi skal sette av tid til parprogrammering som et virkemiddel for å hjelpe med første punkt.
- Revurdere strukturen i prosjektet vårt så langt, lener oss nok mer på libGDX enn vi originalt hadde tenkt.
- Jobbe en del med prosjekttavlen, fordele issues, rydde i issues vi har liggende på tavlen og annet arbeid som trengs.

## Deloppgave 2
Brukerhistorier:
#### Brukerhistorie #1: spillet må kunne bygge
“Som kunde trenger jeg at applikasjonen jeg har bestilt lar seg bygge, det bør ikke være nødvendig å kjøre fra en IDE”
######Akseptansekriterier:
- Skal kunne vite hvordan man bygger applikasjonen
- Dette skal være oversiktlig forklart i github(readme.md)
######Arbeidsoppgaver:
- Dokumentere prosjektet bedre i readme.md fra forsiden

#### Brukerhistorie #2: spillet må kunne kjøre
“Som kunde trenger jeg å kunne kjøre applikasjonen jeg har spilt”
######Akseptansekriterier
- Krasjer ikke ved oppstart
- Krasjer ikke mens det kjører
######Arbeidsoppgaver
- Utføre manuelle og automatiske tester av spillet, sørge for at det kjører relativt problemfritt.

#### Brukerhistorie #3: spillet bør ha tester
“Som utvikler trenger jeg å kunne teste at oppførselen er som forventet ”
######Akseptansekriterier
- Spillet ikke har runtime-error
- Forutsigbar oppførsel fra kode
######Arbeidsoppgaver
- Opprette manuelle og/eller automatiske tester som sjekker at spillets kode oppfører seg som ønsket.

#### Brukerhistorie #4: man bør kunne bevege brikke via GUI
“Som spiller trenger jeg å kunne velge bevegelsen som roboten skal gjøre på skjermen via knapper fremfor keyboard, for å senere kunne programmere roboten via programkort.”
######Akseptansekriterier
- Spillebrettet (tmx-fil) og robot må kunne vises/være interaktive i “GameScreen”.
- Spiller må kunne bevege seg via knapper fremfor keyboard
######Arbeidsoppgaver
- Overføre spillebrett og robot fra “Renderer”-klassen til “GameScreen”-klassen
- Sette seg inn i hvordan man går fra “KeyListener”-input til “Button”-input
- Implementere/eksperimentere med overgang

#### Brukerhistorie #5: knapper/kort bør ikke overlappe kartet.
“Som spiller må jeg kunne se hele kartet, det bør ikke blokkeres av knapper fra grensesnittet”
######Akseptansekriterier
- GUI elementer som knapper bør ikke overlappe
- Bør se relativt ryddig ut ved siden av spillerkartet
######Arbeidsoppgaver
- Undersøke muligheter for å få utvidet det som renderes for spiller, for å få plass til knapper utenfor kartet.


#### Brukerhistorie #6: spillet bør ha runder og faser (neste sprint)
“Som spiller ønsker jeg å kunne se en meny før jeg begynner selve spillet, slik at jeg har mulighet til å navigere mellom ulike stadier av et spill (meny, start, avslutt).”
######Akseptansekriterier
- Implementere forskjellige skjermer som meny og spillskjerm
- Implementere overgang mellom skjermer via knapper 
- Trekke “Renderer”-klassen vekk fra spillogikk.
######Arbeidsoppgaver
- Sette seg inn i konsepter om skjermer/hvordan håndtere overganger.
- La “Renderer”-klassen ha et overordnet ansvar for å kjøre de ulike skjermene
- Implementere/eksperimentere med kode

####Resterende deloppgaver: 
Vi har prioritert oppgaver som er relatert til å slå sammen spillogikk og brukergrensesnitt. Å få en prototype av spillet til å fungere etter de kravene vi har satt for denne og forrige sprint. 

Hva ser vi i ettertid? At vi muligens må omstille oss når det kommer til måten vi skal bygge opp spillet. Nytte oss mer av konseptene som allerede er tilgjengelige i LibGDX. 

Det er ikke gjort noen endringer i forhold til hovedkrav som er satt ennå. Hovedkravene baserer seg fortsatt på de mest grunnleggende reglene i spillet.

I ettertid har det ikke vært prioritert å gjennomføre flere krav, men derimot å sette seg mer inn i LibGDX samt legge om programmet i forbindelse med input og skjermer. Siden sist er det innført “button-input” for å styre brikken, med hensikt om å lettere kunne gjøre overgang til programkort senere (“trykke på knapper”). Det er også lagt til en enkel hovedmeny med anledning til navigasjon mellom meny og spill (inkludert avslutning).

Kjente bugs?
- Masse feilmeldinger i IntelliJ i src\main\assets\ui\uiskin.atlas på formen “JSON standard does not allow such tokens”. Denne feilmeldingen ser ikke ut til å produsere feil, men vi må nok undersøke hvorfor denne kommer.

## Deloppgave 3
Viser til readme.md for teknisk teknisk produktoppsett, UML under mappen "Deliverables", samt Retrospektiv på Wiki.
- Manuelle tester for gui? (Lever beskrivelser av hvordan testen foregår, så gruppeleder kan teste selv)






