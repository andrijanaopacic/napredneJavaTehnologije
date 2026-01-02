# Aplikacija za prodaju autobila

U ovom projektu razvijen je softverski sistem koji omogućava postavljanje i upravljanje oglasima za automobile. Serverski deo aplikacije realizovan je pomoću Spring Boot okvira, dok je klijentski deo napravljen u React tehnologiji. 
Sistem omogućava korisnicima da dodaju nove oglase, filtriraju automobile po različitim kriterijumima kao što su marka, model, cena ili vrsta goriva, i da pregledaju detalje o svakom oglasu. Administratori imaju dodatnu kontrolu nad oglasima, uključujući mogućnost aktiviranja ili deaktiviranja oglasa, dodavanja, brisanja ili promene.

## Struktura projekta

- **automobili_back** - serverski deo aplikacije je zadužen za upravljanje oglasima, obradu podataka o automobilima, korisničku autentifikaciju i autorizaciju, kao i za komunikaciju sa bazom podataka.
- **automobili_front** - klijentski deo aplikacije predstavlja interfejs putem kojeg korisnici direktno komuniciraju sa sistemom. Njegova uloga je da obezbedi jednostavno i vizuelno privlačno okruženje u kome korisnici mogu da pregledaju automobile, postavljaju ili uređuju svoje oglase, kao i da administratori upravljaju oglasima.

## Tehnologije i alati koji su korišćeni

- Java - primarni programski jezik, izabran zbog stabilnosi i objektno-orijentisanog pristupa
- Spring Boot - glavni frejmvork za ubrzan razvoj aplikacije, omogućio je brzo postavljanje projekta, fokusiranje na poslovnu logiku i laku integraciju RESTful API endpoint-a
- Spring Sequrity i JWT (JSON Web Token) - obezbeđuju sigurnu autenifikaciju i autorizaciju, dozvoljavajući da samo ovlašćeni korisnici pristupaju određenim resursima
- MySQL - relacioni sistem za skladištenje podataka o oglasima, korisnicima i automobilima
- Maven - za upravljanje zavisnostima i izgradnju backend dela aplikacije
- React - za izradudinamičkog i interaktivnog veb interfejsa
- Axios - za slanje HTTP zahteva ka Spring Boot API-ju, uključujući i dodatak JWT tokena u zaglavljima za zaštićene resurse
- React Router DOM - za upravljanje navigacijom unutar aplikacije

