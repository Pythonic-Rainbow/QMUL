size = int(input('Enter total amount of bits for virtual address: '))
addr = int(input('Enter virtual address: '))

indices_pos = []
bits_count = 0
while bits_count < size:
    index_bit = int(input('Enter amount of bits for slice: '))
    indices_pos.append(index_bit)
    bits_count += index_bit

for i in range(len(indices_pos) - 1):
    index_bit = indices_pos[i]
    index_range = size - index_bit
    mask = (1 << size) - (1 << index_range)
    val = (addr & mask) >> index_range
    print(f'Level {i+1} page table: {val} {hex(val)}')
    size = index_range
mask = (1 << size) - 1
val = addr & mask
print(f'Offset: {val} {hex(val)}')