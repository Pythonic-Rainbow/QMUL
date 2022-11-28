class BTNode:

    def __init__(self, data):
        self.data = data
        self.left = self.right = None

    def __str__(self):
        return str(self.data)

    def niceStr(self): # this goes in the BTNode class
        S = ["├","─","└","│"]
        angle = S[2]+S[1]+" "
        vdash = S[0]+S[1]+" "
        
        def niceRec(ptr,acc,pre):
            if ptr == None: return acc+pre+"None"
            if ptr.left==ptr.right==None: return acc+pre+str(ptr.data)
            if pre == vdash: pre2 = S[3]+"  "
            elif pre == angle: pre2 = "   "
            else: pre2 = ""
            left = niceRec(ptr.right,acc+pre2,vdash)
            right = niceRec(ptr.left,acc+pre2,angle)
            return acc+pre+str(ptr.data)+"\n"+left+"\n"+right
            
        return niceRec(self,"","")

    def remove(self):
        if self.left:
            if self.right:
                ptr = self.right
                while ptr.left:
                    if ptr.left.left:
                        ptr = ptr.left
                    else:
                        self.data = ptr.left.data
                        ptr.left = ptr.left.remove()
                        return self
                self.data = ptr.data
                self.right = self.right.remove()
                return self
            return self.left
        elif self.right:
            return self.right

class BST:

    def __init__(self, *data):
        self.root = BTNode(data[0]) if data else None
        for i in range(1, len(data)):
            self.add(data[i])

    def __str__(self):
        return self.root.niceStr() if self.root else 'None'

    def add(self, data):
        if self.root:
            ptr = self.root
            while True:
                if data < ptr.data:
                    if ptr.left:
                        ptr = ptr.left
                    else:
                        ptr.left = BTNode(data)
                        return
                elif ptr.right:
                    ptr = ptr.right
                else:
                    ptr.right = BTNode(data)
                    return
        self.root = BTNode(data)

    def removeAll(self, data):
        i = 0
        while self.root.data == data:
            self.root = self.root.remove()
            i += 1
        ptr = self.root
        while ptr:
            if data < ptr.data:
                while ptr.left and data == ptr.left.data:
                    i += 1
                    ptr.left = ptr.left.remove()
                ptr = ptr.left
            else:
                while ptr.right and data == ptr.right.data:
                    i += 1
                    ptr.right = ptr.right.remove()
                ptr = ptr.right
        return i

    def min(self):
        if self.root:
            ptr = self.root
            while ptr.left:
                ptr = ptr.left
            return ptr

    def max(self):
        if self.root:
            ptr = self.root
            while ptr.right:
                ptr = ptr.right
            return ptr

    def _sumAllRec(self, ptr):
        if not self.root:
            return 0

        sum = ptr.data
        if ptr.left:
            sum += self._sumAllRec(ptr.left)
        if ptr.right:
            sum += self._sumAllRec(ptr.right)
        return sum

    def sumAll(self):
        return self._sumAllRec(self.root)

    def sumAllBFS(self):
        if not self.root:
            return 0
        q = [self.root]
        i = 0
        while q:
            n = q.pop(0)
            i += n.data
            if n.left:
                q.append(n.left)
            if n.right:
                q.append(n.right)
        return i

    def toSortedArray(self):
        if not self.root:
            return []
        a, stack = [], [(self.root, True)]
        while stack:
            n, need_left = stack.pop()
            if need_left and n.left:
                stack.append((n, False))
                stack.append((n.left, True))
            else:
                a.append(n.data)
                if n.right:
                    stack.append((n.right, True))
        return a



t = BST(22,20,42,11,21,22,44,11,1)
print(t)
print('Min:', t.min())
print('Max:', t.max())
print(f'Removed {t.removeAll(22)} 11')
print(t)
print('='*10)
t = BST(22,20,42,11,21,22,44)
print('Sum:', t.sumAll())
print('SumBFS:', t.sumAllBFS())
print(t.toSortedArray())
print('='*10)