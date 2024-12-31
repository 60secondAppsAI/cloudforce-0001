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
            <campaign-table
            v-if="campaigns && campaigns.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:campaigns="campaigns"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-campaigns="getAllCampaigns"
             >

            </campaign-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import CampaignTable from "@/components/CampaignTable";
import CampaignService from "../services/CampaignService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    CampaignTable,
  },
  data() {
    return {
      campaigns: [],
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
    async getAllCampaigns(sortBy='campaignId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await CampaignService.getAllCampaigns(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.campaigns.length) {
					this.campaigns = response.data.campaigns;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching campaigns:", error);
        }
        
      } catch (error) {
        console.error("Error fetching campaign details:", error);
      }
    },
  },
  mounted() {
    this.getAllCampaigns();
  },
  created() {
    this.$root.$on('searchQueryForCampaignsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllCampaigns();
    })
  }
};
</script>
<style></style>
