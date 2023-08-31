package poisepms; 
import java.sql.*;
import java.text.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.Date;

/** 
 * This project is for a project management system for fictional construction company "Poised".
 * This will interact with a sql database to take in new information, change details and edit data. 
 * 
 * @author Toufeeq Toffar 
 * @version 1.3 
 */

public class PoisePMS {
	/*
	 * Will be used in the findProject method later on.
	 * This variable will be set as 1 if the project is found and as 0 if the project is not found.
	 * This will be used in the updateProject method to avoid runtime error - if the project is not found the entire method should not work.
	 */
	static int found;
	
	public static void main(String[] args) throws ParseException{
		/**
	     * Connects to the poisePMS database 
	     *
	     * @param statement the statement
	     * @throws SQLException if there is an issue with the SQL query.
	     */
		try {
			// Creates a direct line to the poisePMS database for running our queries
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisepms?useSSL=false", "otheruser", "swordfish");
            //Creates a direct line from the database for queries
            Statement statement = connection.createStatement();
            menu(statement);            
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
	}
	/**
     * Method that reads the whole project table in the database and prints each record. 
     *
     * @param projectResults 	A temporary statement for use within the printAllProjects method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void printAllProjects(Statement statement) throws SQLException{
        ResultSet projectResults = statement.executeQuery("SELECT * FROM project");
        System.out.println("\nAll projects: ");
        //Controlled the lengths of fields like this to ensure equal and neat spacing
		System.out.format("%17s%15s%16s%19s%13s%12s%13s%12s%12s%15s%15s%15s%15s%n", "Project Number | ", "Project Name | ", "Building Type | ", "Building Address | ", "ERF Number | ", "Total Fee | ", "Total Paid | ", "Deadline | ", "Finalized | ", "Engineer | ", "Architect | ", "Manager | ", "Customer");																																			
        while (projectResults.next()) 
 		{
        	System.out.format("%16s%15s%16s%19s%13s%12s%13s%10s%12s%15s%15s%15s%15s%n", 
	            projectResults.getInt("projectNumber")  +" |", 
	        	projectResults.getString("projectName")  +" |", 
	        	projectResults.getString("buildingType")  +" |", 
	        	projectResults.getString("buildingAddress")  +" |", 
	        	projectResults.getInt("erfNumber")  +" |", 
	        	projectResults.getFloat("totalFee")  +" |", 
	        	projectResults.getFloat("totalPaid")  +" |", 
	        	projectResults.getDate("deadline")  +" |", 
	        	projectResults.getString("finalized")  +" |", 
	        	projectResults.getString("engineerName")  +" |", 
	        	projectResults.getString("architectName")  +" |", 
	        	projectResults.getString("managerName")  +" |",
	        	projectResults.getString("customerName"));     
        }
    }
	/**
     * Method that reads the whole engineer table in the database and prints each record. 
     *
     * @param engineerResults 	A temporary statement for use within the printEngineerTable method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void printEngineerTable(Statement statement) throws SQLException{
        ResultSet engineerResults = statement.executeQuery("SELECT * FROM engineer");
		System.out.println("\nAll engineers: ");
		//Prints out a neat heading
		System.out.format("%16s%15s%12s%25s%15s%n", "Project Number | ", "Engineer Name | ", "Cell Number | ", "Email address | ", "Work Address | ");																																			
		//Prints out a rows of the tables and leaves the loop when all rows are printed 
		while (engineerResults.next()) {
			System.out.format("%16s%16s%14s%25s%15s%n",
				engineerResults.getInt("projectNumber")  +" |",
				engineerResults.getString("engineerName")  +" |",
				engineerResults.getString("cellNumber")  +" |",
				engineerResults.getString("emailAddress")  +" |",
				engineerResults.getString("workAddress")  +" |");  
		} 		
    }
	/**
     * Method that reads the whole architect table in the database and prints each record. 
     *
     * @param architectResults 	A temporary statement for use within the printArchitectTable method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void printArchitectTable(Statement statement) throws SQLException{
        ResultSet architectResults = statement.executeQuery("SELECT * FROM architect");
		System.out.println("\nAll architects: ");
		System.out.format("%16s%15s%12s%25s%15s%n", "Project Number | ", "Architect Name | ", "Cell Number | ", "Email address | ", "Work Address | ");																																			
		while (architectResults.next()) {
			System.out.format("%16s%17s%14s%25s%15s%n",
				architectResults.getInt("projectNumber")  +" |",
				architectResults.getString("architectName")  +" |",
				architectResults.getString("cellNumber")  +" |",
				architectResults.getString("emailAddress")  +" |",
				architectResults.getString("workAddress")  +" |");  
		}
	}
	/**
     * Method that reads the whole manager table in the database and prints each record. 
     *
     * @param managerResults 	A temporary statement for use within the printManagerTable method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void printManagerTable(Statement statement) throws SQLException{
        ResultSet managerResults = statement.executeQuery("SELECT * FROM manager");
		System.out.println("\nAll project managers: ");
		System.out.format("%16s%16s%12s%25s%15s%n", "Project Number | ", "Manager Name | ", "Cell Number | ", "Email address | ", "Work Address | ");																																			
		while (managerResults.next()) {
			System.out.format("%16s%16s%14s%25s%15s%n",
				managerResults.getInt("projectNumber")  +" |",
				managerResults.getString("managerName")  +" |",
				managerResults.getString("cellNumber")  +" |",
				managerResults.getString("emailAddress")  +" |",
				managerResults.getString("workAddress")  +" |");  
		}
	} 
	/**
     * Method that reads the whole customer table in the database and prints each record. 
     *
     * @param customerResults 	A temporary statement for use within the printCustomerTable method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void printCustomerTable(Statement statement) throws SQLException{
        ResultSet customerResults = statement.executeQuery("SELECT * FROM customer");
		System.out.println("\nAll customers: ");
		System.out.format("%16s%16s%12s%25s%15s%n", "Project Number | ", "Customer Name | ", "Cell Number | ", "Email address | ", "Work Address | ");																																			
		while (customerResults.next()) {
			System.out.format("%16s%16s%14s%25s%15s%n",
				customerResults.getInt("projectNumber")  +" |",
				customerResults.getString("customerName")  +" |",
				customerResults.getString("cellNumber")  +" |",
				customerResults.getString("emailAddress")  +" |",
				customerResults.getString("workAddress")  +" |");  
		}
	} 
	/**
     * Method that takes in a number from the user as input for which record to delete from the project table.
     * 
     * @param projectDel 		A variable for the project number for use in the SQL query to delete the record.
     * @param projectToDelete 	A temporary statement for use within the deleteProject method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void deleteProject(Statement statement, int projectDel) throws SQLException{
		//Executes the update in the database and then stores it as 0 if unsuccessful and 1 if successful
		int rowsAffected = statement.executeUpdate("DELETE FROM project WHERE projectNumber = " + projectDel );	    
		//Executes the query and pushes the entries from user to the data table in the database
		ResultSet projectToDelete = statement.executeQuery("SELECT * FROM project");
		//If unsuccessful update then error message will print
		if(rowsAffected == 0) {
			System.out.println("\tProject not found.\n");
	  	}
		//if successful then the new table will print 
		else {
			System.out.println("Query complete, " + rowsAffected + " rows removed.\n");
			//use method to print the table to ensure streamlined code
			printAllProjects(statement);
		}
	}
	/**
	 * Method that takes in multiple values from the user as input for each row and inserts it into the project table.
     * 
     * @param insertedNewProj 	A temporary statement for use within the insertNewProject method.
     * @param projectNumber		A variable that stores the new project number value 
     * @param projectName 		A variable that stores the new project number value 
     * @param buildingType 		A variable that stores the new building type value 
     * @param buildingAddress 	A variable that stores the new building address value 
     * @param erfNumber 		A variable that stores the new erf number value 
     * @param totalFee 			A variable that stores the new total fee value 
     * @param totalPaid 		A variable that stores the new total paid value 
     * @param deadline 			A variable that stores the new deadline value 
     * @param finalized 		A variable that stores the new finalized value 
     * @param engineerName		A variable that stores the new engineer name value 	
     * @param architectName 	A variable that stores the new architect name value 
     * @param managerName 		A variable that stores the new manager name value 
     * @param customerName 		A variable that stores the new customer name value 
     * 
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void insertNewProject(Statement statement, int projectNumber, String projectName, String buildingType, String buildingAddress, int erfNumber, float totalFee, float totalPaid, String deadline, String finalized, String engineerName, String architectName, String managerName, String customerName) throws SQLException{
		int rowsAffected = statement.executeUpdate("INSERT IGNORE INTO project VALUES (" + projectNumber + ", '" + projectName + "', '" + buildingType + "', '" 
													+ buildingAddress + "', " + erfNumber + ", " + totalFee + ", " + totalPaid + ", '" + deadline + "', '" + finalized + "', '" 
													+ engineerName + "', '" + architectName + "', '" + managerName + "', '" + customerName + "')");
		ResultSet insertedNewProj = statement.executeQuery("SELECT * FROM project");
		if(rowsAffected == 0) {
			System.out.println("\tInsert unsuccessful - Project already exists!\n");
      	}
		else {
			System.out.println("Query complete, " + rowsAffected + " rows updated.\n");
			printAllProjects(statement);
		}
	}
	/**
     * Takes in a multiple inputs to be able to update a record in the project table.
     *
     * @param statement 		A temporary statement for use within the updateProject method.
     * @param projectNumber		A variable that stores the project number to determine which project will be updated.
     * @param whichFieldIn	 	A variable that takes input from the user to control the switch in the method used to update the project.
     * @param fieldUpdate 		A variable that takes input from the user to insert into the SQL query. 
     * 
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void updateProject(Statement statement, int projectNumber) throws SQLException {//, String buildingType, String buildingAddress, int erfNumber, float totalFee, float totalPaid, String deadline, String finalized, String engineerName, String architectName, String managerName, String customerName) throws SQLException{
		int rowsAffected;
		//first checks if project number entered exists - if yes will allow update if not will exit 
		findProject(statement, Integer.toString(projectNumber));
		switch(found) {
			case 0:
				break;
			case 1: 
				System.out.println("Which field would you like to update? : \n1 - Project Name\n2 - Building Type\n3 - Building Address\n4 - ERF Number\n5 - Total Fee\n6 - Total Paid\n7 - Deadline\n8 - Finalized\n9 - Engineer\n10 - Architect\n11 - Manager\n12 - Customer");
				Scanner whichFieldIn = new Scanner(System.in);
				String whichField = whichFieldIn.nextLine();				
				//Scanner ran from here as variable is dependent on which case is chosen from the menu
				Scanner fieldUpdate;
				String fieldUpdateIn;	
				switch(whichField) {
				case  "1"://Project Name
					System.out.println("Enter the updated project name: ");
					fieldUpdate = new Scanner(System.in);
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET projectName = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");			
					break;
				case  "2"://Building Type
					System.out.println("Enter the updated type of building: ");
					fieldUpdate = new Scanner(System.in); 
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET buildingType = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");	
					break;
				case  "3"://Building Address
					System.out.println("Enter the updated building address: ");
					fieldUpdate = new Scanner(System.in); 
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET buildingAddress = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case  "4"://ERF Number
					System.out.println("Enter the updated ERF number: ");
					fieldUpdate = new Scanner(System.in); 
					//loop to ensure a new input is taken until the input matches the desired input type to avoid type mismatch errors
					while(!fieldUpdate.hasNextInt()) {
						System.out.println("That's not a number! Please only enter a number: ");
						fieldUpdate = new Scanner(System.in);
					}
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET erfNumber = " + fieldUpdateIn + " WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case  "5"://Total Fee
					System.out.println("Enter the updated total fee of the project: R00,00");
					fieldUpdate = new Scanner(System.in); 
					while(!fieldUpdate.hasNextFloat()) {
						System.out.println("That's not a number or you are using the wrong format! Please try again: ");
						fieldUpdate = new Scanner(System.in);
					}
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET totalFee = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case  "6"://Total Paid
					System.out.println("Enter the updated total fee of the project paid so far: R00,00");
					fieldUpdate = new Scanner(System.in); 
					while(!fieldUpdate.hasNextFloat()) {
						System.out.println("That's not a number or you are using the wrong format! Please try again: ");
						fieldUpdate = new Scanner(System.in);
					}
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET totalPaid = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case  "7"://Deadline
					System.out.println("Enter the updated project deadline: YYYY-MM-DD");
					fieldUpdate = new Scanner(System.in); 
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET deadline = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case  "8"://Finalized
					System.out.println("Enter if the project is now finalized: If no type '0' and if yes insert date YYYY-MM-DD ");
					fieldUpdate = new Scanner(System.in); 
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET finalized = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case  "9"://Engineer
					System.out.println("Enter the updated engineer name: ");
					fieldUpdate = new Scanner(System.in);; 
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET engineerName = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case "10"://Architect
					System.out.println("Enter the updated architect name: ");
					fieldUpdate = new Scanner(System.in);; 
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET architectName = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case "11"://Manager
					System.out.println("Enter the updated manager name: ");
					fieldUpdate = new Scanner(System.in);; 
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET managerName = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				case "12"://Customer
					System.out.println("Enter the updated customer name: ");
					fieldUpdate = new Scanner(System.in);; 
					fieldUpdateIn = fieldUpdate.next();
					rowsAffected = statement.executeUpdate("UPDATE project SET customerName = '" + fieldUpdateIn + "' WHERE projectNumber = " + projectNumber);
					System.out.println("Query complete, " + rowsAffected + " rows updated.");
					break;
				default:
					break;
				}
		}
	}
	/** 
     * Method that takes in a number from the user as input for which record to finalize in the project table.
     * 
     * @param projToFinalize	A variable for the project number for use in the SQL query to update the record.
     * @param finalizedProj 	A temporary statement for use within the finalizeProject method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void finalizeProject(Statement statement, int projToFinalize) throws SQLException{
		int rowsAffected = statement.executeUpdate("UPDATE project SET finalized = '" + java.time.LocalDate.now() + "' WHERE projectNumber = " + projToFinalize);
		ResultSet finalizedProj = statement.executeQuery("SELECT * FROM project");
		if(rowsAffected == 0) {
			System.out.println("\tUpdate unsuccessful - Project already finalized or Project Number does not exist.\n");
      	}
		else {
			System.out.println("Query complete, " + rowsAffected + " rows updated.\n");
			printAllProjects(statement);			
		}
	}
	/**
     * Method that finds all the projects where finalized column is false or 0.
     * 
     * @param findUncompletedProj 	A temporary statement for use within the findUncompletedProjects method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void findUncompletedProjects(Statement statement) throws SQLException{	
		ResultSet findUncompletedProj = statement.executeQuery("SELECT * FROM project WHERE finalized = '0'");
		if(!findUncompletedProj.next()) {
      		System.out.println("\tNo uncompleted projects found.\n");
      	}
		else {
			System.out.format("%17s%15s%16s%19s%13s%12s%13s%12s%12s%15s%15s%15s%15s%n", "Project Number | ", "Project Name | ", "Building Type | ", "Building Address | ", "ERF Number | ", "Total Fee | ", "Total Paid | ", "Deadline | ", "Finalized | ", "Engineer | ", "Architect | ", "Manager | ", "Customer");																																			
			do {
				System.out.format("%16s%15s%16s%19s%13s%12s%13s%10s%12s%15s%15s%15s%15s%n", 
						findUncompletedProj.getInt("projectNumber")  +" |", 
						findUncompletedProj.getString("projectName")  +" |", 
						findUncompletedProj.getString("buildingType")  +" |", 
						findUncompletedProj.getString("buildingAddress")  +" |", 
						findUncompletedProj.getInt("erfNumber")  +" |", 
						findUncompletedProj.getFloat("totalFee")  +" |", 
						findUncompletedProj.getFloat("totalPaid")  +" |", 
						findUncompletedProj.getDate("deadline")  +" |", 
						findUncompletedProj.getString("finalized")  +" |", 
						findUncompletedProj.getString("engineerName")  +" |", 
						findUncompletedProj.getString("architectName")  +" |", 
						findUncompletedProj.getString("managerName")  +" |",
						findUncompletedProj.getString("customerName")); 
			}while(findUncompletedProj.next());
		}		
	}
	/**
     * Method that finds all the projects where deadline is past the current date. 
     * 
     * @param findOverdueProj 	A temporary statement for use within the findOverdueProject method.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void findOverdueProject(Statement statement) throws SQLException{		
		ResultSet findOverdueProj = statement.executeQuery("SELECT * FROM project WHERE deadline < '" + java.time.LocalDate.now() + "' AND finalized = '0'");
		if(!findOverdueProj.next()) {
      		System.out.println("\tNo projects found past their due date.\n");
      	}
		else {
			System.out.format("%17s%15s%16s%19s%13s%12s%13s%12s%12s%15s%15s%15s%15s%n", "Project Number | ", "Project Name | ", "Building Type | ", "Building Address | ", "ERF Number | ", "Total Fee | ", "Total Paid | ", "Deadline | ", "Finalized | ", "Engineer | ", "Architect | ", "Manager | ", "Customer");																																			
			do {
				System.out.format("%16s%15s%16s%19s%13s%12s%13s%10s%12s%15s%15s%15s%15s%n", 
						findOverdueProj.getInt("projectNumber")  +" |", 
						findOverdueProj.getString("projectName")  +" |", 
						findOverdueProj.getString("buildingType")  +" |", 
						findOverdueProj.getString("buildingAddress")  +" |", 
						findOverdueProj.getInt("erfNumber")  +" |", 
						findOverdueProj.getFloat("totalFee")  +" |", 
						findOverdueProj.getFloat("totalPaid")  +" |", 
						findOverdueProj.getDate("deadline")  +" |", 
						findOverdueProj.getString("finalized")  +" |", 
						findOverdueProj.getString("engineerName")  +" |", 
						findOverdueProj.getString("architectName")  +" |", 
						findOverdueProj.getString("managerName")  +" |",
						findOverdueProj.getString("customerName")); 
			}while(findOverdueProj.next());
		}		
	}
	/**
     * Method that takes in a user input and finds a project using either the number or the name of the project. 
     * 
     * @param findProject 	A temporary statement for use within the findProject method.
     * @param inputFind 	A variable that takes in the project number or the project name and is used in the SQL query.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void findProject(Statement statement, String inputFind) throws SQLException{
		ResultSet findProject = statement.executeQuery("SELECT * FROM project WHERE projectName = '" + inputFind + "' OR projectNumber = '" + inputFind + "'");
		//If statement used as there will only ever be one result as the primary key project number can never be repeated
		if (findProject.next()) 
		{	
			//found will be set as 1 - this is for use in the updateProject method.
			found=1;
			System.out.format("%17s%15s%16s%19s%13s%12s%13s%12s%12s%15s%15s%15s%15s%n", "Project Number | ", "Project Name | ", "Building Type | ", "Building Address | ", "ERF Number | ", "Total Fee | ", "Total Paid | ", "Deadline | ", "Finalized | ", "Engineer | ", "Architect | ", "Manager | ", "Customer");																																			
			System.out.format("%16s%15s%16s%19s%13s%12s%13s%10s%12s%15s%15s%15s%15s%n", 
	        		findProject.getInt("projectNumber")  +" |", 
	        		findProject.getString("projectName")  +" |", 
	        		findProject.getString("buildingType")  +" |", 
	        		findProject.getString("buildingAddress")  +" |", 
	        		findProject.getInt("erfNumber")  +" |", 
	        		findProject.getFloat("totalFee")  +" |", 
	        		findProject.getFloat("totalPaid")  +" |", 
	        		findProject.getDate("deadline")  +" |", 
	        		findProject.getString("finalized")  +" |", 
	        		findProject.getString("engineerName")  +" |", 
	        		findProject.getString("architectName")  +" |", 
	        		findProject.getString("managerName")  +" |",
	        		findProject.getString("customerName")); 
		}
		else {
      		System.out.println("\tProject not found!\n");
      	    //found will be set as 0 - this is for use in the updateProject method.
      		found = 0;
      	}
	}
	/**
     * Method that takes in a user input and controls the switch statements containing the methods running the entire program. 
     * 
     * @param statement 	A temporary statement for use within the findProject method.
     * @param menuOptEntry 	A variable that takes in the user input as a selection choice.
     * @throws SQLException if there is an issue with the SQL query.
     */
	public static void menu(Statement statement) throws SQLException {
		//While loop to ensure that the programs switch statement runs infinite amount of times 
		while (true) {
			//Menu options which control the case switch options
            System.out.println("\nPlease select an option from the menu: \n"
					+ "1 - Print Project table \n"
					+ "2 - Print Persons tables \n"
					+ "3 - Insert new project\n"
					+ "4 - Update existing project \n"
					+ "5 - Delete existing project \n"
					+ "6 - Finalise existing project \n"
					+ "7 - Find uncompleted projects \n"
					+ "8 - Find overdue projects\n"
					+ "9 - Find a project \n"
					+ "0 - Exit");
            Scanner menuOptEntry = new Scanner(System.in);
			String optSelect = menuOptEntry.nextLine();
			//If statement that will end the program if at any point the "0" is selected 
				if (optSelect.equals("0")) {
					System.out.println("Program exited!");
			        break;
			    }
				//all methods invoked here 
				switch(optSelect) {
					case "1":
						printAllProjects(statement);
						break;
					case "2": 
						//print all person tables
						printEngineerTable(statement);
						printArchitectTable(statement);
						printManagerTable(statement);
						printCustomerTable(statement);
						break;
					case "3":
						System.out.println("Enter the new project number: ");
							Scanner numIn = new Scanner(System.in);
							//loop to ensure a new input is taken until the input matches the desired number input type to avoid type mismatch errors
							while(!numIn.hasNextInt()) {
								System.out.println("That's not a number! Please only enter a number: ");
								numIn = new Scanner(System.in);
							}
							int numInput = numIn.nextInt();							
						System.out.println("Enter the new project name: ");
							Scanner nameIn = new Scanner(System.in);
							String nameInput = nameIn.nextLine();
						System.out.println("Enter the type of building: ");
							Scanner buildTypeIn = new Scanner(System.in);
							String buildTypeInput = buildTypeIn.nextLine();
						System.out.println("Enter the building address: ");
							Scanner buildAddressIn = new Scanner(System.in);
							String buildAddressInput = buildAddressIn.nextLine();
						System.out.println("Enter the ERF number: ");
							Scanner erfIn = new Scanner(System.in);
							while(!erfIn.hasNextInt()) {
								System.out.println("That's not a number! Please only enter a number: ");
								erfIn = new Scanner(System.in);
							}
							int erfInput = erfIn.nextInt();	
						System.out.println("Enter the total fee of the project: R00,0");
							Scanner totFeeIn = new Scanner(System.in);
							while(!totFeeIn.hasNextFloat()) {
								System.out.println("That's not a number! Please only enter a number: ");
								totFeeIn = new Scanner(System.in);
							}
							float totFeeInput = totFeeIn.nextFloat();	
						System.out.println("Enter the total amount paid so far: R00,0");
							Scanner totPaidIn = new Scanner(System.in);
							while(!totPaidIn.hasNextFloat()) {
								System.out.println("That's not a number! Please only enter a number: ");
								totPaidIn = new Scanner(System.in);
							}							
							float totPaidInput = totPaidIn.nextFloat();	
						System.out.println("Enter the project deadline: YYYY-MM-DD");		
							Scanner dealineIn = new Scanner(System.in);
							String dealineInput = dealineIn.nextLine();						
						System.out.println("Enter if the project is finalized - 0 if no, YYYY-MM-DD if yes: ");
							Scanner finIn = new Scanner(System.in);
							String finInput = finIn.nextLine();
						System.out.println("Enter the engineer name: ");
							Scanner engNameIn = new Scanner(System.in);
							String engNameInput = engNameIn.nextLine();
						System.out.println("Enter the architect name: ");
							Scanner archiNameIn = new Scanner(System.in);
							String archiNameInput = archiNameIn.nextLine();
						System.out.println("Enter the project manager name: ");		
							Scanner mngrNameIn = new Scanner(System.in);
							String mngrNameInput = mngrNameIn.nextLine();		
						System.out.println("Enter the customer name: ");		
							Scanner custNameIn = new Scanner(System.in);
							String custNameInput = custNameIn.nextLine();
						insertNewProject(statement, numInput, nameInput, buildTypeInput, buildAddressInput, erfInput, totFeeInput, totPaidInput, dealineInput, finInput, engNameInput, archiNameInput, mngrNameInput, custNameInput );
						break;
					case "4":
						System.out.println("Enter the  number of the project you would like to update: ");
						Scanner projectNumberIn = new Scanner(System.in);
						int projectNumber;
						while(!projectNumberIn.hasNextInt()) {
							System.out.println("That's not a number! Please only enter a number: ");
							projectNumberIn = new Scanner(System.in);
						}
						projectNumber = projectNumberIn.nextInt();								
						updateProject(statement, projectNumber);
						printAllProjects(statement);						
						break;
					case "5":
						System.out.println("Enter the project number you would like to delete: ");
						Scanner deleteInput = new Scanner(System.in);
					  	deleteProject(statement, deleteInput.nextInt());
						break;
					case "6":
						System.out.println("Enter the project number you would like to finalize: ");
						Scanner finalizeInput = new Scanner(System.in);
						finalizeProject(statement, finalizeInput.nextInt());
						break;
					case "7":
						System.out.println("All projects that still need to be completed:");
						findUncompletedProjects(statement);
						break;						
					case "8":
						System.out.println("All projects that are past the due date:");
						findOverdueProject(statement);
						break;
					case "9":
						System.out.println("Please enter the name of the project you want to view: ");
						Scanner userInput = new  Scanner(System.in);
						findProject(statement, userInput.nextLine());
						break;
					default:
						//If none of the options are chosen program won't crash but rather exit immediately and prints out message
						System.out.println("That is not a valid option!");
				}
			}            
    }

}
