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
            <dashboard-table
            v-if="dashboards && dashboards.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:dashboards="dashboards"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-dashboards="getAllDashboards"
             >

            </dashboard-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import DashboardTable from "@/components/DashboardTable";
import DashboardService from "../services/DashboardService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    DashboardTable,
  },
  data() {
    return {
      dashboards: [],
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
    async getAllDashboards(sortBy='dashboardId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await DashboardService.getAllDashboards(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.dashboards.length) {
					this.dashboards = response.data.dashboards;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching dashboards:", error);
        }
        
      } catch (error) {
        console.error("Error fetching dashboard details:", error);
      }
    },
  },
  mounted() {
    this.getAllDashboards();
  },
  created() {
    this.$root.$on('searchQueryForDashboardsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllDashboards();
    })
  }
};
</script>
<style></style>
