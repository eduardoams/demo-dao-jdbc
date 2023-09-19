package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	
	private static SellerDao sellerDao = DaoFactory.createSellerDao();
	private static DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ParseException {
				
		System.out.println("======= Department and Sales Management =======\n");
		
		System.out.println("Choose one of the options to manage:");
		System.out.print("1) Department\n2) Seller\n");
		
		int option = selectedOption(2);
				
		switch (option) {
			case 1:
				System.out.println("\n======= Selected: Department =======\n");
				manageDepartment();
				break;
			case 2:
				System.out.println("\n======= Selected: Seller =======\n");
				manageSeller();
				break;
		}
	}

	private static void manageDepartment() throws ParseException {

		printAllDepartments();
		System.out.println();
		printOptions();
		
		int option = selectedOption(4);
		
		try {
			switch (option) {
				case 1: //Insert
					System.out.println("\n======= Selected: INSERT =======\n");
					System.out.print("Enter the name: ");
					sc.nextLine();
					String name = sc.nextLine();
					
					Integer newDepartmentId = insertDepartment(name);
					System.out.println("\nInserted! New id: " + newDepartmentId);
										
					break;
				case 2: //Delete 
					System.out.println("\n======= Selected: DELETE =======\n");
					System.out.print("Enter id: ");
					int id = sc.nextInt();
					departmentDao.deleteById(id);
					System.out.println("Id " + id + " deleted!");
					
					break;
				case 3: //Update
					System.out.println("\n======= Selected: UPDATE =======\n");
					System.out.print("Enter id: ");
					id = sc.nextInt();
					
					Department department = departmentDao.findById(id);
					
					while (departmentDao.findById(id) == null) {
						System.out.print("No data found. Enter a new id: ");
						id = sc.nextInt();
					}
					
					System.out.println("\n======= Selected: " + department.getName() + " =======\n");
					System.out.println("Enter the new values ​​for the respective data");
					
					System.out.print("Name: ");
					sc.nextLine();
					name = sc.nextLine();
					
					updateDepartment(name, department);
					
					System.out.println("\nUpdated data!");
					System.out.println(departmentDao.findById(id));

					break;
			}
			
			System.out.println("\n======= Application closed =======\n");

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			sc.close();
		}
		
	}

	private static void manageSeller() {
				
		printAllSellers();
		System.out.println();
		printAllDepartments();
		System.out.println();
		printOptions();
		
		int option = selectedOption(4);
		
		try {
			switch (option) {
				case 1: //Insert
					System.out.println("\n======= Selected: INSERT =======\n");
					System.out.print("Enter the name: ");
					sc.nextLine();
					String name = sc.nextLine();
					System.out.print("Enter email: ");
					String email = sc.next();
					System.out.print("Enter date of birth (dd/mm/yyyy): ");
					String birthDate = sc.next();
					System.out.print("Enter base salary: ");
					Double baseSalary = sc.nextDouble();
					System.out.print("Enter department id: ");
					Integer departmentId = sc.nextInt();
						
					Integer newSellerId = insertSeller(name, email, birthDate, baseSalary, departmentId);
					System.out.println("\nInserted! New id: " + newSellerId);
										
					break;
				case 2: //Delete 
					System.out.println("\n======= Selected: DELETE =======\n");
					System.out.print("Enter id: ");
					int id = sc.nextInt();
					sellerDao.deleteById(id);
					System.out.println("Id " + id + " deleted!\n");
					
					break;
				case 3: //Update
					System.out.println("\n======= Selected: UPDATE =======\n");
					System.out.print("Enter id: ");
					id = sc.nextInt();
					
					Seller seller = sellerDao.findById(id);
					
					while (sellerDao.findById(id) == null) {
						System.out.print("No data found. Enter a new id: ");
						id = sc.nextInt();
					}
					
					System.out.println("\n======= Selected: " + seller.getName() + " =======\n");
					System.out.println("Enter the new values ​​for the respective data");
					
					System.out.print("Name: ");
					sc.nextLine();
					name = sc.nextLine();
					System.out.print("Email: ");
					email = sc.next();
					System.out.print("Date of birth (dd/mm/yyyy): ");
					birthDate = sc.next();
					System.out.print("Base salary: ");
					baseSalary = sc.nextDouble();
					System.out.print("Department id: ");
					departmentId = sc.nextInt();
					
					updateSeller(name, email, birthDate, baseSalary, departmentId, seller);
					
					System.out.println("\nUpdated data!");
					System.out.println(sellerDao.findById(id));

					break;
			}
			
			System.out.println("\n======= Application closed =======\n");

		} catch (ParseException e) {
			System.out.println("Error inserting date: " + e.getMessage());
		} finally {
			sc.close();
		}
		
	}
	
	private static int selectedOption(int lastOption) {
		int n = 0;
		while (n == 0 || n > lastOption) {
			System.out.print("Enter: ");
			n = sc.nextInt();
			if (n == 0 || n > lastOption)
				System.out.println("Invalid option");
		}
		return n;
	}

	private static void printOptions() {
		System.out.println("What do you want to do?");
		System.out.print("1) Insert\n"
				+ "2) Delete\n"
				+ "3) To update\n"
				+ "4) Close\n");		
	}
	
	private static void printAllSellers() {
		List<Seller> list;
		System.out.println("Sellers:");
		list = sellerDao.findAll();
		list.forEach(System.out::println);
	}
	
	private static void printAllDepartments() {
		List<Department> list;
		System.out.println("Departments:");
		list = departmentDao.findAll();
		list.forEach(System.out::println);
	}
	
	private static Integer insertSeller(String name, String email, String birthDate, Double baseSalary, Integer departmentId) throws ParseException {
		Seller newSeller = new Seller(null, name, email, sdf.parse(birthDate), baseSalary, new Department(departmentId, null));
		sellerDao.insert(newSeller);
		return newSeller.getId();
	}
	
	private static void updateSeller(String name, String email, String birthDate, Double baseSalary, Integer departmentId, Seller seller) throws ParseException {
		seller.setName(name);
		seller.setEmail(email);
		seller.setBirthDate(sdf.parse(birthDate));
		seller.setBaseSalary(baseSalary);
		seller.setDepartment(new Department(departmentId, null));		
		sellerDao.update(seller);
	}
	
	private static Integer insertDepartment(String name) {
		Department newDepartment = new Department(null, name);
		departmentDao.insert(newDepartment);
		return newDepartment.getId();
	}
	
	private static void updateDepartment(String name, Department department) {
		department.setName(name);
		departmentDao.update(department);
	}
	
}
