import math
from fractions import Fraction

from matrix import E


class V:

    def __init__(self, *mags):
        self.mags = list(mags)

    def __str__(self):
        return f'({", ".join(str(e) for e in self.mags)})'

    def __check_same_dim__(self, other):
        if len(self.mags) != len(other.mags):
            raise Exception('Different vector dimensions')

    def __getitem__(self, item):
        return self.mags[item]

    def __add__(self, other):
        self.__check_same_dim__(other)
        return V(*(m + other[i] for i, m in enumerate(self.mags)))

    def __sub__(self, other):
        self.__check_same_dim__(other)
        return V(*(m - other[i] for i, m in enumerate(self.mags)))

    def __mul__(self, other):
        if type(other) == V:
            self.__check_same_dim__(other)
            return sum(self[i] * other[i] for i in range(len(self.mags)))  # Dot product
        return V(*(m * other for m in self.mags))  # Scalar mult

    def __rmul__(self, other):
        return self * other

    def __truediv__(self, other):
        return V(*(m / other for m in self.mags))  # Scalar mult

    def __neg__(self):
        return V(*(-m for m in self.mags))

    def mag(self):
        return math.sqrt(sum(m*m for m in self.mags))

    def is_unit_v(self):
        return self.mag() == 1

    def to_unit_v(self):
        return self/self.mag()

    def fraction(self):
        v = list(self.mags)
        for i, m in enumerate(v):
            if type(m) == float:
                v[i] = Fraction(m).limit_denominator()
        return V(*v)

    def is_nonzero(self):
        for m in self.mags:
            if m != 0:
                return True
        return False

    def get_cos(self, other):
        if self.is_nonzero() and other.is_nonzero():
            return (self*other)/(self.mag() * other.mag())
        raise Exception("Both vectors must be non-zero!")

    def get_cos_deg(self, other):
        return math.acos(self.get_cos(other)) * 180 / math.pi

    def is_orthogonal(self, other):
        return self * other == 0

    def is_linear_combination(self, vectors):
        e = E([[v[i] for v in vectors] for i in range(len(vectors[0].mags))], self.mags)
        print('Linear combination: Yes if the equation system has at least 1 solution')
        return e.solve()

