package Atomicity;

import lombok.SneakyThrows;
import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class DataStorage
{

    // Map representing hex value and its corresponding color
    public static Map<Character, CustomColors> hexToCharMap = new HashMap<>();

    public static String before_writing;
    public static String after_writing;

    static
    {
        // Assigning distinct colors to hexadecimal characters
        hexToCharMap.put('0', CustomColors.COLOR_0);
        hexToCharMap.put('1', CustomColors.COLOR_1);
        hexToCharMap.put('2', CustomColors.COLOR_2);
        hexToCharMap.put('3', CustomColors.COLOR_3);
        hexToCharMap.put('4', CustomColors.COLOR_4);
        hexToCharMap.put('5', CustomColors.COLOR_5);
        hexToCharMap.put('6', CustomColors.COLOR_6);
        hexToCharMap.put('7', CustomColors.COLOR_7);
        hexToCharMap.put('8', CustomColors.COLOR_8);
        hexToCharMap.put('9', CustomColors.COLOR_9);
        hexToCharMap.put('A', CustomColors.COLOR_10);
        hexToCharMap.put('B', CustomColors.COLOR_11);
        hexToCharMap.put('C', CustomColors.COLOR_12);
        hexToCharMap.put('D', CustomColors.COLOR_13);
        hexToCharMap.put('E', CustomColors.COLOR_14);
        hexToCharMap.put('F', CustomColors.COLOR_15);
    }



    class MyException extends Exception {
        public MyException() {}
        public MyException(String message) {
            super (message);
        }
    }

    public static boolean someSomeCondition;
    public static boolean someOtherCondition;



    public void foo() {
        // ... some code ...
        assert someSomeCondition;

        // ... some code ...
        assert someOtherCondition: "Some error occurred!";

        // ... some other code ...
    }


    public void hideExceptions(){
        try{
            foo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class CouldntDoSomethingException extends Exception {
        public CouldntDoSomethingException() {
            hideExceptions();
        }
    }



    @SuppressWarnings("ClassEscapesDefinedScope")

    public void bar()
            throws CouldntDoSomethingException, IOException {
        try{
            for (int i = 0; i < 12; i++) {
                try{
                    // ... some repetitive task ...
                    foo();
                } catch (MyException e){
                    // ... handling code ...
                } catch (IOException e){
                    // ... handling code ...
                    throw new Exception(e);
                } catch (Exception e){
                    // ... handling code ...
                    throw e;
                }
            }
        } catch (IOException e){
            // ... handling code ...
            System.out.println(e.getMessage());
            throw new IOException();
        } catch (Exception e){
            // ... handling code ...
            String className = e.getClass().getName();
            System.out.println(className);
        }
    }


    public static void main(String[] args) {









        //String hex = convertFileToHex("C:\\Users\\Cx3n2\\Desktop\\aqlemi.zip");
        //System.out.println(hex);

        //metodus();

        //readImage("417321468_327397630195961_8865867596264536829_n (1) (1).png");
    }

    private static void metodus() {
        // Data of the file read and written in its hexadecimal form
        String zipHexadecimal = convertFileToHex("123.zip");

        //TODO: delete this
        before_writing = zipHexadecimal;

        // Matrix where encoded data will be stored and then will be converted into corresponding image 1920 × 1080
        List<List<Color>> encodedImageMatrix = new ArrayList<>(1080);


        List<Color> row1OfEncodedImageMatrix = new ArrayList<>(1920);
        List<Color> row2OfEncodedImageMatrix = new ArrayList<>(1920);
        List<Color> row3OfEncodedImageMatrix = new ArrayList<>(1920);
        List<Color> row4OfEncodedImageMatrix = new ArrayList<>(1920);

        for (int i = 0; i < zipHexadecimal.length(); i++)
        {
            // If size of each row exceeds 1920 - 4 (because there is 4 x 4 pixels) add them to the matrix
            // NOTE: all four rows have same length, so we only check first one
            if (row1OfEncodedImageMatrix.size() > 1916)
            {
                encodedImageMatrix.add(row1OfEncodedImageMatrix);
                encodedImageMatrix.add(row2OfEncodedImageMatrix);
                encodedImageMatrix.add(row3OfEncodedImageMatrix);
                encodedImageMatrix.add(row4OfEncodedImageMatrix);

                // Create new rows to add rest of the data
                row1OfEncodedImageMatrix = new ArrayList<>(1920);
                row2OfEncodedImageMatrix = new ArrayList<>(1920);
                row3OfEncodedImageMatrix = new ArrayList<>(1920);
                row4OfEncodedImageMatrix = new ArrayList<>(1920);
            }


            // Current character which is also a hexadecimal digit
            var hexNumberAtI = zipHexadecimal.charAt(i);
            //  n x n Pixel will be colored with corresponding color
            for (int j = 0; j < 4; j++)
            {
                var hexCorrespondingColor = hexToCharMap.get(hexNumberAtI).getColor();
                row1OfEncodedImageMatrix.add(hexCorrespondingColor);
                row2OfEncodedImageMatrix.add(hexCorrespondingColor);
                row3OfEncodedImageMatrix.add(hexCorrespondingColor);
                row4OfEncodedImageMatrix.add(hexCorrespondingColor);
            }
        }

        if(!row1OfEncodedImageMatrix.isEmpty()){
            encodedImageMatrix.add(row1OfEncodedImageMatrix);
            encodedImageMatrix.add(row2OfEncodedImageMatrix);
            encodedImageMatrix.add(row3OfEncodedImageMatrix);
            encodedImageMatrix.add(row4OfEncodedImageMatrix);
        }

        // Construct image with corresponding matrix
        constructImage(encodedImageMatrix);
    }

    public static List<List<Color>> readImage(String imagePath)
    {
        List<List<Color>> matrix = new ArrayList<>();

        try {
            File input = new File(imagePath);
            BufferedImage image = ImageIO.read(input);

            if (image == null) {
                System.out.println("Could not read the image");
                return null;
            }

            convertImageToHex(image);


            // Get image dimensions
            int width = image.getWidth();
            int height = image.getHeight();

            for (int i = 0; i < height; i++)
            {
                var row = new ArrayList<Color>(1920);
                for (int j = 0; j < width; j++)
                {
                    var pixelColor = image.getRGB(j, i);
                    int red = (pixelColor >> 16) & 0xFF;
                    int green = (pixelColor >> 8) & 0xFF;
                    int blue = pixelColor & 0xFF;
                    row.add(new Color(red, green, blue));
                }
                matrix.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




        return matrix;
    }

    public static void constructImage(List<List<Color>> matrix)
    {
        BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        var g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 1920, 1080); // give the whole image a white background

        for (int y = 0; y < matrix.size(); y++)
        {
            int x = 0;
            var currentRow = matrix.get(y);
            for (Color color : currentRow) {
                g.setColor(color);
                g.fillRect(x, y, 1, 1);
                x++;
            }
        }

        g.dispose();


        // Save the constructed image as a PNG file
        try {
            File output = new File("compressedImage.png");
            ImageIO.write(image, "png", output);
            System.out.println("Image saved successfully at: " + output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void convertImageToHex(BufferedImage image){
        List<Color> colors = new ArrayList<>();

        for (int i = 0; i < 1080; i+=4) {
            int color = 0;

            for (int j = 0; j < 1920; j+=4) {
                //TODO: mere sashualo qeni

                color = image.getRGB(j,i);

                if (Color.white.getRGB() == color){
                    System.out.println("found white stopped");
                    break;
                }

                // Components will be in the range of 0..255:
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;

                colors.add(new Color(red, green, blue));
                System.out.println(new Color(red, green, blue));
            }

            if (Color.white.getRGB() == color){
                System.out.println("found white stopped");
                break;
            }

        }

        StringBuilder sb = new StringBuilder();

        for (Color color : colors) {
            sb.append(getKey(CustomColors.findClosestColor(color)));
        }

        convertHexToFile(sb.toString(), "test.zip");
    }

    private static char getKey(CustomColors color) {
        for (var entry : hexToCharMap.entrySet()) {
            if (color.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return '?';
    }

    private static String convertFileToHex(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            StringBuilder hexString = new StringBuilder();
            int data;
            while ((data = fis.read()) != -1) {
                hexString.append(String.format("%02X", data));
            }
            return hexString.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void convertHexToFile(String hexRepresentation, String outputPath) {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            byte[] bytes = hexStringToByteArray(hexRepresentation);
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}