package com.internship.app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        if (!Login.authenticate()) {
            System.out.println("Invalid Login");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Department");
            System.out.println("2. View Departments");
            System.out.println("3. Add Person");
            System.out.println("4. View Person");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Department.addDepartment();
                    break;
                case 2:
                    Department.viewDepartments();
                    break;
                case 3:
                    Person.addPerson();
                    break;
                case 4:
                    Person.viewPerson();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 0);
    }
}
