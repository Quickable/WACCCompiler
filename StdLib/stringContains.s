	.data

	.text

	.global main
	f_std_stringContains:
		PUSH {lr}
		SUB sp, sp, #8
		LDR r4, =0
		STR r4, [sp, #4]
		LDR r4, =0
		STR r4, [sp]
	stringContains0:
		LDR r4, [sp, #4]
		LDR r5, [sp, #12]
		LDR r5, [r5]
		CMP r4, r5
		MOVLT r4, #1
		MOVGE r4, #0
		CMP r4, #0
		BEQ stringContains1
		ADD r4, sp, #12
		LDR r5, [sp, #4]
		LDR r4, [r4]
		MOV r0, r5
		MOV r1, r4
		BL p_check_array_bounds
		ADD r4, r4, #4
		ADD r4, r4, r5
		LDRSB r4, [r4]
		ADD r5, sp, #16
		LDR r6, [sp]
		LDR r5, [r5]
		MOV r0, r6
		MOV r1, r5
		BL p_check_array_bounds
		ADD r5, r5, #4
		ADD r5, r5, r6
		LDRSB r5, [r5]
		CMP r4, r5
		MOVEQ r4, #1
		MOVNE r4, #0
		CMP r4, #0
		BEQ stringContains2
		LDR r4, [sp]
		ADDS r4, r4, #1
		BLVS p_throw_overflow_error
		STR r4, [sp]
		LDR r4, [sp]
		LDR r5, [sp, #16]
		LDR r5, [r5]
		CMP r4, r5
		MOVEQ r4, #1
		MOVNE r4, #0
		CMP r4, #0
		BEQ stringContains4
		MOV r4, #1
		MOV r0, r4
		ADD sp, sp, #8
		POP {pc}
	stringContains4:
		B stringContains3
	stringContains2:
		LDR r4, =0
		STR r4, [sp]
	stringContains3:
		LDR r4, [sp, #4]
		ADDS r4, r4, #1
		BLVS p_throw_overflow_error
		STR r4, [sp, #4]
		B stringContains0
	stringContains1:
		MOV r4, #0
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

	p_print_bool

	p_print_ln
