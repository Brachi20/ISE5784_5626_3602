package renderer;

import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

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
        var intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null
                ? scene.background
                : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }


    /**
     * Calculates the color of a point
     * @param intersection the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
            .add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculates the color of a point based on local effects
     * @param point the point
     * @return the color of the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        for(LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(cal)),
                double ktr = transparency(lightSource, l, n, gp);
                if (ktr * nl > 0) {
                    Color lightIntensity = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            calcDiffusive(material.kD, n, l, lightIntensity),
                            calcSpecular(material.kS, n, l, v, lightIntensity));
                }
            }
        }
    }



}
