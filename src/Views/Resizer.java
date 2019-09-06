package Views;

public class Resizer {
	
	
	public static int resizeX(int x, int size, int defaultSize, int debutX) {
    	int nColumn = (x / defaultSize);
    	int nLeft = (x % defaultSize);
    	nLeft = nLeft * size / defaultSize;
    	nLeft += nColumn * size;
    	nLeft += debutX;
    	return nLeft;
    }
	
	public static int resizeY(int y, int size, int defaultSize, int debutY) {
    	int nColumn = (y / defaultSize);
    	int nLeft = (y % defaultSize);
    	nLeft = nLeft * size / defaultSize;
    	nLeft += nColumn * size;
    	nLeft += debutY;
    	return nLeft;
    }
}
