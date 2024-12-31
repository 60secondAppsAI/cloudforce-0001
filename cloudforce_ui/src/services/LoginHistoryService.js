import http from "../http-common"; 

class LoginHistoryService {
  getAllLoginHistorys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/loginHistory/loginHistorys`, searchDTO);
  }
 

  get(loginHistoryId) {
    return this.getRequest(`/loginHistory/${loginHistoryId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/loginHistory?field=${matchData}`, null);
  }

  addLoginHistory(data) {
    return http.post("/loginHistory/addLoginHistory", data);
  }

  update(data) {
  	return http.post("/loginHistory/updateLoginHistory", data);
  }
  
  uploadImage(data,loginHistoryId) {
  	return http.postForm("/loginHistory/uploadImage/"+loginHistoryId, data);
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

export default new LoginHistoryService();
