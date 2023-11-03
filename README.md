# Slutprojekt i Avancerad Java - Produktionsregulator

## Projektbeskrivning
I detta slutprojekt har utvecklingen fokuserat på att skapa ett system som använder Java-trådar för att simulera ett produktionsflöde. Kärnan i systemet utgörs av en buffert som möjliggör för producent-trådar att effektivt leverera enheter medan konsument-trådar bearbetar dessa utan dröjsmål. `ProductionBufferMonitor` ansvarar för övervakning och beräkning av buffertens genomsnittliga storlek, en funktion som inte bara håller användaren uppdaterad, utan också levererar värdefulla insikter för loggningsändamål. `Controller`-klassen är dirigenten som styr alla producenter och konsumenter, medan `LogController` ser till att varje händelse i systemet dokumenteras på ett bra sätt.

## Lösning
Lösningen som presenteras är funktionell och visar på en stark grund, även om det alltid finns utrymme för förbättringar. Trådarnas synkronisering sköts på ett effektivt sätt som eliminerar risken för kollisioner när de olika trådarna hanterar bufferten. Genom att använda `synchronized` metoder i `ProductionBuffer`, kan vi garantera att trådarnas åtkomst till buffertens innehåll sker på ett kontrollerat vis. `EventLog`-klassen, som används av singleton-mönstret, ser till att varje betydande händelse blir ordentligt registrerad och arkiverad.

## Reflektioner & Lärdomar
Projektet har varit oerhört givande och har framhävt hur viktigt det är att behärska olika aspekter av avancerade Java-koncept, särskilt när det gäller trådhantering och synkronisering. En lärdom som starkt har förankrats är att konsekvenserna kan bli omfattande om trådsäkerheten nonchaleras. Denna insikt kommer förhoppningsvis att vara värdefull i framtida projekt.

Trots att jag inte är helt nöjd med koden i dess nuvarande skick, har processen varit väldigt bra för min utveckling. Jag tillbringade tre dagar med att debugga loggsystemet, vilket till slut visade sig vara ett felaktigt antagande från min sida. Den tidskrävande processen av debugging hindrade mig från att omstrukturera koden och lägga till ytterligare funktionaliteter som skulle uppfylla kraven för ett högre betyg. Men jag anser att koden och systemet fortfarande är stabilt, jag hade kunnat göra det bättre bara.

I projektets slutskede valde jag att prioritera förbättringar av klassdiagrammet och att färdigställa denna rapport, vilket har varit lika viktigt som själva kodarbetet.

## Användargränssnitt - SwingView
Användargränssnittet som är byggt med Swing ger en bra bild av systemets aktuella status. En progressbar illustrerar buffertens fyllnadsgrad, och användaren kan på ett dynamiskt sätt justera antalet aktiva producenter via knappar. De loggmeddelanden som kontinuerligt visas i textfältet tjänar inte enbart som visuell återkoppling för användaren, utan är även på systemets loggningsfunktion.
