package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import org.junit.jupiter.api.Test;
import primitives.Point;

public class ImproveRunTimeTests {
    DofTests myTest = new DofTests();

    @Test
    void MultithreadingTest() {
        myTest.createAll();
        // without dof
        Camera camera1 = myTest.createCamera();
        long startTime1 = System.currentTimeMillis();
        camera1.setImageWriter(new ImageWriter("without dof improve run time", 100, 100)) //
                .renderImage()
                .writeToImage();


        //with dof
        double dof = camera1.getDofByPoint(new Point(120, 100, -46.5));
        camera1.setGridParams(6)
                .setAperture(5)
                .setDepthOfField(dof)
                .setImageWriter(new ImageWriter("with dof improve run time", 50, 50))
                .renderImageWithDepthOfField()
                .writeToImage();
        long endTime1 = System.currentTimeMillis();
        System.out.println("without multithreading took " + (endTime1 - startTime1) + " milliseconds\n");

        Camera camera2 = myTest.createCamera().setMultithreading(true);

        // without dof
        long startTime2 = System.currentTimeMillis();
        camera2.setImageWriter(new ImageWriter("without dof improve run time", 100, 100)) //
                .renderImage()
                .writeToImage();


        //with dof
        camera2.setGridParams(6)
                .setAperture(5)
                .setDepthOfField(dof)
                .setImageWriter(new ImageWriter("with dof improve run time", 50, 50))
                .renderImageWithDepthOfField()
                .writeToImage();
        long endTime = System.currentTimeMillis();
        System.out.println("with multithreading took " + (endTime - startTime2) + " milliseconds\n");
    }

    @Test
    void bvhTest() {
        myTest.createAll();
        Camera camera1 = myTest.createCamera();
        long startTime1 = System.currentTimeMillis();
        camera1.setImageWriter(new ImageWriter("without dof improve run time", 100, 100)) //
                .renderImage()
                .writeToImage();


        //with dof
        double dof = camera1.getDofByPoint(new Point(120, 100, -46.5));
        camera1.setGridParams(6)
                .setAperture(5)
                .setDepthOfField(dof)
                .setImageWriter(new ImageWriter("with dof improve run time", 50, 50))
                .renderImageWithDepthOfField()
                .writeToImage();
        long endTime1 = System.currentTimeMillis();
        System.out.println("without BVH took " + (endTime1 - startTime1) + " milliseconds\n");


        for (Intersectable intersectable : myTest.scene.geometries.getItems()) {
            intersectable.setBvhIsOn(true);
        }
        myTest.scene.geometries = Geometries.buildBVH(myTest.scene.geometries);
        Camera camera2 = myTest.createCamera();

        // without dof
        long startTime2 = System.currentTimeMillis();
        camera2.setImageWriter(new ImageWriter("without dof improve run time", 100, 100)) //
                .renderImage()
                .writeToImage();


        //with dof
        camera2.setGridParams(6)
                .setAperture(5)
                .setDepthOfField(dof)
                .setImageWriter(new ImageWriter("with dof improve run time", 50, 50))
                .renderImageWithDepthOfField()
                .writeToImage();
        long endTime = System.currentTimeMillis();
        System.out.println("with BVH took " + (endTime - startTime2) + " milliseconds\n");
    }

    @Test
    void bvhAndMultithreadingTest() {
        myTest.createAll();
        Camera camera1 = myTest.createCamera();
        long startTime1 = System.currentTimeMillis();
        camera1.setImageWriter(new ImageWriter("without dof improve run time", 100, 100)) //
                .renderImage()
                .writeToImage();


        //with dof
        double dof = camera1.getDofByPoint(new Point(120, 100, -46.5));
        camera1.setGridParams(6)
                .setAperture(5)
                .setDepthOfField(dof)
                .setImageWriter(new ImageWriter("with dof improve run time", 50, 50))
                .renderImageWithDepthOfField()
                .writeToImage();
        long endTime1 = System.currentTimeMillis();
        System.out.println("without BVH and multithreading took " + (endTime1 - startTime1) + " milliseconds\n");

        for (Intersectable intersectable : myTest.scene.geometries.getItems()) {
            intersectable.setBvhIsOn(true);
        }
        myTest.scene.geometries = Geometries.buildBVH(myTest.scene.geometries);
        Camera camera2 = myTest.createCamera().setMultithreading(true);

        // without dof
        long startTime2 = System.currentTimeMillis();
        camera2.setImageWriter(new ImageWriter("without dof improve run time", 100, 100)) //
                .renderImage()
                .writeToImage();


        //with dof
        camera2.setGridParams(6)
                .setAperture(5)
                .setDepthOfField(dof)
                .setImageWriter(new ImageWriter("with dof improve run time", 50, 50))
                .renderImageWithDepthOfField()
                .writeToImage();
        long endTime = System.currentTimeMillis();
        System.out.println("with BVH and multithreading took " + (endTime - startTime2) + " milliseconds\n");
    }
}

