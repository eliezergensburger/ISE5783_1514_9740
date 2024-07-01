package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Polygon;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;

public class OurPictureTest {

    private final Material material = new Material().setKd(0.5).setKs(0.5).setShininess(30);

    private final Intersectable mirror = new Polygon(new Point(45, 50, 0), new Point(-15, 50, 0), new Point(-15, 50, -107), new Point(45, 50, -107))
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKr(0.5));

    private final Intersectable table1 = new Polygon(new Point(20, 20, -62.5), new Point(20, -20, -62.5),
            new Point(-20, -20, -62.5), new Point(-20, 20, -62.5)).setEmission(new Color(RED)) //
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.4));

    private final Intersectable ball1 = new Sphere(12.5, new Point(0, 0, -50))
            .setEmission(new Color(BLUE))
            .setMaterial(material.setKt(0.5));
    private final Intersectable ball2 = new Sphere(18d, new Point(0, 0, -85))
            .setEmission(new Color(234, 63, 247))
            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.4));
    private final Intersectable leg1_1 = new Polygon(new Point(20, 20, -62.5), new Point(16, 20, -62.5), new Point(16, 20, -102.5), new Point(20, 20, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg1_2 = new Polygon(new Point(16, 20, -62.5), new Point(16, 16, -62.5), new Point(16, 16, -102.5), new Point(16, 20, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg1_3 = new Polygon(new Point(16, 16, -62.5), new Point(20, 16, -62.5), new Point(20, 16, -102.5), new Point(16, 16, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg1_4 = new Polygon(new Point(20, 16, -62.5), new Point(20, 20, -62.5), new Point(20, 20, -102.5), new Point(20, 16, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg2_1 = new Polygon(new Point(-20, 20, -62.5), new Point(-16, 20, -62.5), new Point(-16, 20, -102.5), new Point(-20, 20, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg2_2 = new Polygon(new Point(-16, 20, -62.5), new Point(-16, 16, -62.5), new Point(-16, 16, -102.5), new Point(-16, 20, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg2_3 = new Polygon(new Point(-16, 16, -62.5), new Point(-20, 16, -62.5), new Point(-20, 16, -102.5), new Point(-16, 16, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg2_4 = new Polygon(new Point(-20, 16, -62.5), new Point(-20, 20, -62.5), new Point(-20, 20, -102.5), new Point(-20, 16, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg3_1 = new Polygon(new Point(-20, -20, -62.5), new Point(-16, -20, -62.5), new Point(-16, -20, -102.5), new Point(-20, -20, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg3_2 = new Polygon(new Point(-16, -20, -62.5), new Point(-16, -16, -62.5), new Point(-16, -16, -102.5), new Point(-16, -20, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg3_3 = new Polygon(new Point(-16, -16, -62.5), new Point(-20, -16, -62.5), new Point(-20, -16, -102.5), new Point(-16, -16, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg3_4 = new Polygon(new Point(-20, -16, -62.5), new Point(-20, -20, -62.5), new Point(-20, -20, -102.5), new Point(-20, -16, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg4_1 = new Polygon(new Point(20, -20, -62.5), new Point(16, -20, -62.5), new Point(16, -20, -102.5), new Point(20, -20, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg4_2 = new Polygon(new Point(16, -20, -62.5), new Point(16, -16, -62.5), new Point(16, -16, -102.5), new Point(16, -20, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg4_3 = new Polygon(new Point(16, -16, -62.5), new Point(20, -16, -62.5), new Point(20, -16, -102.5), new Point(16, -16, -102.5))
            .setEmission(new Color(RED));
    private final Intersectable leg4_4 = new Polygon(new Point(20, -16, -62.5), new Point(20, -20, -62.5), new Point(20, -20, -102.5), new Point(20, -16, -102.5))
            .setEmission(new Color(RED));
    private final Scene scene = new Scene.SceneBuilder("Our picture").setBackground(new Color(GRAY))
            .setAmbientLight(new AmbientLight(new Color(BLACK), new Double3(0.15)))
            .setLights(List.of(new SpotLight(new Color(PINK), new Point(100, 100, 75), new Vector(-1, -1, -1)),
                    //new DirectionalLight(new Color(WHITE), new Vector(0,1,0))
                    //new PointLight(new Color(234,63,247), new Point(0,0,-95)),
                    new SpotLight(new Color(115, 251, 253), new Point(0, 0, 0), new Vector(0, 0, -1))
            ))
            .setGeometries(new Geometries(List.of(mirror, table1, ball1, ball2, leg1_1, leg1_2, leg1_3, leg1_4, leg2_1, leg2_2, leg2_3, leg2_4, leg3_1, leg3_2, leg3_3, leg3_4, leg4_1, leg4_2, leg4_3, leg4_4))).build();
    private final Camera camera = new Camera(new Point(400, -1100, 250), new Vector(-0.4, 1, -0.3), new Vector(-0.1, 0.26, 1))
            .setVPSize(200, 200).setVPDistance(1000)
            .setRayTracer(new RayTracerBasic(scene));

    @Test
    public void ourTest() {

//        scene.lights.add(new PointLight(new Color(400, 240, 0), new Point(-100,-100,200)) //
//                .setKl(1E-5).setKq(1.5E-7));

        camera.setImageWriter(new ImageWriter("Our picture", 400, 400)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void ourTest2() {

//        scene.lights.add(new PointLight(new Color(400, 240, 0), new Point(-100,-100,200)) //
//                .setKl(1E-5).setKq(1.5E-7));

        camera.setImageWriter(new ImageWriter("Our picture2", 400, 400)) //
                .setAperture(3)
                .setDepthOfField(100)
                .setGridParams(10)
                .renderImageWithDepthOfField() //
                .writeToImage();
    }
}
