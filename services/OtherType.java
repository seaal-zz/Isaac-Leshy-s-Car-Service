package services;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OtherType extends DefaultService implements ServiceInterface {
	
	private Scanner sc = new Scanner(System.in);
	
	public OtherType() {
		super("Other Type of Service", true);
	}
	
	// overrides DefaultService's method
	protected String inputApptInfo() {
		return "";
	}
	
	public boolean inputPartInfo() {
		System.out.println("Due to the ambiguity of this service, you will not be able to schedule an " +
							"appointment until we get in contact with you to confirm the details of your " + 
							"requests. What kinds of services do you require for your car? If you have " +
							"multiple requests, place them on the same line.");
							
		// does not have a unique unknown response, 
		// provides only one item of a varied type,
		// and does not get multiples of a single part.
		boolean restart = inputParts(false, true, false);
		return restart;
	}
	
	public boolean collectAllInfo() {
		String input;
		String contactInfo = inputContactInfo();
		if (contactInfo.equals("reset"))
			return collectAllInfo();
		String apptInfo = inputApptInfo();
		if (apptInfo.equals("reset"))
			return collectAllInfo();
		String partsInfo = getPartInfo();
		
		String allInfo = partsInfo + contactInfo + apptInfo;
		System.out.println("\nIs the following information correct?\n\n" + allInfo);
		System.out.println("\nEnter Yes to finalize your request. If only contact and appointment information " +
							"is incorrect, enter No. If information about your request is incorrect, enter Back");
		while (true) {
			input = sc.nextLine();
			if (input.equalsIgnoreCase("no"))
				return collectAllInfo();
			else if (input.equalsIgnoreCase("back"))
				return true;
			else if (input.equalsIgnoreCase("yes"))
				break;
			else
				System.out.println("Please enter Yes, No, or Back.");
		}
		
		// write all info into a .txt file (this was something I needed help from the internet to do)
		try {
			File myObj = new File("CarMiscServiceDetails.txt");
			if (myObj.createNewFile()) {
				System.out.println("Receipt created: " + myObj.getName());
			} 
			else {
				System.out.println("Receipt already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		try {
			FileWriter myWriter = new FileWriter("CarMiscServiceDetails.txt");
			myWriter.write(allInfo);
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		
		// insert hypothetical method here that would then send allInfo over the internet to the company
		return false;
	}
}