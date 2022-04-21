package com.kineo.app.exception;

public class EmployeesExistException extends RuntimeException {

	public EmployeesExistException(Long id, int employeeSize) {
		super(String.format("%d employees still exit in company with Id %d", id, employeeSize));
	}
}
