import http from "../http-common"; 

class PriceBookService {
  getAllPriceBooks(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/priceBook/priceBooks`, searchDTO);
  }
 

  get(priceBookId) {
    return this.getRequest(`/priceBook/${priceBookId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/priceBook?field=${matchData}`, null);
  }

  addPriceBook(data) {
    return http.post("/priceBook/addPriceBook", data);
  }

  update(data) {
  	return http.post("/priceBook/updatePriceBook", data);
  }
  
  uploadImage(data,priceBookId) {
  	return http.postForm("/priceBook/uploadImage/"+priceBookId, data);
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

export default new PriceBookService();
