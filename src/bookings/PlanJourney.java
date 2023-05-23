package bookings;

import registration_and_login.DbCredintials;
import registration_and_login.MemberLogin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import CustomExceptions.PSNullException;
import mainpage.MenuOptions;

public class PlanJourney
{
	private PlanJourney()
	{}
	static List<String> busNumbers = new ArrayList<>();
    public static void planJourneyCase() throws SQLException, PSNullException {
        try{
            if(MenuOptions.menuOptions.fromUserLogin || MemberLogin.memberVerification("user"))
            {
                PlanJourney.toursAvailable();
                OrderConfirmation.bookingTicket();
            }
            else
            {
                System.out.println("You are not a valid user");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public static void toursAvailable() throws SQLException, NullPointerException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	try {
        		connection = DbCredintials.getDBConnection();
                preparedStatement = connection.prepareStatement("SELECT * FROM journey_details",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			} catch (NullPointerException e) {
				System.out.println(e);
			}
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            if(!result.next())
            {
            	System.out.println("No more tours available");
            }
            else
            {
            	result.beforeFirst();
				ResultSetMetaData metaData = result.getMetaData();
				int columns = metaData.getColumnCount();
				for (int i = 1; i <= columns; i++) {
					System.out.printf("%-15s \t", metaData.getColumnName(i));
				}
				System.out.println();
				while (result.next()) {
					busNumbers.add(result.getString("bus_no"));
					for (int i = 1; i <= columns; i++) {
						System.out.printf("%-15s \t", result.getString(i));
					}
					System.out.println();
				}
				result.close();
            }
        }	
		finally {
        	if(preparedStatement == null)
        	{
        		throw new PSNullException("Check your DB Credentials and DB Syntax");
        	}
        	else 
        	{
				preparedStatement.close();
				connection.close();
			}
		}
    }
}
