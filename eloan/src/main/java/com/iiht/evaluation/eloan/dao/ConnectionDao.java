package com.iiht.evaluation.eloan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.iiht.evaluation.eloan.model.ApprovedLoan;
import com.iiht.evaluation.eloan.model.LoanInfo;
import com.iiht.evaluation.eloan.model.User;

public class ConnectionDao {
	private static final long serialVersionUID = 1L;
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public ConnectionDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	public  Connection connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");//com.mysql.jdbc.Driver
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
		return jdbcConnection;
	}

	public void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	// put the relevant DAO methods here..
	public User getRegisterNewUser(String username, String email, String password, String role, String phone) throws SQLException{
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "INSERT INTO users (username, email, password,role,phone) VALUES( "+"'"+username+"'"+","+"'"+email+"'"+","+"'"+password+"'"+","+null+","+"'"+phone+"'"+")";
		int success = st.executeUpdate(sql);
		if(success==1){
			String sql2 = "select * from users where username=" + "'"+username+"'" +" and password="+"'"+password+"'"+"";
			ResultSet rs = st.executeQuery(sql2);
			User user = new User();
			if(rs.next())
			{
				user.setUsername(rs.getString("username"));
				user.setId(rs.getString("id"));
			}
			return user;
		}else{
			return null;
		}
		
	}
	
	public User getRegisterUser(String username, String password) throws SQLException{
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "select * from users where username=" + "'"+username+"'" +" and password="+"'"+password+"'"+"";
		ResultSet rs = st.executeQuery(sql);
		User user = null;
		if(rs.next())
		{
			user = new User();
			user.setUsername(rs.getString("username"));
			user.setId(rs.getString("id"));
			user.setRole(rs.getString("role"));
		}
		
		return user;
	}
	
	public LoanInfo putLoanRequest(String loanname, String loanamount,
			String businessstruture, String taxindiacator, String address1, String address2, String address3,
			String uid) throws SQLException {
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "INSERT INTO loans (loanname, loanamount, businessstruture,taxindiacator, address1, address2, address3, id) "
				+ "VALUES( " + "'" + loanname + "'" + "," + "'" + loanamount + "'" + "," + "'" + businessstruture + "'"
				+ "," +  "'" + taxindiacator +  "'" + "," + "'" + address1 + "'" + "," + "'" + address2 + "'" + "," + "'" + address3
				+ "'" + "," +  "'" +  uid +  "'" + ")";
		int success = st.executeUpdate(sql);
		if (success == 1) {
			String sql1 = "select * from loans where loanname=" + "'" + loanname + "'" + " and loanamount=" + "'"
					+ loanamount + "'" + "";
			ResultSet rs = st.executeQuery(sql1);
			LoanInfo loan = new LoanInfo();
			if (rs.next()) {
				loan.setApplno(rs.getString("loanid"));				
				loan.setAmtrequest(Integer.parseInt(rs.getString("loanamount")));
				loan.setBstructure(rs.getString("businessstruture"));
				loan.setDoa(rs.getString("loan_create_time"));
				loan.setBindicator(rs.getString("taxindiacator"));
				loan.setAddress(rs.getString("address1") + rs.getString("address2") + rs.getString("address3"));
				loan.setLoanname(rs.getString("loanname"));
			}
			return loan;
		} else {
			return null;
		}

	}
	
	public List<LoanInfo> getUserLoansList() throws SQLException{
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "select * from loans";
		ResultSet rs = st.executeQuery(sql);
		List<LoanInfo> loans = new ArrayList<LoanInfo>();
		while(rs.next()){
			LoanInfo loan = new LoanInfo();
			loan.setApplno(rs.getString("loanid"));				
			loan.setAmtrequest(Integer.parseInt(rs.getString("loanamount")));
			loan.setBstructure(rs.getString("businessstruture"));
			loan.setDoa(rs.getString("loan_create_time"));
			loan.setBindicator(rs.getString("taxindiacator"));
			loan.setAddress(rs.getString("address1") + rs.getString("address2") + rs.getString("address3"));
			loan.setLoanname(rs.getString("loanname"));
			loans.add(loan);
		}
		
		
		return loans;
	}
	
	public LoanInfo getUserLoan(int loanId) throws SQLException{
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "select * from loans where loanid="+loanId;
		ResultSet rs = st.executeQuery(sql);
		
		LoanInfo loan = new LoanInfo();
		if (rs.next()) {
			loan.setUserId(Integer.parseInt(rs.getString("id")));
			loan.setApplno(rs.getString("loanid"));				
			loan.setAmtrequest(Integer.parseInt(rs.getString("loanamount")));
			loan.setBstructure(rs.getString("businessstruture"));
			loan.setDoa(rs.getString("loan_create_time"));
			loan.setBindicator(rs.getString("taxindiacator"));
			loan.setAddress(rs.getString("address1") + rs.getString("address2") + rs.getString("address3"));
			loan.setLoanname(rs.getString("loanname"));
			loan.setStatus(rs.getString("loanstatus"));
		}
		return loan;
	}
	
	public LoanInfo getUserLoanByUserId(int userid) throws SQLException{
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "select * from loans where loanid="+userid;
		ResultSet rs = st.executeQuery(sql);
		
		LoanInfo loan = new LoanInfo();
		if (rs.next()) {
			loan.setUserId(Integer.parseInt(rs.getString("id")));
			loan.setApplno(rs.getString("loanid"));				
			loan.setAmtrequest(Integer.parseInt(rs.getString("loanamount")));
			loan.setBstructure(rs.getString("businessstruture"));
			loan.setDoa(rs.getString("loan_create_time"));
			loan.setBindicator(rs.getString("taxindiacator"));
			loan.setAddress(rs.getString("address1") + rs.getString("address2") + rs.getString("address3"));
			loan.setLoanname(rs.getString("loanname"));
			loan.setStatus(rs.getString("loanstatus"));
		}
		return loan;
	}
	
	public List<LoanInfo>  getLoanStatus(int userId) throws SQLException{
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "select * from loans where id="+userId;
		ResultSet rs = st.executeQuery(sql);
		
		List<LoanInfo> loans = new ArrayList<LoanInfo>();
		while(rs.next()){
			LoanInfo loan = new LoanInfo();
			loan.setApplno(rs.getString("loanid"));	
			String status;
			if(rs.getString("loanstatus") == null){
				status = "In Review";
			}else{
				status = rs.getString("loanstatus");
			}
			loan.setStatus(status);
			loans.add(loan);
		}
		return loans;
	}
	
	public ApprovedLoan getApprovedLoan(int loanId) throws SQLException{
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "select * from approvedloans where loanid="+loanId;
		ResultSet rs = st.executeQuery(sql);
		
		ApprovedLoan approvedLoan = new ApprovedLoan();
		if (rs.next()) {
			approvedLoan.setApplno(rs.getString("loanid"));				
			approvedLoan.setAmotsanctioned(Integer.parseInt(rs.getString("amountsanctioned")));
			approvedLoan.setLoanterm(Integer.parseInt(rs.getString("loanterm")));
			approvedLoan.setPsd(rs.getString("psd"));
			approvedLoan.setLcd(rs.getString("lcd"));
			approvedLoan.setEmi(Integer.parseInt(rs.getString("emi")));
		}
		return approvedLoan;
		
	}
	
	
	
	public boolean saveApprovedLoan(String loanId, double sanctionedAmount, int loanTerm,
			String paymentStartDate, String loanClosureDate, double emi) throws SQLException {
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "INSERT INTO approvedloans (amountsanctioned, loanterm, psd,lcd, emi, loanid) "
				+ "VALUES( " + "'" + sanctionedAmount + "'" + "," + "'" + loanTerm + "'" + "," + "'" + paymentStartDate + "'"
				+ "," +  "'" + loanClosureDate +  "'" + "," + "'" + emi + "'" +  "," + "'" + loanId
				+ "'" + ")";
		int success = st.executeUpdate(sql);
		return success == 1;
		
		}
	
	public boolean updateLoanStatus(String loanId, String status) throws SQLException {
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		String sql = "UPDATE loans SET loanstatus = "+ "'" + status + "'" +  " WHERE loanid="+loanId;
		int success = st.executeUpdate(sql);
		return success ==1;
		}
	
	
	public boolean updateLoan(int loanId, String loanName, String loanAmount, String bs, String ti, String a1, String a2, String a3) throws SQLException {
		jdbcConnection = connect();
		Statement st = jdbcConnection.createStatement();
		
		String sql = "UPDATE loans SET loanname = "+ "'" + loanName + "'" + "," + "loanamount = "+ "'" + loanAmount + "'" + "," +"businessstruture = "+ "'" + bs + "'" + "," +"taxindiacator = "+ "'" + ti + "'" + "," +"address1 = "+ "'" + a1 + "'" + "," +"address2 = "+ "'" + a2 + "'" + "," +"address3 = "+ "'" + a3 + "'"  +" WHERE loanid="+loanId;
		int success = st.executeUpdate(sql);
		return success ==1;
		}
	
	
	
}
