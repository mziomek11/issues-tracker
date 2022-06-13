# IssuesTracker

Issues tracker is application that helps organizations to track issues inside their projects. Application has been created for educational purposes.

## Dlaczego taka apliakcja?

Celem utworzenia tej aplikacji bylo zrobycie praktycznej wiedzy na tematy zwiazane zarowno ze springiem (spring/spring boot/spring web/spring webflux) jak i rowniez na tematy zwiazane z architura/dobrymi praktykami (DDD/CQRS/Event Sourcing/Event Driven Architecture/Testing). Do tematow zwiazanych z CQRS/Event sourcingiem postanowilem nie uzywac zadnych bibliotek, zeby nie uczyc sie zbyt wielu rzeczy jednoczesnie. Oczywiscie architektura i narzedzia nie sa najlepije dobrane jesli chodzi o problem do rozwiazania, ale priorytet bylo zdobycie wiedzy, a nie dowiezienie produktu

## Architektura

Projekts sklada sie w sumie z 11 aplikacji. Warstwa backendu prawie w calosci komunikuje sie asynchronicznie. W sklad aplikacji wchodza

- Issues Command (Spring web)
- Issues Query (Spring webflux)
- Organization Command (Spring web)
- Organization Query (Spring webflux)
- Users Command (Spring web)
- Users Query (Spring webflux)
- Notifications (Spring webflux)
- Api gateway (Spring Cloud Gateway)
- Discovery service (Eureka)
- Reverse proxy (nginx)
- Frontend (React + nginx)

Kazdy z applikacji operujacych na bazie danym ma swoja instancje mongodb (dlaczego mongo? zeby zawracac sobie glowy schematem bazy i migracjami). Jako message broker uzywata zostala kafka, ale nie poswiecalem zbyt duzo czasu na zrozumienie jej wszystkich konfiguracji (zalezalo mi bardziej na poznaniu konceptow zwiazanych z event driven, niz poznawania konkretnego event brokera).

TUTAJ OBRAZEK ARCHITEKTURY

## Frontend

TUTAJ LINK DO README W MODULE FRONTEND

## Komunikacja client <-> server

Komunikacja pomiedzy aplikacja frontowa, odbywa sie na 2 sposoby

1. Komunikacja synchroniczna - wystepuje gdy niezalogowany uzykownik korzysta z aplikacji

TU OBRAZEK

1. Klient wysyla request
2. Server zwraca response

Nic ciekawego

2. Komunikacja asynchroniczna - wystepuje gdy zalogowany uzytkwonik korzysta z aplikacji

TU OBRAKZEK

1. Client zaczyna nasluchiwac na SSE (modul notyfikacji)
2. Client wysyla komende
3. Komenda zostaje przetworzona, a klient otrzymuje id utworzonego obiektu (jesli takowy zostal utworzony)
4. Zdarzenie zostaje przeslane do message brokera
5. Aplikacja do odczytu otrzymuje zdarzenie, przetwarza je, a nastepnie wysyla informacje do modulu notyfikacji o tym, ze dane sa gotowe do pobrania
6. Poprzez SSE modul notyfikacji informuje klienta o nowym danych
7. Klient decyduje co chce zrobic, albo odpytuje o swieze dane, albo nie.

## Uruchomienia applikacji

Aby uruchomic aplikacje potrzebny jest docker i docker-compose. Przy pierwszych uruchomieniu nalezy zbudowac kontenery poprzez komende docker-compose build. Nastepnie kontenery mozna uruchomic komenda docker-compose up. Pierwsze uruchomienia moze troche potrwac, bo wszystko zaleznosci musze sie zainstalowac. Po uruchomieniu dostepne sa nastepujace strony

- Aplikacja frontowa - http://localhost
- Dokumentacja API - http://localhost/swagger
- Eureka dashboard - http://localhost/eureka
- Mailhog (mail client) - http://localhost/mailhog

## Known issues

- Brak outbox/inbox pattern -> mozliwa niedotarcie zdarzen/deduplikacja zdarzen
- Brak transakcji baz danych
- Logika authoryzacji w module users, powinniem byc osobnt modul auth pod to
- Logika sprawdzania dostepu w komendach prawdopodobnie powinna byc wyciagnieta do jakichs dekoratorow
- Brak hateos
- 504 jesli sse przez jakis czas nie rzuci eventu
- api gateway nie formatuje bledow tak jak reszta applikacji
- testy integractyjne testuja mocki, zamiast uzywac prawdziwej bazy danych
- brak testow w applikacjach do odczytu i pod event handlery w applikacjach do zapisu
- produkcyjny config dockera (mongo/kafka/networking/using docker-compose in production) moze nie byc najlepszy, natomiast nie chcialem tracic zbyt duzo czasu na rzeczy zwiazane z infrastuktura

## Wnioski

Uzycie tak skomplikowanej architektury to chyba najlepsze to moglem zrobic. Nauczylem sie przy tym mase nowych konceptow. Jednoczesnie zobaczylem jak wiele jest jeszcze do nauki ;)