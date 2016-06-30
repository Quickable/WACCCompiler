	.data
	
	printIntArray_0:
		.word 1
		.ascii "["
	printIntArray_1:
		.word 2
		.ascii ", "
	printIntArray_2:
		.word 1
		.ascii "]"

	.text
	
	.global main
	f_std_printIntArray:
		PUSH {lr}
		SUB sp, sp, #4
		LDR r4, =printIntArray_0
		MOV r0, r4
		BL p_print_string
		LDR r4, =0
		STR r4, [sp]
	printIntArray0:
		LDR r4, [sp]
		LDR r5, [sp, #8]
		LDR r5, [r5]
		LDR r6, =1
		SUBS r5, r5, r6
		BLVS p_throw_overflow_error
		CMP r4, r5
		MOVLT r4, #1
		MOVGE r4, #0
		CMP r4, #0
		BEQ printIntArray1
		ADD r4, sp, #8
		LDR r5, [sp]
		LDR r4, [r4]
		MOV r0, r5
		MOV r1, r4
		BL p_check_array_bounds
		ADD r4, r4, #4
		ADD r4, r4, r5, LSL #2
		LDR r4, [r4]
		MOV r0, r4
		BL p_print_int
		LDR r4, =printIntArray_1
		MOV r0, r4
		BL p_print_string
		LDR r4, [sp]
		ADDS r4, r4, #1
		BLVS p_throw_overflow_error
		STR r4, [sp]
		B printIntArray0
	printIntArray1:
		ADD r4, sp, #8
		LDR r6, [sp]
		LDR r5, [r4]
		MOV r0, r6
		MOV r1, r5
		BL p_check_array_bounds
		ADD r5, r5, #4
		ADD r5, r5, r6, LSL #2
		LDR r4, [r5]
		MOV r0, r4
		BL p_print_int
		LDR r4, =printIntArray_2
		MOV r0, r4
		BL p_print_string
		BL p_print_ln
		MOV r4, #1
		MOV r0, r4
		ADD sp, sp, #4
		POP {pc}
		POP {pc}
		.ltorg

	*DEPENDENCIES*

	p_print_string

	p_throw_overflow_error

	p_throw_runtime_error

	p_check_array_bounds

	p_print_int

	p_print_ln
