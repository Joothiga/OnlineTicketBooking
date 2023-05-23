package mainpage;

import bookings.PlanJourney;
import bookings.ReschedhuleJourney;
import registration_and_login.MemberLogin;
import registration_and_login.UserCreation;
import CustomExceptions.PSNullException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuOptions 
{
    public static final Scanner input = new Scanner(System.in);
    public static final MenuOptions menuOptions = new MenuOptions();
    public String option;
    static boolean exit = true;                    // Entry Accepted.
    public boolean fromUserLogin = false;
    public boolean fromAdminLogin = false;
   
    public static void main(String[] args) throws LogoLoad, SQLException, IOException 
    {
    	String filePath = "C:\\Users\\20819\\OneDrive\\Desktop\\AAtravels.txt";
    	try 
    	{
			if(LogoLoad.logoLoading(filePath))
			{
				mainPage();
			}
			else {
				System.out.println("Please check logo file path");
			}
		} 
    	catch (SQLException | IOException | LogoLoad e) 
    	{
			System.out.println(e);
		}
    }

	public static void mainPage() throws SQLException {
		do {
			System.out.print("\n Enter your option \t");
			System.out.print(" 1. New Registration \t");
			System.out.print(" 2. Login \t");
			System.out.print(" 3. Lock Account \t"); // 5 incorrect passwords causes account lock.
			System.out.print(" 4. Unlock Account \t");
			System.out.print(" 5. Plan Journey \t");
			System.out.print(" 6. Reschedule Journey \t \n");
			System.out.println("Enter 0 to exit from the application");
			menuOptions.option = input.next();
			try {
				switch (menuOptions.option) {
				case "1" -> UserCreation.registrationCase();
				case "2" -> MemberLogin.loginCase();
				case "3" -> AccountStatus.statusLock(); // true --> account active false --> account blocked
				case "4" -> AccountStatus.statusUnlock();
				case "5" -> PlanJourney.planJourneyCase();
				case "6" -> ReschedhuleJourney.rescheduleCase();
				case "0" -> MenuOptions.exit = false;
				default -> System.out.println("Enter Correct Option");
				}
			} catch (PSNullException e) {
				System.out.println(e);
			}
		} while (MenuOptions.exit);
	}
}
