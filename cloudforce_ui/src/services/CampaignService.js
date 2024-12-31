import http from "../http-common"; 

class CampaignService {
  getAllCampaigns(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/campaign/campaigns`, searchDTO);
  }
 

  get(campaignId) {
    return this.getRequest(`/campaign/${campaignId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/campaign?field=${matchData}`, null);
  }

  addCampaign(data) {
    return http.post("/campaign/addCampaign", data);
  }

  update(data) {
  	return http.post("/campaign/updateCampaign", data);
  }
  
  uploadImage(data,campaignId) {
  	return http.postForm("/campaign/uploadImage/"+campaignId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new CampaignService();
