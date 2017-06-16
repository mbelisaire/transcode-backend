/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transcode.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.Stateless;

/**
 *
 * @author myriam
 */
@Stateless
public class TranscodeService {
    
    public String transcode(String in, String out){
        
        try {
            in = "\"d:\\Supinfo Courses\\TranscodeOfficial\\Transcode\\Pile\\" + in + "\"";
            out = "\"d:\\Supinfo Courses\\TranscodeOfficial\\Transcode\\Files\\" + out + "\"";
            /*ProcessBuilder pb = new ProcessBuilder();
            pb.command("ffmpeg", "-i", in, out);*/
            final Process p = Runtime.getRuntime().exec("ffmpeg -i " + in + " " + out);
            //final Process p = pb.start();
            /*InputStream stdin = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<OUTPUT>");
            while ( (line = br.readLine()) != null)
                System.out.println(line);*/
            
            
            new Thread(){
                public void run(){
                    Scanner sc = new Scanner(p.getErrorStream());
                    
                    Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");
                    String dur = sc.findWithinHorizon(durPattern, 0);
                    if (dur == null)
                        throw new RuntimeException("Could not parse duration.");
                    String[] hms = dur.split(":");
                    double totalSecs = Integer.parseInt(hms[0]) * 3600
                            + Integer.parseInt(hms[1]) *   60
                            + Double.parseDouble(hms[2]);
                    System.out.println("Total duration: " + totalSecs + " seconds.");
                    
                    Pattern timePattern = Pattern.compile("(?<=time=)[\\d:.]*");
                    String match;
                    String[] matchSplit;
                    while (null != (match = sc.findWithinHorizon(timePattern, 0))) {
                        matchSplit = match.split(":");
                        double progress = Integer.parseInt(matchSplit[0]) * 3600 +
                                Integer.parseInt(matchSplit[1]) * 60 +
                                Double.parseDouble(matchSplit[2]) / totalSecs;
                        System.out.printf("Progress: %.2f%%%n", progress * 100);
                    }
                }
            }.start();
            System.out.println("</OUTPUT>");
            p.waitFor();            
            //System.out.println("Process exitValue: " + exitVal);
            System.out.println("fin conversion!");
            
            String length = "";
            return length;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(TranscodeService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(TranscodeService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
