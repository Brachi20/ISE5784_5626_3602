package lighting;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    /**
     * getter for the vector from the light source to the point
     * @param point the point at which the vector is calculated
     * @return the vector from the light source to the point
     */
    public DirectionalLight(Color iD, Vector direction){
        super(iD);
        this.direction = direction.normalize();
    }

    public Color getIntensity(Point point){
        return intensity;
    }

    public Vector getL(Point point){
        return direction;
    }

    @Override
    public double getDistance(Point nwePoint) {
        return Double.POSITIVE_INFINITY;
    }


}
