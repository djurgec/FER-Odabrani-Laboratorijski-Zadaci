#include <iostream>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <semaphore.h>
#include <mutex>
#include <vector>
#include <list>
#define BC 4
#define BP 3
#define BB 2
using namespace std;

list<int> lista;
list<int>::iterator it;
sem_t sem_ispis;
pthread_mutex_t monitor;
pthread_cond_t red[3];
int br_citaca_ceka, br_citaca_cita,
    br_pisaca_pise, br_pisaca_ceka,
    br_brisaca_ceka, br_brisaca_brise;

void *pisac_dretva(void *arg)
{
    int id = *((int *)arg);
    while (1)
    {
        int x = rand() % 100;
        pthread_mutex_lock(&monitor);
        sem_wait(&sem_ispis);
        cout << "pisac " << id << " zeli dodati vrijednost " << x << " u listu" << endl;
        cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
        cout << "Lista:";
        for (auto it = lista.begin(); it != lista.end(); ++it)
        {
            cout << " " << *it;
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);
        br_pisaca_ceka++;
        while (br_brisaca_brise + br_brisaca_ceka + br_pisaca_pise > 0)
        {
            pthread_cond_wait(&red[0], &monitor);
        }
        br_pisaca_pise++;
        br_pisaca_ceka--;

        lista.push_back(x);
        sem_wait(&sem_ispis);
        cout << "pisac " << id << " dodaje vrijednosti " << x << " na kraj liste" << endl;
        cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
        cout << "Lista:";
        for (auto it = lista.begin(); it != lista.end(); ++it)
        {
            cout << " " << *it;
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);
        pthread_mutex_unlock(&monitor);
        sleep(rand() % 3 + 2);

        pthread_mutex_lock(&monitor);
        br_pisaca_pise--;
        sem_wait(&sem_ispis);
        cout << "pisac " << id << " vise ne koristi listu" << endl;
        cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
        cout << "Lista:";
        for (auto it = lista.begin(); it != lista.end(); ++it)
        {
            cout << " " << *it;
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);
        if (br_brisaca_ceka > 0)
        {
            pthread_cond_signal(&red[2]);
        }
        else if (br_pisaca_ceka > 0)
        {
            pthread_cond_signal(&red[0]);
        }

        pthread_mutex_unlock(&monitor);
        sleep(rand() % 3 + 2);
    }
}

void *citac_dretva(void *arg)
{
    int id = *((int *)arg);
    while (1)
    {
        int x = rand() % lista.size();
        pthread_mutex_lock(&monitor);
        sem_wait(&sem_ispis);
        cout << "citac " << id << " zeli citati element " << x << " liste" << endl;
        cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
        cout << "Lista:";
        for (auto it = lista.begin(); it != lista.end(); ++it)
        {
            cout << " " << *it;
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);
        br_citaca_ceka++;
        while (br_brisaca_brise + br_brisaca_ceka > 0)
        {
            pthread_cond_wait(&red[1], &monitor);
        }
        br_citaca_cita++;
        br_citaca_ceka--;
        auto it = lista.begin();
        advance(it, x);
        int element = *it;
        sem_wait(&sem_ispis);
        cout << "citac " << id << " cita element " << x << " liste (vrijednost=" << element << ")" << endl;
        cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
        cout << "Lista:";
        for (auto it = lista.begin(); it != lista.end(); ++it)
        {
            cout << " " << *it;
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);
        pthread_mutex_unlock(&monitor);
        sleep(rand() % 6 + 5);

        pthread_mutex_lock(&monitor);
        br_citaca_cita--;
        sem_wait(&sem_ispis);
        cout << "citac " << id << " vise ne koristi listu" << endl;
        cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
        cout << "Lista:";
        for (auto it = lista.begin(); it != lista.end(); ++it)
        {
            cout << " " << *it;
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);

        if (br_citaca_cita == 0 && br_brisaca_ceka > 0)
        {
            pthread_cond_signal(&red[2]);
        }

        pthread_mutex_unlock(&monitor);
        sleep(rand() % 6 + 5);
    }
}

void *brisac_dretva(void *arg)
{
    int id = *((int *)arg), x;
    while (1)
    {
        if (!lista.empty())
        {
            x = rand() % lista.size();
        }
        else
            x = 0;
        pthread_mutex_lock(&monitor);
        sem_wait(&sem_ispis);
        cout << "brisac " << id << " zeli obrisati element " << x << " s liste" << endl;
        cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
        cout << "Lista:";
        for (auto it = lista.begin(); it != lista.end(); ++it)
        {
            cout << " " << *it;
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);
        br_brisaca_ceka++;
        while (br_brisaca_brise + br_citaca_cita + br_pisaca_pise > 0)
        {
            pthread_cond_wait(&red[2], &monitor);
        }
        br_brisaca_brise++;
        br_brisaca_ceka--;
        if (!lista.empty() && x < lista.size())
        {
            auto it = lista.begin();
            advance(it, x);
            lista.erase(it);
            sem_wait(&sem_ispis);
            cout << "brisac " << id << " brise element " << x << " s liste" << endl;
            cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
            cout << "Lista:";
            for (auto it = lista.begin(); it != lista.end(); ++it)
            {
                cout << " " << *it;
            }
            cout << endl
                 << endl;
            sem_post(&sem_ispis);
        }
        else if (lista.empty())
        {
            sem_wait(&sem_ispis);
            cout << "brisac " << id << " nije izbrisao ni jedan element jer je lista prazna" << endl;
            cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
            cout << "Lista:";
            for (auto it = lista.begin(); it != lista.end(); ++it)
            {
                cout << " " << *it;
            }
            cout << endl
                 << endl;
            sem_post(&sem_ispis);
        }
        else
        {
            sem_wait(&sem_ispis);
            cout << "brisac " << id << " nije izbrisao element " << x << " s liste jer on ne postoji" << endl;
            cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
            cout << "Lista:";
            for (auto it = lista.begin(); it != lista.end(); ++it)
            {
                cout << " " << *it;
            }
            cout << endl
                 << endl;
            sem_post(&sem_ispis);
        }

        pthread_mutex_unlock(&monitor);
        sleep(rand() % 6 + 5);

        pthread_mutex_lock(&monitor);
        br_brisaca_brise--;
        sem_wait(&sem_ispis);
        cout << "brisac " << id << " vise ne koristi listu" << endl;
        cout << "aktivnih: " << "citaca=" << br_citaca_cita << ", pisaca=" << br_pisaca_pise << ", brisaca=" << br_brisaca_brise << endl;
        cout << "Lista:";
        for (auto it = lista.begin(); it != lista.end(); ++it)
        {
            cout << " " << *it;
        }
        cout << endl
             << endl;
        sem_post(&sem_ispis);
        if (br_brisaca_ceka > 0)
        {
            pthread_cond_signal(&red[2]);
        }
        else if (br_pisaca_ceka > 0)
        {
            pthread_cond_signal(&red[0]);
            pthread_cond_broadcast(&red[1]);
        }
        else if (br_citaca_ceka > 0)
        {
            pthread_cond_broadcast(&red[1]);
        }
        pthread_mutex_unlock(&monitor);
        sleep(rand() % 6 + 10);
    }
}

int main(int argc, char const *argv[])
{
    int cid[BC], pid[BP], bid[BB];
    pthread_t cd_id[BC], pd_id[BP], bd_id[BB];
    pthread_mutex_init(&monitor, NULL);
    pthread_cond_init(&red[0], NULL);
    pthread_cond_init(&red[1], NULL);
    pthread_cond_init(&red[2], NULL);
    sem_init(&sem_ispis, 0, 1);
    srand(time(NULL));
    for (int i = 0; i < BP; i++)
    {
        pid[i] = i;
        if (pthread_create(&pd_id[i], NULL, pisac_dretva, &pid[i]))
        {
            cout << "Greška pri stvaranju dretve pisača";
            exit(1);
        }
    }
    sleep(20);
    for (int i = 0; i < BC; i++)
    {
        cid[i] = i;
        if (pthread_create(&cd_id[i], NULL, citac_dretva, &cid[i]))
        {
            cout << "Greška pri stvaranju dretve čitača";
            exit(1);
        }
    }
    sleep(10);
    for (int i = 0; i < BB; i++)
    {
        bid[i] = i;
        if (pthread_create(&bd_id[i], NULL, brisac_dretva, &bid[i]))
        {
            cout << "Greška pri stvaranju dretve brisača";
            exit(1);
        }
    }

    while (1)
        ;

    return 0;
}
