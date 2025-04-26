#include<iostream>
#include<random>
#include <time.h>
#include <unistd.h>
#define BROJ_STRANICA 16
#define BROJ_OKVIRA 4
#define VELICINA_STRANICE 64
using namespace std;


int main(int argc, char const *argv[])
{
    int adresa, stranica, zadnji_napunjen_okvir = 0;
    int sadrzaj_okvira[BROJ_OKVIRA];
    bool bitovi_prisutnosti[BROJ_STRANICA], okvir_pun[BROJ_OKVIRA], pronasao_okvir=0;
    srand(time(NULL));
    for (int i = 0; i < BROJ_OKVIRA; i++)
    {
        okvir_pun[i]=0;
        sadrzaj_okvira[i]=-1;
    }
    for (int i = 0; i < BROJ_STRANICA; i++)
    {
        bitovi_prisutnosti[i]=0;
    }
    while (1)
    {
        adresa = rand() % (VELICINA_STRANICE*BROJ_STRANICA);
        stranica = adresa/VELICINA_STRANICE;
        cout << "Izgenerirana logicka adresa 0x" << hex << adresa << ", koja se nalazi u stranici " << dec << stranica << endl;
        if (bitovi_prisutnosti[stranica]==0)
        {
            cout << "Promasaj!" <<endl;
            bitovi_prisutnosti[stranica]=1;
            for (int i = 0; i < BROJ_OKVIRA; i++)
            {
                if (okvir_pun[i]==0)
                {
                    okvir_pun[i]=1;
                    pronasao_okvir=1;
                    cout << "Smjestam stranicu " << stranica<< " u okvir " << i << endl;
                    sadrzaj_okvira[i]=stranica;
                    break;
                }
            }
            if (pronasao_okvir==0)
            {
                cout << "Iz okvira " << zadnji_napunjen_okvir << " izbacujem stranicu " << sadrzaj_okvira[zadnji_napunjen_okvir] << endl;
                bitovi_prisutnosti[sadrzaj_okvira[zadnji_napunjen_okvir]]=0;
                sadrzaj_okvira[zadnji_napunjen_okvir]=stranica;
                zadnji_napunjen_okvir = (zadnji_napunjen_okvir + 1) % BROJ_OKVIRA;
            }
        }
        else{
            cout << "Pogodak!" << endl;
            cout << "Potrebna stranica se nalazi u memoriji u okviru ";
            for (int i = 0; i < BROJ_OKVIRA; i++)
            {
                if (sadrzaj_okvira[i]==stranica)
                {
                    cout << i << endl;
                    break;
                }
            }
        }
        cout << "---------------------------" << endl;
        cout << "BITOVI PRISUTNOSTI: " << endl;
        for (int i = 0; i < BROJ_STRANICA; i++)
        {
            cout << "Stranica " << i << " -> "  << bitovi_prisutnosti[i] << endl;
        }
        cout << "---------------------------" << endl;
        cout << "SADRZAJ OKVIRA: " << endl;
        for (int i = 0; i < BROJ_OKVIRA; i++)
        {
            if (sadrzaj_okvira[i]==-1)
            {
                cout << "Okvir " << i << " je prazan" << endl;
            }
            else{
                cout << "U okviru " << i << " se nalazi stranica " << sadrzaj_okvira[i] << endl;
            }
        }
        cout << "---------------------------"<< endl << endl;
        pronasao_okvir=0;
        sleep(2);
    }
    
    return 0;
}
