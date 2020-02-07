#Overordnede mål
- Man programmerer en robot som beveger seg på et kart ved hjelp av kort (moves) som tilsvarer enkle bevegelser
- Man vinner ved å besøke alle flaggende i stigende rekkefølge
- Man har tre liv, mister man alle er man ute av spillet
- Den som først kommer seg innom alle flaggene (på gyldig måte) vinner
   - Vil man spille til alle kommer i mål eller dør? (tvilsomt?)
   - Eller avsluttes spillet når første spiller kommer i mål?
- Gameloop er rundebasert, hver runde består av 5 faser.
    - 0: Roboter som skal i powerdown settes i powerdown
    - 1: Kort deles ut
    - 2: Alle programmerer robotens fem faser (1 programkort pr fase). Spillere godkjenner sine kort og kan deretter ikke endre dem (de er da overført til roboten). Når nest siste spiller har godkjent sine kort, får sistemann 30 sekunder til å legge ned sine kort. Gjenværende registre fylles med random kort fra hånden til spilleren.
    - 3: Spillrunden gjennomføres med fem faser
        - I hver fase utføres robotens program for den fasen, elementer på brettet beveger seg, og lasere skyter, skade deles ut
    - 4: Opprydning: eventuelle optionskort deles ut, skade fjernes og roboter i powerdown vekkes til live igjen, roboter som gjenoppstår fra de døde settes ut på brettet igjen

#MVP

- ha et spillebrett
- vise en brikke
- kunne flytte en brikke
- spille fra ulike maskiner
- dele ut kort
- velge kort (5 av 9)
- flytte brikke utfra kort
- dele ut nye kort ved ny runde
- vise flere (i alle fall to) brikker på samme brett
- dele ut kort til hver robot
- flytte flere brikker samtidig
- flytte brikker utfra prioritet på kort
    - Sortere fase sine bevegelseskort
- flagg på brettet
- kunne registrere at en robot har vært innom et flagg
- håndtere konflikter i bevegelse riktig
- kunne legge igjen backup
- restarte fra backup v ødeleggelse
- går du i et hull, blir du ødelagt, mister et liv og begynner fra forrige backup
- går du av brettet, blir du ødelagt, mister et liv og begynner fra forrige backup
- blir du skutt i fillebiter (9 i skade) blir du ødelagt, mister et liv og begynner fra forrige backup
- vender en robot mot deg ved slutten av en fase blir du skutt og får en i skade
- får du skade får du mindre kort i henhold til skaden du har
- kan ikke gå gjennom vegger
- for mye skade brenner fast programkort fra runde til runde



#Nice to have
- Kunne ta powerdown for å reparere skade
- Options-kort, som endrer reglene for deg (bad stuff for andre, good stuff for deg osv)
- Pushers, teleportere og andre elementer på brettet
- Kunne lage større brett/sette sammen ulike brett
- Andre elementer på brettet
- Generere hvordan brett kobles sammen?
- Generere brett?
- Vanskelighetsgrad på brett??
- Single player