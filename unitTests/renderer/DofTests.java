package renderer;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.*;

public class DofTests {

    Geometries geometries = new Geometries();
    Material material = new Material().setKs(0.7).setKd(0.4).setShininess(30);

    void addWalles(Color color, Material material, Point...points){
        for(int i =0; i < 7; i=(i + 2)){
            geometries.add(new Polygon(points[i], points[i +1], points[(i + 3) % 8], points[(i+2) % 8]).setEmission(color).setMaterial(material));
        }
        Color floorColor = new Color(220, 220, 220);
        geometries.add(new Polygon(points[1], points[3], points[5], points[7]).setEmission(floorColor).setMaterial(material));
        geometries.add(new Polygon(points[0], points[2], points[4], points[6]).setEmission(floorColor).setMaterial(material));
    }

    void addMirror(){
        geometries.add(new Polygon(
                new Point(339.99, -100, -80),
                new Point(339.99, -100, 0),
                new Point(339.99, -300, 0),
                new Point(339.99, -300, -80)).setEmission(new Color(GRAY))
                .setMaterial(new Material().setShininess(50).setKd(0.3).setKs(0.4).setKr(0.5)));
    }

    //creates all objects and returns the camera
    Camera createAll(){
        Color tableColor = new Color(217,148,98);

        addWalles(new Color(200, 247, 219),
                material,
                new Point(-100, 200, -100),
                new Point(-100, 200, 250),
                new Point(340, 200, -100),
                new Point(340, 200, 250),
                new Point(340, -540, -100),
                new Point(340, -540, 250),
                new Point(-100, -540, -100),
                new Point(-100, -540, 250));

        addMirror();
        createTablesAndChairs(new Point(-20, -400, -76.5));


        // teacher table
        geometries.add(new Polygon(
                new Point(80, 70, -66.5),
                new Point(160, 70, -66.5),
                new Point(160, 130, -66.5),
                new Point(80, 130, -66.5)).setEmission(tableColor).setMaterial(material));

        //teacher table legs
        createLegs(new Point(80, 70, -66.5),
                new Point(152, 70, -66.5),
                new Point(152, 124, -66.5),
                new Point(80, 124, -66.5), 8, 6, 36, tableColor);

        //ball
        geometries.add(new Sphere(20,new Point(120, 100, -46.5))
                .setEmission(new Color(234,63,247))
                .setMaterial(new Material().setKd(0.3).setKt(0.4).setKs(0.4).setShininess(500)));


        List<LightSource> list = new LinkedList<>();
        list.add(new PointLight(new Color(122, 68, 41), new Point(240, -100, 40)));
        list.add(new SpotLight(new Color(22, 68, 41), new Point(-100, -540, 50), new Vector(1,1,-1)));


        final Scene scene = new Scene.SceneBuilder("DOF picture").setBackground(new Color(WHITE))
                .setAmbientLight(new AmbientLight(new Color(BLACK), new Double3(0.15)))
                .setLights(list)
                .setGeometries(geometries).build();



        final Camera camera = new Camera(new Point(-40,-510, 0), new Vector(0.6, 1,-0.6),new Vector(0.6, 1,1.36/0.6))
                .setVPSize(400, 400).setVPDistance(300)
                .setRayTracer(new RayTracerBasic(scene));

        return camera;
    }

    //add tables and chairs
    void createTablesAndChairs(Point point){
        for (int i = 0; i < 3; i++){
            Double3 coori = point.getCoordinate();
            Point myPointi;
            if (i != 0)
                myPointi = point.add(new Vector(0, i * 140, 0));
            else
                myPointi = point;
            for (int j = 0; j < 3; j++){
                Double3 coorj = myPointi.getCoordinate();
                Point myPointj;
                if(j != 0)
                    myPointj = myPointi.add(new Vector(j * 80, 0, 0));
                else
                    myPointj = myPointi;
                double x = myPointj.getCoordinate().getX();
                double y = myPointj.getCoordinate().getY();
                double z = myPointj.getCoordinate().getZ();
                geometries.add(new Polygon(myPointj,new Point(x + 40, y, z), new Point(x + 40, y + 40, z), new Point(x, y + 40, z)).setEmission(new Color(217,148,98)).setMaterial(material));
                createLegs(myPointj, new Point(x + 36, y, z), new Point(x + 36, y + 36, z), new Point(x, y + 36, z),4,4,26,new Color(217,148,98));

                geometries.add(new Polygon(new Point(x+10, y, z + 5), new Point(x+30, y, z+5), new Point(x+30, y,z-10), new Point(x+10, y, z-10)).setEmission(new Color(GRAY)));
                geometries.add(new Polygon(new Point(x+10, y, z-10), new Point(x+10, y+15, z-10), new Point(x+30, y+15, z-10), new Point(x+30, y, z-10)).setEmission(new Color(GRAY)));

                createLegs(new Point(x+10, y, z-10),  new Point(x+28, y,z-10),new Point(x+28, y+13.5, z-10),new Point(x+10, y +13.5, z-10),2,1.5,16, new Color(GRAY));

            }
        }
    }

    void createLegs(Point corner1, Point corner2, Point corner3, Point corner4, double i, double j, double k, Color color) {
        List<Point> corners = List.of(corner1, corner2, corner3, corner4);
        for (Point corner: corners) {
            Double3 coor = corner.getCoordinate();
            double x = coor.getX();
            double y = coor.getY();
            double z = coor.getZ();
            geometries.add(new Polygon(corner, new Point(x + i, y, z), new Point(x + i, y, z - k), new Point(x, y, z - k)).setEmission(new Color(217,148,98)).setMaterial(material).setEmission(color));

            corner = new Point(x + i, y, z);
            coor = corner.getCoordinate();
            x = coor.getX();
            y = coor.getY();
            z = coor.getZ();
            geometries.add(new Polygon(corner, new Point(x , y + j, z), new Point(x , y + j, z - k), new Point(x, y, z - k)).setEmission(new Color(217,148,98)).setMaterial(material).setEmission(color));

            corner = new Point(x , y + j, z);
            coor = corner.getCoordinate();
            x = coor.getX();
            y = coor.getY();
            z = coor.getZ();
            geometries.add(new Polygon(corner, new Point(x - i, y, z), new Point(x - i, y, z - k), new Point(x, y, z - k)).setEmission(new Color(217,148,98)).setMaterial(material).setEmission(color));

            corner = new Point(x - i, y, z);
            coor = corner.getCoordinate();
            x = coor.getX();
            y = coor.getY();
            z = coor.getZ();
            geometries.add(new Polygon(corner, new Point(x, y - j, z), new Point(x, y - j, z - k), new Point(x, y, z - k)).setEmission(new Color(217,148,98)).setMaterial(material).setEmission(color));
        }
    }

    @Test
    void withoutDofTest(){
        Camera camera = createAll();
        camera.setImageWriter(new ImageWriter("without dof", 2000, 2000)) //
                .renderImage() //
                .writeToImage();

    }

    @Test
    void withDofTest(){
        Camera camera = createAll();
        double dof = camera.getDofByPoint(new Point(120,  100, -46.5));
        camera.setGridParams(6)
                .setAperture(5)
                .setDepthOfField(dof)
                .setImageWriter(new ImageWriter("with dof", 1000, 1000))
                .renderImageWithDepthOfField()
                .writeToImage();
    }
}
