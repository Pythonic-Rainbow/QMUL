import os
import glob
import sys

if len(sys.argv) != 2:
        print('ERROR: This program only accepts one argument!')
elif not sys.argv[1].isnumeric():
        print('ERROR: argument is not an integer!')
else:
        threshold = int(sys.argv[1])
        has_large_file = False

        for filename in glob.glob('*'):
                size = os.path.getsize(filename)
                if size >= threshold:
                        has_large_file = True
                        print(size, filename)

        if not has_large_file:
                print('No large files found.')
