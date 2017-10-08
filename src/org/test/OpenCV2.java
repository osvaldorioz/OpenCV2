/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.test;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import org.opencv.core.Core;
import org.opencv.core.Mat;
 
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.test.bean.PatternBean;
 
/**
*
* @author OR501695
* https://programarfacil.com/blog/vision-artificial/detector-de-bordes-canny-opencv/
*/
public class OpenCV2 {
    //C:/Temp/Ejemplo_KDD/Cheques
 
    public OpenCV2(){
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
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
   
    /**
     * Detección de bordes con Sobel
     * @param input
     * @param output
     */   
    public void detectorBordesCanny(String input, String output, Boolean gaussian){
        Size s = new Size(3,3);
        //min_threshold: es el umbral mínimo y
        //máximo en la umbralización por histéresis
        int min_threshold=50;
        int ratio = 4;
       
        Mat source = Imgcodecs.imread(input,  Imgcodecs.CV_LOAD_IMAGE_COLOR);
           
        Mat gris  = new Mat(source.width(),source.height(),source.type());
        Mat blur  = new Mat(source.width(),source.height(),source.type());
        Mat canny = new Mat(source.width(),source.height(),source.type());
       
        //Convertir a escala de grises
        Imgproc.cvtColor(source, gris, Imgproc.COLOR_RGB2GRAY);       
        
        //Filtrado de ruido
        if(gaussian){
            Imgproc.GaussianBlur(gris, blur, s, 0);       
        } else {
            Imgproc.blur(gris, blur, s); 
        }
        Imgproc.Canny(blur, canny, min_threshold,min_threshold*ratio);
        Imgcodecs.imwrite(output, canny);
    }
   
    /**
    * gaussianBlur: es la imagen desenfocada resultante.
    * @param input: es la imagen original, la que queremos suavizar o desenfocar.
    * @param output: es la imagen resultante.
    */
    public void gaussianBlur(String input, String output){
        //n X n: es el tamaño del kernel o máscara de convolución. Debe ser impar.
        Size s = new Size(3,3);     
        
        Mat source = Imgcodecs.imread(input,  Imgcodecs.CV_LOAD_IMAGE_COLOR);
           
        Mat gris=new Mat(source.width(),source.height(),source.type());
        Mat gaussian=new Mat(source.width(),source.height(),source.type());
      
        //Convertir a escala de grises
        Imgproc.cvtColor(source, gris, Imgproc.COLOR_RGB2GRAY);       
        
        //Filtro Gaussiano de ruido
        //sigma (σ) representa la desviación estándar en el eje X es decir,
        //la anchura de la campana de Gauss. Si ponemos un 0,
        //OpenCV se encargará automáticamente de calcular ese valor para
        //el kernel o máscara que hemos elegido. Esta es la opción aconsejable.
        Imgproc.GaussianBlur(gris, gaussian, s, 0);
        Imgcodecs.imwrite(output, gaussian);  
        
    }
   
    
    
    public void dilatarImagen(String sourceImg, String outputImg){
        try{               
            Mat source = Imgcodecs.imread(sourceImg, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);                 
            Mat destination = new Mat(source.rows(),source.cols(),source.type());
 
            destination = source;
 
            int erosion_size = 5;
            int dilation_size = 5;
            destination = source;
        
            Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dilation_size + 1, 2*dilation_size+1));
            Imgproc.dilate(source, destination, element1);
            Imgcodecs.imwrite(outputImg, destination);
        
        }catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
   
    public void erosionarImagen2(String input, String output){
        //COLOR_RGB2GRAY
        Size s = new Size(3,3);
        //min_threshold: es el umbral mínimo y
        //máximo en la umbralización por histéresis
        int min_threshold=40;
        int ratio = 4;
       
        /*Scalar color = new Scalar(255,255,105);
        List<MatOfPoint> list = new ArrayList<>();
        int countourIdX = -1;
        int thickness = 1;
       
        int mode = Imgproc.RETR_EXTERNAL; //obtiene el contorno externo de un objeto
        int method = Imgproc.CHAIN_APPROX_SIMPLE; //eliminar los píxeles del contorno redundantes*/
       
        Mat source = Imgcodecs.imread(input);
        Mat gris  = new Mat(source.width(),source.height(),source.type());
        Mat blur  = new Mat(source.width(),source.height(),source.type());
        Mat canny = new Mat(source.width(),source.height(),source.type());
        Mat erode = new Mat(source.width(),source.height(),source.type());
      
        //Convertir a escala de grises
        Imgproc.cvtColor(source, gris, Imgproc.COLOR_RGB2GRAY);       
        Imgcodecs.imwrite(output + "1.jpg", gris);
       
        //Filtrado de ruido
        Imgproc.GaussianBlur(gris, blur, s, 0); 
        Imgcodecs.imwrite(output + "2.jpg", blur);
       
        Imgproc.Canny(blur, canny, min_threshold,min_threshold*ratio);
        Imgcodecs.imwrite(output + "3.jpg", canny);
       
        //Imgproc.MORPH_ELLIPSE = MORPH_OPEN
        //Imgproc.MORPH_RECT = MORPH_ERODE
        Mat ellipse = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,
                              new  Size(3,3));
       
        printEE(canny, erode, ellipse);
       
        Imgcodecs.imwrite(output + "4.jpg", erode);
   
    }
   
    
    public void printEE(Mat blur, Mat erode, Mat element){
        Imgproc.erode(blur, erode, element);
        System.out.println(erode.rows() + " " +
         erode.cols());
        /*for( int x = 0; x < erode.rows(); x++ ) {
            for( int y = 0; y < erode.cols(); y++ ) {
                byte[] data = new byte[erode.cols()];
                erode.get(x,y,data);
                int a = 1;
                for(byte b: data){
                    System.out.print(b+",");
                   
                    if(a%9==0){
                        System.out.println();
                    }
                    a++;
                }
            }
        }
       
        /*for( int x = 0; x < element.rows(); x++ ) {
            for( int y = 0; y < element.cols(); y++ ) {
                byte[] data = new byte[9];
                element.get(x,y,data);
                int a = 1;
                for(byte b: data){
                    System.out.print(b+",");
                   
                    if(a%9==0){
                        System.out.println();
                    }
                    a++;
                }
            }
        }*/
    }
   
    public void escribirCSV(String output, Integer[][] realPatterns,
                            Integer[][] syntheticPositive,
                            Integer[][] syntheticNegative){
        String ruta = output + "resultados.csv";
        File archivo = new File(ruta);
        BufferedWriter bw = null;
        String temp = "";
        int counter = 1;
        try{
            bw = new BufferedWriter(new FileWriter(archivo));
           
            temp = "Firma,EE1,EE2,EE3,EE4,EE5,EE6,EE7,EE8,EE9,EE10," +
                   "EE11,EE12,EE13,EE14,EE15,EE16,EE17,EE18,EE19,EE20," +
                   "EE21,EE22,EE23,EE24,EE25,EE26,EE27,EE28,EE29,EE30," +
                   "EE31,EE32\n";
                   //"EE33,EE34,EE35,EE36,EE37,EE38,EE39,EE40," +
                   //"EE41,EE42,EE43,EE44,EE45,EE46,EE47,EE48,EE49,EE50," +
                   //"EE51,EE52,EE53,EE54\n";
            bw.write(temp);
            for(int x = 0; x < realPatterns.length; x++){
                temp = "RealPattern,";
                for(int y = 0; y < realPatterns[x].length; y++){
                    temp += realPatterns[x][y] + ",";
                }
                temp = temp.substring(0, temp.length()-1);
                temp = temp + "\n";
                bw.write(temp);
                counter++;
            }
       
            for(int x = 0; x < 50; x++){
                temp = "SynthPos,";
                for(int y = 0; y < syntheticPositive[x].length; y++){
                    temp += syntheticPositive[x][y] + ",";
                }
                temp = temp.substring(0, temp.length()-1);
                temp = temp + "\n";
                bw.write(temp);
                counter++;
            }
       
            for(int x = 0; x < 50; x++){
                temp = "SynthNeg,";
                for(int y = 0; y < syntheticNegative[x].length; y++){
                    temp += syntheticNegative[x][y] + ",";
                }
                temp = temp.substring(0, temp.length()-1);
                temp = temp + "\n";
                bw.write(temp);
                counter++;
            }
           
            bw.flush();
            bw.close();
           
            System.out.println("El archivo resultados.csv se generó correctamente." );
        }catch(FileNotFoundException err){
            err.printStackTrace();
        }catch(IOException err){
            err.printStackTrace();
        }
    }
   
    public Mat watershedAndDistanceTransform(Mat src){
        byte[] vec = new byte[3];
        for( int x = 0; x < src.rows(); x++ ) {
            for( int y = 0; y < src.cols(); y++ ) {
                src.get(x, y, vec);
               
                int pos = 0;
                for(Byte b: vec){
                    Integer i = new Integer(b);
                    //System.out.print(i + ",");
                    switch(i){
                        case 0:
                            vec[pos] = -1;
                            break;
                        case -1:
                            vec[pos] = 0;
                            break;
                    }
                    pos++;
                }            
                src.put(x, y, vec);  
                //System.out.println();
            }
        }
        return src;
    }
   
    public int binarizarImagen(String input, String output, int cuadrante,
                               int sizeDorE, int morphType, Boolean erosionar){
        int min_threshold=95;
        int max_threshold=200;
      
        Size s = new Size(3,3);
        Integer sumrow = 0;
      
        Random r = new Random();
        TreeSet<Integer> set = new TreeSet<>();
       
        File f = new File(input);
        String prefijo = f.getName();
        int pos = prefijo.indexOf(".");
        prefijo = prefijo.substring(0, pos);
        try{                           
            Mat source = Imgcodecs.imread(input,  Imgcodecs.CV_LOAD_IMAGE_COLOR);
            Mat gris  = new Mat(source.width(),source.height(),source.type());
            Mat blur  = new Mat(source.width(),source.height(),source.type());
            Mat canny = new Mat(source.width(),source.height(),source.type());
            Mat erosion = new Mat(source.rows(),source.cols(),source.type());
            Mat dilatation = new Mat(source.rows(),source.cols(),source.type());
           
            //Convertir a escala de grises
            Imgproc.cvtColor(source, gris, Imgproc.COLOR_RGB2GRAY);       
            Imgcodecs.imwrite(output + prefijo + "_gris.png", gris);
            
            //Filtrado de ruido
            Imgproc.GaussianBlur(gris, blur, s, 0); 
            Imgcodecs.imwrite(output + prefijo + "_blur.png", blur);
            
            if(erosionar){
                Mat element = Imgproc.getStructuringElement(morphType,
                          new Size(sizeDorE, sizeDorE));
           
                Imgproc.erode(blur, erosion, element);
                Imgcodecs.imwrite(output + prefijo + "_erosion.png", erosion);
                source = erosion;
            } else {
                Mat element1 = Imgproc.getStructuringElement(morphType,
                        new Size(sizeDorE, sizeDorE));
                Imgproc.dilate(blur, dilatation, element1);
                Imgcodecs.imwrite(output + prefijo + "_dilate.png", dilatation);
                source = dilatation;
            }
            Imgproc.Canny(source, canny, min_threshold,max_threshold);
           
            Imgcodecs.imwrite(output + prefijo + "_canny.png", canny);
       
            source = canny.clone();
           
            List<Integer> goodRow = new ArrayList<>();
            for(int rr = 0; rr < source.rows(); rr++){
                Boolean haveOne = false;
                byte[] b = new byte[source.cols()];
                source.get(rr, 0, b);
             
                for(byte bb: b){
                    int xx = bb * -1;
                    if(xx == 1 && !haveOne){
                        haveOne = true;
                    }
                    System.out.print(xx);
                }
                if(haveOne){
                    goodRow.add(rr);
                }
                System.out.println();
            }
            int iniRow = 0;
            int finRow = 0;
            int iniCol = 0;
            int rr = goodRow.size();
            int cc = source.cols();
            int temp = cc / 2;
            cc = cc - temp;
            temp = rr / 2;
            rr = rr - temp;
           
            /*     |
               1   |   0
            _______|_______
                   |
               2   |   3
                   |
            */
            switch(cuadrante){
                case 0:
                    iniRow = 0;
                    finRow = rr;
                    iniCol = cc;                         
                    break;
                case 1:
                    iniRow = 0;
                    finRow = rr;
                    iniCol = 0; 
                    break;
                case 2:
                    iniRow = rr;
                    finRow = goodRow.size();
                    iniCol = 0; 
                    break;
                case 3:
                    iniRow = rr;
                    finRow = goodRow.size();
                    iniCol = cc; 
                    break;
            }
           
            for(int i = iniRow; i < finRow; i++){
                byte[] b = new byte[cc];
                int suma = 0;
                source.get(i, iniCol, b);
                for(byte bb: b){                    
                    int xx = bb * -1;
                    suma += xx;
                }               
                sumrow += suma;
            }
         
        }catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
        return sumrow;
    }
   
    public List<PatternBean> getPatterns(){
        List<PatternBean> list = new ArrayList<>();
        PatternBean
        //Dilatación
        pb = new PatternBean("0 F 2 3");            
        list.add(pb);
        pb = new PatternBean("1 F 2 3");            
        list.add(pb);
        pb = new PatternBean("2 F 2 3");              
        list.add(pb);       
        pb = new PatternBean("3 F 2 3");           
        list.add(pb);       
        pb = new PatternBean("0 F 0 3");            
        list.add(pb);
        pb = new PatternBean("1 F 0 3");            
        list.add(pb);
        pb = new PatternBean("2 F 0 3");              
        list.add(pb);       
        pb = new PatternBean("3 F 0 3");           
        list.add(pb);       
        
        //Erosión
        pb = new PatternBean("0 T 2 3");            
        list.add(pb);
        pb = new PatternBean("1 T 2 3");            
        list.add(pb);
        pb = new PatternBean("2 T 2 3");              
        list.add(pb);       
        pb = new PatternBean("3 T 2 3");           
        list.add(pb);       
        pb = new PatternBean("0 T 0 3");            
        list.add(pb);
        pb = new PatternBean("1 T 0 3");            
        list.add(pb);
        pb = new PatternBean("2 T 0 3");              
        list.add(pb);       
        pb = new PatternBean("3 T 0 3");           
        list.add(pb);     
        
        pb = new PatternBean("0 T 2 6");            
        list.add(pb);
        pb = new PatternBean("1 T 2 6");            
        list.add(pb);
        pb = new PatternBean("2 T 2 6");              
        list.add(pb);       
        pb = new PatternBean("3 T 2 6");           
        list.add(pb);       
        pb = new PatternBean("0 T 0 6");            
        list.add(pb);
        pb = new PatternBean("1 T 0 6");            
        list.add(pb);
        pb = new PatternBean("2 T 0 6");              
        list.add(pb);       
        pb = new PatternBean("3 T 0 6");           
        list.add(pb);     
        
        pb = new PatternBean("0 T 2 9");            
        list.add(pb);
        pb = new PatternBean("1 T 2 9");            
        list.add(pb);
        pb = new PatternBean("2 T 2 9");              
        list.add(pb);       
        pb = new PatternBean("3 T 2 9");           
        list.add(pb);       
        pb = new PatternBean("0 T 0 9");            
        list.add(pb);
        pb = new PatternBean("1 T 0 9");            
        list.add(pb);
        pb = new PatternBean("2 T 0 9");              
        list.add(pb);       
        pb = new PatternBean("3 T 0 9");           
        list.add(pb); 
        
        
        return list;
    }
   
    
    public static void main( String[] args ) {
        OpenCV2 ocv = new OpenCV2();
       
        String source = "C:/Users/Oswald/Pictures/f%.jpg";
        String output = "C:/Temp/";
        int nfiles = 16;
        List<PatternBean> list = ocv.getPatterns();
        Integer[][] realPatterns = new Integer[nfiles][list.size()];
        Integer[][] syntheticPositive = new Integer[50][list.size()];
        Integer[][] syntheticNegative = new Integer[50][list.size()];             
        
        for(int x = 0; x < nfiles; x++){
            String file = source.replace("%", (x+1)+"");
           
            for(int y = 0; y < 1; y++){//list.size(); y++){
                int total = 0;
                PatternBean pb = list.get(y);
                int morphType = pb.getMorphType();                  
                boolean erosion = pb.isErosion();
                int sizeDorE = pb.getSizeDorE();
                int cuadrante = pb.getCuadrante();
 
                total = ocv.binarizarImagen(file, output, cuadrante, sizeDorE, morphType, erosion);
                System.out.print("morphType--> " + morphType + "(" +Imgproc.MORPH_RECT+","+Imgproc.MORPH_ELLIPSE  + "). ");
                System.out.print("erosion----> " + erosion + ". ");
                System.out.print("sizeDorE---> " + sizeDorE + ". ");
                System.out.print("cuadrante--> " + cuadrante + ". ");
                System.out.print("Total------> " + total + "\n");   
                realPatterns[x][y] = total;
            }
        }
       
        for(int x = 0; x < 50; x++){
            int factor = 0;
            int aleat  = 0;
            for(int y = 0; y < list.size(); y++){
                int suma = 0;
                factor = new Random().nextInt(nfiles);               
                aleat = new Random().nextInt(300);        
                for(int p = 0; p < nfiles; p++){
                    suma += realPatterns[p][y];
                }
                double media = suma / nfiles;
                double desv = 0;
                desv += Math.pow(realPatterns[factor][y] - media, 2);
                desv = Math.sqrt(desv/nfiles);
                syntheticPositive[x][y] = new Double(desv + aleat).intValue();
            }
        }
       
        int r = 0;
        for(int x = 0; x < 50; x++){
            for(int y = 0; y < list.size(); y++){
                do{
                    r = new Random().nextInt(999);
                }while(r < 1);
                syntheticNegative[x][y] = r;
            }
        }
       
        System.out.println("Generando el archivo de salida...");
        ocv.escribirCSV(output, realPatterns, syntheticPositive, syntheticNegative);
       
        
    }   
}