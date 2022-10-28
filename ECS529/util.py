def append(A, k):
    B = [0 for i in range(len(A)+1)]
    for i in range(len(A)):
        B[i] = A[i]
        B[len(A)] = k
    return B