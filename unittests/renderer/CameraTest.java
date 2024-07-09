package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.YELLOW;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import scene.Scene;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
class CameraTest {
    /**
     * Camera builder for the tests
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(10);

    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {
        final String badRay = "Bad ray";

        // ============ Equivalence Partitions Tests ==============
        // EP01: 4X4 Inside (1,1)
        Camera camera1 = cameraBuilder.setVpSize(8, 8).build();
        assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
                camera1.constructRay(4, 4, 1, 1), badRay);

        // =============== Boundary Values Tests ==================
        // BV01: 4X4 Corner (0,0)
        assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
                camera1.constructRay(4, 4, 0, 0), badRay);

        // BV02: 4X4 Side (0,1)
        assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)),
                camera1.constructRay(4, 4, 1, 0), badRay);

        // BV03: 3X3 Center (1,1)
        Camera camera2 = cameraBuilder.setVpSize(6, 6).build();
        assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
                camera2.constructRay(3, 3, 1, 1), badRay);

        // BV04: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)),
                camera2.constructRay(3, 3, 1, 0), badRay);

        // BV05: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)),
                camera2.constructRay(3, 3, 0, 1), badRay);

        // BV06: 3X3 Corner (0,0)
        assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)),
                camera2.constructRay(3, 3, 0, 0), badRay);

    }


    @Test
    public void testLookAt() {

        // Create a test scene with entities in the scene
        Scene scene = new Scene("LookAt Test Scene");

        scene.geometries.add(
                new Sphere(new Point(0, 0, -100), 50d),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)) // down right
        );
        //Define pink ambient lighting and green background color
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
                .setBackground(new Color(75, 127, 90));

        final Camera.Builder cameraBuilder = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpDistance(100)
                .setVpSize(500, 500);


        // ============ Equivalence Partitions Tests ==============

        //TC01:Looking on the right side of the scene
        cameraBuilder.setImageWriter(new ImageWriter("LookAt Test01", 1000, 1000))
                .build()
                .lookAt(new Point(100, 0, -1000), new Vector(0, 1, 0))
                .renderImage()
                .printGrid(100, new Color(YELLOW))
                .writeToImage();

        //TC02:Looking on the left side of the scene
        cameraBuilder.setImageWriter(new ImageWriter("LookAt Test02", 1000, 1000))
                .build()
                .lookAt(new Point(-100, 0, -1000), new Vector(0, 1, 0))
                .renderImage()
                .printGrid(100, new Color(YELLOW))
                .writeToImage();

        //TC03:Looking on the top side of the scene
        cameraBuilder.setImageWriter(new ImageWriter("LookAt Test03", 1000, 1000))
                .build()
                .lookAt(new Point(0, 0, -1000), new Vector(0, -1, 0))
                .renderImage()
                .printGrid(100, new Color(YELLOW))
                .writeToImage();


    }


}
