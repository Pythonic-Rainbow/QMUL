.data
input_file_name: .asciiz "pog.bmp"
output_file_name: .asciiz "out.bmp"

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

li  $s3, 3 # colours
li  $s4, 78 # columns
li  $s5, 128 # rows

li      $t4, 0 # row counter
Loop_over_rows:
li      $t3, 0 # column counter
Loop_over_pixels_in_each_row: # i.e., columns
li      $t2, 0 # colour counter (each colour represents Red-Green-Blue)
Loop_over_colours_in_each_pixel:
lb      $t1, 0($t0) # loading the value of the byte, representing the intensity of the corresponding RGB colour of that pixel
# Do something with the value of the byte
sb      $t1, 50($t0) #
addi    $t0, $t0, 1 # address of the next byte
addi    $t2, $t2, 1 # counter of the colour bytes for each pixel
bne     $t2, $s3, Loop_over_colours_in_each_pixel
addi    $t3, $t3, 1 # counter over the pixels in a row (column)
bne     $t3, $s4, Loop_over_pixels_in_each_row
addi    $t4, $t4, 1
bne     $t4, $s5, Loop_over_rows

# <<<<<<<< MAKE YOUR CHANGES ABOVE THIS LINE

# Open a file for saving the processed image to: 
li $v0, 13 # system call for open file 
la $a0, output_file_name # input file name
li $a1, 0x41 # flag for writing 
li $a2, 0x1B4 # mode is ignored 
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