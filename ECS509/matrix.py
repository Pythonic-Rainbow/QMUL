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
            raise Exception("First matrix column count != second matrix row count. Note: AB != BA")
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
        if power < 0:
            raise Exception("Power < 0")
        if not self.is_sq_m():
            raise Exception("You can't do powers in a non-square matrix")
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
        return M(*self.rows)

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

    def solve(self, func):
        return func(self)

    def det(self):
        if self.is_sq_m():
            if self.row == 2:
                return self.rows[0][0] * self.rows[1][1] - self.rows[0][1] * self.rows[1][0]
            raise NotImplementedError("Cannot find det of square matrix with size != 2")

    def inverse(self):
        det = self.det()
        if det:
            n = M([self.rows[1][1], -self.rows[0][1]], [-self.rows[1][0], self.rows[0][0]])
            n *= 1 / det
            return n
        raise Exception("This matrix is not invertible!")

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
