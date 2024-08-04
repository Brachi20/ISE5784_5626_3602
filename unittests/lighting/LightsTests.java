package lighting;

import geometries.Cylinder;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {

    /**
     * The level of the anti aliasing in the tests
     */
    private int antiAliasingLevel = 1;

    /**
     * Shininess value for most of the geometries in the tests
     */
    private static final int SHININESS = 301;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final double KD = 0.5;
    /**
     * Diffusion attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KD3 = new Double3(0.2, 0.6, 0.4);
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final double KS = 0.5;
    /**
     * Specular attenuation factor for some of the geometries in the tests
     */
    private static final Double3 KS3 = new Double3(0.2, 0.4, 0.3);
    /**
     * Radius of the sphere
     */
    private static final double SPHERE_RADIUS = 50d;
    /**
     * First scene for some of tests
     */
    private final Scene scene1 = new Scene("Test scene");
    /**
     * Second scene for some of tests
     */
    private final Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
    private final Vector v = new Vector(0, 0, -1);
    private final Vector Y = new Vector(0, 1, 0);
    /**
     * First camera builder for some of tests
     */
    private final Camera.Builder camera1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene1))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(v, Y)
            .setVpSize(150, 150).setVpDistance(1000);
    /**
     * Second camera builder for some of tests
     */
    private final Camera.Builder camera2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene2))
            .setLocation(new Point(0, 0, 1000))
            .setDirection(v, Y)
            .setVpSize(200, 200).setVpDistance(1000);
    /**
     * Material for some of the geometries in the tests
     */
    private final Material material = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);
    /**
     * Light color for tests with triangles
     */
    private final Color trianglesLightColor = new Color(800, 500, 250);
    //Lights color for the Multiple light sources in tests with triangles
    private final Color triangleDirectionalLightColor = new Color(500, 300, 100);
    private final Color triangleSpotLightColor = new Color(300, 500, 200);
    private final Color trianglePointLightColor = new Color(200, 200, 500);

    /**
     * Light color for tests with sphere
     */
    private final Color sphereLightColor = new Color(800, 500, 0);
    //Lights color for the Multiple light sources in tests with sphere
    /**
     * Light color for tests with Multiple light sources on sphere
     */
    private final Color sphereDirectionalLightColor1 = new Color(600, 400, 200);
    private final Color sphereSpotLightColor1 = new Color(400, 600, 300);
    private final Color spherePointLightColor1 = new Color(300, 300, 600);
    // Colors for the Multiple light sources in tests with sphere
    private final Color sphereDirectionalLightColor = new Color(800, 500, 250);
    private final Color spherePointLightColor = new Color(800, 500, 0);
    private final Color sphereSpotLightColor = new Color(500, 250, 250);


    /**
     * Color of the sphere
     */
    private final Color sphereColor = new Color(BLUE).reduce(2);
    /**
     * Center of the sphere
     */
    private final Point sphereCenter = new Point(0, 0, -50);
    /**
     * The triangles' vertices for the tests with triangles
     */
    private final Point[] vertices =
            {
                    // the shared left-bottom:
                    new Point(-110, -110, -150),
                    // the shared right-top:
                    new Point(95, 100, -150),
                    // the right-bottom
                    new Point(110, -110, -150),
                    // the left-top
                    new Point(-75, 78, 100)
            };

    /**
     * Position of the light in tests with sphere
     */
    private final Point sphereLightPosition = new Point(-50, -50, 25);
    //Positions for the Multiple light sources in tests with sphere
    /**
     * Position of the spotLight sources in tests with sphere
     */
    private final Point sphereSpotLightPosition = new Point(30, 30, 50);
    /**
     * Position of the PointLight sources in tests with sphere
     */
    private final Point spherePointLightPosition = new Point(-30, -30, 50);
    /**
     * Light direction (directional and spot) in tests with sphere
     */
    private final Vector sphereLightDirection = new Vector(1, 1, -0.5);
    //Directions for the Multiple light sources in tests with sphere
    /**
     * DirectionLight direction in tests with Multiple light sources on sphere
     */
    private final Vector sphereDirectionLightDirection = new Vector(-1, -1, 0.5);
    /**
     * spotLight direction in tests with Multiple light sources on triangle
     */
    private final Vector sphereSpotLightDirection = new Vector(-1, -1, 0.5);


    /**
     * Position of the DirectionalLight in tests with triangles
     */
    private final Point trianglesLightPosition = new Point(30, 10, -100);

    //Positions for the Multiple light sources in tests with triangles

    /**
     * Position of the spotLight in tests with Multiple light sources on triangle
     */
    private final Point trianglesLightPosition1 = new Point(-30, 50, -50);
    /**
     * Position of the PointLight in tests with Multiple light sources on triangle
     */
    private final Point trianglesLightPosition2 = new Point(0, -10, 0);

    /**
     * Direction of the DirectionalLight in tests with triangles
     */
    private final Vector trianglesLightDirection = new Vector(-2, -2, -2);
    //lights direction for the Multiple light sources in tests with triangles
    /**
     * DirectionLight direction in tests with Multiple light sources on triangle
     */
    private final Vector trianglesLightDirection1 = new Vector(2, 2, -1);
    /**
     * spotLight direction in tests with Multiple light sources on triangle
     */
    private final Vector trianglesLightDirection2 = new Vector(1, 1, -1);


    /**
     * The sphere in appropriate tests
     */
    private final Geometry sphere = new Sphere(sphereCenter, SPHERE_RADIUS)
            .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
    /**
     * The first triangle in appropriate tests
     */
    private final Geometry triangle1 = new Triangle(vertices[0], vertices[1], vertices[2])
            .setMaterial(material);
    /**
     * The second triangle in appropriate tests
     */
    private final Geometry triangle2 = new Triangle(vertices[0], vertices[1], vertices[3])
            .setMaterial(material);

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(sphereLightColor, sphereLightDirection));

        camera1.setImageWriter(new ImageWriter("lightSphereDirectional", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
                .setKl(0.001).setKq(0.0002));

        camera1.setImageWriter(new ImageWriter("lightSpherePoint", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
                .setKl(0.001).setKq(0.0001));

        camera1.setImageWriter(new ImageWriter("lightSphereSpot", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

        camera2.setImageWriter(new ImageWriter("lightTrianglesDirectional", 500, 500)) //
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
                .setKl(0.001).setKq(0.0002));

        camera2.setImageWriter(new ImageWriter("lightTrianglesPoint", 500, 500)) //
                .build() //
                .renderImage(antiAliasingLevel) //
                .writeToImage(); //
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKl(0.001).setKq(0.0001));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpot", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    @Test
    public void MultipleLightSourcesSphere() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(sphereDirectionalLightColor, sphereDirectionLightDirection));
        scene1.lights.add(new PointLight(spherePointLightColor, spherePointLightPosition).setKl(0.001).setKq(0.0002));
        scene1.lights.add(new SpotLight(sphereSpotLightColor, sphereSpotLightPosition, sphereSpotLightDirection).setKl(0.001).setKq(0.0001));

        camera1.setImageWriter(new ImageWriter("multiLightSphere", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();

    }

    @Test
    public void MultipleLightSourcesTriangles() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(triangleDirectionalLightColor, trianglesLightDirection1));
        scene2.lights.add(new PointLight(trianglePointLightColor, trianglesLightPosition1).setKl(0.0005).setKq(0.003));
        scene2.lights.add(new SpotLight(triangleSpotLightColor, trianglesLightPosition2, trianglesLightDirection2)
                .setKl(0.001).setKq(0.0001));


        camera2.setImageWriter(new ImageWriter("multiLightTriangles", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    //Bonus- tests for spot light on sphere with a narrower skin beam

    /**
     * Produce a picture of a sphere lighted by a spotlight with a narrower skin beam
     */
    @Test
    public void sphereNarrowerSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, sphereLightDirection)
                .setKl(0.001).setKq(0.0001).setNarrowness(10));

        camera1.setImageWriter(new ImageWriter("lightSphereSpotWithNarrower", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight with a narrower skin beam
     */
    @Test
    public void trianglesNarrowerSpot() {
        scene2.geometries.add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
                .setKl(0.001).setKq(0.0001).setNarrowness(10));

        camera2.setImageWriter(new ImageWriter("lightTrianglesSpotWithNarrower", 500, 500))
                .build()
                .renderImage(antiAliasingLevel)
                .writeToImage();
    }

}
