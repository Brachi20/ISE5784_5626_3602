package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * A simple ray tracer that traces rays and returns the color of the closest intersection point
 */
public class SimpleRayTracer extends RayTracerBase{

    /**
     * Constructs a new simple ray tracer with the given scene
     * @param scene the scene to trace rays in
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and returns the color of the closest intersection point
     * @param ray the ray to trace
     * @return the color of the closest intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> pointList = scene.geometries.findIntersections(ray);
        if (pointList == null) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(pointList);
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color of a point
     * @param point the point
     * @return the color of the point
     */
    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }


}
