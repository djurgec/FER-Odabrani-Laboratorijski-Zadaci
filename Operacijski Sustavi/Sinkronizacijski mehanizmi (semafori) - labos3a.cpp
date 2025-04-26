#include <iostream>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <semaphore.h>
#include <vector>
#include <algorithm>
using namespace std;

struct ums
{
    int ulaz;
    int izlaz;
    sem_t ums_bs;
    sem_t ums_os;
    char *ums_podaci;
};

struct ims
{
    int ulaz;
    int izlaz;
    sem_t ims_bs;
    char *ims_podaci;
    bool dodao_novi;
    int izlazni_brojac;
};

int bud, brd, bid, vms;
vector<ums> ulazni_meduspremnici;
vector<ims> izlazni_meduspremnici;
sem_t sem_ispis;

char dohvati_ulaz()
{
    return 'A' + rand() % 26;
}

int obradi_ulaz()
{
    return rand() % ulazni_meduspremnici.size();
}

char dohvati_podatak(int id)
{
    char rez = ulazni_meduspremnici.at(id).ums_podaci[ulazni_meduspremnici.at(id).izlaz];
    ulazni_meduspremnici.at(id).ums_podaci[ulazni_meduspremnici.at(id).izlaz] = '\0';
    ulazni_meduspremnici.at(id).izlaz = (ulazni_meduspremnici.at(id).izlaz + 1) % vms;
    return rez;
}

void obradi_podatak(char s, int t)
{
    izlazni_meduspremnici.at(t).ims_podaci[izlazni_meduspremnici.at(t).ulaz] = tolower(s);
    if ((izlazni_meduspremnici.at(t).ulaz == izlazni_meduspremnici.at(t).izlaz) && (std::all_of(izlazni_meduspremnici.at(t).ims_podaci, izlazni_meduspremnici.at(t).ims_podaci + vms, [](char c)
                                                                                                     { return c != '\0'; })))
    {
        izlazni_meduspremnici.at(t).izlaz = (izlazni_meduspremnici.at(t).izlaz + 1) % vms;
    }
    izlazni_meduspremnici.at(t).izlazni_brojac++;
    izlazni_meduspremnici.at(t).ulaz = (izlazni_meduspremnici.at(t).ulaz + 1) % vms;
    if (!izlazni_meduspremnici.at(t).dodao_novi)
    {
        izlazni_meduspremnici.at(t).dodao_novi = 1;
    }
}

void *ulazna_dretva(void *arg)
{
    int id = *((int *)arg);
    while (1)
    {
        char u = dohvati_ulaz();
        int t = obradi_ulaz();
        sem_wait(&ulazni_meduspremnici.at(t).ums_bs);
        ulazni_meduspremnici.at(t).ums_podaci[ulazni_meduspremnici.at(t).ulaz] = u;
        if ((ulazni_meduspremnici.at(t).ulaz == ulazni_meduspremnici.at(t).izlaz) && (std::all_of(ulazni_meduspremnici.at(t).ums_podaci, ulazni_meduspremnici.at(t).ums_podaci + vms, [](char c)
                                                                                                       { return c != '\0'; })))
        {
            ulazni_meduspremnici.at(t).izlaz = (ulazni_meduspremnici.at(t).izlaz + 1) % vms;
            sem_wait(&ulazni_meduspremnici.at(t).ums_os);
        }
        ulazni_meduspremnici.at(t).ulaz = (ulazni_meduspremnici.at(t).ulaz + 1) % vms;
        sem_post(&ulazni_meduspremnici.at(t).ums_os);
        sem_post(&ulazni_meduspremnici.at(t).ums_bs);
        sem_wait(&sem_ispis);
        cout << "U" << id << ": dohvati_ulaz(" << id << ")=>'"
             << u << "'; obradi_ulaz('" << u << "')=>" << t
             << "; '" << u << "' => UMS[" << t << "]" << endl;
        cout << "UMS[]: ";
        for (int i = 0; i < brd; i++)
        {
            for (int j = 0; j < vms; j++)
            {
                if (ulazni_meduspremnici.at(i).ums_podaci[j] == '\0')
                {
                    cout << "-";
                }
                else
                {
                    cout << ulazni_meduspremnici.at(i).ums_podaci[j];
                }
            }
            cout << " ";
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);

        sleep(10);
    }
}

void *radna_dretva(void *arg)
{
    int id = *((int *)arg);
    while (1)
    {
        sem_wait(&ulazni_meduspremnici.at(id).ums_os);
        sem_wait(&ulazni_meduspremnici.at(id).ums_bs);
        char s = dohvati_podatak(id);
        sem_post(&ulazni_meduspremnici.at(id).ums_bs);

        int t = rand() % izlazni_meduspremnici.size();

        sem_wait(&izlazni_meduspremnici.at(t).ims_bs);
        obradi_podatak(s, t);
        sem_post(&izlazni_meduspremnici.at(t).ims_bs);
        sem_wait(&sem_ispis);
        cout << "R" << id << ": uzimam iz UMS[" << id << "]=>'" << s << "' i stavljam u IMS[" << t << "]" << endl;
        cout << "UMS[]: ";
        for (int i = 0; i < brd; i++)
        {
            for (int j = 0; j < vms; j++)
            {
                if (ulazni_meduspremnici.at(i).ums_podaci[j] == '\0')
                {
                    cout << "-";
                }
                else
                {
                    cout << ulazni_meduspremnici.at(i).ums_podaci[j];
                }
            }
            cout << " ";
        }
        cout << endl;
        cout << "IMS[]: ";
        for (int i = 0; i < bid; i++)
        {
            for (int j = 0; j < vms; j++)
            {
                if (izlazni_meduspremnici.at(i).ims_podaci[j] == '\0')
                {
                    cout << "-";
                }
                else
                {
                    cout << izlazni_meduspremnici.at(i).ims_podaci[j];
                }
            }
            cout << " ";
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);
        sleep(3);
    }
}

void *izlazna_dretva(void *arg)
{
    int id = *((int *)arg);
    bool prvi_rad = 1;
    char prosli_podatak;
    while (1)
    {

        sem_wait(&izlazni_meduspremnici.at(id).ims_bs);
        if (prvi_rad == 1 && !izlazni_meduspremnici.at(id).dodao_novi)
        {
            sem_wait(&sem_ispis);
            cout << "Izlazna dretva " << id << " ispisuje 0 (spremnik prazan)" << endl;
            cout << "IMS[]: ";
            for (int i = 0; i < bid; i++)
            {
                for (int j = 0; j < vms; j++)
                {
                    if (izlazni_meduspremnici.at(i).ims_podaci[j] == '\0')
                    {
                        cout << "-";
                    }
                    else
                    {
                        cout << izlazni_meduspremnici.at(i).ims_podaci[j];
                    }
                }
                cout << " ";
            }
            cout << endl
                 << endl;
            sem_post(&sem_ispis);
        }
        else if (!izlazni_meduspremnici.at(id).dodao_novi)
        {
            sem_wait(&sem_ispis);
            cout << "Izlazna dretva " << id << " ispisuje prethodni podatak " << prosli_podatak << endl;
            cout << "IMS[]: ";
            for (int i = 0; i < bid; i++)
            {
                for (int j = 0; j < vms; j++)
                {
                    if (izlazni_meduspremnici.at(i).ims_podaci[j] == '\0')
                    {
                        cout << "-";
                    }
                    else
                    {
                        cout << izlazni_meduspremnici.at(i).ims_podaci[j];
                    }
                }
                cout << " ";
            }
            cout << endl
                 << endl;
            ;
            sem_post(&sem_ispis);
        }
        else
        {
            sem_wait(&sem_ispis);
            cout << "Izlazna dretva " << id << " ispisuje " << izlazni_meduspremnici.at(id).ims_podaci[izlazni_meduspremnici.at(id).izlaz] << endl;
            prosli_podatak = izlazni_meduspremnici.at(id).ims_podaci[izlazni_meduspremnici.at(id).izlaz];
            izlazni_meduspremnici.at(id).ims_podaci[izlazni_meduspremnici.at(id).izlaz] = '\0';
            cout << "IMS[]: ";
            for (int i = 0; i < bid; i++)
            {
                for (int j = 0; j < vms; j++)
                {
                    if (izlazni_meduspremnici.at(i).ims_podaci[j] == '\0')
                    {
                        cout << "-";
                    }
                    else
                    {
                        cout << izlazni_meduspremnici.at(i).ims_podaci[j];
                    }
                }
                cout << " ";
            }
            cout << endl
                 << endl;
            sem_post(&sem_ispis);
            prvi_rad = 0;
            izlazni_meduspremnici.at(id).izlazni_brojac--;
            if (izlazni_meduspremnici.at(id).izlazni_brojac == 0)
            {
                izlazni_meduspremnici.at(id).dodao_novi = 0;
            }

            izlazni_meduspremnici.at(id).izlaz = (izlazni_meduspremnici.at(id).izlaz + 1) % vms;
        }
        sem_post(&izlazni_meduspremnici.at(id).ims_bs);
        sleep(5);
    }
}

int main(int argc, char const *argv[])
{
    srand(time(NULL));
    cout << "Unesite broj ulaznih dretva: ";
    cin >> bud;
    cout << "Unesite broj radnih dretva: ";
    cin >> brd;
    cout << "Unesite broj izlaznih dretva: ";
    cin >> bid;
    cout << "Unesite veličinu međuspremnika: ";
    cin >> vms;
    sem_init(&sem_ispis, 0, 1);

    for (int i = 0; i < brd; i++)
    {
        ums temp;
        temp.ulaz = 0;
        temp.izlaz = 0;
        sem_init(&temp.ums_bs, 0, 1);
        sem_init(&temp.ums_os, 0, 0);
        temp.ums_podaci = new char[vms];
        ulazni_meduspremnici.push_back(temp);
    }

    for (int i = 0; i < bid; i++)
    {
        ims temp;
        temp.ulaz = 0;
        temp.izlaz = 0;
        sem_init(&temp.ims_bs, 0, 1);
        temp.ims_podaci = new char[vms];
        temp.dodao_novi = 0;
        temp.izlazni_brojac = 0;
        izlazni_meduspremnici.push_back(temp);
    }

    pthread_t ud_id[bud], rd_id[brd], id_id[bid];
    int rid[brd], iid[bid], uid[bud];

    for (int i = 0; i < bud; i++)
    {
        uid[i] = i;
        if (pthread_create(&ud_id[i], NULL, ulazna_dretva, &uid[i]))
        {
            cout << "Greška pri stvaranju ulazne dretve";
            exit(1);
        }
    }

    sleep(3);
    for (int i = 0; i < brd; i++)
    {
        rid[i] = i;
        if (pthread_create(&rd_id[i], NULL, radna_dretva, &rid[i]))
        {
            cout << "Greška pri stvaranju radne dretve";
            exit(1);
        }
    }
    sleep(10);

    for (int i = 0; i < bid; i++)
    {
        iid[i] = i;
        if (pthread_create(&id_id[i], NULL, izlazna_dretva, &iid[i]))
        {
            cout << "Greška pri stvaranju izlazne dretve";
            exit(1);
        }
    }

    while (1)
        ;

    return 0;
}
