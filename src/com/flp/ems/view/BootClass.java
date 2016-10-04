package com.flp.ems.view;

import java.util.Scanner;

import com.flp.ems.util.InvalidMenuSelectionException;

public class BootClass {
	static UserInteraction usr = new UserInteraction();
	
	public static void main(String[] args) {

		while (true) {
			System.out.println("\n1.Add Employee");
			System.out.println("2.Modify details");
			System.out.println("3.Remove");
			System.out.println("4.Search");
			System.out.println("5.Show all");
			System.out.println("6.Exit");
			try {
				menuSelection();
			} catch (InvalidMenuSelectionException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public static void menuSelection() throws InvalidMenuSelectionException {
		Scanner sc = new Scanner(System.in);
		int choice = -1;
		boolean opstatus = false;
		

		System.out.println("Enter option : ");
		choice = sc.nextInt();

		if (!(choice > 0 && choice < 6))
			throw new InvalidMenuSelectionException();
		else {
			switch (choice) {
			case 1:
				opstatus = usr.addEmployee();

				if (opstatus)
					System.out.println("Employee Added Successfully !");
				else
					System.out.println("Failed to add. ");

				break;

			case 2:
				opstatus = usr.modifyEmployee();

				if (opstatus)
					System.out.println("Employee Modified Successfully !");
				else
					System.out.println("Employee not found. Failed to modify");

				break;

			case 3:
				opstatus = usr.removeEmployee();

				if (opstatus)
					System.out.println("Employee Removed Successfully !");
				else
					System.out.println("Employee not found. Failed to remove. ");

				break;

			case 4:
				usr.searchEmployee();

				/*
				 * if (opstatus) System.out.println(
				 * "Employee Added Successfully !"); else System.out.println(
				 * "Failed to add. ");
				 * 
				 */ break;

			case 5:
				usr.getAllEmployees();
				break;

			case 6:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Menu Selection. \nPlease select correct option");
			}

		}

	}
}
