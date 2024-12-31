import http from "../http-common"; 

class TaskService {
  getAllTasks(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/task/tasks`, searchDTO);
  }
 

  get(taskId) {
    return this.getRequest(`/task/${taskId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/task?field=${matchData}`, null);
  }

  addTask(data) {
    return http.post("/task/addTask", data);
  }

  update(data) {
  	return http.post("/task/updateTask", data);
  }
  
  uploadImage(data,taskId) {
  	return http.postForm("/task/uploadImage/"+taskId, data);
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

export default new TaskService();
