<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <product-table
            v-if="products && products.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:products="products"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-products="getAllProducts"
             >

            </product-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ProductTable from "@/components/ProductTable";
import ProductService from "../services/ProductService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ProductTable,
  },
  data() {
    return {
      products: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllProducts(sortBy='productId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ProductService.getAllProducts(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.products.length) {
					this.products = response.data.products;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching products:", error);
        }
        
      } catch (error) {
        console.error("Error fetching product details:", error);
      }
    },
  },
  mounted() {
    this.getAllProducts();
  },
  created() {
    this.$root.$on('searchQueryForProductsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllProducts();
    })
  }
};
</script>
<style></style>
