# POO Sistem-Energetic - Etapa 2
## Nicolae Mihaela-Diana, 325CA

1. Functionalitate

    Pentru citirea testelor de input am creat clasa Parser, care parseaza fisierele si
initializeaza instanta Singleton de Input, ce contine informatiile brute. Datele obtinute sunt
apoi transformate cu ajutorul factory-ului EntityFactory din obiecte de tip EntityInput in
obiecte de tip Consumer/Distributor/Producer. Noile informatii sunt retinute in Database, care va
avea tot o instanta de tip Singleton.
    Sunt rulate apoi rundele, in clasa Process, cu ajutorul metodelor din clasa ProcessStrategy,
in care am creat o metoda pentru runda initiala si alta pentru rundele ce contin update. In aceste metode
distribuitorii isi aleg producatorii si isi determina costurile, sunt stabilite contractele, entitatile
isi platesc costurile si isi primesc veniturile, iar la final isi determina statusul (dpdv al falimentarii).
    La finalul procesarii, cu ajutorul clasei Writer sunt afisate rezultatele, informatiile
despre entitati, realizand cu Object Mapper un pretty format.

2. Design

    In implementarea jocului am folosit design pattern-ul Singleton pentru crearea instantei
de Input si a celei de Database.
    Pattern-ul Factory a fost folosit pentru crearea entitatilor, definit in EntityFactory,
clasele Consumer, Distributor si Producer implementand interfata comuna Entity. Pattern-ul a fost de
asemenea utilizat in procesul de alegere a strategiilor pentru distributori, pentru care am implementat
StrategyFactory si cele trei tipuri de strategii (Green, Price si Quantity).
    Am introdus si design pattern-ul Observer, in care distributorii sunt Observers, iar producatorii
Observables, astfel incat atunci cand apare o modificare asupra producatorilor, distributorii
vor fi notificati.
    In plus, am realizat doua interfete: Payer (implementata de platitori, anume Consumer) si
Payee (implementata de primitori, anume Distributor), acestea avand scopul de a simplifica
procesul de tranzactie a platilor intre entitati.
