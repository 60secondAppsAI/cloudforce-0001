import http from "../http-common"; 

class PermissionService {
  getAllPermissions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/permission/permissions`, searchDTO);
  }
 

  get(permissionId) {
    return this.getRequest(`/permission/${permissionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/permission?field=${matchData}`, null);
  }

  addPermission(data) {
    return http.post("/permission/addPermission", data);
  }

  update(data) {
  	return http.post("/permission/updatePermission", data);
  }
  
  uploadImage(data,permissionId) {
  	return http.postForm("/permission/uploadImage/"+permissionId, data);
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

export default new PermissionService();
