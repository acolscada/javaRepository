package cn.acol.security.core.properties;

/**
 * 
 * @author DaveZhang
 *
 */
public class ImageCodeProperties extends SmsCodeProperties{
	private int width = 67;
    private int height = 23;
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	
    
	
}