package gbw.roguelike;

public class Vector2D {

    private double x,y;

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Vector2D(Vector2D v1, Vector2D v2){
        this.x = v1.getX() - v2.getX();
        this.y = v1.getY() - v2.getY();
    }

    //BASIC MATH
    public void multiply(double fx, double fy){
        x *= fx;
        y *= fy;
    }
    public void multiply(Vector2D vec){
        x *= vec.getX();
        y *= vec.getY();
    }
    public void multiply(double f){
        x *= f;
        y *= f;
    }
    public Vector2D getMultiplied(double fx, double fy){
        return new Vector2D(x * fx, y * fy);
    }
    public Vector2D getMultiplied(Vector2D vec){
        return new Vector2D(x * vec.getX(), y * vec.getY());
    }
    public Vector2D getMultiplied(double f){
        return new Vector2D(x * f, y * f);
    }

    public void add(double fx, double fy){
        x += fx;
        y += fy;
    }
    public void add(Vector2D vec){
        x += vec.getX();
        y += vec.getY();
    }
    public void add(double f){
        x += f;
        y += f;
    }
    public Vector2D getAdded(double fx, double fy){
        return new Vector2D(x + fx, y + fy);
    }
    public Vector2D getAdded(Vector2D vec){
        return new Vector2D(x + vec.getX(), y + vec.getY());
    }
    public Vector2D getAdded(double f){
        return new Vector2D(x + f, y + f);
    }

    public void subtract(double f){
       x -= f;
       y -= f;
    }
    public void subtract(double fx, double fy){
        x -= fx;
        y -= fy;
    }
    public void subtract(Vector2D vec){
        x -= vec.getX();
        y -= vec.getY();
    }
    public Vector2D getSubtracted(double f){
        return new Vector2D(x - f, y - f);
    }
    public Vector2D getSubtracted(double fx, double fy){
        return new Vector2D(x - fx, y - fy);
    }
    public Vector2D getSubtracted(Vector2D vec){
        return new Vector2D(x - vec.getX(), y - vec.getY());
    }

    public void divide(double f){
        if(f == 0){f = 1;}
        x /= f;
        y /= f;
    }
    public void divide(double fx, double fy){
        if(fx == 0){fx = 1;}
        if(fy == 0){fy = 1;}
        x /= fx;
        y /= fy;
    }
    public void divide(Vector2D vec){
        if(vec.getX() == 0){return;}
        if(vec.getY() == 0){return;}
        x /= vec.getX();
        y /= vec.getY();
    }
    public Vector2D getDivided(double f){
        if(f == 0){return new Vector2D(0,0);}
        return new Vector2D(x / f, y / f);
    }
    public Vector2D getDivided(double fx, double fy){
        if(fx == 0 || fy == 0){return new Vector2D(0,0);}
        return new Vector2D(x / fx, y / fy);
    }
    public Vector2D getDivided(Vector2D vec){
        if(vec.getX() == 0 || vec.getY() == 0){return new Vector2D(0,0);}
        return new Vector2D(x / vec.getX(), y / vec.getY());
    }

    //COOL MATH
    public void limitTo(Vector2D pos, Vector2D vec2){
        //View this vector object as a velocity vector from point "pos". This limits the x and y parameters of this object to the vec2 given.
        //Vec2 is viewed as a point in space.
        double exceedingByX = vec2.getX() - (pos.getX() + this.getX());
        double exceedingByY = vec2.getY() - (pos.getY() + this.getY());

        if(exceedingByX > 0){
            x += exceedingByX;
        }
        if(exceedingByY > 0){
            y += exceedingByY;
        }

    }
    public double distanceTo(Vector2D vec2){
        return new Vector2D(this,vec2).magnitude();
    }
    public double distanceToSQ(Vector2D vec2){
        return new Vector2D(this,vec2).magnitudeSQ();
    }
    public Vector2D getNormalized(){
        double mag = magnitude();
        if (mag == 0.0) {
            return new Vector2D(0.0, 0.0);
        }
        return new Vector2D(this.getX() / mag, this.getY() / mag);
    }
    public void normalize(){
        if(magnitude() == 0){return;}
        x /= magnitude();
        y /= magnitude();
    }
    public double magnitude(){
        //For multiple calls in a row, use the squared version Vector2D.magnitudeSQ()
        return Math.sqrt((x * x) + (y * y));
    }
    public double magnitudeSQ(){
        //For multiple magnitude calls in a row, not calling Math.sqrt() improves performance drastically
        return(x * x) + (y * y);
    }
    public double angleTo(Vector2D vec){
        double gradient = (vec.getY() - this.getY()) / (vec.getX() - this.getX());
        double theta = Math.atan(gradient);
        return (theta / (Math.PI / 2.00)) * 180;
    }
    public Vector2D getOrthogonal(){
        return new Vector2D(-y,x);
    }
    public void orthogonalize(){
        double tempX = x;
        this.x = -y;
        this.y = tempX;
    }

    //WIERD MATH
    public Vector2D getLerped(double f){
        Vector2D start = this.getNormalized();
        Vector2D end = this.clone();
        Vector2D diff = start.getSubtracted(end);
        Vector2D diffLerped = diff.getMultiplied(f);
        return diffLerped.getAdded(start);
    }

    //IMPORTANT STUFF
    public Vector2D clone(){
        return new Vector2D(this.getX(),this.getY());
    }

    //BASIC STUFF
    public double getX(){return x;}
    public double getY(){return y;}
    public void setX(double fx){x = fx;}
    public void setY(double fy){y = fy;}

    //STATIC STUFF
    public static double distance(Vector2D v1, Vector2D v2){
        return new Vector2D(v1,v2).magnitude();
    }
    public static double distanceSQ(Vector2D v1, Vector2D v2){
        return new Vector2D(v1,v2).magnitudeSQ();
    }


}
