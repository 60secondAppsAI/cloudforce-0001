import http from "../http-common"; 

class ContactService {
  getAllContacts(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/contact/contacts`, searchDTO);
  }
 

  get(contactId) {
    return this.getRequest(`/contact/${contactId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/contact?field=${matchData}`, null);
  }

  addContact(data) {
    return http.post("/contact/addContact", data);
  }

  update(data) {
  	return http.post("/contact/updateContact", data);
  }
  
  uploadImage(data,contactId) {
  	return http.postForm("/contact/uploadImage/"+contactId, data);
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

export default new ContactService();
