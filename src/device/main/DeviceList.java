package device.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.sql.Statement;


import device.entities.Computer;
import device.entities.Laptop;
import device.entities.PC;
import device.utils.ValidationRules;
import test.DatabaseConnection;

public class DeviceList {
	private ArrayList<Computer> devices;
	
	public DeviceList() {
		this.devices = new ArrayList<>();
	}
	
	public static void main(String[] args) throws SQLException {
		DeviceList deviceList = new DeviceList();
		Scanner sc = new Scanner(System.in);
		
		while(true){
			System.out.println("====== Device Management ======");
			System.out.println("1. Create");
			System.out.println("2. Display all");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Search by name");
			System.out.println("6. Save to database");
			System.out.println("7. Quit");
			System.out.println("Please choose function you'd like to do: ");
			int choice = Integer.parseInt(sc.nextLine());
			
			switch(choice) {
				case 1:
					deviceList.add(sc);
					break;
					
				case 2:
					deviceList.display();
					break;
					
				case 3:
					deviceList.update(sc);
					break;
					
				case 4:
					deviceList.delete(sc);
					break;
					
				case 5:
					deviceList.sortByName(sc);
					break;
					
				case 6:
					deviceList.saveDevicesToFile();
					break;
					
				case 7:
					deviceList.exit(sc);
					return;
					
				default:
					System.err.println("Function not found, pls try again!");
			}	
		}	
	}
	
	
	private void add(Scanner sc) throws SQLException {
		int choose = 0;
	    while (choose != 1 && choose != 2) {      // != 1 va !=2 thi vong lap se chay lai, đk đúng = true -> tiep tuc chạy
	    	try {
	    		System.out.println("1. PC    2. Laptop");
	    		System.out.println("Choose device type to add: ");
	    		choose = Integer.parseInt(sc.nextLine());
	    		if (choose !=1 && choose !=2) {
	    			System.err.println("Invalid number!");
	    		}
	        } catch (NumberFormatException e) {
	        	System.err.println("Pls enter the number!");
	        }
	    }
	    
	    	//ID
	    	int id;
	    	while(true) {
	    		try {
	    			System.out.println("ID = ");
	    			id = Integer.parseInt(sc.nextLine());
	    			if (!ValidationRules.isValidId(id, devices)) {
	    				System.err.println("ID existed in this list!");
	    			}else if (!DatabaseConnection.isValidId(id)) {
	    				System.err.println("ID existed in database!");
	    			}else {
	    				break;
	    			}
	    		}catch (NumberFormatException nfe) {
	    			System.err.println("Pls enter the number!");
	    		}
	    	}
	    
			//Name
			String name;
			while(true) {
				System.out.println("Name = ");
				name = sc.nextLine();
				if (!ValidationRules.isValidName(name)) {   // !true = false -> khong thuc hien, !false = true -> thuc hien
					System.err.println("Cannot be empty and must be at least five characters!");
				}else {
					break;
				}
			}
			
		
			//Ram
			byte ram;
			while(true) {
				try {
					System.out.println("Ram = ");
					ram = Byte.parseByte(sc.nextLine());
					if (!ValidationRules.isValidRam(ram)) {
						System.err.println("Must be an integer number and ranges from 2 to 128");
					}else {
						break;
					}
				}catch (NumberFormatException nfe) {
					System.err.println("Must be an integer number and ranges from 2 to 128");
				}
			}
			
			
			//CPU
			float cpu;
			while(true) {
				try {
					System.out.println("CPU = ");
					cpu = Float.parseFloat(sc.nextLine());
					if (!ValidationRules.isValidCPU(cpu)) {
						System.err.println("Must be an number and ranges from 1 to 10");
					}else {
						break;
					}
				}catch (NumberFormatException nfe) {
					System.err.println("Must be an number and ranges from 1 to 10");
				}
			}
			
			//HDD
			short hdd;
			while(true) {
				try {
					System.out.println("HDD = ");
					hdd = Short.parseShort(sc.nextLine());
					if (!ValidationRules.isValidHDD(hdd)) {
						System.err.println("Must be an integer number and ranges from 128");
					}else {
						break;
					}	
				}catch (NumberFormatException nfe) {
					System.err.println("Must be an integer number and ranges from 128");
				}
			}
				
			
			if (choose == 1) {
				devices.add(new PC(id, name, ram, cpu, hdd));
			}
			else if (choose == 2) {
				//Weight
				float weight;
				while(true) {
					try {
						System.out.println("Weight = ");
						weight = Float.parseFloat(sc.nextLine());
						if (!ValidationRules.isValidWeight(weight)) {
							System.err.println("Must be an number and ranges from 0.5 to 8");
						}else {
							break;
						}	
					}catch (NumberFormatException nfe) {
						System.err.println("Must be an number and ranges from 0.5 to 8");
					}
				}
				
				//Color
				String color;
				while(true) {
						System.out.println("Color = ");
						color = sc.nextLine();
						if (!ValidationRules.isValidColor(color)) {
							System.err.println("Cant be empty and is a valid color representation as red, white, blue, gray");
						}else {
							break;
						}
				}
				devices.add(new Laptop(id, name, ram, cpu, hdd, weight, color));
			}
			System.out.println("Add succesfully!");
		}

	private void display() throws SQLException {
		System.out.println("Devices in list: ");
		ArrayList<Computer> displaySort = new ArrayList<>();
		for (Computer display : devices) {
			displaySort.add(display);											// duyet tung thanh phan trong device, sau do add vao displaySort
			displaySort.sort(Comparator.comparing(Computer::getName).reversed()  //sắp xếp order by name . reversed = desc
							.thenComparing(Computer::getId));					// sau đó sắp xếp theo id tăng
		}
		for (Computer display : displaySort) {						
			System.out.println(display);
		}
		System.out.println("");
		System.out.println("Devices in database: ");
		Connection conn = DatabaseConnection.getConnection();
		Statement st = conn.createStatement();
		String sql = "SELECT * FROM device ORDER BY name DESC, id ASC";
		ResultSet result = st.executeQuery(sql);
		
		 while (result.next()) {
			 int id = result.getInt("id");
			 String name = result.getString("name");
			 byte ram =  result.getByte("ram");
			 float cpu = result.getFloat("cpu");
			 short hdd = result.getShort("hdd");
			 float weight = result.getFloat("weight");
			 String color = result.getString("color");
			 String type = result.getString("type");
			 if ("Laptop".equalsIgnoreCase(type)) {   //so sanh 2 chuoi, ignore ki tu hoa-thuong, neu giong nhau -> true -> thuc hien
				 System.out.println(new Laptop(id, name, ram, cpu, hdd, weight, color));
			 }else if("PC".equalsIgnoreCase(type)) {
				 System.out.println(new PC(id, name, ram, cpu, hdd));
			 }		 
		 }
//		 conn.close();
}

	private void update(Scanner sc) throws SQLException{
		int idFind;
		while(true) {
			try {
				System.out.println("Enter device ID to update: ");
				idFind = Integer.parseInt(sc.nextLine());
				break;
			} catch(Exception e) {
				System.err.println("Pls enter the number!");
			}
		}
		
		//Tim trong list va them vao list
		Computer deviceUpdate = null;
		for(Computer device : devices) {
			if(device.getId() == idFind) {
				deviceUpdate = device;
				break;
			}
		}
		
		//Tim trong DB
		boolean resultNotEmpty = true;
		if(deviceUpdate == null) {
			Connection conn = DatabaseConnection.getConnection();
			String sql = "SELECT * FROM device WHERE id = ?";
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setInt(1, idFind);
			ResultSet result = ppst.executeQuery();
			if(result.next()) { //ko tim thay id
				resultNotEmpty = false;
				 String name = result.getString("name");
	                byte ram = result.getByte("ram");
	                float cpu = result.getFloat("cpu");
	                short hdd = result.getShort("hdd");
	                String type = result.getString("type");
	                if ("Laptop".equalsIgnoreCase(type)) {
	                    float weight = result.getFloat("weight");
	                    String color = result.getString("color");
	                    deviceUpdate = new Laptop(idFind, name, ram, cpu, hdd, weight, color);
	                } else {
	                    deviceUpdate = new PC(idFind, name, ram, cpu, hdd);
	                }
			} else {
				System.out.println("Device does not exist.");
	            return;
	        }
		}
		
		
		//Thuc hien update
		if(deviceUpdate != null || resultNotEmpty) {  //neu resultEmpty = true -> thi thuc hien update
			//Name
			
			System.out.println("New Name = ");
			String newName = sc.nextLine();
			if (!newName.isEmpty()){
				deviceUpdate.setName(newName);
			}
				
			//Ram
			System.out.println("New Ram = ");
			String newRam = sc.nextLine();
			if (!newRam.isEmpty()){
				deviceUpdate.setRam(Byte.parseByte(newRam));
			}	
			
			//CPU
			System.out.println("New CPU: ");
	        String newCpu = sc.nextLine();
	        if (!newCpu.isEmpty()) {
	            deviceUpdate.setCpu(Float.parseFloat(newCpu));
	        }
	        
	        //HDD
	        System.out.println("New HDD: ");
	        String newHdd = sc.nextLine();
	        if (!newHdd.isEmpty()) {
	            deviceUpdate.setHdd(Short.parseShort(newHdd));
	        }

	        if (deviceUpdate instanceof Laptop) {
	            Laptop laptop = (Laptop) deviceUpdate;
	            //Weight
	            System.out.println("New Weight: ");
	            String newWeight = sc.nextLine();
	            if (!newWeight.isEmpty()) {
	                laptop.setWeight(Float.parseFloat(newWeight));
	            }
	            
	            //Color
	            System.out.println("Enter new color (leave blank to keep current): ");
	            String newColor = sc.nextLine();
	            if (!newColor.isEmpty()) {
	                laptop.setColor(newColor);
	            }	            
	          }	 
	        
	        //Update trong DB
	        Connection conn = DatabaseConnection.getConnection();
	        String sql = "UPDATE device SET name = ?, ram = ?, cpu = ?, hdd = ?, weight = ?, color = ? WHERE id = ?";
	        PreparedStatement ppst = conn.prepareStatement(sql);
	        ppst.setInt(7, idFind);
	        ppst.setString(1, deviceUpdate.getName());
	        ppst.setFloat(2, deviceUpdate.getRam());
        	ppst.setFloat(3, deviceUpdate.getCpu());
        	ppst.setShort(4, deviceUpdate.getHdd());
        	if (deviceUpdate instanceof PC) {     // doi tuong thuoc lop PC
        		ppst.setString(5, null);
                ppst.setString(6, null);
        	}
        	if (deviceUpdate instanceof Laptop) {  //doi tuong thuoc lop laptop
        		Laptop laptopDB = (Laptop) deviceUpdate;
        		ppst.setFloat(5, laptopDB.getWeight());
        		ppst.setString(6, laptopDB.getColor());
        	}
        	int numberRowAffected = ppst.executeUpdate();
        	if(numberRowAffected > 0 || deviceUpdate != null) {
        		System.out.println("Update success!");
        		return;
        	}
	        
		}
	}
		
	private void delete(Scanner sc) throws SQLException{
		int id;
		while(true) {
			try {
				System.out.println("Enter device ID to delete: ");
				id = Integer.parseInt(sc.nextLine());
				break;
			} catch(Exception e) {
				System.err.println("Pls enter the number!");
			}
		}
		
		
		//Kiem tra trong list
		Computer deviceDelete = null;
		for(Computer device : devices) {
			if(device.getId() == id) {
				deviceDelete = device;
				break;
			}
		}
		
		//Kiem tra trong DB
		if(deviceDelete == null) {
			Connection conn = DatabaseConnection.getConnection();
			String sql = "SELECT * FROM device WHERE id = ?";
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setInt(1, id);
			ResultSet result = ppst.executeQuery();
			while(!result.next()) { //ko tim thay id
				System.out.println("Device does not exist!");
				return;
			}
		}
		
		//Confirm delete and delete
		while(true) {
			try {
				System.out.println("Are you sure to delete this device?");
				System.out.println("1. Yes   2. No");
				int choose = Integer.parseInt(sc.nextLine());
				if (choose == 1) {
					if (!(deviceDelete == null)) {
						//Xoa trong list
						devices.remove(deviceDelete);
						System.out.println("Delete success!");
						break;
					}else {
						//Xoa trong DB
						Connection conn = DatabaseConnection.getConnection();
						String sql2 = "DELETE FROM device WHERE id = ?";
						PreparedStatement ppstt = conn.prepareStatement(sql2);
						ppstt.setInt(1, id);
						int numberRowAffected = ppstt.executeUpdate();
						if(numberRowAffected > 0) {
							System.out.println("Delete success!");
						}else {
							System.out.println("Delete fail!");
						}
						conn.close();
						break;
					}
				}else if(choose == 2) {
					System.out.println("Delete cancel!");
					break;
				}else {
					System.err.println("Invalid number!");
				}
			} catch (NumberFormatException nfe){
				System.err.println("Pls enter the number");
			} catch(Exception e) {
				System.err.println(e.getMessage());
			}
		}

}
	
	private void sortByName(Scanner sc) throws SQLException {
		System.out.println("Enter name to search: ");
		String name = sc.nextLine();
		
		// tìm trong list
		ArrayList<Computer> resultFind = new ArrayList<>();
		for (Computer device : devices) {					
			if (device.getName().contains(name)) {
				resultFind.add(device);                   //tim ten va add vao resultFind arraylist
			}
		}
		
		//tìm trong DB
		Connection conn = DatabaseConnection.getConnection();
		String sql = "SELECT * FROM device WHERE name LIKE ?";  //LIKE: tìm kiếm chuỗi có chứa giá trị nhập vào
		PreparedStatement ppst = conn.prepareStatement(sql);
		ppst.setString(1, "%" + name + "%");
		ResultSet result = ppst.executeQuery();
		
		ArrayList<Computer> resultFromDB = new ArrayList<>();
		boolean resultEmpty = true;   //Gia su resultSet = blank
		
		while (result.next()) {		  //Di chuyen con tro, neu co ban ghi thi thuc hien
		     resultEmpty = false;
			 int id = result.getInt("id");
			 String namee = result.getString("name");
			 byte ram =  result.getByte("ram");
			 float cpu = result.getFloat("cpu");
			 short hdd = result.getShort("hdd");
			 float weight = result.getFloat("weight");
			 String color = result.getString("color");
			 String type = result.getString("type");
			 if ("Laptop".equalsIgnoreCase(type)) {   //so sanh 2 chuoi, ignore ki tu hoa-thuong, neu giong nhau -> true -> thuc hien
				 resultFromDB.add(new Laptop(id, namee, ram, cpu, hdd, weight, color));
			 }else if("PC".equalsIgnoreCase(type)) {
				 resultFromDB.add(new PC(id, namee, ram, cpu, hdd));
			 }		 
		 }
		 conn.close();
		 
		resultFind.addAll(resultFromDB);
		resultFind.sort(Comparator.comparing(Computer::getName)); //Sap xep danh sach theo name (ORDER BY) default la ASC
		if (resultFind.isEmpty() && resultEmpty){  
			System.out.println("Have no any device");
		}else {
			for (Computer device : resultFind)
			System.out.println(device);
		}			
		
		
	}
	
	private void saveDevicesToFile() throws SQLException {
		Connection conn = DatabaseConnection.getConnection();
        try{	
        	String sql = "INSERT INTO device (id, name, ram, cpu, hdd, weight, color, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        	for (Computer device : devices) {
	        	PreparedStatement ppst = conn.prepareStatement(sql);
	        	ppst.setInt(1, device.getId());
	        	ppst.setString(2, device.getName());
	        	ppst.setByte(3, device.getRam());
	        	ppst.setFloat(4, device.getCpu());
	        	ppst.setShort(5, device.getHdd());
       
	        	if (device instanceof PC) {     // doi tuong thuoc lop PC
	        		ppst.setString(6, null);
	                ppst.setString(7, null);
	                ppst.setString(8, "PC");
	        	}
	        	if (device instanceof Laptop) {  //doi tuong thuoc lop laptop
	        		Laptop laptop = (Laptop) device;
	        		ppst.setFloat(6, laptop.getWeight());
	        		ppst.setString(7, laptop.getColor());
	        		ppst.setString(8, "Laptop");
	        	}
	        	ppst.executeUpdate();
	        }	    
	        	System.out.println("All devices saved to database successfully!");
	        	devices.clear();
		} catch (Exception e) {
	    	System.out.println(e.getMessage());
	    } finally{
	    	conn.close();
	    }
	}
	
	private void exit(Scanner sc) throws SQLException {
		if (!devices.isEmpty()) {
			while(true) {
				try {
					System.out.println("Save to database before exit?");
					System.out.println("1. Yes  2. No");
					int choose = Integer.parseInt(sc.nextLine());
					if (choose == 1) {				
						saveDevicesToFile();
						System.out.println("Exit menu!");
						return;
					}else if (choose == 2) {
						System.out.println("Exit menu!");
						return;
					}else {
						System.err.println("Invalid number!");
					}
				}catch(Exception e) {
					System.err.println("Pls enter the number!");
				}
			}
		}else {
			System.out.println("Exit menu!");
		}
	}
 
}
	// gap return -> method dừng
	// vong lap gap break -> thoat khoi vong lap
	

