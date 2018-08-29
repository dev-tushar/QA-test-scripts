package org.myProject.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;
public class SampleDataProvider {

  @DataProvider
  public static Iterator<Object[]> fileDataProvider() {
    String currentDir = System.getProperty("user.dir");
    String inputFile = "";
    YamlReader.setYamlFilePath();
    int journalCount =Integer.parseInt(YamlReader.getYamlValue("journalsCount"));
    
    List<Object[]> dataToBeReturned = new ArrayList<Object[]>();

    try {
      if (System.getProperty("os.name").contains("Windows")) {
        inputFile = currentDir + "\\journals.csv";
      } else {
        inputFile = currentDir + "/journals.csv";
      }
      List<String> testData = getFileContentList(inputFile, journalCount);

      for (String userData : testData) {
        dataToBeReturned.add(new Object[] {userData});
      }
    } catch (Exception ex) {
      if (System.getProperty("os.name").contains("Windows")) {
        inputFile = currentDir + "\\src\\test\\resources\\testdata\\listOfJournals.csv";
      } else {
        inputFile = currentDir + "/src/test/resources/testdata/listOfJournals.csv";
      }
      List<String> testData = getFileContentList(inputFile, journalCount);

      for (String userData : testData) {
        dataToBeReturned.add(new Object[] {userData});
      }
    }
    return dataToBeReturned.iterator();

  }

  public static List<String> getFileContentList(String filenamePath, int counter) {
    InputStream is;
    List<String> lines = new ArrayList<String>();
    int count = 1;
    try {
      is = new FileInputStream(new File(filenamePath));
      DataInputStream in = new DataInputStream(is);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine;
      if (counter>0) {
        while ((strLine = br.readLine()) != null && count <= counter) {
          lines.add(strLine);
          count++;
        }
      }
      else{
        while ((strLine = br.readLine()) != null) {
          lines.add(strLine);
        }
      }
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }
}
