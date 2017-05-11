package edu.asu.spring.quadriga.web.manageusers.beans;


public class AccountRequest {

    private String name;
    private String username;
	private String email;
	private String password;
	private String repeatedPassword;
	private String provider;
	private String userIdOfProvider;
	private boolean socialSignIn;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
    public String getProvider() {
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }
    public String getUserIdOfProvider() {
        return userIdOfProvider;
    }
    public void setUserIdOfProvider(String userIdOfProvider) {
        this.userIdOfProvider = userIdOfProvider;
    }
    public boolean isSocialSignIn() {
        return socialSignIn;
    }
    public void setSocialSignIn(boolean socialSignIn) {
        this.socialSignIn = socialSignIn;
    }
}
