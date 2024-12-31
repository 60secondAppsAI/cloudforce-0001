import http from "../http-common"; 

class LeadService {
  getAllLeads(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/lead/leads`, searchDTO);
  }
 

  get(leadId) {
    return this.getRequest(`/lead/${leadId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/lead?field=${matchData}`, null);
  }

  addLead(data) {
    return http.post("/lead/addLead", data);
  }

  update(data) {
  	return http.post("/lead/updateLead", data);
  }
  
  uploadImage(data,leadId) {
  	return http.postForm("/lead/uploadImage/"+leadId, data);
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

export default new LeadService();
