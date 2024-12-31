import http from "../http-common"; 

class ActivityService {
  getAllActivitys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/activity/activitys`, searchDTO);
  }
 

  get(activityId) {
    return this.getRequest(`/activity/${activityId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/activity?field=${matchData}`, null);
  }

  addActivity(data) {
    return http.post("/activity/addActivity", data);
  }

  update(data) {
  	return http.post("/activity/updateActivity", data);
  }
  
  uploadImage(data,activityId) {
  	return http.postForm("/activity/uploadImage/"+activityId, data);
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

export default new ActivityService();
