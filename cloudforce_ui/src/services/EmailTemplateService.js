import http from "../http-common"; 

class EmailTemplateService {
  getAllEmailTemplates(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/emailTemplate/emailTemplates`, searchDTO);
  }
 

  get(emailTemplateId) {
    return this.getRequest(`/emailTemplate/${emailTemplateId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/emailTemplate?field=${matchData}`, null);
  }

  addEmailTemplate(data) {
    return http.post("/emailTemplate/addEmailTemplate", data);
  }

  update(data) {
  	return http.post("/emailTemplate/updateEmailTemplate", data);
  }
  
  uploadImage(data,emailTemplateId) {
  	return http.postForm("/emailTemplate/uploadImage/"+emailTemplateId, data);
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

export default new EmailTemplateService();
