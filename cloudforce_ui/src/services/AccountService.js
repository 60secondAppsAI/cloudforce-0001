import http from "../http-common"; 

class AccountService {
  getAllAccounts(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/account/accounts`, searchDTO);
  }
 

  get(accountId) {
    return this.getRequest(`/account/${accountId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/account?field=${matchData}`, null);
  }

  addAccount(data) {
    return http.post("/account/addAccount", data);
  }

  update(data) {
  	return http.post("/account/updateAccount", data);
  }
  
  uploadImage(data,accountId) {
  	return http.postForm("/account/uploadImage/"+accountId, data);
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

export default new AccountService();
