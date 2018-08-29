package org.myProject.getpageobjects;

import static org.myProject.utils.ConfigPropertyReader.getProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.myProject.TestSessionInitiator;
import org.testng.Assert;


/**
 * This class reads the PageObjectRepository text files. Uses buffer reader.
 *
 * @author QAIT
 *
 */
public class ObjectFileReader {

    static String tier,product,tierInputName;
    static String filepath = "src/test/resources/PageObjectRepository/";

    public static String[] getELementFromFile(String pageName,
            String elementName) {
        setTier();
        setProduct();
        try {
          FileReader specFile = new FileReader(filepath + product + File.separator + tier + File.separator+ pageName + ".spec");
          return getElement(specFile, elementName);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }

    public static String getPageTitleFromFile(String pageName) {
        setTier();
        setProduct();
        BufferedReader br = null;
        String returnElement = "";
        try {
            br = new BufferedReader(
                    new FileReader(filepath + product + File.separator +tier + File.separator + pageName + ".spec"));
            String line = br.readLine();

            while (line != null) {
                if (line.split(":", 3)[0].equalsIgnoreCase("pagetitle")
                        || line.split(":", 3)[0].equalsIgnoreCase("title")) {
                    returnElement = line;
                    break;
                }
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnElement.split(":", 3)[1].trim();
    }

    private static String[] getElement(FileReader specFile, String elementName) throws Exception {

        String[] elementLineSplit;
        ArrayList<String> elementLines = getSpecSection(specFile);

        for (String elementLine : elementLines) {
          elementLineSplit = elementLine.split(" ");

          if (elementLineSplit[0].trim().equalsIgnoreCase(elementName)) {
            return elementLine.split(" ", 3);
          } else {
            continue;
          }
        }
        throw new Exception();
      }

      private static ArrayList<String> getSpecSection(FileReader specfile) {
        String readBuff = null;
        ArrayList<String> elementLines = new ArrayList<String>();

        try {
          BufferedReader buff = new BufferedReader(specfile);
          try {
            boolean flag = false;
            readBuff = buff.readLine();
            while ((readBuff = buff.readLine()) != null) {
              if (readBuff.startsWith("========")) {
                flag = !flag;
              }
              if (flag) {
                elementLines.add(readBuff.trim().replaceAll("[ \t]+", " "));
              }
              if (!elementLines.isEmpty() && !flag) {
                break;
              }
            }
          } finally {
            buff.close();
            if (elementLines.get(0).startsWith("========")) {
              elementLines.remove(0);
            }
          }
        } catch (FileNotFoundException e) {
          System.out.println("Spec File not found at location :- " + filepath);
        } catch (IOException e) {
          System.out.println("exceptional case");
        }
        return elementLines;
      }

    
      private static void setTier() {
    	    try {
    	      if (System.getProperty("tier").contains("defaultTier")||System.getProperty("tier").isEmpty())
    	        tierInputName = Tiers.valueOf(getProperty("Config.properties", "tier")).toString();
    	      else
    	        tierInputName = System.getProperty("tier");
    	    } catch (NullPointerException e) {
    	      tierInputName = Tiers.valueOf(getProperty("Config.properties", "tier")).toString();
    	    }
    	    switch (Tiers.valueOf(tierInputName)) {
    	      case production:
    	      case PROD:
    	      case PRODUCTION:
    	      case Production:
    	      case prod:
    	        tier = "PROD/";
    	        break;
    	      case stag:
    	      case staging:
    	      case Staging:
    	      case STAGING:
    	        tier = "STAGING/";
    	        break;
    	      default:
    	        System.out.println("Please check tier value");
    	        break;
    	    }
    	  }
    
    private static void setProduct() {
    		product = TestSessionInitiator.getProduct().toUpperCase();
    	}
}
