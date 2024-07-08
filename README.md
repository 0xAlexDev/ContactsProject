# ContactsProject

Si prega di leggere il file "Contacts Readme.pdf" per dettagli maggiori.

# Come Avviare L'applicazione

1. Effettuare il clone della repository
2. Entrare nella cartella creata
3. eseguire il comando: docker compose up
   
L’applicazione è composta da 3 entità principali:

- Webapp Angular (Frontend)

- Spring app (Backend)

- MySql DB

Per mandare in esecuzione l’intero sistema ed effettuare i dovuti test è stata implementata una soluzione che prevedere l’utilizzo della tecnologia Docker

Run tramite Docker

Per mandare in esecuzione l’intero sistema attraverso docker eseguire il comando nella root della cartella scaricata in cui sono presenti le applicazioni:

docker compose up

nella cartella che contiene le applicazioni Angular e Spring.

questo avvierà le 3 entità in container separati generando:

1 container - Database MySQL (con il nostro database Contacts al suo interno) (ESPOSTO SULLA PORTA - 15900)

2 container - Springboot App (ESPOSTO SULLA PORTA - 16900)

3 container - Webapp Angular (ESPOSTO SULLA PORTA - 16990)

entrambi i container sono configurati lavorando in una network privata.

per accedere all’app sarà sufficiente attendere l’avvio dei container ed entrare nell’applicazione tramite il seguente url: http://localhost:16990
