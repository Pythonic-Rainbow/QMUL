import os

temp = -1
new_pid = os.fork()
if new_pid == 0:
    temp = -3
    print("The value of temp is", temp)

print("Temp and pid here are", temp, new_pid)
