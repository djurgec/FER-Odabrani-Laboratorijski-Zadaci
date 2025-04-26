#include <iostream>
#include <csignal>
#include <unistd.h>
#include <pthread.h>

using namespace std;

bool pokrenuto = true;
int tp = 0;
int kon[5] = {-1, -1, -1, -1, -1}, oznake[5];

void obradi_sigint()
{

    cout << "---------Pocetak obrade signala (SIGINT)---------" << endl;
    cout << "STRUKTURE PODATAKA:" << endl;
    cout << "Tekuci prioritet - " << tp << endl;
    cout << "Oznake cekanja - ";
    for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
    {
        cout << oznake[i];
    }
    cout << endl;
    for (int i = 1; i < 5; i++)
    {
        cout << "Na kon[" << i << "]";
        if (kon[i] == 0)
        {
            cout << " je spremljen kontekst dretve glavnog programa";
        }
        else if (kon[i] == 1)
        {
            cout << " je spremljen kontekst dretve prekida SIGUSR1";
        }
        else if (kon[i] == 2)
        {
            cout << " je spremljen kontekst dretve prekida SIGUSR2";
        }
        else if (kon[i] == 3)
        {
            cout << " je spremljen kontekst dretve prekida SIGIO";
        }
        else if (kon[i] == 4)
        {
            cout << " je spremljen kontekst dretve prekida SIGINT";
        }
        else
            cout << " nije spremljeno nista";
        cout << endl;
    }
    sleep(1);
    for (int i = 1; i <= 10; i++)
    {
        cout << "Obrada signala (SIGINT) : " << i << "/10" << endl;
        sleep(1);
    }
    cout << "---------Kucanski poslovi (Kraj obrade signala (SIGINT))---------" << endl;
    sleep(0.5);
}

void obradi_sigio()
{

    cout << "---------Pocetak obrade signala (SIGIO)---------" << endl;
    cout << "STRUKTURE PODATAKA:" << endl;
    cout << "Tekuci prioritet - " << tp << endl;
    cout << "Oznake cekanja - ";
    for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
    {
        cout << oznake[i];
    }
    cout << endl;
    for (int i = 1; i < 5; i++)
    {
        cout << "Na kon[" << i << "]";
        if (kon[i] == 0)
        {
            cout << " je spremljen kontekst dretve glavnog programa";
        }
        else if (kon[i] == 1)
        {
            cout << " je spremljen kontekst dretve prekida SIGUSR1";
        }
        else if (kon[i] == 2)
        {
            cout << " je spremljen kontekst dretve prekida SIGUSR2";
        }
        else if (kon[i] == 3)
        {
            cout << " je spremljen kontekst dretve prekida SIGIO";
        }
        else if (kon[i] == 4)
        {
            cout << " je spremljen kontekst dretve prekida SIGINT";
        }
        else
            cout << " nije spremljeno nista";
        cout << endl;
    }
    sleep(1);
    for (int i = 1; i <= 10; i++)
    {
        cout << "Obrada signala (SIGIO) : " << i << "/10" << endl;
        sleep(1);
    }
    cout << "---------Kucanski poslovi (Kraj obrade signala (SIGIO))---------" << endl;
    sleep(0.5);
}

void obradi_sigusr2()
{

    cout << "---------Pocetak obrade signala (SIGUSR2)---------" << endl;
    cout << "STRUKTURE PODATAKA: " << endl;
    cout << "Tekuci prioritet - " << tp << endl;
    cout << "Oznake cekanja - ";
    for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
    {
        cout << oznake[i];
    }
    cout << endl;

    for (int i = 1; i < 5; i++)
    {
        cout << "Na kon[" << i << "]";
        if (kon[i] == 0)
        {
            cout << " je spremljen kontekst dretve glavnog programa";
        }
        else if (kon[i] == 1)
        {
            cout << " je spremljen kontekst dretve prekida SIGUSR1";
        }
        else if (kon[i] == 2)
        {
            cout << " je spremljen kontekst dretve prekida SIGUSR2";
        }
        else if (kon[i] == 3)
        {
            cout << " je spremljen kontekst dretve prekida SIGIO";
        }
        else if (kon[i] == 4)
        {
            cout << " je spremljen kontekst dretve prekida SIGINT";
        }
        else
            cout << " nije spremljeno nista";
        cout << endl;
    }
    sleep(1);
    for (int i = 1; i <= 10; i++)
    {
        cout << "Obrada signala (SIGUSR2) : " << i << "/10" << endl;
        sleep(1);
    }
    cout << "---------Kucanski poslovi (Kraj obrade signala (SIGUSR2))---------" << endl;
    sleep(0.5);
}

void obradi_sigusr1()
{

    cout << "---------Pocetak obrade signala (SIGUSR1)---------" << endl;
    cout << "STRUKTURE PODATAKA:" << endl;
    cout << "Tekuci prioritet - " << tp << endl;
    cout << "Oznake cekanja - ";
    for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
    {
        cout << oznake[i];
    }
    cout << endl;
    for (int i = 1; i < 5; i++)
    {
        cout << "Na kon[" << i << "]";
        if (kon[i] == 0)
        {
            cout << " je spremljen kontekst dretve glavnog programa";
        }
        else if (kon[i] == 1)
        {
            cout << " je spremljen kontekst dretve prekida SIGUSR1";
        }
        else if (kon[i] == 2)
        {
            cout << " je spremljen kontekst dretve prekida SIGUSR2";
        }
        else if (kon[i] == 3)
        {
            cout << " je spremljen kontekst dretve prekida SIGIO";
        }
        else if (kon[i] == 4)
        {
            cout << " je spremljen kontekst dretve prekida SIGINT";
        }
        else
            cout << " nije spremljeno nista";
        cout << endl;
    }
    sleep(1);
    for (int i = 1; i <= 10; i++)
    {
        cout << "Obrada signala (SIGUSR1) : " << i << "/10" << endl;
        sleep(1);
    }
    cout << "---------Kucanski poslovi (Kraj obrade signala (SIGUSR1))---------" << endl;
    sleep(0.5);
}

void blokiraj_odblokiraj_signale(int blokiraj)
{
    sigset_t signali;
    sigemptyset(&signali);
    sigaddset(&signali, SIGINT);
    sigaddset(&signali, SIGIO);
    sigaddset(&signali, SIGUSR2);
    sigaddset(&signali, SIGUSR1);
    if (blokiraj == 1)
        pthread_sigmask(SIG_BLOCK, &signali, NULL);
    else
        pthread_sigmask(SIG_UNBLOCK, &signali, NULL);
}

void obradi_signal(int sig)
{
    int j;
    if (sig == SIGINT)
    {
        j = 4;
        oznake[j] = 1;
        cout << "---------Kućanski poslovi(Dogodio se prekid SIGINT)---------" << endl;
        sleep(0.5);
        cout << "Tekuci prioritet - " << tp << endl;
        cout << "Oznake cekanja - ";
        for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
        {
            cout << oznake[i];
        }
        cout << endl;
        cout << "---------------------------------------------" << endl;
    }
    else if (sig == SIGIO)
    {
        j = 3;
        oznake[j] = 1;
        cout << "---------Kućanski poslovi(Dogodio se prekid SIGIO)---------" << endl;
        sleep(0.5);
        cout << "Tekuci prioritet - " << tp << endl;
        cout << "Oznake cekanja - ";
        for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
        {
            cout << oznake[i];
        }
        cout << endl;
        cout << "---------------------------------------------" << endl;
        sleep(0.5);
    }
    else if (sig == SIGUSR2)
    {
        j = 2;
        oznake[j] = 1;
        cout << "---------Kućanski poslovi(Dogodio se prekid SIGUSR2)---------" << endl;
        sleep(0.5);
        cout << "Tekuci prioritet - " << tp << endl;
        cout << "Oznake cekanja - ";
        for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
        {
            cout << oznake[i];
        }
        cout << endl;
        cout << "---------------------------------------------" << endl;
    }
    else if (sig == SIGUSR1)
    {
        j = 1;
        oznake[j] = 1;
        cout << "---------Kućanski poslovi(Dogodio se prekid SIGUSR1)---------" << endl;
        sleep(0.5);
        cout << "Tekuci prioritet - " << tp << endl;
        cout << "Oznake cekanja - ";
        for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
        {
            cout << oznake[i];
        }
        cout << endl;
        cout << "---------------------------------------------" << endl;
    }
    for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
    {
        if (oznake[i] != 0)
            j = i;
    }

    while (j > tp)
    {
        oznake[j] = 0;
        kon[j] = tp;
        tp = j;
        blokiraj_odblokiraj_signale(0);
        if (tp == 4)
        {
            obradi_sigint();
        }
        else if (tp == 3)
        {
            obradi_sigio();
        }
        else if (tp == 2)
        {
            obradi_sigusr2();
        }

        else if (tp == 1)
        {
            obradi_sigusr1();
        }

        blokiraj_odblokiraj_signale(1);
        tp = kon[j];
        kon[j] = -1;
        j = 0;
        for (int i = 1; i < sizeof(oznake) / sizeof(oznake[0]); i++)
        {
            if (oznake[i] != 0)
                j = i;
        }
    }
    return;
}

int main(int argc, char const *argv[])
{
    struct sigaction act;
    // svaki signal se obraduje u jednom prekidnom potprogramu
    act.sa_handler = obradi_signal;
    act.sa_flags = 0; // napredne mogucnosti signala preskocene
    sigaction(SIGINT, &act, NULL);
    sigaction(SIGIO, &act, NULL);
    sigaction(SIGUSR2, &act, NULL);
    sigaction(SIGUSR1, &act, NULL);

    cout << "Program s PID = " << (long)getpid() << " krenuo s radom." << endl;
    int i = 1;
    while (pokrenuto)
    {
        cout << "Program: iteracija " << i++ << endl;
        sleep(1);
    }

    return 0;
}
