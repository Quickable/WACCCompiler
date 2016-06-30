	.data

	.text

	.global main
	f_std_sumIntArray:
		PUSH {lr}
		SUB sp, sp, #8
		LDR r4, =0
		STR r4, [sp, #4]
		LDR r4, =0
		STR r4, [sp]
	sumIntArray0:
		LDR r4, [sp, #4]
		LDR r5, [sp, #12]
		LDR r5, [r5]
		CMP r4, r5
		MOVLT r4, #1
		MOVGE r4, #0
		CMP r4, #0
		BEQ sumIntArray1
		LDR r4, [sp]
		ADD r5, sp, #12
		LDR r6, [sp, #4]
		LDR r5, [r5]
		MOV r0, r6
		MOV r1, r5
		BL p_check_array_bounds
		ADD r5, r5, #4
		ADD r5, r5, r6, LSL #2
		LDR r5, [r5]
		ADDS r4, r4, r5
		BLVS p_throw_overflow_error
		STR r4, [sp]
		LDR r4, [sp, #4]
		ADDS r4, r4, #1
		BLVS p_throw_overflow_error
		STR r4, [sp, #4]
		B sumIntArray0
	sumIntArray1:
		LDR r4, [sp]
		MOV r0, r4
		ADD sp, sp, #8
		POP {pc}
		POP {pc}
		.ltorg

	*DEPENDENCIES*

	p_check_array_bounds

	p_throw_runtime_error

	p_print_string

	p_throw_overflow_error

	p_print_int

	p_print_ln