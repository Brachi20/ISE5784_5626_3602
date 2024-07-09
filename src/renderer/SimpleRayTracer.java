package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A simple ray tracer that traces rays and returns the color of the closest intersection point
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a new simple ray tracer with the given scene
     *
     * @param scene the scene to trace rays in
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and returns the color of the closest intersection point
     *
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
     *
     * @param intersection the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculates the color of a point based on local effects
     *
     * @param point the point
     * @return the color of the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);//normal of the geometry in the point
        Vector v = ray.getDirection();//direction of the ray from the camera
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;//if so, the light has no effect at all
        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);//vector from the light source to the lighting point
            double nl = alignZero(n.dotProduct(l));
            if ((nl * nv > 0) && unshaded(gp, lightSource,l,n,nl)) {// sign(nl) == sing(nv) check if the light is in the same direction as the normal
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl)
                                .add(calcSpecular(material, n, l, nl, v))));//this is the color of the light source
            }
        }
        return color;
    }

    /**
     * Calculate the Diffusive light
     *
     * @param material the material of the geometry
     * @param nl       the dot product of n and l
     * @return the Diffusive light
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }


    /**
     * Calculate the Specular light
     *
     * @param material the material of the geometry
     * @param n        the normal of the geometry
     * @param l        the vector from the light source to the lighting point
     * @param nl       the dot product of n and l
     * @param v        the direction of the ray from the camera
     * @return the Specular light
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));//the reflection of the light vector
        double vr = v.dotProduct(r);
        return material.kS .scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), material.nShininess));
    }

    private boolean unshaded(GeoPoint gp, LightSource ls, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1);
        Vector epsVector = n.scale(nl < 0 ? EPS : -EPS);
        Point point = gp.point.add(epsVector);
        Ray ray = new Ray(point, lightDirection);
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections != null) {
            for (Point nwePoint : intersections) {
                if (nwePoint.distance(ray.getHead()) < ls.getDistance(nwePoint))
                    return false;
            }
        }
        return true;
    }

    public static final double EPS = 0.1;


}
