from datetime import datetime
from glob import glob
import math
import os

header = ('Name', 'Size (B)', 'Type', 'Last modified', 'Real path/inode')

names, sizes, types, last_modified, links = [], [], [], [], []
inode_maps = {}

for i, file in enumerate(glob('*')):
    stat = os.stat(file)
    names.append(file)
    sizes.append(stat.st_size)
    if os.path.islink(file):  # Soft link
        links.append(os.path.realpath(file))
        types.append('Link')
    elif stat.st_nlink > 1:  # Hard link
        if stat.st_ino in inode_maps:
            inode_maps[stat.st_ino].append(file)
        else:
            inode_maps[stat.st_ino] = [file]
        links.append('')
        types.append('File')
    else:
        links.append('')
        if os.path.isdir(file):
            types.append('Dir')
        else:
            types.append('File')
    last_modified.append(datetime.fromtimestamp(stat.st_mtime).strftime('%b %d %Y %H:%M:%S'))

name_width = max(map(lambda name: len(name), names + [header[0]])) + 1
size_width = max(max(map(lambda size: math.log10(size) if size else 1, sizes)), len(header[1])) + 1
link_width = max(map(lambda ln: len(ln), links + [header[4]])) + 1

print(f'{header[0]: <{name_width}}{header[1]: <{size_width}}{header[2]} {header[3]: <21}{header[4]: <{link_width}}')
print('-'*(name_width + size_width + 5 + 20 + link_width))
for i in range(len(names)):
    print(f'{names[i]: <{name_width}}{sizes[i]: <{size_width}}{types[i]: <4} {last_modified[i]} {links[i] :<{link_width}}')

for inode, files in inode_maps.items():
    if len(files) > 1:
        print(f'The following files are hard link: {", ".join(files)}')
