import math


class V:

    def __init__(self, *mags):
        self.mags = mags

    def __str__(self):
        return f'({", ".join(str(e) for e in self.mags)})'

    def __getitem__(self, item):
        return self.mags[item]

    def __add__(self, other):
        return V(*(m + other[i] for i, m in enumerate(self.mags)))

    def __sub__(self, other):
        return V(*(m - other[i] for i, m in enumerate(self.mags)))

    def __mul__(self, other):
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

