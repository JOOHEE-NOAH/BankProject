package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
   Connection conn;
   public void createTable() {
	   conn = Jdbc.getInstance().getConnection();
       Statement stmt = null;
      try {
         stmt = conn.createStatement();
         int result = stmt.executeUpdate("create table if not exists member(ID varchar(50) not null primary key,"
         		+ " Name varchar(50) not null,"
         		+ " PW varchar(50),"
         		+ " BALANCE int unsigned,"
         		+ " ACCOUNTNO varchar(13) not null) ");
         if (result < 0 ) {
            System.out.println("실행실패");
         }else {
            System.out.println("실행성공");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         try {
               if (stmt != null)   stmt.close();
               if (conn != null)   conn.close();   
         
         } catch (SQLException e) {
            // TODO: handle exception
         }
      }
   }
   public static void main(String[] args) {
      CreateTable ct = new CreateTable();
      ct.createTable();
   }

}