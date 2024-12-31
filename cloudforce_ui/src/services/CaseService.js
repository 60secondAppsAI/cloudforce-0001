import http from "../http-common"; 

class CaseService {
  getAllCases(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/case/cases`, searchDTO);
  }
 

  get(caseId) {
    return this.getRequest(`/case/${caseId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/case?field=${matchData}`, null);
  }

  addCase(data) {
    return http.post("/case/addCase", data);
  }

  update(data) {
  	return http.post("/case/updateCase", data);
  }
  
  uploadImage(data,caseId) {
  	return http.postForm("/case/uploadImage/"+caseId, data);
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

export default new CaseService();
