# POO Sistem-Energetic - Etapa 1
## Nicolae Mihaela-Diana, 325CA

1. Functionalitate

    Pentru citirea testelor de input am creat clasa Parser, care parseaza fisierele si
initializeaza instanta SingletonInput, ce contine informatiile brute. Datele obtinute sunt
apoi transformate cu ajutorul factory-ului EntityFactory din obiecte de tip EntityInput in
obiecte de tip Consumer/Distributor. Noile informatii sunt retinute in Database, care va
avea o instanta de tip Singleton.
    Sunt rulate apoi rundele, in clasa Process, cu ajutorul metodelor din clasa Strategy
fiind procesate mai intai update-urile (mai putin in prima runda), iar apoi platile, in care
sunt stabilite contractele, entitatile isi platesc costurile si isi primesc veniturile, iar
la final isi determina statusul (dpdv al falimentarii).
    La finalul jocului, cu ajutorul clasei Writer sunt afisate rezultatele, informatiile
despre entitati, realizand cu Object Mapper un pretty format .

2. Design

    In implementarea jocului am folosit design pattern-ul Singleton pentru crearea instantei
de Input si a celei de Database, si pattern-ul Factory pentru crearea entitatilor, definit in
EntityFactory, clasele Consumer si Distributor implementand interfata comuna Entity.
    In plus, am realizat doua interfete: Payer (implementata de platitori, anume Consumer) si
Payee (implementata de primitori, anume Distributor), acestea avand scopul de a simplifica
procesul de tranzactie a platilor intre entitati.