package special;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;
import geometries.*;
import primitives.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.*;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import java.util.Random;

import primitives.Double3;

/**
 * Unit test for rendering a scene with a snowman, trees, and various lights.
 * <p>
 * Configures and renders a scene with a snowman, random trees, and spheres,
 * including different types of lights.
 *
 * @author Yoni and adiel
 */
class ManyObjectsTest {
    //create a picture with ICE CREAM
    @Test
    public void reflectionRefractionManyObjectsTest() {
        Scene newScene = new Scene("Test scene");
        Camera.Builder newCameraBuilder = new Camera.Builder()
                .setAntialiasingLevel(2)
                .setAdaptive(false)
                .setThreadsCount(0);

        newScene.geometries.add(

                // The ice cream stand
                new Polygon(new Point(3.5, -0.5, 0),
                        new Point(4.75, 1.5, 0),
                        new Point(0.5, 5.5, 0),
                        new Point(-0.5, 3.5, 0))//
                        .setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material()
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKd(0.7)
                                .setKs(0.4)
                                .setShininess(30)),
                // C, D, B, A .The bottom of the ice cream stand

                new Polygon(new Point(3.5, -0.5, 1),
                        new Point(3.5, -0.5, 0),
                        new Point(-0.5, 3.5, 0),
                        new Point(-0.5, 3.5, 1))
                        .setEmission(new Color(80, 80, 80)).setMaterial(new Material()
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKd(0.7)
                                .setKs(0.4)
                                .setShininess(30)),
                // E, A, B, G the rectangle behind the stand
                new Polygon(new Point(4.75, 1.5, 1),
                        new Point(4.75, 1.5, 0),
                        new Point(3.5, -0.5, 0),
                        new Point(3.5, -0.5, 1))
                        .setEmission(new Color(80, 80, 80)).setMaterial(new Material()
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKd(0.7)
                                .setKs(0.4)
                                .setShininess(30)),
                // F, C, A, E Left rectangle of the stand

                new Polygon(new Point(0.5, 5.5, 1),
                        new Point(0.5, 5.5, 0),
                        new Point(-0.5, 3.5, 0),
                        new Point(-0.5, 3.5, 1))
                        .setEmission(new Color(80, 80, 80)).setMaterial(new Material()
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKd(0.7)
                                .setKs(0.4)
                                .setShininess(30)),
                // H, D, B, G Right rectangle of the stand

                new Polygon(
                        new Point(4.75, 1.5, 0.74),
                        new Point(0.5, 5.5, 0.74),
                        new Point(0.5, 5.5, 1),
                        new Point(4.75, 1.5, 1)
                        // L_{3}, M_{3}, H, F  It's the small rectangle at the top of the page
                ).setEmission(new Color(80, 80, 80)).setMaterial(new Material()
                        .setKr(new Double3(0.3, 0.3, 0.3))
                        .setKd(0.7)
                        .setKs(0.4)
                        .setShininess(10)),

                //Black candy on one of the ice cream scoops
                new Polygon(new Point(1.89, 2.83, 3.37),
                        new Point(1.7, 2.92, 3.49),
                        new Point(1.79, 2.98, 3.32)) // N_{1} V_{1} W_{1}
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material()
                                .setKr(new Double3(0.1, 0.1, 0.1))
                                .setKd(0.6)
                                .setKs(0.5)
                                .setShininess(50)), // N_{1}, V_{1}, W_{1}, triangle black candy

                new Polygon(new Point(1.36, 2.59, 3.74),
                        new Point(1.2, 2.58, 3.71),
                        new Point(1.35, 2.83, 3.66))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material()
                                .setKr(new Double3(0.1, 0.1, 0.1))
                                .setKd(0.6)
                                .setKs(0.5)
                                .setShininess(50)), // I_{2}, J_{2} ,K_{2}, triangle black candy


                new Polygon(new Point(1.94, 2.81, 2.91),
                        new Point(1.86, 2.95, 3),
                        new Point(1.84, 2.89, 2.83))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material()
                                .setKr(new Double3(0.1, 0.1, 0.1))
                                .setKd(0.6)
                                .setKs(0.5)
                                .setShininess(50)), // C_{2}, D_{2}, E_{2} triangle black candy

                new Polygon(new Point(1.15, 3.02, 2.8),
                        new Point(1.31, 3.09, 2.85),
                        new Point(1.26, 2.97, 2.67))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material()
                                .setKr(new Double3(0.1, 0.1, 0.1))
                                .setKd(0.6)
                                .setKs(0.5)
                                .setShininess(50)), // H_{2}, F_{2}, G_{2} triangle black candy

                new Polygon(new Point(0.99, 3.01, 3.12),
                        new Point(0.8, 2.74, 3),
                        new Point(0.86, 2.85, 3.21))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material()
                                .setKr(new Double3(0.1, 0.1, 0.1))
                                .setKd(0.6)
                                .setKs(0.5)
                                .setShininess(50)), // N_{2}, M_{2}, L_{2} triangle black candy

                new Polygon(new Point(6, 8.3, 0),
                        new Point(-3, 8.3, 0),
                        new Point(-3, -1, 0),
                        new Point(6, -1, 0))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8)).setKd(0.5).setKs(0.5).setShininess(60)),
                // C_{3}, B_{3}, E_{3}, D_{3} the table

                //Ice cream cones
                new Polygon(new Point(4.55, 1.56168, 2.5),
                        new Point(4.5, 0.24, 2.5),
                        new Point(3.45, 0.5, 2.5))
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(new Double3(0.2, 0.2, 0.2))
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)), // N, O, I  , Part of the ice cream cone

                new Polygon(new Point(0.2, 3.2, 2.4),
                        new Point(1, 4, 0),
                        new Point(-0.5, 3, 2.5))
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(0.2)
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(30)), // L, A_{1}, W  , Part of the ice cream cone

                new Polygon(new Point(0.2, 3.2, 2.4),
                        new Point(0.2, 2.45, 2.5),
                        new Point(1, 4, 0))
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(0.2)
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)), // Z, A_{1}, L  , Part of the ice cream cone

                new Polygon(new Point(1, 4, 0),
                        new Point(0.2, 2.45, 2.5),
                        new Point(-0.5, 3, 2.5))
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(0.2)
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)), // L, Z, W , Part of the ice cream cone


                new Polygon(new Point(1.5, 2.5, 0),
                        new Point(1.5, 2.81901, 2.5),
                        new Point(1.4, 1.6, 2.5))
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(new Double3(0.2, 0.2, 0.2))
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)), // U, V, K , Part of the ice cream cone

                new Polygon(new Point(2.75, 1.04, 2.5),
                        new Point(2.7, 2.2, 2.5),
                        new Point(2.25, 1.8, 0))
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(new Double3(0.2, 0.2, 0.2))
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)), // T, Q, J , Part of the ice cream cone

                new Polygon(new Point(1.5, 2.5, 0),
                        new Point(1.5, 2.81901, 2.5),
                        new Point(0.7, 2, 2.5))
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(new Double3(0.2, 0.2, 0.2))
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)), // K, V, S  , Part of the ice cream cone

                new Polygon(new Point(4.55, 1.56168, 2.5),
                        new Point(3.45, 0.5, 2.5),
                        new Point(3.5, 1, 0))
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(new Double3(0.2, 0.2, 0.2))
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)), // O, M, I  , Part of the ice cream cone

                new Polygon(new Point(2.25, 1.8, 0),
                        new Point(1.75, 1.13, 2.5),
                        new Point(2.7, 2.2, 2.5))
                        .setEmission(new Color(210, 180, 140)).
                        setMaterial(new Material()
                                .setKr(new Double3(0.2, 0.2, 0.2))
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)), // J, P, Q  , Part of the ice cream cone


                new Polygon(new Point(4.5, 0.24, 2.5),
                        new Point(3.45, 0.5, 2.5),
                        new Point(3.5, 1, 0)) //N M I , Part of the ice cream cone
                        .setEmission(new Color(210, 180, 140)).setMaterial(new Material()
                                .setKr(new Double3(0.2, 0.2, 0.2))
                                .setKd(0.7)
                                .setKs(0.3)
                                .setShininess(20)),

// Ice cream scoops

                new Sphere(new Point(-0.2, 2.5, 2.9), 0.65)
                        .setEmission(new Color(128, 0, 64))
                        .setMaterial(new Material()
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(50)
                        ),
                //K_{1} First scoop of ice cream

                new Sphere(new Point(1.4, 2.5, 3.1), 0.65)
                        .setEmission(new Color(173, 216, 230))
                        .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(2)),
// L_{1} Second scoop of ice cream

                new Sphere(new Point(2.5, 1.5, 2.9), 0.65)
                        .setEmission(new Color(255, 182, 193))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(15)),
// Q_{1} Third scoop of ice cream

                new Sphere(new Point(4.4, 1.2, 2.9), 0.65)
                        .setEmission(new Color(255, 253, 208))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)),
// I_{1} Fourth scoop of ice cream

                //candy's on the fourth scoop of ice cream
                new Sphere(new Point(4.95, 1.44, 3.16), 0.05)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // R , a pink candy

                new Sphere(new Point(4.65, 1.55, 3.39), 0.05)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // J_{1} , a pink candy

                new Sphere(new Point(4.37, 1.77, 3.22), 0.05)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // M_{1} , a pink candy

                new Sphere(new Point(4.85, 1.66, 3), 0.05)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // O_{1} , a pink candy

                new Sphere(new Point(4.12, 1.69, 2.58), 0.05) // P_{1}
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // P_{1} , a pink candy
                new Sphere(new Point(4.4, 1.85, 2.91), 0.05)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // R_{2} , a pink candy

                new Sphere(new Point(4.73, 1.51, 2.44), 0.05)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // S_{1} , a pink candy

                new Sphere(new Point(5.02, 1.38, 2.88), 0.05)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // T_{1} , a pink candy

                new Sphere(new Point(4.93, 1.23, 3.27), 0.05)
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                // U_{1} , a pink candy


                // Candy's on the table
                new Sphere(new Point(4.36928, 5.97949, 0), 0.05)
                        .setEmission(new Color(255, 20, 147)) // strong pink
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(4.55775, 5.65349, 0), 0.05)
                        .setEmission(new Color(255, 165, 0)) // orange
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(5.04566, 4.59264, 0), 0.05)
                        .setEmission(new Color(0, 0, 255)) // strong blue
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(5.17191, 5.39594, 0), 0.05)
                        .setEmission(new Color(219, 112, 147)) // light purple
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(4.13168, 5.1055, 0), 0.05)
                        .setEmission(new Color(135, 206, 250)) // pink blue
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(4.34224, 4.73467, 0), 0.05)
                        .setEmission(new Color(173, 216, 230)) // weak blue
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(4.82563, 5.33586, 0), 0.05)
                        .setEmission(new Color(255, 255, 255)) // white
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),
                new Sphere(new Point(4.34564, 4.10618, 0), 0.05)
                        .setEmission(new Color(128, 0, 128)) // purple
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(4.68181, 4.06632, 0), 0.05)
                        .setEmission(new Color(138, 43, 226)) // strong purple
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(4.80037, 4.68077, 0), 0.05)
                        .setEmission(new Color(255, 0, 0)) // red
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),

                new Sphere(new Point(5, 4, 0), 0.05)
                        .setEmission(new Color(255, 255, 0)) // yellow
                        .setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(100)),


                //handle of the cap
                new Sphere(new Point(-2.25, 1.9, 0.05), 0.05)
                        .setMaterial(new Material()
                                .setKt(new Double3(0.9, 0.9, 0.9))
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKs(0.7)
                                .setShininess(100)),
                // G_{1} a part of the handle of the cap

                new Sphere(new Point(-2.25, 1.9, 0.75), 0.07)
                        .setMaterial(new Material()
                                .setKt(new Double3(0.9, 0.9, 0.9))
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKs(0.7)
                                .setShininess(100)),
                // F_{1} a part of the handle of the cap


                // The glass behind the picture
                new Cylinder(1d, new Ray(new Point(-1.4, 2.2, 0), new Vector(0, 0, 1)), 0.5)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material()
                                .setKt(0.9)
                                .setKr(0.5)
                                .setKs(0.7)
                                .setShininess(100)),
                // B_{1}, C_{1}  a write glass

                new Cylinder(1d, new Ray(new Point(-2.26, 2.48, 0.84), new Vector(0.26, -0.38, -0.1)), -0.06)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material()
                                .setKt(new Double3(0.9, 0.9, 0.9))
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKs(0.7)
                                .setShininess(100)),
                // D_{1}, F_{1} part of the handle of the cap  (-2,2.1,0.78)(-2.26,2.48,0.84)

                new Cylinder(0.76d, new Ray(new Point(-2.26, 2.48, 0.84), new Vector(0, 0, -1)), 0.06)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material()
                                .setKt(new Double3(0.9, 0.9, 0.9))
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKs(0.7)
                                .setShininess(100)),
                // F_{1}, G_{1} part of the handle of the cap

                new Cylinder(1d, new Ray(new Point(-2.26, 2.48, 0.05), new Vector(0.26, -0.38, 0.1)), 0.06)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material()
                                .setKt(new Double3(0.9, 0.9, 0.9))
                                .setKr(new Double3(0.3, 0.3, 0.3))
                                .setKs(0.7)
                                .setShininess(100)),
                // G_{1}, E_{1} part of the handle of the cap  (-2,2.1,0.15)
                //Walls
                new Plane(new Point(1.84, -8, 3), new Point(1.84, -8, 0), new Point(-6.84, -8, 0))
                        .setEmission(new Color(50, 80, 50))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8))
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(10)),
                // Z_{3}, F_{3}, G_{3} the green wall behind the ice cream stand

                new Plane(new Point(-10, 0.32, 0), new Point(-6.84, -8, 0), new Point(-10, 0.32, 3))
                        .setEmission(new Color(50, 80, 50))
                        .setMaterial(new Material().setKr(new Double3(0.8, 0.8, 0.8))
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(10))
                // X_{3}, G_{3}, H_{3} the green right wall

        );


        newScene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.05));

        newScene.lights.add(
                new SpotLight(new Color(127, 112, 93), new Point(50, 40, 20), new Vector(-45.6, -38.8, -17.1))
                        .setKl(0.0005).setKq(0.0001)
        );

        newScene.lights.add(
                new SpotLight(new Color(100, 90, 60), new Point(4.6, 3.7, 11.3), new Vector(-1.2, -0.3, -2.1))
                        .setKl(0.0005).setKq(0.0001)
        );

        newScene.lights.add(
                new DirectionalLight(new Color(150, 150, 150), new Vector(1, -1, -1))
        );


        newCameraBuilder.setLocation(new Point(66, 97, 80))
                .setVpDistance(18000)
                .setVpSize(1000, 1000)
                .setDirection(new Vector(-64, -94, -80), new Vector(0, -80, 94))
                .setRayTracer(new SimpleRayTracer(newScene))
                .setImageWriter(new ImageWriter("reflectionManyObjectsTest", 1000, 1000))
                .build()
                .renderImage()
                .writeToImage();

    }

}
