package application;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.print("Enter your seller ID: ");
		int id = sc.nextInt();
		Seller seller = sellerDao.findById(id);
		
		System.out.println(seller);
	}
}
