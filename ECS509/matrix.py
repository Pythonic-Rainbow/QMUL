import itertools
from fractions import Fraction


class M:

    def __init__(self, *data):
        self.rows = list(data)
        self.__validate_size__()

    @staticmethod
    def I(size):
        data = []
        for i in range(size):
            data.append([0] * size)
            data[i][i] = 1
        return M(*data)

    def __validate_size__(self):
        if self.rows:
            self.row = len(self.rows)
            self.column = len(self.rows[0])
            if self.column == 0:
                raise Exception('Size of row can not be 0')
            for i in range(1, len(self.rows)):
                c = len(self.rows[i])
                if c != self.column:
                    raise Exception(f'Size of row 0 is {self.column} but size of row {i} is {c}!')
        else:
            self.row = self.column = 0

    def __str__(self):
        s = ''
        for c in self.rows:
            s += ' '.join(str(e) for e in c) + '\n'
        return s

    def __getitem__(self, args):
        r, c = args
        if r > self.row:
            raise Exception(f'There are only {self.row} rows')
        if c > self.column:
            raise Exception(f'Size of row is {self.column}')
        return self.rows[r - 1][c - 1]

    def __eq__(self, ma):
        if not self.eq_size(ma):
            return False
        for i in range(self.row):
            if self.rows[i] != ma.rows[i]:
                return False
        return True

    def __add__(self, ma):
        if not self.eq_size(ma):
            raise Exception(f'Cannot add {ma.size_str()} to {self.size_str()}')
        n = M()
        for r in range(self.row):
            cl = []
            for c in range(self.column):
                cl.append(self.rows[r][c] + ma.rows[r][c])
            n.rows.append(cl)
        n.row, n.column = self.row, self.column
        return n

    def __radd__(self, other):
        return self + other

    def __iadd__(self, ma):
        if not self.eq_size(ma):
            raise Exception(f'Cannot add {ma.size_str()} to {self.size_str()}')
        for r in range(self.row):
            for c in range(self.column):
                self.rows[r][c] += ma.rows[r][c]
        return self

    def __sub__(self, ma):
        if not self.eq_size(ma):
            raise Exception(f'Cannot subtract {ma.size_str()} from {self.size_str()}')
        n = M()
        for r in range(self.row):
            cl = []
            for c in range(self.column):
                cl.append(self.rows[r][c] - ma.rows[r][c])
            n.rows.append(cl)
        n.row, n.column = self.row, self.column
        return n

    def __isub__(self, ma):
        if not self.eq_size(ma):
            raise Exception(f'Cannot subtract {ma.size_str()} from {self.size_str()}')
        for r in range(self.row):
            for c in range(self.column):
                self.rows[r][c] -= ma.rows[r][c]
        return self

    def __mul__(self, other):
        if type(other) == M:
            if self.column == other.row:
                n = M()
                for row in self.rows:
                    cl = []
                    for column in range(other.column):
                        s = 0
                        for i, otr in enumerate(other.rows):
                            s += row[i] * otr[column]
                        cl.append(s)
                    n.rows.append(cl)
                n.row, n.column = self.row, other.column
                return n
            raise Exception("First matrix column count != second matrix row count. Note: A*B != B*A")
        n = []
        for r in self.rows:
            cl = []
            for c in r:
                cl.append(c * other)
            n.append(cl)
        return M(*n)

    def __imul__(self, other):
        if type(other) == M:
            n = self * other
            return n
        for r in range(self.row):
            for c in range(self.column):
                self.rows[r][c] *= other
        return self

    def __rmul__(self, other):
        return self * other

    def __pow__(self, power, modulo=None):
        if not self.is_sq_m():
            raise Exception("You can't do powers in a non-square matrix")
        if power < 0:
            raise Exception("Power < 0")
        if power == 0:
            return M.I(self.row)
        if power == 1:
            return self
        m = self.copy()
        for _ in range(power - 1):
            m *= m
        return m

    def __neg__(self):
        return self * -1

    def is_row_m(self):
        return self.row == 1

    def is_col_m(self):
        return self.column == 1

    def is_sq_m(self):
        return self.row == self.column

    def is_zero_m(self):
        for r in self.rows:
            for c in r:
                if c:
                    return False
        return False

    def __ensure_sq_m__(self, msg='This matrix is not square!'):
        if not self.is_sq_m():
            raise Exception(msg)

    def diag(self):
        self.__ensure_sq_m__()
        return [self.rows[i][i] for i in range(self.row)]

    def diagonal(self):
        for e in self.diag():
            if not e:
                return False
        return True

    def trace(self):
        return sum(self.diag())

    def eq_size(self, ma):
        return self.row == ma.row and self.column == ma.column

    def size_str(self):
        return f'{self.row}x{self.column}'

    def copy(self):
        return M(*[[c for c in row] for row in self.rows])

    def transpose(self):
        n = [[] for _ in range(self.column)]
        for r in self.rows:
            for i, e in enumerate(r):
                n[i].append(e)
        return M(*n)

    def i(self):
        self.__ensure_sq_m__("Cannot create identity matrix because it is non-square")
        return M.I(self.row)

    def fraction(self):
        n = self.copy()
        n.row, n.column = self.row, self.column
        for r in range(self.row):
            for c in range(self.column):
                if type(self.rows[r][c]) == float:
                    n.rows[r][c] = Fraction(self.rows[r][c]).limit_denominator()
        return n

    def det(self):
        self.__ensure_sq_m__()
        if self.row == 2:
            return self.rows[0][0] * self.rows[1][1] - self.rows[0][1] * self.rows[1][0]
        if self.row == 3:
            return self.rows[0][0] * self.rows[1][1] * self.rows[2][2] \
                   + self.rows[0][1] * self.rows[1][2] * self.rows[2][0] \
                   + self.rows[0][2] * self.rows[1][0] * self.rows[2][1] \
                   - self.rows[0][2] * self.rows[1][1] * self.rows[2][0] \
                   - self.rows[0][0] * self.rows[1][2] * self.rows[2][1] \
                   - self.rows[0][1] * self.rows[1][0] * self.rows[2][2]
        raise NotImplementedError("Det of square matrix with size != 2 or 3 is not supported")

    def inverse(self, steps=0, show_step_on_n=False):
        if self.row <= 3:
            det = self.det()
            if not det:
                raise Exception('Det = 0, this matrix is not invertible!')
            if self.row == 0:  # 2
                n = M([self.rows[1][1], -self.rows[0][1]], [-self.rows[1][0], self.rows[0][0]])
                n *= 1 / det
                return n
        self.__ensure_sq_m__()
        n = self.copy()
        i_m = n.i()
        n_step = steps if show_step_on_n else 0
        for i, r in enumerate(n.rows):
            if r[i] != 1:
                c = 1 / r[i]
                n.mul_row(c, i, n_step)
                i_m.mul_row(c, i, steps)
            for j in itertools.chain(range(i), range(i+1, n.row)):
                t = n.rows[j][i]
                if t:
                    c = -t
                    n.mul_add_to(j, c, i, n_step)
                    i_m.mul_add_to(j, c, i, steps)
        if self.row >= 4 and n != self.i():
            raise Exception("This matrix is not invertible!")
        return i_m

    def upper_triangular(self):
        if self.is_sq_m():
            for r in range(1, self.row):
                for c in range(r):
                    if self.rows[r][c]:
                        return False
            return True
        return False

    def lower_triangular(self):
        if self.is_sq_m():
            for r in range(self.row - 1):
                for c in range(1 + r, self.column):
                    if self.rows[r][c]:
                        return False
            return True
        return False

    def symmetric(self):
        if self.is_sq_m():
            for r in range(1, self.row):
                for c in range(r):
                    if self.rows[r][c] != self.rows[c][r]:
                        return False
            return True
        return False

    def mul_row(self, c, i, steps=0):
        if not c:
            raise Exception("Cannot multiply 0 to row")
        if steps:
            print(f'{Fraction(c).limit_denominator()}R{i + 1}')
        for j in range(self.column):
            self.rows[i][j] *= c
        if steps == 2:
            print(self)

    def mul_add_to(self, j, c, i, steps=0):
        if not c:
            raise Exception("Cannot multiply 0 to row")
        if steps:
            print(f'R{j + 1} {Fraction(c).limit_denominator()}R{i + 1}')
        for k in range(self.column):
            self.rows[j][k] += self.rows[i][k] * c
        if steps == 2:
            print(self)

    def swap(self, i, j, steps=0):
        if steps:
            print(f'R{i + 1}<->R{j + 1}')
        temp = self.rows[i]
        self.rows[i] = self.rows[j]
        self.rows[j] = temp
        if steps == 2:
            print(self)

    def append(self, m):
        if m.row != self.row:
            raise Exception("First matrix row size != Second matrix")
        for r in range(self.row):
            for e in m.rows[r]:
                self.rows[r].append(e)


class E(M):

    def __init__(self, coe, cst):
        super().__init__(*coe)
        self.constants = cst

    def __str__(self):
        s = ''
        for i in range(self.row):
            s += f"{' '.join(str(e) for e in self.rows[i])}| {self.constants[i]}\n"
        return s

    @staticmethod
    def __no_solution__():
        raise Exception("This equation system has no solutions!")

    @staticmethod
    def __infinite_solution():
        print('This equation system has infinite solutions')

    def __cst_col__(self):
        return M(*([c] for c in self.constants))

    def is_homo(self):
        for c in self.constants:
            if c:
                return False
        return True

    def mul_row(self, c, i, steps=0):
        super().mul_row(c, i, steps)
        self.constants[i] *= c

    def mul_add_to(self, j, c, i, steps=0):
        super().mul_add_to(j, c, i, steps)
        self.constants[j] += self.constants[i] * c

    def swap(self, i, j, steps=0):
        super().swap(i, j, steps)
        temp = self.constants[i]
        self.constants[i] = self.constants[j]
        self.constants[j] = temp

    def solve(self):
        if self.is_sq_m():
            if self.det():
                return self.inverse() * self.__cst_col__()
            r = self.rows[0][0] / self.rows[1][0]
            if not self.constants[1] or r != self.constants[0] / self.constants[1]:
                self.__no_solution__()
            for i in range(1, self.column):
                if not self.rows[1][i] or r != self.rows[0][i] / self.rows[1][i]:
                    self.__no_solution__()
            self.__infinite_solution()
            return
        return self.gauss()

    def gauss(self, steps=0):
        for i in range(self.row):
            all_zero = True
            for e in self.rows[i]:
                if e:
                    all_zero = False
                    break
            if all_zero:
                r = self.rows[i]
                self.rows.pop(i)
                self.rows.append(r)
        if self.rows[0][0] != 1:
            for i in range(1, self.row):
                if self.rows[i][0] == 1:
                    self.swap(0, i, steps)
                    break
        if self.rows[0][0] != 1:
            mul = 1 / self.rows[0][0]
            self.mul_row(mul, 0, steps)

        for x in range(1, self.row):
            for i in range(x, self.row):
                if self.rows[i][x - 1] == 1:
                    self.swap(x, i, steps)
                    break
            for i in range(x, self.row):
                mul = -self.rows[i][x - 1]
                self.mul_add_to(i, mul, x - 1, steps)

            if self.rows[x][x] != 1:
                mul = 1 / self.rows[x][x]
                self.mul_row(mul, x, steps)

    def gauss_jordan(self, steps=0):
        self.gauss(steps)
        print('JORDAN')
        for i in range(1, self.row):
            for j in range(i):
                mul = -self.rows[j][i]
                self.mul_add_to(j, mul, i, steps)
