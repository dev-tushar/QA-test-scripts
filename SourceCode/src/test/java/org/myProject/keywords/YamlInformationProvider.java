package org.myProject.keywords;

import static org.myProject.utils.YamlReader.getYamlValue;
import static org.myProject.utils.YamlReader.getYamlValues;

import java.util.Map;

public class YamlInformationProvider {
	

		Map<String, Object> userInfoMap;
		String applicationToken;

		public YamlInformationProvider() {
		}
		
		public YamlInformationProvider(Map<String, Object> userInfoMap) {
			this.userInfoMap = userInfoMap;
		}
		
		public String getApplicationURL(){
			return getYamlValue("baseUrl");
		}
		
		public String getUserDetails(String token){
			return getYamlValue("userDetails"+"."+token);
		}
		
			
		
		public Map<String, Object> getValuesForBluePearl(String token){
			return  getYamlValues("bluePearl."+token);
		}
		


}
