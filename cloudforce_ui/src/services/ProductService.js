import http from "../http-common"; 

class ProductService {
  getAllProducts(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/product/products`, searchDTO);
  }
 

  get(productId) {
    return this.getRequest(`/product/${productId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/product?field=${matchData}`, null);
  }

  addProduct(data) {
    return http.post("/product/addProduct", data);
  }

  update(data) {
  	return http.post("/product/updateProduct", data);
  }
  
  uploadImage(data,productId) {
  	return http.postForm("/product/uploadImage/"+productId, data);
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

export default new ProductService();
