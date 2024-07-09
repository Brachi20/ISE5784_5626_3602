package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {

    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color of the closest intersection point
     *
     * @param ray the ray to trace
     * @return the color of the closest intersection point
     */
    public abstract Color traceRay(Ray ray);

}
