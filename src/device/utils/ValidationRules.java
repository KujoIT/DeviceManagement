package device.utils;

import java.util.Arrays;
import java.util.List;

import device.entities.Computer;

public class ValidationRules {
	private static final List<String> VALID_COLORS = Arrays.asList("red", "white", "blue", "gray");
	
	public static boolean isValidId(int id, List<Computer> devices) {
		for (Computer device : devices) { // Class var : list[]
			if (device.getId() == id) { // id nhap vao == id cua doi tuong ddevices
				return false;
			}
		}
		return true; // neu id chua ton tai
	}//ctrl shift F
	
	public static boolean isValidName(String name) {
		return name != null && name.length() >= 5;    
	}
	
	public static boolean isValidRam (byte ram) {
		return ram >= 2 && ram <= 128;
	}
	
	public static boolean isValidCPU (float cpu) {
		return cpu >= 1 && cpu <= 10;
	}
	
	public static boolean isValidHDD (short hdd){
		return hdd >= 128;
	}
	
	public static boolean isValidWeight(float weight) {
		return weight >= 0.5 && weight <= 8;
	}
	
	public static boolean isValidColor (String color) {
		return color != null && VALID_COLORS.contains(color.toLowerCase()); 
	}
	
//	public static boolean isValidNewName(String newName) {
//		return  newName.length() >= 5;    
//	}
//	
//	public static boolean isValidNewColor (String newColor) {
//		return VALID_COLORS.contains(newColor.toLowerCase()); 
//	}
//	
	
}
