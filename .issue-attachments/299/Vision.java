 

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
//import org.opencv.highgui.Highgui;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {
    public static VideoCapture videoCapture;
//    private static String url = "http://raspberrypi.local:1180/?action=stream?dummy=param.mjpeg";
    private static String url = "http://localhost:1180/?action=stream&dummy=param.mjpg";
    private static Mat image;

    public static void main(String[] args) {
        System.out.println("Raspberry Pi Vision Code Starting   ...");
        try {
            System.load("/home/pi/opencv-3.1.0/build/lib/libopencv_java310.so");
            System.load("/home/pi/networktables/lib/libntcore.so");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library faild to load.\n" + e);
            System.exit(1);
        }
//      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Opening Camera - "+url);
        videoCapture = new VideoCapture();
        videoCapture.open(url);
//        videoCapture.open(0);

        if (!videoCapture.isOpened()) {
            System.out.println("Failed to open camera!");
            return;
        }
        
        videoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        videoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        
        NetworkTable.setClientMode();
        NetworkTable.setIPAddress("roboRIO-4453-FRC.local");
        NetworkTable subtable = NetworkTable.getTable("/GRIP/Stronghold");

        int count = 0;
        while (!subtable.isConnected() && count++ < 100) {
            System.out.println("Waiting for NetworkTable Server!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (!subtable.isConnected()) {
            System.out.println("Failed to connect to NetworkTable!");
            return;
        }

        image = new Mat();
        boolean keepRunning = true;
        while (keepRunning) {
            if (!videoCapture.isOpened()) {
                System.out.println("Camera was closed on us!");
                keepRunning = false;
            }

            videoCapture.read(image);
            if (image.empty()) {
                continue;
            }
            System.out.println("Retrieved new image ...");
            // image = Highgui.imread("test.jpg");
            Mat HSVimage = new Mat();
            Imgproc.cvtColor(image, HSVimage, Imgproc.COLOR_RGB2HSV);

            Mat filteredImage = new Mat();
            Core.inRange(HSVimage, new Scalar(50, 0, 0), new Scalar(130, 255, 255), filteredImage);

            // Highgui.imwrite("filt.jpg", filteredImage);

            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Mat hierarchy = new Mat();

            Imgproc.findContours(filteredImage, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

            HeightWidth bestrect = new HeightWidth(0, 0, 0, 0);

            // Mat filteredContoursImage = image.clone();
            for (int i = 0; i < contours.size(); i++) {
                MatOfPoint2f points = new MatOfPoint2f();
                points.fromList(contours.get(i).toList());
                RotatedRect rotatedrect = Imgproc.minAreaRect(points);
                Rect uprightrect = Imgproc.boundingRect(contours.get(i));
                // if(rect.size.area() >= 1000.0)
                // {
                // System.out.println("Rect found: ");
                // System.out.print(rect.angle); System.out.println("
                // degrees.");
                // System.out.print(rect.size.width); System.out.println("
                // width.");
                // System.out.print(rect.size.height); System.out.println("
                // height.");
                // Imgproc.drawContours(filteredContoursImage, contours, i, new
                // Scalar(0,255,0), 5);
                // Point[] rectpoints = new Point[4];
                // rect.points(rectpoints);
                // for(int j = 0; j < 4; j++)
                // {
                // Core.line(filteredContoursImage, rectpoints[j],
                // rectpoints[(j+1) % 4], new Scalar(255, 0, 0), 5);
                // }

                // Rect uprightrect = Imgproc.boundingRect(contours.get(i));

                // Core.rectangle(filteredContoursImage, uprightrect.tl(),
                // uprightrect.br(), new Scalar(0,128,128), 5);
                // }
                // else
                // {
                // Imgproc.drawContours(filteredContoursImage, contours, i, new
                // Scalar(0,0,255), 5);
                // }

                HeightWidth rect = new HeightWidth(rotatedrect.size.height, uprightrect.width,
                        uprightrect.x + uprightrect.width / 2, rotatedrect.center.y);
                if (rect.width[0] > bestrect.width[0]) {
                    bestrect = rect;
                }

            }
/*
            subtable.putNumberArray("width", bestrect.width);
            subtable.putNumberArray("height", bestrect.height);
            subtable.putNumberArray("centerX", bestrect.centerX);
            subtable.putNumberArray("centerY", bestrect.centerY);
*/
            System.out.println("\twidth:   " + bestrect.width[0]);
            System.out.println("\theight:  " + bestrect.height[0]);
            System.out.println("\tcenterX: " + bestrect.centerX[0]);
            System.out.println("\tcenterY: " + bestrect.centerY[0]);

//            NetworkTable.flush();

            // Highgui.imwrite("contours.jpg", filteredContoursImage);

        }

//        NetworkTable.shutdown();

        return;
    }

}
