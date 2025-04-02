package villagegaulois;

public class VillageSansChefException extends IllegalStateException{
	
	public VillageSansChefException() {
		super();
	}
	
	public VillageSansChefException(String s) {
		super(s);
	}
	
	public VillageSansChefException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public VillageSansChefException(Throwable cause) {
		super(cause);
	}
}
