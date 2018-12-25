package KirieHaruna;

import java.util.ArrayList;
import java.util.List;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class OpencvDemo {


    public static void main(String[] args) {
// Set image path
        String filename1 = "D:/3.jpg";
        String filename2 = "D:/2.jpg";


        double ret;
        ret = compareHistogram(filename1, filename2);
       // System.out.println(ret);

    }


    /**
     * Compare that two images is similar using histogram
     * @author minikim
     * @param filename1 - the first image
     * @param filename2 - the second image
     * @return integer - 1 if two images are similar, 0 if not
     */
    public static double compareHistogram(String filename1, String filename2) {
        double retVal = 0;

        long startTime = System.currentTimeMillis();

        System.load("C:\\Users\\Congyi Liu\\Desktop\\OpencvDemo\\opencv\\x64\\opencv_java400.dll");

// Load images to compare
        Mat img1 = Imgcodecs.imread(filename1, Imgcodecs.IMREAD_COLOR);
        Mat img2 = Imgcodecs.imread(filename2, Imgcodecs.IMREAD_COLOR);

        Mat hsvImg1 = new Mat();
        Mat hsvImg2 = new Mat();

// Convert to HSV
        Imgproc.cvtColor(img1, hsvImg1, Imgproc.COLOR_BGR2HSV);
        Imgproc.cvtColor(img2, hsvImg2, Imgproc.COLOR_BGR2HSV);

        // Set configuration for calchist()
        List<Mat> listImg1 = new ArrayList<Mat>();
        List<Mat> listImg2 = new ArrayList<Mat>();

        listImg1.add(hsvImg1);
        listImg2.add(hsvImg2);

        MatOfFloat ranges = new MatOfFloat(0,255);
        MatOfInt histSize = new MatOfInt(50);
        MatOfInt channels = new MatOfInt(0);


        // Histograms
        Mat histImg1 = new Mat();
        Mat histImg2 = new Mat();

        // Calculate the histogram for the HSV imgaes
        Imgproc.calcHist(listImg1, channels, new Mat(), histImg1, histSize, ranges);
        Imgproc.calcHist(listImg2, channels, new Mat(), histImg2, histSize, ranges);

        Core.normalize(histImg1, histImg1, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        Core.normalize(histImg2, histImg2, 0, 1, Core.NORM_MINMAX, -1, new Mat());


        // Apply the histogram comparison methods
        // 0 - correlation: the higher the metric, the more accurate the match "> 0.9"
        // 1 - chi-square: the lower the metric, the more accurate the match "< 0.1"
        // 2 - intersection: the higher the metric, the more accurate the match "> 1.5"
        // 3 - bhattacharyya: the lower the metric, the more accurate the match  "< 0.3"
        double result0, result1, result2, result3;
        result0 = Imgproc.compareHist(histImg1, histImg2, 0);
        result1 = Imgproc.compareHist(histImg1, histImg2, 1);
        result2 = Imgproc.compareHist(histImg1, histImg2, 2);
        result3 = Imgproc.compareHist(histImg1, histImg2, 3);

//        System.out.println("Method [0] " + result0);
//        System.out.println("Method [1] " + result1);
//        System.out.println("Method [2] " + result2);
//        System.out.println("Method [3] " + result3);

        // If the count that it is satisfied with the condition is over 3, two images is same.
        int count=0;
        if (result0 > 0.9) {
            count++;
            System.out.println("Method [0] = " + result0+">0.9, matched");
        }else {
            System.out.println("Method [0] = " + result0+"<0.9, not matched");
        }
        if (result1 < 0.1){
            count++;
            System.out.println("Method [1] = " + result1+"<0.1, matched");
        }else {
            System.out.println("Method [1] = " + result1+">0.1, not matched");
        }
        if (result2 > 1.5){
            count++;
            System.out.println("Method [2] = " + result2+">1.5, matched");
        }else {
            System.out.println("Method [2] = " + result2+"<1.5, not matched");
        }
        if (result3 < 0.3) {
            count++;
            System.out.println("Method [3] = " + result3+"<0.3, matched");
        }else {
            System.out.println("Method [3] = " + result3+">0.3, not matched");
        }
        if (count >= 3) retVal = 1;

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("estimatedTime=" + estimatedTime + "ms");

        return retVal;
    }
}