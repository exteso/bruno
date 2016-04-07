package com.exteso.bruno.configuration.support;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        return System.getenv("VCAP_SERVICES") != null;
    }
    
    public String getS3BucketName() {
        return "bruno"; //TODO check if hardcoded is ok
    }
    
    
    public String getS3AccessKey() {
        return getS3Credentials().get("accessKey");
    }
    
    public String getS3SecretKey() {
        return getS3Credentials().get("sharedSecret");
    }
    
    public String getS3Endpoint() {
        return getS3Credentials().get("accessHost");
    }
    
    /**
     * targeting the s3 service from swisscom cloud
     * <pre>
    
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
    private static Map<String, String> getS3Credentials() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> r = mapper.readValue(System.getenv("VCAP_SERVICES"), new TypeReference<Map<String, Object>>(){});
            return (Map<String, String>) ((Map<String, Object>)((List<Object>) r.get("dynstrg")).get(0)).get("credentials");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
