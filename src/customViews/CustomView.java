package customViews;

public class CustomView {

	protected int yCoordinate;
	protected int xCoordinate;
	protected int height;
	protected int width;
	protected int relX;
	protected int relY;
	protected int relW;
	public int getRelW() {
		return relW;
	}

	public void setRelW(int relW) {
		this.relW = relW;
	}

	public int getRelH() {
		return relH;
	}

	public void setRelH(int relH) {
		this.relH = relH;
	}

	protected int relH;
	public int getRelX() {
		return relX;
	}

	public void setRelX(int relX) {
		this.relX = relX;
	}

	public int getRelY() {
		return relY;
	}

	public void setRelY(int relY) {
		this.relY = relY;
	}

	

	CustomView(int xCoordinate, int yCoordinate, int height, int width) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.height = height;
		this.width = width;
		this.relX=xCoordinate;
		this.relY=yCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	
}
