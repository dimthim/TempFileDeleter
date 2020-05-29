package com.company;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Scanner;

public class Main {

    private static class Date{

        public Date(int y, int m, int d){
            year = y;
            month = m;
            day = d;
            total = (year*1000)+(month*40)+(day);
        }
        private int year = 0;
        private int month = 0;
        private int day = 0;
        private int total = 0;

        private void DateTotal()
        {
            total = ((year*1000)+(month*40)+(day));
        }

        private void CopyDate(Date src)
        {
            year = src.year;
            month = src.month;
            day = src.day;
            total = src.total;
        }
    }


    public static void main(String[] args) throws IOException {

        // Receive names of directories to be read-in
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter the path of the directory that you would like to trim: ");
        String userDir = userInput.nextLine();
        Path dir = Paths.get(userDir);

        BasicFileAttributes attr;
        Date FirstValidDate = new Date(0,0,0);
        Date LastValidDate = new Date(0,0,0);
        Date thisDate = new Date(0,0,0);
        FileTime CreatedDate;

        //DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir);
       try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir);){
           for (Path file : dirStream){
               //try {
                   attr = Files.readAttributes(file, BasicFileAttributes.class);
                   CreatedDate = attr.creationTime();
                   System.out.println(CreatedDate);
                   String[] date = CreatedDate.toString().split("-");
                   thisDate.year = Integer.parseInt(date[0]);
                   thisDate.month = Integer.parseInt(date[1]);
                   thisDate.day = Integer.parseInt(date[2],0,2,10);
                   thisDate.DateTotal();
                   FirstValidDate.DateTotal();
                   LastValidDate.DateTotal();
                   if(thisDate.total > FirstValidDate.total){
                       LastValidDate.CopyDate(FirstValidDate);
                       FirstValidDate.CopyDate(thisDate);
                   }else if (thisDate.total < FirstValidDate.total && thisDate.total > LastValidDate.total) {
                       LastValidDate.CopyDate(thisDate);
                   }
              /* } catch (IOException e) {
                   System.out.println("oops error! " + e.getMessage());
               }*/
           }
       }
        try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir);) {
            for (Path file : dirStream) {
                attr = Files.readAttributes(file, BasicFileAttributes.class);
                CreatedDate= attr.creationTime();
                String[] date = CreatedDate.toString().split("-");
                thisDate.year = Integer.parseInt(date[0]);
                thisDate.month = Integer.parseInt(date[1]);
                thisDate.day = Integer.parseInt(date[2], 0, 2, 10);
                thisDate.DateTotal();
                if (thisDate.total < LastValidDate.total){
                    //Files.delete(file);
                    System.out.println(file.toString());
                }
            }
        }
    }
}
