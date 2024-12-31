import http from "../http-common"; 

class GroupService {
  getAllGroups(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/group/groups`, searchDTO);
  }
 

  get(groupId) {
    return this.getRequest(`/group/${groupId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/group?field=${matchData}`, null);
  }

  addGroup(data) {
    return http.post("/group/addGroup", data);
  }

  update(data) {
  	return http.post("/group/updateGroup", data);
  }
  
  uploadImage(data,groupId) {
  	return http.postForm("/group/uploadImage/"+groupId, data);
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

export default new GroupService();
