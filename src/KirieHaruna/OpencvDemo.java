package KirieHaruna;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

public class OpencvDemo {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //注意程序运行的时候需要在VM option添加该行 指明opencv的dll文件所在路径
        //-Djava.library.path=$PROJECT_DIR$\opencv\x64
    }
    public static void main(String[] args){
        Mat mat = Imgcodecs.imread("D:/4101151.jpg");
        System.out.println(mat);
    }

}
