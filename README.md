ZAMFIRESCU RADU IOAN, 322CD

Proiect POO etapa 1, 2020
https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa1

* input
    Contine clasele necesare pentru preluarea inputului din fisierle json

* factory
    Singleton + Factory patterns:
    In pachetul factory, clasa Factory contine metode pentru a crea obiecte
    cu atribute proprii dupa datele din input: consumatori si distribuitori.
    Result reprezinta un obiect cu informatiile esentiale de la finalul jocului.

* business
    Payer - interfata implementata de entitatile care contin o metoda de plata;
    Consumer - clasa specifica unui consumator care implementeaza interfata
        Payer din moment ce in fiecare luna trebuie sa plateasca o suma de bani
        (evident, daca mai e in joc);
    Distributor - implementeaza interfata Payer din moment ce are
        costuri lunare de platit;
    Contract - pastreaza informatiile stabilite la inceput de catre un
        consumator si un distribuitor cu privire la perioada de intelegere,
        costul lunar, dar si o referinta catre un distribuitor impreuna cu
        indexul consumatorului necesare in situatiile cand trebuie anulat
        contractul;

    Atat un consumator, cat si un distribuitor, isi insusesc statutul de
    intrare in faliment (daca este cazul) in metoda pay, iar modificarile
    ulterioare necesare vor fi facute in exteriorul acestora de catre metodele
    din clasa BusinessFlow (e.g. stergerea contractelor unui distribuitor
    falimentat).

* utils
    BusinessFlow - reprezinta clasa cu toate informatiile necesare desfasurarii
    jocului. Contine metodele principale care pun in functiune simularea
    jocului, actualizeaza starile entitatilor si legaturile dintre ele (schimba
    preturile distribuitorilor, face contracte intre entitati), adauga noii
    consumatori si respecta ordinea din cerinta.

    Constants - contine numere constante, in aceasta faza doar penalizarea
            pentru intarzierea platii a unui consumator si procentul de profit
            al unui distribuitor;
    Utils - Se poate ignora: contine o metoda prin care se pastreaza
            rezultatele obtinute de mine cu scopul de a vedea diferentele fata
            de ref. Am lasat-o in sursa pentru ca cel mai probabil voi avea
            nevoie si la etapa a 2 a.

* strategies/PriceStrategy
    Pune in evidenta metoda prin care un distribuitor isi stabileste pretul
    final al unui contract.
