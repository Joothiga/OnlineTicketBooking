package bookings;

import mainpage.MenuOptions;
import registration_and_login.DbCredintials;
import registration_and_login.MemberLogin;

import java.sql.*;
import java.time.LocalDate;

import CustomExceptions.PSNullException;
import login.UserOptions;

class OrderConfirmation 
{
	private OrderConfirmation()
	{}
    static String confirmation;
    static String from;
    
    static String to;

    public static void bookingTicket() throws SQLException{
        int bill = 0;
        System.out.println("\t\t Ticket Counter");
        busNumberVerifier(bill);       
    }

	public static void busNumberVerifier(int bill) throws SQLException{
		System.out.println("Enter Bus Number");
        String busnumber = MenuOptions.input.next();
		BusSchedule.getDatesbyBusno(busnumber);
        if(PlanJourney.busNumbers.contains(busnumber))
        {
        	availableDateVerifier(bill,busnumber);
        }
        else 
        {
			System.out.println("\tInvalid Bus Number");
			busNumberVerifier(bill);
		}
	}

	public static void availableDateVerifier(int bill,String busnumber) throws SQLException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		System.out.println("Enter Date(YYYY-MM-DD)");
		String date = MenuOptions.input.next();
		if(BusSchedule.datesAvailable == null)
		{
			System.out.println("No more schedhules available");
		}
		else 
		{
			if(BusSchedule.datesAvailable.contains(date))
	    	{
				try {
		    		billing(bill, connection, preparedStatement, busnumber, date);
				} catch (PSNullException e) {
					System.out.println(e);
				}
	    	}
	    	else 
	    	{
				System.out.println("\tBuses are not available on that day");
			}
		}
	}

	public static void billing(int bill, Connection connection, PreparedStatement preparedStatement, String busnumber,
			String date) throws SQLException{
		System.out.println("To confirm booking enter Yes(y) otherwise No(n)");
        confirmation = MenuOptions.input.next();
        PreparedStatement statement1 = preparedStatement;
        PreparedStatement statement2 = preparedStatement; 
        if(confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes"))
        {
            try {
                connection = DbCredintials.getDBConnection();
                statement1 = connection.prepareStatement("SELECT from_place,to_place,price FROM journey_details WHERE bus_no = ? AND available_date = ?");
                statement1.setString(1,busnumber);
                statement1.setString(2, date);
                ResultSet result = statement1.executeQuery();
                if(result == null)
                {
                	System.out.println("Data not Available");
                }
                else {
					while(result.next())
                    {
                        from = result.getString(1);
                        to = result.getString(2);
                        bill = result.getInt(3);
                    }
                    LocalDate todayDate = LocalDate.parse(date);
                    String dayOfWeek = String.valueOf(todayDate.getDayOfWeek());
                    if(dayOfWeek.equals("SATURDAY") || dayOfWeek.equals("SUNDAY"))
                    {
                        System.out.println("\t You are travelling in weekends, So you pay 20rupees more than other days cost");
                        System.out.println("\t Booking is Confirmed");
                        System.out.println("\t Bill of " + (bill+20) + " was completed");          // Weekends
                        System.out.println("\t Thank you, Your Ticket was booked");
                    }
                    else
                    {
                        System.out.println("\t Booking is Confirmed");
                        System.out.println("\t Bill of " + bill + " was completed");          // Other Days
                        System.out.println("\t Thank you, Your Ticket was booked");
                    }
                    try {
						statement2 = connection.prepareStatement("INSERT INTO user_journey_details VALUES(?,?,?,?)");
		                statement2.setString(1,MemberLogin.mail);
		                statement2.setString(2,busnumber);
		                statement2.setString(3,date);
		                statement2.setInt(4,bill);
		                statement2.executeUpdate();
					}
                    finally {
                    	statement2.close();
					}
				}    
            }
            finally
            {
            	statement1.close();
                connection.close();
            }
        }
        else
        {
            System.out.println("Booking cancelled");
            PlanJourney.toursAvailable();
        }
	}
}