package device.entities;

public class Computer {
	private int id;
	private String name;
	private byte ram;
	private float cpu;
	private short hdd;

	public Computer(int id, String name, byte ram, float cpu, short hdd) {
		this.id = id;
		this.name = name;
		this.ram = ram;
		this.cpu = cpu;
		this.hdd = hdd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getRam() {
		return ram;
	}

	public void setRam(byte ram) {
		this.ram = ram;
	}

	public float getCpu() {
		return cpu;
	}

	public void setCpu(float cpu) {
		this.cpu = cpu;
	}

	public short getHdd() {
		return hdd;
	}

	public void setHdd(short hdd) {
		this.hdd = hdd;
	}
	
	public String toString(){
		return "[id = " + id + ", name = " + name + ", ram = " + ram + ", cpu = " + cpu + ", hdd = " + hdd;
	}

}
