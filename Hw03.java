import java.io.*;
import java.util.*;

class Hw03{

    public static int xram(String input, int length){
        int rand1 = 0xbcde98ef;
        int rand2 = 0x7890face;
        int hashValue = 0xfa01bc96;
        int roundedEnd = (length & 0xfffffffc);
        int tmpData = 0; //how do i initialize?
       
        int[] data = new int[length];
        for(int i=0; i<length; i++){
            data[i] = (int)input.charAt(i);
        }

        for(int i=0; i<roundedEnd; i+=4){
            tmpData = (data[i] & 0xff)|((data[i+ 1] & 0xff)<<8)|((data[i+ 2] & 0xff)<<16)|(data[i+ 3]<<24);
            tmpData *= rand1;
            tmpData = Integer.rotateLeft(tmpData,12);
            tmpData *= rand2;
            hashValue = hashValue ^ tmpData;
            hashValue = Integer.rotateLeft(hashValue,13);
            hashValue = (hashValue * 5) + 0x46b6456e;
        }
        tmpData = 0;

        if((length & 0x03) == 3){
            tmpData = (data[roundedEnd + 2] & 0xff) << 16;
            length -= 1;
        }
        if((length & 0x03) == 2){
            
            tmpData |= (data[roundedEnd + 1] & 0xff) << 8;
            length -= 1;
        }
        if((length & 0x03) == 1){
            tmpData = tmpData | (data[roundedEnd] & 0xff);
            tmpData = tmpData * rand1;
            tmpData = Integer.rotateLeft(tmpData,14);
            tmpData = tmpData * rand2;
            hashValue = hashValue ^ tmpData;
        }

        hashValue = hashValue ^ length;
        hashValue = hashValue & 0xb6acbe58;
        hashValue = hashValue ^ hashValue >>> 13;
        hashValue = hashValue * 0x53ea2b2c;
        hashValue = hashValue ^ hashValue >>> 16;

        System.out.format("%10x:%s\n",hashValue, input);

        return hashValue;
    }

    public static void complexityIndicator(){
        System.err.println("al144291;4;4");
    }
    
    public static void main(String[] args) throws Exception
    {
        complexityIndicator();

        String infile = args[0];
        Scanner scnr = new Scanner(new File(infile));

        while(scnr.hasNext()){
            String line = scnr.nextLine();
            int length = line.length();
            xram(line, length);
        } 
        System.out.println("Input file processed");                
    }
}