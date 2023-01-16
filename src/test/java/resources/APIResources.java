package resources;

public enum APIResources {
    
    AccessToken("/oauth2/token"),
    LogOnUSerDetail("/LogOnUser/LogOnUserDetail"),
    LogOnUserPassword("/LogOnUser/Password"),
    PageActionRights("/LogOnUser/refresh/LogOnUser/PageAccessRight/M"),
    UserLogOff("/LogOnUser/LogOff"),
    UserChatToken("/LogOnUser/chat/token"),
    
    //Announcement apis
	TriggerType("/Engage/Announcement/TriggerType"),
	RecurringType("/Engage/Announcement/RecurringType"),
	EventType("Engage/Announcement/EventType/2"),
	Announcement("/Engage/Announcement"),
	Tag("/Engage/Announcement/Tag"),
	SuperAnnouncement("/Engage/Super/Announcement/3"),
	EmployeeAnnouncement("/Engage/Announcement/6/4340"),
	
	//Bank apis
	Bank("/core/Bank"),
	ServiceProvider("/core/Bank/Provider/HDFC"),
	BankActive("/core/Bank/2/true"),
	BankCode("/core/Bank/2"),
	PayMode("/core/PayMode"),                             
	BankBranch("/core/BankBranch"),
	BankBranchByCode("/core/BankBranch/19"),                             //Bankbranch code 19,13,18,16,17,14,15
	BankChallan("/core/BankBranch/Challan"),
	BankAlias("/core/BankBranch/Alias"),
	BankList("/core/BankBranch/Register/List/19"),
	
	//Candidateapis
	CandidateStatus("/recruitment/Candidate/Status"),
	JoiningSource("/recruitment/Candidate/JoiningSource"),
	Category("/recruitment/Candidate/JoiningSourceCategory"),
	CandidateRound("/recruitment/Candidate/Bucket"),
	CandidateFilter("/recruitment/Candidate/Filters"),
	SocialProfile("/recruitment/Candidate/Social"),
	
	//ApplicationVersion Apis
	ApplicationVersion("/core/ApplicationVersion"), //PageAcessRight error
	
	//AssetCard Apis
	AssetCard("/Asset/CardMaster"),
	FormID("/Asset/Card/2"),
	
	//AssetGeneralSetting APis
	GeneralSettings("/Asset/GeneralSetting"),
	
	//AutoArchiveServiceSetting
	AutoArchiveServiceSetting("/core/autoarchive");
	
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
