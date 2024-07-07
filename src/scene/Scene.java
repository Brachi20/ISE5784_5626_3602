package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String name;
    public Color background= Color.BLACK;
    public AmbientLight ambientLight =AmbientLight.NONE;
    public Geometries geometries=new Geometries();
    public List<LightSource> lights=new LinkedList<>();

    public Scene(String name){
        this.name=name;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene addGeometries(Geometries geometries) {
        this.geometries.add(geometries);
        return this;
    }

    /**
     * Adds a light source to the scene
     * @param light the light source to add
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
