package services;
import java.util.*;

// for checking appointment dates (got this info from interneetttt)
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Locale;  

// all services extend this class
class DefaultService {
	private final int PART_COST = 40;
	private final int SERVICE_COST = 60;
	private final int TRAVEL_COST = 20;
	private final int EXTRA_COSTS = 20;
	
	private String serviceType;
	
	private boolean unknownFlag = false;	// if total cost will not be exact due to unknown parts
	private boolean holdOffAppointment = false;	// if appointment details should not be asked yet
	private boolean hasExtraCosts = false;	// if extra costs will apply
	
	private int currentIndex = 0;	// where in the parts and costs lists the next empty space is at
	private String partList[] = new String[10];	// holds parts and/or request-related stuff
	private boolean isPartUnknown[] = new boolean[10]; // holds if part it corresponds to is of unknown cost
	
	private Scanner sc = new Scanner(System.in);
	
	// constructor
	DefaultService(String serviceType, boolean holdOffAppointment) {
		this.serviceType = serviceType;
		this.holdOffAppointment = holdOffAppointment;
		printInitialization();
	}
	
	// print the service selected (made this private cause it shouldn't ever need to be called again after class is constructed)
	private void printInitialization() {
		System.out.println("You have selected " + serviceType + ". Type Back to return to the menu.");
	}
	
	// add a part to the part list
	protected void addPart(String p, boolean unknownCost) {
		partList[currentIndex] = p;
		if (unknownCost) {
			isPartUnknown[currentIndex] = true;
			unknownFlag = true;
		}
		currentIndex++;
	}
	
	// set whether service will have extra costs or not
	protected void setExtraCosts(boolean e) {
		hasExtraCosts = e;
	}
	
	// function to manage user input (returning false means proceed with program, true means return to the main menu)
	protected boolean inputParts(boolean uniqueUnknown, boolean onlyOneElement, boolean singleMultiples) {
		String input; String input2;
		beginInput:
		for (int i = 0; i < 10; i++) {
			System.out.print("test");
			input = sc.nextLine();
			
			// if "back" was entered
			if (input.equalsIgnoreCase("back")) {
				return true;
			}
			// if unknown was entered for the first answer and unknown is a unique answer
			else if (i == 0 && input.equalsIgnoreCase("unknown") && uniqueUnknown) {
				System.out.println( "The repairman will need to bring additional tools and take time " +
									"evaluating the situation, possibly even leave and come back should " +
									"the tools on hand not be sufficient. This process will cost extra " +
									"($20). We will also not be able to provide an exact cost for the " +
									"parts until they are determined. Would you still like this service? " +
									"Type Yes or No.");
				do {
					input2 = sc.nextLine();
					if (input2.equalsIgnoreCase("yes")) {
							addPart(input, true);
							setExtraCosts(true);
							break beginInput;	// end input since there is nothing else for user to input
						}
						else if (input2.equalsIgnoreCase("no"))
							return true; // restart service from beginning
						
						System.out.println("Please enter Yes or No.");
				} while (true);
			}
			// if unknown was entered for any answer other than the first and unknown is a unique answer
			else if (i != 0 && input.equalsIgnoreCase("unknown") && uniqueUnknown) {
				System.out.println( "Unknown can only be entered as your first answer. Would you like to " +
									"go back to the menu? Type Yes or No.");
				do {
					input2 = sc.nextLine();
					if (input2.equalsIgnoreCase("yes")) {
						return true;
					}
					else if (input2.equalsIgnoreCase("no")) {
						System.out.println("Enter another part. ");
						i--; continue beginInput; // go back one since no part was entered
					}
					System.out.println("Please enter Yes or No.");
				} while (true);
			}
			// if part entered was not unknown, multiple parts are allowed, and it is not multiple of a single item
			else if (!onlyOneElement && !singleMultiples) {
				addPart(input, false);
				if (i != 9) {
					System.out.println("Would you like to add more parts? Enter Yes or No.");
					do {
						input2 = sc.nextLine();
						if (input2.equalsIgnoreCase("no")) {
							break beginInput;
						}
						else if (input2.equalsIgnoreCase("yes")) {
							System.out.println("Enter another part. ");
							continue beginInput;
						}
						System.out.println("Please enter Yes or No.");
					} while (true);
				}
			}
			// if part entered was not unknown, multiple unique parts are not allowed, and it is multiple of a single item
			else if (singleMultiples) {
				int count;
				System.out.println("How many do you want delivered & installed?");
				do {
					count = sc.nextInt(); input2 = sc.nextLine(); // nextLine is called so that the next input doesn't break
					if (count < 1 || count > 10)
						System.out.println("Please enter a number between 1 and 10.");
					else {
						for (int j = 0; j < count; j++)
							addPart(input, false);
						break beginInput;
					}
				} while (true);
			}
			// if part entered was not unknown and multiple parts are not allowed
			else
				addPart(input, false);
				break;
		}
		return false;
	}
	
	// obtains and returns contact info (typing reset has user redo inputting contact and appointment info)
	protected String inputContactInfo() {
		String email, phoneNumber;
		
		System.out.print("You will now be entering contact ");
		if (!holdOffAppointment) System.out.print("and appointment ");
		System.out.println("information. If at any point you need to re-enter any information, type Reset.\n");
		System.out.println("Please enter your email address.");
		email = sc.nextLine();
		if (email.equalsIgnoreCase("reset")) return "reset";
		
		System.out.println("Please enter your phone number.");
		phoneNumber = sc.nextLine();
		if (phoneNumber.equalsIgnoreCase("reset")) return "reset";
		
		String contactInfo = ("Email Address: " + email + "\nPhone Number: " + phoneNumber);
		return contactInfo;
	}
	
	// assists with date input in inputApptInfo()
	private String inputDate() {
		// date validators
		Date date = new Date();  
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM");  
		String tempMonth = formatter.format(date); 
		
		formatter = new SimpleDateFormat("dd");  
		String tempDay = formatter.format(date); 
		
		formatter = new SimpleDateFormat("yyyy");  
		String tempYear = formatter.format(date); 
		
		int currentMonth = Integer.parseInt(tempMonth);
		int currentDay = Integer.parseInt(tempDay);
		int currentYear = Integer.parseInt(tempYear);
		
		// if wrong inputs were typed																		
		final String dateErrorMessage = "Incorrect input. Ensure the month, day, and year are within range and that it is " +
										"entered in this format:\nMM/DD/YEAR (Examples: 12/04/2020, 05/20/2021";
										
		int month, day, year;
		
		String input = sc.nextLine();
		
		// check if user typed reset
		if (input.equalsIgnoreCase("reset")) return "reset";
		
		// remove the slashes basically
		String temp1 = input.replaceAll("[^0-9]", "");
	
		// check length
		if (temp1.length() != 8) {
			System.out.println(dateErrorMessage);
			return inputDate();
		}
		
		// check for incorrect numbers
		String temp3 = temp1.substring(0, 2); month = Integer.parseInt(temp3);
		String temp4 = temp1.substring(2, 4); day = Integer.parseInt(temp4);
		String temp5 = temp1.substring(4); year = Integer.parseInt(temp5);
		
		// first, out-of-range numbers
		if (year < currentYear || (year != currentYear && year > (currentYear + 1)) || 
			day < 0 || day > 28 || month < 0 || month > 12) {
			System.out.println(dateErrorMessage);
			return inputDate();
		}
		// second, if current year, ensure month & day are not the past
		if (year == currentYear && (month < currentMonth || (month == currentMonth && day < currentDay))) {
			System.out.println(dateErrorMessage);
			return inputDate();
		}
		// third, if next year, ensure month does not surpass the 1 year range
		if (year == (currentYear + 1) && month > currentMonth) {
			System.out.println(dateErrorMessage);
			return inputDate();
		}
		return input;
	}
	
	private String inputTime() {
		// if wrong inputs were typed
		final String timeErrorMessage = "Incorrect input. Ensure the time is within range and that it is " +
										"entered in this format:\nHH:MM A/PM (Examples: 10:30 AM, 04:00 PM)";
		int hour, minutes;
		boolean isAM = true;
		
		String time = sc.nextLine();
		String temp2 = time.replaceAll("[^0-9]", "");
		
		// check if user typed reset
		if (time.equalsIgnoreCase("reset")) return "reset";
		
		// check length
		if (time.length() != 8 || temp2.length() != 4) {
			System.out.println(timeErrorMessage);
			return inputTime();
		}
		
		// check AM/PM
		String temp = time.substring(6);
		if (temp.equalsIgnoreCase("AM")) 
			isAM = true;
		else if (temp.equalsIgnoreCase("PM"))
			isAM = false;
		else {
			System.out.println(timeErrorMessage);
			return inputTime();
		}
		
		// check for incorrect numbers
		String temp3 = temp2.substring(0, 2); hour = Integer.parseInt(temp3);
		String temp4 = temp2.substring(2); minutes = Integer.parseInt(temp4);
		
		if (hour < 1 || hour > 12 || minutes < 0 || minutes > 59 ||
			(hour < 7 && isAM) || (hour > 6 && !isAM && hour != 12) || (hour == 12 && isAM)) {
			System.out.println(timeErrorMessage);
			return inputTime();
		}
		
		return time;
	}
	
	// obtains and returns appointment info (unused by some services, gets overridden in those that don't)
	protected String inputApptInfo() {
		// input address
		System.out.println("Please enter your home address.");
		String address = sc.nextLine();
		// check if user typed reset
		if (address.equalsIgnoreCase("reset")) return "reset";
		
		// beyond this point, I wrote everything and then realized I forgot strings were immutable, 
		// so I had to edit it later and create new methods to handle it. It gets a bit messy.
		// It's why inputTime()'s temp strings starts with temp2 instead of temp1.
		// On the bright side, it opened an opportunity for me to make a recursive method
		// after note: just (re-)learned that it's the string you assign to a reference variable that's unchangeable,
		// not the dang reference variable itself. I did a lot of extra work because of that mixup. At least its 
		// (I think??) more efficient & clean now in some areas.
		
		// input date
		System.out.println("\nPlease enter a date you would like the repairman to come. The date must " + 
							"be within a year from today and the day must not be higher than the " +
							"28th. Enter it in this format:\nMM/DD/YEAR (Examples: 12/04/2020, 05/20/2021");
		String apptDate = inputDate();
		if (apptDate.equalsIgnoreCase("reset")) return "reset";
		
		// input time
		System.out.println("\nPlease enter the time that works best for you. The time must be after " +
							"7:00 AM and before 6 PM Enter it in this format:\nHH:MM A/PM (Examples: " +
							"10:30 AM, 04:00 PM)");
		String time = inputTime();
		if (time.equalsIgnoreCase("reset")) return "reset";
		
		String apptInfo = "\nAddress: " + address + "\nAppointment Date: " + apptDate + "\nAppointment Time: " + time;
		return apptInfo;
	}
	
	// returns total cost of service
	protected int getTotalCost() {
		int total = TRAVEL_COST + SERVICE_COST;
		if (hasExtraCosts) total += EXTRA_COSTS;
		for (int i = 0; i < currentIndex; i++)
			if (!isPartUnknown[i])	// skip unknown costs
				total += PART_COST;
		return total;
	}
	
	// prints information pertaining to pricing
	protected String getPartInfo() {
		String line1 = "The service you have selected is " + serviceType + ".\n\n";
		String line2 = ""; String line3 = ""; String line4 = ""; 
		String line5 = ""; String line6 = "";
		if (!holdOffAppointment) {
			line2 = "Pricing:\n";
			for (int i = 0; i < currentIndex; i++) {
				line2 += "\n" + partList[i] + " ($";
				
				if (isPartUnknown[i]) line2 += "???";
				else line2 += PART_COST;
				
				line2 += ")";
			}
			
			line3 += "\nService Cost ($" + SERVICE_COST + ")\nTravel Cost ($" + TRAVEL_COST + ")\n";
			
			if (hasExtraCosts) {
				line4 += "Extra Costs ($" + EXTRA_COSTS + ")\n";
			}
			
			line5 += "Total Cost: $" + getTotalCost();
			if (unknownFlag) line5 += " + ?";
			line5 += "\n\n";
		}
		else {
			line6 += "Information:\n\n" + partList[0] + "\n\nTotal Cost: Currently Unknown\n\n";
			// services that will require further contact will store
			// all information in index 0.
		}
		String report = line1 + line2 + line3 + line4 + line5 + line6;
		return report;
	}
}