package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== TEST 1: seller findById ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: seller findByDepartment ===");
		List<Seller> list = sellerDao.findByDepartment(new Department(2, null));
		list.forEach(System.out::println);
		
		System.out.println("\n=== TEST 3: seller findAll ===");
		list = sellerDao.findAll();
		list.forEach(System.out::println);
		
		System.out.println("\n=== TEST 4: seller insert ===");
		Seller newSeller = new Seller(null, "Eduardo Souza", "eduardo@gmail.com", sdf.parse("02/11/1998"), 4000.00, new Department(2, null));
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New ID: " + newSeller.getId());
		
		System.out.println("\n=== TEST 5: seller update ===");
		seller = sellerDao.findById(9);
		seller.setName("Eduardo Augusto");
		sellerDao.update(seller);
		System.out.println("Update completed!");
		
	}
}
