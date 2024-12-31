import http from "../http-common"; 

class OpportunityService {
  getAllOpportunitys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/opportunity/opportunitys`, searchDTO);
  }
 

  get(opportunityId) {
    return this.getRequest(`/opportunity/${opportunityId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/opportunity?field=${matchData}`, null);
  }

  addOpportunity(data) {
    return http.post("/opportunity/addOpportunity", data);
  }

  update(data) {
  	return http.post("/opportunity/updateOpportunity", data);
  }
  
  uploadImage(data,opportunityId) {
  	return http.postForm("/opportunity/uploadImage/"+opportunityId, data);
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

export default new OpportunityService();
