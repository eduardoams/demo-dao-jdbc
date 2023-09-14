package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department d = new Department(1, "Books");
		System.out.println(d);
		
		Seller s = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.00, d);
		System.out.println(s);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();		
	}
}
