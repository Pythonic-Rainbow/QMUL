#!/usr/bin/env python

"""my_shell_outline.py:
Simple shell that interacts with the filesystem, e.g., try "PShell>files".

Try to stick to Style Guide for Python Code and Docstring Conventions:
see https://peps.python.org/pep-0008 and https://peps.python.org/pep-0257/

(Note: The breakdown into Input/Action/Output in this script is just a suggestion.)
"""

import glob
import os
import pwd
import shutil
import sys
import time
from datetime import datetime


# ========================
#    files command
#    List file and directory names
#    No command arguments
# ========================
def files_cmd(fields):
    """Return nothing after printing names/types of files/dirs in working directory.
    
    Input: takes a list of text fields
    Action: prints for each file/dir in current working directory their type and name
            (unless list is non-empty in which case an error message is printed)
    Output: returns no return value
    """
    
    if check_args(fields, 0):
        for filename in os.listdir('.'):
            if os.path.isdir(os.path.abspath(filename)):
                print("dir:", filename)
            else:
                print("file:", filename)

# ========================
#  info command
#     List file information
#     1 command argument: file name
# ========================
def info_cmd(fields):
    if check_args(fields, 1):
        path = fields[1]
        try:
            stat = os.stat(path)
            owner = pwd.getpwuid(stat.st_uid).pw_name
            last_modified = datetime.fromtimestamp(stat.st_mtime).strftime('%b %d %Y %H:%M:%S')
            print(f'Owner: {owner} \nLast modified: {last_modified}')
            if os.path.isfile(path):  # is a file
                print(f'File {stat.st_size}B {"Executable" if os.access(os.path.abspath(path), os.X_OK) else ""}')
            else:
                print('Dir')
        except FileNotFoundError:
            print(f"'{path} is not a file/directory")


def delete_cmd(fields):
    if check_args(fields, 1):
        if os.path.isfile(fields[1]):
            try:
                os.remove(fields[1])
            except Exception as e:
                print(f"Cannot delete file: {e}")
        else:
            print('Not a file')


def copy_cmd(fields):
    if check_args(fields, 2):
        src = fields[1]
        if not os.path.isfile(src):
            print(f"'{src}' is not a file")
            return
        dest = fields[2]
        if os.path.exists(dest):
            print(f"'{dest} already exists")
            return
        try:
            shutil.copy2(src, dest)
        except Exception as e:
            print(f"Cannot copy file: {e}")


def where_cmd(fields):
    if check_args(fields, 0):
        print(os.getcwd())


def down_cmd(fields):
    if check_args(fields, 1):
        cwd = os.getcwd() + '/'
        dest = os.path.realpath(cwd + fields[1])
        if os.path.isdir(dest) and dest.startswith(cwd):
            os.chdir(dest)
        else:
            print('Not a child directory: ' + dest)


def up_cmd(fields):
    if check_args(fields, 0):
        if os.getcwd() == '/':
            print('Already in root directory /')
        else:
            os.chdir('..')

# ----------------------
# Other functions
# ----------------------
def check_args(fields, num):
    """Returns if len(fields)-1 == num and print an error in shell if not.
    
    Input: takes a list of text fields and how many non-command fields are expected
    Action: prints error to shell if the number of fields is unexpected
    Output: returns boolean value to indicate if it was expected number of fields
    """

    num_args = len(fields) - 1
    if num_args == num:
        return True
    if num_args > num:
        print("Unexpected argument", fields[num+1], "for command", fields[0])
    else:
        print("Missing argument for command", fields[0])
        
    return False

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
                print("Unknown command", fields[0])
    
    return 0 # currently unreachable code

if __name__ == '__main__':
    sys.exit( main() ) # run main function and then exit
