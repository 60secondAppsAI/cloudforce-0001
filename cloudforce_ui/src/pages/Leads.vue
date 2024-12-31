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
            <lead-table
            v-if="leads && leads.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:leads="leads"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-leads="getAllLeads"
             >

            </lead-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import LeadTable from "@/components/LeadTable";
import LeadService from "../services/LeadService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    LeadTable,
  },
  data() {
    return {
      leads: [],
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
    async getAllLeads(sortBy='leadId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await LeadService.getAllLeads(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.leads.length) {
					this.leads = response.data.leads;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching leads:", error);
        }
        
      } catch (error) {
        console.error("Error fetching lead details:", error);
      }
    },
  },
  mounted() {
    this.getAllLeads();
  },
  created() {
    this.$root.$on('searchQueryForLeadsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllLeads();
    })
  }
};
</script>
<style></style>
