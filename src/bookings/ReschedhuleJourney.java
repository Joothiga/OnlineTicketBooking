package bookings;

import mainpage.MenuOptions;
import registration_and_login.DbCredintials;
import registration_and_login.MemberLogin;

import java.sql.*;

import CustomExceptions.PSNullException;

public class ReschedhuleJourney
{
    Connection connection = null;
    PreparedStatement statement = null;
    static ReschedhuleJourney obj = new ReschedhuleJourney();
    public static void rescheduleCase() throws SQLException, PSNullException {
        if(MenuOptions.menuOptions.fromUserLogin || MemberLogin.memberVerification("user"))
        {
            rescheduleJourney();
        }
        else
        {
            System.out.println("You are not a valid user");
        }
    }
    public static void rescheduleJourney() throws SQLException, PSNullException {
        if(previousBookings())
        {
        	System.out.println("\t\t Tours Available");
            PlanJourney.toursAvailable();
            System.out.println("If you want to re-schedule your journey, Enter previously scheduled bus_no");
            String previousBookedBusNo = MenuOptions.input.next();
            System.out.print("Enter present schedule bus_no");
            int presentBusNo = MenuOptions.input.nextInt();
            System.out.println("Enter New schedule Date from the above available dates");
            String newDate = MenuOptions.input.next();
            bookingUpdate(newDate,previousBookedBusNo,presentBusNo);
            System.out.print("Updated successfully");
        }
        else
        {
            System.out.println("You are not book any tickets till now");
        }
    }

    public static boolean previousBookings() throws SQLException, PSNullException 
    {
		try {
			obj.connection = DbCredintials.getDBConnection();
			if (MenuOptions.menuOptions.fromAdminLogin) {
				String query = "SELECT email_id,from_place,to_place,price,journey_details.bus_no,date_of_travel FROM journey_details JOIN user_journey_details ON journey_details.bus_no = user_journey_details.bus_no AND journey_details.available_date = user_journey_details.date_of_travel";
				obj.statement = obj.connection.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			} else {
				String query = "SELECT from_place,to_place,price,journey_details.bus_no,date_of_travel FROM journey_details JOIN user_journey_details ON journey_details.bus_no = user_journey_details.bus_no AND journey_details.available_date = user_journey_details.date_of_travel WHERE email_id = ?";
				obj.statement = obj.connection.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				obj.statement.setString(1, MemberLogin.userMail);
			}
			ResultSet result = obj.statement.executeQuery();
			result.next();
			if(!result.next())
			{
				return false;
			}
			else {
				result.beforeFirst();
				ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			if (columnCount == 0) {
				System.out.println("You didn't have any bookings");
				return false;
			} else {
				System.out.println("\t\t Your prevoius bookings");
				final String FORMAT = "%-13s";
				for (int i = 1; i <= columnCount; i++) {
					System.out.printf(FORMAT, metaData.getColumnName(i));
				}
				System.out.println();
				while (result.next()) {
					for (int i = 1; i <= columnCount; i++) {
						System.out.printf(FORMAT, result.getString(i));
					}
					System.out.println();
				}
			}
		    }
			return true;
		}
		finally {
			obj.statement.close();
			obj.connection.close();
		}
    }

    public static void bookingUpdate(String newDate,String previousBookedBusNo,int presentBusNo) throws SQLException
    {
    	try {
    		 	obj.connection = DbCredintials.getDBConnection();
    	        String query = "UPDATE user_journey_details SET date_of_travel = ?, bus_no = ? WHERE email_id = ? AND bus_no = ?";
    	        obj.statement = obj.connection.prepareStatement(query);
    	        obj.statement.setString(1,newDate);
    	        obj.statement.setInt(2,presentBusNo);
    	        obj.statement.setString(3,MemberLogin.mail);
    	        obj.statement.setString(4,previousBookedBusNo);
    	        obj.statement.executeUpdate();
		} 
       finally
       {
	       obj.statement.close();
	       obj.connection.close();
	   }
    }
}
