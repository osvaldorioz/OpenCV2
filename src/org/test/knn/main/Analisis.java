/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test.knn.main;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.ml.KNearest;

/**
 *
 * @author Oswald
 */
public class Analisis {
    byte[] header = new byte[16];
    Mat training = null;
    Mat training_labels = null;
    List<Mat> images = new ArrayList<Mat>();
    public Analisis(){
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    }
    
    public void guardarImagenes(String input){
        Mat source = Imgcodecs.imread(input);
        System.out.println(source.rows() + "*" + source.cols() + "=" + (source.rows() * source.cols()));
        images.add(source);
    }
    
    public void extraerDimensiones(){
        int r = images.get(0).rows();
        int c = images.get(0).cols();
        training = new Mat(images.size(), (r*c), CvType.CV_8U);
        
        int i = 0;
        for(Mat mat: images){
            int pxls = mat.rows()*mat.cols();
            byte[] img = new byte[pxls];
            mat.get(mat.rows(), mat.cols(), img);
            System.out.println(i);
            training.put(i++, 0, img);
        }
        training.convertTo(training, CvType.CV_32FC1);
    }
    
    public void extraerLabels(String label){
        
        byte[] labelsData = new byte[images.size()];
        
        training_labels = new Mat(images.size(), 1, CvType.CV_8U);
        Mat temp_labels = new Mat(1, images.size(), CvType.CV_8U);
        byte[] header = new byte[8];
               
        labelsData = label.getBytes();
        temp_labels.put(0, 0, labelsData);
        Core.transpose(temp_labels, training_labels);
        training_labels.convertTo(training_labels, CvType.CV_32FC1);
        
    }
    
    public void knn(){
        //KNearest k = new KNearest();
        
    }
    
    public void _main(String ... args){
        Analisis a = new Analisis();
        int nfiles = 13;
        String source = "C:/Temp/f%_erosion.png";
        
        for(int x = 0; x < nfiles; x++){
            String file = source.replace("%", (x+1)+"");
            a.guardarImagenes(file);
        }
        a.extraerDimensiones();
        String a1 = new String("Knn analisis");
        a.extraerLabels(a1);
        System.out.print(a1.getBytes().length);
    }
}
