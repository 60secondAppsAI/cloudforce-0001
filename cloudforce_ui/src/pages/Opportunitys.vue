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
            <opportunity-table
            v-if="opportunitys && opportunitys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:opportunitys="opportunitys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-opportunitys="getAllOpportunitys"
             >

            </opportunity-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import OpportunityTable from "@/components/OpportunityTable";
import OpportunityService from "../services/OpportunityService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    OpportunityTable,
  },
  data() {
    return {
      opportunitys: [],
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
    async getAllOpportunitys(sortBy='opportunityId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await OpportunityService.getAllOpportunitys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.opportunitys.length) {
					this.opportunitys = response.data.opportunitys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching opportunitys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching opportunity details:", error);
      }
    },
  },
  mounted() {
    this.getAllOpportunitys();
  },
  created() {
    this.$root.$on('searchQueryForOpportunitysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllOpportunitys();
    })
  }
};
</script>
<style></style>
