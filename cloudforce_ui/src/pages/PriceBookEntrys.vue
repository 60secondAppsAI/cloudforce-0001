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
            <priceBookEntry-table
            v-if="priceBookEntrys && priceBookEntrys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:priceBookEntrys="priceBookEntrys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-price-book-entrys="getAllPriceBookEntrys"
             >

            </priceBookEntry-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PriceBookEntryTable from "@/components/PriceBookEntryTable";
import PriceBookEntryService from "../services/PriceBookEntryService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PriceBookEntryTable,
  },
  data() {
    return {
      priceBookEntrys: [],
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
    async getAllPriceBookEntrys(sortBy='priceBookEntryId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PriceBookEntryService.getAllPriceBookEntrys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.priceBookEntrys.length) {
					this.priceBookEntrys = response.data.priceBookEntrys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching priceBookEntrys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching priceBookEntry details:", error);
      }
    },
  },
  mounted() {
    this.getAllPriceBookEntrys();
  },
  created() {
    this.$root.$on('searchQueryForPriceBookEntrysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPriceBookEntrys();
    })
  }
};
</script>
<style></style>
