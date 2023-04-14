import os

threshold = 1000
has_large_file = False

# open a pipe to the output of the ls  command
p = os.popen("ls -l")
# discard the first summary line of ls  if it causes problems
p.readline()

for line in p:
    print(line.rstrip())
    # split each line of ls  output into fields
    fields = line.split()
    
    if int(fields[4]) >= threshold:
        has_large_file = True
        print(fields[4], fields[8])

if not has_large_file:
    print('No large files found.')
	
