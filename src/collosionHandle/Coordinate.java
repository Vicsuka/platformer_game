package collosionHandle;

public class Coordinate {	
	int x;
    int y;
    
    // megcsinaljuk az egyenloseget 
    public boolean equals(Object o) {
    	Coordinate c = (Coordinate) o;
        return c.x == x && c.y == y;
    }

    public Coordinate(int x, int y) { 
        this.x = x;
        this.y = y;
    }
    
    public int getx() {
    	return x;
    }
    
    public int gety() {
    	return y;
    }
}