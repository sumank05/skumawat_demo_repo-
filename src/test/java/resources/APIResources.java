package resources;

public enum APIResources {
    
    AccessToken("/oauth2/token"),
    LogOnUSerDetail("/LogOnUser/LogOnUserDetail"),
    LogOnUserPassword("/LogOnUser/Password"),
    PageActionRights("/LogOnUser/refresh/LogOnUser/PageAccessRight/M"),
    UserLogOff("/LogOnUser/LogOff"),
    UserChatToken("/LogOnUser/chat/token");
    
    private String resource;
    

    APIResources(String resource) {
        this.resource= resource;
        // TODO Auto-generated constructor stub
    }
    
    public String getResource()
    {
        return resource;
    }
    

}
