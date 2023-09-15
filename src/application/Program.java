package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== TEST 1: seller findById ===");
		System.out.println(sellerDao.findById(3));
		
		System.out.println("\n=== TEST 2: seller findByDepartment ===");
		List<Seller> findByDepartment = sellerDao.findByDepartment(new Department(2, null));
		findByDepartment.forEach(System.out::println);
		
		System.out.println("\n=== TEST 3: seller findAll ===");
		List<Seller> findAll = sellerDao.findAll();
		findAll.forEach(System.out::println);
		
		System.out.println("\n=== TEST 4: seller insert ===");
		Seller insert = new Seller(null, "Eduardo Souza", "eduardo@gmail.com", sdf.parse("02/11/1998"), 4000.00, new Department(2, null));
		sellerDao.insert(insert);
		System.out.println("Inserted! New ID: " + insert.getId());
		
	}
}
