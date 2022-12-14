package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
   Connection conn;
   //멤버테이블 생성
   public void createMemberTable() {
	   conn = Jdbc.getInstance().getConnection();
       Statement stmt = null;
      try {
         stmt = conn.createStatement();
         int result = stmt.executeUpdate("CREATE TABLE IF NOT EXISTS MEMBER("
							         		+ " ID varchar(50) PRIMARY KEY,"
							         		+ " Name varchar(50) NOT NULL,"
							         		+ " PW varchar(50) NOT NULL,"
							         		+ " GRADE VARCHAR(5) DEFAULT 'C',"
							         		+ " RDATE DATETIME DEFAULT now())"
							         		);
         if (result < 0 ) {
            System.out.println("실행실패");
         }else {
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         try {
               if (stmt != null)   stmt.close();
               //if (conn != null)   conn.close();   
         
         } catch (SQLException e) {
            // TODO: handle exception
         }
      }
   }
   
   public void createAccountTable() {
	   conn = Jdbc.getInstance().getConnection();
       Statement stmt = null;
      try {
         stmt = conn.createStatement();
         int result = stmt.executeUpdate("create table if not exists ACCOUNT("
							           	    + " ACCOUNTNO varchar(13) PRIMARY KEY,"
							           	    + " ID VARCHAR(50),"
							           	    + " BALANCE int unsigned Default 0,"
							           	    + " OADATE DATETIME DEFAULT now(),"
							           	    + " FOREIGN KEY(ID)"
							           	    + " REFERENCES MEMBER(ID) ON DELETE CASCADE ON UPDATE CASCADE) "
							         		);
         if (result < 0 ) {
            System.out.println("실행실패");
         }else {
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         try {
               if (stmt != null)   stmt.close();
               //if (conn != null)   conn.close();   
         
         } catch (SQLException e) {
            // TODO: handle exception
         }
      }
   }
   public static void main(String[] args) {
      CreateTable ct = new CreateTable();
      ct.createMemberTable();
   }

}