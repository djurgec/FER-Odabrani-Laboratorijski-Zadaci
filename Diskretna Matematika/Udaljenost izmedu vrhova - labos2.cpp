#include <cmath>
#include <iostream>
#include <stdlib.h>
#include <vector>

using namespace std;

int minUdaljenost(vector<unsigned long long> udaljenost, vector<bool> s0,
                  int n) {
   unsigned long long min = INT64_MAX;
   int min_index;
   for (int i = 0; i < n; ++i) {
      if (s0[i] == false && udaljenost[i] <= min) {
         min = udaljenost[i];
         min_index = i;
      }
   }
   return min_index;
}

int main(int argc, char const *argv[]) {
   int n, a, b, c, k, l;

   cout << "Unesite prirodan broj n:";
   cin >> n;
   cout << "Unesite prirodan broj a:";
   cin >> a;
   cout << "Unesite prirodan broj b:";
   cin >> b;
   cout << "Unesite prirodan broj c:";
   cin >> c;
   cout << "Unesite vrh k:";
   cin >> k;
   cout << "Unesite vrh l:";
   cin >> l;
   vector<vector<unsigned long long>> matrica(n, vector<unsigned long long>(n));
   for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
         if (i == j)
            matrica[i][j] = 0;
         else {
            matrica[i][j] = pow(a, abs(i - j) % c) + b * pow(i - j, 2) - 1;
         }
      }
   }
   vector<unsigned long long> udaljenost(n);
   vector<bool> s0(n);
   for (int i = 0; i < n; ++i) {
      udaljenost[i] = matrica[k - 1][i];
      s0[i] = false;
   }
   for (int i = 0; i < n - 1; ++i) {
      int min = minUdaljenost(udaljenost, s0, n);
      s0[min] = true;
      for (int j = 0; j < n; ++j) {
         if (s0[j] == false &&
             udaljenost[min] + matrica[min][j] < udaljenost[j]) {
            udaljenost[j] = udaljenost[min] + matrica[min][j];
         }
      }
      if (s0[l - 1])
         break;
   }
   cout << "Udaljenost izmedu vrhova k i l je " << udaljenost[l - 1] << endl;
}
