import http from "../http-common"; 

class EventService {
  getAllEvents(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/event/events`, searchDTO);
  }
 

  get(eventId) {
    return this.getRequest(`/event/${eventId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/event?field=${matchData}`, null);
  }

  addEvent(data) {
    return http.post("/event/addEvent", data);
  }

  update(data) {
  	return http.post("/event/updateEvent", data);
  }
  
  uploadImage(data,eventId) {
  	return http.postForm("/event/uploadImage/"+eventId, data);
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

export default new EventService();
