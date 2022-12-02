class ArrayList:
    def __init__(self):
        self.inArray = [0 for i in range(10)]
        self.count = 0
        
    def get(self, i):
        return self.inArray[i]

    def set(self, i, e):
        self.inArray[i] = e

    def length(self):
        return self.count

    def append(self, e):
        self.inArray[self.count] = e
        self.count += 1
        if len(self.inArray) == self.count:
            self._resizeUp()     # resize array if reached capacity

    def insert(self, i, e):
        for j in range(self.count,i,-1):
            self.inArray[j] = self.inArray[j-1]
        self.inArray[i] = e
        self.count += 1
        if len(self.inArray) == self.count:
            self._resizeUp()     # resize array if reached capacity

    def remove(self, i):
        self.count -= 1
        val = self.inArray[i]
        for j in range(i,self.count):
            self.inArray[j] = self.inArray[j+1]
        return val
    
    def __str__(self):
        return str(self.inArray[:self.count])

    def _resizeUp(self):
        newArray = [0 for i in range(2*len(self.inArray))]
        for j in range(len(self.inArray)):
            newArray[j] = self.inArray[j]
        self.inArray = newArray

class Heap:
    def __init__(self):
        self.inList = ArrayList()
        self.size = 0
        
    def __str__(self):
        return str(self.inList)
 
    def add(self,d):
        # add d in the bottom leaf position
        self.inList.append(d)
        
        # and then pull it up in its right position by swapping
        pos = self.size # position of "offending element"
        
        # if element in position pos is larger than its parent, swap them
        while pos > 0 and self.inList.get(pos) > self.inList.get((pos-1)//2): 
            self._swap(pos,(pos-1)//2)
            pos = (pos-1)//2              

        # increase the size of the heap
        self.size += 1

    def removeRoot(self):
        # store the root somewhere
        val = self.inList.get(0)
        
        # set the root to the value of the bottom leaf
        self.inList.set(0,self.inList.get(self.size-1))
        self.inList.remove(self.size-1)
        self.size -= 1
        
        # fix the heap (heapify)
        self.heapify(0)

        return val    
    
    def heapify(self,pos): # fixes a heap that is possibly broken in position pos
        
        # if there is no left child, heap is fine, return
        if self.size <= 2*pos+1: return
        
        # compare element at pos with its children and fix if needed
        
        # pos has children left: 2*pos+1 and right: 2*pos+2
        if self.size <= 2*pos+2 or self.inList.get(2*pos+1) >= self.inList.get(2*pos+2):
            maxChild = 2*pos+1
        else: maxChild = 2*pos+2

        # compare maxChild with pos
        if self.inList.get(pos) < self.inList.get(maxChild):
            self._swap(pos,maxChild)
            self.heapify(maxChild)

    def _swap(self,i,j):
        temp = self.inList.get(i)
        self.inList.set(i,self.inList.get(j))
        self.inList.set(j,temp)    

    def append(self, d):
        self.inList.append(d)
        self.size += 1
        
    def addAll(self, A): # just to help with testing
        for x in A: self.add(x)

    def max(self):
        if self.inList:
            return self.inList.get(0)

    def _search(self, d):
        for i in range(self.size):
            if d == self.inList.get(i):
                return i
        return -1

    def _remove(self, i):
        self.inList.set(i, self.inList.get(self.size-1))
        self.inList.remove(self.size-1)
        self.size -= 1
        self.heapify(i)
        while i > 0 and self.inList.get(i) > self.inList.get((i-1)//2): 
            self._swap(i,(i-1)//2)
            i = (i-1)//2

    def removeVal(self, d):
        i = self._search(d)
        if i != -1:
            self._remove(i)
            return i

    def clone(self):
        h = Heap()
        for i in range(self.size):
            h.add(self.inList.get(i))
        h.size = self.size
        return h

    def toSortedArray(self):
        if self.inList:
            a = [self.inList.get(i) for i in range(self.size)]
            q = [(0, self.size-1)]
            while q:
                lo, hi = q.pop(0)
                pivot = a[hi]
                i = lo - 1
                for j in range(lo, hi):
                    if a[j] <= pivot:
                        i += 1
                        temp = a[i]
                        a[i] = a[j]
                        a[j] = temp
                i += 1
                temp = a[i]
                a[i] = a[hi]
                a[hi] = temp
                if lo < i-1:
                    q.append((lo, i-1))
                if i+1 < hi:
                    q.append((i+1, hi))
            return a
            
                


h = Heap()
h.addAll([4,42,0,-4,124,0,34,0,43])
print(h)
print('max:', h.max())
print('First occurance of 34 is at', h._search(34))
h._remove(2)
print(h)
print(h.removeVal(42), h)
print('Sorted:', h.toSortedArray())