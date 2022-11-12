class Node:
    def __init__(self, d, n):
        self.data = d
        self.next = n

class LinkedList:
    def __init__(self):
        self.head = None
        self.length = 0

    def __str__(self):
        if self.head == None: 
            return "empty"
        st = "-"
        ptr = self.head
        while ptr != None:
            st += "-> "+str(ptr.data)+" "
            ptr = ptr.next
        return st+"|"
        
    def search(self, d):
        i = 0
        ptr = self.head
        while ptr != None:
            if ptr.data == d:
                return i
            ptr = ptr.next
            i += 1
        return -1
    
    def search2(self, d): # recursive implementation
        def searchRec(ptr,d,i):
            if ptr == None: return -1
            if prt.data == d: return i
            return searchRec(ptr.next,d,i+1)
        
        return searchRec(self.head,d,0)    
    
    def append(self, d):
        if self.head == None:      
            self.head = Node(d,None) 
        else:
            ptr = self.head
            while ptr.next != None:
                ptr = ptr.next
            ptr.next = Node(d,None)
        self.length += 1

    def appendAll(self, a):
        for node in a:
            self.append(node)

    def insert(self, i, d):
        if self.head == None or i == 0:
            self.head = Node(d,self.head)
        else:
            ptr = self.head
            while i>1 and ptr.next != None:
                ptr = ptr.next
                i -= 1
            ptr.next = Node(d,ptr.next)
        self.length += 1

    def insertRec(ptr,i,d):
        if i == 0: # base case
            return Node(d,ptr)
        if i > 0:
            if ptr.next:
                n = LinkedList.insertRec(ptr.next, i-1, d)
                return Node(ptr.data, n)
            return Node(d, None)

    def insert2(self, i, d):
        if i < 0:
            i = 0
        elif i > self.length:
            i = self.length
        self.head = LinkedList.insertRec(self.head, i, d)
        self.length += 1


    def remove(self, i): # removes i-th element and returns it
        if self.head == None:
            return None
        if i == 0:
            val = self.head.data
            self.head = self.head.next
            self.length -= 1
            return val
        ptr = self.head
        while ptr.next != None:
            if i == 1:
                val = ptr.next.data
                ptr.next = ptr.next.next
                self.length -= 1
                return val                
            ptr = ptr.next
            i -= 1
            
    def removeVal(self, d):
        if self.head == None:
            return -1
        if self.head.data == d:
            self.head = self.head.next
            self.length -= 1
            return 0
        else:
            i = 0
            ptr = self.head	
            while ptr.next != None:
                if ptr.next.data == d:
                    ptr.next = ptr.next.next
                    self.length -= 1
                    return i+1
                ptr = ptr.next
                i += 1
        return -1
    
    def sublist(self, i):
        ptr = self.head
        ls = LinkedList()
        ls.length = self.length
        while ptr != None and i>0:
            ptr = ptr.next
            i -= 1
            ls.length -= 1
        ls.head = ptr
        return ls
    
    def get(self, i):
        ptr = self.head
        while i > 0:
            ptr = ptr.next
            i -= 1
        return ptr.data
    
    def set(self, i, d):
        ptr = self.head
        while i > 0:
            ptr = ptr.next
            i -= 1
        ptr.data = d

    def nullify(self):
        self.__init__()

    def clone(self):
        ls = LinkedList()
        ls.head = self.head
        ls.length = self.length
        return ls

    def merge(self, other):
        if self.length:
            ptr = self.head
            while ptr.next:
                ptr = ptr.next
            ptr.next = other.head
            self.length += other.length
        else:
            self.head = other.head
            self.length = other.length
        other.nullify()

    def sort(self):
        if self.length <= 1:
            return
        smaller, other = LinkedList(), LinkedList()
        pivot, ptr = self.head.data, self.head
        while ptr.next:
            ptr = ptr.next
            if ptr.data < pivot:
                smaller.append(ptr.data)
            else:
                other.append(ptr.data)
        smaller.sort()
        other.sort()
        smaller.append(pivot)
        smaller.merge(other)
        self.head = smaller.head

    def isCyclic(self):
        if self.head:
            ptr, trace = self.head, []
            while ptr.next:
                if ptr.next == ptr or ptr in trace:
                    return True
                trace.append(ptr)
                ptr = ptr.next
        return False



# some testing
ls = LinkedList()
ls.appendAll([1,2,3])
print('Init:', ls)
ls.nullify()
print('Nullified:', ls)
#ls.appendAll([1,2])
ls2 = ls.clone()
print('Cloning ls to ls2:', ls2)
ls2.appendAll([3,5,7,8,6,4])
print('Merge')
print(ls)
ls.merge(ls2)
print(f'ls1: {ls.length} {ls} ls2: {ls2.length} {ls2}')
ls2.appendAll([2,4,6,1,3])
ls.merge(ls2)
print(f'ls1: {ls.length} {ls} ls2: {ls2.length} {ls2}')
ls.insert2(3, 69)
ls2.insert2(333, 70)
print('Inserted',ls, ls2)
ls.sort()
print('Sorted:', ls)
print('Cyclic?', ls.isCyclic())
ls.head.next.next.next = ls.head
print('3rd node pointing to head. Cyclic?', ls.isCyclic())
ls = LinkedList()
ls.head = Node(3, None)
ls.head.next = ls.head
print('Head point to head.Cyclic?', ls.isCyclic())

class DNode:

    def __init__(self, d, n, p):
        self.data = d
        self.next = n
        self.prev = p

class DLinkedList:
    def __init__(self):
        self.head = None
        self.tail = None
        self.length = 0

    def __str__(self):
        st = ""
        ptr = self.head
        while ptr != None:
            st = st + str(ptr.data)
            st = st+" -> "
            ptr = ptr.next
        st += "None, and reversed: "
        ptr = self.tail
        while ptr != None:
            st = st + str(ptr.data)
            st = st+" -> "
            ptr = ptr.prev
        return st+"None"

    def nullify(self):
        self.__init__()

    def append(self, d):
        if self.length:
            self.tail.next = DNode(d, None, self.tail)
            self.tail = self.tail.next
        else:
            self.head = self.tail = DNode(d, None, None)
        self.length += 1

    def appendAll(self, a):
        if self.length:
            for d in a:
                self.tail.next = DNode(d, None, self.tail)
                self.tail = self.tail.next
        else:
            self.head = self.tail = DNode(a[0], None, None)
            for i in range(1, len(a)):
                self.head.next = self.tail = DNode(a[i], None, self.head)
        self.length += len(a)

    def search(self, d):
        i = 0
        ptr = self.head
        while ptr != None:
            if ptr.data == d:
                return i
            ptr = ptr.next
            i += 1
        return -1

    def insert(self, i, d):
        if self.head == None or i == 0:
            self.head = DNode(d,self.head, None)
        else:
            ptr = self.head
            t = i
            while i>1 and ptr.next != None:
                ptr = ptr.next
                i -= 1
            ptr.next = DNode(d,ptr.next, ptr)
            ptr = ptr.next
            if ptr.next:
                ptr.next.prev = ptr
            if t == self.length:
                self.tail = self.tail.next
        self.length += 1

    def remove(self, i): # removes i-th element and returns it
        if self.head == None:
            return None
        if i == 0:
            val = self.head.data
            self.length -= 1
            if self.length == 0:
                self.head = self.tail = None
                return val
            self.head = self.head.next
            self.head.prev = None
            return val
        if i == self.length-1:
            val = self.tail.data
            self.tail = self.tail.prev
            self.tail.next = None
            self.length -= 1
            return val
        ptr = self.head
        while ptr.next != None:
            if i == 1:
                val = ptr.next.data
                ptr.next = ptr.next.next
                ptr.next.prev = ptr
                self.length -= 1
                return val                
            ptr = ptr.next
            i -= 1


print('RRRRRRRR')
ls = DLinkedList()
print("1. creation test:",ls,ls.length)
for x in [0,1,3]: ls.append(x)
print("2. append test:",ls,ls.length)
ls.insert(0,-2); ls.insert(1,-1)
print("3. insert test:",ls,ls.length)
ls.insert(4,2)
print("4. insert test:",ls,ls.length)
ls.insert(6,4)
print("4. insert test:",ls,ls.length)
print("5. search test:",ls.search(1),ls.search(2))
ls.remove(0)
print("6. remove test:",ls,ls.length,ls.tail.data)
ls.remove(5)
print("7. remove test:",ls,ls.length,ls.tail.data)
print("8. search test:",ls.search(1),ls.search(2),ls.search(3),ls.search(4))
ls.insert(4,1)
print("9. insert test:",ls,ls.length,ls.tail.data)
print("10. search test:",ls.search(1),ls.search(2),ls.search(3),ls.search(4))
for i in range(ls.length-1,-1,-1): ls.remove(i)
print("11. remove test:",ls,ls.length,ls.tail)