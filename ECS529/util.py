def append(A, k):
    B = [0 for i in range(len(A)+1)]
    for i in range(len(A)):
        B[i] = A[i]
        B[len(A)] = k
    return B

def insertion_sort(a):
    for i in range(1, len(a)):
        for j in range(i, 1, -1):
            if a[j] < a[j-1]:
                temp = a[j]
                a[j] = a[j-1]
                a[j-1] = temp

def selection_sort(a):
    for i in range(len(a) - 1):
        x = i
        for j in range(i, len(a)):
            if a[j] < a[x]:
                x = j
        temp = a[i]
        a[i] = a[x]
        a[x] = temp

x = [1,5,4,3,7,8,5,10,10]
for f in (insertion_sort, selection_sort):
    y = x
    f(y)
    print(y)