package com.exteso.bruno.configuration.support;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum Platform {
    DEFAULT;

    public String getUrl(Environment env) {
        return "jdbc:hsqldb:mem:bruno";
    }

    public String getUsername(Environment env) {
        return "sa";
    }

    public String getPassword(Environment env) {
        return "";
    }

    public String getDialect(Environment env) {
        return "HSQLDB";
    }

    public boolean isHosting(Environment env) {
        return true;
    }
    
    public boolean useS3AsStorage(Environment env) {
        return env.getProperty("VCAP_SERVICES") != null;
    }
    
    public String getS3BucketName() {
        return "bruno"; //TODO check if hardcoded is ok
    }
    
    
    public String getS3AccessKey(Environment env) {
        return getS3Credentials(env).get("accessKey");
    }
    
    public String getS3SecretKey(Environment env) {
        return getS3Credentials(env).get("sharedSecret");
    }
    
    public String getS3Endpoint(Environment env) {
        return getS3Credentials(env).get("accessHost");
    }
    
    /**
     * 
     * Expecting a env variable with the format: BRUNO_OAUTH_PROVIDER = "{clientId: ...., clientSecret: ....}"
     * 
     * */
    public Pair<String, String> getOauthConfiguration(Environment env, String provider) {
        try {
            Map<String, String> m = MAPPER.readValue(env.getRequiredProperty("BRUNO_OAUTH_"+provider), new TypeReference<Map<String, String>>(){});
            return Pair.of(m.get("clientId"), m.get("clientSecret"));
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading property/env variable OAUTH_" + provider, e);
        }
    }
    
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    /**
     * targeting the s3 service from swisscom cloud
     * <pre>return 
    
    {
        "dynstrg": [
          {
            "credentials": {
              "accessHost": 
              "accessKey": 
              "sharedSecret": 
            },
            ..
          }
        ]
      }
      </pre>
     */
    @SuppressWarnings("unchecked")
    private static Map<String, String> getS3Credentials(Environment env) {
        try {
            Map<String, Object> r = MAPPER.readValue(env.getProperty("VCAP_SERVICES"), new TypeReference<Map<String, Object>>(){});
            return (Map<String, String>) ((Map<String, Object>)((List<Object>) r.get("dynstrg")).get(0)).get("credentials");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
