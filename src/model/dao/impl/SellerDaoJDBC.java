package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		PreparedStatement st = null;
		
		try {
			
			String query = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES (?, ?, ?, ?, ?)";
			
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();				
				if (rs.next()) obj.setId(rs.getInt(1));
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
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

			String query = "SELECT d.name AS DepartmentName, s.* FROM seller AS s "
					+ "INNER JOIN department AS d ON s.departmentId = d.id "
					+ "WHERE s.id = ?";
			st = conn.prepareStatement(query);

			st.setInt(1, id);
	
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				return instantateSeller(rs, department);
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
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
						
			String query = "SELECT s.*, d.Name AS DepartmentName FROM seller AS s "
					+ "INNER JOIN department AS d ON s.DepartmentId = d.id "
					+ "ORDER BY s.Name";
			st = conn.prepareStatement(query);
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				list.add(instantateSeller(rs, dep));
				
			}
			
			return list;
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
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
						
			String query = "SELECT s.*, d.Name AS DepartmentName FROM seller AS s "
					+ "INNER JOIN department AS d ON s.DepartmentId = d.id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY s.Name";
			st = conn.prepareStatement(query);
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				list.add(instantateSeller(rs, dep));
				
			}
			
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Seller instantateSeller(ResultSet rs, Department department) throws SQLException {
		
		Integer sellerId = rs.getInt("Id");
		String name = rs.getString("Name");
		String email = rs.getString("Email");
		Double baseSalary = rs.getDouble("BaseSalary");
		Date birthDate = rs.getDate("BirthDate");
		
		return new Seller(sellerId, name, email, birthDate, baseSalary, department);
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		Integer departmentId = rs.getInt("DepartmentId");
		String departmentName = rs.getString("DepartmentName");
		
		return new Department(departmentId, departmentName);
	}
}
