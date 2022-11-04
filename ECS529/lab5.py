class ArrayList:
    def __init__(self):  #  we need an internal array and a count
        self.inArray = [0 for i in range(10)]
        self.count = 0
        
    def get(self, i):  # we do not check bounds!
        self._checkBounds(i)
        return self.inArray[i]

    def set(self, i, e):  # we do not check bounds!
        self._checkBounds(i)
        self.inArray[i] = e

    def length(self):
        return self.count

    def append(self, e):  # assumption: there is always at least one empty space to append
        self.inArray[self.count] = e
        self.count += 1
        if len(self.inArray) == self.count:
            self._resizeUp()     # resize array if reached capacity

    def appendAll(self, A):
        for e in A:
            self.append(e)

    def toArray(self):
        return self.inArray[:self.count]
            
    def insert(self, i, e):
        self._checkBounds(i)

        for j in range(self.count,i,-1):
            self.inArray[j] = self.inArray[j-1]
        self.inArray[i] = e
        self.count += 1
        if len(self.inArray) == self.count:
            self._resizeUp()     # resize array if reached capacity

    def remove(self, i):
        self._checkBounds(i)

        self.count -= 1
        val = self.inArray[i]
        for j in range(i,self.count):
            self.inArray[j] = self.inArray[j+1]
        return val

    def removeVal(self, e):
        for i in range(self.count):
            if self.inArray[i] == e:
                self.remove(i)
                return i
        return -1

    def removeInterval(self, i, j):
        if j < i:
            return False
        self._checkBounds(i)
        self._checkBounds(j)

        y = 0
        for x in range(j+1, self.count):
            self.inArray[i+y] = self.inArray[x]
            y += 1
        self.count -= j-i+1
        return True

    def sort(self):
        for i in range(1, self.count):
            for j in range(i, 0, -1):
                if self.inArray[j] < self.inArray[j-1]:
                    temp = self.inArray[j]
                    self.inArray[j] = self.inArray[j-1]
                    self.inArray[j-1] = temp

    
    def __str__(self):
        if self.count == 0: return "[]"
        s = "["
        for i in range(self.count-1): s += str(self.inArray[i])+", "
        return s+str(self.inArray[self.count-1])+"]"

    def _resizeUp(self):  # is called when len(inArray) == count
        newArray = [0 for i in range(2*len(self.inArray))]
        for j in range(len(self.inArray)):
            newArray[j] = self.inArray[j]
        self.inArray = newArray

    def _checkBounds(self, i):  # checks whether i is in [0,hi]
        if i < 0 or i > self.count-1:
            raise Exception("index "+str(i)+" out of bounds!")

arr = ArrayList()
for i in range(9): arr.append(i)
print(f'Init {arr}')
print('\nQ1: appendAll(), toArray()')
arr.appendAll([5,12,69])
print(f'Result {arr} {arr.toArray()}')
print('\nQ2 Out of bounds exception')
try:
    arr.get(-9999)
except Exception as e:
    print(e)
print('\nQ3: removeVal()')
print(f'Removing 12. Returned value: {arr.removeVal(12)}. Array now: {arr}')
print(f'Removing 123. Returned value: {arr.removeVal(123)}. Array now: {arr}')
print('\nQ4: sort')
arr.sort()
print(arr)
print('\nQ5: Remove interval 1-4')
arr.removeInterval(1, 4)
print(arr, arr.count)