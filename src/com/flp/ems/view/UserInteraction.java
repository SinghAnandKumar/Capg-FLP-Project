package com.flp.ems.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import com.flp.ems.domain.Employee;
import com.flp.ems.service.EmployeeServiceImpl;
import com.flp.ems.util.Constants;

public class UserInteraction {
	HashMap<String, String> employeeData = new HashMap<>();
	EmployeeServiceImpl services = new EmployeeServiceImpl();
	Scanner sc = new Scanner(System.in);;
	String stringTemp;
	Long longTemp;
	int intTemp;
	private String kinIds[];

	public boolean addEmployee() {
		boolean status = false;

		// KIN ID auto assigned
		// EMAIL ID (Auto generated)
		
		// NAME
		System.out.println("Enter Employee Name : ");
		stringTemp = sc.nextLine();
		employeeData.put(Constants.name, stringTemp);

		// PHONENO
		System.out.print("Enter Phone Number : ");
		stringTemp = sc.nextLine();
		employeeData.put(Constants.phoneNo, stringTemp);


		// DATE OF BIRTH
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		while(true){
			System.out.print("Enter date of birth (dd/MM/yyyy) : ");
			stringTemp = sc.nextLine();
			try {
				date = dateFormat.parse(stringTemp);
			} catch (ParseException e) {
				System.out.println("Invalid date format. Re-enter date");
				continue;
				// e.printStackTrace();
			}
			break;
		}
		employeeData.put(Constants.dateOfBirth, stringTemp);

		
		// DATE OF JOINING
		while(true){
			System.out.print("Enter date of joining (dd/MM/yyyy) : ");
			stringTemp = sc.nextLine();
			try {
				date = dateFormat.parse(stringTemp);
			} catch (ParseException e) {
				System.out.println("Invalid date format. Re-enter date");
				continue;
				// e.printStackTrace();
			}
			break;
		}
		employeeData.put(Constants.dateOfJoining, stringTemp);

		// ADDRESS
		System.out.print("Enter Address : ");
		stringTemp = sc.nextLine();
		employeeData.put(Constants.address, stringTemp);

		// validate ROLE PROJECT and DEPARTMENT

		// DEPARTMENT ID
		System.out.println("Enter Department Id : ");
		intTemp = sc.nextInt();
		sc.nextLine();
		employeeData.put(Constants.departmentId, Integer.toString(intTemp));

		// PROJECT
		System.out.println("Enter Project Id : ");
		intTemp = sc.nextInt();
		sc.nextLine();
		employeeData.put(Constants.projectId, Integer.toString(intTemp));

		// ROLE
		System.out.println("Enter role Id : ");
		intTemp = sc.nextInt();
		sc.nextLine();
		employeeData.put(Constants.roleId, Integer.toString(intTemp));

		// pass data to next layer
		status = services.addEmployee(employeeData);
		return status;
	}

	public boolean modifyEmployee() {
		boolean status = false;
		int ch = 0;
		String kinId = "";
		employeeData = new HashMap<>();

		System.out.println("Enter kinId to be modified : ");
		kinId = sc.next();
		employeeData.put(Constants.kinId, kinId);

		while (true) {
			System.out.println("Select data field to modify : ");
			System.out.println("1.Name \n2.Email Id \n3.Phone No.\n4.Date of Birth");
			System.out.println("5.Date of Joining \n6.Address \n7.Department ID\n8.Project ID \n9.Role ID\n10.Exit");
			ch = sc.nextInt();

			switch (ch) {
			case 1:
				System.out.println("Enter Name : ");
				stringTemp = sc.nextLine();
				employeeData.put(Constants.name, stringTemp);
				break;

			case 2:
				System.out.println("Enter Email Id : ");
				stringTemp = sc.nextLine();
				employeeData.put(Constants.emailId, stringTemp.toLowerCase());
				break;

			case 3:
				System.out.println("Enter Phone No :");
				stringTemp = sc.nextLine();
				employeeData.put(Constants.phoneNo, stringTemp);
				break;

			case 4:
				System.out.println("Enter Date of Birth :");
				stringTemp = sc.nextLine();
				employeeData.put(Constants.dateOfBirth, stringTemp);
				break;

			case 5:
				System.out.println("Enter Date of Joining :");
				stringTemp = sc.nextLine();
				employeeData.put(Constants.dateOfJoining, stringTemp);
				break;

			case 6:
				System.out.println("Enter Address :");
				stringTemp = sc.nextLine();
				employeeData.put(Constants.address, stringTemp);
				break;

			case 7:
				System.out.println("Enter Department Id :");
				intTemp = sc.nextInt();
				sc.nextLine();
				employeeData.put(Constants.departmentId, Integer.toString(intTemp));
				break;

			case 8:
				System.out.println("Enter Project Id :");
				intTemp = sc.nextInt();
				sc.nextLine();
				employeeData.put(Constants.projectId, Integer.toString(intTemp));
				break;

			case 9:
				System.out.println("Enter Role Id :");
				intTemp = sc.nextInt();
				sc.nextLine();
				employeeData.put(Constants.roleId, Integer.toString(intTemp));
				break;
			case 10:
				break;
			}
			if (ch == 9)
				break;
		}
		status = services.modifyEmployee(employeeData);
		return status;
	}

	public boolean removeEmployee() {
		boolean status = false;
		kinIds = null;
		String k;
		System.out.println("Enter Kin ID : ");
    	 k = sc.next();
    	kinIds[0] = k;
		status = services.removeEmployee(kinIds);
		return status;
	}

	public void searchEmployee() {
		int choice = -1;
		String searchBy = "kinId";
		String value = "";
		Employee emp = null;

		System.out.print("Search by : 1.KinId  2.EmailId 3.Name \nEnter option : ");
		choice = sc.nextInt();

		switch (choice) {
		case 1:
			System.out.print("Enter KinId :");
			value = sc.next();
			// validate kinId
			searchBy = Constants.kinId;
			emp = services.searchEmployee(searchBy, value);
			break;

		case 2:
			System.out.print("Enter KinId :");
			value = sc.next();
			// validate emailId
			searchBy = Constants.emailId;
			emp = services.searchEmployee(searchBy, value);
			break;

		case 3:
			System.out.print("Enter Name :");
			value = sc.next();
			// validate emailId
			searchBy = Constants.name;
			emp = services.searchEmployee(searchBy, value);
			break;

		default:
			System.out.println("Wrong selection !!!");
		}

		if (emp != null)
			printEmployeeData(emp);
	}

	public void getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<>();
		employees = services.getAllEmployees();

		if (employees.size() == 0)
			System.out.println("No more Employees !!!");
		else {
			for (Employee emp : employees)
				printEmployeeData(emp);
		}
	}

	public void printEmployeeData(Employee emp) {
		System.out.println("*************************************** \nEmployee Kin ID : " + emp.getKinId());
		System.out.println("Name : " + emp.getName());
		System.out.println("Email ID : " + emp.getEmailId());
		System.out.println("Phone No. : " + emp.getPhoneNo());
		System.out.println("Date of Birth : " + emp.getDateOfBirth());
		System.out.println("Date of Joining : " + emp.getDateOfJoining());
		System.out.println("Address : " + emp.getAddress());
		System.out.println("Department ID : " + emp.getDeptId());
		System.out.println("Project ID : " + emp.getProjectId());
		System.out.println("Role ID : " + emp.getRoleId() + "\n****************************************\n");
	}
}
