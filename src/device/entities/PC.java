package device.entities;

public class PC extends Computer {

	public PC(int id, String name, byte ram, float cpu, short hdd) {
		super(id, name, ram, cpu, hdd);
	}
	
	public String toString() {
		return "PC " + super.toString() + "]";
	}

}
