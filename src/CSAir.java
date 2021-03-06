import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

import org.json.simple.*;

/**
 * Ammar Khan Bachelors in Computer Science
 * 
 */

import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

import org.json.simple.*;

/**
 * Ammar Khan Bachelors in Computer Science
 * 
 */

public class CSAir {

	static List<City> allCities = new LinkedList<City>();

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {

		populateCitiesList();

	populateFlightList();
	
	readItinerary();
//		while (true) {
//			userInteraction();
//		}
	}

	// creates JSONObject from the raw file
	public static JSONObject getObject() throws FileNotFoundException {
		FileReader reader = new FileReader("csair.json");
		JSONObject csairData = (JSONObject) JSONValue.parse(reader);
		return csairData;

	}

	// creates the JSONArray of metros from JSONObject of csairData
	public static JSONArray getMetros() throws FileNotFoundException {
		JSONArray metros = (JSONArray) getObject().get("metros");
		return metros;
	}

	// //creates the JSONArray of routes from JSONObject of csairData
	public static JSONArray getRoutes() throws FileNotFoundException {

		JSONArray routes = (JSONArray) getObject().get("routes");
		return routes;

	}

	public static void populateCitiesList() throws FileNotFoundException {

		for (int i = 0; i < getMetros().size(); i++) {
			JSONObject metro = (JSONObject) getMetros().get(i);
			String name = (String) metro.get("name");
			String code = ((String) metro.get("code"));
			String country = ((String) metro.get("country"));
			JSONObject coordinates = (JSONObject) metro.get("coordinates");
			String coordinatestoString = coordinates.toString();
			String latitude = coordinatestoString.substring(1, 7);
			String longitude = coordinatestoString.substring(8, 14);

			String continent = ((String) metro.get("continent"));
			int region = ((Long) metro.get("region")).intValue();
			int population = ((Long) metro.get("population")).intValue();
			double timezone = 0;
			if (metro.get("timezone") instanceof Long) {

				timezone = ((Long) metro.get("timezone")).doubleValue();

			} else if (metro.get("timezone") instanceof Double) {

				timezone = ((Double) metro.get("timezone")).doubleValue();
			}

			City city = new City(name, code, country, continent, region,
					population, timezone, latitude, longitude);
			allCities.add(city);

		}

	}

	public static void populateFlightList() throws FileNotFoundException {

		for (int j = 0; j < getRoutes().size(); j++) {

			JSONObject route = (JSONObject) getRoutes().get(j);
			JSONArray myarray = (JSONArray) route.get("ports");

			String sourceCode = (String) myarray.get(0);
			String destinationCode = (String) myarray.get(1);
			Long distance = (Long) route.get("distance");

			City source = getCityFromCode(sourceCode);
			City destination = getCityFromCode(destinationCode);

			Flight flight1 = new Flight(source, destination, distance);
			Flight flight2 = new Flight(destination, source, distance);
			System.out.println();
			source.addFlight(flight1);
			destination.addFlight(flight2);

		}

	}

	public static void printcityList(List<City> allCities) {
		System.out.println("The list is as follows to which CSAir flies to:");
		for (int i = 0; i < allCities.size(); i++) {
			System.out.println("(" + (i + 1) + ")" + allCities.get(i).name 
					+ " ("+allCities.get(i).code+")");
			

		}
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");
		System.out.println();
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");

	}

	public static void userInteraction() throws FileNotFoundException {

		welcomeMessage();
		String input = scanner.nextLine();

		if (input.equals("1")) {
			printcityList(allCities);
		} else if (input.equals("2")) {
			System.out.println("Enter code : ");
			String code = scanner.nextLine().toUpperCase();
			specificCityInformation(code);
		} else if (input.equals("3")) {
			printStatisticalInformationOptions();
			System.out.print("Enter your choice : ");
			String choice = scanner.nextLine();
			System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");
			System.out.println();
			printStatisticalInformation(choice);
		} else if (input.equals("4")) {
			addFlight();
		} else if (input.equals("5")) {
			removeFlight1();
		} else if (input.equals("6")) {
			addNewCity();
		} else if (input.equals("7")) {
			removeCity();
		}
		
	

	}

	public static void longestFlight() {
		Long distance = allCities.get(0).flights.get(0).distance;
		String source = allCities.get(0).flights.get(0).source.name;
		String destination = allCities.get(0).flights.get(0).destination.name;

		for (int i = 0; i < allCities.size(); i++) {

			List<Flight> flights = allCities.get(i).flights;
			for (int j = 0; j < flights.size(); j++) {

				if (allCities.get(i).flights.get(j).distance > distance) {
					distance = allCities.get(i).flights.get(j).distance;
					source = allCities.get(i).flights.get(j).source.name;
					destination = allCities.get(i).flights.get(j).destination.name;
				}
			}

		}
		System.out.println("Longest flight is from " + source + " " + " to "
				+ destination + " " + "having a distance of" + " " + distance);
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");

	}

	public static void shortestFlight() {

		Long distance = allCities.get(0).flights.get(0).distance;
		String source = allCities.get(0).flights.get(0).source.name;
		String destination = allCities.get(0).flights.get(0).destination.name;
		for (int i = 0; i < allCities.size(); i++) {

			List<Flight> flights = allCities.get(i).flights;
			for (int j = 0; j < flights.size(); j++) {

				if (allCities.get(i).flights.get(j).distance < distance) {
					distance = allCities.get(i).flights.get(j).distance;
					source = allCities.get(i).flights.get(j).source.name;
					destination = allCities.get(i).flights.get(j).destination.name;

				}
			}

		}

		System.out.println("Shortest flight is from " + source + " " + " to "
				+ destination + " " + "having a distance of" + " " + distance);
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");

	}

	public static void averageDistanceOfAllFlights() {

		Long distance = (long) 0;
		int count = 0;
		for (int i = 0; i < allCities.size(); i++) {

			List<Flight> flights = allCities.get(i).flights;
			for (int j = 0; j < flights.size(); j++) {

				distance += (allCities.get(i).flights.get(j).distance
						/ flights.size());

			}

		}

		System.out.println("Average Distance of all flights is " + distance);
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");
	}

	public static void biggestCityByPopulation() {
		int population = allCities.get(0).population;
		String city = "";
		for (int i = 0; i < allCities.size(); i++) {

			if (allCities.get(i).population > population) {
				population = allCities.get(i).population;
				city = allCities.get(i).name;

			}

		}

		System.out.println("Biggest city by population is");
		System.out.print(city + " : " + population);
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");

	}

	public static void smallestCityByPopulation() {

		int population = allCities.get(0).population;
		String city = "";
		for (int i = 0; i < allCities.size(); i++) {

			if (allCities.get(i).population < population) {
				population = allCities.get(i).population;
				city = allCities.get(i).name;

			}

		}

		System.out.println("Smallest city by population is");
		System.out.print(city + " : " + population);
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");

	}

	public static void averageSizeByPopulation() {

		int population = 0;
		int count = 0;
		for (int i = 0; i < allCities.size(); i++) {

			population += allCities.get(i).population;
			count++;
		}
		System.out.println("Average size by population of all "
				+ "the cities is " + population / count);
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");

	}

	public static void citiesInContinents() {

		for (int i = 0; i < allCities.size(); i++) {

			if (allCities.get(i).continent.equalsIgnoreCase("Africa")) {
				System.out.println(allCities.get(i).continent + " "
						+ allCities.get(i).name);
			} else if (allCities.get(i).continent.equalsIgnoreCase("Asia")) {
				System.out.println(allCities.get(i).continent + " "
						+ allCities.get(i).name);
			} else if (allCities.get(i).continent.equalsIgnoreCase("Australia")) {
				System.out.println(allCities.get(i).continent + " "
						+ allCities.get(i).name);
			} else if (allCities.get(i).continent.equalsIgnoreCase("Europe")) {
				System.out.println(allCities.get(i).continent + " "
						+ allCities.get(i).name);
			} else if (allCities.get(i).continent
					.equalsIgnoreCase("North America")) {
				System.out.println(allCities.get(i).continent + " "
						+ allCities.get(i).name);
			} else if (allCities.get(i).continent
					.equalsIgnoreCase("South America")) {
				System.out.println(allCities.get(i).continent + " "
						+ allCities.get(i).name);
			}

		}

	}

	public static void welcomeMessage() {
		System.out.println("Welcome to CSAir");
		System.out.println("You can select from the following options :");
		System.out.println("\n 1.Get the list of cities to which CSAir flies");
		System.out.println(" 2.Get information about a specific city "
				+ "in the CSAir route network. ");
		System.out.println(" 3.Get statistical information");
		System.out.println(" 4. Add a flight");
		System.out.println(" 5. Remove a flight");
		System.out.println(" 6. Add a new city ");
		System.out.println(" 7. Remove a city ");

		System.out.println("\nEnter your choice : ");

	}

	public static void specificCityInformation(String code) {

		City city = getCityFromCode(code);
		try {
			System.out.println("Name: " + city.name);
			System.out.println("Code: " + city.code);
			System.out.println("Country: " + city.country);
			System.out.println("Continent: " + city.continent);
			System.out.println("TimeZone: " + city.timezone);
			System.out.println("Longitude and Latitude: " + city.longitude
					+ " " + city.latitude);
			System.out.println("Population: " + city.population);
			System.out.println("Region: " + city.region);

			System.out.println("Non-Stop flights from: " + city.name);
			for (int j = 0; j < city.flights.size(); j++) {
				System.out.println(city.flights.get(j).destination.name
						+ "  -- -- " + city.flights.get(j).distance + " km ");
			}
		} catch (Exception e) {
			System.out.println("Bad Input");
			System.out.println("Please Enter Valid Code");
		}
		System.out.println("");
	}

	public static void printStatisticalInformationOptions() {

		System.out.
		println("   1.The longest single flight in the network");
		System.out.
		println("   2.The shortest single flight in the network ");
		System.out
		.println("   3.The average distance of all the flights in the network");
		System.out
		.println("   4.The biggest city (by population) served by CSAir");
		System.out
		.println("   5.The smallest city (by population) served by CSAir");
		System.out
		.println("   6.The average size (by population) of all the cities");
		System.out
		.println("   7.Continents served by CSAir and which cities are in them ");
		System.out
		.println("   8.Hub cities: the cities that have the most direct connections");

	}

	public static void printStatisticalInformation(String s1) {

		if (s1.equals("1")) {
			longestFlight();
		} else if (s1.equals("2")) {
			shortestFlight();
		} else if (s1.equals("3")) {
			averageDistanceOfAllFlights();
		} else if (s1.equals("4")) {
			biggestCityByPopulation();
		} else if (s1.equals("5")) {
			smallestCityByPopulation();
		} else if (s1.equals("6")) {
			averageSizeByPopulation();
		} else if (s1.equals("7")) {
			citiesInContinents();
		} else if (s1.equals("8")) {

		} else {
			System.out.println("\nBad Input");
			System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");
			System.out.println();
		}

	}

	public static void hubCities() {
		
		
		
		

	}

	public static void addFlight() {
		try{
		System.out.println("Enter source");
		String src = scanner.next().toUpperCase();
		System.out.println("Enter destination");
		String dest = scanner.next().toUpperCase();
		System.out.println("Enter the distance");
		Long distance = scanner.nextLong();
		
		if(src.equalsIgnoreCase(dest)){
			System.out.println("Source and Destination can not be the same");
			addFlight();
		}else if(checkCodeInList(src,dest)){
			System.out.println("CS Air does not flies to these cities");
			System.out.println("Please select (1) from main menu to see the list of cities "
					+ "to which CSAir flies or enter valid Source and Destination Codes");
			addFlight();
		}
		
		City source = getCityFromCode(src);
		City destination = getCityFromCode(dest);
		
		Flight flight1 = new Flight(source, destination, distance);
		Flight flight2 = new Flight(destination, source, distance);
		source.addFlight(flight1);
		destination.addFlight(flight2);
		System.out.println("Done");
		
		}catch (Exception e){
			System.out.println("\nBad Input");
			System.out.println("Please enter valid Source and Destination City Code like (IST)");
			System.out.println("Distance should be a valid value");
			System.out.println("Please select (1) from main menu to see the list of cities "
					+ "and their codes to which CSAir flies");
			System.out.println("");
			
			
		}

	}

	public static City getCityFromCode(String code) {
		for (int i = 0; i < allCities.size(); i++) {
			if (allCities.get(i).code.equals(code)) {
				return allCities.get(i);
			}
		}
		return null;

	}

	public static void removeFlight1() {
try{
		System.out.println("Enter source");
		String src = scanner.next().toUpperCase();
		System.out.println("Enter destination");
		String dest = scanner.next().toUpperCase();
		if(src.equalsIgnoreCase(dest)){
			System.out.println("Source and Destination can not be the same");
			addFlight();
		}else if(!checkCodeInList(src,dest)){
			System.out.println("CS Air does not flies to these cities");
			System.out.println("Please select (1) from main menu to see the list of cities "
					+ "to which CSAir flies or enter valid Source and Destination Codes");
			addFlight();
		}
		
		
		City sourceCity = getCityFromCode(src);
		City destinationCity = getCityFromCode(dest);
		
		
		for (int i = 0; i < sourceCity.flights.size(); i++) {
			City sourceDest = sourceCity.flights.get(i).destination;
			if(dest.equalsIgnoreCase(sourceDest.code)){
				Flight flight1 = sourceCity.flights.get(i);
					flight1.source.removeFlight(flight1);

			}
		}
	
		for (int j = 0; j < destinationCity.flights.size(); j++) {
			City destSource = destinationCity.flights.get(j).destination;
		if(src.equalsIgnoreCase(destSource.code)){
			Flight flight2 = destinationCity.flights.get(j);
			flight2.source.removeFlight(flight2);
		}
		
		
		
		}
}catch (Exception e){
			System.out.println("\nSource or Destination code not valid");
			System.out.println("Please enter three a letter city code like (IST)");
			System.out.println("---- ---- ---- ----");
			removeFlight1();
		}
	

		

	}

	public static void addNewCity() {
		try{
			
		System.out.println("Enter Name: ");
		String name = scanner.nextLine();
		System.out.println("Enter Code: ");
		String code = scanner.nextLine();
		System.out.println("Enter Country: ");
		String country = scanner.nextLine();
		System.out.println("Enter Continet: ");
		String continent = scanner.nextLine();
		System.out.println("Enter Latitude: ");
		String latitude = scanner.nextLine();
		System.out.println("Enter Longitude: ");
		String longitude = scanner.nextLine();
		System.out.println("Enter timezone: ");
		double timezone = scanner.nextDouble();
		System.out.println("Enter Region: ");
		int region = scanner.nextInt();
		System.out.println("Enter Population: ");
		int population = scanner.nextInt();

		City city = new City(name, code, country, continent, region,
				population, timezone, latitude, longitude);

		allCities.add(city);
		System.out.println("City Successfully added ");
		System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");
		}catch (Exception e){
			System.out.println("\nBad Input");
			System.out.println("Please Enter Valid Values");
			System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");
		}

	}

	public static void removeCity() {
try{
		System.out.println("Enter code: ");
		String code = scanner.next().toUpperCase();
		for (int i = 0; i < allCities.size(); i++) {
			
			
			
			if (code.equalsIgnoreCase(allCities.get(i).code)
					&& code.length() == 3) {
				City city = allCities.get(i);
				allCities.remove(city);
				for (int j = 0; j < city.flights.size(); j++) {
					Flight flight = city.flights.get(j);
					flight.source.removeFlight(flight);
					flight.destination.removeFlight(flight);
					
				}
				System.out.println("City successfully removed");
				break;
			}
			}
		}catch (Exception e) {
			System.out.println("Bad Input");
			removeCity();
		}
System.out.println("---- ---- ---- ---- ---- ---- ---- ---- ---- ----");

	}
	
	public static boolean checkCodeInList(String source , String destination){
		
		for (int i = 0; i <allCities.size(); i++) {
			
			if(source.equalsIgnoreCase(allCities.get(i).code) 
					&& destination.equalsIgnoreCase(allCities.get(i).code)){
				return true;
			}else{
				return false;
			}
				
			
		}
		return false;
		
	}
	
	public static void readItinerary(){
		System.out.println("Enter >> ");
		String srcDest = scanner.next();
	
		//Itinerary abc = new Itinerary(itinerary);
		
		
		
	}
}
