package registration_and_login;

import mainpage.MenuOptions;
import CustomExceptions.PSNullException;

import java.sql.SQLException;

public class UserCreation
{
    private UserCreation()
    {}
    public static final UserCreation userCreation = new UserCreation();
	private String firstName;
    private String lastName;
    private String mobileNumber;
    private String gender;
    private String mail;
    private String password;
    static byte failCount = 0;
    private static boolean accountStatus = true;
	public static boolean isAccountStatus() {
		return accountStatus;
	}

    public static void registrationCase() throws SQLException{
        do {
            System.out.print("Enter your option \t");
            System.out.print(" 1. Admin registration \t");
            System.out.println(" 2. User registration \t");
            MenuOptions.menuOptions.option = MenuOptions.input.next();
            switch(MenuOptions.menuOptions.option)
            {
                case "1" :
                    UserCreation.memberRegistration("admin");
                    break;
                case "2":
                    UserCreation.memberRegistration("user");
                    break;
                case "0":
                    System.out.println("Exited From the Registration page");
                    RegistrationDB.status = false;                      // Exited
                    break;
                default:
                    System.out.println("Enter correct option");
            }
        }while(RegistrationDB.status);
    }
    public static void memberData()
    {
        System.out.println("Enter first Name");
        MenuOptions.input.nextLine();
        userCreation.firstName = MenuOptions.input.nextLine();
        System.out.println("Enter last Name");
        userCreation.lastName = MenuOptions.input.next();
        System.out.println("Enter mobile number");
        userCreation.mobileNumber = MenuOptions.input.next();
        System.out.println("Enter gender (Male/Female) ");
        userCreation.gender = MenuOptions.input.next();
        System.out.println("Enter mail-id ");
        userCreation.mail = MailValidation.getMail();
        System.out.println("Enter password ");
        userCreation.password = MenuOptions.input.next();
    }

	public static void memberRegistration(String member) throws SQLException{
		memberData();
		try {
			RegistrationDB.memberEntry(userCreation.firstName, userCreation.lastName, userCreation.mobileNumber, userCreation.gender, userCreation.mail, userCreation.password, member);
		} catch (PSNullException e) {
			System.out.println(e);
		}
	}	
}
