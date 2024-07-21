package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A simple ray tracer that traces rays and returns the color of the closest intersection point
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * The delta value for the shadow rays
     */
    private static final double DELTA = 0.1;
    /**
     * The maximum level of recursion for the calcColor method
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * The minimum value for the color coefficient
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructs a new simple ray tracer with the given scene
     *
     * @param scene the scene to trace rays in
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Constructs a reflected ray
     *
     * @param gp  the point
     * @param ray the ray
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double vn = v.dotProduct(n);
        if (vn == 0) return null;    //if the ray is parallel to the normal
        Vector r = v.subtract(n.scale(2 * vn));  //the reflection of the ray
        if (vn == 0) return null;
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(gp.point, r, n);
    }

    /**
     * Constructs a refracted ray
     *
     * @param gp  the point
     * @param ray the ray
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);
        double vn = v.dotProduct(n);
        if (vn == 0) return null;
        return new Ray(gp.point, v, n);
    }


    /**
     * Finds the closest intersection point of a ray
     *
     * @param ray the ray
     * @return the closest intersection point of the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * Traces a ray and returns the color of the closest intersection point
     *
     * @param ray the ray to trace
     * @return the color of the closest intersection point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp,ray), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp,ray), material.kR, level, k));
    }

    /**
     * Calculates the global effects of a ray
     *
     * @param ray   the ray2
     * @param k     the color coefficient
     * @param level the level of recursion
     * @param kx    the color coefficient
     * @return the color of the ray
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Traces a ray and returns the color of the closest intersection point
     *
     * @param ray the ray to trace
     * @return the color of the closest intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of a point
     *
     * @param gp the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color of a point
     *
     * @param intersection the intersection point
     * @param ray          the ray
     * @param level        the level of recursion
     * @param k            the color coefficient
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color
                : color.add(calcGlobalEffects(intersection, ray, level, k));
    }


    /**
     * Calculates the color of a point based on local effects
     *
     * @param gp the point
     * @return the color of the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector n = gp.geometry.getNormal(gp.point);//normal of the geometry in the point
        Vector v = ray.getDirection();//direction of the ray from the camera
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        Material material = gp.geometry.getMaterial();
        if (nv == 0) return color;//if so, the light has no effect at all
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);//vector from the light source to the lighting point
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {// sign(nl) == sing(nv) check if the light is in the same direction as the normal
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)
                                    .add(calcSpecular(material, n, l, nl, v))));//this is the color of the light source
                }
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
        private Double3 calcDiffusive (Material material,double nl){
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
        private Double3 calcSpecular (Material material, Vector n, Vector l,double nl, Vector v){
            Vector r = l.subtract(n.scale(2 * nl));//the reflection of the light vector
            double vr = v.dotProduct(r);
            return material.kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), material.nShininess));
        }

        /**
         * Checks if the point is unshaded
         *
         * @param gp the point
         * @param ls the light source
         * @param l  the vector from the light source to the lighting point
         * @param n  the normal of the geometry
         * @param nl the dot product of n and l
         * @return true if the point is unshaded, false otherwise
         */
        private boolean unshaded (GeoPoint gp, LightSource ls, Vector l, Vector n,double nl){
            Vector lightDirection = l.scale(-1);
            Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
            Point point = gp.point.add(epsVector);
            Ray ray = new Ray(point, lightDirection);
            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
            if (intersections != null) {
                double lightDistance = ls.getDistance(gp.point);
                for (var intersection : intersections) {
                    if (gp.point.distance(intersection.point) < lightDistance)
                        return false;
                }
            }
            return true;
        }

        /**
         * Calculates the transparency of a point
         * @param gp the point
         * @param ls the light source
         * @param l the vector from the light source to the lighting point
         * @param n the normal of the geometry
         * @return the transparency of the point
         */
        private Double3 transparency (GeoPoint gp, LightSource ls, Vector l, Vector n){
            Vector lightDirection = l.scale(-1);
            double nl = n.dotProduct(l);
            Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
            Point point = gp.point.add(epsVector);
            Ray ray = new Ray(point, lightDirection);
            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
            if (intersections != null) {
                double lightDistance = ls.getDistance(gp.point);
                Double3 ktr = Double3.ONE;
                for (var intersection : intersections) {
                    if (gp.point.distance(intersection.point) < lightDistance) {
                        ktr = ktr.product(intersection.geometry.getMaterial().kT);
                        if (ktr.lowerThan(MIN_CALC_COLOR_K))
                            return Double3.ZERO;
                    }
                }
                return ktr;
            }
            return Double3.ONE;
        }
}

