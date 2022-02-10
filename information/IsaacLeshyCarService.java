// This is a demonstration of nearly everything I learned throughout CIS-18A.
// Tyler Cox, 9 Feb 2022, CIS-18A

package information;	// package that handles obtaining information
import services.*;		// package that helps store obtained information
import java.util.*;

class IsaacLeshyCarService {
	public static void main(String args[]) {
	// declaring var
		Scanner sc = new Scanner(System.in);
		int choice; boolean restartService = true;
		
	// input & processing
		// the start of the service
		while (restartService) {
			restartService = false;
			System.out.println("Welcome to Isaac & Leshy\'s Mobile Car Repair Services! What type of services do you require?\n");
			System.out.println(	"\t1. Engine Repair\n" + "\t2. Radio Repair\n" +
								"\t3. Paint Job\n" + "\t4. Tire Replacements\n" +
								"\t5. Other Parts Replacements (Doors, Mirrors, Chairs, etc.\n" +
								"\t6. Improvements\n" + "\t7. Refuel\n" + "\t8. Other type of Service\n");
			System.out.println("Type the number of the service you desire, then press Enter: ");
			choice = sc.nextInt();
			switch (choice) {
				case 1:
					EngineRepair eR = new EngineRepair();
					restartService = eR.inputPartInfo();
					if (restartService)
						break;
					restartService = eR.collectAllInfo();
					break;
				case 2:
					RadioRepair rR = new RadioRepair();
					restartService = rR.inputPartInfo();
					if (restartService)
						break;
					restartService = rR.collectAllInfo();
					break;
				case 3:
					PaintJob pJ = new PaintJob();
					restartService = pJ.inputPartInfo();
					if (restartService)
						break;
					restartService = pJ.collectAllInfo();
					break;
				case 4:
					TireReplace tR = new TireReplace();
					restartService = tR.inputPartInfo();
					if (restartService)
						break;
					restartService = tR.collectAllInfo();
					break;
				case 5:
					OtherParts oP = new OtherParts();
					restartService = oP.inputPartInfo();
					if (restartService)
						break;
					restartService = oP.collectAllInfo();
					break;
				case 6:
					Improvements iM = new Improvements();
					restartService = iM.inputPartInfo();
					if (restartService)
						break;
					restartService = iM.collectAllInfo();
					break;
				case 7:
					Refuel rF = new Refuel();
					restartService = rF.inputPartInfo();
					if (restartService)
						break;
					restartService = rF.collectAllInfo();
					break;
				case 8:
					OtherType oT = new OtherType();
					restartService = oT.inputPartInfo();
					if (restartService)
						break;
					restartService = oT.collectAllInfo();
					break;
				default:
					System.out.println("You have input an invalid number. Please enter a number between 1 and 8.");
					restartService = true;
			}
		}
		System.out.println("Thank you for your request! We will email you within a few days to confirm the meeting and " +
							"discuss any other needed information.");
	}
}