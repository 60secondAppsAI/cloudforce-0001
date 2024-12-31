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
            <priceBook-table
            v-if="priceBooks && priceBooks.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:priceBooks="priceBooks"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-price-books="getAllPriceBooks"
             >

            </priceBook-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PriceBookTable from "@/components/PriceBookTable";
import PriceBookService from "../services/PriceBookService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PriceBookTable,
  },
  data() {
    return {
      priceBooks: [],
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
    async getAllPriceBooks(sortBy='priceBookId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PriceBookService.getAllPriceBooks(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.priceBooks.length) {
					this.priceBooks = response.data.priceBooks;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching priceBooks:", error);
        }
        
      } catch (error) {
        console.error("Error fetching priceBook details:", error);
      }
    },
  },
  mounted() {
    this.getAllPriceBooks();
  },
  created() {
    this.$root.$on('searchQueryForPriceBooksChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPriceBooks();
    })
  }
};
</script>
<style></style>
