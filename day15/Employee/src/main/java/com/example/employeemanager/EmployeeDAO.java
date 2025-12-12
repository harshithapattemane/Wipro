package com.example.employeemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDAO {

	
	public void addEmployee(Employee emp) throws SQLException{
		
		//PreparedStatement , Statement and CallableStatement
		String sql = "insert into employee(id,name,department,salary)values (?,?,?,?)";
		//The advantage of using try with resources that we need to not explicitly close it 
		try(Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
		{
			
			ps.setInt(1, 101);
			ps.setString(2, "Niti Dwivedi");
			ps.setString(3, "Training");
			ps.setDouble(4, 45000.00);
			
			ps.executeUpdate();
		}
	}
}

