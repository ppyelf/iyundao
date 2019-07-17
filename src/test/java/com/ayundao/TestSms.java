//package com.ayundao;
//
//import com.huawei.api.SMEntry;
//import com.huawei.api.SMException;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Date;
//
//
//public class TestSms {
//
//    public static int testSendSms(Connection dbConn){
//        try {
//            Statement stmt = dbConn.createStatement();
//            String sql = "INSERT INTO tbl_SMSendTask " +
//                    "      ( CreatorID, TaskName,SmSendedNum,  OperationType, SuboperationType, SendType, OrgAddr, DestAddr, SM_Content, SendTime, NeedStateReport, ServiceID, FeeType, FeeCode, MsgID, SMType, MessageID, DestAddrType, SubTime, TaskStatus, SendLevel, SendState, TryTimes, [Count], SuccessID) " +
//                    "VALUES ('0000','','0','WAS','66','1','106573075060','13738700108','wangbohong',GETDATE(),'0','TZJ0010101','01','0','','0','0','0',GETDATE(),'0','0','0','3','1','0')";
//            stmt.executeUpdate(sql);
//            System.out.println("插入成功！");
//
//            return 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return 1;
//        }
//
//    }
//
//    public static void main(String[] args) throws SMException {
//
//        String driverName="com.microsoft.jdbc.sqlserver.SQLServerDriver";
//        String dbURL="jdbc:sqlserver://172.17.0.234:1433;DatabaseName=DB_CustomSMS";
//        String userName="CustomSMS";
//        String userPwd="SqlMsde@InfoxEie2000";
//        try
//        {
//            Class.forName(driverName);
//            System.out.println("加载驱动成功！");
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("加载驱动失败！");
//        }
//        try{
//            Connection dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
//            System.out.println("连接数据库成功！");
//            testSendSms(dbConn);
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//            System.out.print("SQL Server连接失败！");
//        }
//    }
//
//}
