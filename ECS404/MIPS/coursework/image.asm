.data
input_file_name: .asciiz "in.ppm"
output_file_name: .asciiz "out.ppm"

.text
main:
# Open the input file for reading the image from:
li   $v0, 13       # system call for open file
la   $a0, input_file_name      # input file name
li   $a1, 0        # flag for reading
li   $a2, 0        # mode is ignored
syscall            # open a file
addu $s0, $zero, $v0      # save the file descriptor

addi $t0, $zero, 1
sll  $t0, $t0, 7
sll  $t0, $t0, 7
addu $t1, $t0, $t0
addu $t0, $t1, $t0
addi $s1, $t0, 61
sub $s2, $sp, $s1

# s1: size of the image in bytes
# s2: address of the starting byte of the buffer for the image

# Reading from file just opened
li   $v0, 14       # system call for reading from file
addu $a0, $zero, $s0      # file descriptor
addu $a1, $zero, $s2   # address of buffer to read to
addu $a2, $zero, $s1  # how many bytes to read
syscall            # read from file

# Close the input file:
li $v0, 16         # system call code for close file
addu $a0, $zero, $s0      # file descriptor to close
syscall            # close file


# s2: still holds the address of the starting byte of the buffer for the image

# >>>>>>>> MAKE YOUR CHANGES BELOW THIS LINE 

addiu    $t0, $s2, 61

li  $s5, 128 # amount of rows
addi	$t1, $t0, 234
add $t3, $zero, $t0

Loop_over_rows:  # Shift first 78 bytes in decending order. Then we just fill out the first 50 bytes with 0
subi	$t1, $t1, 1			# $t1 -= 1
lb      $t2, 0($t1)
sb      $t2, 150($t1)
bne		$t3, $t1, Loop_over_rows	# if $t0 != $t1 then Loop_over_rows

addi    $t1, $t1, 150
Fill:
subi	$t1, $t1, 1			# $t1 -= 1
sb      $zero, 0($t1)
bne		$t3, $t1, Fill	# if $t0 != $t1 then Fill

addi	$t3, $t3, 384			# t3 = address of next row
addi    $t1, $t3, 234
subi    $s5, $s5, 1
bne     $zero, $s5, Loop_over_rows

# <<<<<<<< MAKE YOUR CHANGES ABOVE THIS LINE

# Open a file for saving the processed image to: 
li $v0, 13 # system call for open file
la $a0, output_file_name # out file name
li $a1, 1 # flag for writing
li $a2, 0 # mode is ignored
syscall # open a file
addu $s0, $zero, $v0 # save the file descriptor 

# Saving the processed image to it: 
li $v0, 15 
addu $a0, $zero, $s0 
addu $a1, $zero, $s2 
addu $a2, $zero, $s1 
syscall 

# Close the output file: 
li $v0, 16 # system call code for close file 
syscall # close file 

# Finish the programme: 
li $v0, 10 # syscall code for exit 
syscall # exit