# Gjøremål

* Innstallere copilot
* Fylle ut README.md
* Opprette rapporten

## Krav til oppgaven

Utvikle et forenklet system for avvikling av togavganger.

#### Skal bestå av:
* Avgangstid, tt:mm på 24timers format
* Linje, "F1" eller "L3" som definerer strekningen toget kjører på
* Tognummer, unik int
* Destinasjon
* Spor, heltall som angir sporet. dersom ingen tildelt sett til -1
* Forsinkelse, forsinkelse i tt:mm, hvis ikke sett til 00:00

#### Avgrensninger
* Skal støtte én stasjon
* Tar ikke hensyn til dato
* "Klokken" oppdaterer manuelt fra brukermeny

#### Funksjonelle krav
Tekstbasert UI i form av meny, med følgende fuksjoner
* Vise/skrive ut oversikt over togavganger sortert etter avreisetidspunkt
* Legge inn ny togavgang. Ikke mulig å legge inn tog med likt tognummer som eksisterende.
* Tildele spor ved å søke opp tognummer
* Legge inn forsinkelse på tognummer
* Søke etter togavgang på tognummer
* Søke etter togavgang på destinasjon (linje?)
* Oppdatere klokken
* Avslutte applikasjon

En togavgang skal automatisk fjernes fra oversikten dersom avreisetidspunktet (pluss eventuell
forsinkelse) er tidligere enn klokken (tidspunktet på dagen).

Klokken skal kun kunne stilles fram i tid. 

#### Tavlekrav

NB! I følgende rekkefølge

* Avgangstid "hh:mm"
* Linje
* Tognummer
* Destinasjon
* Evt. forsinkelse. Skal kun vises ved forsinkelse
* Spor. Hvis ikke tildelt skal den ikke vises.

## Del 1
