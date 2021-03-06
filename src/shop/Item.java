package shop;

public class Item {
	private int id;
	private String fabricName;
	private String fabricCountry;
	private String type;
	private String format;
	private String resolution;
	private String model;
	private int diagonal;
	private int price;
	private int count;

	public Item(int id, String fabricName, String fabricCountry, String type, String format, String resolution, String model, int diagonal, int price, int count) {
		this.id = id;
		this.fabricName = fabricName;
		this.fabricCountry = fabricCountry;
		this.type = type;
		this.format = format;
		this.resolution = resolution;
		this.model = model;
		this.diagonal = diagonal;
		this.price = price;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFabricName() {
		return fabricName;
	}

	public void setFabricName(String fabricName) {
		this.fabricName = fabricName;
	}

	public String getFabricCountry() {
		return fabricCountry;
	}

	public void setFabricCountry(String fabricCountry) {
		this.fabricCountry = fabricCountry;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(int diagonal) {
		this.diagonal = diagonal;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
