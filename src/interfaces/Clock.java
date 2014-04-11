package interfaces;

public interface Clock {
	public void addObserver(ClockObserver clockObserver);
	public void removeObserver(ClockObserver clockObserver);
	public void getState();
	public void setState(String state);
}
