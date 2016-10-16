package com.flp.ems.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import com.flp.ems.domain.Employee;
import com.flp.ems.util.Constants;
import com.flp.ems.util.ServiceLocator;
import com.flp.ems.util.ServiceLocatorException;
import com.flp.ems.util.Utils;

public class EmployeeDaoDS implements IemployeeDao{
	
	private DataSource dataSource = null;
//	private Properties props = new Properties();
	private Utils utils = new Utils();
	private String tableName = "employee1";
	private String jndiName = "jdbc/EMSDataSource";
	/*ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	InputStream input = classLoader.getResourceAsStream("dbDetails.properties");*/
	
	
	public EmployeeDaoDS() {
		// TODO Auto-generated constructor stub
		try {
			System.out.println("in dao before gettion datasource");
			dataSource = ServiceLocator.getDataSource(jndiName);
			System.out.println("in dao after gettion datasource");
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;	
	}
	
	@Override
	public boolean addEmployee(Employee employee) {
		Connection connection = null;
		PreparedStatement insertStatement = null;
		int rows = 0;
		boolean status =false;
		String email,newEmail="";
		
		//creates KinId in the format 'EMS00001'
		int latestKey = getLatestKey();
		String suffix = String.format ("%05d", (latestKey+1));
		employee.setKinId(Constants.Prefix+suffix);
		
		String insertQuery = "insert into "+tableName+" (kinId,name,emailId,phoneNo,dateOfBirth,dateOfJoining,address,deptId,projectId,roleId) values (?,?,?,?,?,?,?,?,?,?)"	;
		

		try{
			try {
				connection = (Connection) dataSource.getConnection();
				connection.setAutoCommit(false);
				insertStatement = connection.prepareStatement(insertQuery,PreparedStatement.RETURN_GENERATED_KEYS);
				
				insertStatement.setString(1, employee.getKinId());
				insertStatement.setString(2, employee.getName());

				// REGENERATEs EMAIL IF ALREADY EXISTS
				email = employee.getEmailId();
				if (!utils.ifEmailNotAssigned(email, connection)) {
					newEmail = utils.regenerateEmail(email);
					email = newEmail;
				}

				insertStatement.setString(3, email);
				insertStatement.setString(4, Long.toString(employee.getPhoneNo()));
				insertStatement.setString(5, employee.getDateOfBirth());
				insertStatement.setString(6, employee.getDateOfJoining());
				insertStatement.setString(7, employee.getAddress());
				insertStatement.setInt(8, employee.getDeptId());
				insertStatement.setInt(9, employee.getProjectId());
				insertStatement.setInt(10, employee.getRoleId());

				rows = insertStatement.executeUpdate();
				connection.commit();
				if (rows > 0)
					status = true;
				
				System.out.println(rows+" rows inserted");
			} 
			catch (SQLException e) {
				if (connection != null)
					connection.rollback();	
				
				throw e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			finally {
				if (connection != null)
					connection.close();				
			}
		}
		catch(SQLException e){
//			throw new JDBCDaoException("SQL error while excecuting query: "+ insertQuery,e);
			System.out.println("SQL error while excecuting query: "+ insertQuery);
			e.printStackTrace();
		}	
		
		return status;
	}

	
	@Override
	public boolean modifyEmployee(Employee employee) {
		Connection connection = null;
		PreparedStatement updateStatement = null;
		int rows = 0;
		boolean status = false;
		
		String updateQuery = "update "+tableName+" SET name=?,emailId=?,phoneNo=?,dateOfBirth=?,dateOfJoining=?,address=?,deptId=?,projectId=?,roleId=? WHERE empId=?"	;
		
		try{
			try {
				connection = (Connection) dataSource.getConnection();
				updateStatement = connection.prepareStatement(updateQuery);
				
				updateStatement.setString(1, employee.getName());
				updateStatement.setString(2, employee.getEmailId());
				updateStatement.setString(3, Long.toString(employee.getPhoneNo()));
				updateStatement.setString(4, employee.getDateOfBirth());
				updateStatement.setString(5, employee.getDateOfJoining());
				updateStatement.setString(6, employee.getAddress());
				updateStatement.setInt(7, employee.getDeptId());
				updateStatement.setInt(8, employee.getProjectId());
				updateStatement.setInt(9, employee.getRoleId());
				
				updateStatement.setInt(10, employee.getEmpId());
				rows = updateStatement.executeUpdate();
				
				if(rows>0)
					status = true;
				System.out.println(rows+" rows updated");
			} 
			catch (SQLException e) {
				if (connection != null)
					connection.rollback();	
				
				throw e;
			} 
			finally {
				if (connection != null)
					connection.close();				
			}
		}
		catch(SQLException e){
			//throw new JDBCDaoException("SQL error while excecuting query: "+ updateQuery,e);
			System.out.println("SQL error while excecuting query: "+ updateQuery);
			e.printStackTrace();
		}	
		
		return status;
	}

	@Override
	public boolean removeEmployee(String[] kinId) {
		Connection connection = null;
		PreparedStatement deleteStatement = null;
		int rows = 0;
		boolean status = false;
		String deleteQuery = "delete from "+tableName+" where empId=?";
		
		try{
			try {
				connection = (Connection) dataSource.getConnection();
				deleteStatement = connection.prepareStatement(deleteQuery);
				
				
				if(kinId!=null){
					for(String id : kinId){
						deleteStatement.setInt(1, Integer.parseInt(id));
						rows+=deleteStatement.executeUpdate();
					}
				}
				
				if (rows > 0)
					status = true;
				System.out.println(rows+" rows deleted");
			} 
			catch (SQLException e) {
				if (connection != null)
					connection.rollback();	
				
				throw e;
			} 
			finally {
				if (connection != null)
					connection.close();				
			}
		}
		catch(SQLException e){
			//throw new JDBCDaoException("SQL error while excecuting query: "+ deleteQuery,e);
			System.out.println("SQL error while excecuting query: "+ deleteQuery);
			e.printStackTrace();
		}
		
		return status;
	}

	
	@Override
	public Employee searchEmployee(String type, String value) {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		Employee emp = new Employee();
		String selectQuery = "";
		
		if (type.equals(Constants.kinId)) {
			selectQuery = "select * from "+tableName+" where empId=?";
		} else if (type.equals(Constants.emailId)) {
			selectQuery = "select * from "+tableName+" where emailId=?";
		} else if (type.equals(Constants.name)) {
			value = "%" + value + "%";//for filtering
			selectQuery = "select * from "+tableName+" where name=?";
		} else {
			return null;
		}
		
		
		try{
			try {
				connection = dataSource.getConnection();
				connection.setAutoCommit(false);
				selectStatement = connection.prepareStatement(selectQuery);
				
				selectStatement.setString(1, value);
				
				ResultSet rs = selectStatement.executeQuery();
				
				if(rs.next()){
					emp.setEmpId(rs.getInt(1));
					emp.setKinId(rs.getString(2));
					emp.setName(rs.getString(3));
					emp.setEmailId(rs.getString(4));
					emp.setPhoneNo(Long.parseLong(rs.getString(5)));
					emp.setDateOfBirth(rs.getString(6));
					emp.setDateOfJoining(rs.getString(7));
					emp.setAddress(rs.getString(8));
					emp.setDeptId(rs.getInt(9));
					emp.setProjectId(rs.getInt(10));
					emp.setRoleId(rs.getInt(11));
				}
			} 
			finally {
				if (connection != null)
					connection.close();			
			}
		}
		catch(SQLException e){
//			throw new JDBCDaoException("SQL error while excecuting query: "+ selectQuery,e);
			System.out.println("SQL error while excecuting query: "+ selectQuery);
			e.printStackTrace();
		}
		
		return emp;
	}

	@Override
	public Employee searchEmployeeById(int empId) {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		Employee emp = new Employee();
		String selectQuery = "select * from "+tableName+" where empId=?";
		
		try{
			try {
				connection = (Connection) dataSource.getConnection();
				selectStatement = connection.prepareStatement(selectQuery);
				
				selectStatement.setInt(1, empId);
				
				ResultSet rs = selectStatement.executeQuery();		
				
				if(rs.next()){
					emp.setEmpId(rs.getInt(1));
					emp.setKinId(rs.getString(2));
					emp.setName(rs.getString(3));
					emp.setEmailId(rs.getString(4));
					emp.setPhoneNo(Long.parseLong(rs.getString(5)));
					emp.setDateOfBirth(rs.getString(6));
					emp.setDateOfJoining(rs.getString(7));
					emp.setAddress(rs.getString(8));
					emp.setDeptId(rs.getInt(9));
					emp.setProjectId(rs.getInt(10));
					emp.setRoleId(rs.getInt(11));
				}
			} 
			catch (SQLException e) {
				if (connection != null)
					connection.rollback();	
				
				throw e;
			} 
			finally {
				if (connection != null)
					connection.close();				
			}
		}
		catch(SQLException e){
//			throw new JDBCDaoException("SQL error while excecuting query: "+ selectQuery,e);
			System.out.println("SQL error while excecuting query: "+ selectQuery);
			e.printStackTrace();
		}
		
		return emp;
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<>();
		Connection connection = null;
		Statement selectStatement = null;
		String selectQuery = "select * from "+tableName;
		
		try{
			try {
				connection = (Connection) dataSource.getConnection();
				selectStatement = connection.createStatement();
				ResultSet rs;
				
				rs = selectStatement.executeQuery(selectQuery);		
				
				while(rs.next()){
					Employee emp = new Employee();
					
					emp.setEmpId(rs.getInt(1));
					emp.setKinId(rs.getString(2));
					emp.setName(rs.getString(3));
					emp.setEmailId(rs.getString(4));
					emp.setPhoneNo(Long.parseLong(rs.getString(5)));
					emp.setDateOfBirth(rs.getString(6));
					emp.setDateOfJoining(rs.getString(7));
					emp.setAddress(rs.getString(8));
					emp.setDeptId(rs.getInt(9));
					emp.setProjectId(rs.getInt(10));
					emp.setRoleId(rs.getInt(11));
					
					employees.add(emp);
				}
				
			} 
			catch (SQLException e) {
				if (connection != null)
					connection.rollback();	
				
				throw e;
			} 
			finally {
				if (connection != null)
					connection.close();				
			}
		}
		catch(SQLException e){
//			throw new JDBCDaoException("SQL error while excecuting query: "+ selectQuery,e);
			System.out.println("SQL error while excecuting query: "+ selectQuery);
			e.printStackTrace();
		}
		
		return employees;
	}

	
	private int getLatestKey(){
		String query="select max(empId) from "+tableName;
		Connection connection = null;
		Statement selectStatement = null;
		int  key = -1;
		

		try{
			try {
				connection = (Connection) dataSource.getConnection();
				selectStatement = connection.createStatement();
				ResultSet rs;
				
				rs = selectStatement.executeQuery(query);		
				
				if(rs.next())
					key = rs.getInt(1);
				else
					key=0;
				
			} 
			catch (SQLException e) {
				if (connection != null)
					connection.rollback();	
				
				throw e;
			} 
			finally {
				if (connection != null)
					connection.close();				
			}
		}
		catch(SQLException e){
//			throw new JDBCDaoException("SQL error while excecuting query: "+ query,e);
			System.out.println("SQL error while excecuting query: "+ query);
			e.printStackTrace();
		}
		
		
		return key;
	}
}
