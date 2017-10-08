/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author OR501695
 */
public class Segmentacion {
    public Segmentacion(){
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    }
    
    public static void showResult(Mat img) {
        System.out.println("showResult");
        Imgproc.resize(img, img, new Size(640, 480));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            JFrame frame = new JFrame();
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void watershedAndDistanceTransform(String input, String output){
        Mat src = Imgcodecs.imread(input);
        //showResult(src);
        Mat laplacian = new Mat(src.width(),src.height(),src.type());
        Mat sharp = new Mat(src.width(),src.height(),src.type());
        Mat imgResult = new Mat(src.width(),src.height(),src.type());
        
        byte[] vec = new byte[3];
        for( int x = 0; x < src.rows(); x++ ) {
            for( int y = 0; y < src.cols(); y++ ) {
                src.get(x, y, vec);
                System.out.println(vec[0]+","+vec[1]+","+vec[2]);
                if( vec[0] == -1 &&
                    vec[1] == -1 &&
                    vec[2] == -1) {
                    vec = new byte[]{0,0,0};
                    src.put(x, y, vec);                    
                }
            }
        }
        
        showResult(src);
        /*
        Mat kernel = new Mat(3,3, CvType.CV_32F){
            {
                put(1, 1, 1);
                put(1,-8, 1);
                put(1, 1, 1);
            }
        };     
        
        Imgproc.filter2D(src, laplacian, CvType.CV_32F, kernel);
        
        //showResult(laplacian);
        
        src.convertTo(sharp, CvType.CV_32F);
        
        Core.subtract(sharp, laplacian, imgResult);
        
        //showResult(imgResult);
        
        laplacian.convertTo(laplacian, CvType.CV_8UC3);
        imgResult.convertTo(imgResult, CvType.CV_8UC3);
        
        src = imgResult;
        
        Mat bw = new Mat(src.width(),src.height(),src.type());
        
        //Convertir a escala de grises
        Imgproc.cvtColor(src, bw, Imgproc.COLOR_BGR2GRAY);   
        
        System.out.println(Imgproc.THRESH_BINARY + " " +
                           Imgproc.THRESH_OTSU   + " " + 
                          (Imgproc.THRESH_OTSU | Imgproc.THRESH_BINARY));
        Imgproc.threshold(bw, bw, 40, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        
        //showResult(bw);
        
        Mat dist = new Mat(src.width(),src.height(),src.type());
        
        Imgproc.distanceTransform(bw, dist,Imgproc.DIST_L2,3);
        
        //showResult(dist);
        
        Imgproc.threshold(dist, dist, 0.4, 1.0, Imgproc.THRESH_BINARY);
        
        //showResult(dist);
        
        Mat kernel1 = Mat.ones(3, 3, CvType.CV_8UC1);   
        
        Imgproc.dilate(dist, dist, kernel1);
        
        showResult(dist);
        
        Mat dist_8u = new Mat(src.width(),src.height(),src.type());
        dist.convertTo(dist_8u, CvType.CV_8U);
        
        int mode = Imgproc.RETR_EXTERNAL; //obtiene el contorno externo de un objeto
        int method = Imgproc.CHAIN_APPROX_SIMPLE;
        
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat(dist_8u.width(),dist_8u.height(),dist_8u.type());
        Imgproc.findContours(dist_8u, contours, hierarchy, mode, method);
        
        Mat markers = Mat.zeros(dist.size(), CvType.CV_32SC1);
        
        Scalar color = new Scalar(255,255,105);
        Point center = new Point(5,5);
        int countourIdX = -1;
        for (int i = 0; i < contours.size(); i++){
            Imgproc.drawContours(markers, contours, countourIdX, color, -1);
        }
        
        Imgproc.circle(markers, center, 3, color, -1);
        Imgproc.watershed(src, markers);
        Mat mark = Mat.zeros(markers.size(), CvType.CV_8UC1);
        markers.convertTo(mark, CvType.CV_8UC1);
        */
        
        
    }
    /**
     * El objetivo de esta fase es analizar todos los bordes
     * detectados y comprobar si son contornos o no.
     * @param input 
     */
    public void buscarContornos(String input, String output){
        Size s = new Size(3,3);
        //min_threshold: es el umbral mínimo y 
        //máximo en la umbralización por histéresis
        int min_threshold=40;
        int ratio = 4;
        
        Scalar color = new Scalar(255,255,105);
        List<MatOfPoint> list = new ArrayList<>();
        int countourIdX = -1;
        int thickness = 1;
        
        int mode = Imgproc.RETR_EXTERNAL; //obtiene el contorno externo de un objeto
        int method = Imgproc.CHAIN_APPROX_SIMPLE; //eliminar los píxeles del contorno redundantes
        
        Mat source = Imgcodecs.imread(input);
        Mat gris  = new Mat(source.width(),source.height(),source.type());
        Mat blur  = new Mat(source.width(),source.height(),source.type());
        Mat canny = new Mat(source.width(),source.height(),source.type());
        Mat hierarchy = new Mat(source.width(),source.height(),source.type());
        Mat temp = new Mat(source.width(),source.height(),source.type());
        
        //Convertir a escala de grises
        Imgproc.cvtColor(source, gris, Imgproc.COLOR_RGB2GRAY);        
        Imgcodecs.imwrite(output + "1.jpg", gris);
        
        //Filtrado de ruido
        Imgproc.blur(gris, blur, s);  
        Imgcodecs.imwrite(output + "2.jpg", blur);
        
        Imgproc.Canny(blur, canny, min_threshold,min_threshold*ratio);
        Imgcodecs.imwrite(output + "3.jpg", canny);
        
        Imgproc.findContours(canny, list, hierarchy, mode, method);
        System.out.println(list.size());
        Imgproc.drawContours(temp, list, countourIdX, color, thickness);
        Imgcodecs.imwrite(output + "4.jpg", temp);
    }
    
    public void _main(String... args){
        Segmentacion s = new Segmentacion();
        
        String source = "C:/Temp/f1_blur.png";
        String output = "C:\\Users\\OR501695\\Pictures\\dilation.jpg";
        
        s.watershedAndDistanceTransform(source, output);
    }
}
