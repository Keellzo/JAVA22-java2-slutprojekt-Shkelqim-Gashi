# Slutprojekt i Avancerad Java

## Projektbeskrivning
Det här systemet är en simulering som effektivt hanterar flödet av enheter med hjälp av Java-trådar. Med en buffert, som är hjärtat i systemet, ser den till att producent-trådarna (Producer) kan leverera sina enheter smidigt, och att konsument-trådarna (Consumer) kan bearbeta dem utan dröjsmål. En `ProductionBufferMonitor` håller koll på buffertens storlek och räknar ut ett snitt för att visa användaren och lägga till i loggen. `Controller`-klassen leder producenter och konsumenter i systemet, medan `LogController` ser till att varje händelse blir dokumenterad - ett väldigt bra verktyg för att säkerställa systemets drift.

## Lösning
Min lösning är helt okej tycker jag - synkronisering mellan trådarna sköts bra, vilket gör att tillgången till den gemensamma resursen, bufferten, sker utan kollisioner. `ProductionBuffer` använder sig av `synchronized`-metoder för att se till att bara en tråd åt gången får lov att manipulera innehållet. `EventLog`-klassen, en praktfull singleton, ser till att varje event som bör noteras fångas och lagras till filen. 

### Användargränssnitt - SwingView
Användargränssnittet, ger en klar bild av systemets nuvarande status. Här finns en progressbar som skildrar buffertens nuvarande last, och knappar som låter dig öka eller minska antalet producenter efter behov. I textområdet ser du loggmeddelanden som rullar förbi i realtid, vilket ger inblick i systemets liv.
