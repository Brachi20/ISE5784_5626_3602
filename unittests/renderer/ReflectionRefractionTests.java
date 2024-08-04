/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Cylinder;
import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {

    /** The level of the anti-aliasing in the tests */
    private int antiAliasingLevel = 1;
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKl(0.00001).setKq(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    /** Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKl(4E-5).setKq(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }


    /**
     * Creating an image in which there is a scene that consists of a partially transparent cylinder with an opaque sphere inside
     * And next to the cylinder there is another smaller cylinder that is reflected on a polygon with 6 vertices
     * And in addition, the transparent cylinder casts a shadow on the smaller cylinder
     */
    @Test
    public void reflectionRefractionFourObjectsTest() {

        scene.geometries.add(
                new Polygon(
                        new Point(-210, -210, -100),
                        new Point(210, -210, -100),
                        new Point(80, 90, -100),
                        new Point(-80, 90, -100)
                ).setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8)).setKd(0.5).setKs(0.5).setShininess(60)),

                new Sphere(new Point(-70, -110, -80), 30d)// blue sphere
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),

                new Sphere(new Point(-100, -40, -100), 50d)// purple sphere
                        .setEmission(new Color(128, 0, 128))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(50).setKt(0.5)));

        scene.lights.add(
                new SpotLight(new Color(255, 255, 255), new Point(-100, -100, 200), new Vector(1, 1, -3))
                        .setKl(0.0001).setKq(0.00001).setNarrowness(10)); // הגברת עוצמת התאורה והצרת הקרן למראה תלת ממדי

        scene.lights.add(
                new PointLight(new Color(255, 200, 150), new Point(100, -200, 100))
                        .setKl(0.0001).setKq(0.00001)); // אור נוסף להדגשת הצללים וההשתקפויות

        cameraBuilder.setLocation(new Point(0, 0, 2000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("reflectionFourObjectsTest", 600, 600))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    @Test
    public void sceneWithTenObjects() {

        scene.geometries.add(
                new Polygon(
                        new Point(-120, -180, -100),
                        new Point(120, -180, -100),
                        new Point(115, -130, -100),
                        new Point(-110, -130, -100)
                ).setEmission(new Color(128, 128, 128))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8)).setKd(0.5).setKs(0.5).setShininess(60)),
                new Polygon(
                        new Point(-120, -180, -100),
                        new Point(120, -180, -100),
                        new Point(120, -190, -100),
                        new Point(-120, -190, -100)
                ).setEmission(new Color(64, 64, 64))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8)).setKd(0.5).setKs(0.5).setShininess(60)),
                new Polygon(
                        new Point(-130, -190, -100),
                        new Point(-130, -80, -100),
                        new Point(-120, -80, -100),
                        new Point(-120, -190, -100)
                ).setEmission(new Color(64, 64, 64))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8)).setKd(0.5).setKs(0.5).setShininess(60)),
                new Polygon(
                        new Point(-120, -180, -100),
                        new Point(-120, -80, -100),
                        new Point(-110, -80, -100),
                        new Point(-110, -130, -100)
                ).setEmission(new Color(128, 128, 128))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8)).setKd(0.5).setKs(0.5).setShininess(60)),
                new Polygon(
                        new Point(120, -190, -100),
                        new Point(120, -130, -100),
                        new Point(130, -80, -100),
                        new Point(130, -190, -100)
                ).setEmission(new Color(64, 64, 64))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8)).setKd(0.5).setKs(0.5).setShininess(60)),
                new Polygon(
                        new Point(120, -180, -100),
                        new Point(120, -80, -100),
                        new Point(130, -80, -100),
                        new Point(130, -190, -100)
                ).setEmission(new Color(64, 64, 64))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8)).setKd(0.5).setKs(0.5).setShininess(60)));


        scene.lights.add(
                new SpotLight(new Color(255, 255, 255), new Point(-100, -100, 200), new Vector(1, 1, -3))
                        .setKl(0.0001).setKq(0.00001).setNarrowness(10)); // הגברת עוצמת התאורה והצרת הקרן למראה תלת ממדי

        scene.lights.add(
                new PointLight(new Color(255, 200, 150), new Point(100, -200, 100))
                        .setKl(0.0001).setKq(0.00001)); // אור נוסף להדגשת הצללים וההשתקפויות

        cameraBuilder.setLocation(new Point(0, 0, 2000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("sceneWithTenObjectsTest", 600, 600))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }




}
