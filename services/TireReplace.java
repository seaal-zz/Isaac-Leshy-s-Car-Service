package services;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TireReplace extends DefaultService implements ServiceInterface {
	
	private Scanner sc = new Scanner(System.in);

	public TireReplace() {
		super("Tire Replacement", false);
	}
	
	public boolean inputPartInfo() {
		System.out.println("Enter the type of tire you would like installed into your car. If you are unsure " +
							"what type of tire you need, type Unknown");
							
		// has a unique unknown response, 
		// does not provide only one item of a varied type,
		// and only gets multiples of a single part.
		boolean restart = inputParts(true, false, true);
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
			File myObj = new File("CarTireReplacementServiceDetails.txt");
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
			FileWriter myWriter = new FileWriter("CarTireReplacementServiceDetails.txt");
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