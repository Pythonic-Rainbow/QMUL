.data
buffer_for_input_string: .space 100
buffer_for_processed_string: .space 100
prompt_for_input: .asciiz "Please enter your string:\n"
prompt_for_output: .asciiz "Your processed string is as follows:\n"

.text
main:
# prompting the user with a message for a string input:
li $v0, 4
la $a0, prompt_for_input
syscall

# reading the input string and putting it in the memory:
# the starting address of the string is accessible as buffer_for_input_string
# 100 is the hard-coded maximum length of the null-terminated string that is
# going to be read from the input. So effectively, up to 99 ascii characters.
li $v0, 8
la $a0, buffer_for_input_string
li $a1, 100
syscall

# >>>> MAKE YOUR CHANGES BELOW HERE:

# Looping over characters of the string:
la      $t0, buffer_for_input_string
la      $t1, buffer_for_processed_string

Loop:
lb      $t2, 0($t0)
add     $t5, $zero, $t2 # t5 is the register to process and store the char

# potentially do some processing on the character loaded in t2

# Sets s0 to whether the char is an upper case
slti    $t3, $t2, 65    #t3 = t2 < 'A'
slti    $t4, $t2, 91    #t4 = t2 < 'Z'
slti    $t3, $t3, 1     # t3 = not t3
and		$s0, $t3, $t4	# s0 = $t3 & $t4

# Sets s1 to whether the char is a lower case
slti    $t3, $t2, 97    # t3 = t2 < 'a'
slti    $t4, $t2, 123   # t3 = t2 < 'z'
slti    $t3, $t3, 1     # t3 = not t3
and     $s1, $t3, $t4   # s1 = t3 & t4

or		$t3, $s0, $s1	# t3 = Whether current char is an alphabet
bne     $t3, $zero, Process  # You gotta process the current char if its an alphabet
add     $s4, $zero, $zero    # s4 = 0
j Next_char

Process:
# s2 and s3 = previous char s0 and s1 respectively
nor		$t3, $s2, $s3   # t3 = Whether previous char is NOT an alphabet
or      $t3, $t3, $s4   # t3 will still be set if s4 is already active
and		$s4, $t3, $s1	# s4 = t3 & current char is lowercase

Next_char:
add     $s2, $zero, $s0  # s2 = s0
add     $s3, $zero, $s1  # s3 = s1
bne     $s4, $zero, Increment
sb      $t5, 0($t1)
addi    $t1, $t1, 1

Increment:
addi    $t0, $t0, 1
bne     $t2, $zero, Loop # keep going until you reach the end of the string,
                         # which is demarcated by the null character.

# <<<< MAKE YOUR CHANGES ABOVE HERE

# prompting the user with a message for the processed output:
li $v0, 4
la $a0, prompt_for_output
syscall

# printing the processed output
# note that v0 already holds 4, the syscall code for printing a string.
la $a0, buffer_for_processed_string
syscall

# Finish the programme:
li $v0, 10      # syscall code for exit
syscall         # exit