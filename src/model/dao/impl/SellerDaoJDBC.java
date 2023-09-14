package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	

	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			String query = "SELECT d.name as DepartmentName, s.* FROM seller as s "
					+ "INNER JOIN department as d ON s.departmentId = d.id "
					+ "WHERE s.id = ?";
			st = conn.prepareStatement(query);

			st.setInt(1, id);
	
			rs = st.executeQuery();
			
			if (rs.next()) {
				
				Integer departmentId = rs.getInt("DepartmentId");
				String departmentName = rs.getString("DepartmentName");
				Department department = new Department(departmentId, departmentName);
				
				Integer sellerId = rs.getInt("Id");
				String name = rs.getString("Name");
				String email = rs.getString("Email");
				Double baseSalary = rs.getDouble("BaseSalary");
				Date birthDate = rs.getDate("BirthDate");
				return new Seller(sellerId, name, email, birthDate, baseSalary, department);
				
			}
			
			return null;

		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
