import http from "../http-common"; 

class PriceBookEntryService {
  getAllPriceBookEntrys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/priceBookEntry/priceBookEntrys`, searchDTO);
  }
 

  get(priceBookEntryId) {
    return this.getRequest(`/priceBookEntry/${priceBookEntryId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/priceBookEntry?field=${matchData}`, null);
  }

  addPriceBookEntry(data) {
    return http.post("/priceBookEntry/addPriceBookEntry", data);
  }

  update(data) {
  	return http.post("/priceBookEntry/updatePriceBookEntry", data);
  }
  
  uploadImage(data,priceBookEntryId) {
  	return http.postForm("/priceBookEntry/uploadImage/"+priceBookEntryId, data);
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

export default new PriceBookEntryService();
