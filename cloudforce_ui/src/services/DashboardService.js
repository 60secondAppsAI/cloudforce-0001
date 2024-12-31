import http from "../http-common"; 

class DashboardService {
  getAllDashboards(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/dashboard/dashboards`, searchDTO);
  }
 

  get(dashboardId) {
    return this.getRequest(`/dashboard/${dashboardId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/dashboard?field=${matchData}`, null);
  }

  addDashboard(data) {
    return http.post("/dashboard/addDashboard", data);
  }

  update(data) {
  	return http.post("/dashboard/updateDashboard", data);
  }
  
  uploadImage(data,dashboardId) {
  	return http.postForm("/dashboard/uploadImage/"+dashboardId, data);
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

export default new DashboardService();
