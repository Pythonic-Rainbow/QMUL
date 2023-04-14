#!/usr/bin/env python

"""my_run_shell_0.py:
Simple shell to start programs, e.g., try "PShell>ps" and "PShell>ls".

The purpose of this script is to give you simple functions
for locating an executable program in common locations in Linux/UNIX
(PATH environmental variable).

You are meant to paste your code from your solution in Part A
into the relevant points in this script.

Try to stick to Style Guide for Python Code and Docstring Conventions:
see https://peps.python.org/pep-0008 and https://peps.python.org/pep-0257/

(Note: The breakdown into Input/Action/Output in this script is just a suggestion.)
"""

from datetime import datetime
import os, shutil, sys

from my_shell_outline import *

# Here the path is hardcoded, but you can easily optionally get your PATH environ variable
# by using: path = os.environ['PATH'] and then splitting based on ':' such as the_path = path.split(':')
THE_PATH = ["/bin/", "/usr/bin/", "/usr/local/bin/", "./"]

# ========================
#   Run command
#   Run an executable somewhere on the path
#   Any number of arguments
# ========================
def run_cmd(fields):
    """Returns nothing (after trying to execute user command expressed in fields).
    
    Input: takes a list of text fields (and global list of directories to search)
    Action: executes command
    Output: returns no return value
    """
    cmd = fields[0]
    exec_name = add_path(cmd, THE_PATH)

    # run the executable
    if exec_name:
        pid = os.fork()
        if pid:  # parent
            _, exit_code = os.wait()
            print('Command exited with code ' + str(exit_code))
        else:
            os.execv(exec_name, fields)
    else:
        print("Executable file", cmd, "not found")

# ========================
#   Constructs the full path used to run the external command
#   Checks to see if an executable file can be found in one of the provided directories.
#   Returns None on failure.
# ========================
def add_path(cmd, executable_dirs):
    """Returns command with full path when possible and None otherwise.
    
    Input: takes a command and a list of paths to search
    Action: no actions
    Output: returns external command prefaced by full path
            (returns None if executable file cannot be found in any of the paths)
    """
    if cmd[0] not in ['/', '.']:
        for dir in executable_dirs:
            execname = dir + cmd
            if os.path.isfile(execname) and os.access(execname, os.X_OK):
                return execname
        return None
    else:
        return cmd

# ---------------------------------------------------------------------

def main():
    """Returns exit code 0 (after executing the main part of this script).
    
    Input: no function arguments
    Action: run multiple user-inputted commands
    Output: return zero to indicate regular termination
    """
    
    while True:
        line = input("PShell> ")
        fields = line.split()
        # split the command into fields stored in the fields list
        # fields[0] is the command name and anything that follows (if it follows) is an argument to the command
        if fields:
            cmd = fields[0]
            if cmd == "files":
                files_cmd(fields)
            elif cmd == "info":
                info_cmd(fields)
            elif cmd == "delete":
                delete_cmd(fields)
            elif cmd == "copy":
                copy_cmd(fields)
            elif cmd == "where":
                where_cmd(fields)
            elif cmd == "down":
                down_cmd(fields)
            elif cmd == "up":
                up_cmd(fields)
            elif cmd == "exit":
                return
            else:
                run_cmd(fields)
    
    return 0 # currently unreachable code

if __name__ == '__main__':
    sys.exit(main()) # run main function and then exit
