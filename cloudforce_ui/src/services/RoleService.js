import http from "../http-common"; 

class RoleService {
  getAllRoles(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/role/roles`, searchDTO);
  }
 

  get(roleId) {
    return this.getRequest(`/role/${roleId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/role?field=${matchData}`, null);
  }

  addRole(data) {
    return http.post("/role/addRole", data);
  }

  update(data) {
  	return http.post("/role/updateRole", data);
  }
  
  uploadImage(data,roleId) {
  	return http.postForm("/role/uploadImage/"+roleId, data);
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

export default new RoleService();
