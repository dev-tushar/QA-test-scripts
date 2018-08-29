package org.myProject.utils;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.http.entity.ContentType;
import org.testng.Reporter;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class IEEEFileUploading {
	
	public static void cleanDownloads(String downloadPath) {
		File file = new File(downloadPath);
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}

	}
	
	public static void fileUploadAPI(String sBallotID, String sPARID, String sSource, String sFileName, String sUserName,
			String sFileLocation) throws InterruptedException, IOException {
		//File sfile= new File(sFileLocation);
		OkHttpClient client = new OkHttpClient();
		RequestBody reqBody = null;
		Request request = new Request.Builder().url(
				"http://myprojectqa.standards.ieee.org/myproject-web/rest/api/public/util/ballot/attachment/upload?srcId1="
						+ sBallotID + "&srcId2=" + sPARID + "&src="+sSource+"&fileName=" + sFileName + "&userEmail="
						+ sUserName + "&voteId=0")
				.post(reqBody.create(MediaType.parse("application/octet-stream"), sFileLocation))
				.addHeader("cache-control", "no-cache").build();

		Response response = client.newCall(request).execute();
		assertTrue(response.isSuccessful(),
				"[ASSERTION FAILED]: Unable to upload file for the PAR. Source Type: "+sSource);
		Reporter.log("[ASSERTION PASSED]: Able to upload file for the PAR. Source Type: "+sSource);

	}
	
	public static void ExcelfileUploadAPI(String sBallotID, String sSource, String sFileName, String sUserName,
			String sUploadType, String sFileLocation) throws InterruptedException, IOException {
		File sfile= new File(sFileLocation);
		
		OkHttpClient client = new OkHttpClient();
		RequestBody reqBody = null;
		Request request = new Request.Builder().url(
				"http://myprojectqa.standards.ieee.org/myproject-web/rest/api/public/util/ballot/attachment/upload?srcId1="
						+ sBallotID + "&src=" + sSource + "&fileName=" + sFileName + "&userEmail="
						+ sUserName + "&uploadType=" + sUploadType + "&voteId=0&recirc=0&srcId2=0")
				.post(reqBody.create(MediaType.parse("application/octet-stream"), sfile))
				.build();

		Response response = client.newCall(request).execute();
		
		assertTrue(response.isSuccessful(),
				"[ASSERTION FAILED]: Unable to upload file for the PAR. Upload Type: " + sUploadType);
		Reporter.log("[ASSERTION PASSED]: Able to upload file for the PAR. upload Type: " + sUploadType);

	}
	
}
