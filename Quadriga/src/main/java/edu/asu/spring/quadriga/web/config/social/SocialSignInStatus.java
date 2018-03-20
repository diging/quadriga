package edu.asu.spring.quadriga.web.config.social;

import java.util.HashMap;
import java.util.Map;

public enum SocialSignInStatus {
    REGISTRATION_SUCCESS("1"), REGISTRATION_FAILED("2");
    
    private String socialSignInStatusType;
    private static final Map<String, SocialSignInStatus> map = new HashMap<>(values().length, 1);

    static {
      for (SocialSignInStatus s : values()) map.put(s.socialSignInStatusType, s);
    }
    
    private SocialSignInStatus(String socialSignInStatusType){
        this.socialSignInStatusType = socialSignInStatusType;
    }
    
    public String getSocialSignInStatusType(){
         return socialSignInStatusType;
    }
    
    public static SocialSignInStatus getSocialSignInStatus(String type) {
        SocialSignInStatus result = map.get(type);
        if (result == null) {
          throw new IllegalArgumentException("Invalid status: " + type);
        }
        return result;
    }
}
