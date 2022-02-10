package services;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EngineRepair extends DefaultService implements ServiceInterface {
	private Scanner sc = new Scanner(System.in);
	
	public EngineRepair() {
		super("Engine Repair", false);
	}
	
	public boolean inputPartInfo() {
		System.out.println(	"What part(s) of the engine need to be repaired? For multiple parts in need of " +
							"repair, type in the first part, press Enter, then enter Yes to the " +
							"following question. (10 parts maximum)");
		
		// has a unique unknown response, 
		// does not provide only one item of a varied type,
		// and does not get multiples of a single part.
		boolean restart = inputParts(true, false, false);
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
			File myObj = new File("CarEngineRepairServiceDetails.txt");
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
			FileWriter myWriter = new FileWriter("CarEngineRepairServiceDetails.txt");
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