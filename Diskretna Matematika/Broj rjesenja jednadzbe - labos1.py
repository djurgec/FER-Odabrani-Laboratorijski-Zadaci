import math
import numpy

def fillArray(x):
    array = [None] * 2
    array[0]=0
    array[1]=-x-1
    return array

def multiply_arrays(arr1, arr2, rez):
    counter=0
    for i in range (len(arr1)):
        for j in range(len(arr2)):
            if((arr1[i]<0 and arr2[j]>0) or (arr1[i]>0 and arr2[j]<0)):
                if(arr1[i]<0):
                    result=arr1[i]-arr2[j]
                else:
                    result=arr2[j]-arr1[i]
            else:
                result=arr1[i] + arr2[j]
            if(arr1[i]<0 and arr2[j]<0):
                result=-result
            rez[counter]=result
            counter=counter+1

def filter (arr, value):
     arr[:]=[element for element in arr if abs(element) <= value]

m = int(input("Unesite nenegativan cijeli broj m: "))
x1 = int(input("Unesite nenegativan cijeli broj a_1: "))
x2 = int(input("Unesite nenegativan cijeli broj a_2: "))
x3 = int(input("Unesite nenegativan cijeli broj a_3: "))
x4 = int(input("Unesite nenegativan cijeli broj a_4: "))
array1 = fillArray(x1)
array2 = fillArray(x2)
array3 = fillArray(x3)
array4 = fillArray(x4)
faktor1 = [0] * (len(array1) * len(array2))
faktor2 = [0] * (len(array3) * len(array4))
umnozak = [0] * (len(faktor1) * len(faktor2))
multiply_arrays(array1, array2, faktor1)
filter(faktor1, m)
multiply_arrays(array3, array4, faktor2)
filter(faktor2, m)
multiply_arrays(faktor1, faktor2, umnozak)
filter(umnozak, m)
result=math.comb(m+3, 3)
for x in range(len(umnozak)):
    if (umnozak[x]==0):
        continue
    if (umnozak[x]<0):
        result=result - math.comb(m-abs(umnozak[x])+3, 3)
    else:
        result=result + math.comb(m-abs(umnozak[x])+3, 3)

print("Broj rjesenja jednadzbe je: ")
print(result)



