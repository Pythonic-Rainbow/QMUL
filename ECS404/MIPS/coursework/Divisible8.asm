.data
prompt_for_input: .asciiz "Please enter your numbers, pressing enter after each, (0 to terminate):\n"
prompt_for_output: .asciiz "Your quantity of interest is equal to: "

.text
main:
# prompting the user with a message for a string input:
li $v0, 4
la $a0, prompt_for_input
syscall

LOOP: 
li  $v0, 5
syscall   # v0 = input integer

andi	$t0, $v0, 7		# t0 = v0 & 7
bne		$t0, $zero, DONE	# if $t0 != 0 then DONE
add	    $s0, $s0, $v0		# $s0 += v0

DONE:
bne $v0, $zero, LOOP

# prompting the user with a message for the processed output:
li $v0, 4
la $a0, prompt_for_output
syscall

# printing the output
addiu  $v0, $zero, 1
addu $a0, $zero, $s0
syscall

# Finish the programme:
li $v0, 10      # syscall code for exit
syscall         # exit