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
            <activity-table
            v-if="activitys && activitys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:activitys="activitys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-activitys="getAllActivitys"
             >

            </activity-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ActivityTable from "@/components/ActivityTable";
import ActivityService from "../services/ActivityService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ActivityTable,
  },
  data() {
    return {
      activitys: [],
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
    async getAllActivitys(sortBy='activityId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ActivityService.getAllActivitys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.activitys.length) {
					this.activitys = response.data.activitys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching activitys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching activity details:", error);
      }
    },
  },
  mounted() {
    this.getAllActivitys();
  },
  created() {
    this.$root.$on('searchQueryForActivitysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllActivitys();
    })
  }
};
</script>
<style></style>
