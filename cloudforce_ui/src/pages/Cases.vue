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
            <case-table
            v-if="cases && cases.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:cases="cases"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-cases="getAllCases"
             >

            </case-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import CaseTable from "@/components/CaseTable";
import CaseService from "../services/CaseService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    CaseTable,
  },
  data() {
    return {
      cases: [],
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
    async getAllCases(sortBy='caseId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await CaseService.getAllCases(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.cases.length) {
					this.cases = response.data.cases;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching cases:", error);
        }
        
      } catch (error) {
        console.error("Error fetching case details:", error);
      }
    },
  },
  mounted() {
    this.getAllCases();
  },
  created() {
    this.$root.$on('searchQueryForCasesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllCases();
    })
  }
};
</script>
<style></style>
