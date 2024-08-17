package device.entities;

public class Laptop extends Computer {
	private float weight;
	private String color;
	
	public Laptop(int id, String name, byte ram, float cpu, short hdd, float weight, String color) {
		super(id , name, ram, cpu, hdd);
		this.weight = weight;
		this.color = color;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String toString() {
		return "Laptop " + super.toString() + ", weight = " + weight + ", color = " + color + "]";
	}
}
