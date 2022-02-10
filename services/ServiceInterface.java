package services;

// all services implement this interface
public interface ServiceInterface {
	
	boolean inputPartInfo();	// helps get required part info for service
	boolean collectAllInfo();	// returns all required information for later (for storage in a .txt file)
}