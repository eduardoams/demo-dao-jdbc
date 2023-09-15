package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== TEST 1: seller findById ===");
		System.out.println(sellerDao.findById(3));
		
		System.out.println("\n=== TEST 2: seller findByDepartment ===");
		List<Seller> findByDepartment = sellerDao.findByDepartment(new Department(2, null));
		findByDepartment.forEach(System.out::println);
		
		System.out.println("\n=== TEST 3: seller findAll ===");
		List<Seller> findAll = sellerDao.findAll();
		findAll.forEach(System.out::println);
		
	}
}
