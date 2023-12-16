Questo progetto è pensato come supporto per la preparazione della prima parte del laboratorio del corso di Reti di Calcolatori dell'Università degli Studi di Milano (dipartimento di Informatica), ovvero il calcolo dei parametri delle sottoreti (e VLAN) dato l'indirizzo base dell'azienda, i nomi delle sottoreti e quanti dispositivi sono presenti (in ogni sottorete).

Vediamo un esempio:
```
E 10.11.160.0/24
R-61
K-31
A-28
G-17
M-12
```
Vogliamo trovare i parametri di rete dato l'indirizzo base (in alto) usando l'euristica (E, R come regola altrimenti). Dalla seconda riga in poi sono presenti i nomi delle sottoreti (R, K ...) seguiti dalla dimensione. In questo caso verranno calcolati questi parametri:
```
_______________________
Rete R, numero dispositivi: 61 -> 64 -> 6 bit necessari
Indirizzo base: 	10.11.160.0/26
Indirizzo broadcast: 	10.11.160.63
Indirizzo gateway: 	10.11.160.62
Indirizzo primo IP: 	10.11.160.1
Indirizzo ultimo IP: 	10.11.160.61
Indirizzo netmask: 	255.255.255.192
Indirizzo wildcard: 	0.0.0.63
_______________________
Rete K, numero dispositivi: 31 -> 32 -> 5 bit necessari
Indirizzo base: 	10.11.160.64/27
Indirizzo broadcast: 	10.11.160.95
Indirizzo gateway: 	10.11.160.94
Indirizzo primo IP: 	10.11.160.65
Indirizzo ultimo IP: 	10.11.160.93
Indirizzo netmask: 	255.255.255.224
Indirizzo wildcard: 	0.0.0.31
_______________________
Rete A, numero dispositivi: 28 -> 32 -> 5 bit necessari
Indirizzo base: 	10.11.160.96/27
Indirizzo broadcast: 	10.11.160.127
Indirizzo gateway: 	10.11.160.126
Indirizzo primo IP: 	10.11.160.97
Indirizzo ultimo IP: 	10.11.160.125
Indirizzo netmask: 	255.255.255.224
Indirizzo wildcard: 	0.0.0.31
_______________________
Rete G, numero dispositivi: 17 -> 32 -> 5 bit necessari
Indirizzo base: 	10.11.160.128/27
Indirizzo broadcast: 	10.11.160.159
Indirizzo gateway: 	10.11.160.158
Indirizzo primo IP: 	10.11.160.129
Indirizzo ultimo IP: 	10.11.160.157
Indirizzo netmask: 	255.255.255.224
Indirizzo wildcard: 	0.0.0.31
_______________________
Rete M, numero dispositivi: 12 -> 16 -> 4 bit necessari
Indirizzo base: 	10.11.160.160/28
Indirizzo broadcast: 	10.11.160.175
Indirizzo gateway: 	10.11.160.174
Indirizzo primo IP: 	10.11.160.161
Indirizzo ultimo IP: 	10.11.160.173
Indirizzo netmask: 	255.255.255.240
Indirizzo wildcard: 	0.0.0.15
```
I parametri calcolati per ogni sottorete sono:
- Indirizzo base
- Indirizzo broadcast
- Indirizzo gateway
- Indirizzo primo IP
- Indirizzo ultimo IP
- Indirizzo netmask
- Indirizzo wildcard