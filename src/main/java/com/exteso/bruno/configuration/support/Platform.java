package com.exteso.bruno.configuration.support;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//TODO move CF specific values in a separate ENUM
public enum Platform {
    DEFAULT;

    public String getUrl(Environment env) {
        if (isCF(env)) {
            return getServiceCredentials(env, "mariadb").get("jdbcUrl");
        } else {
            return "jdbc:hsqldb:mem:bruno";
        }
    }

    public String getUsername(Environment env) {
        if (isCF(env)) {
            return getServiceCredentials(env, "mariadb").get("username");
        } else {
            return "sa";
        }
    }

    public String getPassword(Environment env) {
        if (isCF(env)) {
            return getServiceCredentials(env, "mariadb").get("password");
        } else {
            return "";
        }
    }

    public String getDialect(Environment env) {
        return isCF(env) ? "MYSQL" : "HSQLDB";
    }

    public boolean isHosting(Environment env) {
        return true;
    }
    
    public boolean useS3AsStorage(Environment env) {
        return isCF(env);
    }
    
    private boolean isCF(Environment env) {
        return env.getProperty("VCAP_SERVICES") != null;
    }
    
    public String getS3BucketName() {
        return "bruno"; //TODO check if hardcoded is ok
    }
    
    
    public String getS3AccessKey(Environment env) {
        return getServiceCredentials(env, "dynstrg").get("accessKey");
    }
    
    public String getS3SecretKey(Environment env) {
        return getServiceCredentials(env, "dynstrg").get("sharedSecret");
    }
    
    public String getS3Endpoint(Environment env) {
        return getServiceCredentials(env, "dynstrg").get("accessHost");
    }
    
    public Set<Pair<String, String>> getAdmins(Environment env) {
        
        if(!env.containsProperty("BRUNO_ADMIN_USERS")) {
            return Collections.emptySet();
        }
        
        try {
            List<Map<String, String>> res = MAPPER.readValue(env.getRequiredProperty("BRUNO_ADMIN_USERS"), new TypeReference<List<Map<String, String>>>(){});
            return res.stream().map(m -> Pair.of(m.get("provider"), m.get("username"))).collect(Collectors.toSet());
        } catch(IOException e) {
            throw new IllegalStateException("error while reading BRUNO_ADMIN_USERS value", e);
        }
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
    
    public boolean hasOauthConfiguration(Environment env, String provider) {
        return env.containsProperty("BRUNO_OAUTH_"+provider);
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
    private static Map<String, String> getServiceCredentials(Environment env, String name) {
        try {
            Map<String, Object> r = MAPPER.readValue(env.getProperty("VCAP_SERVICES"), new TypeReference<Map<String, Object>>(){});
            return (Map<String, String>) ((Map<String, Object>)((List<Object>) r.get(name)).get(0)).get("credentials");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
