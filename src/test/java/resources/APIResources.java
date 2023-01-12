package resources;

public enum APIResources {
    
    AccessToken("/oauth2/token"),
    LogOnUSerDetail("/LogOnUser/LogOnUserDetail"),
    LogOnUserPassword("/LogOnUser/Password"),
    PageActionRights("/LogOnUser/refresh/LogOnUser/PageAccessRight/M"),
    UserLogOff("/LogOnUser/LogOff"),
    UserChatToken("/LogOnUser/chat/token"),
	TriggerType("/Engage/Announcement/TriggerType"),
	RecurringType("/Engage/Announcement/RecurringType"),
	EventType("Engage/Announcement/EventType/2"),
	Announcement("/Engage/Announcement"),
	Tag("/Engage/Announcement/Tag"),
	SuperAnnouncement("/Engage/Super/Announcement/3"),
	EmployeeAnnouncement("/Engage/Announcement/6/4340");
	
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
