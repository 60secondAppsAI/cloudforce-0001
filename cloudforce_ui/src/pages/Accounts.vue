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
            <account-table
            v-if="accounts && accounts.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:accounts="accounts"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-accounts="getAllAccounts"
             >

            </account-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import AccountTable from "@/components/AccountTable";
import AccountService from "../services/AccountService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    AccountTable,
  },
  data() {
    return {
      accounts: [],
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
    async getAllAccounts(sortBy='accountId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await AccountService.getAllAccounts(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.accounts.length) {
					this.accounts = response.data.accounts;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching accounts:", error);
        }
        
      } catch (error) {
        console.error("Error fetching account details:", error);
      }
    },
  },
  mounted() {
    this.getAllAccounts();
  },
  created() {
    this.$root.$on('searchQueryForAccountsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllAccounts();
    })
  }
};
</script>
<style></style>
