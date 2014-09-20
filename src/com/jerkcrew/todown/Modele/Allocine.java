package com.jerkcrew.todown.Modele;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import android.util.Base64;

public class Allocine {

    public static String buildURL(String secretKey, String method, HashMap<String, String> params){
    	
        // build the URL
        String url = "http://api.allocine.fr/rest/v3/"+method;

        //sed
        Date today = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String sed = dateFormat.format(today);

        
 	    //encodage des parametres en mode URL (avec des & et des =)
 	    StringBuffer paramsHttp = new StringBuffer();
 	    for (Entry<String, String> pair : params.entrySet()) {
 	        try {
				paramsHttp.append ( URLEncoder.encode ( pair.getKey (), "UTF-8" ) + "=" );
				paramsHttp.append ( URLEncoder.encode ( pair.getValue (), "UTF-8" ) + "&" );
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
 	    }
 	    if (paramsHttp.length () > 0) {
 	        paramsHttp.deleteCharAt ( paramsHttp.length () - 1 );
 	    }
 	    
 	    //encodage en sha1 de la date et des parametres
        String toDigest=secretKey+paramsHttp+"&sed="+sed;
        MessageDigest encodeurSHA1;
		try {
			encodeurSHA1 = MessageDigest.getInstance("SHA1");
			encodeurSHA1.update(toDigest.getBytes());
			String encodedBytes = Base64.encodeToString(encodeurSHA1.digest(),Base64.NO_WRAP);
			
			//encodage en format "URL" 
			String sigSecond = "";
			try {
				sigSecond = URLEncoder.encode(encodedBytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				url = "ERROR : L'encodage en UTF-8 à échoué";
				e.printStackTrace();
			}
			url += "?"+paramsHttp+"&sed="+sed+"&sig="+sigSecond;
		} catch (NoSuchAlgorithmException e1) {
			url = "ERROR : L'algorithme de SHA1 n'existe pas";
			e1.printStackTrace();
		}

        return url;
    }
}