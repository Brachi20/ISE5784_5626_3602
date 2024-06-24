package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    public String name;
    public Color background= Color.BLACK;
    public AmbientLight ambientLight=AmbientLight.NONE;
    public Geometries geometries=new Geometries();

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
}
