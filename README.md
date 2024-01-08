# MB Ontdekt

### Projectbeschrijving
MB Ontdekt is een samenwerking tussen Fontys, Bibliotheek Midden-Brabant en Ontdekstation013.

De bibliotheek midden-brabant en het ontdekstation013 maken meetstations, die in regio Tilburg worden opgehangen (Te zien op: Meet je stad!). Deze meetstations sturen vanuit de erg energiezuinige microprocessor een stukje data de lucht in die later weer opgevangen wordt door verschillende punten die deze signalen kunnen horen. Deze slaan dit op in een database die weergegeven wordt op de meetjestad website. De stakeholders willen voor de opdracht een website gemaakt zien die dezelfde gegevens kan weergeven op een meer gebruiksvriendelijke manier. Zo moet er gebruik gemaakt worden van een heatmap om zo in één overzicht duidelijk te krijgen wat de temperatuurverschillen zijn tussen verschillende meetpunten. Daarnaast is er naast de kleurtjesook meer data beschikbaar waar gebruikers ook toegang tot moeten hebben. Naast de kaart met alle meetpunten moeten gebruikers zich ook kunnen registreren en zo hun eigen meetstation kunnen aanmaken en vervolgens beheren. Hierbij rond dat het basisidee af van de applicatie.

## Build
Om het project op te starten heb je in principe geen datadump nodig, wel is dit natuurlijk nodig voor het kunnen gebruiken van alle features. Deze datadump is te vinden in [de documentatie repository](https://github.com/OntdekIT/Software-Documents).

Verder hebben wij binnen onze groep met IntelliJ gewerkt waarin alles automatisch geregeld wordt. Als je het project handmatig wilt builden, kan je dit met de volgende commando's doen:

``mvn clean package``