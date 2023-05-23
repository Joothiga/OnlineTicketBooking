package login;

import java.sql.SQLException;

import CustomExceptions.PSNullException;
import bookings.PlanJourney;
import bookings.ReschedhuleJourney;
import mainpage.AccountStatus;
import mainpage.MenuOptions;

public class UserOptions 
{
	public static void userLogin() throws SQLException, PSNullException
	{
		MenuOptions.menuOptions.fromUserLogin = true;
		boolean exitStatus = true;
		do
        {
            System.out.print("\n Enter your option \t");
            System.out.print(" 1. Lock Account \t");        // 5 incorrect passwords causes account lock.
            System.out.print(" 2. Plan Journey \t");
            System.out.print(" 3. Reschedule Journey \t ");
            System.out.print(" 4. My Journey Details \t ");
            System.out.println(" 5. Log Out");
            MenuOptions.menuOptions.option = MenuOptions.input.next();
            switch(MenuOptions.menuOptions.option) {
                case "1" : 
                	AccountStatus.statusLock();           // true --> account active    false --> account blocked.
                	MenuOptions.menuOptions.fromUserLogin = false;
                	exitStatus = false;
                	break;	
                case "2" : 
                	PlanJourney.planJourneyCase();
                	break;
                case "3" : 
                	ReschedhuleJourney.rescheduleCase();
                	break;
                case "4" :
                	ReschedhuleJourney.previousBookings();
                	break;
                case "5" : 
                	exitStatus = false;
                	MenuOptions.menuOptions.fromUserLogin = false;
                	break;
                default : System.out.println("Enter correct option");
            }
        }while(exitStatus);
	}
}
