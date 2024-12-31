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
            <emailTemplate-table
            v-if="emailTemplates && emailTemplates.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:emailTemplates="emailTemplates"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-email-templates="getAllEmailTemplates"
             >

            </emailTemplate-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import EmailTemplateTable from "@/components/EmailTemplateTable";
import EmailTemplateService from "../services/EmailTemplateService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    EmailTemplateTable,
  },
  data() {
    return {
      emailTemplates: [],
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
    async getAllEmailTemplates(sortBy='emailTemplateId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await EmailTemplateService.getAllEmailTemplates(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.emailTemplates.length) {
					this.emailTemplates = response.data.emailTemplates;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching emailTemplates:", error);
        }
        
      } catch (error) {
        console.error("Error fetching emailTemplate details:", error);
      }
    },
  },
  mounted() {
    this.getAllEmailTemplates();
  },
  created() {
    this.$root.$on('searchQueryForEmailTemplatesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllEmailTemplates();
    })
  }
};
</script>
<style></style>
