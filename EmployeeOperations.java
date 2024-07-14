package com.practice;
import java.util.*;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class EmployeeOperations {
	
	static Scanner scan = new Scanner(System.in);
	static Connection con = null;
	static Statement stmt= null;
	static PreparedStatement pstmt=null;
	static ResultSet res=null;
	static  String url="jdbc:mysql://localhost:3306/jdbcadv";
	static  String username="root";
	static  String password="root";

	
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection(url,username,password);
			System.out.println("Select the operation to perform:");
			System.out.println("1.Display Table\n2.Diplay Specific Emp Details\n3.Insert new Employee \n"
					+ "4.Update Salary \n5.Delete Employee\n6.Exit");
			System.out.println("select option:");
			int option =scan.nextInt();
			switch(option)
			{
			case 1: displayTable();
			break;
			case 2: displayEmpDetails();
			break;
			case 3:insertEmployeeDetails();
			break;
			case 4:updateSalaryonDepartment();
			break;
			case 5:deleteEmpDetails();
			break;
			case 6: System.out.println("your Exited!...");
			break;
			default:System.out.println("Invalid entry");
			}
			}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			try {
				if(res!=null)
				{
					res.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try {
				if(pstmt!=null)
				{
					pstmt.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try {
				if(stmt!=null)
				{
					stmt.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			try {
				if(con!=null)
				{
					con.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

	}

	public static void displayTable()
	{int EmpId,Salary;
	 String Name,Email,Department;
	String sql="select * from employee";
	try {
		stmt=con.createStatement();
		res=stmt.executeQuery(sql);
		System.out.println("--------------------------------------------");
		while(res.next())
		{
			EmpId= res.getInt(1);
			Name=res.getString(2);
			Email=res.getString(3);
			Department=res.getString(4);
			Salary=res.getInt(5);
			
			System.out.printf("|%-1d|%-5s|%-15s|%-10s|%-7d|\n",
					+EmpId,Name,Email,Department,Salary);
			System.out.println("--------------------------------------------");
	}
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	}
	
	public static void displayEmpDetails(){
		int EmpId,Salary;
	 String Name,Email,Department;
	String sql="select * from employee where EmpId=?";
	System.out.println("Enter the Employee EmpId to view the details:");
	try {
		pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, scan.nextInt());
		res=pstmt.executeQuery();
		System.out.println("--------------------------------------------");
		while(res.next())
		{
			EmpId= res.getInt(1);
			Name=res.getString(2);
			Email=res.getString(3);
			Department=res.getString(4);
			Salary=res.getInt(5);

			System.out.printf("|%-1d|%-5s|%-15s|%-10s|%-7d|\n",
					+EmpId,Name,Email,Department,Salary);
			System.out.println("--------------------------------------------");
	}
		
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

	}
	
	public static void insertEmployeeDetails()
	{
		String sql="insert into `employee`(EmpId,Name,Email,Department,Salary)values (?,?,?,?,?)";
		System.out.println("Insert new Employee details to add:");
		try {
			String ch=null;
			pstmt=con.prepareStatement(sql);
			do
			{
				System.out.println("enter EmpId:");
				pstmt.setInt(1, scan.nextInt());
				System.out.println("enter Name:");
				pstmt.setString(2, scan.next());
				System.out.println("enter Email:");
				pstmt.setString(3, scan.next());
				System.out.println("enter Department:");
				pstmt.setString(4, scan.next());
				System.out.println("enter Salary:");
				pstmt.setInt(5, scan.nextInt());
				 pstmt.addBatch();
			System.out.println(" Do you insert more Details..(Yes/No");
			 ch=scan.next();
			}while(ch.equalsIgnoreCase("yes"));
			int[] x= pstmt.executeBatch();
			System.out.println("--------------------------------------------");
			int k=0;
			for(int i:x)
			{
				k+=i;
			}
			System.out.println("No of rows Effected:"+ k);
		  System.out.println("--------------------------------------------");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		}
	
	public static void updateSalaryonDepartment()
	{
		String sql="update employee set Salary=Salary+? where Department=?";
		System.out.println("updating the Salary of employees based on Department:");
		try {
			String ch=null;
			pstmt=con.prepareStatement(sql);
			do
			{
				System.out.println("enter Department:");
				pstmt.setString(2, scan.next());
				System.out.println("enter salary need to increment:");
				pstmt.setInt(1, scan.nextInt());
			System.out.println(" Do you update  Department ..(Yes/No");
			 ch=scan.next();
			 pstmt.addBatch();
			}while(ch.equalsIgnoreCase("yes"));
			int[] x=pstmt.executeBatch();
			System.out.println("--------------------------------------------");
			int k=0;
			for(int i:x)
			{
				k+=i;
			}
			System.out.println("No of rows Effected:"+ k);
			System.out.println("--------------------------------------------");
			}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void deleteEmpDetails()
	{
		String ch=null;
		String sql="delete from employee where EmpId=?";
		try {
			pstmt=con.prepareStatement(sql);
	do {
		System.out.println("Enter  Employee ID to delete the details");
		pstmt.setInt(1,scan.nextInt());
		System.out.println(" Do you want to delete more rows ..(Yes/No");
		ch=scan.next();
		pstmt.addBatch();
	}while(ch.equalsIgnoreCase("yes"));
	int []x=pstmt.executeBatch();
	System.out.println("--------------------------------------------");
	int k=0;
	for(int i:x)
	{
		k+=i;
	}
	System.out.println("No of rows Effected:"+ k);
	System.out.println("--------------------------------------------");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	}

