
public class Drop {
	private int id;
	private int x;
	private int y;
	private int xStart;
	private int yStart;
	
	public Drop(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.xStart = x;
		this.yStart = y;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getXStart() {
		return xStart;
	}
	public void setXStart(int x) {
		this.xStart = x;
	}
	public int getYStart() {
		return yStart;
	}
	public void setYStart(int y) {
		this.yStart = y;
	}
	
	
	
	
	
}
